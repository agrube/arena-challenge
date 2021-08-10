package com.grube.arena;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PairRunner {

    public static Optional<CSVReader> createReader(String fileLocation) {
        try {
            FileReader fileReader = new FileReader(fileLocation);
            return Optional.of(new CSVReader(fileReader));
        } catch (FileNotFoundException e) {
            return Optional.empty();
        }
    }

    /**
     * Generates all the pairs and the number of occurrences that are in a CSV file.
     * @param reader the CSVReader containing the CSV file
     * @return all pairs of artists associated with their number of occurrences.
     */
    public Map<Pair, Integer> generateAllPairs(CSVReader reader) {
        String[] nextLine;
        try {
            Map<Pair, Integer> results = new HashMap<>();
            while ((nextLine = reader.readNext()) != null) {
                List<Pair> pairs = generatePairs(List.of(nextLine));
                pairs.stream().distinct().forEach(pair -> {
                    if (results.containsKey(pair)) {
                        results.put(pair, results.get(pair) + 1);
                    } else {
                        results.put(pair, 1);
                    }
                });
            }
            return results;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Generates all the possible pairs of values in a list of strings.
     * @param artists the list of strings
     * @return all the possible pairs of values in a list of strings.
     */
    public List<Pair> generatePairs(List<String> artists) {
        if (artists.size() <= 1) {
            return List.of();
        } else if (artists.size() == 2) {
            Stream<Pair> results = generatePairsForValue(artists.get(0), artists.stream().skip(1).toList());
            return results.collect(Collectors.toList());
        } else {
            Stream<Pair> results = generatePairsForValue(artists.get(0), artists.stream().skip(1).toList());
            Stream<Pair> results2 = generatePairs(artists.stream().skip(1).toList()).stream();
            return Stream.concat(results, results2).toList();
        }
    }

    /**
     * Generates a list of pairs that contain the left value and any value in the right list.
     * @param left the value to generate pairs with
     * @param right a list of values to be matched with the left
     * @return all possible pairs for "left" and the "right" array.
     */
    public Stream<Pair> generatePairsForValue(String left, List<String> right) {
        if (right.size() == 1) {
            if (left.equals(right.get(0))) {
                return Stream.of();
            } else {
                return Stream.of(new Pair(left, right.get(0)));
            }
        } else if (right.size() > 1) {
            Stream<Pair> pair1 = generatePairsForValue(left, List.of(right.get(0)));
            Stream<Pair> pair2 = generatePairsForValue(left, right.stream().skip(1).toList());
            return Stream.concat(pair1, pair2);
        } else {
            return Stream.of();
        }
    }

    /**
     * This method will create a new Map containing only entries with a value greater than the passed in
     * numberOfInstances
     *
     * @param map               the map of Pairs to number of instances
     * @param numberOfInstances the minimum number of instances of a pair
     * @return new Map containing only entries with a value greater than the passed in numberOfInstances
     */
    public Map<Pair, Integer> filterByNumberOfInstances(Map<Pair, Integer> map, int numberOfInstances) {
        return map.entrySet().stream()
                .filter(entry -> entry.getValue() > numberOfInstances)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
