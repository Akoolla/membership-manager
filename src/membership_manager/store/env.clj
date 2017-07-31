(ns membership-manager.store.env)

(def storage-directory
  "The path to the directory where data should be stored."
  (str (or (System/getenv "STORAGE_DIRECTORY") "data") "/"))
