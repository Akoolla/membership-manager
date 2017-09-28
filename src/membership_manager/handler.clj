(ns membership-manager.handler
  (:import java.net.URI)
  (:require [compojure.core :refer :all]
            [compojure.core :as compojure :refer (GET POST ANY defroutes)]
            [compojure.route :as route]
                        
            [ring.util.response :as resp]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])
            [cemerick.friend.credentials :refer (hash-bcrypt)]
            
            [hiccup.page :as h]
            [ring.middleware.webjars :refer [wrap-webjars]]

            [environ.core :refer [env]]
            
            [membership-manager.store.users :as users]
            [membership-manager.view.welcome :as view]
            [membership-manager.view.accounts :as account-views]))

(defn- create-admin-user
  []
  (if (= "true" (env :create-admin-user))
    (let [username (env :default-admin)
          password (env :default-password)]
      (if (nil? (users/user-by-login username))
        (users/create-admin {:username username :password password})))
    (println "Not creating admin account")))

(defroutes admin-routes
  (GET "/members/" [] (account-views/member-list (vals (users/list-all))))
  (GET "/members/add" [] (account-views/add-user))
  (POST "/members/add" {params :params}
        (let [details {:username (:username params)
                       :first-name (:first-name params)
                       :second-name (:second-name params)
                       :password "password"  ;;TODO auto-generate password and show on user added screen - will eventually be email to user?
                       :change-password true}]
          (users/create-user details #{})
          (account-views/member-list (vals (users/list-all))))))

(defroutes new-routes
  (GET "/" [] (view/main-page))
  (GET "/login" req (view/log-test))
  (GET "/logout" req
       (friend/logout* (resp/redirect (str (:context req) "/"))))

  (GET "/change-password/" [] (view/change-password))
  (POST "/change-password/" [password]
        (let [username (:username (friend/current-authentication))]
          (users/change-password username password)
          (view/main-page)))

  (context "/admin" []
           (friend/wrap-authorize admin-routes #{::users/admin})))

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
     ;;:credential-fn #(creds/bcrypt-credential-fn @users %)
     :workflows [(workflows/interactive-form)]})

   wrap-webjars
   (wrap-defaults site-defaults)))

;;Do some things on boot
(create-admin-user)
