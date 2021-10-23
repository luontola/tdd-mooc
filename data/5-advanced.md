---
path: "/5-advanced"
title: "Chapter 5: Advanced techniques"
hidden: false
information_page: true
sidebar_priority: 3000
---


## Walking skeleton

Getting all the various technologies working together in a new project is a big job. Unfamiliar technologies often require experimenting and fiddling. If you don't know how something should be used, it's hard to write a test for it.

The walking skeleton approach can help in this situation. The idea is to start with one end-to-end test against a system which is deployed to production(-like) environment. Then develop just the bare bones of the system, so that it'll contain the main architectural components.

At start the test could just send a "hello world" message which goes through the frontend, backend, database and back again to the frontend. Focus on just getting the end-to-end test passing and putting the architectural components together. Focused unit and integration tests are best added *after* you have created the architecture, when there are less unknowns.

Then when the end-to-end test passes through the whole architecture, you can start fleshing out the system. As the system grows, keep improving the end-to-end test along with it.

Read more:
https://www.henricodolfing.com/2018/04/start-your-project-with-walking-skeleton.html


## Continuous delivery

[*Continuous Integration*](https://martinfowler.com/articles/continuousIntegration.html) (CI) is a practice where all team members merge their code changes, many times per day, into the same main branch in version control. Changes to the main branch in turn trigger building the project and running all tests on a shared computer. This helps reduce the risk and magnitude of merge problems, and ensures that the code works on more than the original programmer's machine.

Continuous Integration started as an [Extreme Programming](https://martinfowler.com/bliki/ExtremeProgramming.html) (XP) practice in 1997. According to the XP principle of *taking what works and turning it to eleven*, in 2010 [*Continuous Delivery*](https://martinfowler.com/bliki/ContinuousDelivery.html) (CD) improved on CI by automating the last mile from build to deployment. Some big internet companies deploy to production normally hundreds of times per day. Because each deployment contains only a few new changes, it reduces the risk and magnitude of production failures. Most importantly, it enables faster feedback from real users.

A core tenet of Continuous Delivery is that *at a moment's notice, the current development version of the software can be deployed to production*, and nobody will even bat an eye. On top of good CI practices, CD requires comprehensive tests on multiple levels so that if all tests pass, you can trust that the software will work in production. Incomplete features must also be designed to be deployable to production, for example by hiding them with [feature toggles](https://martinfowler.com/articles/feature-toggles.html) until they are complete.

Continuous Delivery may include a human decision on *when* to deploy to production. If that decision is automated and every build which passes its tests is deployed to production automatically, then it's called *Continuous Deployment*.

Read more:
https://martinfowler.com/books/continuousDelivery.html


## Testing in production

When testing *before* production is topnotch and you practice Continuous Delivery, then it's possible to start testing *also* in production. This includes getting feedback from how the users are using the application, for example through [A/B testing](https://en.wikipedia.org/wiki/A/B_testing) and [conversion funnels](https://en.wikipedia.org/wiki/Purchase_funnel). Another key term is [observability](https://thenewstack.io/monitoring-and-observability-whats-the-difference-and-why-does-it-matter/) - making the internal states of the system visible.

Read more:
https://www.youtube.com/watch?v=z-3aSVfoyBY


## test && commit || revert (TCR)

TCR is this crazy idea that every time that the tests pass, the code is committed automatically, and if the tests fail, the changes are reverted automatically. Try it and see what comes out of it.

Read more:
https://medium.com/@kentbeck_7670/test-commit-revert-870bbd756864


## Test-after patterns

After you have practiced TDD for over 10 years, and writing tests and testable code is as easy as breathing, there may be times when writing tests *after* the code is faster.

Testing and clean code need to be ingrained in your nature, so that you will always come back to clean up the code and write tests for it. The end result should look indistinguishable from whether it was written test-first or test-last.

**Spike and stabilize:** Write some experimental code without tests, and if the experiment is successful, then write the tests afterwards and refactor the code up to production quality. This requires writing testable code instinctively, and intuitive knowledge that what tests would have been needed to produce the code, so that you can write the tests afterwards.

**Ginger cake:** The story behind the name is about a granny's recipe collection; among the recipes there was a recipe for ginger cake, which read just "like chocolate cake, but with ginger". As a software development pattern, this can be used when you need to build a new feature which is quite similar to something you've done before. Copy-paste the old code, remove unnecessary parts and customize it. The code needs to be small enough to fit in your head. Afterwards clean up the duplication between the old and new code.

Read more:
https://vimeo.com/24681032

---

Proceed to [Chapter 6: To infinity and beyond](/6-afterword) or [Exercises](/exercises)
