(ns f-pols.m-func)

(defn invoke-fm [body v-args args]
  (when (= (count v-args) (count args))
    (eval `(let ~(into []
                       (mapcat
                        (fn [s v]
                          (vector (-> s name symbol) v)) 
                        v-args args))
             ~@body))))

(definterface IFuncaoMalina
  (body [nb]))

(deftype FuncaoMaligna
    [v-args body]
  clojure.lang.Seqable
  (seq [_] body)
  clojure.lang.IFn
  (invoke [_ args] (invoke-fm body v-args args))
  IFuncaoMalina
  (body [_ nb] (FuncaoMaligna. v-args nb))
  Object
  (toString [_] (.toString body)))

(defmethod print-method FuncaoMaligna [v ^java.io.Writer w]
  (.write w (str v)))

(defn funcao-maligna
  [args body]
  (FuncaoMaligna. args body))

(defmacro defun [name args & body]
  `(def ~name (funcao-maligna '~(map #(keyword %) args) '~body)))
