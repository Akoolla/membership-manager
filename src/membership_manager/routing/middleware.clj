(ns membership-manager.routing.middleware
  (:require
   [membership-manager.store.users :as users]
   [ring.middleware.webjars :refer [wrap-webjars]]
   [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
   [ring.util.response :as resp]
   [cemerick.friend :as friend]
   (cemerick.friend [workflows :as workflows]
                    [credentials :as creds])))

(def ^:dynamic *current-user*)
(defn authenticated? [] *current-user*)

(defn add-user-data
  [handler]
  (fn [request]
    (binding [*current-user*
              (users/user-by-login
               (:username
                (friend/current-authentication request)))]
      (handler request))))

(defn wrap
  ""
  [fn]
  (-> fn
      add-user-data
      (friend/authenticate
       {:allow-anon? true
        :login-uri "/log-in/"
        :default-landing-uri "/"
        :credential-fn users/authenticate
        :workflows [(workflows/interactive-form)]
        })
      wrap-webjars
      (wrap-defaults site-defaults)))

(defn log-out [location]
 (friend/logout* (resp/redirect location)))
