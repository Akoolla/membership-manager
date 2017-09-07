(ns membership-manager.routing.middleware
  (:require [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.webjars :refer [wrap-webjars]]
            [ring.util.response :as resp]
            [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])

            [hiccup.page :as h]
            [membership-manager.store.users :as users]))

(defn wrap
  ""
  [routes]
  (->
   routes
   (friend/authenticate
    {:allow-anon? true
     :login-uri "/login"
     :default-landing-uri "/"
     :unauthorized-handler #(-> (h/html5
                                 [:h2 "You do not have sufficient privileges to access " (:uri %)])
                                resp/response
                                (resp/status 401))
     :credential-fn #(users/authenticate %)
     ;;:credential-fn #(creds/bcrypt-credential-fn @users %)
     :workflows [(workflows/interactive-form)]})
   wrap-webjars
   (wrap-defaults site-defaults)))
