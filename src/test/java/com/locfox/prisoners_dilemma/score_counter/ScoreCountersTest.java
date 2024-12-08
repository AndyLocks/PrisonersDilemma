package com.locfox.prisoners_dilemma.score_counter;

import com.locfox.prisoners_dilemma.strategy_info.DefaultStrategyInfoImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreCountersTest {

    @Test
    public void static_methods_test() {
        var strategyInfo = new DefaultStrategyInfoImpl("name", "description");
        var scoreCounter = ScoreCounters.withDefaults(strategyInfo);
        var scoreCounter1 = new DefaultScoreCounterImpl<>(strategyInfo);

        assertEquals(scoreCounter, scoreCounter1);
    }

}