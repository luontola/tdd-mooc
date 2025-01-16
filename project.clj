(defproject tdd-mooc "1-SNAPSHOT"

  :dependencies [[clj-commons/clj-yaml "1.0.29"]
                 [com.clojure-goes-fast/clj-async-profiler "1.6.0"]
                 [com.vladsch.flexmark/flexmark-all "0.64.8"]
                 [enlive "1.1.6"]
                 [hiccup "2.0.0-RC2"]
                 [medley "1.4.0"]
                 [optimus "2025.01.15"]
                 [org.clojure/clojure "1.12.0"]
                 [org.clojure/java.data "1.2.107"]
                 [ring/ring-core "1.13.0"]
                 [stasis "2023.11.21"]]
  :managed-dependencies [[org.jsoup/jsoup "1.18.3"]]
  :pedantic? :warn

  :source-paths ["src"]
  :test-paths ["src"]
  :resource-paths ["resources"]
  :global-vars {*warn-on-reflection* false}
  :javac-options ["--release" "21"]
  :jvm-opts ["-XX:-OmitStackTraceInFastThrow"
             "-Djdk.attach.allowAttachSelf"]

  :ring {:handler tdd-mooc.web/app
         :auto-refresh? true
         :refresh-paths ["src" "resources" "data"]}
  :aliases {"export" ["run" "-m" "tdd-mooc.web/export"]
            "kaocha" ["with-profile" "+kaocha" "run" "-m" "kaocha.runner"]}

  :profiles {:dev {:dependencies [[lambdaisland/kaocha "1.91.1392"]
                                  [org.clojure/test.check "1.1.1"]]
                   :plugins [[lein-ancient "0.7.0"]
                             [lein-ring "0.12.6"]]}
             :kaocha {}})
