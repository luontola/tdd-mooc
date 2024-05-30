---
path: "/3-challenges"
title: "Chapter 3: The Untestables"
hidden: false
information_page: true
sidebar_priority: 3000
---

There are things which make testing more challenging. Many of them are global variables of sorts. Global variables cause spooky action at a distance. Do the same thing over and over again, and get a different result. Tests may randomly pass or fail, depending on the order in which they are run.


## How to make any code testable

In general there are two options, the first one being simpler:

* **Pass in the result of the untestable thing as a parameter.**

<div style="columns: 2">
<div>

Before
```
fn():
  ...
  ☠️☠️☠️
  ...
```

</div>
<div style="break-before: column">

After
```
fn(something):
  ...
  ...
```

</div>
</div>

- **Extract the untestable thing to a method and override it in a subclass.** Or in a language with first-class functions, extract it to a function and pass in the untestable function as a parameter.

<div style="columns: 2">
<div>

Before
```
fn():
  ...
  ☠️☠️☠️
  ...
```

</div>
<div style="break-before: column">

After
```
fn():
  ...
  something()
  ...
  
something():
  ☠️☠️☠️
```

</div>
</div>

Tests can then replace the untestable thing with a value object or a test double. 


## Test doubles

If the SUT (system under test) is not a pure function and it's hard to test together with the real objects, its dependencies can be replaced with [test doubles](https://martinfowler.com/bliki/TestDouble.html). The dependencies can be provided as method or constructor arguments (aka dependency injection).

There are five main categories of test doubles:

- **Dummy** is a placeholder to make the code compile, but doesn't affect the SUT.
- **Stub** returns data to the SUT e.g. using hard-coded method return values.
- **Spy** records how the SUT calls the spy, so that the test can afterwards assert on the recorded data.
- **Mock** contains pre-recorded expectations on how the SUT should call it, and will itself automatically verify the expectations. Requires a mocking framework.
- **Fake** is a simplified implementation of a dependency, not appropriate for production use, e.g. persistence layer based on a hashmap.

Only mock types you own.

<recommended-reading>

