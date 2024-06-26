---
path: "/1-tdd"
title: "Chapter 1: What is TDD"
hidden: false
information_page: true
sidebar_priority: 3000
---

The philosophy of this course material is that if something has been written online, there is no need to repeat it here. Instead, we just link to it. The _recommended reading_ links contain additional information which makes the summaries in this course material more understandable. The inline links, on the other hand, just explain terms and provide sources.


## History

Sometime in the 1970s, the 12-year-old [Kent Beck](https://en.wikipedia.org/wiki/Kent_Beck) [[1]](https://www.amazon.com/Test-Driven-Development-Kent-Beck/dp/0321146530) was reading books that his father had brought home. One of the books said that this is how you write programs: *You take the input tape, look at its contents, and type manually the output tape that you expect to create. Then programming is the process of writing a program until the actual output tape matches the expected output tape.*

Some things that should not have been forgotten were lost. And for two decades, the technique passed out of all knowledge. Until, when chance came, it was rediscovered.

Fast forward to 1990s. Kent Beck had followed in his father's footsteps and was now a software consultant. He had written the first unit testing framework in the xUnit family, SUnit for Smalltalk. It was then that Kent remembered the book he had read as a kid. *"If I took this type-the-output-tape-first schema seriously, then I would write the test before I had the code."*

He thought the idea was stupid, so he had to try it.

Kent tried it with a stack, and half an hour later he was hooked. All the anxieties he had felt while programming, which had been getting worse as experience accrued, just dropped away. *"I'm not done until all the tests that I can imagine are all passing. But I don't have to make them all pass at once. I can just type one in and then make it work. And type the next one and make that work. Eventually... can I think of any other tests? No. I must be done."* He was completely relaxed.
[[2]](https://youtu.be/tM1iOJsR7p4?t=2080)
[[3]](https://youtu.be/cGuTmOUdFbo?t=325)


## What is TDD

[A study from 2017](http://dx.doi.org/10.1109/TSE.2017.2776152) found that only 12% of developers who claimed to do TDD, did actually follow it.  Since there is such [semantic diffusion](https://martinfowler.com/bliki/SemanticDiffusion.html) about TDD, let's start with a definition:

1. Write a list of the test scenarios you want to cover
2. Turn exactly one item on the list into an actual, concrete, runnable test
3. Change the code to make the test (& all previous tests) pass (adding items to the list as you discover them)
4. Optionally refactor to improve the implementation design
5. Until the list is empty, go back to #2

<recommended-reading>

- [Canon TDD](https://tidyfirst.substack.com/p/canon-tdd) - a definition of TDD from the inventor of TDD, and a list of typical mistakes

</recommended-reading>

### Three laws of TDD

TDD can also be defined in terms of these three rules:

1. You shall not write any production code, unless required by a failing unit test.
2. You shall not write more of a unit test, than is required to fail (assertion failure/program crash/compile error).
3. You shall not write more production code, than is sufficient to make the one failing unit test pass.

<recommended-reading>

- [The Three Laws of TDD](http://www.butunclebob.com/ArticleS.UncleBob.TheThreeRulesOfTdd) - a definition of TDD

</recommended-reading>


### Red, Green, Refactor

Another way to describe TDD is the following cycle of three phases. Every few minutes, in order:

1. **Red**: Write a failing test. Predict (mentally or out loud) how the test should fail. Run the test. See it fail. If it failed in an unexpected way, check your assumptions. Improve the failure's readability.
2. **Green**: Make the test pass. ASAP. Commit any sins necessary. Hard-coded values. If statements. Fake it until you make it.
3. **Refactor**: Improve the design of the code, without changing its behavior. Atone for your sins. Remove duplication. Improve names. Minimal code which passes the current tests. Make it look as if you knew all along what you were doing.

Repeat until there are no more tests to write or until bored.

If the current design makes it difficult to make a new test pass, consider commenting out the new test and refactoring first. "Make the change easy (warning: this may be hard), then make the easy change." [[1]](https://twitter.com/KentBeck/status/250733358307500032)

<recommended-reading>

- [Red-Green-Refactor](http://www.jamesshore.com/v2/blog/2005/red-green-refactor) - a definition of TDD
- [The Cycles of TDD](https://blog.cleancoder.com/uncle-bob/2014/12/17/TheCyclesOfTDD.html) - the various iterative cycles of TDD, from the second-by-second up to the hour-by-hour scale
- [TDD Process Smells](https://agileinaflash.blogspot.com/2009/03/tdd-process-smells.html) - common ways of doing TDD wrong

</recommended-reading>


#### Why run a test, when you know it'll fail?

A key part of the _red_ phase is to **run the new test and see it fail**. That's where the name of the phase comes from; test runners show failing tests typically in red.

A second before running the test, you should predict how the test will fail. If the test fails differently from how you expected, you should stop for a moment and think. Is the code working differently from how you thought? Is the test not actually testing the thing it was meant to test? Likewise, if the test passed when you expected it to fail. Is the feature already implemented? Does the test have a bug, and it'll never fail?

This is also a good opportunity to improve the readability of the failure message. That will make investigating future test failures easier.

The red phase is an answer to the age-old internet debate of "but who will test the tests?" Good tests fail when the production code has a problem. Running a test before the production code has been implemented is the least you can do to ensure the test's correctness.


#### Triangulation

"As the tests get more specific, the code gets more generic." [[1]](https://thecleancoder.blogspot.com/2010/11/craftsman-63-specifics-and-generics.html)

In the _green_ phase, it's important to not write any more code than is required to pass the test. This means at first using hard-coded return values and naive implementations. If we added behavior without first specifying it with a test, then we would be adding unspecified behavior.

It is called _triangulation_ when we write tests to expose the deficiencies of still naive production code. By making those tests pass, the production code approaches a completely tested solution which will handle all edge cases. 

<recommended-reading>

- [Why Write the Minimum Code to Pass the Test?](https://codingitwrong.com/2020/12/23/why-write-the-minimum-code-to-pass-the-test.html) - on the importance of implementing features in small iterative steps
- [Getting Stuck While Doing TDD. Part 3: Triangulation to the Rescue!](https://www.tddfellow.com/blog/2016/08/31/getting-stuck-while-doing-tdd-part-3-triangulation-to-the-rescue/) - what is triangulation and an example of doing it
- [TDD Guided by ZOMBIES](http://blog.wingman-sw.com/tdd-guided-by-zombies) - an acronym about in which order to implement things and what to consider

</recommended-reading>


#### Test list

To stay focused on the current test and phase, it's helpful to maintain a [test list](https://twitter.com/ursenzler/status/1433096612088356866/photo/2) (on paper, a text file or as TODO comments in tests).

Whenever you come up with an idea for a new test (e.g. a new feature or an edge case that needs to be covered), add it to your test list. Likewise for things that need refactoring. Then when you are done with the current feature, you can go through the list and handle them.


## What it looks like

In the following video, [Otavio Lemos](https://twitter.com/otaviolemos) demonstrates TDD using the [Bowling Game Kata](https://kata-log.rocks/bowling-game-kata). Some points to pay attention to:

- No more code is added than is required to pass the tests. At first the implementation is trivial.
- The code is refactored early and often.
- If the current design makes passing the next test hard, the test is commented out and the code is first refactored toward a better design.
- As more tests are added to cover specific edge cases, the code grows toward a generic algorithm which handles them all.

<iframe style="width: 100%; aspect-ratio: 560/315;"  src="https://www.youtube-nocookie.com/embed/1o_EbACQpQ8" title="Bowling Kata in TypeScript - YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>


## Direct and indirect effects of TDD

*Direct effects*, if just following the three laws of TDD:

- Guarantees code coverage
- Amplifies the pain caused by bad code

*Indirect effects*, if the programmer is skilled enough:

- Enables changing the code without breaking it
- Improves the quality of the code

<recommended-reading>

- [Direct and Indirect Effects of TDD](http://blog.orfjackal.net/2010/04/direct-and-indirect-effects-of-tdd.html) - how TDD works as a _design feedback mechanism_ and why pain is good

</recommended-reading>


## What tests to write?

Ask yourself: What is the next most important thing, which the system *does not* yet do? [[1]](https://dannorth.net/introducing-bdd/)

Stop thinking about _HOW your software works_. Instead, specify _WHAT your software does_ from the point of view of its user. [[2]](https://www.youtube.com/watch?v=gXh0iUt4TXA&t=380s)

When writing the first test, it helps to start small and simplify the problem: Where to start writing a sudoku solver? Solving a 1×1 sudoku. [[3]](https://www.infoq.com/presentations/responsive-design/)


### Test names should be sentences

The test names should be sentences which describe what the system should do - its behavior. Think of them as a specification of what your software does.

Thought exercise: All production and test code has disappeared. The only thing remaining is the test names. Can someone reimplement the system, so that it will do pretty much the same things as before?

Corollary: All test names have disappeared. Can someone read the test code and understand what behavior it is specifying, so that they can write a test name which says pretty much the same as it said before?

When a test fails, look at the name of the test - the behavior that it defines. There are three possibilities:

- The system has a bug. Fix the implementation.
- The behavior is still needed, but the test needs updating. Change the test. Think of how to decouple the test from unrelated behaviors, to have fewer unwanted test failures.
- The behavior is no more needed. Delete the test.

<recommended-reading>

- [Introducing BDD](https://dannorth.net/introducing-bdd/) - the original article about Behavior-Driven Development; on naming tests and how replacing the word _test_ with _behavior_ or _specification_ might help in learning what is _TDD done well_
- [An Ultimate Guide To BDD](https://www.youtube.com/watch?v=gXh0iUt4TXA)

</recommended-reading>


## Is TDD testing?

The tests written as part of TDD do help ensure a level of _basic correctness_ in the software, but it's still a _development_ technique - that's what the last D in TDD stands for. The role of the tests in TDD is to support building the software, whereas the role of tests in software testing is to break the software. Think of scaffolding instead of a wrecking ball.

TDD's tests can be thought of as a specification of the system's behavior, or as small examples of how the code works. In the software development life cycle, the TDD mindset focuses more on requirements and design than on verification, though the verification side is also important to support refactoring and adding new features.

<figure>
<img class="p-image--bordered" src="images/tdd-vs-testing.jpg" alt="Photos of scaffolding around a building and a wrecking ball destroying a brick wall.">
<figcaption>Photo credits: <a href="https://unsplash.com/photos/jAj0pjdzvic">Niklas Hamann</a>, <a href="https://www.flickr.com/photos/rhysasplundh/5202454842">Rhys Asplundh</a></figcaption>
</figure>


<recommended-reading>

- [Interlude: Basic Correctness](https://blog.thecodewhisperer.com/permalink/interlude-basic-correctness) - discussion between a TDD and a software testing expert

</recommended-reading>


---

Proceed to [Chapter 2: Refactoring and design](/2-design) or [Exercises](/exercises)
