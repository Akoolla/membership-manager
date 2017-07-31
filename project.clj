(defproject membership-manager "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [ring/ring-defaults "0.2.1"]
                 [ring-webjars "0.1.1"]

                 [hiccup "1.0.5"]
                 [org.webjars/bootstrap "3.3.6"]
                 [org.webjars/bootswatch-paper "3.3.5+4"]

                 [com.cemerick/friend "0.2.3"]
                 [alandipert/enduro "1.2.0"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler membership-manager.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
