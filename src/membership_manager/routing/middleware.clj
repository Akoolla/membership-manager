(ns membership-manager.routing.middleware
  (:require
   [ring.middleware.webjars :refer [wrap-webjars]]
   [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
   [ring.util.response :as resp]
   [cemerick.friend :as friend]
   (cemerick.friend [workflows :as workflows]
                    [credentials :as creds])))

(defn wrap
  ""
  [fn]
  (wrap-defaults fn site-defaults))


  ;;(-> fn
    ;;  wrap-webjars
      ;;(wrap-defaults site-defaults)))
