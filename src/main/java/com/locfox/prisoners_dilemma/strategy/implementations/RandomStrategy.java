package com.locfox.prisoners_dilemma.strategy.implementations;

import com.locfox.prisoners_dilemma.score_counter.ScoreCounter;
import com.locfox.prisoners_dilemma.score_counter.ScoreCounters;
import com.locfox.prisoners_dilemma.strategy.Strategy;
import com.locfox.prisoners_dilemma.strategy_info.DefaultStrategyInfoImpl;

import java.util.Random;

public class RandomStrategy implements Strategy {

    private static final String NAME = "Random";
    private static final String DESCRIPTION = "Always random actions";
    private static final ScoreCounter<DefaultStrategyInfoImpl> scoreCounter =
            ScoreCounters.withDefaults(DefaultStrategyInfoImpl.of(NAME, DESCRIPTION));
    private static final Random random = new Random();

    @Override
    public boolean initialCooperatesStrategy() {
        return random.nextBoolean();
    }

    @Override
    public boolean cooperates(boolean opponentsPreviousAction) {
        return random.nextBoolean();
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
