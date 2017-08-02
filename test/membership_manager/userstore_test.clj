(ns membership-manager.userstore-test
  (:require
   [clojure.test :refer :all]
   [membership-manager.store.users :as users]
   [clojure.java.io :refer [delete-file]]
   [environ.core :refer [env]]))

(def storage-file
  (clojure.string/join [(env :storage-dir) "/" "users.edn"]))

(def user-details
  {:username "admin@members.org"
   :password "password"})

(defn test-ns-hook
  "Seems to allow us to add a before/after action to all tests"
  []
  (test-all-vars 'membership-manager.userstore-test)
  (delete-file storage-file))

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
  (testing "Change password"
    (let [user (users/create-admin {:username "admin" :password "changeMe"})
          user (users/change-password "admin" "changed")
          user (users/authenticate {:username "admin" :password "changed"})]
      (is (not(nil? user))))))
