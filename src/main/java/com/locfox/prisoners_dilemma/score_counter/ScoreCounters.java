package com.locfox.prisoners_dilemma.score_counter;

import com.locfox.prisoners_dilemma.strategy_info.StrategyInfo;

/// This class contains static methods for managing {@link ScoreCounter}
///
/// This class also contains some implementations
public class ScoreCounters {

    /// @param info information about a specific strategy
    /// @return the standard scoring object for the entire application
    /// @see DefaultScoreCounterImpl
    public static <T extends StrategyInfo> DefaultScoreCounterImpl<T> withDefaults(T info) {
        return DefaultScoreCounterImpl.of(info);
    }

}
