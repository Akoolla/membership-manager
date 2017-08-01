(ns membership-manager.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :as response]
            [membership-manager.routing.middleware :as middleware]
            [membership-manager.view.accounts :as account-views]
            [membership-manager.view.welcome :as view]
            [membership-manager.store.users :as users]
            [environ.core :refer [env]]))

(defn- create-admin-user
  []
  (if (= "true" (env :create-admin-user))
    (let [username (env :default-admin)
          password (env :defaul-password)]
      (if (nil? (users/user-by-login username))
        (users/create-admin {:username username :password password :change-password true})))
    (println "Not creating admin account")))

(defroutes app-routes
  (GET "/" []
       (if (:change-password (middleware/authenticated?))
         (account-views/change-password)
         (view/main-page (middleware/authenticated?))))
  (GET "/log-in/" [] (view/log-in))
  (POST "/change-password/" [] "Changed")
  (route/not-found "Not Found"))

(def app
  (middleware/wrap
   app-routes))

;;Do some things on boot
(create-admin-user)
