(ns membership-manager.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :as response]
            [membership-manager.routing.middleware :as middleware]
            [membership-manager.view.welcome :as view]
            [membership-manager.store.users :as users]
            [environ.core :refer [env]]))

(defn- create-admin-user
  []
  (if (= "true" (env :create-admin-user))
    (let [username (env :default-admin)
          password (env :defaul-password)]
      (if (nil? (users/user-by-login username))
        (users/create-admin {:username username :password password})))
    (println "Not creating admin account")))

;;Do some things on boot
(create-admin-user)

(defroutes app-routes
  (GET "/" [] (view/main-page (middleware/authenticated?)))
  (GET "/log-in/" [] (view/log-in))
  (route/not-found "Not Found"))

(def app
  (middleware/wrap
   app-routes))
