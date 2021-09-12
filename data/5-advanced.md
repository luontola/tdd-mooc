---
path: "/5-advanced"
title: "Part 5: Advanced techniques"
hidden: false
information_page: true
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


# Exercise 5: Full-stack web app

You may clone the project template <https://github.com/luontola/tdd-mooc-webapp> to get started, but fundamentally the tool choices are up to you.

The assignment is to *write a To-Do List app using TDD.* It needs to have a [SPA](https://developer.mozilla.org/en-US/docs/Glossary/SPA) web user interface, an API backend and a database.

Only a few basic features are needed: add a to-do item, rename a to-do item, mark a to-to item completed, archive all completed to-do items. Authentication is not needed.

Start the app's development using the [walking skeleton](#walking-skeleton) approach. Focus on writing tests on every level of the stack:

- unit tests to cover as much of the code as is possible to unit test
- also unit test the user interface components ([visual testing](#visual-testing) is optional)
- focused integration tests for the database and API layers
- one (1) end-to-end test against a fully deployed application (e.g. Docker containers running locally) to make sure that things are connected correctly (start with this - see [walking skeleton](#walking-skeleton))

##### What to submit

* Submit to https://studies.cs.helsinki.fi/stats/courses/tdd-pilot a **Git repository** with the source code and history of you doing this exercise.
* Fill [this questionnaire](https://forms.gle/rQpKzRaFJJdb3ZkK9). Note the need for more detailed time tracking.

---

Proceed to [Part 6: To infinity and beyond](/6-afterword)
