package com.locfox.prisoners_dilemma.strategy.implementations;

import com.locfox.prisoners_dilemma.score_counter.ScoreCounter;
import com.locfox.prisoners_dilemma.score_counter.ScoreCounters;
import com.locfox.prisoners_dilemma.strategy.Strategy;
import com.locfox.prisoners_dilemma.strategy_info.DefaultStrategyInfoImpl;

/// Forgives one betrayal, but reacts with betrayal if it is repeated twice in a row
public class TitForTwoTatsStrategy implements Strategy {

    private static final String NAME = "Tit for two tats";
    private static final String DESCRIPTION = "Forgives one betrayal, but reacts with betrayal if it is repeated twice in a row.";
    private static final ScoreCounter<DefaultStrategyInfoImpl> scoreCounter =
            ScoreCounters.withDefaults(DefaultStrategyInfoImpl.of(NAME, DESCRIPTION));

    private boolean iWasBetrayed = false;

    @Override
    public boolean initialCooperatesStrategy() {
        return true;
    }

    @Override
    public boolean cooperates(boolean opponentsPreviousAction) {
        if (opponentsPreviousAction) return true;

        if (iWasBetrayed) {
            iWasBetrayed = false;

            return false;
        }

        iWasBetrayed = true;

        return true;
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
