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
  "There are ambiguous cases, e.g. is 'eighthree' 'eight' or 'three'? What about 'sevenine'? etc. The surprising (to me) answer comes from a helpful redditor:

   The right calibration values for string 'eighthree' is 83 and for 'sevenine' is 79. - https://www.reddit.com/r/adventofcode/comments/1884fpl/2023_day_1for_those_who_stuck_on_part_2/

   In the reddit thread, someone had a elegant idea. Since the word overlap 
   is never more than one letter, include the last letter of the replaced word after its replacement int, so that any next word is complete, i.e. don't 
   replace 'one' with '1', replace it with '1e'.
   
   We do need to loop, because str/replace has apparently moved forward from 
   the end of the replaced string and misses our repair job to make the next
   word whole."
  [s]
  (let [replacements {"one" "1e" "two" "2o" "three" "3e" "four" "4"
                      "five" "5e" "six" "6" "seven" "7n" "eight" "8t" "nine" "9e"}]
    (loop [current s]
      (let [nxt (str/replace current (pattern replacements) replacements)]
        (if (= current nxt) ; Nothing changed. We're done.
          current
          (recur nxt))))))

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
