# Sql Quality

## Statically detectable SQL performance anti-patterns, with severity and location

SQL statements matching performance anti-pattern rules such as `SELECT *`, `COUNT(*)` used as an existence check, DML with no `WHERE`, non-sargable predicates, leading-wildcard `LIKE`, and missing join conditions. One row per rule match, located by source path and line number, with a severity of INFO, WARNING, or CRITICAL. Covers SQL in string literals, `.sql` files, MyBatis mapper XML, YAML and JSON resources, and statements assembled by concatenation or string interpolation. Findings are derived from the statement text alone, with no knowledge of the schema, indexes, or data volume, so use this to prioritize which queries to review rather than as proof that a query is slow. JPQL passed to `@Query` is reported too where it parses as SQL, in which case the names in the finding are entities rather than physical tables.

## Data Tables

### SQL anti-patterns

**File:** [`sql-anti-patterns.csv`](sql-anti-patterns.csv)

SQL statements matching performance anti-pattern rules.

| Column | Description |
|--------|-------------|
| Source path | The path to the source file containing the SQL. |
| Line number | The line the SQL statement begins on. |
| Rule | The identifier of the anti-pattern rule that matched. |
| Severity | How likely the pattern is to cause a measurable performance problem. |
| Finding | A description of the specific occurrence of the anti-pattern. |
| Query | The SQL statement the anti-pattern was found in. |

