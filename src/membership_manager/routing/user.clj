(ns membership-manager.routing.user
  (:require  [compojure.core :refer :all]
             [ring.util.response :as resp]
             [cemerick.friend :as friend]
             [membership-manager.store.users :as users]
             [membership-manager.view.welcome :as view]))

(defroutes all-routes
  (GET "/login" req (view/log-test))
  (GET "/logout" req
       (friend/logout* (resp/redirect (str (:context req) "/"))))

  (GET "/change-password/" [] (view/change-password))
  (POST "/change-password/" [password]
        (let [username (:username (friend/current-authentication))]
          (users/change-password username password)
          (view/main-page))))
