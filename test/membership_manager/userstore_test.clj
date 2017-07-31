(ns membership-manager.userstore-test
  (:require
   [clojure.test :refer :all]
   [membership-manager.store.users :as users]
   [membership-manager.store.env :as env]))

(deftest create-admin
  (let [details {:username "admin"
                 :password "password"}
        store-location (clojure.string/join
                        [env/storage-directory "users.edn"])]
    
    (testing "Create user"
      (let [created (users/create-admin details)]
        (is (not(nil? created)))))

    (testing "Can return admin user"
      (let [admin (users/user-by-login "admin")]
        (is (not(nil? admin)))))
             
    (clojure.java.io/delete-file store-location)))
