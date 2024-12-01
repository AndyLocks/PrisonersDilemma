package com.locfox.prisoners_dilemma.strategy.implementations;

import com.locfox.prisoners_dilemma.score_counter.ScoreCounter;
import com.locfox.prisoners_dilemma.score_counter.ScoreCounters;
import com.locfox.prisoners_dilemma.strategy.Strategy;
import com.locfox.prisoners_dilemma.strategy_info.DefaultStrategyInfoImpl;

public class AlwaysDefectStrategy implements Strategy {
    private static final String NAME = "Always Defect";
    private static final String DESCRIPTION = "Always chooses betrayal";
    private static final ScoreCounter<DefaultStrategyInfoImpl> scoreCounter =
            ScoreCounters.withDefaults(DefaultStrategyInfoImpl.of(NAME, DESCRIPTION));

    @Override
    public boolean initialCooperatesStrategy() {
        return false;
    }

    @Override
    public boolean cooperates(boolean opponentsPreviousAction) {
        return false;
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
