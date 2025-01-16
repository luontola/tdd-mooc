(ns tdd-mooc.elements
  (:require [hiccup.page]
            [hiccup.util]
            [hiccup2.core :as h]
            [net.cgrand.enlive-html :as enlive]))

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
