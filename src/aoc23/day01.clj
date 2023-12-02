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

;; (defn words-to-ints
;;   "There are ambiguous cases, e.g. is 'eighthree' 'eight' or 'three'? What about 'sevenine'? etc. The surprising (to me) answer comes from a helpful redditor:

;;    The right calibration values for string 'eighthree' is 83 and for 'sevenine' is 79. - https://www.reddit.com/r/adventofcode/comments/1884fpl/2023_day_1for_those_who_stuck_on_part_2/

;;    This means that we only have to deal with the first and last number words, because we're only using the first and last numbers. Interior number words don't matter to us.
;;    "
;;   [s]
;;   (str/replace s
;;                ;; Drop in the number we need, but don't remove the letters,
;;                ;; in case they're part of another replaceable word.
;;                #"one|two|three|four|five|six|seven|eight|nine"
;;                {"one" "1" "two" "2" "three" "3" "four" "4"
;;                 "five" "5" "six" "6" "seven" "7" "eight" "8" "nine" "9"}))

(defn words-to-ints
  "There are ambiguous cases, e.g. is 'oneight' 'one' or 'eight'? What about 'twone'? etc. To deal with this, we loop through and replace first till there's nothing left to replace."
  [s]
  (let [pattern #"one|two|three|four|five|six|seven|eight|nine"
        replacements {#"one" "1one" #"two" "2two" #"three" "3three"
                      #"four" "4four" #"five" "5five" #"six" "6six"
                      #"seven" "7seven" #"eight" "8eight" #"nine" "9nine"}]
    (loop [current s]
      (let [changed (str/replace-first current pattern replacements)]
        (if (= changed current)
          current ; Nothing changed, so we're done.
          (recur changed))))))

(defn part-1
  "On each line, the calibration value can be found by combining the first digit and the last digit (in that order) to form a single two-digit number."
  [file-name]
  (with-open [rdr (io/reader file-name)]
    (reduce + (map #(calibration-value %) (line-seq rdr)))))

(defn part-2
  " From the spec: '...some of the digits are actually spelled out with letters: one, two, three, four, five, six, seven, eight, and nine also count as valid 'digits'.'"
  [file-name]
  (with-open [rdr (io/reader file-name)]
    (reduce + (map #((comp calibration-value words-to-ints) %) (line-seq rdr))))) "

