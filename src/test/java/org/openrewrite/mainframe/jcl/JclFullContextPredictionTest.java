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
package org.openrewrite.mainframe.jcl;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;
import org.openrewrite.mainframe.FullContextPredictions;
import org.openrewrite.mainframe.jcl.internal.grammar.JCLLexer;
import org.openrewrite.mainframe.jcl.internal.grammar.JCLParser;

import static org.assertj.core.api.Assertions.assertThat;

class JclFullContextPredictionTest {

    @Test
    void aWordTakesTheSequenceNumbersThatFollowIt() {
        String jcl = """
          //JOB1     JOB (ACCT),'NAME',CLASS=A,                                   00010000
          //             MSGLEVEL=(1,1)                                           00020000
          /*JOBPARM  SYSAFF=*                                                     00030000
          //*MAIN CLASS=A                                                         00040000
          //* A COMMENT LINE                                                      00050000
          //STEP1    EXEC PGM=IEFBR14                                             00060000
          //DD1      DD DSN=A.B.C,DISP=SHR                                        00070000
          //SYSIN    DD *  ** THE CARDS                                           00080000
            SORT FIELDS=COPY                                                      00090000
          /*                                                                      00100000
          %%SET %%A=1                                                             00110000
            OPTION COPY                                                           00115000
                                                                                  00120000
          //STEP2    EXEC PGM=IEFBR14                                             00130000
          """;
        JCLParser parser = new JCLParser(new CommonTokenStream(new JCLLexer(CharStreams.fromString(JclLineReader.readLines(jcl)))));
        assertThat(FullContextPredictions.fallbacks(parser, parser::compilationUnit, "jclWord", "jclTrailingComment",
          "jes2Word", "jes3Word", "streamWord", "controlMWord", "commentWord", "unknownWord")).isZero();
    }
}
