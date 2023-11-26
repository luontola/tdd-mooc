(defproject tdd-mooc "1-SNAPSHOT"

  :dependencies [[clj-commons/clj-yaml "1.0.27"]
                 [com.vladsch.flexmark/flexmark-all "0.64.8"]
                 [hiccup "2.0.0-RC2"]
                 [medley "1.4.0"]
                 [optimus "2023.11.21"]
                 [org.clojure/clojure "1.11.1"]
                 [org.clojure/java.data "1.0.95"]
                 [ring/ring-core "1.11.0-RC1"]
                 [stasis "2023.11.21"]]
  :managed-dependencies []
  :pedantic? :warn
  :global-vars {*warn-on-reflection* false}
  :javac-options ["--release" "21"]
  :jvm-opts ["-XX:-OmitStackTraceInFastThrow"]

  :ring {:handler tdd-mooc.web/app
         :auto-refresh? true
         :refresh-paths ["src" "resources" "data"]}
  :aliases {"export" ["run" "-m" "tdd-mooc.web/export"]
            "kaocha" ["with-profile" "+kaocha" "run" "-m" "kaocha.runner"]}

  :profiles {:dev {:dependencies [[lambdaisland/kaocha "1.87.1366"]
                                  [org.clojure/test.check "1.1.1"]]
                   :plugins [[lein-ancient "0.7.0"]
                             [lein-ring "0.12.6"]]}
             :kaocha {}})
