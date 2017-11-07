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
                 [alandipert/enduro "1.2.0"]

                 [environ "1.1.0"]
                 [clojure.java-time "0.3.0"]
                 [com.draines/postal "2.0.2"]
                 [clj-time "0.14.0"]]

  :plugins [[lein-ring "0.9.7"]
            [lein-environ "1.1.0"]]
  :ring {:handler membership-manager.handler/app}
  :profiles
  {:default {:env {:app-name "Membership Manager"
                   :create-admin-user "true"
                   :storage-dir "data"
                   :default-admin "admin@mm.org"
                   :default-password "password"
                   :site-url "http://localhost:3000"}}

   :test {:env {:create-admin-user "false"
                :storage-dir "temp-data"}}
})
