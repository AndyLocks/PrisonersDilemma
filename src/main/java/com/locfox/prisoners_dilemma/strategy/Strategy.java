package com.locfox.prisoners_dilemma.strategy;

import com.locfox.prisoners_dilemma.score_counter.ScoreCounter;
import com.locfox.prisoners_dilemma.score_counter.ScoreCounters;
import com.locfox.prisoners_dilemma.strategy.builder.StrategyBuilder;
import com.locfox.prisoners_dilemma.strategy_info.StrategyInfo;

/// Describes the strategy that decides whether to betray an opponent or cooperate.
///
/// # Implementation
///
/// **Override methods {@link Object#equals(Object)} and {@link Object#hashCode()}.**
///
/// ## Factory
///
/// Also implement {@link com.locfox.prisoners_dilemma.strategy_factory.StrategyFactory}
///
/// ## See also documentation of
///
///  - {@link Strategy#getScoreCounter()}
public interface Strategy {

    static StrategyBuilder builder() {
        return new StrategyBuilder();
    }

    /// # Initial Strategy
    ///
    /// Each strategy first chooses its first action.
    /// In this case, the strategy decides whether it wants to cooperate.
    ///
    /// @return true if the strategy want to cooperate
    boolean initialCooperatesStrategy();

    /// Here the strategy chooses whether it wants to cooperate based on the opponent's previous action.
    ///
    /// @param opponentsPreviousAction opponent's previous action. True if cooperates.
    /// @return true if the strategy want to cooperate
    boolean cooperates(boolean opponentsPreviousAction);

    /// Returns a score counter to count points.
    ///
    /// # Implementation
    ///
    /// This counter **should be static for the whole class**.
    ///
    /// ## Example
    ///
    /// ```java
    ///     private static final ScoreCounter<DefaultStrategyInfoImpl> scoreCounter =
    ///             ScoreCounters.withDefaults(DefaultStrategyInfoImpl.of(NAME, DESCRIPTION));
    ///```
    ///
    /// ## See also
    ///
    ///  - {@link ScoreCounters} - here you can get a concrete implementation of the counter ({@link ScoreCounters#withDefaults(StrategyInfo)}).
    ///
    /// @return a score counter to count points
    ScoreCounter<? extends StrategyInfo> getScoreCounter();

    boolean equals(Object o);

    int hashCode();

    /// Returns an object containing information about the strategy.
    ///
    /// # See also
    ///
    ///  - {@link StrategyInfo#withDefaults(String, String)}
    ///  - {@link com.locfox.prisoners_dilemma.strategy_info.DefaultStrategyInfoImpl#withDefaults(String, String)}
    ///
    /// @return a strategy information, that contains a name and description
    default StrategyInfo info() {
        return this.getScoreCounter().getStrategyInfo();
    }

    /// Here the strategy chooses whether it wants to defect based on the opponent's previous result.
    ///
    /// # Warning
    ///
    /// It is advisable not to override this method.
    /// Its standard implementation inverts {@link Strategy#cooperates(boolean)}.
    ///
    /// @param opponentsPreviousAction opponent's previous action. Ture if cooperates.
    /// @return true if the strategy want to defect
    default boolean betray(boolean opponentsPreviousAction) {
        return !cooperates(opponentsPreviousAction);
    }

    /// # Initial Strategy
    ///
    /// Each strategy first chooses its first action.
    /// In this case, the strategy decides whether it wants to cooperate.
    ///
    /// # Warning
    /// It is advisable not to override this method.
    /// Its standard implementation inverts {@link Strategy#initialCooperatesStrategy()}}.
    ///
    /// @return true if the strategy want to defect
    default boolean initialBetrayStrategy() {
        return !initialCooperatesStrategy();
    }
}
