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

(defn words-to-ints
  [s]
  (str/replace s
               #"one|two|three|four|five|six|seven|eight|nine"
               {"one" "1" "two" "2" "three" "3" "four" "4" "five" "5"
                "six" "6" "seven" "7" "eight" "8" "nine" "9"}))

(defn part-1
  "On each line, the calibration value can be found by combining the first digit and the last digit (in that order) to form a single two-digit number."
  [file-name]
  (with-open [rdr (io/reader file-name)]
    (reduce + (map #(calibration-value %) (line-seq rdr)))))

(defn part-2
  " From the spec: '...some of the digits are actually spelled out with letters: one, two, three, four, five, six, seven, eight, and nine also count as valid 'digits'.'
   
   There are ambiguous cases, e.g. is 'oneight' 'one' or 'eight'? What about 'twone'? etc.
   "
  [file-name]
  (with-open [rdr (io/reader file-name)]
    (reduce + (map #((comp calibration-value words-to-ints) %) (line-seq rdr)))))

