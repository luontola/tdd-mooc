---
path: "/2-design"
title: "Chapter 2: Refactoring and design"
hidden: false
information_page: true
sidebar_priority: 3000
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


## Duplication

The *Don't Repeat Yourself* (DRY) principle says:

> Every piece of knowledge must have a single, unambiguous, authoritative representation within a system.
>
> \- Andy Hunt and Dave Thomas, The Pragmatic Programmer (1999)

The alternative is to have the same thing expressed in two or more places. Then if you need to change one place, you might accidentally not remember to change the other place, leading to problems.

A common case of duplicated knowledge is similar code in two or more places. If the places are *almost* similar, first try to refactor them to be exactly the same code, after which it's easier to extract the common code to a shared function.


### Three strikes and you refactor

*aka rule of three*

If it's not yet obvious that in what way the duplicated code will vary from place to place, let the code fester/ripen a bit longer. Wait until the code is repeated in three places, and *then* refactor. This reduces the risk of creating the wrong abstraction. [[1]](https://en.wikipedia.org/wiki/Rule_of_three_(computer_programming))


### Naive duplication

Not all code that looks similar is duplicated knowledge. Even if chairs and dogs both have 4 legs, the 4s are not related.

In [Conway's Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life), the calculation of whether a cell will be alive in the next generation could be written like this:

```pseudo
will_live? = if alive?
                 neighbors == 2 || neighbors == 3
             else
                 neighbors == 3
```

The `neighbors == 3` is repeated, so let's remove the duplication:

```pseudo
will_live? = (alive? && neighbors == 2) || neighbors == 3
```

But this is wrong. It couples together two unrelated concepts:

In the original code, the 2 and 3 in the `alive?` branch are related to the concept of a "stable neighborhood", whereas the 3 in the `not alive?` branch means "genetically fertile neighborhood".

By naming these concepts it becomes clear that they are not actually duplicated:

```pseudo
will_live? = if alive?
                 stable_neighborhood?
             else
                 genetically_fertile_neighborhood?
```

Read more:
this example was from the book https://leanpub.com/4rulesofsimpledesign


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

To do a refactoring with far-reaching consequences, you have two options:

* *One big change:* Carefully analyze the whole codebase to figure out every place that must be changed, and then change all those places at the same time. It may take hours to read the codebase and you could still miss some side-effect. When tests break after your changes, the problem could be anywhere in the codebase.
* *Many small changes:* Plan a series of changes, each of which can be proven through local reasoning to not change behavior. Start the refactoring from one place and propagate it in tiny increments by making mechanical changes which require very little thinking. There could be 10 or 100 times more steps, but each of them takes just a couple seconds. When tests break after your changes, the problem should be near the line you changed 5 seconds ago.

Many small changes is faster than one big change.

When you refactor, don't change behavior. When you change behavior, don't refactor. Maintain three points of contact like a mountain climber. 🧗

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

Design is scale-free. The *parallel change* strategy works the same way regardless of whether we're adding a new field to a class, or replacing the healthcare software system of a city.

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

---

Proceed to [Chapter 3: The Untestables](/3-challenges) or [Exercises](/exercises)
