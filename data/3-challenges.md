---
path: "/3-challenges"
title: "Part 3: Here be dragons"
hidden: false
information_page: true
---


## The Untestables

There are things which make testing more challenging. Many of them are a global variable of sorts. Global variables cause spooky action at a distance and make testing hard; the tests may randomly pass or fail depending on the order in which they are run.


### Test doubles

If the SUT (system under test) is not a pure function and it's hard to test together with the real objects, its dependencies can be replaced with [test doubles](https://martinfowler.com/bliki/TestDouble.html). The dependencies can be provided as method or constructor arguments (i.e. dependency injection).

There are five main categories of test doubles:

- **Dummy** is a placeholder to make the code compile, but doesn't affect the SUT.
- **Stub** returns data to the SUT e.g. using hard-coded method return values.
- **Spy** records how the SUT calls the spy, so that the test can afterwards assert on the recorded data.
- **Mock** contains pre-recorded expectations on how the SUT should call it, and will itself automatically verify the expectations. Requires a mocking framework.
- **Fake** is a simplified implementation of a dependency, not appropriate for production use, e.g. persistence layer based on a hashmap.

Only mock types you own.

Read more:
http://xunitpatterns.com/Test%20Double.html
https://jesusvalerareales.medium.com/testing-with-test-doubles-7c3abb9eb3f2
https://medium.com/@xpmatteo/how-i-learned-to-love-mocks-1-fb341b71328
http://www.mockobjects.com/2009/09/brief-history-of-mock-objects.html


### File system

The file system is a global variable which persists between test executions.

If a test needs to write to the disk, create a unique temporary directory on test setup and delete it recursively on teardown.

If the test process is killed or there are file locks, the teardown may not be able to delete the temporary directory. Avoid using `/tmp` and place the temporary directory inside the project directory, under the build target directory, so that any remains will be removed on a clean build.


### Database

The database is a global variable which persists between test executions.

Make it easy to run the tests locally. Docker makes it easy to start databases locally. For cloud-only databases, using a dev instance in the cloud is necessary.

Usually tests create the database schema on test setup, and remove it on teardown. Another style is to remove and recreate the schema on test setup, which makes it the responsibility of the next text to clean up what the previous test produced.

In focused integration tests, it may be possible to run each test in a transaction which is rolled back at the end of the test. This should make tests faster by avoiding the need to recreate the database schema for each test. If more than one thread is involved or the SUT is complex, this is strategy is usually not possible.

Tests may create their own test data, or there may be a shared set of test data in the database. The former makes tests more understandable and decoupled from each other. The latter can be used for also testing database migrations.

The test schema name may be hard-coded or unique. Unique schema names for each test makes it possible to run tests in parallel. If the test process is killed, test teardown is never executed, so the tests should automatically remove stale test schemas.

*Never run tests against a production database.* One trick is for the tests to only connect to a database whose name starts with "test".

Database tests can be made faster by configuring the database to never fsync to disk, or by configuring Docker to use a RAM disk.


### Network sockets

Network socket port numbers are a global variable at the operating system level.

TODO


### Time

Time is a global variable which is ever changing (hopefully monotonically increasing).

TODO


### Concurrency

The order of memory reads and writes between parallel threads, and the operating system's context switching, are unpredictable global variables.

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
