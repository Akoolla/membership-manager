(ns membership-manager.userstore-test
  (:require
   [clojure.test :refer :all]
   [membership-manager.store.users :as users]
   [membership-manager.store.env :as env]
   [clojure.java.io :refer [delete-file]]))

(def store-location
   (clojure.string/join [env/storage-directory "users.edn"]))

(def user-details
  {:username "admin@members.org"
   :password "password"})

(deftest create-admin
  (testing "Create user"
    (let [created (users/create-admin user-details)]
      (is (not(nil? created)))))

  (testing "Can return admin user"
    (let [admin (users/user-by-login (:username user-details))]
      (is (not(nil? admin)))))

  (testing "Can authenticate"
    (let [response (users/authenticate user-details)]
        (is (= (:username user-details) (:username response))))))

(deftest ammend-details
  (testing "Change password")
  (delete-file store-location))


