(ns membership-manager.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [membership-manager.view.welcome :as view]))

(defroutes app-routes
  (GET "/" [] (view/main-page))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
