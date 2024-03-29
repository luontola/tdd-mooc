import React, { useEffect, useState } from "react"
import styled from "styled-components"

import withSimpleErrorBoundary from "../util/withSimpleErrorBoundary"

const siteBackgroundColor = "#fcfcfc"

const StaticHeader = styled.div`
  position: relative;
`
const StickyHeader = styled.div`
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
const SoonUnstuckHeader = styled(StickyHeader)`
  // due to non-blurred background text, needs more opacity to achieve same readability
  background-color: ${siteBackgroundColor}f0;
`
const UnstuckHeader = styled(SoonUnstuckHeader)`
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

const promiseText = "During this course, I will not add any production code, unless it is required by a failing test."

const PromiseForm = ({ opacity, onAccept }) => (
  <div style={{ opacity: opacity }}>
    Before starting the exercises, you should make the following decision:
    <CheckboxLabel>
      <input type="checkbox" id="tdd-decision"
             checked={false}
             onChange={e => {
               if (e.target.checked) {
                 onAccept()
               }
             }}
      /> {promiseText}
    </CheckboxLabel>
  </div>
)

const NotYetAcceptedDialog = ({ onAccept }) => (
  <StickyHeader>
    <PromiseForm opacity={1} onAccept={onAccept} />
  </StickyHeader>
)

const JustNowAcceptedDialog = () => {
  const [sticky, setSticky] = useState(true)

  useEffect(() => {
    setTimeout(() => {
      setSticky(false)
    }, 5000)
  }, [])

  const MyHeader = sticky ? SoonUnstuckHeader : UnstuckHeader
  return (
    <MyHeader>
      <PromiseForm opacity={0} />
      <Accepted>
        <span aria-hidden="true" style={{ marginRight: "1ex" }}>✅</span> Good. And don't you forget that.
      </Accepted>
    </MyHeader>
  )
}

const PreviouslyAcceptedDialog = () => (
  <StaticHeader>
    <PreviouslyAccepted>
      <span aria-hidden="true">✅</span> {promiseText}
    </PreviouslyAccepted>
  </StaticHeader>
)


const TddDecision = (props) => {
  const [justNowAccepted, setJustNowAccepted] = useState(false)
  const [previouslyAccepted, setPreviouslyAccepted] = useState(false)

  useEffect(() => {
    setPreviouslyAccepted(localStorage.getItem("tdd-decision") === "yes")
  }, [])

  const onAccept = () => {
    setJustNowAccepted(true)
    localStorage.setItem("tdd-decision", "yes")
  }

  const MyBody = (justNowAccepted || previouslyAccepted) ? ShowBody : HideBody
  return (
    <div>
      {previouslyAccepted ? <PreviouslyAcceptedDialog />
        : justNowAccepted ? <JustNowAcceptedDialog />
          : <NotYetAcceptedDialog onAccept={onAccept} />
      }
      <MyBody>{props.children}</MyBody>
    </div>
  )
}

export default withSimpleErrorBoundary(TddDecision)
