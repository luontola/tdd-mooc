---
path: "/1-tdd"
title: "Part 1: What is TDD"
hidden: false
information_page: true
---

## History

Sometime in the 1970s, the 12-year-old [Kent Beck](https://en.wikipedia.org/wiki/Kent_Beck) [[1]](https://www.amazon.com/Test-Driven-Development-Kent-Beck/dp/0321146530) was reading books that his father had brought home. One of the books said that this is how you write programs: *You take the input tape, look at its contents, and type manually the output tape that you expect to create. Then programming is the process of writing a program until the actual output tape matches the expected output tape.*

Some things that should not have been forgotten were lost. And for two decades, the technique passed out of all knowledge. Until, when chance came, it was rediscovered.

Fast forward to 1990s. Kent Beck had followed in his father's footsteps and was now a software consultant. He had written the first unit testing framework in the xUnit family, SUnit for Smalltalk. It was then that Kent remembered the book he had read as a kid. *"If I took this type-the-output-tape-first schema seriously, then I would write the test before I had the code."*

He thought the idea was stupid, so he had to try it.

Kent tried it with a stack, and half an hour later he was hooked. All the anxieties he had felt while programming, which had been getting worse as experience accrued, just dropped away. *"I'm not done until all the tests that I can imagine are all passing. But I don't have to make them all pass at once. I can just type one in and then make it work. And type the next one and make that work. Eventually... can I think of any other tests? No. I must be done."* He was completely relaxed.
[[2]](https://youtu.be/tM1iOJsR7p4?t=2080)
[[3]](https://youtu.be/cGuTmOUdFbo?t=325)


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


## What tests to write?

Ask yourself: What is the next most important thing, which the system *does not* yet do?

The first test and the simplification strategy: Where to start writing a sudoku solver? Solving a 1×1 sudoku.


### Test names should be sentences

The test names should describe the behaviour of the system - what the system should do.

Thought exercise: All production and test code has disappeared. The only thing remaining is the test names. Can a new person reimplement the system, so that it will do pretty much the same things as before?

When a test fails, look at the name of the test. There are three possibilities:

- The system has a bug. Fix the implementation.
- The behavior is still needed, but the test needs updating. Change the test. Think of how to decouple the test from unrelated behaviors, to have fewer unwanted test failures.
- The behavior is no more needed. Delete the test.

Read more:
https://dannorth.net/introducing-bdd/


# Exercises

<tdd-decision>

TODO: tetris exercise
https://github.com/luontola/tdd-mooc-tetris
https://github.com/luontola/tdd-tetris-tutorial

</tdd-decision>
