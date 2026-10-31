/*
 * Copyright 2026 the original author or authors.
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
package org.openrewrite.mainframe.cobol.trait;

import lombok.Value;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.openrewrite.Cursor;
import org.openrewrite.InMemoryExecutionContext;
import org.openrewrite.SourceFile;
import org.openrewrite.mainframe.cobol.CobolIsoVisitor;
import org.openrewrite.mainframe.cobol.CobolParser;
import org.openrewrite.mainframe.cobol.CopybookParser;
import org.openrewrite.mainframe.cobol.Corpus;
import org.openrewrite.mainframe.cobol.SourcePositions;
import org.openrewrite.mainframe.cobol.marker.CopiedWord;
import org.openrewrite.mainframe.cobol.tree.Cobol;
import org.openrewrite.mainframe.cobol.tree.CobolPreprocessor;
import org.openrewrite.mainframe.cobol.tree.Statement;
import org.openrewrite.marker.Range;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Reports, invariant by invariant, where the COBOL traits' answers break rules that every program the
 * compiler accepts obeys. Each check judges a trait's answer against something read without that
 * trait, such as the source as written or the entries a program declares.
 * <p>
 * Run with {@code COBOL_CORPUS=/path/to/corpus}.
 */
@EnabledIfEnvironmentVariable(named = "COBOL_CORPUS", matches = ".+")
class CobolCorpusTest {

    private static final int EXAMPLES = 5;
    private static final Path REPORTS = Paths.get("build", "reports", "cobol-corpus");

    private static final Set<String> NON_CHARACTER = new HashSet<>(Arrays.asList(
      "BINARY", "COMP", "COMP-1", "COMP-2", "COMP-3", "COMP-4", "COMP-5", "PACKED-DECIMAL", "POINTER",
      "POINTER-32", "PROCEDURE-POINTER", "FUNCTION-POINTER", "INDEX", "OBJECT"));
    private static final Set<String> WITHOUT_PICTURE = new HashSet<>(Arrays.asList(
      "COMP-1", "COMP-2", "POINTER", "POINTER-32", "PROCEDURE-POINTER", "FUNCTION-POINTER", "INDEX", "OBJECT"));
    private static final Set<String> NOT_DATA_NAMES = new HashSet<>(Arrays.asList(
      "LENGTH", "ADDRESS", "DFHRESP", "DFHVALUE"));

    /**
     * One rule the report counts violations of.
     */
    enum Invariant {
        ANSWERS("the traits answer without throwing"),
        RESOLVES("resolves to one definition"),
        IN_SCOPE("definition is in scope"),
        QUALIFIERS("qualifiers name items above it"),
        SUBSCRIPTS("subscripts match the OCCURS depth"),
        REFERENCE_MODIFICATION("reference modification on a character item"),
        CONDITION_NAMES("condition names only in conditions and SET"),
        PROCEDURES("PERFORM and GO TO reach one procedure"),
        OPERANDS("DL/I and CICS operands are as written"),
        COPYBOOKS("a missing copybook is missing");

        final String heading;

        Invariant(String heading) {
            this.heading = heading;
        }
    }

    /**
     * One place where a trait's answer breaks an invariant.
     */
    @Value
    static class Violation {
        Invariant invariant;
        String repository;
        String path;
        int line;
        @Nullable
        String copybook;
        String source;
        String detail;
    }

    private final Map<Invariant, long[]> checked = new EnumMap<>(Invariant.class);
    private final Map<Invariant, Map<String, Integer>> excused = new EnumMap<>(Invariant.class);
    private final List<Violation> violations = new ArrayList<>();
    private final Set<String> reported = new HashSet<>();

    private String repository = "";
    private Cobol.@Nullable CompilationUnit cu;
    private byte[] bytes = new byte[0];
    private @Nullable SourcePositions positions;
    private @Nullable List<String> lines;
    private Declarations declarations = new Declarations();
    private Map<String, List<Path>> members = new HashMap<>();
    private Path repositoryRoot = Paths.get("");

    @Test
    void reportsWhereTheTraitsMisreadAProgram() throws IOException {
        Path root = Paths.get(System.getenv("COBOL_CORPUS"));
        int repositories = 0;
        int read = 0;
        int copies = 0;
        int notParsed = 0;
        int[] missingACopybook = new int[1];
        boolean fixtureFound = false;
        Set<String> seen = new HashSet<>();

        for (Path repo : Corpus.repositories(root)) {
            List<Path> distinct = new ArrayList<>();
            Map<Path, byte[]> contents = new HashMap<>();
            for (Path program : Corpus.programs(repo)) {
                byte[] content = Files.readAllBytes(program);
                if (seen.add(digest(content))) {
                    distinct.add(program);
                    contents.put(program, content);
                } else {
                    copies++;
                }
            }
            fixtureFound |= Corpus.isFixture(repo);
            if (distinct.isEmpty()) {
                continue;
            }
            repositories++;
            repository = root.relativize(repo).toString();
            repositoryRoot = repo;
            members = members(repo);
            List<SourceFile> copybooks = CopybookParser.builder().build()
              .parseInputs(Corpus.inputs(Corpus.copybooks(repo)), root, new InMemoryExecutionContext())
              .collect(toList());
            Iterator<SourceFile> parsed = CobolParser.builder().copybooks(copybooks).build()
              .parseInputs(Corpus.inputs(distinct), root, new InMemoryExecutionContext()).iterator();
            while (parsed.hasNext()) {
                SourceFile program = parsed.next();
                if (!(program instanceof Cobol.CompilationUnit)) {
                    notParsed++;
                    continue;
                }
                read++;
                bytes = contents.get(root.resolve(program.getSourcePath()));
                check((Cobol.CompilationUnit) program, missingACopybook);
            }
        }

        String report = report(root, repositories, read, copies, notParsed, missingACopybook[0]);
        System.out.print(report);
        Files.createDirectories(REPORTS);
        Files.write(REPORTS.resolve("report.txt"), report.getBytes());
        writeCsv(REPORTS.resolve("violations.csv"));

        assertThat(read).as("programs read under %s", root).isPositive();
        assertThat(fixtureFound).as("mainframe-fixtures under %s", root).isTrue();
    }

