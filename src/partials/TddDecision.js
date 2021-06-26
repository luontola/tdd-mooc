import React, {useState} from "react"
import styled from "styled-components"

import withSimpleErrorBoundary from "../util/withSimpleErrorBoundary"

const Header = styled.div`
  height: 9rem;
`

const Question = styled.div`
`

const CheckboxLabel = styled.label`
  font-size: 1.25rem;
  padding: 1rem 3rem;
`

const Accepted = styled.div`
  font-size: 1.5rem;
  color: green;
  height: 100%;
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
        {accepted ? (
          <Accepted>
              ✅ Good. And don't you forget that.
          </Accepted>
        ) : (
          <Question>
            <p>Before starting the exercises, you should make the following decision:</p>
            <CheckboxLabel>
              <input type="checkbox" id="tdd-decision"
                    checked={accepted}
                    onClick={e => setAccepted(e.target.checked)}
                    /> During this course, I will not add any production code,
                    unless it's required by a failing test.
            </CheckboxLabel>
          </Question>
        )}
      </Header>
      <Body>{props.children}</Body>
    </div>
  )
}

export default withSimpleErrorBoundary(TextBox)
