---
path: "/3-challenges"
title: "Part 3: Here be dragons"
hidden: false
information_page: true
---


## The Untestables

There are things which make testing more challenging. Many of them are global variables of sorts. Global variables cause spooky action at a distance. Do the same thing over and over again, and get a different result. Tests may randomly pass or fail, depending on the order in which they are run.


### Singletons

[Singleton](https://en.wikipedia.org/wiki/Singleton_pattern) is an anti-pattern. It is the object-oriented equivalent of a global variable. Instead, [just create one](http://www.butunclebob.com/ArticleS.UncleBob.SingletonVsJustCreateOne).


### Test doubles

If the SUT (system under test) is not a pure function and it's hard to test together with the real objects, its dependencies can be replaced with [test doubles](https://martinfowler.com/bliki/TestDouble.html). The dependencies can be provided as method or constructor arguments (aka dependency injection).

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

If the test process is killed or there are file locks, the teardown may not be able to delete the temporary directory. Avoid using `/tmp` and instead place the temporary directory inside the project directory, under the build target directory, so that any stale directories will be removed on a clean build.


### Database

The database is a global variable which persists between test executions.

Make it easy to run the tests locally. [Docker Compose](https://docs.docker.com/compose/) is simple and easy. For cloud-only databases, using a development instance in the cloud is necessary.

Usually tests create the database schema on test setup, and remove it on teardown. Another style is to remove and recreate the schema on test setup, which makes it the responsibility of the next test to clean up what the previous test produced. Clean-before allows peeking the data after a test run, but you would still need to focus run a single test, so commenting out the teardown or using a sleep gives the same effect with the clean-after approach. Mom always told to clean up after yourself.

In focused integration tests, it may be possible to run each test in a rollback-only transaction. This should make tests faster by avoiding the need to recreate the database schema for each test. If more than one thread is involved or the SUT is complex, this strategy is usually not possible.

Tests may create their own test data, or there may be a shared set of test data in the database. The former makes tests more understandable and decoupled from each other. The latter can be used for also testing database migrations.

The test schema name may be hard-coded or unique. Unique names for each test make it possible to run tests in parallel. If the test process is killed, test teardown is never executed, so the tests should automatically remove stale test schemas (especially if using a shared long-running database instead of a local container/VM), or you will learn the database's soft and hard limits.

*Never run tests against a production database.* One safeguard is for the tests to only connect to a database whose name starts with "test".

Database tests can be made faster by [disabling fsync or using a RAM disk](https://pythonspeed.com/articles/faster-db-tests/).


#### Dead ends

You *could* replace the database with an in-memory fake implementation for tests (e.g. hashmap). It will make the tests faster, but will require maintaining two parallel implementations - the real and the fake persistence layer. Even when using [contract tests](https://blog.thecodewhisperer.com/permalink/getting-started-with-contract-tests) to make the implementations functionally equivalent, they will be leaky abstractions with non-obvious differences (transactions, foreign key contraints etc.). It's better to decouple business logic from persistence: you won't need to fake dependencies if you have no dependencies ([insert meme here](https://knowyourmeme.com/memes/roll-safe)).

Some people use an embedded in-memory database in tests and a different database in production, for example HSQLDB vs PostgreSQL. This is a road to madness. Even if SQL is a standard, each implementation is different ([insert meme here](https://xkcd.com/927/)), so you will anyways need to run the tests against both databases. It might avoid having to install a database and the data will be removed after the test process exits, but nowadays `docker compose up -d db` is easy and even with an in-memory database you will need to handle isolation between test cases. Speed is not an argument either; a PostgreSQL which is already running is faster than a HSQLDB that needs to start every test run, not to speak of runtime performance. Most importantly, you would be limited to a subset of SQL that works on both databases, or you will need to maintain alternative versions of the queries; you would miss out on useful database-specific features such as [triggers/stored procedures](https://www.postgresql.org/docs/13/plpgsql-trigger.html) and [range types](https://medium.com/pyankit/postgres-range-types-are-dope-ca18923f3d46). Summa summarum, use the same technology in tests as in production.

I just saved you 5-10 years of experimenting.


### Network sockets

Network socket port numbers are a global variable at the operating system level.

If using a shared [continuous integration](https://martinfowler.com/articles/continuousIntegration.html) server, there can be many builds running on the same machine and they compete for the same port numbers. Even when running tests locally, you will typically have an application instance for manual testing running at the same time as the automated tests. The local development instance may use hard-coded ports, but the tests should allocate a random free port for the database and web server.

Most servers you can bind to listen on port 0 and the operating system will assign it an unused port number. After the server has started, you can find out what port was assigned to it and use that in the tests. [`docker compose port`](https://docs.docker.com/compose/reference/port/) is handy for that. The other approach is to programmatically bind a socket to port 0, check what port number was assigned, close the socket, and then use that port number for starting the actual server - a port collision should be unlikely.

P.S. Docker by default binds to network interface 0.0.0.0 and it [bypasses the firewall](https://github.com/moby/moby/issues/22054), so your development servers will be *publicly accessible even if your firewall is configured to block all incoming connections*. Always bind explicitly to 127.0.0.1 when publishing container ports to the host.


### Time

Time is a global variable which is ever changing (hopefully monotonically increasing).

TODO


### Concurrency

The order of memory reads and writes between parallel threads, and the operating system's context switching, are unpredictable global variables.

TODO: latches, no sleeps, inspect ruthlessly


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

Read more:
https://www.henricodolfing.com/2018/04/start-your-project-with-walking-skeleton.html


## Continuous delivery

TODO


# Exercise 3: Testing legacy code

Clone the project <https://github.com/emilybache/GildedRose-Refactoring-Kata>. Take one of the JavaScript editions of that project and configure it to use the [Stryker mutation testing framework](https://stryker-mutator.io/).

The assignment is to *write tests for the above code, until it has 100% mutation test coverage.*

TODO: simplify the project, preconfigure stryker

**Course artifact:** Git repository with the source code and history of you doing this exercise. Also include a file with the mutation test results.


# Exercise 4: Full-stack web app

The assignment is to *write a To-Do List app using TDD.* It needs to have a web user interface, an API backend and a database.

Only a few basic features are needed: adding to-do items, renaming to-do items, marking to-to items completed. A to-do list doesn't have much business logic, so write at least comprehensive validation rules. Authentication is not needed.

Start the app's development using the [walking skeleton](#walking-skeleton) approach. Focus on writing tests on every level of the stack:

- unit tests to cover as much of the code as is possible to unit test
- also unit test the user interface components (visual testing is optional)
- focused integration tests for the database and API layers
- one (1) end-to-end test against a fully deployed application (e.g. Docker containers running locally) to make sure that things are connected correctly (start with this - see [walking skeleton](#walking-skeleton))

TODO: project template, some example tests, docker-compose.yaml

**Course artifact:** Git repository with the source code and history of you doing this exercise.

---

Proceed to [Part 4: To infinity and beyond](/4-afterword)
