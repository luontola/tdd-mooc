import React, { useEffect, useState } from "react"
import styled from "styled-components"

import withSimpleErrorBoundary from "../util/withSimpleErrorBoundary"

const siteBackgroundColor = "#fcfcfc"

const Header = styled.div`
  position: relative;
`
const StickyHeader1 = styled.div`
  position: sticky;
  top: 0;
  background-color: ${siteBackgroundColor}d0;
  // With z-index 0 the background color would not be visible.
  z-index: 1;
  // Due to blur spread, the header needs to be larger than the content area
  // to fully cover the blur underneath.
  margin: -0.5em -0.5em 0;
  padding: 0.5em 0.5em 0;
`
const StickyHeader2 = styled(StickyHeader1)`
  // due to non-blurred background text, needs more opacity to achieve same readability  
  background-color: ${siteBackgroundColor}f0;
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
  const StickyHeader = accepted ? StickyHeader2 : StickyHeader1
  return (
    <div>
      {previouslyAccepted ?
        <Header>
          <PreviouslyAccepted>
            <span aria-hidden="true">✅</span> {promiseText}
          </PreviouslyAccepted>
        </Header> :
        <StickyHeader>
          <div style={{ opacity: accepted ? 0 : 1 }}>
            Before starting the exercises, you should make the following decision:
            <CheckboxLabel>
              <input type="checkbox" id="tdd-decision"
                     checked={accepted}
                     onChange={e => {
                       const accepted = e.target.checked
                       setAccepted(accepted)
                       setTimeout(() => {
                         // avoids the sticky text staying there until page reload
                         setPreviouslyAccepted(true)
                       }, 5000)
                       localStorage.setItem("tdd-decision", accepted ? "yes" : "no")
                     }}
              /> {promiseText}
            </CheckboxLabel>
          </div>
          {accepted &&
            <Accepted>
              <span aria-hidden="true" style={{ marginRight: "1ex" }}>✅</span> Good. And don't you forget that.
            </Accepted>
          }
        </StickyHeader>
      }
      <Body>{props.children}</Body>
    </div>
  )
}

export default withSimpleErrorBoundary(TextBox)
