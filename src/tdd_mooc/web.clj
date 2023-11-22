(ns tdd-mooc.web
  (:require [hiccup.page :as page]
            [hiccup2.core :as h]
            [stasis.core :as stasis]))

(def export-dir "target/dist")

(defn layout-page [content]
  (str (h/html {:mode :html}
               (page/doctype :html5)
               [:html
                [:head
                 [:meta {:charset "utf-8"}]
                 [:title "TDD MOOC"]
                 [:meta {:name "viewport", :content "width=device-width, initial-scale=1.0"}]
                 [:link {:rel "stylesheet", :href "/style.css"}]]
                [:body
                 [:h1 "TDD MOOC"]
                 [:main content]]])))

(defn get-pages []
  (merge (stasis/slurp-directory "resources/public" #".*\.(html|css|js)$")
         {"/" (layout-page (h/raw "hello <u>there</u>"))}))

(def app (stasis/serve-pages get-pages))

(defn export []
  (stasis/empty-directory! export-dir)
  (stasis/export-pages (get-pages) export-dir))
