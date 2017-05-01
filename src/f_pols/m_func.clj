(ns f-pols.m-func)

(defn invoke-fm [body v-args & args]
  (when (= (count v-args) (count args))
    (eval `(let ~(into []
                       (mapcat
                        (fn [s v]
                          (vector (-> s name symbol) v)) 
                        v-args args))
             ~@body))))

(definterface IFuncaoMalina
  (body [nb]))

(defmethod print-method IFuncaoMalina [v ^java.io.Writer w]
  (.write w (str v)))

(defn funcao-maligna
  [v-args body]
  (letfn [(argfy [args] (map #(-> % name symbol) args))]
    (let [args (argfy v-args)]
      (eval `(reify
               clojure.lang.IPersistentList
               clojure.lang.Seqable
               (seq [_] '~body)
               clojure.lang.IFn
               (invoke [_ ~@args] (invoke-fm '~body ~v-args ~@args))
               f_pols.m_func.IFuncaoMalina
               (body [_ ~(quote nb)] (funcao-maligna ~v-args ~(quote nb)))
               Object
               (toString [_] (.toString '~body)))))))

(defmacro defun [name args & body]
  `(def ~name (funcao-maligna ~(vec (map keyword args)) '~body)))
