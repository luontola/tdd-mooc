---
path: "/3-challenges"
title: "Part 3: Here be dragons"
hidden: false
information_page: true
---


## The Untestables

There are things which make testing more challenging. Many of them are a global variable of sorts. Global variables cause spooky action at a distance and make testing hard; the tests may randomly pass or fail depending on the order in which they are run.


### Test doubles

If the SUT (system under test) is not a pure function and it's hard to test together with the real objects, its dependencies can be replaced with [test doubles](https://martinfowler.com/bliki/TestDouble.html). There are five main categories of test doubles:

- **Dummy** is a placeholder to make the code compile, but doesn't affect the SUT.
- **Stub** returns data to the SUT e.g. using hard-coded method return values.
- **Spy** records how the SUT calls the spy, so that the test can afterwards assert on the recorded data.
- **Mock** contains pre-recorded expectations on how the SUT should call it, and will itself automatically verify the expectations. Requires a mocking framework.
- **Fake** is a simplified implementation of a dependency, not appropriate for production use, e.g. persistence layer based on a hashmap.

Read more:
http://xunitpatterns.com/Test%20Double.html
https://jesusvalerareales.medium.com/testing-with-test-doubles-7c3abb9eb3f2


### Database

TODO


### Time

TODO


### Concurrency

TODO


### User interface

TODO


## Legacy code

TODO


### Fixing bugs test-first

TODO


### Seams

TODO


### Code coverage

TODO


### Golden master/Snapshot/Approval/Characterization testing

TODO


## Walking skeleton

TODO


## Continuous delivery

TODO


# Exercises

TODO: write tests for existing code, 100% mutation coverage
https://github.com/emilybache/GildedRose-Refactoring-Kata

TODO: web app (TODO app), realistic stack, walking skeleton, UI and DB tests
