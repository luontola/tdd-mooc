import React, { useState } from "react"
import styled from "styled-components"

import withSimpleErrorBoundary from "../util/withSimpleErrorBoundary"

const Header = styled.div`
  position: relative;
`

const CheckboxLabel = styled.label`
  font-size: 1.25rem;
  padding: 1rem 3rem;
`

const Accepted = styled.div`
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: white;
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
  const [accepted, setAccepted] = useState(false);
  const Body = accepted ? ShowBody : HideBody;
  return (
    <div>
      <Header>
        Before starting the exercises, you should make the following decision:
        <CheckboxLabel>
          <input type="checkbox" id="tdd-decision"
            checked={accepted}
            onClick={e => setAccepted(e.target.checked)}
          /> During this course, I will not add any production code, unless it's required by a failing test.
        </CheckboxLabel>
        {accepted &&
          <Accepted>
            ✅ Good. And don't you forget that.
          </Accepted>
        }
      </Header>
      <Body>{props.children}</Body>
    </div>
  )
}

export default withSimpleErrorBoundary(TextBox)
