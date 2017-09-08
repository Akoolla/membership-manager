(ns membership-manager.email
  (:require [environ.core :refer [env]]
            [postal.core :refer [send-message]]))

(def mail-enabled
  (if-let [enabled (env :enabled)]
    (do
      (println "enabling email")
      (read-string enabled))
    false))

(def email-conn
  (do
    (println mail-enabled)
  (if-let [enabled mail-enabled]
    {:host (env :email-host)
     :ssl (read-string (env :email-use-ssl))
     :user (env :email-user)
     :pass (env :email-password)
     :port (read-string (env :email-port))}
    {})))

(defn- send-mail
  [from to subject message]
  (if mail-enabled
    (send-message email-conn
                {:from from
                 :to to
                 :subject subject
                 :body message})
    (println "Email not enabled")))

(defn account-created
  ;;TODO Write this bit of code..
  "Sends an email confirmation to user of account created, with password"
  [email password]
  (send-mail (:user email-conn)
             email
             (str
              "Your membership has been created for "
              (env :app-name))
             (str
              "Your username is " email "\n"
              "Your temporary password is " password "\n"
              "Please follow this link to log-in and change it: \n"
              (env :site-url))))
