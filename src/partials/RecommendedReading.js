import React from "react"
import styled from "styled-components"

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faBookOpen } from "@fortawesome/free-solid-svg-icons"
import withSimpleErrorBoundary from "../util/withSimpleErrorBoundary"

const Wrapper = styled.aside`
  padding: 0.8rem;
  margin-bottom: 2rem;
  border-left: 0.2rem solid var(--color);
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.2),
  0 1px 1px 0 rgba(0, 0, 0, 0.14),
  0 2px 1px -1px rgba(0, 0, 0, 0.12);
  border-radius: 4px;
`

const StyledIcon = styled(FontAwesomeIcon)`
  vertical-align: middle;
  margin-right: 0.7rem;
  margin-left: 0.2rem;
  color: var(--color);
`

const Header = styled.div`
  font-size: 1.3rem;
  font-weight: normal;
  padding-bottom: 1rem;
`

const Body = styled.p`
  margin: 0;
  padding-bottom: 0.5rem;

  & ul {
    list-style: none;
    padding: 0;
    margin: 0;
  }

  & li {
    margin-bottom: 0.5rem;
  }

  & li:last-child {
    margin-bottom: 0;
  }
`

const TextBox = (props) => {
  return (
    <Wrapper style={{ "--color": "#528afc" }}>
      <Header>
        <StyledIcon icon={faBookOpen} size="1x" />
        Recommended reading
      </Header>
      <Body>{props.children}</Body>
    </Wrapper>
  )
}

export default withSimpleErrorBoundary(TextBox)
