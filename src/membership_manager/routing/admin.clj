(ns membership-manager.routing.admin
  (:require  [compojure.core :refer :all]
             [cemerick.friend :as friend]
             [java-time :as t]
             [membership-manager.store.users :as users]
             [membership-manager.view.accounts :as account-views]
             [membership-manager.email :as email]))

(defroutes members
  (GET "/members/" [] (account-views/member-list (vals (users/list-all))))
  (GET "/members/add" [] (account-views/add-user))
  (POST "/members/add" {params :params}
        (let [username (:username params)
              password (users/generate-password) 
              details {:username username
                       :first-name (:first-name params)
                       :second-name (:second-name params)
                       :password password  
                       :change-password true}
              expiry-date (-> (t/zoned-date-time)
                              (t/plus (t/years 1))
                              t/instant)]
          (users/create-user details #{} expiry-date)
          (email/account-created username password)
          (account-views/member-list (vals (users/list-all))))))

(defroutes all-routes
  (context "/admin" []
           (friend/wrap-authorize members #{::users/admin})))
