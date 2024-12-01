package com.locfox.prisoners_dilemma.strategy.implementations;

import com.locfox.prisoners_dilemma.score_counter.ScoreCounter;
import com.locfox.prisoners_dilemma.score_counter.ScoreCounters;
import com.locfox.prisoners_dilemma.strategy.Strategy;
import com.locfox.prisoners_dilemma.strategy_info.DefaultStrategyInfoImpl;

/// Cooperates until the other player defects; after the first defection, always defects
public class GrimTriggerStrategy implements Strategy {

    private static final String NAME = "Grim Trigger";
    private static final String DESCRIPTION = "Cooperates until the other player defects; after the first defection, always defects";
    private static final ScoreCounter<DefaultStrategyInfoImpl> scoreCounter =
            ScoreCounters.withDefaults(DefaultStrategyInfoImpl.of(NAME, DESCRIPTION));

    private boolean iWasBetrayed = false;

    @Override
    public boolean initialCooperatesStrategy() {
        return true;
    }

    @Override
    public boolean cooperates(boolean opponentsPreviousAction) {
        if (!opponentsPreviousAction) this.iWasBetrayed = true;

        return !this.iWasBetrayed;
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
