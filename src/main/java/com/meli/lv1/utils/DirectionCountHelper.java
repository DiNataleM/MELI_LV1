package com.meli.lv1.utils;

import java.util.HashMap;
import java.util.Map;

public class DirectionCountHelper {

    public enum Direction {
        HORIZONTAL,
        VERTICAL,
        OBLIQUE_DOW_RIGHT, OBLIQUE_DOW_LEFT,
        OBLIQUE_TOP_RIGHT, OBLIQUE_TOP_LEFT
    }

    private Map<Direction, CountHelper> counters;

    public DirectionCountHelper(int maxSequence) {
        counters = new HashMap<>();

        for (Direction direction : Direction.values()) {
            counters.put(direction, new CountHelper(maxSequence));
        }

    }

    public void check(Direction direction, Character newLetter) {
        counters.get(direction).check(newLetter);
    }

    public void resetLoop() {
        counters.values().forEach(c -> c.resetLoop());
    }

    public int getCoincidences() {
        return counters.values().stream().mapToInt(d -> d.getCoincidence()).sum();
    }

}
