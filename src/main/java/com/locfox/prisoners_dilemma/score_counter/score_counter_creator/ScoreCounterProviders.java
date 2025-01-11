package com.locfox.prisoners_dilemma.score_counter.score_counter_creator;

/// All implementations of [ScoreCounterProvider]
public class ScoreCounterProviders {

    /// Default implementation
    ///
    /// @see DefaultScoreCounterProviderImpl
    public static ScoreCounterProvider withDefaults() {
        return new DefaultScoreCounterProviderImpl();
    }

}
