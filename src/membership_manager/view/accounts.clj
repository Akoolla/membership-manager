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
     [:h3 "Change your password"]
     [:div#form-group
      [:label {:for "password"} "New password"]
      [:input {:id "password"
              :class "form-control"
              :type "password"
              :autofocus "autofocus"
              :name "password"}]
      [:button { :type "submit"} "Change"]]])))


(defn member-list
  [users]
  (bootstrap-page
   {:title "Current Members"}
   [:div
    [:h4 "Members"]
    [:table.table
     [:thead
      [:tr
       [:th "No."]
       [:th "Usermame/Email"]
       [:th "Name"]
       [:th "Expiry Date"]]]
     [:tbody
      (for [user users]
        [:tr
         [:th (:no user)]
         [:td (:username user)]
         [:td (clojure.string/join ", " [(:first-name user) (:second-name user)])]
         [:td (:expiry user)]])
      ]]]))
