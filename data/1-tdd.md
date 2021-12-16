---
path: "/1-tdd"
title: "Chapter 1: What is TDD"
hidden: false
information_page: true
sidebar_priority: 3000
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

TDD can be described in terms of these three rules:

1. You shall not write any production code, unless required by a failing unit test.
2. You shall not write more of a unit test, than is required to fail (assertion failure/program crash/compile error).
3. You shall not write more production code, than is sufficient to make the one failing unit test pass.

Read more:
http://www.butunclebob.com/ArticleS.UncleBob.TheThreeRulesOfTdd
https://codingitwrong.com/2020/12/23/why-write-the-minimum-code-to-pass-the-test.html


## Red, Green, Refactor

Another way to describe TDD is the following cycle of three phases. Every few minutes, in order:

1. **Red**: Write a failing test. Predict (mentally or out loud) how the test should fail. Run the test. See it fail. If it failed in an unexpected way, check your assumptions. Improve the failure's readability.
2. **Green**: Make the test pass. ASAP. Commit any sins necessary. Hard-coded values. If statements. Fake it until you make it.
3. **Refactor**: Improve the design of the code, without changing its behavior. Atone for your sins. Remove duplication. Improve names. Minimal code which passes the current tests.

Repeat until there are no more tests to write/until bored.

If the current design makes it difficult to make a new test pass, consider commenting out the new test and refactoring first. "Make the change easy (warning: this may be hard), then make the easy change." [[1]](https://twitter.com/KentBeck/status/250733358307500032)

As the tests get more specific, the implementation gets more generic.

Read more:
http://www.jamesshore.com/v2/blog/2005/red-green-refactor
https://blog.cleancoder.com/uncle-bob/2014/12/17/TheCyclesOfTDD.html


### Test list

To stay focused on the current test and phase, it's helpful to maintain a [test list](https://twitter.com/ursenzler/status/1433096612088356866/photo/2) (on paper, a text file or as TODO comments in tests).

Whenever you come up with an idea for a new test (e.g. a new feature or an edge case that needs to be covered), add it to your test list. Likewise for things that need refactoring. Then when you are done with the current feature, you can go through the list and handle them.


## What it looks like

In the following video, [Otavio Lemos](https://twitter.com/otaviolemos) demonstrates TDD using the [Bowling Game Kata](https://kata-log.rocks/bowling-game-kata). Some points to pay attention to:

- No more code is added than is required to pass the tests. At first the implementation is trivial.
- The code is refactored early and often.
- If the current design makes passing the next test hard, the test is commented out and the code is first refactored toward a better design.
- As more tests are added to cover specific edge cases, the code grows toward a generic algorithm which handles them all.

<iframe style="width: 100%; aspect-ratio: 560/315;"  src="https://www.youtube-nocookie.com/embed/1o_EbACQpQ8" title="Bowling Kata in TypeScript - YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>


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

When writing the first test, it helps to start small and simplify the problem: Where to start writing a sudoku solver? Solving a 1×1 sudoku.


### Test names should be sentences

Software testing attempts to find faults in the code. But in TDD, the "tests" are thought of as a specification of the system's behavior, or as small examples of how the code functions. The focus is more on requirements and design than on verification.

The test names should be sentences which describe what the system should do - its behavior.

Thought exercise: All production and test code has disappeared. The only thing remaining is the test names. Can someone reimplement the system, so that it will do pretty much the same things as before?

When a test fails, look at the name of the test. There are three possibilities:

- The system has a bug. Fix the implementation.
- The behavior is still needed, but the test needs updating. Change the test. Think of how to decouple the test from unrelated behaviors, to have fewer unwanted test failures.
- The behavior is no more needed. Delete the test.

Read more:
https://dannorth.net/introducing-bdd/

---

Proceed to [Chapter 2: Refactoring and design](/2-design) or [Exercises](/exercises)
