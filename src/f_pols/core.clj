(ns f-pols.core
  (:require [f-pols.m-func :refer :all]))

(defn monomio [c e]
  (list '* c (list 'Math/pow 'x e)))

(defn polinomio [c-es]
  (funcao-maligna
   '(:x)
   (conj
    (reduce (fn [acc [c e]] (->> (monomio c e) (conj acc)))
            '() c-es)
    '+)))

(defn get-n [coll]
  (loop [coll coll]
    (let [n (first coll)]
      (if (number? n)
        n (recur (pop coll))))))

(defn update-l [l n f]
  (concat (take n l) (list (f (nth l n))) (nthnext l (inc n))))

(defn derivar [pol]
  (->>
   (reduce (fn [acc x]
             (if (list? x)
               (->>
                (-> x
                    (update-l 1 #(* % (get-n (nth x 2))))
                    (update-l 2 #(update-l % 2 (fn [y] (dec y)))))
                (conj acc))
               (conj acc x))) '() pol)
   reverse
   (.body pol)))

