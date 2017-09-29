(ns membership-manager.handler
  (:import java.net.URI)
  (:require [compojure.core :refer :all]
            [compojure.core :as compojure :refer (GET POST ANY defroutes)]

            [hiccup.page :as h]
            [ring.middleware.webjars :refer [wrap-webjars]]
            [environ.core :refer [env]]
            
            [membership-manager.store.users :as users]
            [membership-manager.routing.middleware :as middleware]
            [membership-manager.routing.all :as all]
            [membership-manager.routing.admin :as admin]
            [membership-manager.routing.user :as user]))

(defn- create-admin-user
  []
  (if (= "true" (env :create-admin-user))
    (let [username (env :default-admin)
          password (env :default-password)]
      (if (nil? (users/user-by-login username))
        (users/create-admin {:username username :password password})))
    (println "Not creating admin account")))

(defroutes app-routes
  all/all-routes
  user/all-routes
  admin/all-routes)

(def app
  (->
   new-routes
   (friend/authenticate
    {:allow-anon? true
     :login-uri "/login"
     :default-landing-uri "/"
     :unauthorized-handler #(-> (h/html5
                                 [:h2 "You do not have sufficient privileges to access " (:uri %)])
                                resp/response
                                (resp/status 401))
     :credential-fn #(users/authenticate %)
     :workflows [(workflows/interactive-form)]})

   wrap-webjars
   (wrap-defaults site-defaults)))

;;Do some things on boot
(create-admin-user)
