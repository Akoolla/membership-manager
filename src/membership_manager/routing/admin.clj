(ns membership-manager.routing.admin
  (:require  [compojure.core :refer :all]
             [cemerick.friend :as friend]
             [java-time :as t]
             [membership-manager.store.users :as users]
             [membership-manager.view.accounts :as account-views]))

(defroutes members
  (GET "/members/" [] (account-views/member-list (vals (users/list-all))))
  (GET "/members/add" [] (account-views/add-user))
  (POST "/members/add" {params :params}
        (let [details {:username (:username params)
                       :first-name (:first-name params)
                       :second-name (:second-name params)
                       :password "password"  ;;TODO auto-generate password and show on user added screen - will eventually be email to user?
                       :change-password true}]
          (users/create-user details #{} (t/instant))
          (account-views/member-list (vals (users/list-all))))))

(defroutes all-routes
  (context "/admin" []
           (friend/wrap-authorize members #{::users/admin})))
