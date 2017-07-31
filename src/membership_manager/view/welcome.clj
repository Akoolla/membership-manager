(ns membership-manager.view.welcome
  (:require
   [hiccup.page :as h]
   [membership-manager.view.common :refer :all]))



(defn log-in-form
  []
  (form
   {:method "POST" :enctype "multipart/form-data"}
   [:div#form-group
    [:label {:for "email-address"} "Email address"]
    [:input {:id "email-address"
             :class "form-control"
             :autofocus "autofocus"
             :placeholder "someone@somewhere.com"
             :type "email"
             :name "username"}]]

   [:div#form-group
    [:label {:for "password"} "Password"]
    [:input {:type "password"
             :id "password"
             :class "form-control"}]]

   [:button {:type "submit" :value "Log-in"} "Submit"]))

(defn log-in
  []
  (bootstrap-page
   {:title "Log-in"}
   (log-in-form)))

(defn main-page
  [authenticated?]
  (bootstrap-page
   {:title "Welcome"}
   [:div
    [:h1 "Welcome to the Membership Manager for [something]"]
    (if authenticated?
      [:p "Logged in, show some love"]
      (log-in-form))]))
