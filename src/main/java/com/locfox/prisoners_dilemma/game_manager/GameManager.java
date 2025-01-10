package com.locfox.prisoners_dilemma.game_manager;

import com.locfox.prisoners_dilemma.score_counter.ScoreCounter;
import com.locfox.prisoners_dilemma.strategy.Strategy;
import com.locfox.prisoners_dilemma.strategy_factory.StrategyFactory;

import java.util.Arrays;
import java.util.Collection;

/// Manages the game according to its own internal rules using {@link Strategy}
///
/// This class also changes the internal state of strategies.
/// Namely {@link com.locfox.prisoners_dilemma.score_counter.ScoreCounter}.
///
/// Each strategy, when implemented correctly, contains only one score counter and it is static.
/// Thus, each strategy object always returns the same counter.
///
/// If you need to start multiple games with different rules,
/// the {@link com.locfox.prisoners_dilemma.score_counter.ScoreCounter} has a method for resetting points:
/// {@link ScoreCounter#reset()}
///
/// # Implementation
///
/// It is advisable to create a new {@link Strategy} every round with each game using {@link StrategyFactory}.
///
/// # Examples
///
/// ## Example 1
///
/// ```java
/// var factories = GameManagers.withDefaults().play(
///                 () -> StrategyBuilder.builder()
///                         .initialStrategy(() -> true)
///                         .cooperates(opponentsPreviousAction -> true)
///                         .info(StrategyInfo.withDefaults("Always cooperate", "Always cooperates"))
///                         .build(),
///                 () -> StrategyBuilder.builder()
///                         .initialStrategy(() -> false)
///                         .cooperates(opponentsPreviousAction -> false)
///                         .info(StrategyInfo.withDefaults("Always defect", "Never cooperates"))
///                         .build(),
///                 () -> StrategyBuilder.builder()
///                         .initialStrategy(() -> true)
///                         .cooperates(opponentsPreviousAction -> opponentsPreviousAction)
///                         .info(StrategyInfo.withDefaults("Tit for tat", "Returns exactly the opponent's previous answer"))
///                         .build());
/// ```
///
/// ## Example 2
///
/// ```java
/// List<StrategyFactory<? extends Strategy>> strategyFactories = ...;
/// gameManager.play(strategyFactories);
/// ```
///
/// ## Example 3
///
/// ```java
/// gameManager.play(
///     TitForTatStrategyFactory::instance,
///     AlwaysDefectStrategyFactory::instance);
/// ```
///
///
/// # See also
///
/// - {@link ScoreCounter} - for counting points
/// - {@link com.locfox.prisoners_dilemma.score_counter.ScoreCounters} - to get an implementation of {@link ScoreCounter}
///
/// @see ScoreCounter
/// @see com.locfox.prisoners_dilemma.score_counter.ScoreCounters
public interface GameManager {
    /// Starts the game following its internal rules.
    ///
    /// This method also changes the internal state of strategies.
    /// Namely {@link com.locfox.prisoners_dilemma.score_counter.ScoreCounter}.
    ///
    /// Each strategy, when implemented correctly, contains only one score counter and it is static.
    /// Thus, each strategy object always returns the same counter.
    ///
    /// # Implementation
    ///
    /// It is advisable to create a new {@link Strategy} every round with each game using {@link StrategyFactory}.
    ///
    /// # See also
    ///
    /// - {@link ScoreCounter} - for counting points
    /// - {@link com.locfox.prisoners_dilemma.score_counter.ScoreCounters} - to get an implementation of {@link ScoreCounter}
    ///
    /// @param strategies a factories of the players.
    /// @throws com.locfox.prisoners_dilemma.exceptions.TooFewStrategies if list is empty or contains less than 2 fabrics.
    /// @see ScoreCounter
    /// @see com.locfox.prisoners_dilemma.score_counter.ScoreCounters
    Collection<? extends StrategyFactory<? extends Strategy>> play(final Collection<? extends StrategyFactory<? extends Strategy>> strategies);

    /// Starts the game following its internal rules.
    ///
    /// This method also changes the internal state of strategies.
    /// Namely {@link com.locfox.prisoners_dilemma.score_counter.ScoreCounter}.
    ///
    /// Each strategy, when implemented correctly, contains only one score counter and it is static.
    /// Thus, each strategy object always returns the same counter.
    ///
    /// # Implementation
    ///
    /// It is advisable to create a new {@link Strategy} every round with each game using {@link StrategyFactory}.
    ///
    /// # See also
    ///
    /// - {@link ScoreCounter} - for counting points
    /// - {@link com.locfox.prisoners_dilemma.score_counter.ScoreCounters} - to get an implementation of {@link ScoreCounter}
    ///
    /// @param strategies a factories of the players.
    /// @throws com.locfox.prisoners_dilemma.exceptions.TooFewStrategies if list is empty or contains less than 2 fabrics.
    /// @see ScoreCounter
    /// @see com.locfox.prisoners_dilemma.score_counter.ScoreCounters
    default Collection<? extends StrategyFactory<? extends Strategy>> play(final StrategyFactory<? extends Strategy>... strategies) {
        return this.play(Arrays.asList(strategies));
    }
}
