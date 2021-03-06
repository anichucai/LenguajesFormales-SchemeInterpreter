(ns scheme.core-test
  (:require [clojure.test :refer :all]
            [scheme.core :refer :all]))

(deftest verificar-parentesis-test
  (testing "Test del valor de retorno"
    (is (=
         1
         (verificar-parentesis "(hola 'mundo")))
    (is (=
         -1
         (verificar-parentesis "(hola '(mundo)))")))
    (is (=
         -1
         (verificar-parentesis "(hola '(mundo) () 6) 7)")))
    (is (=
         -1
         (verificar-parentesis "(hola '(mundo) () 6) 7) 9)")))
    (is (=
         0
         (verificar-parentesis "(hola '(mundo) )")))))


(deftest actualizar-amb-test
  (testing "Test del valor de retorno"
    (is (=
         '(a 1 b 2 c 3 d 4)
         (actualizar-amb '(a 1 b 2 c 3) 'd 4)))
    (is (=
         '(a 1 b 2 c 3)
         (actualizar-amb '(a 1 b 2 c 3) 'b (list (symbol ";ERROR:") 'mal 'hecho))))
    (is (=
         '(b 7)
         (actualizar-amb () 'b 7)))))
    (is (=
         '(a 1 b 2 c e e 4)
         (actualizar-amb '(a 1 b 2 c e) 'e 4)))

(deftest buscar-test
  (testing "Test del valor de retorno"
    (is (=
         3
         (buscar 'c '(a 1 b 2 c 3 d 4 e 5))))
    (is (=
         (generar-mensaje-error :unbound-variable 'f)
         (buscar 'f '(a f b 2 c 3 d 4 e 5))))
    (is (=
         (generar-mensaje-error :unbound-variable 'f)
         (buscar 'f '(a 1 b 2 c 3 d 4 e 5))))))

(deftest error?-test
  (testing "Test del valor de retorno"
    (is (=
         true
         (error? (generar-mensaje-error :wrong-type-arg 'append 3))))
    (is (=
         true
         (error? (list (symbol ";ERROR:") 'mal 'hecho))))
    (is (=
         false
         (error? (list 'mal 'hecho))))
    (is (=
         true
         (error? (list (symbol ";WARNING:") 'mal 'hecho))))))

(deftest error?-test
  (testing "Test del valor de retorno"
    (is (=
         true
         (error? (generar-mensaje-error :wrong-type-arg 'append 3))))
    (is (=
         true
         (error? (list (symbol ";ERROR:") 'mal 'hecho))))
    (is (=
         false
         (error? (list 'mal 'hecho))))
    (is (=
         true
         (error? (list (symbol ";WARNING:") 'mal 'hecho))))))

(deftest proteger-bool-en-str-test
  (testing "Test del valor de retorno"
    (is (=
         "(or %F %f %t %T)"
         (proteger-bool-en-str "(or #F #f #t #T)")))
    (is (=
         "(and (or %F %f %t %T) %T)"
         (proteger-bool-en-str "(and (or %F %f %t %T) %T)")))))

(deftest restaurar-bool-test
  (testing "Test del valor de retorno"
    (is (=
         (list 'and (list 'or (symbol "#F") (symbol "#f") (symbol "#t") (symbol "#T")) (symbol "#T"))

         (restaurar-bool (read-string (proteger-bool-en-str "(and (or #F #f #t #T) #T)")))))
    (is (=
         (list 'and (list 'or (symbol "#F") (symbol "#f") (symbol "#t") (symbol "#T")) (symbol "#T"))
         (restaurar-bool (read-string "(and (or %F %f %t %T) %T)"))))))


(deftest fnc-igual?-test
  (testing "Test del valor de retorno"
    (is (=
        true
         (igual? (symbol "#t") (symbol "#T"))))
    (is (=
         true
         (igual? (symbol "#f") (symbol "#F"))))
    (is (=
         true
         (igual? 'if 'IF)))
    (is (=
         true
         (igual? 'if 'if)))
    (is (=
         true
         (igual? 'IF 'IF)))
    (is (=
         false
         (igual? 'IF "IF")))
    (is (=
         false
         (igual? 6 "6")))
    (is (=
         true
         (igual? "6" "6")))
    (is (=
         true
         (igual? 6 6)))
    (is (=
         true
         (igual? 6.0 6)))
    (is (=
         false
         (igual? "hola" "Hola")))
    (is (=
         true
         (igual? "hola" "hola")))))

(deftest fnc-append-test
  (testing "Test del valor de retorno"
    (is (=
         '(1 2 9 3 4 5 6 7)
         (fnc-append '((1 2) (9 3) (4 5) (6 7)))))
    (is (=
         '(1 2 3 4 5 6 7)
         (fnc-append '((1 2) (3) (4 5) (6 7)))))
    (is (=
         (generar-mensaje-error :wrong-type-arg 'append 3)
         (fnc-append '((1 2) 3 (4 5) (6 7)))))
    (is (=
         (generar-mensaje-error :wrong-type-arg 'append 'A)
         (fnc-append '((1 2) A (4 5) (6 7)))))))

(deftest fnc-equal?-test
  (testing "Test del valor de retorno"
    (is (=
         (symbol "#t")
         (fnc-equal? ())))
    (is (=
         (symbol "#t")
         (fnc-equal? '(A))))
    (is (=
         (symbol "#t")
         (fnc-equal? '(A a))))
    (is (=
         (symbol "#t")
         (fnc-equal? '(A a A))))
    (is (=
         (symbol "#t")
         (fnc-equal? '(A a A a))))
    (is (=
         (symbol "#f")
         (fnc-equal? '(A a A B))))
    (is (=
         (symbol "#t")
         (fnc-equal? '(1 1 1 1))))
    (is (=
         (symbol "#f")
         (fnc-equal? '(1 1 2 1))))
    (is (=
         (symbol "#f")
         (fnc-equal? '("hola" "Hola"))))
    (is (=
         (symbol "#f")
         (fnc-equal? '((symbol "#f") (symbol "#t")))))
    (is (=
         (symbol "#t")
         (fnc-equal? (list (symbol "#t") (symbol "#T") (symbol "#t")))))
    (is (=
         (symbol "#t")
         (fnc-equal? (list (symbol "#f") (symbol "#F") (symbol "#f")))))))


(deftest fnc-sumar-test
  (testing "Test del valor de retorno"
    (is (=
         0
         (fnc-sumar '())))
    (is (=
         3
         (fnc-sumar '(3))))
    (is (=
         7
         (fnc-sumar '(3 4))))
    (is (=
         12
         (fnc-sumar '(3 9))))
    (is (=
         18
         (fnc-sumar '(3 4 5 6))))
    (is (=
         (generar-mensaje-error :wrong-type-arg1 '+ 'A)
         (fnc-sumar '(A 4 5 6))))
    (is (=
         (generar-mensaje-error :wrong-type-arg2 '+ 'A)
         (fnc-sumar '(1 A 2 4))))
    (is (=
         (generar-mensaje-error :wrong-type-arg2 '+ 'A)
         (fnc-sumar '(1 5 A 2 4))))))

(deftest fnc-restar-test
  (testing "Test del valor de retorno"
    (is (=
         (generar-mensaje-error :wrong-number-args-oper '-)
         (fnc-restar ())))
    (is (=
         -3
         (fnc-restar '(3))))
    (is (=
         -1
         (fnc-restar '(3 4))))
    (is (=
         -6
         (fnc-restar '(3 9))))
    (is (=
         -12
         (fnc-restar '(3 4 5 6))))
    (is (=
         (generar-mensaje-error :wrong-type-arg1 '- 'A)
         (fnc-restar '(A 4 5 6))))
    (is (=
         (generar-mensaje-error :wrong-type-arg2 '- 'A)
         (fnc-restar '(1 A 2 4))))
    (is (=
         (generar-mensaje-error :wrong-type-arg2 '- 'A)
         (fnc-restar '(1 5 A 2 4))))))

(deftest fnc-menor-test
  (testing "Test del valor de retorno"
    (is (=
         (symbol "#t")
         (fnc-menor '(1))))
    (is (=
         (symbol "#t")
         (fnc-menor '(1 2))))
    (is (=
         (symbol "#t")
         (fnc-menor '(1 2 3))))
    (is (=
         (symbol "#t")
         (fnc-menor '(1 2 3 4))))
    (is (=
         (symbol "#f")
         (fnc-menor '(1 2 2 4))))
    (is (=
         (symbol "#f")
         (fnc-menor '(1 2 1 4))))
    (is (=
         (generar-mensaje-error :wrong-type-arg1 '< 'A)
         (fnc-menor '(A 1 2 4))))
    (is (=
         (generar-mensaje-error :wrong-type-arg2 '< 'A)
         (fnc-menor '(1 A 2 4))))
    ))

(deftest fnc-mayor-test
  (testing "Test del valor de retorno"
    (is (=
         (symbol "#t")
         (fnc-mayor '(1))))
    (is (=
         (symbol "#t")
         (fnc-mayor '(2 1))))
    (is (=
         (symbol "#t")
         (fnc-mayor '(3 2 1))))
    (is (=
         (symbol "#t")
         (fnc-mayor '(4 3 2 1))))
    (is (=
         (symbol "#f")
         (fnc-mayor '(4 2 2 1))))
    (is (=
         (symbol "#f")
         (fnc-mayor '(4 2 1 4))))
    (is (=
         (generar-mensaje-error :wrong-type-arg2 '> 'A)
         (fnc-mayor '(3 A 2 1))))
    (is (=
         (generar-mensaje-error :wrong-type-arg2 '> 'A)
         (fnc-mayor '(3 2 A 1))))))

(deftest fnc-mayor-o-igual-test
  (testing "Test del valor de retorno"
    (is (=
         (symbol "#t")
         (fnc-mayor-o-igual '())))
    (is (=
         (symbol "#t")
         (fnc-mayor-o-igual '(1))))
    (is (=
         (symbol "#t")
         (fnc-mayor-o-igual '(2 1))))
    (is (=
         (symbol "#t")
         (fnc-mayor-o-igual '(3 2 1))))
    (is (=
         (symbol "#t")
         (fnc-mayor-o-igual '(4 3 2 1))))
    (is (=
         (symbol "#t")
         (fnc-mayor-o-igual '(4 2 2 1))))
    (is (=
         (symbol "#f")
         (fnc-mayor-o-igual '(4 2 1 4))))
    (is (=
         (generar-mensaje-error :wrong-type-arg2 '>= 'A)
         (fnc-mayor-o-igual '(3 A 2 1))))
    (is (=
         (generar-mensaje-error :wrong-type-arg2 '>= 'A)
         (fnc-mayor-o-igual '(3 2 A 1))))))


(deftest evaluar-escalar-test
  (testing "Test del valor de retorno"
    (is (=
         '(32 (x 6 y 11 z "hola"))
         (evaluar-escalar 32 '(x 6 y 11 z "hola"))))
    (is (=
         '("chau" (x 6 y 11 z "hola"))
         (evaluar-escalar "chau" '(x 6 y 11 z "hola"))))
    (is (=
         '(11 (x 6 y 11 z "hola"))
         (evaluar-escalar 'y '(x 6 y 11 z "hola"))))
    (is (=
         '("hola" (x 6 y 11 z "hola"))
         (evaluar-escalar 'z '(x 6 y 11 z "hola"))))
    (is (=
         '("hola" (x 6 y 11 z "hola"))
         (evaluar-escalar 'z '(x 6 y 11 z "hola"))))
    (is (=
         (list (generar-mensaje-error :unbound-variable 'n)  '(x 6 y 11 z "hola"))
         (evaluar-escalar 'n '(x 6 y 11 z "hola"))))))
      
(deftest evaluar-define-test
  (testing "Test del valor de retorno"
    (is (=
         (list (symbol "#<unspecified>") '(x 2))
         (evaluar-define '(define x 2) '(x 1))))
    (is (=
         (list (symbol "#<unspecified>") '(x 1 f (lambda (x) (+ x 1))))
         (evaluar-define '(define (f x) (+ x 1)) '(x 1))))
    (is (=
         (list (symbol "#<unspecified>") '(x 1 f (lambda (x y) (+ x y))))
         (evaluar-define '(define (f x y) (+ x y)) '(x 1))))
    (is (=
         (list (generar-mensaje-error :missing-or-extra 'define '(define)) '(x 1))
         (evaluar-define '(define) '(x 1))))
    (is (=
         (list (generar-mensaje-error :missing-or-extra 'define '(define x)) '(x 1))
         (evaluar-define '(define x) '(x 1))))
    (is (=
         (list (generar-mensaje-error :missing-or-extra 'define '(define x 2 3)) '(x 1))
         (evaluar-define '(define x 2 3) '(x 1))))
    (is (=
         (list (generar-mensaje-error :missing-or-extra 'define '(define ())) '(x 1))
         (evaluar-define '(define ()) '(x 1))))
    (is (=
         (list (generar-mensaje-error :bad-variable 'define '(define () 2)) '(x 1))
         (evaluar-define '(define () 2) '(x 1))))
    (is (=
         (list (generar-mensaje-error :bad-variable 'define '(define 2 x)) '(x 1))
         (evaluar-define '(define 2 x) '(x 1))))))

(deftest evaluar-if-test
  (testing "Test del valor de retorno"
    (is (=
         '(2 (n 7))
         (evaluar-if '(if 1 2) '(n 7))))
    (is (=
         '(7 (n 7))
         (evaluar-if '(if 1 n 8) '(n 7))))
    (is (=
         '(7 (n 7))
         (evaluar-if '(if 1 n 8) '(n 7))))
    (is (=
         (list (symbol "#<unspecified>") (list 'n 7 (symbol "#f") (symbol "#f")))
         (evaluar-if (list 'if (symbol "#f") 'n) (list 'n 7 (symbol "#f") (symbol "#f")))))
    (is (=
         (list 8 (list 'n 7 (symbol "#f")  (symbol "#f")))
         (evaluar-if (list 'if (symbol "#f") 'n 8) (list 'n 7 (symbol "#f") (symbol "#f")))))
    (is (=
         (list (generar-mensaje-error :missing-or-extra 'if (list 'if 1)) '(n 7))
         (evaluar-if '(if 1) '(n 7))))))

(deftest evaluar-or-test
  (testing "Test del valor de retorno"
    (is (=
         (list (symbol "#f") (list (symbol "#f") (symbol "#t") (symbol "#t") (symbol "#f")))
         (evaluar-or (list 'or) (list (symbol "#f") (symbol "#t") (symbol "#t") (symbol "#f")))))
    (is (=
         (list (symbol "#t") (list (symbol "#f") (symbol "#f") (symbol "#t") (symbol "#t")))
         (evaluar-or (list 'or (symbol "#t")) (list (symbol "#f") (symbol "#f") (symbol "#t") (symbol "#t")))))
    (is (=
         (list 7 (list (symbol "#f") (symbol "#f") (symbol "#t") (symbol "#t")))
         (evaluar-or (list 'or 7) (list (symbol "#f") (symbol "#f") (symbol "#t") (symbol "#t")))))
    (is (=
         (list 5 (list (symbol "#f") (symbol "#f") (symbol "#t") (symbol "#t")))
          (evaluar-or (list 'or (symbol "#f") 5) (list (symbol "#f") (symbol "#f") (symbol "#t") (symbol "#t")))))
    (is (=
         (list (symbol "#f") (list (symbol "#f") (symbol "#f") (symbol "#t") (symbol "#t")))
         (evaluar-or (list 'or (symbol "#f")) (list (symbol "#f") (symbol "#f") (symbol "#t") (symbol "#t")))))))

(deftest evaluar-set!-test
  (testing "Test del valor de retorno"
    (is (=
         (list (symbol "#<unspecified>") '(x 1))
         (evaluar-set! '(set! x 1) '(x 0))))
    (is (=
         (list (generar-mensaje-error :unbound-variable 'x) '())
         (evaluar-set! '(set! x 1) '())))
    (is (=
         (list (generar-mensaje-error :missing-or-extra 'set! '(set! x)) '(x 0))
         (evaluar-set! '(set! x) '(x 0))))
    (is (=
         (list (generar-mensaje-error :missing-or-extra 'set! '(set! x 1 2)) '(x 0))
         (evaluar-set! '(set! x 1 2) '(x 0))))
    (is (=
         (list (generar-mensaje-error :bad-variable 'set! 1) '(x 0))
         (evaluar-set! '(set! 1 2) '(x 0))))
    (is (=
         (list (symbol "#<unspecified>") '(x 5))
         (evaluar-set! '(set! x 5) '(x 0))))))