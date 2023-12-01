(ns aoc23.day01
  (:require
   [clojure.java.io :as io]
   [rabbithole.core :as rh]))

(defn- elf-calories
  "Return a seq of each elf's total calories."
  [file-name]
  (with-open [rdr (io/reader file-name)]
    (->> (line-seq rdr) ; ("123" "456" "" "789" "" "987" "654")
         (partition-by #(= "" %)) ; ( ("123" "456") ("") ("789") ("") ("987" "654") )
         (remove #(= % [""])) ; ( ("123" "456") ("789") ("987" "654") )
         (map #(map rh/to-int %)) ; ( (123 456) (789) (987 654) )
         (map #(reduce + %)) ; (579 789 1641)
         doall))) ; Eval the lazy seqs before the stream closes.

(defn part-1
  "Find the elf carrying the most calories. How many total calories is that elf carrying?"
  [file-name]
  (->> file-name
       elf-calories
       (apply max)))

(defn part-2
  "Find the top three Elves carrying the most Calories. How many Calories are those Elves carrying in total?"
  [file-name]
  (->> file-name
       elf-calories
       (sort >)
       (take 3)
       (reduce +)))