    private void check(Cobol.CompilationUnit program, int[] missingACopybook) {
        cu = program;
        positions = null;
        lines = null;
        // One root for every pass, so that the DataName index kept on it is built once per file.
        Cursor root = new Cursor(null, Cursor.ROOT_VALUE);
        try {
            declarations = Declarations.of(program, root);
            new DataName.Matcher().<Integer>asVisitor((name, p) -> {
                if (!name.isDefinition()) {
                    checkUsage(name);
                }
                return name.getTree();
            }).visit(program, 0, root);
            checkHostVariables(program, root);
            new DliCall.Matcher().<Integer>asVisitor((call, p) -> {
                checkDliCall(call);
                return call.getTree();
            }).visit(program, 0, root);
            new CicsCommand.Matcher().<Integer>asVisitor((command, p) -> {
                checkCicsCommand(command);
                return command.getTree();
            }).visit(program, 0, root);
            checkProcedures(program, root);
            if (checkCopybooks(program, root)) {
                missingACopybook[0]++;
            }
            check(Invariant.ANSWERS);
        } catch (RuntimeException e) {
            check(Invariant.ANSWERS);
            Throwable cause = e;
            while (cause.getCause() != null) {
                cause = cause.getCause();
            }
            String at = Arrays.stream(cause.getStackTrace())
              .filter(frame -> frame.getClassName().startsWith("org.openrewrite.mainframe."))
              .limit(3).map(frame -> " at " + frame).collect(joining());
            violation(Invariant.ANSWERS, null, cause.getClass().getSimpleName() + ": " + cause.getMessage() + at);
        }
    }

    // ---------------------------------------------------------------- data names

    private void checkUsage(DataName usage) {
        check(Invariant.RESOLVES);
        DataName.UnresolvedReason reason = usage.getUnresolvedReason();
        if (reason != null) {
            unresolved(usage.getCursor(), usage.getName(), reason);
            return;
        }
        DataName definition = usage.getDefinition();
        if (definition == null) {
            violation(Invariant.RESOLVES, usage.getCursor(), usage.getName() + ": no reason, and no definition");
            return;
        }
        checkScope(usage, definition);
        checkQualifiers(usage, definition);
        checkSubscripts(usage, definition);
        checkReferenceModification(usage, definition);
        checkConditionName(usage, definition);
    }

    private void unresolved(Cursor at, String name, DataName.UnresolvedReason reason) {
        String upper = name.toUpperCase(Locale.ROOT);
        switch (reason) {
            case COPYBOOK_NOT_READ:
                excuse(Invariant.RESOLVES, "copybook not read in its own program");
                return;
            case CONTAINER_COPYBOOK_NOT_READ:
                excuse(Invariant.RESOLVES, "copybook not read in a containing program");
                return;
            case AMBIGUOUS:
                Object written = at.getParentTreeCursor().getValue();
                violation(Invariant.RESOLVES, at, name + ": more than one declaration matches, written in a " +
                                                  (written instanceof Cobol.DataRedefinesClause ? "REDEFINES clause" : context(at)));
                return;
            default:
                UUID program = programId(at);
                if (declarations.isIndexName(program, upper)) {
                    excuse(Invariant.RESOLVES, "index name");
                } else if (upper.startsWith("XML-") || upper.startsWith("JSON-") || "JNIENVPTR".equals(upper)) {
                    excuse(Invariant.RESOLVES, "special register");
                } else {
                    String declared = declarations.whereDeclared(program, upper);
                    violation(Invariant.RESOLVES, at, name + ": " +
                                                      (declared == null ? "declared nowhere in the file" : declared + ", but not resolved"));
                }
        }
    }

    private void checkHostVariables(Cobol.CompilationUnit program, Cursor root) {
        Set<UUID> blocks = new HashSet<>();
        new CopybookSkippingVisitor<Integer>() {
            @Override
            public Cobol.Word visitWord(Cobol.Word word, Integer p) {
                CobolPreprocessor.ExecStatement exec = Execs.blockOn(word);
                // The word after the colon of WHENEVER ... GO TO is a paragraph.
                if (exec != null && Execs.isSql(exec) && blocks.add(exec.getId()) &&
                    Execs.wordsOf(exec).stream().noneMatch(w -> "WHENEVER".equalsIgnoreCase(w.getWord()))) {
                    String previous = "";
                    for (Cobol.Word each : Execs.wordsOf(exec)) {
                        if (":".equals(previous)) {
                            Cursor at = new Cursor(getCursor(), each);
                            // A host variable that resolves is a DataName, and checked with the rest.
                            if (!new DataName.Matcher().get(at).isPresent()) {
                                check(Invariant.RESOLVES);
                                DataName.UnresolvedReason reason = new DataName(at).getUnresolvedReason();
                                if (reason != null) {
                                    unresolved(at, each.getWord(), reason);
                                }
                            }
                        }
                        previous = each.getWord();
                    }
                }
                return super.visitWord(word, p);
            }
        }.visit(program, 0, root);
    }

    private void checkScope(DataName usage, DataName definition) {
        check(Invariant.IN_SCOPE);
        Program using = Program.of(usage.getCursor());
        Program defining = Program.of(definition.getCursor());
        if (using == null || defining == null || using.getTree().getId().equals(defining.getTree().getId())) {
            return;
        }
        boolean encloses = false;
        for (Iterator<Cursor> path = usage.getCursor().getPathAsCursors(); path.hasNext(); ) {
            Object value = path.next().getValue();
            if (value instanceof Cobol.ProgramUnit && ((Cobol.ProgramUnit) value).getId().equals(defining.getTree().getId())) {
                encloses = true;
            }
        }
        if (encloses && definition.isGlobal()) {
            return;
        }
        violation(Invariant.IN_SCOPE, usage.getCursor(), usage.getName() + " -> " + definition + " in " + defining.getName() +
                                                         (encloses ? ", which does not declare it GLOBAL" : ", which does not contain this program"));
    }

