---
path: "/4-legacy-code"
title: "Part 4: Legacy code"
hidden: false
information_page: true
---

TODO: legacy code definition


## Fixing bugs test-first

TODO


## Seams

TODO

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


# Exercise 3: Testing legacy code

Clone the project <https://github.com/emilybache/GildedRose-Refactoring-Kata>. Take one of the JavaScript editions of that project and configure it to use the [Stryker mutation testing framework](https://stryker-mutator.io/). Also install a basic code coverage tool which calculates line coverage.

The assignment is to *write tests for the above code, until it has 100% mutation test coverage.*

TODO: simplify the project, preconfigure stryker and some line coverage tool

**Submittable artifact:** Git repository with the source code and history of you doing this exercise. Also include a file with the mutation test results.


---

Proceed to [Part 5: Advanced techniques](/5-advanced)
