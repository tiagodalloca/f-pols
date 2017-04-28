(defproject f-pols "0.1.0-SNAPSHOT"
  :description "Biblioteca para ajudar a fazer polinomios malignos" 
  :dependencies [[org.clojure/clojure "1.8.0"]]

  :source-paths ["src"]
  :profiles {:dev {:dependencies
                   [[org.clojure/tools.namespace "0.2.11"]]
                   :source-paths
                   ["dev"]}})
