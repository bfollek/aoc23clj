(ns aoc23.day01
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]
   [rabbithole.core :as rh]))

(defn calibration-value
  [s]
  (let [just-digits (filter #(Character/isDigit %) s)
        cal-digits (str (first just-digits) (last just-digits))
        rv (rh/to-int cal-digits)]
    ;(println cal-digits rv)
    rv))

(defn pattern
  [hash]
  (re-pattern (str/join "|" (keys hash))))

(defn words-to-ints
  "There are ambiguous cases, e.g. is 'eighthree' 'eight' or 'three'? What about 'sevenine'? etc. The surprising (to me) answer came from a helpful redditor:

  The right calibration values for string 'eighthree' is 83 and for 'sevenine' is 79. - [[https://www.reddit.com/r/adventofcode/comments/1884fpl/2023_day_1for_those_who_stuck_on_part_2/]]

  My favorite solution in the discussion replaces number words not simply with their numbers, but also with their possible overlap letters. That way, we preserve possible double-use letters that are part of two number words. 
   
  So 'eighthree' becomes 'e8t3e', and 'threeight' becomes 't3e8t'. We get the numbers we want, and a few more garbage letters that `(calibration_value)` filters out."
  [s]
  (let [replacements {"one" "o1e" "two" "t2o" "three" "t3e" "four" "f4r"
                      "five" "f5e" "six" "s6x" "seven" "s7n" "eight" "e8t" "nine" "n9e"}]
    (reduce (fn [s [k v]] (clojure.string/replace s k v)) s replacements)))

(defn part-1
  "On each line, the calibration value can be found by combining the first digit and the last digit (in that order) to form a single two-digit number."
  [file-name]
  (with-open [rdr (io/reader file-name)]
    (reduce + (map #(calibration-value %) (line-seq rdr)))))

(defn part-2
  "...some of the digits are actually spelled out with letters: one, two, three, four, five, six, seven, eight, and nine also count as valid 'digits'."
  [file-name]
  (with-open [rdr (io/reader file-name)]
    (reduce + (map #((comp calibration-value words-to-ints) %) (line-seq rdr)))))
