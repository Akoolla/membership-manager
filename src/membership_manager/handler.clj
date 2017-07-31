(ns membership-manager.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :as response]
            [membership-manager.routing.middleware :as middleware]
            [membership-manager.view.welcome :as view]))

(defroutes app-routes
  (GET "/" [] (view/main-page (middleware/authenticated?)))
  (GET "/log-in/" [] (view/log-in))
  (route/not-found "Not Found"))

(def app
  (middleware/wrap
   app-routes))
