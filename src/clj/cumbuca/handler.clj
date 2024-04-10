(ns cumbuca.handler
  (:require [cumbuca.config :refer [env]]
            [cumbuca.db.core]
            [cumbuca.env :refer [defaults]]
            [cumbuca.middleware :as middleware]
            [cumbuca.routes.services :refer [service-routes]]
            [datomic.api :as d]
            [mount.core :as mount]
            [reitit.ring :as ring]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.webjars :refer [wrap-webjars]]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(mount/defstate init-app
  :start ((or (:init defaults) (fn [])))
  :stop  ((or (:stop defaults) (fn []))))

(defn- async-aware-default-handler
  ([_] nil)
  ([_ respond _] (respond nil)))

(mount/defstate conn
  :start (do (-> env :database-url d/create-database)
             (-> env :database-url d/connect))
  :stop (-> conn .release))

(mount/defstate app-routes
  :start
  (ring/ring-handler
    (ring/router
      [["/" {:get
             {:handler (constantly {:status 301 :headers {"Location" "/api/api-docs/index.html"}}) }}]
       (service-routes)])
    (ring/routes
      (ring/create-resource-handler
        {:path "/"})
      (wrap-content-type (wrap-webjars async-aware-default-handler))
      (ring/create-default-handler))))

(defn app []
  (middleware/wrap-base #'app-routes))
