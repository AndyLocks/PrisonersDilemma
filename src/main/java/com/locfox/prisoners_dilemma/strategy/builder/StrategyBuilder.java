package com.locfox.prisoners_dilemma.strategy.builder;

import com.locfox.prisoners_dilemma.score_counter.ScoreCounter;
import com.locfox.prisoners_dilemma.score_counter.score_counter_creator.DefaultScoreCounterProviderImpl;
import com.locfox.prisoners_dilemma.score_counter.score_counter_creator.ScoreCounterProvider;
import com.locfox.prisoners_dilemma.score_counter.score_counter_creator.ScoreCounterProviders;
import com.locfox.prisoners_dilemma.strategy.Strategy;
import com.locfox.prisoners_dilemma.strategy_factory.factory_creator.CooperationStrategy;
import com.locfox.prisoners_dilemma.strategy_info.StrategyInfo;
import org.springframework.util.Assert;

import java.util.function.BooleanSupplier;

/// Allows you to create an anonymous strategy
///
/// # Example
/// ```java
/// var titForTat = StrategyBuilder.builder()
///                 .initialStrategy(() -> true)
///                 .cooperates(opponentsPreviousAction -> opponentsPreviousAction)
///                 .info(StrategyInfo.withDefaults("Tit for tat", "Returns exactly the opponent's previous answer"))
///                 .build();
/// ```
public class StrategyBuilder {

    private CooperationStrategy cooperationStrategy;
    private BooleanSupplier initialCooperationStrategy;
    private ScoreCounterProvider scoreCounterCreator = ScoreCounterProviders.withDefaults();
    private StrategyInfo strategyInfo;

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

    /// Changes the implementation of [ScoreCounterProvider]
    ///
    /// By default: [ScoreCounterProviders#withDefaults()] ([DefaultScoreCounterProviderImpl])
    ///
    /// @see ScoreCounterProvider
    public StrategyBuilder scoreCounterCreator(ScoreCounterProvider scoreCounterCreator) {
        this.scoreCounterCreator = scoreCounterCreator;
        return this;
    }

    /// Sets strategy information
    ///
    /// This is necessary to display information about the strategy and also to compare it with other strategies.
    /// In some standard implementations, the information determines what [ScoreCounter] will be issued.
    public StrategyBuilder info(StrategyInfo strategyInfo) {
        this.strategyInfo = strategyInfo;
        return this;
    }

    /// @return a new anonymous strategy
    /// @throws IllegalArgumentException if strategy, initial strategy or score counter is null
    public Strategy build() {
        Assert.notNull(this.cooperationStrategy, "Cooperation strategy must not be null");
        Assert.notNull(this.initialCooperationStrategy, "Initial cooperation strategy must not be null");
        Assert.notNull(this.strategyInfo, "Strategy info must not be null");

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
                return scoreCounterCreator.createOrGet(strategyInfo);
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(Object o) {
                return o != null && getClass() == o.getClass();
            }

            @Override
            public String toString() {
                return String.format("Info [%s] Points [%d]", strategyInfo.name(), getScoreCounter().getPoints());
            }

        };
    }

}
