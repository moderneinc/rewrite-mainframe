/*
 * Copyright 2025 the original author or authors.
 * <p>
 * Licensed under the Moderne Source Available License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://docs.moderne.io/licensing/moderne-source-available-license
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openrewrite.mainframe.db2;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.*;
import org.jspecify.annotations.Nullable;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Parser;
import org.openrewrite.SourceFile;
import org.openrewrite.mainframe.db2.internal.Db2ParserVisitor;
import org.openrewrite.mainframe.db2.internal.grammar.DB2Lexer;
import org.openrewrite.mainframe.db2.internal.grammar.DB2Parser;
import org.openrewrite.mainframe.db2.tree.Db2;
import org.openrewrite.internal.EncodingDetectingInputStream;
import org.openrewrite.internal.MetricsHelper;
import org.openrewrite.tree.ParseError;
import org.openrewrite.tree.ParsingEventListener;
import org.openrewrite.tree.ParsingExecutionContextView;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Db2Parser implements Parser {

    @Override
    public Stream<SourceFile> parseInputs(Iterable<Input> sourceFiles, @Nullable Path relativeTo, ExecutionContext ctx) {
        ParsingExecutionContextView pctx = ParsingExecutionContextView.view(ctx);
        ParsingEventListener parsingListener = pctx.getParsingListener();

        return acceptedInputs(sourceFiles)
                .map(sourceFile -> {
                    Timer.Builder timer = Timer.builder("rewrite.parse")
                            .description("The time spent parsing a DB2 DDL file")
                            .tag("file.type", "DB2");
                    Timer.Sample sample = Timer.start();
                    Path path = sourceFile.getRelativePath(relativeTo);
                    try {
                        EncodingDetectingInputStream is = sourceFile.getSource(ctx);
                        String sourceStr = is.readFully();
                        Db2.Ddl cu = parse(path, sourceStr, is.getCharset(),
                                is.isCharsetBomMarked(), sourceFile.getPath(), ctx);
                        sample.stop(MetricsHelper.successTags(timer).register(Metrics.globalRegistry));
                        parsingListener.parsed(sourceFile, cu);
                        return (SourceFile) cu;
                    } catch (Throwable t) {
                        sample.stop(MetricsHelper.errorTags(timer, t).register(Metrics.globalRegistry));
                        return ParseError.build(this, sourceFile, relativeTo, pctx, t);
                    }
                });
    }

    /**
     * Reads DDL that is not a file of its own. Most of an estate's tables are created by a JCL job
     * rather than by a {@code .ddl} member, so the DDL has to be readable straight out of the text a
     * {@code SYSIN DD *} stream holds.
     *
     * @param path what to call the result, which for in-stream DDL names the job it was found in
     *             rather than anything on disk.
     * @throws Db2ParsingException when the stream holds a statement this grammar cannot read.
     */
    public Db2.Ddl parseFragment(Path path, String source, ExecutionContext ctx) {
        return parse(path, source, UTF_8, false, path, ctx);
    }

    private Db2.Ddl parse(Path path, String source, Charset charset, boolean charsetBomMarked,
                                      Path reportedPath, ExecutionContext ctx) {
        BufferingErrorListener errorListener = new BufferingErrorListener(reportedPath);
        DB2Lexer lexer = new DB2Lexer(CharStreams.fromString(source));
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);
        DB2Parser parser = new DB2Parser(new CommonTokenStream(lexer));

        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);

        DB2Parser.CompilationUnitContext cu = parser.compilationUnit();
        for (Db2ParsingException error : errorListener.errors) {
            ctx.getOnError().accept(error);
        }
        try {
            return new Db2ParserVisitor(path, null, source, charset, charsetBomMarked)
                    .visitCompilationUnit(cu);
        } catch (RuntimeException e) {
            if (errorListener.errors.isEmpty()) {
                throw e;
            }
            throw errorListener.errors.get(0);
        }
    }

    @Override
    public boolean accept(Path path) {
        String name = path.toString().toLowerCase();
        if (!name.endsWith(".ddl") && !name.endsWith(".sql")) {
            return false;
        }
        String source;
        try {
            source = read(path);
        } catch (IOException | UncheckedIOException e) {
            return true;
        }
        return SqlDialect.of(source) == null || readsAsDb2(source);
    }

    private static String read(Path path) throws IOException {
        if (!Files.isRegularFile(path)) {
            return "";
        }
        try (EncodingDetectingInputStream is =
                     new EncodingDetectingInputStream(Files.newInputStream(path))) {
            return is.readFully();
        }
    }

    private static boolean readsAsDb2(String source) {
        BufferingErrorListener errorListener = new BufferingErrorListener(Paths.get(""));
        DB2Lexer lexer = new DB2Lexer(CharStreams.fromString(source));
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);
        DB2Parser parser = new DB2Parser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        parser.compilationUnit();
        return errorListener.errors.isEmpty();
    }

    @Override
    public Path sourcePathFromSourceText(Path prefix, String sourceCode) {
        return prefix.resolve("file.ddl");
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static class BufferingErrorListener extends BaseErrorListener {
        private final Path sourcePath;

        final List<Db2ParsingException> errors = new ArrayList<>();

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                int line, int charPositionInLine, String msg, RecognitionException e) {
            errors.add(new Db2ParsingException(sourcePath,
                    String.format("Syntax error in %s at line %d:%d %s.", sourcePath, line, charPositionInLine, msg), e));
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends org.openrewrite.Parser.Builder {

        public Builder() {
            super(Db2.Ddl.class);
        }

        @Override
        public Db2Parser build() {
            return new Db2Parser();
        }

        @Override
        public String getDslName() {
            return "db2";
        }
    }
}
