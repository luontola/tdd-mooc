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
                 [:link {:rel "stylesheet", :href "/remark.css"}]]
                [:body
                 [:h1 title]
                 [:main content]]])))

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
