package com.locfox.prisoners_dilemma.strategy.implementations;

import com.locfox.prisoners_dilemma.strategy_factory.implementations.FriedmanStrategyFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FriedmanStrategyTest {

    @Test
    public void test() {
        var strategy = FriedmanStrategyFactory.instance();

        for (int i = 0; i < 50; i++) {
            assertTrue(strategy.cooperates(true));
        }

        for (int i = 0; i < 50; i++) {
            assertFalse(strategy.cooperates(false));
        }

    }

    @AfterAll
    public static void afterAll() {
        FriedmanStrategyFactory.instance().getScoreCounter().reset();
    }


}