    private void checkQualifiers(DataName usage, DataName definition) {
        Written written = Written.of(usage.getCursor());
        if (written == null || written.qualifiers.isEmpty() && written.file == null) {
            return;
        }
        check(Invariant.QUALIFIERS);
        if (!written.qualifiers.equals(usage.getQualifiers())) {
            violation(Invariant.QUALIFIERS, usage.getCursor(), usage.getName() + " is written OF " + written.qualifiers +
                                                               ", but getQualifiers() is " + usage.getQualifiers());
            return;
        }
        List<String> above = new ArrayList<>();
        for (DataName parent = definition.getParent(); parent != null; parent = parent.getParent()) {
            above.add(parent.getName().toUpperCase(Locale.ROOT));
        }
        int at = 0;
        for (String qualifier : written.qualifiers) {
            int found = above.subList(at, above.size()).indexOf(qualifier);
            if (found < 0) {
                violation(Invariant.QUALIFIERS, usage.getCursor(), usage.getName() + " OF " + written.qualifiers + " -> " +
                                                                   definition + ", under " + above);
                return;
            }
            at += found + 1;
        }
        String file = declarations.files.get(definition.getTree().getId());
        if (written.file != null && !written.file.equals(file)) {
            violation(Invariant.QUALIFIERS, usage.getCursor(), usage.getName() + " IN " + written.file + " -> " +
                                                               definition + ", a record of " + file);
        }
    }

    private void checkSubscripts(DataName usage, DataName definition) {
        Written written = Written.of(usage.getCursor());
        if (written == null || written.subscripts < 0) {
            return;
        }
        check(Invariant.SUBSCRIPTS);
        int depth = declarations.occursDepth.getOrDefault(definition.getTree().getId(), 0);
        if (written.subscripts == depth || written.subscripts == 0 && namesAWholeTable(usage.getCursor())) {
            return;
        }
        violation(Invariant.SUBSCRIPTS, usage.getCursor(), usage.getName() + ": " + written.subscripts +
                                                           " subscripts, but " + definition + " is under " + depth + " OCCURS");
    }

    private static boolean namesAWholeTable(Cursor at) {
        Statement statement = at.firstEnclosing(Statement.class);
        return statement instanceof Cobol.Search || statement instanceof Cobol.Sort;
    }

    private void checkReferenceModification(DataName usage, DataName definition) {
        if (!usage.isReferenceModified()) {
            return;
        }
        check(Invariant.REFERENCE_MODIFICATION);
        UUID id = definition.getTree().getId();
        String type = declarations.usage.getOrDefault(id, "");
        if (definition.getLevel() == 88 || declarations.elementary.contains(id) && NON_CHARACTER.contains(type)) {
            violation(Invariant.REFERENCE_MODIFICATION, usage.getCursor(), usage.getName() + " -> " + definition +
                                                                           (type.isEmpty() ? "" : ", USAGE " + type));
        }
    }

    private void checkConditionName(DataName usage, DataName definition) {
        Cobol.SetTo set = usage.isSet() ? usage.getCursor().firstEnclosing(Cobol.SetTo.class) : null;
        boolean toTruth = set != null && set.getValues().stream()
          .anyMatch(v -> "TRUE".equals(Names.upperOf(v)) || "FALSE".equals(Names.upperOf(v)));
        if (definition.getLevel() != 88 && !toTruth) {
            return;
        }
        check(Invariant.CONDITION_NAMES);
        if (definition.getLevel() != 88) {
            violation(Invariant.CONDITION_NAMES, usage.getCursor(), "SET " + usage.getName() + " TO TRUE -> " + definition);
        } else if (set == null && !inCondition(usage.getCursor())) {
            violation(Invariant.CONDITION_NAMES, usage.getCursor(), usage.getName() + " -> " + definition + ", written in a " +
                                                                    context(usage.getCursor()));
        }
    }

    private static String context(Cursor at) {
        Statement statement = at.firstEnclosing(Statement.class);
        return statement == null ? "data description" : statement.getClass().getSimpleName();
    }

    private static boolean inCondition(Cursor at) {
        for (Iterator<Cursor> path = at.getPathAsCursors(); path.hasNext(); ) {
            Object value = path.next().getValue();
            if (value instanceof Cobol.ConditionNameReference || value instanceof Cobol.Condition ||
                value instanceof Cobol.EvaluateWhen) {
                return true;
            }
            if (value instanceof Statement) {
                return value instanceof Cobol.Evaluate;
            }
        }
        return false;
    }

    // ---------------------------------------------------------------- procedures

    private void checkProcedures(Cobol.CompilationUnit program, Cursor root) {
        Map<UUID, List<Procedure>> byProgram = new HashMap<>();
        new Procedure.Matcher().<Integer>asVisitor((procedure, p) -> {
            byProgram.computeIfAbsent(programId(procedure.getCursor()), k -> new ArrayList<>()).add(procedure);
            return procedure.getTree();
        }).visit(program, 0, root);
        Map<UUID, Boolean> readsEveryCopybook = new HashMap<>();
        new CopybookSkippingVisitor<Integer>() {
            @Override
            public Cobol.ProcedureName visitProcedureName(Cobol.ProcedureName name, Integer p) {
                // USE FOR DEBUGGING names a data item or a file as readily as a procedure.
                if (getCursor().firstEnclosing(Cobol.UseStatement.class) == null) {
                    reach(getCursor(), name, byProgram, readsEveryCopybook);
                }
                return super.visitProcedureName(name, p);
            }
        }.visit(program, 0, root);
    }

