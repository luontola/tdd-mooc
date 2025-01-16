(ns tdd-mooc.web
  (:require [clj-async-profiler.core :as prof]
            [clj-yaml.core :as yaml]
            [clojure.string :as str]
            [clojure.test :refer [deftest is testing]]
            [clojure.walk :as walk]
            [hiccup.page]
            [hiccup.util]
            [hiccup2.core :as h]
            [net.cgrand.enlive-html :as enlive]
            [optimus.assets :as optimus.assets]
            [optimus.export :as optimus.export]
            [optimus.optimizations :as optimizations]
            [optimus.prime :as optimus]
            [optimus.strategies :as optimus.strategies]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [stasis.core :as stasis])
  (:import (com.vladsch.flexmark.ext.anchorlink AnchorLinkExtension)
           (com.vladsch.flexmark.ext.yaml.front.matter YamlFrontMatterBlock YamlFrontMatterExtension)
           (com.vladsch.flexmark.html HtmlRenderer)
           (com.vladsch.flexmark.parser Parser)
           (com.vladsch.flexmark.util.ast Node)
           (com.vladsch.flexmark.util.data MutableDataSet)))

(alter-var-root #'hiccup.util/*html-mode* (constantly :html))

(def export-dir "target/dist")

(def navigation-tree
  [{:children [{:href "/"}
               {:href "/practicalities/"
                :children [{:href "/enrollment/"}]}
               {:title "Course material"
                :children [{:href "/exercises/"}
                           {:href "/1-tdd/"}
                           {:href "/2-design/"}
                           {:href "/3-challenges/"}
                           {:href "/4-legacy-code/"}
                           {:href "/5-advanced/"}
                           {:href "/6-afterword/"}]}]}
   {:children [{:href "/credits/"}
               {:href "/coaching/"}]}])

(defn enrich-navigation-tree [navigation-tree get-page-title]
  (walk/postwalk (fn [v]
                   (if (and (map? v)
                            (some? (:href v)))
                     (assoc v :title (or (get-page-title (:href v))
                                         (:href v)))
                     v))
                 navigation-tree))

(defn navigation-item [{:keys [title href children]} current-path]
  (h/html [:li
           (if (some? href)
             [:a (merge {:href href}
                        (when (= current-path href)
                          {:aria-current "page"}))
              title]
             [:span title])
           (when (some? children)
             [:ul (map #(navigation-item % current-path) children)])]))

(defn navigation-menu [current-path navigation-tree]
  (h/html (for [{:keys [children]} navigation-tree]
            [:ul (map #(navigation-item % current-path) children)])))

(defn layout-navigation [current-path navigation-tree]
  (h/html [:nav#site-navigation {:aria-label "Table of contents"}
           [:div.nav-title "TDD MOOC"]
           (navigation-menu current-path navigation-tree)]))

(defn twitter-icon []
  (h/html [:svg {:aria-hidden "true"
                 :role "img"
                 :xmlns "http://www.w3.org/2000/svg"
                 :viewBox "0 0 512 512"}
           [:path {:fill "currentColor"
                   :d "M459.37 151.716c.325 4.548.325 9.097.325 13.645 0 138.72-105.583 298.558-298.558 298.558-59.452 0-114.68-17.219-161.137-47.106 8.447.974 16.568 1.299 25.34 1.299 49.055 0 94.213-16.568 130.274-44.832-46.132-.975-84.792-31.188-98.112-72.772 6.498.974 12.995 1.624 19.818 1.624 9.421 0 18.843-1.3 27.614-3.573-48.081-9.747-84.143-51.98-84.143-102.985v-1.299c13.969 7.797 30.214 12.67 47.431 13.319-28.264-18.843-46.781-51.005-46.781-87.391 0-19.492 5.197-37.36 14.294-52.954 51.655 63.675 129.3 105.258 216.365 109.807-1.624-7.797-2.599-15.918-2.599-24.04 0-57.828 46.782-104.934 104.934-104.934 30.213 0 57.502 12.67 76.67 33.137 23.715-4.548 46.456-13.32 66.599-25.34-7.798 24.366-24.366 44.833-46.132 57.827 21.117-2.273 41.584-8.122 60.426-16.243-14.292 20.791-32.161 39.308-52.628 54.253z"}]]))

(defn facebook-icon []
  (h/html [:svg {:aria-hidden "true"
                 :role "img"
                 :xmlns "http://www.w3.org/2000/svg"
                 :viewBox "0 0 512 512"}
           [:path {:fill "currentColor"
                   :d "M504 256C504 119 393 8 256 8S8 119 8 256c0 123.78 90.69 226.38 209.25 245V327.69h-63V256h63v-54.64c0-62.15 37-96.48 93.67-96.48 27.14 0 55.52 4.84 55.52 4.84v61h-31.28c-30.8 0-40.41 19.12-40.41 38.73V256h68.78l-11 71.69h-57.78V501C413.31 482.38 504 379.78 504 256z"}]]))

(defn youtube-icon []
  (h/html [:svg {:aria-hidden "true"
                 :role "img"
                 :xmlns "http://www.w3.org/2000/svg"
                 :viewBox "0 0 576 512"}
           [:path {:fill "currentColor"
                   :d "M549.655 124.083c-6.281-23.65-24.787-42.276-48.284-48.597C458.781 64 288 64 288 64S117.22 64 74.629 75.486c-23.497 6.322-42.003 24.947-48.284 48.597-11.412 42.867-11.412 132.305-11.412 132.305s0 89.438 11.412 132.305c6.281 23.65 24.787 41.5 48.284 47.821C117.22 448 288 448 288 448s170.78 0 213.371-11.486c23.497-6.321 42.003-24.171 48.284-47.821 11.412-42.867 11.412-132.305 11.412-132.305s0-89.438-11.412-132.305zm-317.51 213.508V175.185l142.739 81.205-142.739 81.201z"}]]))

(defn social-link [{:keys [label href icon]}]
  (h/html [:a {:href href,
               :aria-label label
               :title label}
           icon]))

(defn layout-footer []
  (h/html [:footer
           [:div "This course was brought to you by "
            [:a {:href "https://twitter.com/EskoLuontola"} "Esko Luontola"]
            " and "
            [:a {:href "https://nitor.com/"} "Nitor"]
            "."]

           [:div [:a {:href "https://github.com/luontola/tdd-mooc"}
                  [:small "Website source code"]]]

           [:div [:a {:href "/credits"}
                  [:small "Credits and about the material"]]]

           [:div.social-links-footer
            (social-link {:label "Mooc.fi Twitter profile"
                          :href "https://twitter.com/moocfi"
                          :icon (twitter-icon)})
            (social-link {:label "Mooc.fi Facebook profile"
                          :href "https://www.facebook.com/Moocfi"
                          :icon (facebook-icon)})
            (social-link {:label "Mooc.fi YouTube channel"
                          :href "https://www.youtube.com/@mooc-fi"
                          :icon (youtube-icon)})]

           [:div.university-links-footer
            [:div.hy-logo
             [:a {:href "https://www.helsinki.fi"}
              [:svg {:focusable "false"
                     :aria-hidden "true"
                     :viewBox "0 0 1000 1000"}
               [:path {:fill "currentColor"
                       :d "M452 0h96v97h-96V0zm0 903h96v97h-96v-97zm380-358q-32-20-38-74-25 3-44-3-28-10-40-42-6-13-12-47t-13-52q-12-32-33-56-33-35-74-50-37-14-78-11 30 19 37 46 6 23-7 41t-36 19-42-12q-8-5-35-27-22-18-40-26-26-12-58-12-25 0-51 13 24 3 40 16 13 12 24 32 3 7 16 39 10 23 27 36t44 22q-13 6-38 6-29 0-55-15-20-11-45-36t-43-36q-28-16-61-16-16 0-29 4t-19 9q23 3 42 14 23 15 23 34 0 11-7 17t-19 5-23-12q-18-20-43-33t-54-12q-13 0-26 3T0 339q34 5 58 28t45 72q15 35 33 51 24 23 64 23 5 0 29-3 20-2 31 0 17 2 27 13 9 8 12 21 2 6 5 23 2 15 6 23 10 21 28 31 21 11 56 11-19 19-54 21-32 2-65-9t-49-28q2 46 25 80 25 37 68 50 49 14 113-4 18-5 30-1t19 24q16 41 71 35 48-5 79 6t59 42q8-81-77-135-15-9-23-19-6-8-9-19l-4-17q16 18 38 28 17 8 43 14 82 10 110 52 2-23-6-42-6-15-19-29-10-10-26-22-19-15-23-18-11-10-13-18 19 12 36 17t38 5q7-1 27-6t31-4q16 0 28 7 15 9 27 29 29-18 68-15 35 3 64 21-12-30-34-52-17-18-44-33-12-7-47-23-28-14-43-24zm-284 36h-96v-97h96v97z"}]]
              [:span "University of Helsinki"]]]

            [:div.moocfi-logo
             [:a {:href "https://www.mooc.fi"}
              [:img {:src "/moocfi-logo.png" :alt ""}]
              [:span "MOOC.fi"]]]]]))

(defn layout-page [{:keys [path title content navigation-tree]}]
  (str (h/html (hiccup.page/doctype :html5)
               [:html {:lang "en"}
                [:head
                 [:meta {:charset "utf-8"}]
                 [:title
                  (when (and (some? title)
                             (not= "/" path))
                    (str title " - "))
                  "Test-Driven Development MOOC"]
                 [:meta {:name "viewport", :content "width=device-width, initial-scale=1.0"}]
                 [:link {:rel "stylesheet" :href "https://cdn.jsdelivr.net/npm/@openfonts/open-sans-condensed_all@1.44.2/index.min.css"}]
                 [:link {:rel "stylesheet" :href "https://cdn.jsdelivr.net/npm/@fontsource/roboto-slab@5.0.17/index.min.css"}]
                 [:link {:rel "stylesheet" :href "https://cdn.simplecss.org/simple.css"}]
                 [:link {:rel "stylesheet" :href "/styles.css"}]
                 [:script {:type "module" :defer true :src "/custom-elements.mjs"}]]
                [:body
                 (layout-navigation path navigation-tree)

                 [:main
                  (when (= "/" path)
                    [:div#home-banner
                     [:div "Test-Driven Development"]
                     [:div "a plunge into the TDD programming technique"]])
                  [:h1 title]
                  ;; TODO: table of contents from markdown
                  content]

                 (layout-footer)]])))

(defn- parse-front-matter [^Node document]
  (let [front-matter (.getFirstChild document)]
    (when (instance? YamlFrontMatterBlock front-matter)
      (yaml/parse-string (.toString (.getChildChars front-matter))))))

(defn parse-markdown [^String markdown]
  (let [options (doto (MutableDataSet.)
                  (.set Parser/EXTENSIONS [(YamlFrontMatterExtension/create)
                                           (AnchorLinkExtension/create)]))
        parser (.build (Parser/builder options))
        renderer (.build (HtmlRenderer/builder options))
        document (.parse parser markdown)
        html (.render renderer document)
        metadata (parse-front-matter document)]
    {:html html
     :metadata metadata}))

(deftest test-parse-markdown
  (testing "plain markdown"
    (is (= {:html "<p>content</p>\n"
            :metadata nil}
           (parse-markdown "content"))))

  (testing "markdown with frontmatter"
    (is (= {:html "<p>content</p>\n"
            :metadata {:key "value"}}
           (parse-markdown "---\nkey: value\n---\n\ncontent"))))

  (testing "headings have anchor links"
    (is (= {:html "<h1><a href=\"#some-title\" id=\"some-title\">Some Title</a></h1>\n"
            :metadata nil}
           (parse-markdown "# Some Title")))))

(defn get-markdown-pages [pages]
  (->> pages
       (map (fn [[path markdown]]
              (let [path (-> path
                             (str/replace #"/index\.md$" "/")
                             (str/replace #"\.md$" "/"))]
                [path (parse-markdown markdown)])))
       (into {})))


(def recommended-reading-snippet
  (enlive/html-snippet (h/html [:aside.recommended-reading
                                [:h5.heading "📖 Recommended reading"]
                                [:div.content]])))

(defn schedule-step [{:keys [id icon content duration]}]
  (h/html [:tr
           [:td.icon-cell {:aria-hidden "true"} icon]
           [:td content]
           [:td duration]
           [:td [:input {:type "checkbox"
                         :data-schedule-step id}]]]))

(def exercise-schedule-snippet
  (enlive/html-snippet
   (str (h/html
         [:table#exercise-schedule
          [:thead
           [:tr
            [:th.icon-cell {:aria-hidden "true"}]
            [:th "What to read and do"]
            [:th "Duration (average)"]
            [:th "Done"]]]
          [:tbody
           (schedule-step {:id "chapter1"
                           :icon "📖"
                           :content [:a {:href "/1-tdd"} "Chapter 1: What is TDD"]})
           (schedule-step {:id "chapter2"
                           :icon "📖"
                           :content [:a {:href "/2-design"} "Chapter 2: Refactoring and design"]})
           (schedule-step {:id "exercise1"
                           :icon "👨‍💻"
                           :content [:a {:href "#exercise-1-small-safe-steps"} "Exercise 1: Small, safe steps"]
                           :duration "3 h"})
           (schedule-step {:id "exercise2"
                           :icon "👩‍💻"
                           :content [:a {:href "#exercise-2-tetris"} "Exercise 2: Tetris"]
                           :duration "38 h"})
           (schedule-step {:id "chapter3"
                           :icon "📖"
                           :content [:a {:href "/3-challenges"} "Chapter 3: The Untestables"]})
           (schedule-step {:id "exercise3"
                           :icon "👨‍💻"
                           :content [:a {:href "#exercise-3-untestable-code"} "Exercise 3: Untestable code"]
                           :duration "7 h"})
           (schedule-step {:id "chapter4"
                           :icon "📖"
                           :content [:a {:href "/4-legacy-code"} "Chapter 4: Legacy code"]})
           (schedule-step {:id "exercise4"
                           :icon "👩‍💻"
                           :content [:a {:href "#exercise-4-legacy-code"} "Exercise 4: Legacy code"]
                           :duration "6 h"})
           (schedule-step {:id "chapter5"
                           :icon "📖"
                           :content [:a {:href "/5-advanced"} "Chapter 5: Advanced techniques"]})
           (schedule-step {:id "exercise5"
                           :icon "👨‍💻"
                           :content [:a {:href "#optional-exercise-5-full-stack-web-app"} "(optional) Exercise 5: Full-stack web app"]
                           :duration "32 h"})
           (schedule-step {:id "exercise6"
                           :icon "👩‍💻"
                           :content [:a {:href "#exercise-6-conways-game-of-life"} "Exercise 6: Conway's Game of Life"]
                           :duration "13 h"})
           (schedule-step {:id "chapter6"
                           :icon "📖"
                           :content [:a {:href "/6-afterword"} "Chapter 6: To infinity and beyond"]})]]))))

(def tdd-decision-snippet
  (let [promise-text " During this course, I will not add any production code, unless it is required by a failing test."]
    (enlive/html-snippet
     (str (h/html
           [:div#tdd-decision
            [:div.not-yet-accepted-dialog {:style {:display "none"}}
             [:div.question
              "Before starting the exercises, you should make the following decision:"
              [:label.checkbox-label
               [:input {:type "checkbox"}]
               promise-text]]

             [:div.confirmation
              [:span {:aria-hidden "true"
                      :style {:margin-right "1ex"}}
               "✅"]
              " Good. And don't you forget that."]]

            [:div.previously-accepted-dialog {:style {:display "none"}}
             [:span {:aria-hidden "true"} "✅"]
             promise-text]

            [:div.content]])))))

(defn render-custom-elements [page-html]
  (enlive/sniptest page-html
                   [:recommended-reading] (fn [{:keys [content]}]
                                            (enlive/at recommended-reading-snippet
                                                       [:.content] (enlive/append content)))
                   [:exercise-schedule] (enlive/substitute exercise-schedule-snippet)
                   [:tdd-decision] (fn [{:keys [content]}]
                                     (enlive/at tdd-decision-snippet
                                                [:.content] (enlive/append content)))))

(defn render-markdown-pages [pages]
  (let [get-page-title (update-vals pages (comp :title :metadata))
        navigation-tree (enrich-navigation-tree navigation-tree get-page-title)]
    (->> pages
         (map (fn [[path page]]
                [path (layout-page {:path path
                                    :title (:title (:metadata page))
                                    :content (-> (:html page)
                                                 (render-custom-elements)
                                                 (h/raw))
                                    :navigation-tree navigation-tree})]))
         (into {}))))

(defn get-pages []
  (stasis/merge-page-sources
   {:public (stasis/slurp-directory "resources/public" #".*\.(html)$")
    :markdown (render-markdown-pages (get-markdown-pages (stasis/slurp-directory "data" #"\.md$")))}))

(deftest test-get-pages
  (let [pages (get-pages)
        chapter-1 (get pages "/1-tdd/")]
    (testing "navigation menu"
      (testing "shows page titles"
        (is (str/includes? chapter-1 "<li><a href=\"/exercises/\">Exercises</a></li>")))
      (testing "highlights the current page"
        (is (str/includes? chapter-1 "<li><a aria-current=\"page\" href=\"/1-tdd/\">Chapter 1: What is TDD</a></li>"))))))

(defn get-assets []
  (optimus.assets/load-assets "public" [#".*\.(css|png|jpg|mjs)$"]))

(def app
  (-> (stasis/serve-pages get-pages)
      (optimus/wrap get-assets optimizations/none optimus.strategies/serve-live-assets)
      wrap-content-type))

(defn export []
  (let [assets (optimizations/all (get-assets) {})
        pages (get-pages)]
    (stasis/empty-directory! export-dir)
    (optimus.export/save-assets assets export-dir)
    (stasis/export-pages pages export-dir {:optimus-assets assets})))

#_(prof/serve-ui 3001)
