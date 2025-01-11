package com.locfox.prisoners_dilemma.score_counter.score_counter_creator;

import com.locfox.prisoners_dilemma.score_counter.ScoreCounter;
import com.locfox.prisoners_dilemma.strategy_info.StrategyInfo;

import java.util.function.Function;

/// An object that can convert [StrategyInfo] into a [ScoreCounter].
/// This score counter can be cached for a specific [StrategyInfo].
///
/// ### Examples
///
/// ```java
/// var titForTatStrategy = StrategyBuilder.builder()
///                 .info(StrategyInfo.withDefaults("Tit for tat", "made with builder"))
///                 .scoreCounterCreator(ScoreCounters::withDefaults)
///                 .initialStrategy(() -> true)
///                 .cooperates(opponentsPreviousAction -> opponentsPreviousAction)
///                 .build();
/// ```
///
/// In this case, the method `scoreCounterCreator` always returns a new counter, which is not considered a good practice.
/// The builder changes the standard implementation to a new [ScoreCounterProvider].
///
/// ---
///
/// ### Implementation
///
/// In an ideal implementation, this object should return the same counter via `StrategyInfo`,
/// which doesn't have to be the same, it just has to be the same via [Object#equals(Object)]
///
/// You can also think of this as a [java.util.HashMap] in which the key is [StrategyInfo] and the value is [ScoreCounter]
///
/// ---
///
/// You can find standard implementations in [ScoreCounterProviders] ([ScoreCounterProviders#withDefaults()])
///
/// ### Implementations
/// - [DefaultScoreCounterProviderImpl]: inside it contains a [java.util.HashMap] and returns the required counter for the same strategy data
public interface ScoreCounterProvider extends Function<StrategyInfo, ScoreCounter<? extends StrategyInfo>> {

    default ScoreCounter<? extends StrategyInfo> createOrGet(StrategyInfo strategyInfo) {
        return apply(strategyInfo);
    }

}
