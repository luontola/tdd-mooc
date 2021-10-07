---
path: "/4-legacy-code"
title: "Chapter 4: Legacy code"
hidden: false
information_page: true
sidebar_priority: 3000
---

What is legacy code? Code written by someone else? Code that you're afraid to change? If the code would have tests, you could change it without fear of breaking existing behavior. Thus the most useful definition of legacy code is *code without tests*. [[1]](https://www.amazon.com/Working-Effectively-Legacy-Michael-Feathers/dp/0131177052)

The first step in refactoring is that the code has tests. [[2]](https://martinfowler.com/books/refactoring.html) Otherwise it's just changing stuff and hoping for the best. Even if the code is bad, we can improve the design safely if it has tests. The biggest challenge with legacy code is that it was not written with testing in mind.


## Seams

A seam is a place in the code where you can alter the behavior of the program without editing that place. In object-oriented languages, the most common seam is a polymorphic method call. In some languages (e.g. C), preprocessor and linker based seams are also an option.

The basic strategy for changing legacy code is:

1. Identify the place you need to change.
2. Plan that where would be a good place to test it.
3. Break dependencies which hinder testing. Without tests, you must introduce new seams using minimal, safe changes. The code quality may temporarily worsen.
    * As an example, the *Extract and Override Call* technique goes like this: Extract the difficult line of code to a new method. In tests, create a testable subclass which overrides the problematic method with a fake implementation. Write tests against that testable subclass.
4. Cover the code with characterization tests.
5. Do the change you originally wanted.
6. Refactor and make the code more testable.

A whole book has been written about this topic, so it doesn't need to be repeated here. Read the book - [it gets better with age](https://www.commitstrip.com/en/2019/03/13/like-a-good-wine/).

Read more:
https://www.amazon.com/Working-Effectively-Legacy-Michael-Feathers/dp/0131177052


## Code coverage

Code coverage tools can be useful in finding untested areas, but not all of them can tell whether the code is well tested.

**Line/statement coverage** says whether a line was executed by a test. (You could have 100% line coverage even if the tests have zero assertions.)

**Branch/edge/condition coverage** also makes a distinction between partially executed lines. For example in `a = b() && c()`, if `b()` returns false then `c()` is never executed. Line coverage would say the line was 100% covered, but branch coverage would say it was 50% covered. (You could have 100% branch coverage even if the tests have zero assertions.)

**Mutation coverage** introduces bugs into your code and alarms if no test noticed them. It'll actually test your tests. For a code like `a = b() && c()`, it could try mutations `a = b() || c()`, `a = b() == c()`, `a = b() != c()`, `a = !b() && c()`, `a = true && c()`, `a = false && c()` and so on. There can be some false warnings, so going through the coverage report takes more time, but refactoring the code to avoid the false warnings can sometimes improve code quality. Even when the mutation coverage tool is smart about which tests to run for each mutation, it's typically an order of magnitude slower than running the tests normally: if the tests normally run in 1 second, measuring mutation coverage might take 1 minute.

When writing tests for legacy code, code coverage can be helpful. See which lines are not covered and for each conditional, write test cases that cause the code to take both branches.

Read more:
https://www.artima.com/weblogs/viewpost.jsp?thread=204677


## Characterization tests

*aka golden master, aka snapshot testing, aka approval testing.*

With legacy code, the source code is the truth, even when it contains bugs. The first priority is improving the code coverage, so that the code can be refactored safely.

Write tests which just assert that the code *does what it does*. Understanding the code is not necessary for writing such tests; use code coverage to your advantage and make sure that every code path is executed.

After the code is covered with characterization tests, it can be refactored, making it easier to understand what the code does. After you understand the code, it's easier to write more descriptive tests for it and begin changing what the code does.

Read more:
https://www.infoq.com/news/2007/03/characterization-testing/
https://www.youtube.com/watch?v=8OxH9Lz0Ckg


## Fixing bugs test-first

TODO



---

Proceed to [Chapter 5: Advanced techniques](/5-advanced) or [Exercises](/exercises)
