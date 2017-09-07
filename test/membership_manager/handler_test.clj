(ns membership-manager.handler-test
  (:require [clojure.test :refer :all]
            ;;[ring.mock.request :as mock]
            [membership-manager.email :refer :all]
            [membership-manager.handler :refer :all]))

;(deftest test-email
 ; (invite-collaborator "asdsad" "asdsad" "asdsadsad")
  ;(testing "asdsad"
   ; (= 1 1)))

;(deftest test-app
  ;(testing "main route"
   ; (let [response (app (mock/request :get "/"))]
    ;  (is (= (:status response) 200))))

  ;(testing "not-found route"
   ; (let [response (app (mock/request :get "/invalid"))]
    ;  (is (= (:status response) 404)))))
