(ns aoc23.day01-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc23.day01 :refer [part-1 part-2]]))

(deftest day01-happy-path
  (testing "part-1."
    (is (= 24000 (part-1 "data/day01_short.txt")))
    (is (= 75622 (part-1 "data/day01_long.txt"))))
  (testing "part-2."
    (is (= 45000 (part-2 "data/day01_short.txt")))
    (is (= 213159 (part-2 "data/day01_long.txt")))))