    private void reach(Cursor at, Cobol.ProcedureName name, Map<UUID, List<Procedure>> byProgram,
                       Map<UUID, Boolean> readsEveryCopybook) {
        check(Invariant.PROCEDURES);
        String paragraph = Names.upperOf(name.getParagraphName());
        String section = name.getInSection() == null ? null : Names.upperOf(name.getInSection().getName());
        List<Procedure> matching = new ArrayList<>();
        for (Procedure procedure : byProgram.getOrDefault(programId(at), emptyList())) {
            if (procedure.getName().equals(paragraph) &&
                (section == null || !procedure.isSection() && section.equals(sectionOf(procedure.getCursor())))) {
                matching.add(procedure);
            }
        }
        if (section == null && matching.size() > 1) {
            String here = sectionOf(at);
            List<Procedure> local = matching.stream()
              .filter(p -> !p.isSection() && Objects.equals(here, sectionOf(p.getCursor())))
              .collect(toList());
            if (local.size() == 1) {
                return;
            }
        }
        if (matching.size() == 1) {
            return;
        }
        Program program = Program.of(at);
        if (matching.isEmpty() && program != null && !readsEveryCopybook.computeIfAbsent(program.getTree().getId(),
          id -> program.getCopybookReferences().stream().allMatch(CopybookReference::isResolved))) {
            excuse(Invariant.PROCEDURES, "copybook not read");
            return;
        }
        String elsewhere = byProgram.entrySet().stream()
          .filter(e -> !Objects.equals(e.getKey(), programId(at)))
          .filter(e -> e.getValue().stream().anyMatch(p -> p.getName().equals(paragraph)))
          .map(e -> declarations.programNames.get(e.getKey()))
          .findFirst().map(other -> ", but " + other + " has one").orElse("");
        violation(Invariant.PROCEDURES, at, paragraph + (section == null ? "" : " OF " + section) + ": " +
                                            (matching.isEmpty() ? "no paragraph or section of " : matching.size() + " procedures of ") +
                                            Program.nameOf(at) + elsewhere);
    }

    private static @Nullable String sectionOf(Cursor at) {
        Cobol.ProcedureSection section = at.firstEnclosing(Cobol.ProcedureSection.class);
        if (section != null) {
            return Names.upperOf(section.getProcedureSectionHeader().getSectionName());
        }
        Cobol.ProcedureDeclarative declarative = at.firstEnclosing(Cobol.ProcedureDeclarative.class);
        return declarative == null ? null : Names.upperOf(declarative.getProcedureSectionHeader().getSectionName());
    }

    // ---------------------------------------------------------------- DL/I and CICS

    private void checkDliCall(DliCall call) {
        check(Invariant.OPERANDS);
        boolean callLevel = call.getTree() instanceof Cobol.Call;
        List<@Nullable String> texts = new ArrayList<>();
        List<@Nullable DataName> names = new ArrayList<>();
        texts.add(call.getFunctionOperand());
        names.add(call.getFunctionDataName());
        texts.add(call.getPcb());
        names.add(call.getPcbDataName());
        texts.add(call.getIoArea());
        names.add(call.getIoAreaDataName());
        texts.addAll(call.getSsas());
        names.addAll(call.getSsaDataNames());
        if (texts.size() != names.size()) {
            violation(Invariant.OPERANDS, call.getCursor(), call.getSsas().size() + " SSAs, but " +
                                                            call.getSsaDataNames().size() + " SSA occurrences");
            return;
        }
        UUID program = programId(call.getCursor());
        for (int i = 0; i < texts.size(); i++) {
            String text = texts.get(i);
            DataName name = names.get(i);
            if (text == null || isLiteral(text)) {
                continue;
            }
            if (name == null) {
                // EXEC DLI gives an occurrence for its INTO or FROM operand only, the I/O area.
                if (callLevel || i == 2 && declarations.isVisible(program, text.toUpperCase(Locale.ROOT))) {
                    violation(Invariant.OPERANDS, call.getCursor(), call.getIface() + " operand " + text + " has no occurrence");
                }
            } else if (!name.getName().equalsIgnoreCase(text)) {
                violation(Invariant.OPERANDS, name.getCursor(), call.getIface() + " operand " + text + " is read as " + name.getName());
            }
        }
        if (callLevel) {
            checkOperandOrder(call, names);
        }
        checkPcbEntry(call);
    }

    private void checkOperandOrder(DliCall call, List<@Nullable DataName> names) {
        List<UUID> words = new ArrayList<>();
        new CobolIsoVisitor<Integer>() {
            @Override
            public Cobol.Word visitWord(Cobol.Word word, Integer p) {
                words.add(word.getId());
                return word;
            }
        }.visit(call.getTree(), 0);
        int last = -1;
        for (DataName name : names) {
            if (name == null) {
                continue;
            }
            int at = words.indexOf(name.getTree().getId());
            if (at <= last) {
                violation(Invariant.OPERANDS, name.getCursor(), "CALL operand " + name.getName() + " is out of order");
                return;
            }
            last = at;
        }
    }

    private void checkPcbEntry(DliCall call) {
        DataName pcb = call.getPcbDataName();
        DataName definition = pcb == null ? null : pcb.getDefinition();
        if (definition == null || definition.getLevel() != 1 ||
            definition.getCursor().firstEnclosing(Cobol.LinkageSection.class) == null) {
            return;
        }
        Program declaring = Program.of(definition.getCursor());
        if (declaring == null) {
            return;
        }
        List<String> using = declarations.using.getOrDefault(declaring.getTree().getId(), emptyList());
        int expected = using.indexOf(definition.getName().toUpperCase(Locale.ROOT));
        if (expected < 0) {
            return;
        }
        ProgramEntry entry = call.getEntry();
        Program entering = entry == null ? null : Program.of(entry.getCursor());
        if (entering == null || !entering.getTree().getId().equals(declaring.getTree().getId())) {
            violation(Invariant.OPERANDS, pcb.getCursor(), "PCB " + pcb.getName() + " is declared and handed over in " +
                                                           declaring.getName() + ", but the entry is " + (entering == null ? "none" : "in " + entering.getName()));
        } else if (!declarations.containers.containsKey(declaring.getTree().getId()) && call.getPcbPosition() != expected) {
            violation(Invariant.OPERANDS, pcb.getCursor(), "PCB " + pcb.getName() + " is parameter " + expected +
                                                           " of PROCEDURE DIVISION USING, but its position is " + call.getPcbPosition());
        }
    }

