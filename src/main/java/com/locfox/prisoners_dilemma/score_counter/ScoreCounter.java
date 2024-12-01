package com.locfox.prisoners_dilemma.score_counter;

import com.locfox.prisoners_dilemma.strategy_info.StrategyInfo;

import java.util.stream.Stream;

/// Allows you to calculate points for each strategy separately.
/// For each case there are methods that change the number of points in their own way.
public interface ScoreCounter<T extends StrategyInfo> {
    /// In the event that both participants cooperate.
    void bothCooperate();

    /// In the event that both participants betray.
    void bothBetrayed();

    /// In the case where the strategy has decided to cooperate, but its opponent has chosen to defect.
    void iWasBetrayed();

    /// In the case where a strategy has decided to betray its opponent while its opponent has chosen to cooperate.
    void iBetrayed();

    /// @return an object that contains information about the strategy
    T getStrategyInfo();

    /// @return current points scored by the strategy
    int getPoints();

    /// Deletes all points
    ///
    /// If the game is over and a new one is planned to start under new rules,
    /// this method will help to reset the points.
    void reset();

    /// @return a stream that contains only one element (this score counter).
    default Stream<ScoreCounter<T>> asStream() {
        return Stream.<ScoreCounter<T>>of(this);
    }
}
