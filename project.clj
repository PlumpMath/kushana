(defproject kushana "0.1.0"
  :description ""
  :url "https://github.com/MysteryMachine/kushana"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src"]
  :test-paths   ["test"]
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "0.0-3058" :scope "provided"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.omcljs/om "0.8.8"]
                 [ring "1.3.2"]
                 [ring/ring-defaults "0.1.4"]
                 [compojure "1.3.2"]
                 [enlive "1.1.6"]
                 [environ "1.0.0"]
                 [http-kit "2.1.19"]
                 [jamesmacaulay/zelkova "0.4.0"]
                 [com.taoensso/sente "1.6.0"]
                 [cljsjs/babylon "2.2.0-0"]]
  :plugins [[lein-cljsbuild "1.1.0"]
            [lein-environ "1.0.0"]]
  :min-lein-version "2.5.0"
  :uberjar-name "server.jar"
  :cljsbuild
  {:builds
   {:app {:source-paths ["src"]
          :compiler {:output-to     "resources/public/js/app.js"
                     :output-dir    "resources/public/js/out"
                     :source-map    "resources/public/js/out.js.map"
                     :preamble      ["react/react.min.js"]
                     :optimizations :none
                     :pretty-print  true}}}}
  :prep-tasks ["javac" "compile"]
  :profiles
  {:dev
   {:source-paths ["env/dev"]
    :test-paths   ["test"]
    :dependencies [[figwheel "0.2.5"]
                   [figwheel-sidecar "0.2.5"]
                   [com.cemerick/piggieback "0.1.5"]
                   [weasel "0.6.0"]]
    :repl-options {:init-ns server.core
                   :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
    :plugins [[lein-figwheel "0.2.5"]]
    :figwheel {:http-server-root "public"
               :server-port 3449
               :css-dirs ["resources/public/css"]
               :ring-handler server.core/http-handler}
    :env {:is-dev true}
    :cljsbuild
    {:test-commands { "test" ["phantomjs"
                              "env/test/js/unit-test.js"
                              "env/test/unit-test.html"] }
     :builds {:app  {:source-paths ["env/dev"]}
              :test {:source-paths ["src" "test"]
                     :compiler
                     {:output-to     "resources/public/js/app_test.js"
                      :output-dir    "resources/public/js/test"
                      :source-map    "resources/public/js/test.js.map"
                      :preamble      ["react/react.min.js"]
                      :optimizations :whitespace
                      :pretty-print  false}}}}}

   :uberjar {:source-paths ["env/prod"]
             :hooks [leiningen.cljsbuild]
             :env {:production true}
             :omit-source true
             :aot :all
             :main server.core
             :cljsbuild
             {:builds {:app
                       {:source-paths ["env/prod"]
                        :compiler {:optimizations :advanced
                                   :pretty-print false}}}}}})
