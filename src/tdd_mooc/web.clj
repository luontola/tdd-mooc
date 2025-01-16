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
            [stasis.core :as stasis]
            [tdd-mooc.layout :as layout])
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
                [path (layout/page {:path path
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
