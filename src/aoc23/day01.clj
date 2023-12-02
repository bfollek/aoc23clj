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

   We handle these special cases first. Then we do the standard replacements."
  [s]
  (let [special-cases {;; End/start with "o"
                       "twone" "21"
                       ;; End/start with "t"
                       "eightwo" "82" "eighthree" "83"
                       ;; End/start with "e"
                       "oneight" "18" "threeight" "38" "fiveight" "58"
                       "nineight" "98"
                       ;; End/start with "n"
                       "sevenine" "79"}
        standard-cases {"one" "1" "two" "2" "three" "3" "four" "4"
                        "five" "5" "six" "6" "seven" "7" "eight" "8" "nine" "9"}]
    (-> s
        (str/replace (pattern special-cases) special-cases)
        (str/replace (pattern standard-cases) standard-cases))))

(defn part-1
  "On each line, the calibration value can be found by combining the first digit and the last digit (in that order) to form a single two-digit number."
  [file-name]
  (with-open [rdr (io/reader file-name)]
    (reduce + (map #(calibration-value %) (line-seq rdr)))))

(defn part-2
  " From the spec: '...some of the digits are actually spelled out with letters: one, two, three, four, five, six, seven, eight, and nine also count as valid 'digits'.'"
  [file-name]
  (with-open [rdr (io/reader file-name)]
    (reduce + (map #((comp calibration-value words-to-ints) %) (line-seq rdr)))))
