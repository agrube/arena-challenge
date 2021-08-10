package com.grube.arena;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Pair(String one, String two) {

    public String toAlphabetizedString() {
        return Stream.of(one, two).sorted().reduce((accum, cur) -> accum + "," + cur).orElse(null);
    }

    public String[] toAlphabetizedArray() {
        return toAlphabetizedString().split(",");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return (Objects.equals(one, pair.one) && Objects.equals(two, pair.two))
                || (Objects.equals(one, pair.two) && Objects.equals(two, pair.one));
    }

    @Override
    public int hashCode() {
        // alphabetize it so that pairs that contain the same values will be the same no matter what order
        // the values are in
        String alphabetizedKey = toAlphabetizedString();
        return Objects.hash(alphabetizedKey);
    }
}
