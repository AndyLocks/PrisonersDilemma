package com.locfox.prisoners_dilemma.strategy.implementations;

import com.locfox.prisoners_dilemma.score_counter.ScoreCounter;
import com.locfox.prisoners_dilemma.score_counter.ScoreCounters;
import com.locfox.prisoners_dilemma.strategy.Strategy;
import com.locfox.prisoners_dilemma.strategy_info.DefaultStrategyInfoImpl;

import java.util.Random;

/// Starts with collaboration if another player betrays, it forgives in 10% of cases
public class GenerousTitForTatStrategy implements Strategy {

    private static final String NAME = "Generous Tit For Tat Strategy";
    private static final String DESCRIPTION = "Starts with collaboration if another player betrays, it forgives in 10% of cases";
    private static final ScoreCounter<DefaultStrategyInfoImpl> scoreCounter =
            ScoreCounters.withDefaults(DefaultStrategyInfoImpl.of(NAME, DESCRIPTION));

    private static final Random random = new Random();
    /// Mercy chance in percent
    private static final int mercyChance = 10;

    @Override
    public boolean initialCooperatesStrategy() {
        return true;
    }

    @Override
    public boolean cooperates(boolean opponentsPreviousAction) {
        return opponentsPreviousAction || (random.nextInt(100) < mercyChance);
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
