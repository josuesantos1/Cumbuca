(ns cumbuca.schemas.account
  (:require
   [clojure.spec.alpha :as s]
   [clojure.string :as strings]))

(s/def ::name (s/and string?
                     #(-> (strings/split % #" ")
                          (count)
                          (>= 2))))

(def email-regex #"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,63}$")
(def tax-id-regex #"(^\d{3}\.\d{3}\.\d{3}\-\d{2}$)|(^\d{2}\.\d{3}\.\d{3}\/\d{4}\-\d{2}$|\d{11}|\d{14})")
(s/def ::email (s/and string? #(re-matches email-regex %)))
(s/def ::tax-id (s/and string? #(re-matches tax-id-regex %)))

(s/def :account/id uuid?)
(s/def :account/name ::name)
(s/def :account/email ::email)
(s/def :account/tax-id ::tax-id)
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
