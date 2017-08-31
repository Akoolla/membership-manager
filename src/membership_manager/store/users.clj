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

(def max-mem-numbers (atom 5000))
(def new-num (atom nil))
(defn generate-membership-number
  "Generates a random number that's not alread in a set of numbers"
  []
  (let [used #{}
        ;;Generate a hash-set of existing numbers
        used (reduce (fn [accum user]
                       (conj accum (:mem-number user)))
                     used
                     (vals @storage))]
    (if (> (count used) @max-mem-numbers)
      (throw (Exception. "Max membership numbers reached"))
      (do
        ;;generate a new membership number until you find one thats not been used
        (while (nil? @new-num)
          (do
            (let [num (rand-int @max-mem-numbers)]
              (if (not (contains? used num))
                (reset! new-num num)))))

        ;;Found one that's not been used - now reset atom and return new number
        (let [membership-num @new-num]
          (reset! new-num nil)
          membership-num)))))

(defn user-by-login
  [username]
  (get @storage username))

(defn list-all
  []
  ;@storage)
  (let [users {}]
    (reduce (fn [accum user]
              (let [email (first user)
                    details (second user)
                    details (dissoc details :password)
                    ;;{:username (:username details)}
                   accum (assoc accum email details)]
                accum))
              users
              @storage)))

(defn authenticate
  [{:keys [username password]}]
  (let [user-record (get @storage
                         username)]
      (when (creds/bcrypt-verify password (get user-record :password))
        (dissoc user-record :password))
      user-record))

(defn- encrypt-password
  [details]
  (assoc details :password  (creds/hash-bcrypt (:password details))))

(defn- add-security-concern
  [details roles]
  (let [details (assoc details
                       :roles roles
                       :mem-number (generate-membership-number))
        details (encrypt-password details)]
    details))

(defn update-user
  [details]
  (let [existing (user-by-login (:username details))
        existing (assoc existing
                        :password (:password details)
                        :change-password (:change-password details))
        details (encrypt-password existing)]
    (try
      (enduro/swap!
       storage
       (fn [users]
         (assoc users (:username details) details)))))
  nil)

(defn create-user
  [details roles]
  (let [details (add-security-concern details (conj roles ::user))]
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

(defn create-admin
  [details]
  (create-user details #{::admin}))
        
(defn change-password
  [username password]
  (update-user {:username username :password password :change-password false}))


