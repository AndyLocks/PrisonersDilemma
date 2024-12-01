package com.locfox.prisoners_dilemma.exceptions;

import java.util.Collection;

/// Throws if there are less than two strategies in {@link com.locfox.prisoners_dilemma.game_manager.GameManager#play(Collection)}.
public class TooFewStrategies extends RuntimeException {
    public TooFewStrategies(String message) {
        super(message);
    }
}
