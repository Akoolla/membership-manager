(ns membership-manager.view.welcome
  (:require
   [hiccup.page :as h]
   [membership-manager.view.common :refer :all]))

(defn main-page
  []
  (bootstrap-page
   {:title "Welcome"}
   [:div
    [:h1 "Welcome to the Membership Manager for [something]"]
    [:p "This will be a application to help manage membership to [something]"]]))
