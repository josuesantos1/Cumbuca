(ns cumbuca.schemas.account
  (:require
   [clojure.spec.alpha :as s]))

(s/def :account/id uuid?)
(s/def :account/name string?)
(s/def :account/email string?)
(s/def :account/tax-id string?)
(s/def :account/amount int?)
(s/def :account/created-at (partial instance? java.time.LocalDateTime))

(s/def ::account
  (s/keys
   :req [:account/name
         :account/email
         :account/tax-id]
   :opt [:account/amount
         :account/created-at
         :account/id]))
