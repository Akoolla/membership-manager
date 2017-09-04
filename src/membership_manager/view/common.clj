(ns membership-manager.view.common
  (:require
   [hiccup.page :as h]
   [ring.util.anti-forgery :refer [anti-forgery-field]]
   [membership-manager.routing.middleware :as middleware]
   [cemerick.friend :as friend]
   [environ.core :refer [env]]))

(def app-name
  (str (or (env :app-name) "Membership Manager")))

(defmacro form
  [params & contents]
  `[:form ~params
    (anti-forgery-field)
    ~@contents])

(defn nav-bar
  "Renders a navigation bar somewhere"
  [user-details]
  [:nav.navbar.navbar-default
   [:div.container-fluid
    [:div.navbar-header
     [:button.navbar-toggle.collapsed {:data-toggle "collapse" :data-target "#bs-example-navbar-collapse-1" :aria-expanded "false"}
      [:span.sr-only "Toggle navigation"]
      [:span.icon-bar]
      [:span.icon-bar]
      [:span.icon-bar]]
     [:a.navbar-brand {:href "/"} app-name]]
    [:div.collapse.navbar-collapse {:id "bs-example-navbar-collapse-1"}
     [:ul.nav.navbar-nav
      [:li [:a {:href "/admin/members/"} "Users List" ]]
      [:li [:a {:href "/admin/members/add"} "Add User" ]]
      [:li [:a {:href "/change-password/"} "Change Password" ]]
      [:li [:a {:href "/logout"} "Log-out" ]]
      ]]]])
     
(defmacro bootstrap-page
  [misc & elts]
  (let [misc
        (merge

         {:breadcrumbs
          [{:href "/" :name "Home"}]
          :title "Some page"
          :css []
          :onload false
          :navbar true}

         misc)]
    `(h/html5
      [:head
       [:meta {:charset "utf-8"}]
       [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
       (h/include-css "/assets/bootstrap/css/bootstrap.min.css")
       (h/include-css "/assets/bootstrap/css/bootstrap-theme.min.css")
       [:script {:src "http://code.jquery.com/jquery-2.0.3.min.js"}]
       [:script {:src "/assets/bootstrap/js/bootstrap.min.js"}]
       (for [c# ~(:css misc)] (h/include-css c#))
       [:title ~(:title misc)]]
      [:body {:onload ~(:onload misc)}
       [:div
        (if (empty? (friend/current-authentication))
          ""
          (nav-bar (friend/current-authentication)))
        [:div.container
        ~@elts]]
       ])
    )
  )