    private void checkCicsCommand(CicsCommand command) {
        if (command.isVerb("HANDLE") || command.isVerb("IGNORE")) {
            return;
        }
        List<Execs.Option> options = Execs.optionsOf(Execs.wordsOf(command.getExec()));
        Map<String, Integer> written = new HashMap<>();
        options.forEach(o -> written.merge(o.getName(), 1, Integer::sum));
        UUID program = programId(command.getCursor());
        for (Execs.Option option : options) {
            List<Cobol.Word> operand = option.getOperand();
            if (operand.isEmpty() || written.get(option.getName()) > 1) {
                continue;
            }
            check(Invariant.OPERANDS);
            Cobol.Word first = operand.get(0);
            Cursor at = new Cursor(command.getCursor(), first);
            String text = option.getName() + "(" + operand.stream().map(Cobol.Word::getWord).reduce((a, b) -> a + " " + b).orElse("") + ")";
            DataName name = command.getOptionDataName(option.getName());
            String upper = first.getWord().toUpperCase(Locale.ROOT);
            if (name == null) {
                if (!isLiteral(upper) && !NOT_DATA_NAMES.contains(upper) && declarations.isVisible(program, upper)) {
                    violation(Invariant.OPERANDS, at, text + ": " + first.getWord() + " is declared, but getOptionDataName is null");
                }
            } else if (!name.getTree().getId().equals(first.getId())) {
                violation(Invariant.OPERANDS, at, text + ": getOptionDataName is " + name.getName());
            }
        }
    }

    private static boolean isLiteral(String text) {
        char first = text.isEmpty() ? ' ' : text.charAt(0);
        return first == '\'' || first == '"' || Character.isDigit(first) || first == '+' || first == '-' ||
               text.indexOf(' ') >= 0;
    }

    // ---------------------------------------------------------------- copybooks

    /**
     * Whether a copybook the file copies was not read.
     */
    private boolean checkCopybooks(Cobol.CompilationUnit program, Cursor root) {
        boolean[] unread = new boolean[1];
        new CopybookReference.Matcher().<Integer>asVisitor((reference, p) -> {
            check(Invariant.COPYBOOKS);
            String name = reference.getName().replaceAll("^['\"]|['\"]$", "").toUpperCase(Locale.ROOT);
            if (!reference.isResolved()) {
                unread[0] = true;
            }
            if (reference.isMissing()) {
                List<Path> held = members.getOrDefault(name, emptyList());
                if (!held.isEmpty()) {
                    violation(Invariant.COPYBOOKS, reference.getCursor(), "COPY " + name + " is missing, but the repository holds " +
                                                                          held.stream().map(m -> repositoryRoot.relativize(m).toString()).collect(joining(", ")));
                }
            } else if (!reference.isResolved()) {
                excuse(Invariant.COPYBOOKS, "not searched for");
            } else if (reference.getSourcePath() != null && !name.equals(memberName(reference.getSourcePath()))) {
                violation(Invariant.COPYBOOKS, reference.getCursor(), "COPY " + name + " was read from " + reference.getSourcePath());
            }
            return reference.getTree();
        }).visit(program, 0, root);
        return unread[0];
    }

    private static Map<String, List<Path>> members(Path repository) throws IOException {
        Map<String, List<Path>> members = new HashMap<>();
        try (Stream<Path> paths = Files.walk(repository)) {
            paths.filter(Files::isRegularFile)
              .filter(p -> Corpus.isSource(repository.relativize(p)))
              .sorted()
              .forEach(p -> members.computeIfAbsent(memberName(p), k -> new ArrayList<>()).add(p));
        }
        return members;
    }

    private static String memberName(Path path) {
        String file = path.getFileName().toString();
        int dot = file.indexOf('.');
        return (dot < 0 ? file : file.substring(0, dot)).toUpperCase(Locale.ROOT);
    }

    // ---------------------------------------------------------------- recording

    private @Nullable UUID programId(Cursor at) {
        Program program = Program.of(at);
        return program == null ? null : program.getTree().getId();
    }

    private void check(Invariant invariant) {
        checked.computeIfAbsent(invariant, k -> new long[1])[0]++;
    }

    private void excuse(Invariant invariant, String reason) {
        excused.computeIfAbsent(invariant, k -> new TreeMap<>()).merge(reason, 1, Integer::sum);
    }

    /**
     * Records a violation once. One in a copybook's text is recorded against the copybook, so that it
     * counts once however many programs copy it.
     */
    private void violation(Invariant invariant, @Nullable Cursor at, String detail) {
        Cobol.CompilationUnit program = Objects.requireNonNull(cu);
        String copybook = null;
        int line = 0;
        for (Iterator<Cursor> path = at == null ? Collections.emptyIterator() : at.getPathAsCursors(); path.hasNext(); ) {
            Object value = path.next().getValue();
            if (value instanceof CobolPreprocessor && positions().get((CobolPreprocessor) value) != null) {
                line = Objects.requireNonNull(positions().get((CobolPreprocessor) value)).getStart().getLine();
                break;
            }
            if (!(value instanceof Cobol) || value instanceof Cobol.CompilationUnit) {
                continue;
            }
            Optional<CopiedWord> copied = ((Cobol) value).getMarkers().findFirst(CopiedWord.class);
            if (copied.isPresent()) {
                copybook = copied.get().getCopybook();
                break;
            }
            Range range = positions().get((Cobol) value);
            if (range != null) {
                line = range.getStart().getLine();
                break;
            }
        }
        String path = Paths.get(repository).relativize(program.getSourcePath()).toString();
        String where = copybook != null ? "COPY " + copybook : path + ":" + line;
        if (!reported.add(invariant + "|" + repository + "|" + where + "|" + detail)) {
            return;
        }
        String source = copybook == null && line > 0 && line <= sourceLines().size() ? code(sourceLines().get(line - 1)) : "";
        violations.add(new Violation(invariant, repository, path, line, copybook, source, detail));
    }

