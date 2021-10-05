import React, { useEffect, useState } from "react"
import styled from "styled-components"

import withSimpleErrorBoundary from "../util/withSimpleErrorBoundary"

const Header = styled.div`
  position: relative;
`

const CheckboxLabel = styled.label`
  font-size: 1.25rem;
  padding: 1rem 3rem;
  text-indent: -1em;
  margin-left: 1em;
`
const PreviouslyAccepted = styled.div`
  font-size: 1.25rem;
  padding: 1rem 3rem;
  text-indent: -1.25em;
  margin-left: 1.25em;
  color: green;
`

const Accepted = styled.div`
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #fcfcfc;
  font-size: 1.5rem;
  color: green;
  display: flex;
  justify-content: center;
  align-items: center;
`

const HideBody = styled.div`
  filter: blur(5px);
`
const ShowBody = styled.div`
`


const TextBox = (props) => {
  const [accepted, setAccepted] = useState(false)
  const [previouslyAccepted, setPreviouslyAccepted] = useState(false)
  useEffect(() => {
    setPreviouslyAccepted(localStorage.getItem("tdd-decision") === "yes")
  }, [])
  const Body = (accepted || previouslyAccepted) ? ShowBody : HideBody
  const promiseText = "During this course, I will not add any production code, unless it is required by a failing test."
  return (
    <div>
      {previouslyAccepted ?
        <Header>
          <PreviouslyAccepted>
            <span aria-hidden="true">✅</span> {promiseText}
          </PreviouslyAccepted>
        </Header> :
        <Header>
          Before starting the exercises, you should make the following decision:
          <CheckboxLabel>
            <input type="checkbox" id="tdd-decision"
                   checked={accepted}
                   onChange={e => {
                     const accepted = e.target.checked
                     setAccepted(accepted)
                     localStorage.setItem("tdd-decision", accepted ? "yes" : "no")
                   }}
            /> {promiseText}
          </CheckboxLabel>
          {accepted &&
          <Accepted>
            <span aria-hidden="true">✅</span> Good. And don't you forget that.
          </Accepted>
          }
        </Header>
      }
      <Body>{props.children}</Body>
    </div>
  )
}

export default withSimpleErrorBoundary(TextBox)
