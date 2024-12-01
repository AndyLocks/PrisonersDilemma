package com.locfox.prisoners_dilemma.strategy.builder;

import com.locfox.prisoners_dilemma.score_counter.ScoreCounter;
import com.locfox.prisoners_dilemma.strategy.Strategy;
import com.locfox.prisoners_dilemma.strategy_factory.factory_creator.CooperationStrategy;
import com.locfox.prisoners_dilemma.strategy_info.StrategyInfo;
import org.springframework.util.Assert;

import java.util.function.BooleanSupplier;

/// Allows you to create an anonymous strategy
///
/// # Example
/// ```java
/// var strategy = StrategyBuilder.builder()
///     .initialStrategy(() -> true)
///     .cooperates(opponentsPreviousAction -> true)
///     .scoreCounter(someScoreCounter)
///     .build();
///```
public class StrategyBuilder {

    private CooperationStrategy cooperationStrategy;
    private BooleanSupplier initialCooperationStrategy;
    private ScoreCounter<? extends StrategyInfo> scoreCounter;

    public static StrategyBuilder builder() {
        return new StrategyBuilder();
    }

    /// Sets a strategy provided as a functional interface and returns the builder
    ///
    /// @param strategy a function, that returns boolean
    /// @return this builder with the strategy
    public StrategyBuilder cooperates(CooperationStrategy strategy) {
        this.cooperationStrategy = strategy;
        return this;
    }

    /// Sets an initial strategy provided as a functional interface and returns the builder
    ///
    /// @param supplier a supplier, that returns boolean
    /// @return this builder with the initial strategy
    public StrategyBuilder initialStrategy(BooleanSupplier supplier) {
        initialCooperationStrategy = supplier;
        return this;
    }

    /// Sets a score counter and returns the builder
    ///
    /// @param scoreCounter a score counter to set
    /// @return this builder with the score counter
    /// @see com.locfox.prisoners_dilemma.score_counter.ScoreCounters#withDefaults(StrategyInfo)
    /// @see StrategyInfo#withDefaults(String, String)
    public StrategyBuilder scoreCounter(ScoreCounter<? extends StrategyInfo> scoreCounter) {
        this.scoreCounter = scoreCounter;
        return this;
    }

    /// @return a new anonymous strategy
    /// @throws IllegalArgumentException if strategy, initial strategy or score counter is null
    public Strategy build() {
        Assert.notNull(this.cooperationStrategy, "Cooperation strategy must not be null");
        Assert.notNull(this.initialCooperationStrategy, "Initial cooperation strategy must not be null");
        Assert.notNull(this.scoreCounter, "Score counter must not be null");

        return new Strategy() {
            @Override
            public boolean initialCooperatesStrategy() {
                return initialCooperationStrategy.getAsBoolean();
            }

            @Override
            public boolean cooperates(boolean opponentsPreviousAction) {
                return cooperationStrategy.cooperates(opponentsPreviousAction);
            }

            @Override
            public ScoreCounter<? extends StrategyInfo> getScoreCounter() {
                return scoreCounter;
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(Object o) {
                return o != null && getClass() == o.getClass();
            }

        };
    }

}
