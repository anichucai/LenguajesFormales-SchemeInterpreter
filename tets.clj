(ns proyecto-scheme.core-test
  (:require [clojure.test :refer :all]
            [proyecto-scheme.core :refer :all]))

(deftest saludar-test

  (testing "Test del valor de retorno"
      (is (= nil (saludar "Diego")))
      (is (= nil (saludar nil)))
      (is (= nil (saludar "Pablo")))
  )

  (testing "Test del efecto colateral"
      (let [res (with-out-str (saludar "Diego"))]
          (is (= res "Hola Diego\n"))
      )
  )
) 

(deftest calcular-test

  (testing "Varias aserciones"
      (is (= 4 (calcular 3)))
      (is (= 5 (calcular 4)))
      (is (= 6 (calcular 5)))
  )
)

