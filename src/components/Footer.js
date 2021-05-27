import React from "react"
import styled from "styled-components"
import BackgroundImage from "../../data/images/banner.png"
import { Card, CardContent } from "@material-ui/core"
import { OutboundLink } from "gatsby-plugin-google-analytics"
import { Link } from "gatsby"
import { withTranslation } from "react-i18next"
import withSimpleErrorBoundary from "../util/withSimpleErrorBoundary"
import CourseSettings from "../../course-settings"

import UHLogo from "../images/uh-logo.png"
import MoocfiLogo from "../images/moocfi-logo-bw.png"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faFacebook, faGithub, faTwitter, faYoutube } from "@fortawesome/free-brands-svg-icons"

const StyledIcon = styled(FontAwesomeIcon)`
  color: black;
  margin-bottom: 1rem;
  margin: 1rem;
`

const SocialContainer = styled.div``

const ContentContainer = styled.div`
  padding: 1rem 0;
`

const GithubContainer = styled.div`
  padding-top: 1rem;
  a {
    display: flex;
    align-items: center;
    justify-content: center;
  }
`

const FooterWrapper = styled.footer`
  height: 35rem;
  position: relative;
  a {
    color: #006fe6;
  }
`

const FooterBackground = styled.div`
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  width: 100%;
  height: 100%;
  background-image: url(${BackgroundImage});
  filter: invert(1) grayscale(1) brightness(1.5) opacity(0.5);
  z-index: -50000;
`

const FooterContent = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
`

const StyledCard = styled(Card)`
  width: 90%;
  max-width: 800px;
`

const StyledCardContent = styled(CardContent)`
  flex-direction: column;
  display: flex;
  align-items: center;
  justify-content: center;
`

const BrandsContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  flex-wrap: wrap;
  img {
    height: 6rem;
    width: auto;
    margin: 1rem;
  }
`

const Footer = ({ t }) => {
  return (
    <FooterWrapper>
      <FooterBackground />
      <FooterContent>
        <StyledCard>
          <StyledCardContent>
            <GithubContainer>
              <OutboundLink
                href={CourseSettings.githubUrl}
                target="_blank"
                rel="noopener noreferrer"
              >
                <StyledIcon icon={faGithub} size="3x" title={t("footer-src")} />
                <div>{t("footer-src")}</div>
              </OutboundLink>
            </GithubContainer>
            <ContentContainer>
              {"This course was brought to you by "}
              <OutboundLink href="https://www.luontola.fi/"
                            target="_blank"
                            rel="noopener noreferrer">
                Esko Luontola
              </OutboundLink>
              {" and "}
              <OutboundLink href="https://nitor.com/"
                            target="_blank"
                            rel="noopener noreferrer">
                Nitor
              </OutboundLink>.
            </ContentContainer>

            <ContentContainer>
              <Link to="/credits">{t("credits")}</Link>.
            </ContentContainer>
            <SocialContainer>
              <OutboundLink
                href="https://twitter.com/moocfi"
                target="_blank"
                rel="noopener noreferrer"
                aria-label={t("twitter-aria")}
              >
                <StyledIcon icon={faTwitter} size="3x" />
              </OutboundLink>
              <OutboundLink
                href="https://www.facebook.com/Moocfi"
                target="_blank"
                rel="noopener noreferrer"
                aria-label={t("facebook-aria")}
              >
                <StyledIcon icon={faFacebook} size="3x" />
              </OutboundLink>
              <OutboundLink
                href="https://www.youtube.com/channel/UCkHoQ5p9skFdyjrV3_tnUrA"
                target="_blank"
                rel="noopener noreferrer"
                aria-label={t("facebook-aria")}
              >
                <StyledIcon icon={faYoutube} size="3x" />
              </OutboundLink>
            </SocialContainer>
            <BrandsContainer>
              <OutboundLink
                href="https://helsinki.fi"
                target="_blank"
                rel="noopener noreferrer"
              >
                <img alt="Helsingin yliopisto" src={UHLogo} />
              </OutboundLink>
              <OutboundLink
                href="https://mooc.fi"
                target="_blank"
                rel="noopener noreferrer"
              >
                <img alt="MOOC.fi" src={MoocfiLogo} />
              </OutboundLink>
            </BrandsContainer>
          </StyledCardContent>
        </StyledCard>
      </FooterContent>
    </FooterWrapper>
  )
}

export default withTranslation("common")(withSimpleErrorBoundary(Footer))
