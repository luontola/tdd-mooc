import React, { useEffect, useState } from "react"
import styled from "styled-components"
import withSimpleErrorBoundary from "../util/withSimpleErrorBoundary"
import { SMALL_MEDIUM_BREAKPOINT } from "../util/constants"

const Table = styled.table`
  width: 100%;
  border-collapse: collapse;
  border: 1px solid lightgray;
  background-color: white;
  border-radius: 4px;
  box-shadow: 0 2px 1px -1px rgb(0 0 0 / 20%), 0 1px 1px 0 rgb(0 0 0 / 14%), 0 1px 3px 0 rgb(0 0 0 / 12%);

  tr {
    border-bottom: 1px solid rgba(224, 224, 224, 1);
  }

  tbody > tr:last-of-type {
    border-bottom: none;
  }

  td, th {
    padding: 0.8em 0.25em 0.8em 1em;
  }
`

const IconCell = styled.td`
  width: 0;
  @media only screen and (max-width: ${SMALL_MEDIUM_BREAKPOINT}) {
    display: none;
  }
`

const BigCheckbox = styled.input`
  width: 1.5em;
  height: 1.5em;
  margin: -0.25em 0;
  vertical-align: middle;
`

function Step({ id, icon, content, duration }) {
  const [done, setDone] = useState(false)
  const key = "exercise-schedule/" + id
  useEffect(() => {
    setDone(localStorage.getItem(key) === "true")
  }, [key])

  function onChange(event) {
    const checked = event.target.checked
    setDone(checked)
    localStorage.setItem(key, "" + checked)
  }

  const rowStyle = done ? { backgroundColor: "#32cd324f" } : {}
  const textStyle = done ? { textDecoration: "line-through" } : {}
  return (
    <tr style={rowStyle}>
      <IconCell aria-hidden="true">{icon}</IconCell>
      <td style={textStyle}>{content}</td>
      <td style={textStyle}>{duration}</td>
      <td><BigCheckbox type="checkbox" checked={done} onChange={onChange} /></td>
    </tr>
  )
}

function ExerciseSchedule() {
  return (
    <Table>
      <thead>
      <tr>
        <IconCell aria-hidden="true" />
        <th>What to read and do</th>
        <th>Duration (average)</th>
        <th>Done</th>
      </tr>
      </thead>
      <tbody>
      <Step id="chapter1"
            icon="📖"
            content={<a href="/1-tdd">Chapter 1: What is TDD</a>} />
      <Step id="chapter2"
            icon="📖"
            content={<a href="/2-design">Chapter 2: Refactoring and design</a>} />
      <Step id="exercise1"
            icon="👨‍💻"
            content={<a href="#exercise-1-small-safe-steps">Exercise 1: Small, safe steps</a>}
            duration="3 h" />
      <Step id="exercise2"
            icon="👩‍💻"
            content={<><a href="#exercise-2-tetris">Exercise 2: Tetris</a></>}
            duration="38 h" />
      <Step id="chapter3"
            icon="📖"
            content={<a href="/3-challenges">Chapter 3: The Untestables</a>} />
      <Step id="exercise3"
            icon="👨‍💻"
            content={<a href="#exercise-3-untestable-code">Exercise 3: Untestable code</a>}
            duration="7 h" />
      <Step id="chapter4"
            icon="📖"
            content={<a href="/4-legacy-code">Chapter 4: Legacy code</a>} />
      <Step id="exercise4"
            icon="👩‍💻"
            content={<a href="#exercise-4-legacy-code">Exercise 4: Legacy code</a>}
            duration="6 h" />
      <Step id="chapter5"
            icon="📖"
            content={<a href="/5-advanced">Chapter 5: Advanced techniques</a>} />
      <Step id="exercise5"
            icon="👨‍💻"
            content={<a href="#optional-exercise-5-full-stack-web-app">(optional) Exercise 5: Full-stack web app</a>}
            duration="32 h" />
      <Step id="exercise6"
            icon="👩‍💻"
            content={<a href="#exercise-6-conways-game-of-life">Exercise 6: Conway's Game of Life</a>}
            duration="13 h" />
      <Step id="chapter6"
            icon="📖"
            content={<a href="/6-afterword">Chapter 6: To infinity and beyond</a>} />
      </tbody>
    </Table>
  )
}

export default withSimpleErrorBoundary(ExerciseSchedule)
