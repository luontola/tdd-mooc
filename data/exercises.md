---
path: "/exercises"
title: "Exercises"
hidden: false
information_page: true
sidebar_priority: 3001
---

In this course, 99% of the learning happens when doing the exercises. The prose chapters are there for support and to move things from "unknown unknowns" to "known unknowns".[¹](https://en.wikipedia.org/wiki/There_are_unknown_unknowns)

Below is the suggested order of reading the chapters and doing the exercises. You may use the checkboxes to keep track of your progress; they are saved only in your browser's local storage.

<exercise-schedule>
</exercise-schedule>

<tdd-decision>

# Exercise 1: Small, safe steps

> Here the goal is to learn fast feedback and making changes without breaking things. Both are essential skills for TDD.

Clone the project <https://github.com/luontola/tdd-mooc-small-steps> and follow its instructions.

The assignment is to *refactor the above code to replace [Date](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Date) class with [Temporal.PlainDate](https://tc39.es/proposal-temporal/docs/plaindate.html) class.* Repeat this refactoring many times. Focus on doing as small changes as possible, so that *all tests will pass between every change*. Practice smaller and smaller steps, until you can **do the whole refactoring by changing only _one or two lines at a time, in under 10 minutes_**.

In the study groups, we will have an informal competition between the students of this course about doing this refactoring using the smallest possible steps or as quickly as possible. This kind of mechanical refactorings, which don't require thinking, benefit from training your muscle memory and learning IDE shortcuts.

##### What to submit

* [Submit this exercise](/practicalities#exercise-submissions) as "part1". Provide a **Git repository** with the history of you doing this refactoring, so that the commits are **as small as possible** and all the tests **pass on every commit**.
    * Doing the [TCR challenge](https://github.com/luontola/tdd-mooc-small-steps#tcr-challenge) is the simplest way to create such small commits.
* Fill [this questionnaire](https://forms.gle/bN6YkZEssTTewBxq8).

##### Effort estimate

* <dfn data-title="...assuming a professional developer with good design skills">Minimum</dfn>: 1 hours
* <dfn data-title="...based on past student submissions">Average</dfn>: 3 hours
* Doing the refactoring once takes ~10 minutes, but you should repeat it many times and practice different approaches.


# Exercise 2: Tetris

> This is the primary exercise for learning TDD. It will teach writing tests first, working incrementally and evolutionary design.

Clone the project <https://github.com/luontola/tdd-mooc-tetris> and follow its instructions.

The assignment is to *write a Tetris game using TDD.* The first couple dozen tests have already been written for you, so that it'll be easier to get into the rhythm of writing tests first.

If you get stuck, you may watch [this example screencast](https://www.youtube.com/playlist?list=PLSADDT9dzgRCjVvS13ekPr1KwX_JkDQJM) for inspiration. It's based on an older version of the exercise, so it's in Java instead of JavaScript, but otherwise it's pretty much the same.

##### What to submit

* [Submit this exercise](/practicalities#exercise-submissions) as "part2". Provide a **Git repository** with the source code and history of you doing this exercise.
    * Submit it after doing all 10 levels. The exercise submission system doesn't support editing old submissions.
* Fill [this questionnaire](https://forms.gle/XCmGN4ZDvtdtGbLG6).

##### Effort estimate

* <dfn data-title="...assuming a professional developer with good design skills">Minimum</dfn>: 11 hours
* <dfn data-title="...based on past student submissions">Average</dfn>: 38 hours
* The exercise is split into 10 levels of roughly the same size, though level 4 is bigger due to the need for major refactoring.


# Exercise 3: Untestable code

> This exercise's goal is to teach why test-last is harder than test-first, and how to cope with things that make testing hard.

Clone the project <https://github.com/luontola/tdd-mooc-untestable-code> and follow its instructions.

The assignment is to *refactor the untestable code examples to make them testable, and then write tests for them.*

##### What to submit

* [Submit this exercise](/practicalities#exercise-submissions) as "part3". Provide a **Git repository** with the source code and history of you doing this exercise.
* Fill [this questionnaire](https://forms.gle/27xgUm3WH4skKUj18).

##### Effort estimate

* <dfn data-title="...assuming a professional developer with good design skills">Minimum</dfn>: 2 hours
* <dfn data-title="...based on past student submissions">Average</dfn>: 7 hours


# Exercise 4: Legacy code

> This exercise's goal is to learn writing tests for non-TDD'd legacy code, to make it safe to change the code.

Clone the project <https://github.com/luontola/tdd-mooc-legacy-code> and follow its instructions.

The assignment is to *write tests for the above code, until it has 100% mutation test coverage*, and then *refactor it and add a new feature*. See the project's readme for details.

##### What to submit

* [Submit this exercise](/practicalities#exercise-submissions) as "part4". Provide a **Git repository** with the source code and history of you doing this exercise. Also include a file with the **mutation test results**.
* Fill [this questionnaire](https://forms.gle/Z11PazAmaWyTXvX8A).

##### Effort estimate

* <dfn data-title="...assuming a professional developer with good design skills">Minimum</dfn>: 2 hours
* <dfn data-title="...based on past student submissions">Average</dfn>: 6 hours


# (optional) Exercise 5: Full-stack web app

> This exercise's goal is to learn writing tests for real applications. You will face all the things which commonly complicate testing in the real world: database, user interface, APIs.
>
> **If you decide to not do this exercise, [submit a blank response](/practicalities#exercise-submissions) as "part5": Leave the exercise checkbox unchecked, enter 0 or 1 hours as the duration, and write a dummy repository URL (it doesn't need to exist).**

You may clone the project template <https://github.com/luontola/tdd-mooc-webapp> to get started, but fundamentally the tool choices are up to you. Any programming language is okay.

The assignment is to *write a To-Do List app using TDD.* It needs to have a [SPA](https://developer.mozilla.org/en-US/docs/Glossary/SPA) web user interface, an API backend and a database.

Only a few basic features are needed: add a to-do item, rename a to-do item, mark a to-do item completed, archive all completed to-do items. Authentication is not needed.

Start the app's development using the [walking skeleton](/5-advanced#walking-skeleton) approach. Focus on writing tests on every level of the stack:

- use unit tests to cover as much of the code as is possible to unit test
- also unit test the user interface components ([visual testing](/3-challenges#visual-testing) is optional)
    - tests for the UI components should not depend on the API
- use focused integration tests for the database and API layers
    - tests for the API (request routing and validation) should not depend on the database
    - tests for the database should not depend on the API
- write only one end-to-end test which requires a fully deployed application (e.g. Docker containers running locally) to make sure that things are wired together correctly (start with this - see [walking skeleton](/5-advanced#walking-skeleton))

##### What to submit

* [Submit this exercise](/practicalities#exercise-submissions) as "part5". Provide a **Git repository** with the source code and history of you doing this exercise.
* Fill [this questionnaire](https://forms.gle/rQpKzRaFJJdb3ZkK9).

##### Effort estimate

* <dfn data-title="...assuming a professional developer with good design skills">Minimum</dfn>: 10 hours
* <dfn data-title="...based on past student submissions">Average</dfn>: 32 hours
* Much of the effort is in setting up the infrastructure, and that becomes the faster the more old full-stack projects you have from where to copy-paste snippets.


# Exercise 6: Conway's Game of Life

> This exercise's goal is to review what you have learned during this course.
>
> **Create a screen recording video which shows you programming this exercise. This is like this course's final exam to demonstrate that you can write code using TDD.**
>
> ⚠️ If you wish to receive ECTS study credits, remember to [enroll on this course](/enrollment/) before [the course ends](/practicalities#course-duration).

The assignment is to *write [Conway's Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life) using TDD.* Write it as a command line application which takes as input a *pattern file* in [RLE format](https://www.conwaylife.com/wiki/Run_Length_Encoded) and the *number of generations* to simulate, and then outputs the *resulting pattern* in RLE format after the specified number of generations.

For test data, you can download RLE files from LifeWiki; see the *Pattern files* section in each pattern's infobox, on the right side of the page. [Glider](https://www.conwaylife.com/wiki/Glider), [Blinker](https://www.conwaylife.com/wiki/Blinker) and [Block](https://www.conwaylife.com/wiki/Block) are some of the simplest patterns. [Gosper glider gun](https://www.conwaylife.com/wiki/Gosper_glider_gun) is an example of an infinitely growing pattern. Clicking the animated picture on a pattern's wiki page opens the LifeViewer app, from which you can also select and copy to clipboard the pattern's specific generation.

Any programming language is okay. For a starter project, there is <https://github.com/luontola/tdd-mooc-new-js-project> from before, and <https://github.com/swkBerlin/kata-bootstraps> contains many other languages.

##### What to submit

* **Record a video** of you coding this exercise from the beginning to the end.
  * Audio commentary is not necessary (we'll anyway use 5x playback speed when checking the submissions). No textual explanations either (they just make it slower to see how you code). The video only needs to show your text editor and test runner as you write code, so that we can check that you use TDD.
  * Use a screen recording software such as [OBS Studio](https://obsproject.com/) (see [instructions](https://obsproject.com/kb/quick-start-guide)) or similar. Remember to do a short test recording before doing the real thing, to make sure that the recording shows your editor and test runner.
  * Upload the video to any video streaming service (e.g. unlisted video on YouTube) and include a **link to the video** in the comment field as you submit the Git repository. Having the link in the Git repository's readme file is also fine. If the video is in multiple parts, provide all their links or a link to a playlist which contains all of them.
* [Submit this exercise](/practicalities#exercise-submissions) as "part6". Provide a **Git repository** with the source code and history of you doing this exercise. Write a **link to your screen recording video** in the comment field.
* Fill [this questionnaire](https://forms.gle/SyocAsQksBq5GUNk6).

##### Effort estimate

* <dfn data-title="...assuming a professional developer with good design skills">Minimum</dfn>: 6 hours
* <dfn data-title="...based on past student submissions">Average</dfn>: 13 hours

</tdd-decision>

---

Proceed to [Chapter 1: What is TDD](/1-tdd)
