(ns membership-manager.view.welcome
  (:require
   [hiccup.page :as h]
   [membership-manager.view.common :refer :all]
   [environ.core :refer [env]]))

(defn log-in-form
  []
  [:div
  [:h4 "Please sign-in"]
  (form
   {:method "POST" :enctype "multipart/form-data" :action "/log-in/"}
   [:div.form-group
    [:label {:for "email-address"} "Email address"]
    [:input {:id "email-address"
             :class "form-control"
             :autofocus "autofocus"
             :placeholder "someone@somewhere.com"
             :type "email"
             :name "username"}]]

   [:div.form-group
    [:label {:for "password"} "Password"]
    [:input {:type "password"
             :id "password"
             :class "form-control"}]]

   [:button {:type "submit" :value "Log-in"} "Submit"])])

(defn log-in
  []
  (bootstrap-page
   {:title "Log-in"}
   (log-in-form)))

(defn main-page
  [authenticated?]
  (bootstrap-page
   {:title "Welcome"}
   [:h2 (clojure.string/join ["Welcome "
                               (:username authenticated?)
                               " to " app-name])]
    (if authenticated?
      [:p "Logged in, show some love"]
      (log-in-form))))
