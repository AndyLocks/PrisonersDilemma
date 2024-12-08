package com.locfox.prisoners_dilemma.score_counter;

import com.locfox.prisoners_dilemma.strategy_info.DefaultStrategyInfoImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultScoreCounterImplTest {

    @Test
    public void points_test() {
        var scoreCounter = new DefaultScoreCounterImpl<>(new DefaultStrategyInfoImpl("name", "description"));

        assertEquals(0, scoreCounter.getPoints());

        scoreCounter.bothBetrayed();
        assertEquals(1, scoreCounter.getPoints());

        scoreCounter.bothBetrayed();
        assertEquals(2, scoreCounter.getPoints());

        scoreCounter.bothBetrayed();
        assertEquals(3, scoreCounter.getPoints());

        scoreCounter.iWasBetrayed();
        assertEquals(3, scoreCounter.getPoints());

        scoreCounter.iWasBetrayed();
        assertEquals(3, scoreCounter.getPoints());

        scoreCounter.iWasBetrayed();
        assertEquals(3, scoreCounter.getPoints());

        scoreCounter.iBetrayed();
        assertEquals(8, scoreCounter.getPoints());

        scoreCounter.iBetrayed();
        assertEquals(13, scoreCounter.getPoints());

        scoreCounter.iBetrayed();
        assertEquals(18, scoreCounter.getPoints());

        scoreCounter.bothCooperate();
        assertEquals(21, scoreCounter.getPoints());

        scoreCounter.bothCooperate();
        assertEquals(24, scoreCounter.getPoints());

        scoreCounter.bothCooperate();
        assertEquals(27, scoreCounter.getPoints());
    }

    @Test
    public void strategy_info_test() {
        var strategyInfo = new DefaultStrategyInfoImpl("name", "description");
        var scoreCounter = new DefaultScoreCounterImpl<>(strategyInfo);

        assertEquals(strategyInfo, scoreCounter.getStrategyInfo());
        assertEquals(strategyInfo.name(), scoreCounter.getStrategyInfo().name());
        assertEquals(strategyInfo.description(), scoreCounter.getStrategyInfo().description());
    }

    @Test
    public void static_methods_test() {
        var strategyInfo = new DefaultStrategyInfoImpl("name", "description");
        var scoreCounter1 = DefaultScoreCounterImpl.of(strategyInfo);
        var scoreCounter2 = new DefaultScoreCounterImpl<>(strategyInfo);

        assertEquals(scoreCounter1, scoreCounter2);
        assertEquals(scoreCounter1.getStrategyInfo(), scoreCounter2.getStrategyInfo());

        assertEquals(scoreCounter1.getStrategyInfo().name(), scoreCounter2.getStrategyInfo().name());
        assertEquals(scoreCounter1.getStrategyInfo().description(), scoreCounter2.getStrategyInfo().description());
    }

}