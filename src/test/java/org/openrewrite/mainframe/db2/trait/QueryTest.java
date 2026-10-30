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
package org.openrewrite.mainframe.db2.trait;

import org.junit.jupiter.api.Test;
import org.openrewrite.DocumentExample;
import org.openrewrite.mainframe.db2.tree.Db2;
import org.openrewrite.test.RewriteTest;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openrewrite.mainframe.db2.Assertions.db2;

class QueryTest implements RewriteTest {

    @DocumentExample
    @Test
    void aViewSaysWhatItIsBuiltFrom() {
        rewriteRun(
          db2(
            """
              CREATE VIEW CLM.OPEN_CLAIMS (CLAIM_NO, POLICY_NO) AS
                SELECT C.CLAIM_NO, P.POLICY_NO
                FROM CLM.CLAIM C INNER JOIN CLM.POLICY AS P ON C.POLICY_NO = P.POLICY_NO;
              """,
            spec -> spec.afterRecipe(cu -> {
                View view = new View.Matcher().lower(cu).findFirst().orElseThrow();
                assertThat(view.getSchema()).isEqualTo("CLM");
                assertThat(view.getName()).isEqualTo("OPEN_CLAIMS");
                assertThat(view.getColumns()).containsExactly("CLAIM_NO", "POLICY_NO");
                assertThat(view.getQuery().getTables()).extracting(Db2.Name::getFullName)
                  .containsExactly("CLM.CLAIM", "CLM.POLICY");
            })
          )
        );
    }

    @Test
    void eachQueryOfAUnionHasItsOwnFromList() {
        rewriteRun(
          db2(
            """
              CREATE VIEW CLM.ALL_CLAIMS AS
                SELECT C.CLAIM_NO FROM CLM.CLAIM C, CLM.POLICY P WHERE C.POLICY_NO = P.POLICY_NO
                UNION ALL
                SELECT H.CLAIM_NO FROM CLM.CLAIM_HIST H;
              """,
            spec -> spec.afterRecipe(cu -> assertThat(tables(cu))
              .containsExactly("CLM.CLAIM", "CLM.POLICY", "CLM.CLAIM_HIST"))
          )
        );
    }

    /**
     * A column in the select list, a join's condition or a {@code WHERE}, a function, and an alias
     * written with {@code AS} or without are none of them a table.
     */
    @Test
    void takesNoColumnFunctionOrAliasForATable() {
        rewriteRun(
          db2(
            """
              CREATE VIEW CLM.TOTALS AS
                SELECT T.TYPE_CD, SUM(C.AMT)
                FROM CLM.CLAIM C JOIN CLM.POLICY AS P ON C.POLICY_NO = P.POLICY_NO, CLM.TYPES T
                WHERE C.TYPE_CD = T.TYPE_CD
                GROUP BY T.TYPE_CD;
              """,
            spec -> spec.afterRecipe(cu -> assertThat(tables(cu))
              .containsExactly("CLM.CLAIM", "CLM.POLICY", "CLM.TYPES"))
          )
        );
    }

    @Test
    void aSubquerysTablesAreReadOnce() {
        rewriteRun(
          db2(
            """
              CREATE VIEW CLM.OPEN_RECENT AS
                SELECT X.CLAIM_NO
                FROM (SELECT CLAIM_NO, STATUS FROM CLM.CLAIM_HIST) X
                WHERE X.STATUS IN (SELECT S.CODE FROM CLM.STATUS S WHERE S.FLAG = 'Y');
              """,
            spec -> spec.afterRecipe(cu -> assertThat(tables(cu))
              .containsExactly("CLM.CLAIM_HIST", "CLM.STATUS"))
          )
        );
    }

    @Test
    void aCommonTableExpressionIsNoTable() {
        rewriteRun(
          db2(
            """
              WITH OPENED (CLAIM_NO) AS (SELECT CLAIM_NO FROM CLM.CLAIM WHERE STATUS = 'O'),
                   PAID AS (SELECT CLAIM_NO FROM CLM.PAYMENT)
              SELECT O.CLAIM_NO FROM OPENED O, PAID P, CLM.POLICY WHERE O.CLAIM_NO = P.CLAIM_NO;
              """,
            spec -> spec.afterRecipe(cu -> assertThat(tables(cu))
              .containsExactly("CLM.CLAIM", "CLM.PAYMENT", "CLM.POLICY"))
          )
        );
    }

    @Test
    void anUpdateOrADeleteReadsTheTablesOfItsSubqueries() {
        rewriteRun(
          db2(
            """
              UPDATE CLM.CLAIM C SET STATUS = (SELECT S.CODE FROM CLM.STATUS S WHERE S.FLAG = 'C')
                WHERE C.POLICY_NO IN (SELECT POLICY_NO FROM CLM.POLICY);
              DELETE FROM CLM.CLAIM_HIST WHERE CLAIM_NO NOT IN (SELECT CLAIM_NO FROM CLM.ARCHIVE);
              """,
            spec -> spec.afterRecipe(cu -> assertThat(tables(cu))
              .containsExactly("CLM.STATUS", "CLM.POLICY", "CLM.ARCHIVE"))
          )
        );
    }

    private static List<String> tables(Db2.Ddl cu) {
        return new Query.Matcher().lower(cu)
          .flatMap(query -> query.getTables().stream())
          .map(Db2.Name::getFullName)
          .collect(toList());
    }
}
