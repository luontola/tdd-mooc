import React from "react"
import styled from "styled-components"
import withSimpleErrorBoundary from "../util/withSimpleErrorBoundary"
import LoginStateContext, { withLoginStateContext } from "../contexes/LoginStateContext"

const TopBarContainer = styled.div`
  height: 4rem;
  width: 100%;
  display: flex;
  justify-content: flex-end;
  @media only screen and (max-width: 1200px) {
    justify-content: center;
  }
`

class TopBar extends React.Component {
  static contextType = LoginStateContext

  render() {
    return (
      <TopBarContainer>
      </TopBarContainer>
    )
  }
}

export default withSimpleErrorBoundary(withLoginStateContext(TopBar))
