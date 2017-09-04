(ns membership-manager.view.welcome
  (:require
   [hiccup.page :as h]
   [membership-manager.view.common :refer :all]
   [cemerick.friend :as friend]
   [environ.core :refer [env]]))

(defn log-in-form
  []
  [:div
  [:h4 "Please sign-in"]
  (form
   {:method "POST" :enctype "multipart/form-data" :action "login"}
   [:div.form-group
    [:label {:for "username"} "Email address"]
    [:input {:id "username"
             :class "form-control"
             :autofocus "autofocus"
             ;;:placeholder "someone@somewhere.com"
             :type "text"
             :name "username"}]]

   [:div.form-group
    [:label {:for "password"} "Password"]
    [:input {:type "password"
             :id "password"
             :class "form-control"
             :name "password"}]]

   [:button {:type "submit" :value "Log-in"} "Submit"])])


(def login-form
  (form
   {:method "POST" :action "login"}
   [:div "Usernameawdawd" [:input {:type "text" :name "username"}]]
   [:div "Password" [:input {:type "password" :name "password"}]]
   [:div [:input {:type "submit" :class "button" :value "Login"}]]))

(defn log-test
  []
  (bootstrap-page
   {:title "Login"}
   (log-in-form)))

(defn change-password
  []
  (bootstrap-page
   {:title "Change password"}
   (form
    {:method "POST" :enctype "multipart/form-data" :action "/change-password/"}
    [:div
     [:h3 "Change your password"]
     [:div#form-group
      [:label {:for "password"} "New password"]
      [:input {:id "password"
              :class "form-control"
              :type "password"
              :autofocus "autofocus"
              :name "password"}]
      [:button { :type "submit"} "Change"]]])))

(defn welcome-page
  []
  (bootstrap-page
   {:title "Home"}
    [:div
    [:p (if-let [identity (friend/current-authentication)]
          (apply str  "Logged in, with these roles: "
                 (:roles (friend/current-authentication)))
             (log-in-form))]]))
  
(defn main-page
  []
  (let [authenticated (friend/current-authentication)]
    (if (:change-password authenticated)
      (change-password)
      (welcome-page))))
