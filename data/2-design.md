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

With TDD, point 1 is a given. If points 2 and 3 are covered, then point 4 rarely comes up. Thus the rules can be summarized:

- Remove duplication
- Fix bad names

Keep practising these, and in a few decades every other design principle and pattern will emerge.

Read more:
https://blog.jbrains.ca/permalink/the-four-elements-of-simple-design
https://martinfowler.com/bliki/BeckDesignRules.html


## Naming things

> There are only two hard things in Computer Science: cache invalidation and naming things.
>
> \- Phil Karlton

Naming is a process: nonsense, vague, precise, meaningful. Never stop improving.

Removing duplication and improving names feed into each other in a virtuous cycle.

Read more:
https://blog.thecodewhisperer.com/permalink/a-model-for-improving-names
https://www.digdeeproots.com/articles/on/naming-process/
https://martinfowler.com/bliki/TwoHardThings.html


## Composed method

> Divide your program into methods that perform one identifiable task. Keep all of the operations in a method at the same level of abstraction. This will naturally result in programs with many small methods, each a few lines long.
>
> \- Kent Beck, Smalltalk Best Practice Patterns (1996)

Read more:
https://farenda.com/patterns/composed-method-pattern/
https://wiki.c2.com/?ComposedMethod


## Small, safe steps

Many small changes is faster than one big change.

When you refactor, don't change behavior. When you change behavior, don't refactor. Maintain three points of contact like a rock climber. 🧗

Read more:
https://www.infoq.com/presentations/responsive-design/


## Four strategies

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

In five seconds you will have forgotten over half of your working memory. Make test so fast that you won't forget what you were thinking.

![Diagram: Decay of working memory if rehearsal is prevented. ](images/working-memory-decay.png "The first image search result for [working memory decay](https://www.researchgate.net/figure/Decay-of-working-memory-if-rehearsal-is-prevented-The-time-scale-is-of-the-order-of-a_fig7_35883885).")


Read more:
https://agileinaflash.blogspot.com/2009/02/first.html
https://medium.com/@kentbeck_7670/programmer-test-principles-d01c064d7934


## Test smells

TODO


# Exercises

TODO: lift pass refactoring, safe steps, replace Date with Temporal.PlainDate
https://github.com/martinsson/Refactoring-Kata-Lift-Pass-Pricing/tree/with_tests/typescript
https://www.sitepoint.com/javascript-temporal-api-introduction/
