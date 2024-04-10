(ns cumbuca.schemas.transaction
  (:require [clojure.spec.alpha :as s]))

(def status #{:transaction.status/reversed
              :transaction.status/done})

(s/def :transaction/id uuid?)
(s/def :transaction/sender uuid?)
(s/def :transaction/receiver uuid?)
(s/def :transaction/amount int?)
(s/def :transaction/status status)

(s/def ::transaction
  (s/keys
   :req [:transaction/sender
         :transaction/receiver
         :transaction/amount]
   :opt [:transaction/id
         :transaction/status]))
