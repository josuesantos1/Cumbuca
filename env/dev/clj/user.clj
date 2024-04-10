(ns user
  "Userspace functions you can run by default in your local REPL."
  #_{:clj-kondo/ignore [:unused-referred-var]}
  (:require
   [clojure.pprint]
   [clojure.spec.alpha :as s]
   [expound.alpha :as expound]
   [mount.core :as mount]
   [cumbuca.core :refer [start-app]]))

(alter-var-root #'s/*explain-out* (constantly expound/printer))

(add-tap (bound-fn* clojure.pprint/pprint))

(defn start
  "Starts application.
  You'll usually want to run this on startup."
  []
  (mount/start-without #'cumbuca.core/repl-server))

(defn stop
  "Stops application."
  []
  (mount/stop-except #'cumbuca.core/repl-server))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn restart
  "Restarts application."
  []
  (stop)
  (start))


