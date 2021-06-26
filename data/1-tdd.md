---
path: "/1-tdd"
title: "Part 1: What is TDD"
hidden: false
information_page: true
---

## Three rules of TDD

1. You shall not write any production code, unless required by a failing unit test.
2. You shall not write more of a unit test, than is required to fail (assertion failure/program crash/compile error).
3. You shall not write more production code, than is sufficient to make the one failing unit test pass.

Read more:
http://www.butunclebob.com/ArticleS.UncleBob.TheThreeRulesOfTdd


## Red, Green, Refactor

Every minute or so:

1. **Red**: Write a failing test. Run the test. See it fail. If it failed in an unexpected way, be surprised. Improve the failure's readability.
2. **Green**: Make the test pass. ASAP. Commit any sins necessary. Hard-coded values. If statements. Fake it until you make it.
3. **Refactor**: Improve the design of the code, without changing its behavior. Atone for your sins. Remove duplication. Fix bad names. Minimal code which passes the current tests.

Repeat until there are no more tests to write/until bored.

As the tests get more specific, the implementation gets more generic.

Read more:
http://www.jamesshore.com/v2/blog/2005/red-green-refactor
https://blog.cleancoder.com/uncle-bob/2014/12/17/TheCyclesOfTDD.html
https://blog.cleancoder.com/uncle-bob/2013/05/27/TheTransformationPriorityPremise.html


## What it looks like

TODO: demo video, roman numerals kata, with steps explained


## Direct and indirect effects of TDD

*Direct effects*, if just following the three rules of TDD:

- Guarantees code coverage
- Amplifies the pain caused by bad code

*Indirect effects*, if the programmer is skilled enough:

- Enables changing the code without breaking it
- Improves the quality of the code

Read more:
http://blog.orfjackal.net/2010/04/direct-and-indirect-effects-of-tdd.html


## Test names should be sentences

When writing a test, ask yourself: What is the next most important behavior, which the system does not yet do? Write that down as the name of the test.

When a test fails, look at the name of the test. It should describe that what the system should do. Possibilities:

- The implementation had a bug. Fix it.
- The behavior is still needed, but the test needs updating. Change the test. Make it less coupled to the implementation details, so that you would get fewer false warnings.
- The behavior is no more needed. Delete the test.

Read more:
https://dannorth.net/introducing-bdd/
