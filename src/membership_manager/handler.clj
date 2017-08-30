(ns membership-manager.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :as response]
            [membership-manager.routing.middleware :as middleware]
            [membership-manager.view.accounts :as account-views]
            [membership-manager.view.welcome :as view]
            [membership-manager.store.users :as users]
            [cemerick.friend :as friend]
            [environ.core :refer [env]]))

(defn- create-admin-user
  []
  (if (= "true" (env :create-admin-user))
    (let [username (env :default-admin)
          password (env :default-password)]
      (if (nil? (users/user-by-login username))
        (users/create-admin {:username username
                             :first-name "Default"
                             :second-name "Administrator"
                             :password password
                             :change-password true})))
    (println "Not creating admin account")))

(defroutes admin-routes
  (GET "/members/" [] (account-views/member-list (vals (users/list-all)))))

(defroutes app-routes
  (GET "/" []
       (if (:change-password (middleware/authenticated?))
         (account-views/change-password)
         (view/main-page (middleware/authenticated?))))
  
  (GET "/log-in/" [] (view/log-in))

  (GET "/change-password/" [] (account-views/change-password))
  (POST "/change-password/" [password]
        (let [username (:username (middleware/authenticated?))]
          (users/change-password username password)
          (view/main-page (middleware/authenticated?))))

  (context "/admin" []  admin-routes)
  
  (route/not-found "Not Found"))

(def app
  (middleware/wrap
   app-routes))

;;Do some things on boot
(create-admin-user)
