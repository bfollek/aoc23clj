(ns aoc23.day01-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc23.day01 :refer [part-1 part-2 words-to-ints]]))

(deftest words-to-ints-tests
  (is (= "e8t3e" (words-to-ints "eighthree")))
  (is (= "s6xf4r" (words-to-ints "sixfour")))
  (is (= "s7n9e" (words-to-ints "sevenine"))))

(deftest day01-happy-path
  (testing "part-1."
    (is (= 142 (part-1 "data/day01_short_part_1.txt")))
    (is (= 54877 (part-1 "data/day01_long.txt")))))
(testing "part-2."
  (is (= 281 (part-2 "data/day01_short_part_2.txt")))
  (is (= 54100 (part-2 "data/day01_long.txt"))))
