package com.grube.arena;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        if (args.length > 0) {
            String fileLocation = args[0];
            Optional<CSVReader> reader = PairRunner.createReader(fileLocation);
            PairRunner runner = new PairRunner();
            reader.map(runner::generateAllPairs) // CSVReader -> Map<String, Integer>
                    .map(results -> runner.filterByNumberOfInstances(results, 50))
                    .ifPresentOrElse(results -> {
                        try {
                            CSVWriter writer = new CSVWriter(new FileWriter("output.csv"));
                            results.keySet().stream()
                                    .sorted(Comparator.comparing(Pair::toAlphabetizedString))
                                    .map(Pair::toAlphabetizedArray)
                                    .forEach(writer::writeNext);
                            writer.flush();
                            System.out.println("Results size: " + results.size());
                        } catch (IOException e) {
                            System.err.println("Unable to write output.csv.");
                        }
                    }, () -> System.out.println("No results!"));
        } else {
            System.err.println("No file location has been provided. Unable to create pairs.");
        }
    }
}
