(ns membership-manager.userstore-test
  (:require
   [clojure.test :refer :all]
   [java-time :as t]
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
    (let [user (users/create-admin {:username "admin" :password "changeMe" :first-name "wibble"})
          user (users/change-password "admin" "changed")
          user (users/authenticate {:username "admin" :password "changed"})]
      (is (not(nil? user)))
      (is (= "admin" (:username user)))
      (is (= "wibble" (:first-name user))))))

(deftest can-list-users
  ;;TODO Loop
  (users/create-admin {:username "one@members.org"})
  (users/create-admin {:username "two@members.org"})
  (users/create-admin {:username "three@members.org"})
  (testing "List all users"
    (let [user-list (users/list-all)]
      (is (not(nil? user-list))))))

(deftest create-user
  (let [new-user (users/create-user {:username "four@members.org"
                                     :password "changeme"
                                     :first-name "four"} #{}
                                    (t/instant))
        new-user (users/user-by-login "four@members.org")]
    (testing "default-user-created"
      (is (not(nil? new-user)))
      (is (= "four@members.org" (:username new-user)))
      (is (not(nil? (:expiry new-user)))))))

;(;deftest membership-numbers
  ;(testing "generate-membership-number"
    ;(let [user-one (users/create-user {:username "five@members.org" :password "changeme"} #{} (t/local-date))]
      ;(println (users/generate-membership-number))
      ;(is (= 1 1)))))
