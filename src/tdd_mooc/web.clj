(ns tdd-mooc.web
  (:require [clojure.string :as str]
            [hiccup.page]
            [hiccup.util]
            [hiccup2.core :as h]
            [markdown.core :as markdown]
            [stasis.core :as stasis]))

(alter-var-root #'hiccup.util/*html-mode* (constantly :html))

(def export-dir "target/dist")

(defn layout-page [{:keys [path title content]}]
  (str (h/html (hiccup.page/doctype :html5)
               [:html
                [:head
                 [:meta {:charset "utf-8"}]
                 [:title
                  (when (and (some? title)
                             (not= "/" path))
                    (str title " - "))
                  "Test-Driven Development MOOC"]
                 [:meta {:name "viewport", :content "width=device-width, initial-scale=1.0"}]
                 [:link {:rel "stylesheet", :href "/reboot.css"}]
                 [:link {:rel "stylesheet", :href "/theme.css"}]
                 [:link {:rel "stylesheet", :href "/remark.css"}]
                 [:link {:rel "stylesheet", :href "/styled-components.css"}]]
                [:body

                 [:div#___gatsby
                  [:div#gatsby-focus-wrapper
                   {:tabindex "-1", :style "outline: none;"}

                   [:div.Layout__Wrapper-dzTsrx.eisbNB
                    [:div

                     [:div.Sidebar__MenuExpanderWrapper-kMlMFn.eEQwYJ
                      [:button.MuiButtonBase-root.MuiButton-root.MuiButton-outlined.MuiButton-outlinedPrimary
                       {:tabindex "0", :type "button"}
                       [:span.MuiButton-label
                        [:span
                         [:svg.svg-inline--fa.fa-bars.fa-w-14.Sidebar__StyledIcon-DzvBe.iqtrpt
                          {:aria-hidden "true",
                           :focusable "false",
                           :data-prefix "fas",
                           :data-icon "bars",
                           :role "img",
                           :xmlns "http://www.w3.org/2000/svg",
                           :viewBox "0 0 448 512"}
                          [:path
                           {:fill "currentColor",
                            :d "M16 132h416c8.837 0 16-7.163 16-16V76c0-8.837-7.163-16-16-16H16C7.163 60 0 67.163 0 76v40c0 8.837 7.163 16 16 16zm0 160h416c8.837 0 16-7.163 16-16v-40c0-8.837-7.163-16-16-16H16c-8.837 0-16 7.163-16 16v40c0 8.837 7.163 16 16 16zm0 160h416c8.837 0 16-7.163 16-16v-40c0-8.837-7.163-16-16-16H16c-8.837 0-16 7.163-16 16v40c0 8.837 7.163 16 16 16z"}]]
                         "Show navigation"]]
                       [:span.MuiTouchRipple-root]]]

                     [:div.Sidebar__SidebarContainer-hNbYUn.cvhAWi
                      [:div.Sidebar__Brand-hwdqxh.iKbMPT "Test-Driven Development"]
                      [:nav.Sidebar__TreeViewContainer-eLCvpi.eSOumu
                       [:ul.TreeView__StyledUl-cZtrYl.bCqPWG

                        [:div.TreeViewItem__ItemTitleWrapper-hdMboY.hUKTrQ.nav-item-course-overview
                         [:a.TreeViewItem__NavigationLink-bYRQVk.jBjZBN
                          {:aria-current "page", :active "t", :href "/"}
                          [:li.TreeViewItem__ListItem-wThmR.gKbkGC
                           [:div.TreeViewItem__ListItemLabel-dWnLpX.jCecle
                            "Course overview"]]]]

                        [:div.TreeViewItem__ItemTitleWrapper-hdMboY.hUKTrQ
                         {:class "nav-item-practicalities-(2024-spring)"}
                         [:a.TreeViewItem__NavigationLink-bYRQVk.eKHeMv
                          {:active "f", :href "/practicalities"}
                          [:li.TreeViewItem__ListItem-wThmR.gKbkGC
                           [:div.TreeViewItem__ListItemLabel-dWnLpX.jCecle
                            "Practicalities (2024 Spring)"]]]]

                        [:div.TreeViewItem__ItemTitleWrapper-hdMboY.hUKTrQ.nav-item-exercises
                         [:a.TreeViewItem__NavigationLink-bYRQVk.eKHeMv
                          {:active "f", :href "/exercises"}
                          [:li.TreeViewItem__ListItem-wThmR.gKbkGC
                           [:div.TreeViewItem__ListItemLabel-dWnLpX.jCecle "Exercises"]]]]

                        [:div.TreeViewItem__ItemTitleWrapper-hdMboY.hUKTrQ.nav-item-chapter-1:-what-is-tdd
                         [:a.TreeViewItem__NavigationLink-bYRQVk.eKHeMv
                          {:active "f", :href "/1-tdd"}
                          [:li.TreeViewItem__ListItem-wThmR.gKbkGC
                           [:div.TreeViewItem__ListItemLabel-dWnLpX.jCecle
                            "Chapter 1: What is TDD"]]]]

                        [:div.TreeViewItem__ItemTitleWrapper-hdMboY.hUKTrQ.nav-item-chapter-2:-refactoring-and-design
                         [:a.TreeViewItem__NavigationLink-bYRQVk.eKHeMv
                          {:active "f", :href "/2-design"}
                          [:li.TreeViewItem__ListItem-wThmR.gKbkGC
                           [:div.TreeViewItem__ListItemLabel-dWnLpX.jCecle
                            "Chapter 2: Refactoring and design"]]]]

                        [:div.TreeViewItem__ItemTitleWrapper-hdMboY.hUKTrQ.nav-item-chapter-3:-the-untestables
                         [:a.TreeViewItem__NavigationLink-bYRQVk.eKHeMv
                          {:active "f", :href "/3-challenges"}
                          [:li.TreeViewItem__ListItem-wThmR.gKbkGC
                           [:div.TreeViewItem__ListItemLabel-dWnLpX.jCecle
                            "Chapter 3: The Untestables"]]]]

                        [:div.TreeViewItem__ItemTitleWrapper-hdMboY.hUKTrQ.nav-item-chapter-4:-legacy-code
                         [:a.TreeViewItem__NavigationLink-bYRQVk.eKHeMv
                          {:active "f", :href "/4-legacy-code"}
                          [:li.TreeViewItem__ListItem-wThmR.gKbkGC
                           [:div.TreeViewItem__ListItemLabel-dWnLpX.jCecle
                            "Chapter 4: Legacy code"]]]]

                        [:div.TreeViewItem__ItemTitleWrapper-hdMboY.hUKTrQ.nav-item-chapter-5:-advanced-techniques
                         [:a.TreeViewItem__NavigationLink-bYRQVk.eKHeMv
                          {:active "f", :href "/5-advanced"}
                          [:li.TreeViewItem__ListItem-wThmR.gKbkGC
                           [:div.TreeViewItem__ListItemLabel-dWnLpX.jCecle
                            "Chapter 5: Advanced techniques"]]]]

                        [:div.TreeViewItem__ItemTitleWrapper-hdMboY.hUKTrQ.nav-item-chapter-6:-to-infinity-and-beyond
                         [:a.TreeViewItem__NavigationLink-bYRQVk.eKHeMv
                          {:active "f", :href "/6-afterword"}
                          [:li.TreeViewItem__ListItem-wThmR.gKbkGC
                           [:div.TreeViewItem__ListItemLabel-dWnLpX.jCecle
                            "Chapter 6: To infinity and beyond"]]]]

                        [:div.TreeViewItem__ItemTitleWrapper-hdMboY.hUKTrQ.nav-item-about-the-material
                         [:a.TreeViewItem__NavigationLink-bYRQVk.eKHeMv
                          {:active "f", :href "/credits"}
                          [:li.TreeViewItem__ListItem-wThmR.gKbkGC
                           [:div.TreeViewItem__ListItemLabel-dWnLpX.jCecle
                            "About the material"]]]]

                        [:div.TreeViewItem__ItemTitleWrapper-hdMboY.hUKTrQ.nav-item-technical-coach-for-your-team
                         [:a.TreeViewItem__NavigationLink-bYRQVk.eKHeMv
                          {:active "f", :href "/coaching"}
                          [:li.TreeViewItem__ListItem-wThmR.gKbkGC
                           [:div.TreeViewItem__ListItemLabel-dWnLpX.jCecle
                            "Technical coach for your team"]]]]]]

                      [:div.Sidebar__LogoContainer-fzcLKe.caPWJt
                       [:a.Logo__StyledLink-fTpKNo.fPvQSS
                        {:href "https://mooc.fi"}
                        [:img.Logo__LogoImg-igYGeX.ynGfR
                         {:src "data:image/png;base64,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"}]
                        [:div.Logo__LogoTypography-ihwmQD.llXBQp
                         {:color "inherit"}
                         "MOOC.fi"]]]]]

                    [:div.Layout__SidebarPush-gNtwOm.fRYZzJ
                     [:div.TopBar__TopBarContainer-cdnEdj.hiesVy]

                     [:main.ContentArea__ContentAreaContainer-hwRtzb.jlIBZz.content-area
                      [:header.Banner__BannerWrapper-djuISb.hmGmSr
                       [:div.Banner__Heading-hemedI.kTtmWY "Test-Driven Development"]
                       [:div.Banner__SubHeading-gGWxMY.bHenQC
                        "a plunge into the TDD programming technique"]]
                      [:div.Container-ineSfH.bXpZSt
                       [:article.InfoPageTemplate__ContentWrapper-eGpevX
                        [:h1 "Course overview"]
                        [:div
                         [:h2#what.material-header
                          "What?"
                          [:a.anchor.after
                           {:href "#what", :aria-label "what permalink"}
                           [:svg
                            {:aria-hidden "true",
                             :focusable "false",
                             :height "16",
                             :version "1.1",
                             :viewBox "0 0 16 16",
                             :width "16"}
                            [:path
                             {:fill-rule "evenodd",
                              :d "M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"}]]]]
                         [:p
                          "Test-Driven Development (TDD) is an iterative programming technique where you first write a failing test, which describes what you want to achieve, and after that write production code to pass the test. The code is continually refactored to make the next change easier."]
                         [:p
                          "This course should help you get over the initial hurdle of getting started with TDD. After that you'll likely still make changes in too big steps and your tests could be better, but at least you're writing them. Getting good at TDD will require further 6-12 months of deliberate practice."]
                         [:h2#why.material-header
                          "Why?"
                          [:a.anchor.after
                           {:href "#why", :aria-label "why permalink"}
                           [:svg
                            {:aria-hidden "true",
                             :focusable "false",
                             :height "16",
                             :version "1.1",
                             :viewBox "0 0 16 16",
                             :width "16"}
                            [:path
                             {:fill-rule "evenodd",
                              :d "M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"}]]]]
                         [:p
                          "Fewer bugs. Less debugging. Better design. More fun. Produce better software faster."]
                         [:p
                          [:a
                           {:href "https://tidyfirst.substack.com/p/tdd-outcomes",
                            :target "_blank",
                            :rel "noopener noreferrer"}
                           "TDD Outcomes"]
                          "lists some things that you can expect from using TDD."]
                         [:p
                          [:a
                           {:href "https://blog.jbrains.ca/permalink/how-test-driven-development-works-and-more",
                            :target "_blank",
                            :rel "noopener noreferrer"}
                           "How Test-Driven Development Works (And More!)"]
                          "gives a more theoretical explanation on"
                          [:em "why"]
                          "TDD works than just \"it works for me\"."]
                         [:p
                          "If you'd like to read academic papers, there's the 2020 research paper on"
                          [:a
                           {:href "https://arxiv.org/pdf/2007.09863",
                            :target "_blank",
                            :rel "noopener noreferrer"}
                           "Why Research on Test-Driven Development is Inconclusive?"]
                          "(TLDR: most test subjects have &lt;1 year experience)."]
                         [:h2#who.material-header
                          "Who?"
                          [:a.anchor.after
                           {:href "#who", :aria-label "who permalink"}
                           [:svg
                            {:aria-hidden "true",
                             :focusable "false",
                             :height "16",
                             :version "1.1",
                             :viewBox "0 0 16 16",
                             :width "16"}
                            [:path
                             {:fill-rule "evenodd",
                              :d "M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"}]]]]
                         [:p
                          "You are ready for this course if you have written at least a few small applications of over 1000 lines of code."]
                         [:p
                          "The basic version of this course (4 cr) requires good JavaScript and object-oriented programming skills."]
                         [:p
                          "The extended version of this course (4+1 cr) requires additionally some web development and database experience: you will need to implement a small full-stack web app. Relevant courses to that end are"
                          [:a
                           {:href "https://fullstackopen.com/en/",
                            :target "_blank",
                            :rel "noopener noreferrer"}
                           "Full Stack Open"]
                          "(parts 0-5),"
                          [:a
                           {:href "https://devopswithdocker.com/",
                            :target "_blank",
                            :rel "noopener noreferrer"}
                           "DevOps with Docker"]
                          "(parts 0-2),"
                          [:a
                           {:href "https://tikape.mooc.fi/",
                            :target "_blank",
                            :rel "noopener noreferrer"}
                           "Introduction to Databases"]
                          "(Finnish only) or"
                          [:a
                           {:href "https://www.postgresql.org/docs/current/tutorial.html",
                            :target "_blank",
                            :rel "noopener noreferrer"}
                           "any SQL tutorial"]
                          "."]
                         [:h2#how.material-header
                          "How?"
                          [:a.anchor.after
                           {:href "#how", :aria-label "how permalink"}
                           [:svg
                            {:aria-hidden "true",
                             :focusable "false",
                             :height "16",
                             :version "1.1",
                             :viewBox "0 0 16 16",
                             :width "16"}
                            [:path
                             {:fill-rule "evenodd",
                              :d "M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"}]]]]
                         [:p
                          "This course consists of"
                          [:a {:href "/exercises"} "six exercises"]
                          "(or small projects) about using TDD, refactoring and writing tests. Exercise 5 (full-stack web app) is optional, but if you do it, you will get an extra study credit. For that purpose, this course is visible as two course units:"]
                         [:ul
                          [:li
                           [:a
                            {:href "https://studies.helsinki.fi/courses/course-unit/otm-adcdbb43-dc29-467b-b68d-f5f7bf13ea7d",
                             :target "_blank",
                             :rel "noopener noreferrer"}
                            "Test-Driven Development (4 cr)"]
                           "- do just the basic exercises (#1-4, #6)"]
                          [:li
                           [:a
                            {:href "https://studies.helsinki.fi/courses/course-unit/otm-6fd8f9b4-9566-449b-8668-d91b3134dbcf",
                             :target "_blank",
                             :rel "noopener noreferrer"}
                            "Test-Driven Development: Full Stack (1 cr)"]
                           "- do also the full-stack exercise (#5)"]]
                         [:p
                          "The exercises are in JavaScript, but for the exercises where you create a project from scratch, you may use any programming language of your choice. To do the basic exercises, you'll need"
                          [:a
                           {:href "https://nodejs.org/",
                            :target "_blank",
                            :rel "noopener noreferrer"}
                           "Node.js"]
                          ","
                          [:a
                           {:href "https://git-scm.com/",
                            :target "_blank",
                            :rel "noopener noreferrer"}
                           "Git"]
                          "and an IDE or text editor (e.g."
                          [:a
                           {:href "https://www.jetbrains.com/idea/",
                            :target "_blank",
                            :rel "noopener noreferrer"}
                           "IntelliJ IDEA"]
                          "or"
                          [:a
                           {:href "https://code.visualstudio.com/",
                            :target "_blank",
                            :rel "noopener noreferrer"}
                           "VS Code"]
                          "). For the full-stack exercise,"
                          [:a
                           {:href "https://www.docker.com/",
                            :target "_blank",
                            :rel "noopener noreferrer"}
                           "Docker"]
                          "would be useful. To submit the completed exercises, you'll need a"
                          [:a
                           {:href "https://github.com/",
                            :target "_blank",
                            :rel "noopener noreferrer"}
                           "GitHub"]
                          "account and a screen recording software (e.g."
                          [:a
                           {:href "https://obsproject.com/",
                            :target "_blank",
                            :rel "noopener noreferrer"}
                           "OBS Studio"]
                          ")."]
                         [:h2#when.material-header
                          "When?"
                          [:a.anchor.after
                           {:href "#when", :aria-label "when permalink"}
                           [:svg
                            {:aria-hidden "true",
                             :focusable "false",
                             :height "16",
                             :version "1.1",
                             :viewBox "0 0 16 16",
                             :width "16"}
                            [:path
                             {:fill-rule "evenodd",
                              :d "M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"}]]]]
                         [:p
                          "This course will run from January until May 2024 (and presumably next year around the same time). There will be a Discord channel and study groups. See the"
                          [:a {:href "/practicalities"} "practicalities page"]
                          "for details."]
                         [:p
                          "Of course, you can also use this course material for self-study whenever you want, even if the course is not active."]
                         [:hr.MuiDivider-root.Hr__StyledDivider-gQsKkI.embgRO]
                         [:p
                          "Proceed to"
                          [:a {:href "/practicalities"} "Practicalities"]
                          "or"
                          [:a {:href "/exercises"} "Exercises"]]]]]]

                     [:footer.Footer__FooterWrapper-fnqvNv.imgwrR
                      [:div.Footer__FooterBackground-kjRHOC.cOgljD]
                      [:div.Footer__FooterContent-gyUxpp.cuOkjz
                       [:div.MuiPaper-root.MuiCard-root.Footer__StyledCard-fPeoHm.zuIqA.MuiPaper-elevation1.MuiPaper-rounded
                        [:div.MuiCardContent-root.Footer__StyledCardContent-glSydn.gFNiRW
                         [:div.Footer__GithubContainer-fbxrQT.czSdBg

                          [:a
                           {:href "https://github.com/luontola/tdd-mooc",
                            :target "_blank",
                            :rel "noopener noreferrer"}
                           [:svg.svg-inline--fa.fa-github.fa-w-16.fa-3x.Footer__StyledIcon-gYbBZt.fwfoFU
                            {:aria-labelledby "svg-inline--fa-title-wvD7hAbHp9Fy",
                             :data-prefix "fab",
                             :data-icon "github",
                             :role "img",
                             :xmlns "http://www.w3.org/2000/svg",
                             :viewBox "0 0 496 512"}
                            [:title#svg-inline--fa-title-wvD7hAbHp9Fy
                             "Source code of the material"]
                            [:path
                             {:fill "currentColor",
                              :d "M165.9 397.4c0 2-2.3 3.6-5.2 3.6-3.3.3-5.6-1.3-5.6-3.6 0-2 2.3-3.6 5.2-3.6 3-.3 5.6 1.3 5.6 3.6zm-31.1-4.5c-.7 2 1.3 4.3 4.3 4.9 2.6 1 5.6 0 6.2-2s-1.3-4.3-4.3-5.2c-2.6-.7-5.5.3-6.2 2.3zm44.2-1.7c-2.9.7-4.9 2.6-4.6 4.9.3 2 2.9 3.3 5.9 2.6 2.9-.7 4.9-2.6 4.6-4.6-.3-1.9-3-3.2-5.9-2.9zM244.8 8C106.1 8 0 113.3 0 252c0 110.9 69.8 205.8 169.5 239.2 12.8 2.3 17.3-5.6 17.3-12.1 0-6.2-.3-40.4-.3-61.4 0 0-70 15-84.7-29.8 0 0-11.4-29.1-27.8-36.6 0 0-22.9-15.7 1.6-15.4 0 0 24.9 2 38.6 25.8 21.9 38.6 58.6 27.5 72.9 20.9 2.3-16 8.8-27.1 16-33.7-55.9-6.2-112.3-14.3-112.3-110.5 0-27.5 7.6-41.3 23.6-58.9-2.6-6.5-11.1-33.3 2.6-67.9 20.9-6.5 69 27 69 27 20-5.6 41.5-8.5 62.8-8.5s42.8 2.9 62.8 8.5c0 0 48.1-33.6 69-27 13.7 34.7 5.2 61.4 2.6 67.9 16 17.7 25.8 31.5 25.8 58.9 0 96.5-58.9 104.2-114.8 110.5 9.2 7.9 17 22.9 17 46.4 0 33.7-.3 75.4-.3 83.6 0 6.5 4.6 14.4 17.3 12.1C428.2 457.8 496 362.9 496 252 496 113.3 383.5 8 244.8 8zM97.2 352.9c-1.3 1-1 3.3.7 5.2 1.6 1.6 3.9 2.3 5.2 1 1.3-1 1-3.3-.7-5.2-1.6-1.6-3.9-2.3-5.2-1zm-10.8-8.1c-.7 1.3.3 2.9 2.3 3.9 1.6 1 3.6.7 4.3-.7.7-1.3-.3-2.9-2.3-3.9-2-.6-3.6-.3-4.3.7zm32.4 35.6c-1.6 1.3-1 4.3 1.3 6.2 2.3 2.3 5.2 2.6 6.5 1 1.3-1.3.7-4.3-1.3-6.2-2.2-2.3-5.2-2.6-6.5-1zm-11.4-14.7c-1.6 1-1.6 3.6 0 5.9 1.6 2.3 4.3 3.3 5.6 2.3 1.6-1.3 1.6-3.9 0-6.2-1.4-2.3-4-3.3-5.6-2z"}]]
                           [:div "Source code of the material"]]]

                         [:div.Footer__ContentContainer-fAXGAb.jEKLpz
                          "This course was brought to you by "
                          [:a
                           {:href "https://twitter.com/EskoLuontola",
                            :target "_blank",
                            :rel "noopener noreferrer"}
                           "Esko Luontola"]
                          " and "
                          [:a
                           {:href "https://nitor.com/",
                            :target "_blank",
                            :rel "noopener noreferrer"}
                           "Nitor"]
                          "."]

                         [:div.Footer__ContentContainer-fAXGAb.jEKLpz
                          [:a {:href "/credits"} "Credits and about the material"]
                          "."]

                         [:div.Footer__SocialContainer-fsiepV
                          [:a
                           {:href "https://twitter.com/moocfi",
                            :target "_blank",
                            :rel "noopener noreferrer",
                            :aria-label "Mooc.fi Twitter -profile"}
                           [:svg.svg-inline--fa.fa-twitter.fa-w-16.fa-3x.Footer__StyledIcon-gYbBZt.fwfoFU
                            {:aria-hidden "true",
                             :focusable "false",
                             :data-prefix "fab",
                             :data-icon "twitter",
                             :role "img",
                             :xmlns "http://www.w3.org/2000/svg",
                             :viewBox "0 0 512 512"}
                            [:path
                             {:fill "currentColor",
                              :d "M459.37 151.716c.325 4.548.325 9.097.325 13.645 0 138.72-105.583 298.558-298.558 298.558-59.452 0-114.68-17.219-161.137-47.106 8.447.974 16.568 1.299 25.34 1.299 49.055 0 94.213-16.568 130.274-44.832-46.132-.975-84.792-31.188-98.112-72.772 6.498.974 12.995 1.624 19.818 1.624 9.421 0 18.843-1.3 27.614-3.573-48.081-9.747-84.143-51.98-84.143-102.985v-1.299c13.969 7.797 30.214 12.67 47.431 13.319-28.264-18.843-46.781-51.005-46.781-87.391 0-19.492 5.197-37.36 14.294-52.954 51.655 63.675 129.3 105.258 216.365 109.807-1.624-7.797-2.599-15.918-2.599-24.04 0-57.828 46.782-104.934 104.934-104.934 30.213 0 57.502 12.67 76.67 33.137 23.715-4.548 46.456-13.32 66.599-25.34-7.798 24.366-24.366 44.833-46.132 57.827 21.117-2.273 41.584-8.122 60.426-16.243-14.292 20.791-32.161 39.308-52.628 54.253z"}]]]

                          [:a
                           {:href "https://www.facebook.com/Moocfi",
                            :target "_blank",
                            :rel "noopener noreferrer",
                            :aria-label "Mooc.fi Facebook -profile"}
                           [:svg.svg-inline--fa.fa-facebook.fa-w-16.fa-3x.Footer__StyledIcon-gYbBZt.fwfoFU
                            {:aria-hidden "true",
                             :focusable "false",
                             :data-prefix "fab",
                             :data-icon "facebook",
                             :role "img",
                             :xmlns "http://www.w3.org/2000/svg",
                             :viewBox "0 0 512 512"}
                            [:path
                             {:fill "currentColor",
                              :d "M504 256C504 119 393 8 256 8S8 119 8 256c0 123.78 90.69 226.38 209.25 245V327.69h-63V256h63v-54.64c0-62.15 37-96.48 93.67-96.48 27.14 0 55.52 4.84 55.52 4.84v61h-31.28c-30.8 0-40.41 19.12-40.41 38.73V256h68.78l-11 71.69h-57.78V501C413.31 482.38 504 379.78 504 256z"}]]]

                          [:a
                           {:href "https://www.youtube.com/channel/UCkHoQ5p9skFdyjrV3_tnUrA",
                            :target "_blank",
                            :rel "noopener noreferrer",
                            :aria-label "Mooc.fi Facebook -profile"}
                           [:svg.svg-inline--fa.fa-youtube.fa-w-18.fa-3x.Footer__StyledIcon-gYbBZt.fwfoFU
                            {:aria-hidden "true",
                             :focusable "false",
                             :data-prefix "fab",
                             :data-icon "youtube",
                             :role "img",
                             :xmlns "http://www.w3.org/2000/svg",
                             :viewBox "0 0 576 512"}
                            [:path
                             {:fill "currentColor",
                              :d "M549.655 124.083c-6.281-23.65-24.787-42.276-48.284-48.597C458.781 64 288 64 288 64S117.22 64 74.629 75.486c-23.497 6.322-42.003 24.947-48.284 48.597-11.412 42.867-11.412 132.305-11.412 132.305s0 89.438 11.412 132.305c6.281 23.65 24.787 41.5 48.284 47.821C117.22 448 288 448 288 448s170.78 0 213.371-11.486c23.497-6.321 42.003-24.171 48.284-47.821 11.412-42.867 11.412-132.305 11.412-132.305s0-89.438-11.412-132.305zm-317.51 213.508V175.185l142.739 81.205-142.739 81.201z"}]]]]

                         [:div.Footer__BrandsContainer-cpFXbK.fjCpe
                          [:a
                           {:href "https://helsinki.fi",
                            :target "_blank",
                            :rel "noopener noreferrer"}
                           [:img
                            {:alt "Helsingin yliopisto",
                             :src "data:image/png;base64,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"}]]
                          [:a
                           {:href "https://mooc.fi",
                            :target "_blank",
                            :rel "noopener noreferrer"}
                           [:img
                            {:alt "MOOC.fi",
                             :src "/static/moocfi-logo-bw-40fb2db8a1b68fff676338919b31ae7c.png"}]]]]]]]]]]]]])))

(defn markdown-pages [pages]
  (->> pages
       (map (fn [[path markdown]]
              (let [path (-> path
                             (str/replace #"/index\.md$" "/")
                             (str/replace #"\.md$" "/"))
                    {:keys [html metadata]} (markdown/md-to-html-string-with-meta markdown)]
                [path (layout-page {:path path
                                    :title (:title metadata)
                                    :content (h/raw html)})])))
       (into {})))

(defn get-pages []
  (stasis/merge-page-sources
   {:public (stasis/slurp-directory "resources/public" #".*\.(html|css|js)$")
    :markdown (markdown-pages (stasis/slurp-directory "data" #"\.md$"))}))

(def app (stasis/serve-pages get-pages))

(defn export []
  (stasis/empty-directory! export-dir)
  (stasis/export-pages (get-pages) export-dir))
