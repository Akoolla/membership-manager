(ns membership-manager.userstore-test
  (:require
   [clojure.test :refer :all]
   [membership-manager.store.users :as users]
   [membership-manager.store.env :as env]))

(deftest create-admin
  (let [details {:username "admin@roxy.org"
                 :password "password"}
        store-location (clojure.string/join
                        [env/storage-directory "users.edn"])]
    
    (testing "Create user"
      (let [created (users/create-admin details)]
        (is (not(nil? created)))))

    (testing "Can return admin user"
      (let [admin (users/user-by-login "admin@roxy.org")]
        (is (not(nil? admin)))))

    (testing "Can authenticate"
      (let [response (users/authenticate
                      {:username "admin@roxy.org" :password "password"})]
        (is (= "admin@roxy.org" (:username response)))))

    (clojure.java.io/delete-file store-location)
    ))
