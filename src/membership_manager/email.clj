(ns membership-manager.email
  (:require [environ.core :refer [env]]
            [postal.core :refer [send-message]]))

(def email-conn
  {:host (env :email-host)
   :ssl true ;;TODO convert from string to bool
   :user (env :email-user)
   :pass (env :email-password)
   :port 465})

(defn- send-mail
  [from to subject message]
  (println email-conn)
  (send-message email-conn
                {:from from
                 :to to
                 :subject subject
                 :body message}))

(defn invite-collaborator
  [collaborator token project]
  (send-mail "csejenkins@gmail.com"
             "rtiffin@gmail.com"
             (str "You have been invited to collaborate on " project)
             (str
              "Please follow this link to accept the invitation: \n"
              "blah/" token)))
