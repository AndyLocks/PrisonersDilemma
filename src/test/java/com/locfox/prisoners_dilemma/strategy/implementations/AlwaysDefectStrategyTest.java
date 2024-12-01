package com.locfox.prisoners_dilemma.strategy.implementations;

import com.locfox.prisoners_dilemma.strategy_factory.implementations.AlwaysDefectStrategyFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlwaysDefectStrategyTest {

    @Test
    public void test() {
        var strategy = AlwaysDefectStrategyFactory.instance();

        for (int i = 0; i < 50; i++) {
            assertFalse(strategy.cooperates(true));
        }

        for (int i = 0; i < 50; i++) {
            assertFalse(strategy.cooperates(false));
        }
    }

}