package com.locfox.prisoners_dilemma.strategy_factory.factory_creator;

import com.locfox.prisoners_dilemma.score_counter.ScoreCounter;
import com.locfox.prisoners_dilemma.score_counter.ScoreCounters;
import com.locfox.prisoners_dilemma.strategy.Strategy;
import com.locfox.prisoners_dilemma.strategy_factory.StrategyFactory;
import com.locfox.prisoners_dilemma.strategy_info.StrategyInfo;
import org.springframework.util.Assert;

import java.util.function.BooleanSupplier;

/// Allows you to create anonymous factories with anonymous strategies
@Deprecated
public class StrategyFactoryCreator {
    /// Creates an anonymous factory with an anonymous strategy with a specific {@link ScoreCounter}
    ///
    /// # Example
    ///
    /// ```java
    /// var strategyInfo = StrategyInfo.withDefaults("Tit for tat",
    ///         "Returns exactly the opponent's previous answer");
    ///
    /// var scoreCounter = ScoreCounters.withDefaults(strategyInfo);
    ///
    /// var strategyFactory = StrategyFactoryCreator.create(
    ///         aBoolean -> aBoolean,
    ///         () -> true,
    ///         scoreCounter);
    ///```
    ///
    /// # See also
    ///
    /// - {@link StrategyInfo#withDefaults(String, String)}
    /// - {@link ScoreCounters#withDefaults(StrategyInfo)}
    ///
    /// @see StrategyInfo#withDefaults(String, String)
    /// @see ScoreCounters#withDefaults(StrategyInfo)
    @Deprecated
    public static StrategyFactory<Strategy> create(CooperationStrategy cooperates, BooleanSupplier initialCooperatesStrategy, ScoreCounter<? extends StrategyInfo> scoreCounter) {
        Assert.noNullElements(new Object[]{cooperates, initialCooperatesStrategy, scoreCounter}, "Arguments must not be null");

        return StrategyFactory.instance(() -> {
            return Strategy.builder()
                    .initialStrategy(initialCooperatesStrategy)
                    .cooperates(cooperates)
                    .scoreCounter(scoreCounter)
                    .build();
        });
    }

    /// Creates an anonymous factory with a specific {@link Strategy}
    ///
    /// # Example
    ///
    /// ```java
    /// var strategy = new StrategyImpl();
    /// var strategyFactory = StrategyFactoryCreator.create(strategy);
    ///```
    ///
    /// # See also
    ///
    /// - {@link StrategyInfo#withDefaults(String, String)}
    /// - {@link ScoreCounters#withDefaults(StrategyInfo)}
    ///
    /// @see StrategyInfo#withDefaults(String, String)
    /// @see ScoreCounters#withDefaults(StrategyInfo)
    @Deprecated
    public static StrategyFactory<Strategy> create(Strategy strategy) {
        Assert.notNull(strategy, "Strategy must not be null");

        return StrategyFactory.instance(() -> strategy);
    }

}
