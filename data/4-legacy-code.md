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

<recommended-reading>

- [Working Effectively with Legacy Code](https://www.amazon.com/Working-Effectively-Legacy-Michael-Feathers/dp/0131177052) - the definitive book about refactoring legacy code

</recommended-reading>


## Code coverage

[A study from 2014](https://www.usenix.org/conference/osdi14/technical-sessions/presentation/yuan) found that 58% of catastrophic failures were caused by "trivial mistakes or can be exposed by statement coverage testing." With TDD you'll get such coverage out of the box, but in non-TDD'ed codebases you can't trust that everything has been covered.

Code coverage tools can be useful in finding untested areas, but not all of them can tell whether the code is well tested.

**Line/statement coverage** says whether a line was executed by a test. (You could have 100% line coverage even if the tests have zero assertions.)

**Branch/edge/condition coverage** also makes a distinction between partially executed lines. For example in `a = b() && c()`, if `b()` returns false then `c()` is never executed. Line coverage would say the line was 100% covered, but branch coverage would say it was 50% covered. (You could have 100% branch coverage even if the tests have zero assertions.)

**Mutation coverage** introduces bugs into your code and alarms if no test noticed them. It'll actually test your tests. For a code like `a = b() && c()`, it could try mutations `a = b() || c()`, `a = b() == c()`, `a = b() != c()`, `a = !b() && c()`, `a = true && c()`, `a = false && c()` and so on. There can be some false warnings, so going through the coverage report takes more time, but refactoring the code to avoid the false warnings can sometimes improve code quality. Even when the mutation coverage tool is smart about which tests to run for each mutation, it's typically an order of magnitude slower than running the tests normally: if the tests normally run in 1 second, measuring mutation coverage might take 1 minute.

When writing tests for legacy code, code coverage can be helpful. See which lines are not covered and for each conditional, write test cases that cause the code to take both branches.

<recommended-reading>

- [How Much Unit Test Coverage Do You Need? - The Testivus Answer](https://www.artima.com/weblogs/viewpost.jsp?thread=204677)

</recommended-reading>


## Characterization tests

*aka golden master, aka snapshot testing, aka approval testing.*

With legacy code, the source code is the truth, even when it contains bugs. The first priority is improving the code coverage, so that the code can be refactored safely.

Write tests which just assert that the code *does what it does*. Understanding the code is not necessary for writing such tests; use code coverage to your advantage and make sure that every code path is executed.

After the code is covered with characterization tests, it can be refactored, making it easier to understand what the code does. After you understand the code, it's easier to write more descriptive tests for it and begin changing what the code does.

<recommended-reading>

- [Understanding Legacy Code with Characterization Testing](https://www.infoq.com/news/2007/03/characterization-testing/) - what is characterization testing
- [Cutting Code Quickly: From 0% to Cleanly Refactored 100% Tested Code](https://www.youtube.com/watch?v=8OxH9Lz0Ckg) - a technique for characterization testing and refactoring without having to understand the code

</recommended-reading>


## Fixing bugs test-first

An excellent time and place for improving legacy code's test coverage is when there is a bug to fix. First write a test which reproduces the bug, and then fix it. Having a test which reproduces the bug makes it easier to fix it, and once the test is in place, you can be sure that the same bug will not reappear.

In the larger scope, this slowly improves the codebase as a whole. Bugs tend to cluster together, so by writing tests for them and refactoring those areas of the code, it reduces the risk of other bugs in the same area.

Also when adding new features, it's best to do it test-first. You can write the new code as an isolated component using TDD, and then change the legacy code to just delegate to the new code. Little by little you grow oases of safe areas that are easy to work with, and those will be in the areas of the codebase which change most often.

---

Proceed to [Chapter 5: Advanced techniques](/5-advanced) or [Exercises](/exercises)
