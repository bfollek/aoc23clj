(ns aoc23.day01
  (:require
   [clojure.java.io :as io]
   [rabbithole.core :as rh]))

(defn calibration-value
  [s]
  (let [just-digits (filter #(Character/isDigit %) s)
        cal-digits (str (first just-digits) (last just-digits))
        rv (rh/to-int cal-digits)]
    ;(println cal-digits rv)
    rv))

(defn part-1
  "Consider your entire calibration document. What is the sum of all of the calibration values?"
  [file-name]
  (with-open [rdr (io/reader file-name)]
    (reduce + (map #(calibration-value %) (line-seq rdr)))))

(defn part-2
  "Find the top three Elves carrying the most Calories. How many Calories are those Elves carrying in total?"
  [file-name])


