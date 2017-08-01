(ns membership-manager.store.env
  (:require [environ.core :refer [env]]))

(def storage-directory
  "The path to the directory where data should be stored."
  (str (or (env :storage-dir) "data") "/"))
