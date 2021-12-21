
(ns proyecto-scheme.core-test
  (:require [clojure.test :refer :all]
            [scheme.core :refer :all]))

(deftest proteger-bool-en-str-test
  (testing "Test del valor de retorno"
      (is (= (read-string "(and (or #F #f #t #T) #T)") (restaurar-bool (read-string (proteger-bool-en-str "(and (or #F #f #t #T) #T)")))))
      (is (= (read-string "(and (or #F #f #t #T) #T)") (restaurar-bool (read-string "(and (or %F %f %t %T) %T)"))))
  )
) 

(deftest proteger-bool-en-str-test
  (testing "Test del valor de retorno"
      (is (= '(and (or (symbol "#F") (symbol "#f") (symbol "#t") (symbol "#T")) (symbol "#T")) (restaurar-bool (read-string (proteger-bool-en-str "(and (or #F #f #t #T) #T)")))))
      (is (= '(and (or (symbol "#F") (symbol "#f") (symbol "#t") (symbol "#T")) (symbol "#T")) (restaurar-bool (read-string "(and (or %F %f %t %T) %T)"))))
  )
) 