---
path: "/2-design"
title: "Part 2: Refactoring and design"
hidden: false
information_page: true
---


## Four elements of simple design

In priority order:

1. Passes its tests
2. Minimizes duplication
3. Maximizes clarity
4. Has fewer elements

With TDD, point 1 is a given. If points 2 and 3 are covered, then point 4 rarely comes up. Thus, the rules can be summarized:

- Remove duplication
- Improve names

Keep practicing these, and in a few decades every other design principle and pattern will emerge.

Read more:
https://blog.jbrains.ca/permalink/the-four-elements-of-simple-design
https://martinfowler.com/bliki/BeckDesignRules.html


## Naming things

> There are only two hard things in Computer Science: cache invalidation and naming things.
>
> \- Phil Karlton [[1]](https://martinfowler.com/bliki/TwoHardThings.html)

Removing duplication and improving names feed into each other in a virtuous cycle.

Naming is a process: nonsense, accurate-but-vague, precise, meaningful. Never stop improving.

Read more:
https://agileinaflash.blogspot.com/2009/02/meaningful-names.html
https://blog.thecodewhisperer.com/permalink/a-model-for-improving-names
https://www.digdeeproots.com/articles/on/naming-process/


## Composed method

> Divide your program into methods that perform one identifiable task. Keep all of the operations in a method at the same level of abstraction. This will naturally result in programs with many small methods, each a few lines long.
>
> \- Kent Beck, Smalltalk Best Practice Patterns (1996) [[1]](https://wiki.c2.com/?ComposedMethod)

Each new method is an opportunity to introduce a new name.

The code should read like a newspaper article. First the high-level overview, then dig deeper into more details.

Each refactoring can be done in two directions. If you inline everything into one big method, it may be possible to see a different way to split it into small methods.

Read more:
https://farenda.com/patterns/composed-method-pattern/
https://github.com/jnguyen095/clean-code/issues/28


## Small, safe steps

Many small changes is faster than one big change.

When you refactor, don't change behavior. When you change behavior, don't refactor. Maintain three points of contact like a rock climber. 🧗

Read more:
https://www.infoq.com/presentations/responsive-design/


### Refactoring hell

If it's been more than a few minutes since the tests last passed, `git reset --hard` and try again. (Your IDE's [local history](https://www.jetbrains.com/help/idea/local-history.html) may also show when all tests last passed, so you can revert to that point in time.)

Read more:
https://wiki.c2.com/?RefactoringHell


## Four strategies

There are four strategies to refactor and design using small, safe steps:

- **Leap:** The change is small enough. Just do it.
- **Parallel:** Build the new solution side-by-side with the old solution, until the new can replace the old.
- **Stepping stone:** You want to build thing A, but if you built thing B first, that could make it easier to build thing A. (Beware of gold plating.)
- **Simplification:** Solve a trivialized version of the problem first. 1×1 sudoku.

Read more:
https://www.infoq.com/presentations/responsive-design/


## Test quality

Tests should be sensitive to behavior changes and insensitive to structure changes.

Each test should test only one thing.

Each bug should cause only one test to fail.

From the pattern of failing tests, it should be possible to guess in which function or line the problem is.

If some code can be commented out and no test fails, the code should be dead.

In five seconds you will have forgotten over half of your [working memory](https://en.wikipedia.org/wiki/Working_memory). Make test so fast that you won't forget what you were thinking.

![Decay of working memory if rehearsal is prevented. The time scale is of the order of a few seconds.](images/working-memory-decay.png "How quickly working memory decays when you think of something else. [[1]](https://www.researchgate.net/figure/Decay-of-working-memory-if-rehearsal-is-prevented-The-time-scale-is-of-the-order-of-a_fig7_35883885)")


Read more:
https://agileinaflash.blogspot.com/2009/02/first.html
https://medium.com/@kentbeck_7670/programmer-test-principles-d01c064d7934


### Test smells

**Many asserts per test:** Normally tests are structured in the [Arrange-Act-Assert](https://agileinaflash.blogspot.com/2009/03/arrange-act-assert.html)/[Given-When-Then](https://dannorth.net/introducing-bdd/)/[Hoare Triple](https://en.wikipedia.org/wiki/Hoare_logic) format. But if they are instead *Arrange-Act-Assert-Act-Assert-Act...*, that usually indicates that a test is lacking focus and testing many different things (making the test's purpose harder to decipher), or that it's testing how the system does things instead of what the system does (making refactoring harder). Depending on the testing framework, earlier test failures may also mask later test failures, making it harder to know why the test failed. Instead, write many small, focused tests.

**Complex test setup:** If the *arrange* part of a test is long, requiring the careful arrangement of many collaborators to get the system into the desired initial state for the test, it may indicate design problems in the code. Lots of constructor arguments is a similar smell (which is why dependency injection frameworks are best avoided - they make it too easy to add dependencies). Instead, try to think of a design which eliminates some of the dependencies.


# Exercise 2: Small, safe steps

Clone the project <https://github.com/luontola/tdd-mooc-small-steps> and follow its instructions.

The assignment is to *refactor the above code to replace [Date](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Date) class with [Temporal.PlainDate](https://tc39.es/proposal-temporal/docs/plaindate.html) class.* Repeat this refactoring many times. Focus on doing as small changes as possible, so that all tests will pass between every change.

Afterwards we will have a competition between the students of this course about doing this refactoring using the smallest possible steps.

##### What to submit

* **Perform this refactoring live** in the weekly exercise groups.
* Submit to https://studies.cs.helsinki.fi/stats/courses/tdd-pilot a **Git repository** with the history of you doing this refactoring, so that the commits are **as small as possible** and all the tests **pass on every commit**.

---

Proceed to [Part 3: The Untestables](/3-challenges)