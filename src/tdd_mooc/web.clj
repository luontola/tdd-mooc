(ns tdd-mooc.web
  (:require [stasis.core :as stasis]))

(def export-dir "target/dist")

(defn get-pages []
  (stasis/slurp-directory "resources/public" #".*\.(html|css|js)$"))

(def app (stasis/serve-pages get-pages))

(defn export []
  (stasis/empty-directory! export-dir)
  (stasis/export-pages (get-pages) export-dir))
