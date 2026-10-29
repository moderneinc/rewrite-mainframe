# Parser cost

A change to the grammars here or to `CobolParserVisitor` must not make parsing slower or allocate more without its PR saying why. `ParseCostTest` measures it. It is skipped unless `COBOL_CORPUS` names a corpus of real programs:

```bash
COBOL_CORPUS=/path/to/corpus ./gradlew test --tests '*ParseCostTest' --rerun
```

`--rerun` keeps Gradle from replaying an earlier result. The report goes to the test's standard output, which Gradle keeps in `build/test-results/test/TEST-org.openrewrite.mainframe.cobol.ParseCostTest.xml`.

Run it on main and on the branch, alternating, three times each, under an exclusive CPU claim. Compare in this order:

- **The prediction cache's configurations, and how many differ only in predicate order.** Both are deterministic, so any difference is real. The parser sorts the operands of every predicate it combines, so the second number is 0, and anything else means some prediction path combines predicates without going through that sort.
- **Building the cache, pass 4 minus warm.** This is what a change to prediction costs. A single pass's excess over a warm one is this plus JIT warm-up, so comparing single passes compares warm-up.
- **The warm passes.** They are what a long run over many programs sees, for time and allocation alike.
- **The full-context predictions in pass 1**, by rule, and the longest span one scanned. Each is an ambiguity SLL could not settle, and costs a second prediction every time the parser meets it. The test fails when one prediction scans more than 50 tokens, the shape of a change that makes parsing quadratic. A rule that appears here for the first time has an exit or an alternative that needs a predicate stating the answer, as `imperativeStatements` and the statement phrases have.

## What went wrong before

- A predicate on both sides of a loop over statement lists made SLL closure grow until a 10 GB heap ran out on a 20-line program (#189). One predicate on the exit side of the decision, in a rule shared by every call site, did the same job.
- An ambiguity that SLL resolves to the first alternative still costs a full-context retry every time the parser meets it. `FullContextPredictionTest` asserts there are none in the rules it names, so give it a fixture for each rule a change touches.
- A predicate is evaluated during SLL prediction only at the start of an alternative or at a loop's decision. One placed after a consumed token, `qualifiedDataName {!operatorAhead()}?` say, is skipped by prediction and only checked when the alternative is parsed, so it settles nothing. Put the lookahead test first: `{plainSubscriptAhead($ctx)}? qualifiedDataName`.
- A predicate that references `$ctx` is context-dependent. SLL evaluates it in its own rule's decisions and passes through it from every other decision's closure, so the predicates of nested statements never combine. Sixty-three plain `{_input.LA(1) != X}?` tail predicates ran a 10 GB heap out; the same predicates taking `$ctx` cost nothing. Every predicate on a statement's tail takes it.
- A predicate at a rule's end sits on the FOLLOW path of every decision that can exit through that rule. The predicates #212 put on `programUnit`'s tail, so that SLL would not scan every nested program in full once more, turned 74 LL(1) decisions in the division and paragraph rules into `adaptivePredict` calls. Reading a batch as a flat sequence and nesting the programs in `CobolParserVisitor` needs neither. Weigh a tail predicate against the decisions it reaches before adding one.
- Splitting the grammar into a lexer and a parser renumbered rules and predicates (#197). Where main had no configurations differing only in predicate order, 192,356 appeared, and building the cache over the corpus went from 0.74 s to 1.11 s. Nothing in the grammar diff shows this, which is why the report counts it.
