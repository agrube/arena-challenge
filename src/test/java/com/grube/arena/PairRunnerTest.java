package com.grube.arena;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PairRunnerTest {

    private static PairRunner runner;
    private static String testString;
    private static List<String> testStringList;

    @BeforeAll
    static void setup() {
        runner = new PairRunner();
        testString = "1";
        testStringList = List.of("2", "3", "4", "5", "6");
    }

    @Test
    void testGeneratePairsForValueBaseCase() {
        Stream<Pair> result = runner.generatePairsForValue(testString, List.of());
        assertEquals(result.count(), 0);
    }

    @Test
    void testGeneratePairsForValueDuplicates() {
        Stream<Pair> result = runner.generatePairsForValue(testString, List.of(testString));
        assertEquals(result.count(), 0);
    }

    @Test
    void testGeneratePairsForValueListOfOne() {
        Stream<Pair> result = runner.generatePairsForValue(testString, List.of(testStringList.get(0)));
        assertEquals(result.count(), 1);
    }

    @Test
    void testGeneratePairsForValueFullList() {
        Stream<Pair> result = runner.generatePairsForValue(testString, testStringList);
        assertEquals(result.count(), 5);
    }

    @Test
    void testGeneratePairs() {

    }

    @Test
    void testFilterPairs() {

    }
}
