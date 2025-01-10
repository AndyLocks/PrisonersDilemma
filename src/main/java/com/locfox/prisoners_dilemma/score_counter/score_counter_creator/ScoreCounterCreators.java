package com.locfox.prisoners_dilemma.score_counter.score_counter_creator;

/// All implementations of [ScoreCounterCreator]
public class ScoreCounterCreators {

    /// Default implementation
    ///
    /// @see DefaultScoreCounterCreatorImpl
    public static ScoreCounterCreator withDefaults() {
        return new DefaultScoreCounterCreatorImpl();
    }

}
