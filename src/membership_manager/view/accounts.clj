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
         [:th (:mem-number user)]
         [:td (:username user)]
         [:td (clojure.string/join ", " [(:first-name user) (:second-name user)])]
         [:td (:expiry user)]])
      ]]]))

(defn add-user
  "View for adding a new user"
  []
  (bootstrap-page
   {:title "Add new user"}
   (form
    {:method "POST" :enctype "multipart/form-data" :action "/admin/members/add"}
    [:div
     [:h3 "Add User"]
     [:div.form-group
      [:label {:for "username"} "Username/Email"]
      [:input.form-control {:id "username"
                            :name "username"
                            :placeholder "User Name"
                            :autofocus "autofocuss"}]]
     [:div.form-group
      [:label {:for "first-name"} "First Name"]
      [:input.form-control {:id "first-name"
                            :name "first-name"
                            :placeholder "First Name"}]]
     [:div.form-group
      [:label {:for "second-name"} "Second Name"]
      [:input.form-control {:id "second-name"
                            :name "second-name"
                            :placeholder "Second Name"}]]

     [:div.form-group
      [:label {:for "role"} "User Role"]
      [:select.form-control {:name "role"}
       [:option {:value "::User"} "User"]
       [:option {:value "::Admin"} "Admin"]]]
     
     [:button.btn.btn-default {:type "submit"} "Add User"]])))
