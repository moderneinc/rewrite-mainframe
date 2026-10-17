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
package org.openrewrite.mainframe.db2.bind.trait;

import lombok.Value;
import org.jspecify.annotations.Nullable;
import org.openrewrite.Cursor;
import org.openrewrite.mainframe.controlcard.CardLines;
import org.openrewrite.mainframe.db2.bind.tree.Bind;
import org.openrewrite.mainframe.db2.bind.tree.Statement;
import org.openrewrite.trait.SimpleTraitMatcher;
import org.openrewrite.trait.Trait;

/**
 * A {@code RUN} subcommand of DSN, the program a TSO batch step runs under a plan:
 * {@code RUN PROGRAM(CLMD010) PLAN(CLMPLAN) LIB('CLM.PROD.LOADLIB')}.
 * <p>
 * The step's JCL names only the TSO terminal monitor, {@code IKJEFT01}, so this deck is the one
 * place the program, the plan it runs under and the library it is loaded from are written down.
 */
@Value
public class RunCommand implements Trait<Bind.Command> {

    Cursor cursor;

    /**
     * The program run, from {@code PROGRAM} or its abbreviation {@code PROG}.
     */
    public @Nullable String getProgram() {
        return operand("PROGRAM", "PROG");
    }

    /**
     * The plan the program runs under, which says which packages its SQL is bound in.
     */
    public @Nullable String getPlan() {
        return Operands.textOf(getTree(), "PLAN");
    }

    /**
     * The load library the program is read from, from {@code LIBRARY} or {@code LIB}, or null when the
     * step's {@code STEPLIB} supplies it.
     */
    public @Nullable String getLibrary() {
        return operand("LIBRARY", "LIB");
    }

    /**
     * The parameters passed to the program, from {@code PARMS}, as written.
     */
    public @Nullable String getParameters() {
        return operand("PARMS", "PARM");
    }

    /**
     * The DB2 subsystem the deck's {@code DSN} command connects to, from its {@code SYSTEM}, or null
     * for a deck that names none.
     */
    public @Nullable String getSubsystem() {
        Bind.CompilationUnit cu = cursor.firstEnclosing(Bind.CompilationUnit.class);
        if (cu == null) {
            return null;
        }
        String subsystem = null;
        for (Statement statement : cu.getStatements()) {
            if (statement == getTree()) {
                return subsystem;
            }
            if (statement instanceof Bind.Command && ((Bind.Command) statement).isVerb("DSN")) {
                subsystem = Operands.textOf((Bind.Command) statement, "SYSTEM");
            }
        }
        return subsystem;
    }

    /**
     * The one-based line of the deck the command was written on.
     */
    public int getLine() {
        return CardLines.of(cursor, Bind.CompilationUnit.class, BindCommand.words())
                .getOrDefault(getTree().getVerb().getId(), 1);
    }

    private @Nullable String operand(String... keywords) {
        for (String keyword : keywords) {
            String text = Operands.textOf(getTree(), keyword);
            if (text != null) {
                return text;
            }
        }
        return null;
    }

    public static class Matcher extends SimpleTraitMatcher<RunCommand> {

        @Override
        protected @Nullable RunCommand test(Cursor cursor) {
            Object value = cursor.getValue();
            return value instanceof Bind.Command && ((Bind.Command) value).isVerb("RUN") ? new RunCommand(cursor) : null;
        }
    }

    @Override
    public String toString() {
        return "RUN " + getProgram() + (getPlan() == null ? "" : " PLAN " + getPlan());
    }
}
