package com.grube.arena;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PairTest {

    @Test
    void unorderedEquality() {
        Pair p1 = new Pair("One", "Two");
        Pair p2 = new Pair("Two", "One");
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void equivalentMaps() {
        Pair p1 = new Pair("One", "Two");
        Pair p2 = new Pair("Two", "One");
        Map<Pair, Integer> pairs = new HashMap<>();
        pairs.put(p1, 1);
        assertEquals(pairs.get(p1), 1);
        pairs.put(p2, 2);
        assertEquals(pairs.get(p1), 2);
    }
}
