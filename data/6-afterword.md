---
path: "/6-afterword"
title: "Chapter 6: To infinity and beyond"
hidden: false
information_page: true
sidebar_priority: 3000
---

## Homework for the next 10 years

After doing this course's exercises, you should have gotten over the initial hurdle of learning TDD, and have some idea of what TDD-done-well should look like. Likely your tests are still crap: test names which are not very descriptive, test code which is tightly coupled with implementation details, complex test setups, untestable code, duplication which has gone unnoticed and so on. But a bad test is better than no test, and the first step to improvement is admitting your deficiencies.

It should take something like 200 hours of practice to reach moderate competence with TDD. For the first year, focus on giving [good names](/1-tdd#test-names-should-be-sentences) to your tests. Write lots of little applications which push you to solve different kinds of problems, whether they are [code katas](https://kata-log.rocks/) or real applications. Avoid things that make testing hard (e.g. database, user interface), so that you can focus on just improving test readability.

After the first year of practicing TDD, writing tests first should be natural to you, and you should have seen how easy it is to work in well-tested codebases. The next challenge will be learning to write testable code. This will take more than 10 years[¹](https://norvig.com/21-days.html) - even a lifetime. Tackle problems which others say can't be tested, and figure out how to test them. Explore designs which make testing them easier. Start a project and then leave it for a couple of years for others to maintain - then come back to see how your original designs survived the test of time. Learn and repeat. Join legacy code projects and transform them to be as easy to work with as greenfield projects.

Join communities of like-minded people. For example the [SoCraTes](https://www.socrates-conference.de/home#conferences) (**So**ftware **Cra**ft and **Tes**ting) open space unconferences are held around the world and are filled with people who are passionate about the craft, including TDD. [SoCraTes Germany](https://www.socrates-conference.de/) is the oldest and biggest, and of particular mention is the chilled-out [Codefreeze](https://codefreeze.fi/) in Northern Finland.


## Books

Here are a few books that every software developer should read:

[Test Driven Development: By Example](https://www.amazon.com/Test-Driven-Development-Kent-Beck/dp/0321146530) (Kent Beck, 2002)<br>
Covers everything there is to know about TDD, except mock objects.

[Growing Object-Oriented Software, Guided by Tests](https://www.amazon.com/Growing-Object-Oriented-Software-Guided-Tests/dp/0321503627) (Steve Freeman, Nat Pryce, 2009)<br>
Covers everything there is to know about mock objects and messaging passing based object-oriented programming.

[Clean Code: A Handbook of Agile Software Craftsmanship](https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882) (Robert C. Martin, 2008)<br>
About writing good code.

[Understanding the Four Rules of Simple Design](https://leanpub.com/4rulesofsimpledesign) (Corey Haines, 2014)<br>
About removing duplication and improving names.

[Continuous Delivery: Reliable Software Releases through Build, Test, and Deployment Automation](https://www.amazon.com/Continuous-Delivery-Deployment-Automation-Addison-Wesley/dp/0321601912) (Jez Humble, David Farley, 2010)<br>
Describes a fundamental practice of modern software development.

---

EOF

Proceed to [Credits](/credits) or [Exercises](/exercises)
