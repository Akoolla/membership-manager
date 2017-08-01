(ns membership-manager.store.users
  (:require
   [alandipert.enduro :as enduro]
   [membership-manager.store.env :as env]
   [cemerick.friend.credentials :as creds]))

(def storage
  ;; a map from user-id to user details, each being a map of attributes
  (enduro/file-atom
   {}
   (str env/storage-directory "users.edn")))

(defn user-by-login
  [username]
  (get @storage username))

(defn authenticate
  [{:keys [username password]}]
  (let [user-record (get @storage
                         username)]
      (when (creds/bcrypt-verify password (get user-record :password))
        (dissoc user-record :password))
      user-record))

(defn- add-security-concern
  [details]
  (assoc details
         :roles #{::admin}
         :password (creds/hash-bcrypt (:password details))))

(defn create-admin
  [details]
   (let [details (add-security-concern details)]    
    (try
     (enduro/swap!
      storage
      (fn [users]
        (let [username-exists (into #{} (keys users))
              proposed-username (:username details)]
          (if (username-exists proposed-username)
            (throw (Exception. (format "%s already registered" proposed-username)))
            (assoc users proposed-username details)))))
     false
     (catch Exception e
       (println e)
       ["User probably already exists or something."]))))

(defn change-password
  [username])
