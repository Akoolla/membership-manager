(ns membership-manager.view.accounts
  (:require
   [hiccup.page :as h]
   [membership-manager.view.common :refer :all]))

(defn change-password
  []
  (bootstrap-page
   {:title "Change password"}
   (form
    {:method "POST" :enctype "multipart/form-data" :action "/change-password/"}
    [:div
     [:h3 "Please change your password"]
     [:div#form-group
      [:label {:for "password"} "New password"]
      [:input {:id "password"
              :class "form-control"
              :type "password"
              :autofocus "autofocus"
              :name "password"}]
      [:button { :type "submit"} "Change"]]])))
