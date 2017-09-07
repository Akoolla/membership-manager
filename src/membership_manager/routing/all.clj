(ns membership-manager.routing.all
  (:require  [compojure.core :refer :all]
             [membership-manager.view.welcome :as view]))

(defroutes all-routes
   (GET "/" [] (view/main-page)))