    /**
     * A fixed-format line's program text, without the sequence number and identification areas.
     */
    private static String code(String line) {
        String text = line.length() > 72 ? line.substring(0, 72) : line;
        return (text.length() > 7 && text.substring(0, 6).matches("[0-9 ]{6}") ? text.substring(7) : text).trim();
    }

    private SourcePositions positions() {
        if (positions == null) {
            positions = SourcePositions.of(Objects.requireNonNull(cu));
        }
        return positions;
    }

    private List<String> sourceLines() {
        if (lines == null) {
            lines = Arrays.asList(new String(bytes, Objects.requireNonNull(cu).getCharset()).split("\r?\n", -1));
        }
        return lines;
    }

    private String report(Path root, int repositories, int read, int copies, int notParsed, int missingACopybook) {
        StringBuilder report = new StringBuilder(String.format(Locale.ROOT,
          "COBOL trait invariants under %s: %d repositories, %,d distinct programs read (%,d identical copies skipped), " +
          "%,d not parsed, %,d missing a copybook%n%n", root, repositories, read, copies, notParsed, missingACopybook));
        report.append(String.format(Locale.ROOT, "%-46s %12s %11s %10s%n", "", "checked", "violations", "excused"));
        Map<Invariant, List<Violation>> byInvariant = new EnumMap<>(Invariant.class);
        violations.forEach(v -> byInvariant.computeIfAbsent(v.getInvariant(), k -> new ArrayList<>()).add(v));
        for (Invariant invariant : Invariant.values()) {
            Map<String, Integer> reasons = excused.getOrDefault(invariant, emptyMap());
            report.append(String.format(Locale.ROOT, "%-46s %,12d %,11d %,10d  %s%n", invariant.heading,
              checked.getOrDefault(invariant, new long[1])[0], byInvariant.getOrDefault(invariant, emptyList()).size(),
              reasons.values().stream().mapToInt(Integer::intValue).sum(),
              reasons.entrySet().stream().map(e -> e.getKey() + " " + String.format(Locale.ROOT, "%,d", e.getValue()))
                .reduce((a, b) -> a + ", " + b).orElse("")));
        }
        for (Map.Entry<Invariant, List<Violation>> entry : byInvariant.entrySet()) {
            List<Violation> found = entry.getValue();
            Map<String, Violation> examples = new LinkedHashMap<>();
            found.forEach(v -> examples.putIfAbsent(v.getRepository(), v));
            report.append(String.format(Locale.ROOT, "%n%s: %,d in %d repositories%n", entry.getKey().heading,
              found.size(), examples.size()));
            examples.values().stream().limit(EXAMPLES).forEach(v -> report.append(String.format(Locale.ROOT,
              "  %s  %s  %s%n      %s%n", v.getRepository(),
              v.getCopybook() == null ? v.getPath() + ":" + v.getLine() : v.getPath() + " (COPY " + v.getCopybook() + ")",
              v.getSource(), v.getDetail())));
        }
        return report.toString();
    }

    private static Map<String, Integer> emptyMap() {
        return Collections.emptyMap();
    }

    private void writeCsv(Path csv) throws IOException {
        List<String> rows = new ArrayList<>();
        rows.add("invariant,repository,path,line,copybook,source,detail");
        for (Violation v : violations) {
            rows.add(String.join(",", v.getInvariant().name(), quote(v.getRepository()), quote(v.getPath()),
              String.valueOf(v.getLine()), quote(v.getCopybook() == null ? "" : v.getCopybook()), quote(v.getSource()),
              quote(v.getDetail())));
        }
        Files.write(csv, rows);
    }

