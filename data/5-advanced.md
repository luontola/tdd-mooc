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

TODO

Read more:
http://www.jamesshore.com/v2/books/aoad2/continuous_deployment


## test && commit || revert (TCR)

TODO

Read more:
https://medium.com/@kentbeck_7670/test-commit-revert-870bbd756864


## Test-after patterns

TODO: spike and stabilize, ginger cake

Read more:
https://vimeo.com/24681032

---

Proceed to [Chapter 6: To infinity and beyond](/6-afterword) or [Exercises](/exercises)
