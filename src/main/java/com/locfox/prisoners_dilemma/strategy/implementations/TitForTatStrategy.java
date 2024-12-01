package com.locfox.prisoners_dilemma.strategy.implementations;

import com.locfox.prisoners_dilemma.score_counter.ScoreCounter;
import com.locfox.prisoners_dilemma.score_counter.ScoreCounters;
import com.locfox.prisoners_dilemma.strategy.Strategy;
import com.locfox.prisoners_dilemma.strategy_info.DefaultStrategyInfoImpl;

/// Returns exactly the opponent's previous answer
public class TitForTatStrategy implements Strategy {

    private static final String NAME = "Tit for tat";
    private static final String DESCRIPTION = "Returns exactly the opponent's previous answer";
    private static final ScoreCounter<DefaultStrategyInfoImpl> scoreCounter =
            ScoreCounters.withDefaults(DefaultStrategyInfoImpl.of(NAME, DESCRIPTION));

    @Override
    public boolean initialCooperatesStrategy() {
        return true;
    }

    @Override
    public boolean cooperates(boolean opponentsPreviousAction) {
        return opponentsPreviousAction;
    }

    @Override
    public ScoreCounter<DefaultStrategyInfoImpl> getScoreCounter() {
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

}