    private static String quote(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    private static String digest(byte[] content) {
        try {
            return Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-256").digest(content));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * The qualifiers and subscripts written with a data-name usage, read off the tree or, inside an
     * {@code EXEC} block, off the option's words.
     */
    private static final class Written {
        final List<String> qualifiers;
        final @Nullable String file;

        /**
         * -1 where the subscript count says nothing, as for a qualifier, in the DATA DIVISION, in USE, under
         * {@code LENGTH OF} or {@code ADDRESS OF}, and in SQL.
         */
        final int subscripts;

        private Written(List<String> qualifiers, @Nullable String file, int subscripts) {
            this.qualifiers = qualifiers;
            this.file = file;
            this.subscripts = subscripts;
        }

        static @Nullable Written of(Cursor word) {
            Cursor parent = word.getParentTreeCursor();
            Object value = parent.getValue();
            if (value instanceof Cobol.Word) {
                return inExec((Cobol.Word) value, word.getValue());
            }
            boolean counts = word.firstEnclosing(Cobol.DataDivision.class) == null &&
                             word.firstEnclosing(Cobol.SpecialRegister.class) == null &&
                             word.firstEnclosing(Cobol.UseStatement.class) == null;
            if (value instanceof Cobol.ConditionNameReference) {
                Cobol.ConditionNameReference reference = (Cobol.ConditionNameReference) value;
                List<String> qualifiers = new ArrayList<>();
                if (reference.getInDatas() != null) {
                    reference.getInDatas().forEach(in -> qualifiers.add(Names.upperOf(in.getName())));
                }
                return new Written(qualifiers, reference.getInFile() == null ? null : Names.upperOf(reference.getInFile().getName()),
                  counts ? count(reference.getReferences()) : -1);
            }
            if (!(value instanceof Cobol.QualifiedDataNameFormat1)) {
                return null;
            }
            Cobol.QualifiedDataNameFormat1 format1 = (Cobol.QualifiedDataNameFormat1) value;
            List<String> qualifiers = new ArrayList<>();
            if (format1.getQualifiedInData() != null) {
                for (Cobol in : format1.getQualifiedInData()) {
                    if (in instanceof Cobol.QualifiedDataName) {
                        in = ((Cobol.QualifiedDataName) in).getDataName();
                    }
                    if (in instanceof Cobol.InData) {
                        qualifiers.add(Names.upperOf(((Cobol.InData) in).getName()));
                    } else if (in instanceof Cobol.InTable) {
                        qualifiers.add(Names.upperOf(((Cobol.InTable) in).getTableCall().getQualifiedDataName()));
                    }
                }
            }
            int subscripts = 0;
            Cursor qualified = parent.getParentTreeCursor();
            if (qualified.getValue() instanceof Cobol.QualifiedDataName) {
                Object above = qualified.getParentTreeCursor().getValue();
                if (above instanceof Cobol.TableCall) {
                    subscripts = count(((Cobol.TableCall) above).getSubscripts());
                }
            }
            return new Written(qualifiers, format1.getInFile() == null ? null : Names.upperOf(format1.getInFile().getName()),
              counts ? subscripts : -1);
        }

        private static int count(@Nullable List<Cobol.Parenthesized> groups) {
            int count = 0;
            if (groups != null) {
                for (Cobol.Parenthesized group : groups) {
                    for (Cobol each : group.getContents()) {
                        if (each instanceof Cobol.Subscript) {
                            count++;
                        }
                    }
                }
            }
            return count;
        }

        /**
         * Reads {@code NAME OF GROUP (I, J)} after the word in a CICS or DL/I option's operand.
         */
        private static @Nullable Written inExec(Cobol.Word standIn, Cobol.Word word) {
            CobolPreprocessor.ExecStatement exec = Execs.blockOn(standIn);
            Execs.Option option = exec == null ? null : Execs.optionHolding(exec, word);
            if (option == null) {
                return null;
            }
            List<Cobol.Word> operand = option.getOperand();
            int i = 0;
            while (i < operand.size() && !operand.get(i).getId().equals(word.getId())) {
                i++;
            }
            List<String> qualifiers = new ArrayList<>();
            int subscripts = 0;
            for (i++; i < operand.size(); ) {
                String text = operand.get(i).getWord();
                if (("OF".equalsIgnoreCase(text) || "IN".equalsIgnoreCase(text)) && i + 1 < operand.size()) {
                    qualifiers.add(operand.get(i + 1).getWord().toUpperCase(Locale.ROOT));
                    i += 2;
                } else if ("(".equals(text)) {
                    int depth = 0;
                    int items = 0;
                    boolean modified = false;
                    String previous = "(";
                    for (; i < operand.size(); i++) {
                        String each = operand.get(i).getWord();
                        if ("(".equals(each)) {
                            depth++;
                        } else if (")".equals(each)) {
                            if (--depth == 0) {
                                i++;
                                break;
                            }
                        } else if (depth == 1) {
                            if (":".equals(each)) {
                                modified = true;
                            } else if (!",".equals(each) && !"+".equals(each) && !"-".equals(each) &&
                                       !"+".equals(previous) && !"-".equals(previous)) {
                                items++;
                            }
                        }
                        previous = each;
                    }
                    subscripts += modified ? 0 : items;
                } else {
                    break;
                }
            }
            return new Written(qualifiers, null, subscripts);
        }
    }

    /**
     * What each program in a file declares, read off its data description entries without the DataName
     * trait.
     */
    private static final class Declarations {
        final Map<@Nullable UUID, String> programNames = new HashMap<>();
        final Map<UUID, UUID> containers = new HashMap<>();
        final Map<UUID, List<String>> using = new HashMap<>();
        final Map<UUID, Set<String>> names = new HashMap<>();
        final Map<UUID, Set<String>> globals = new HashMap<>();
        final Map<UUID, Set<String>> indexNames = new HashMap<>();

        /**
         * Keyed by the word a definition is named by.
         */
        final Map<UUID, Integer> occursDepth = new HashMap<>();
        final Map<UUID, String> usage = new HashMap<>();
        final Set<UUID> elementary = new HashSet<>();
        final Map<UUID, String> files = new HashMap<>();

        static Declarations of(Cobol.CompilationUnit cu, Cursor root) {
            Declarations declarations = new Declarations();
            Deque<Deque<Group>> open = new ArrayDeque<>();
            new CopybookSkippingVisitor<Deque<UUID>>() {
                @Override
                public Cobol.ProgramUnit visitProgramUnit(Cobol.ProgramUnit unit, Deque<UUID> programs) {
                    UUID id = unit.getId();
                    if (!programs.isEmpty()) {
                        declarations.containers.put(id, programs.peek());
                    }
                    declarations.programNames.put(id, String.valueOf(Names.upperOf(
                      unit.getIdentificationDivision().getProgramIdParagraph().getProgramName())));
                    declarations.using.put(id, usingOf(unit));
                    programs.push(id);
                    open.push(new ArrayDeque<>());
                    Cobol.ProgramUnit visited = super.visitProgramUnit(unit, programs);
                    open.pop();
                    programs.pop();
                    return visited;
                }

                @Override
                public Cobol.DataDescriptionEntry visitDataDescriptionEntry(Cobol.DataDescriptionEntry entry, Deque<UUID> programs) {
                    if (!programs.isEmpty()) {
                        declarations.define(getCursor(), programs.peek(), open.peek());
                    }
                    return super.visitDataDescriptionEntry(entry, programs);
                }
            }.visit(cu, new ArrayDeque<>(), root);
            return declarations;
        }

        /**
         * An entry that later entries of a higher level are under.
         */
        private static final class Group {
            final int level;
            final int depth;
            final String usage;
            final boolean global;

            Group(int level, int depth, String usage, boolean global) {
                this.level = level;
                this.depth = depth;
                this.usage = usage;
                this.global = global;
            }
        }

        private void define(Cursor at, UUID program, Deque<Group> open) {
            Cobol.DataDescriptionEntry entry = at.getValue();
            int level = levelOf(entry);
            Group parent;
            if (level == 88) {
                parent = open.peek();
            } else if (level == 66) {
                parent = null;
            } else {
                if (level == 1 || level == 77) {
                    open.clear();
                }
                while (!open.isEmpty() && open.peek().level >= level) {
                    open.pop();
                }
                parent = open.peek();
            }
            List<Cobol> clauses = entry.getClauses() == null ? emptyList() : entry.getClauses();
            boolean occurs = false;
            String own = "";
            boolean picture = false;
            boolean declaredGlobal = false;
            for (Cobol clause : clauses) {
                if (clause instanceof Cobol.DataOccursClause) {
                    occurs = true;
                    List<Cobol> indexed = ((Cobol.DataOccursClause) clause).getSortIndexed();
                    if (indexed != null) {
                        for (Cobol each : indexed) {
                            if (each instanceof Cobol.DataOccursIndexed) {
                                ((Cobol.DataOccursIndexed) each).getIndexNames().forEach(index ->
                                  indexNames.computeIfAbsent(program, k -> new HashSet<>()).add(index.getWord().toUpperCase(Locale.ROOT)));
                            }
                        }
                    }
                } else if (clause instanceof Cobol.DataUsageClause) {
                    own = usageOf((Cobol.DataUsageClause) clause);
                } else if (clause instanceof Cobol.DataPictureClause) {
                    picture = true;
                } else if (clause instanceof Cobol.DataGlobalClause) {
                    declaredGlobal = true;
                }
            }
            Cobol.FileDescriptionEntry file = at.firstEnclosing(Cobol.FileDescriptionEntry.class);
            if (level == 1 && file != null && file.getClauses() != null) {
                declaredGlobal |= file.getClauses().stream().anyMatch(c -> c instanceof Cobol.GlobalClause);
            }
            String effective = own.isEmpty() && parent != null ? parent.usage : own;
            Group group = new Group(level, (parent == null ? 0 : parent.depth) + (occurs && level != 88 ? 1 : 0),
              effective, parent == null ? declaredGlobal : parent.global || declaredGlobal);
            if (level != 88 && level != 66) {
                open.push(group);
            }
            Cobol.Word name = entry.getName();
            if (name == null || "FILLER".equalsIgnoreCase(name.getWord())) {
                return;
            }
            String upper = name.getWord().toUpperCase(Locale.ROOT);
            names.computeIfAbsent(program, k -> new HashSet<>()).add(upper);
            if (group.global) {
                globals.computeIfAbsent(program, k -> new HashSet<>()).add(upper);
            }
            occursDepth.put(name.getId(), group.depth);
            usage.put(name.getId(), effective);
            if (picture || WITHOUT_PICTURE.contains(effective)) {
                elementary.add(name.getId());
            }
            if (file != null) {
                files.put(name.getId(), Names.upperOf(file.getName()));
            }
        }

        private static int levelOf(Cobol.DataDescriptionEntry entry) {
            try {
                return entry.getWords().isEmpty() ? 0 : Integer.parseInt(entry.getWords().get(0).getWord());
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        private static String usageOf(Cobol.DataUsageClause clause) {
            for (Cobol.Word word : clause.getWords()) {
                String text = word.getWord().toUpperCase(Locale.ROOT);
                if (!"USAGE".equals(text) && !"IS".equals(text)) {
                    return text.startsWith("COMPUTATIONAL") ? "COMP" + text.substring("COMPUTATIONAL".length()) : text;
                }
            }
            return "";
        }

        private static List<String> usingOf(Cobol.ProgramUnit unit) {
            List<String> names = new ArrayList<>();
            Cobol.ProcedureDivision division = unit.getProcedureDivision();
            Cobol.ProcedureDivisionUsingClause clause = division == null ? null : division.getProcedureDivisionUsingClause();
            if (clause != null) {
                for (Cobol phrase : clause.getProcedureDivisionUsingParameter()) {
                    if (phrase instanceof Cobol.ProcedureDivisionByReferencePhrase) {
                        ((Cobol.ProcedureDivisionByReferencePhrase) phrase).getProcedureDivisionByReference()
                          .forEach(parameter -> names.add(Names.upperOf(parameter.getReference())));
                    } else if (phrase instanceof Cobol.ProcedureDivisionByValuePhrase) {
                        ((Cobol.ProcedureDivisionByValuePhrase) phrase).getPhrases()
                          .forEach(parameter -> names.add(Names.upperOf(parameter)));
                    }
                }
            }
            return names;
        }

        boolean isIndexName(@Nullable UUID program, String name) {
            for (UUID each = program; each != null; each = containers.get(each)) {
                if (indexNames.getOrDefault(each, emptySet()).contains(name)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Whether the program or a GLOBAL item of a program containing it declares the name.
         */
        boolean isVisible(@Nullable UUID program, String name) {
            if (program == null) {
                return false;
            }
            if (names.getOrDefault(program, emptySet()).contains(name)) {
                return true;
            }
            for (UUID outer = containers.get(program); outer != null; outer = containers.get(outer)) {
                if (globals.getOrDefault(outer, emptySet()).contains(name)) {
                    return true;
                }
            }
            return false;
        }

        @Nullable
        String whereDeclared(@Nullable UUID program, String name) {
            if (program == null) {
                return null;
            }
            if (names.getOrDefault(program, emptySet()).contains(name)) {
                return "declared in this program";
            }
            for (UUID outer = containers.get(program); outer != null; outer = containers.get(outer)) {
                if (names.getOrDefault(outer, emptySet()).contains(name)) {
                    return (globals.getOrDefault(outer, emptySet()).contains(name) ? "declared GLOBAL in " : "declared, not GLOBAL, in ") +
                           programNames.get(outer);
                }
            }
            for (Map.Entry<UUID, Set<String>> other : names.entrySet()) {
                if (other.getValue().contains(name)) {
                    return "declared only in " + programNames.get(other.getKey()) + ", which this program cannot see";
                }
            }
            return null;
        }
    }
}
