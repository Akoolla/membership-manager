(ns membership-manager.view.welcome
  (:require
   [hiccup.page :as h]
   [membership-manager.view.common :refer :all]))

(defn main-page
  [authenticated?]
  (bootstrap-page
   {:title "Welcome"}
   [:div
    [:h1 "Welcome to the Membership Manager for [something]"]
    (if authenticated?
      [:p "Logged in, show some love"]
      [:p "Login-Credentials"])]))