- [Test Doubles: Dummy, Stub, Spy, Mock & Fake](https://archive.ph/2yx7C)
- [How I learned to love mocks](https://medium.com/@xpmatteo/how-i-learned-to-love-mocks-1-fb341b71328)

</recommended-reading>


### London school of TDD

Mock objects were invented in a London meetup, and it gave birth to a mock-based outside-in approach to TDD, which is commonly called London style TDD. This is in contrast to Detroit/Chicago style TDD, where the code is typically written bottom-up and dependencies are faked only when they complicate testing (named such because Chrysler's C3 project, which gave birth to Extreme Programming, happened in Detroit). They are also known as mockist and classicist styles.

London style TDD focuses on the communication protocols between objects sending messages to each other. It goes hand-in-hand [[1]](https://groups.google.com/g/growing-object-oriented-software/c/-t6fp3392oM/m/QO342CHAAwAJ) with the [original vision of object-oriented programming](https://www.purl.org/stefan_ram/pub/doc_kay_oop_en), by [Alan Kay](https://en.wikipedia.org/wiki/Alan_Kay), where objects are like individual computers on the network sending messages to each other (in which sense Erlang is the most object-oriented programming language).

When using mock objects, it's important to understand the object-oriented style for which they were created. Otherwise, over-mocking may lead to tight coupling between tests and implementation details. The best description of how mock objects were meant to be used is the book *Growing Object-Oriented Software, Guided by Tests* (Steve Freeman, Nat Pryce 2009).

<recommended-reading>

- [A Brief History of Mock Objects](https://web.archive.org/web/20230318070013/http://www.mockobjects.com/2009/09/brief-history-of-mock-objects.html)
- [Growing Object-Oriented Software, Guided by Tests](https://www.amazon.com/Growing-Object-Oriented-Software-Guided-Tests/dp/0321503627) - how the inventors of mock objects use them

</recommended-reading>


## Singletons

[Singleton](https://en.wikipedia.org/wiki/Singleton_pattern) is an anti-pattern. It is the object-oriented equivalent of a global variable. Instead, [just create one](http://www.butunclebob.com/ArticleS.UncleBob.SingletonVsJustCreateOne).

<recommended-reading>

- [Singleton vs. Just Create One](http://www.butunclebob.com/ArticleS.UncleBob.SingletonVsJustCreateOne)

</recommended-reading>


## File system

The file system is a global variable which persists between test executions.

Firstly, decouple as much code from the file system as possible. If you have code which reads data from a file and then does something with the data, separate it into two functions that can be run in isolation: one which does the file reading, and another which does the data processing.

Sometimes people have test data in files. It's better to have all data related to a test in the same file as the test (and preferably inline inside the test method). Otherwise, you'll need to jump between two files, which makes understanding the test harder.

After you have decoupled file handling and business logic, the file handling will still need to be tested. Files are unavoidable then (don't try mocking the file system).

If a test needs to read from the disk, you can create small example files and commit them into version control. The tests can then read those test resources.

If a test needs to write to the disk, create a unique temporary directory on test setup and delete it recursively on teardown.

Note: If the test process is killed or there are file locks, the teardown may not be able to delete the temporary directory. Avoid using `/tmp` and instead place the temporary directory inside the project directory, under the build target directory, so that any stale directories will be removed on a clean build.


## Database

The database is a global variable which persists between test executions.

Make it easy to run the tests locally. [Docker Compose](https://docs.docker.com/compose/) makes it easy to start up a database without needing to install it. For cloud-only databases, using a development instance in the cloud is necessary. If more than one person (or process) uses the same database, then care must be taken to isolate the tests from parallel test runs.

Usually tests create the database schema on test setup, and remove it on teardown. Another style is to remove and recreate the schema on test setup, which makes it the responsibility of the next test to clean up what the previous test produced. Clean-before allows peeking the data after a test run, but you would still need to focus run a single test, so commenting out the teardown or using a sleep gives the same effect with the clean-after approach. (Mom always told to clean up after yourself.)

In focused integration tests, it may be possible to run each test in a rollback-only transaction. This should make tests faster by avoiding the need to recreate the database schema for each test. If more than one thread is involved or the SUT is complex, this strategy is usually not possible.

Tests may create their own test data, or there may be a shared set of test data in the database. The former makes tests more understandable and decoupled from each other. The latter can be used for also testing database migrations.

The test schema name may be hard-coded or unique. Unique names for each test make it possible to run tests in parallel. If the test process is killed, test teardown is never executed, so the tests should automatically remove stale test schemas (especially if using a shared long-running database instead of a local container/VM), or you will eventually learn the database's soft and hard limits.

*Never run tests against a production database.* One safeguard is for the tests to only connect to a database whose name starts with "test".

Database tests can be made faster by [disabling fsync or using a RAM disk](https://pythonspeed.com/articles/faster-db-tests/).


### Dead ends

You *could* replace the database with an in-memory fake implementation for tests (e.g. hashmap). It will make the tests faster, but will require maintaining two parallel implementations - the real and the fake persistence layer. It works in simple cases, but gets harder the more database code there is. Even when using [contract tests](https://blog.thecodewhisperer.com/permalink/getting-started-with-contract-tests) to make the implementations functionally equivalent, they will be leaky abstractions with non-obvious differences (transactions, foreign key constraints etc.). It's better to decouple business logic from persistence: you won't need to fake dependencies if you have no dependencies.[¹](https://imgflip.com/i/5kxd8x)

Some people use an embedded in-memory database in tests and a different database in production, for example HSQLDB vs PostgreSQL. This is a road to madness. Even if SQL is a standard,[²](https://xkcd.com/927/) each implementation is different, so you will anyways need to run the tests against both databases. It might avoid having to install a database and the data will be removed after the test process exits, but nowadays `docker compose up -d db` is easy and even with an in-memory database you will need to handle isolation between test cases. Speed is not an argument either; a PostgreSQL which is already running is faster than a HSQLDB that needs to start every test run, not to speak of runtime performance. Most importantly, you would be limited to a subset of SQL that works on both databases, or you will need to maintain alternative versions of the queries; you would miss out on useful database-specific features such as [triggers/stored procedures](https://www.postgresql.org/docs/13/plpgsql-trigger.html) and [range types](https://medium.com/pyankit/postgres-range-types-are-dope-ca18923f3d46). Summa summarum, use the same technology in tests as in production.

You just saved 5+ years of experimenting.


## Network sockets

Network socket port numbers are a global variable at the operating system level.

If using a shared [continuous integration](https://martinfowler.com/articles/continuousIntegration.html) server, there can be many builds running on the same machine and they compete for the same port numbers. Even when running tests locally, you will typically have an application instance for manual testing running at the same time as the automated tests. The local development instance may use hard-coded ports, but the tests should allocate a random free port for the database and web server.

Most servers you can bind to listen on port 0 and the operating system will assign it an unused port number. After the server has started, you can find out what port was assigned to it and use that in the tests. [`docker compose port`](https://docs.docker.com/compose/reference/port/) is handy for that. The other approach is to programmatically bind a socket to port 0, check what port number was assigned, close the socket, and then use that port number for starting the actual server - a port collision should be unlikely.

P.S. Docker by default binds to network interface 0.0.0.0 and it [bypasses the firewall](https://github.com/moby/moby/issues/22054), so your development servers will be *publicly accessible even if your firewall is configured to block all incoming connections*. Always bind explicitly to 127.0.0.1 when publishing container ports to the host.


## Time

Time is a global variable which is ever changing (hopefully monotonically increasing).

Code which reads the current time (e.g. using `new Date()`) is inherently untestable. Instead, pass in the current time as a method parameter, or inject a [clock](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/Clock.html) which can be replaced with a [fake clock](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/Clock.html#fixed(java.time.Instant,java.time.ZoneId)) in tests.


## Concurrency

The order of memory reads and writes between parallel threads, and the operating system's context switching, are unpredictable global variables.

*If a test fails randomly, don't ignore it as an outlier.* The code *has* a concurrency bug, in the production or test code. Save any stack traces and logs of the failure, and *inspect the code ruthlessly*, until you know why it failed. It's important to know the [memory model](https://en.wikipedia.org/wiki/Memory_model_(programming)) of the programming language and the CPU.

Minimize the amount of code that needs to be thread-safe. Use [concurrency abstractions](https://en.wikipedia.org/wiki/Concurrency_pattern) which allow most of the code to be single-threaded. [Immutability](https://en.wikipedia.org/wiki/Immutable_object) makes the code easier to reason about, also in single-threaded code.

Don't use `sleep()` in tests. The sleep time is either too long, making the tests slower, or too short, making them [flaky](https://semaphoreci.com/community/tutorials/how-to-deal-with-and-eliminate-flaky-tests) (i.e. they fail randomly). Instead, react to events or use polling.

Concurrency artifacts such as [CountDownLatch](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/CountDownLatch.html) and [CyclicBarrier](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/CyclicBarrier.html) are useful for unit testing concurrent code. With them you can make thread 1 wait at point A, until thread 2 has arrived at point B.

[Testing cannot prove](https://www.goodreads.com/quotes/506689-program-testing-can-be-used-to-show-the-presence-of) that code is thread-safe, but together with code review, you can get quite far by writing a test which executes lots of tasks in parallel and then asserts invariants about what the tasks did. For example, each write happened exactly once, each task saw a consistent view of the state, tasks could read their own writes, and so on.

Always have a timeout for asynchronous tests, in case the code gets stuck in an infinite loop or deadlock or doesn't send some event. The timeout needs to be long enough to not be triggered randomly when the computer is overloaded, but short enough that you don't need to wait long for the tests to fail, especially if the wait time is `NumberOfTests * Timeout`.

<recommended-reading>

- [How to Find and Eliminate Flaky Tests](https://semaphoreci.com/community/tutorials/how-to-deal-with-and-eliminate-flaky-tests)

</recommended-reading>


## Randomness

It's desirable for tests to pass or fail reliably. But what if the code being tested is meant to have randomness? If you can't anymore assert exact values, you will need to approach it like [property-based testing](https://increment.com/testing/in-praise-of-property-based-testing/) and assert *invariants*.

For example, let's test a function which returns random integers between 1 and 10. You can call it lots of times and check that all values are within the range 1 to 10. You may also check that, with a sufficiently large sample size, each of the integers between 1 and 10 is returned at least once. You may also check that the values are returned in unpredictable order: build a few lists of same length from the return values, and check that the list contents are different. Depending on the domain, there might be other restrictions as well. For example, when dealing cards from a deck of cards, each card appears exactly once.

But even if you assert that the random values are not predictable, once in a blue moon the values could be returned in seemingly predictable order[¹](https://web.archive.org/web/20230304094645/https://dilbert.com/strip/2001-10-25) and your tests would fail. To improve repeatability, you could always use the same seed for the pseudorandom number generator. Or better yet, choose randomly from a couple different hard-coded seeds, so that the tests cannot be coupled to any single predictable random order.

(Testing whether something is true randomness is outside the scope of this course. That's in the realm of mathematics and not TDD.)


## User interface

Tests should be sensitive to behavior changes and insensitive to structure changes. This is even more important in the user interface. Changing the visual style or layout of the UI, should not break behavioral tests.

There are patterns like [passive view](https://martinfowler.com/eaaDev/PassiveScreen.html) which try to separate the logic and visuals of the UI, to make the logic more testable. With the advent of [React](https://reactjs.org/), UI components can be written as stateless functions, which makes testing them easier.


### Unit testing web app components

Asserting on the [innerText](https://developer.mozilla.org/en-US/docs/Web/API/HTMLElement/innerText) of a component (after whitespace normalization) produces tests which are decoupled from visual changes.

Asserting the presence/absence of a CSS class is useful for testing logic that is observable only visually. Make sure to use the same constant for presence and absence checks; a mispelled <!-- sic --> CSS class is always absent.


### End-to-end testing web apps

Don't click buttons directly in test code. Create an automation layer of high-level operations and call those. The tests should focus on *what* the system does, and the automation layer on *how* the system does it. That way when the UI changed, only the automation layer needs to be updated, instead of fixing all tests individually.

Prefer selecting elements based on the visible text on the button/link/label; it makes the tests easier to read. But don't be afraid to add extra IDs, classes and [data attributes](https://developer.mozilla.org/en-US/docs/Learn/HTML/Howto/Use_data_attributes) to simplify testing.

Have only a few end-to-end tests. They are slow and flaky. Prefer unit tests. Set a hard limit for how many end-to-end tests the whole application may have (≤10 for even big apps) and stick to it. End-to-end tests should only check that things are wired together, not behavioral correctness. Overreliance on end-to-end tests can [grind development to a halt](https://building.nubank.com.br/why-we-killed-our-end-to-end-test-suite/).

<recommended-reading>

- [Integrated Tests Are A Scam](https://blog.thecodewhisperer.com/permalink/integrated-tests-are-a-scam)

</recommended-reading>


### Visual testing

It's hard to write an assertion that something looks good. But for a human it's easy to check it visually, and the computer can compare whether the pixels have changed since the last approval.

There are tools like [Storybook](https://storybook.js.org/) for rendering UI components in various states, and it's possible to [take a screenshot](https://storybook.js.org/docs/react/workflows/visual-testing) of the result and check whether it has changed.

Optimize the diff for humans. Even video and audio can be diffed as an image.

<recommended-reading>

- [You're Testing WHAT?](https://www.youtube.com/watch?v=5_IW7npQk9k)

</recommended-reading>

---

Proceed to [Chapter 4: Legacy code](/4-legacy-code) or [Exercises](/exercises)
