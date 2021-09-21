---
path: "/exercises"
title: "Exercises and schedule"
hidden: false
information_page: true
sidebar_priority: 3001
---

In this course, 99% of the learning happens when doing the exercises. The prose chapters are there for support and to move things from "unknown unknowns" to "known unknowns". [[1]](https://en.wikipedia.org/wiki/There_are_known_knowns)

This is the suggested order of completing the material and exercises. You may use the checkboxes to keep track of your progress.

TODO: save checkboxes in local storage, strikethrough done rows

|    | What to read and do | Duration | Done |
|----|---------------------|----------|------|
| 📖 | [Chapter 1: What is TDD](/1-tdd) | 30 min | <input type="checkbox"> |
| 🧑‍💻 | [Exercise 1: Tetris](#exercise-1-tetris), levels 1-3 | 3h | <input type="checkbox"> |
| 📖 | [Chapter 2: Refactoring and design](/2-design) | 15 min | <input type="checkbox"> |
| 🧑‍💻 | [Exercise 2: Small, safe steps](#exercise-2-small-safe-steps) | 2 h | <input type="checkbox"> |
| 🧑‍💻 | [Exercise 1: Tetris](#exercise-1-tetris), levels 4-10 | 8 h | <input type="checkbox"> |
| 📖 | [Chapter 3: The Untestables](/3-challenges) | 30 min | <input type="checkbox"> |
| 🧑‍💻 | [Exercise 3: Untestable code](#exercise-3-untestable-code) | 2 h | <input type="checkbox"> |
| 📖 | [Chapter 4: Legacy code](/4-legacy-code) | 15 min | <input type="checkbox"> |
| 🧑‍💻 | [Exercise 4: Legacy code](#exercise-4-legacy-code) | 2 h | <input type="checkbox"> |
| 📖 | [Chapter 5: Advanced techniques](/5-advanced) | 15 min | <input type="checkbox"> |
| 🧑‍💻 | [Exercise 5: Full-stack web app](#exercise-5-full-stack-web-app) | 20 h | <input type="checkbox"> |
| 🧑‍💻 | [Exercise 6: Conway's Game of Life](#exercise-6-conways-game-of-life) | 5 h | <input type="checkbox"> |
| 📖 | [Chapter 6: To infinity and beyond](/6-afterword) | 5 min | <input type="checkbox"> |


TODO: store the decision in local storage, avoid having to click the checkbox every time when entering this page

<tdd-decision>

# Exercise 1: Tetris

Clone the project <https://github.com/luontola/tdd-mooc-tetris> and follow its instructions.

The assignment is to *write a Tetris game using TDD.* The first couple dozen tests have already been written for you, so that it'll be easier to get into the rhythm of writing tests first.

If you get stuck, you may watch [this example screencast](https://www.youtube.com/playlist?list=PLSADDT9dzgRCjVvS13ekPr1KwX_JkDQJM) for inspiration. It's based on an older version of the exercise, so it's in Java instead of JavaScript, but otherwise it's pretty much the same.

##### What to submit

* Submit to https://studies.cs.helsinki.fi/stats/courses/tdd-pilot a **Git repository** with the source code and history of you doing this exercise.
* Fill [this questionnaire](https://forms.gle/XCmGN4ZDvtdtGbLG6). Note the need for more detailed time tracking.

##### Effort estimate

The exercise is split into 10 levels:
* Level 1 ≈ 1h
* Level 2 ≈ 1h
* Level 3 ≈ 1h
* Between 3 and 4 is a good time to do [exercise 2](/2-design#exercise-2-small-safe-steps). Refactoring in small steps makes level 4 easier.
* Level 4 ≈ 2h
* Level 5 ≈ 1h?
* Level 6 ≈ 1h?
* Level 7 ≈ 1h?
* Level 8 ≈ 1h?
* Level 9 ≈ 1h?
* Level 10 ≈ 1h?


# Exercise 2: Small, safe steps

Clone the project <https://github.com/luontola/tdd-mooc-small-steps> and follow its instructions.

The assignment is to *refactor the above code to replace [Date](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Date) class with [Temporal.PlainDate](https://tc39.es/proposal-temporal/docs/plaindate.html) class.* Repeat this refactoring many times. Focus on doing as small changes as possible, so that *all tests will pass between every change*. Practise smaller and smaller steps, until you can **do the whole refactoring by _changing only one line at a time_**.

Afterwards we will have a competition between the students of this course about doing this refactoring using the smallest possible steps.

TODO: tool to automatically check the size of steps and that the tests pass on every step, maybe as TCR and by default disabled test https://mochajs.org/#inclusive-tests `this.skip()`, add retries for flaky tests https://mochajs.org/#retry-tests

##### What to submit

* **Perform this refactoring live** in the weekly exercise groups.
* Submit to https://studies.cs.helsinki.fi/stats/courses/tdd-pilot a **Git repository** with the history of you doing this refactoring, so that the commits are **as small as possible** and all the tests **pass on every commit**.
* Fill [this questionnaire](https://forms.gle/bN6YkZEssTTewBxq8).

##### Effort estimate

* 1-2 hours. Doing the refactoring once takes ~10 minutes, but you should repeat it many times and practice different approaches.


# Exercise 3: Untestable code

Write a function which is as hard to test as possible. Then write tests for it.

##### What to submit

* Submit to https://studies.cs.helsinki.fi/stats/courses/tdd-pilot a **Git repository** with the source code and history of you doing this exercise.
* Fill [this questionnaire](https://forms.gle/27xgUm3WH4skKUj18).

##### Effort estimate

* 1-2 hours?


# Exercise 4: Legacy code

Clone the project <https://github.com/emilybache/GildedRose-Refactoring-Kata>. Take one of the JavaScript editions of that project and configure it to use the [Stryker mutation testing framework](https://stryker-mutator.io/). Also install a basic code coverage tool which calculates line coverage.

The assignment is to *write tests for the above code, until it has 100% mutation test coverage.*

TODO: simplify the project, preconfigure stryker and some line coverage tool

##### What to submit

* Submit to https://studies.cs.helsinki.fi/stats/courses/tdd-pilot a **Git repository** with the source code and history of you doing this exercise. Also include a file with the **mutation test results**.
* Fill [this questionnaire](https://forms.gle/Z11PazAmaWyTXvX8A).

##### Effort estimate

* 1-2 hours?


# Exercise 5: Full-stack web app

You may clone the project template <https://github.com/luontola/tdd-mooc-webapp> to get started, but fundamentally the tool choices are up to you.

The assignment is to *write a To-Do List app using TDD.* It needs to have a [SPA](https://developer.mozilla.org/en-US/docs/Glossary/SPA) web user interface, an API backend and a database.

Only a few basic features are needed: add a to-do item, rename a to-do item, mark a to-do item completed, archive all completed to-do items. Authentication is not needed.

Start the app's development using the [walking skeleton](#walking-skeleton) approach. Focus on writing tests on every level of the stack:

- unit tests to cover as much of the code as is possible to unit test
- also unit test the user interface components ([visual testing](#visual-testing) is optional)
- focused integration tests for the database and API layers
- one (1) end-to-end test against a fully deployed application (e.g. Docker containers running locally) to make sure that things are connected correctly (start with this - see [walking skeleton](#walking-skeleton))

##### What to submit

* Submit to https://studies.cs.helsinki.fi/stats/courses/tdd-pilot a **Git repository** with the source code and history of you doing this exercise.
* Fill [this questionnaire](https://forms.gle/rQpKzRaFJJdb3ZkK9). Note the need for more detailed time tracking.

##### Effort estimate

* 10-20 hours? Most of the effort is in setting up the infrastructure, and that becomes the faster the more old projects you have from where to copy-paste snippets.


# Exercise 6: Conway's Game of Life

The assignment is to *write [Conway's Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life) using TDD.* Write it as a command line application which takes as input (1) a pattern file in [RLE format](https://www.conwaylife.com/wiki/Run_Length_Encoded) and (2) the number of iterations to simulate, and then outputs the resulting pattern in RLE format after the specified number of iterations.

TODO: example RLE files for test data, link to OBS screen recording instructions

##### What to submit

* **Record a video** of you doing this exercise. Use a screen recording software (e.g. [OBS Studio](https://obsproject.com/), see [instructions](https://www.alphr.com/record-screen-obs/)). Upload the video to a streaming service (e.g. unlisted video on YouTube) and include a **link to the video** in the comment field as you submit the Git repository.
* Submit to https://studies.cs.helsinki.fi/stats/courses/tdd-pilot a **Git repository** with the source code and history of you doing this exercise.
* Fill [this questionnaire](https://forms.gle/SyocAsQksBq5GUNk6). Note the need for more detailed time tracking.

##### Effort estimate

* 3-5 hours?

</tdd-decision>

---

Proceed to [Chapter 1: What is TDD](/1-tdd)
