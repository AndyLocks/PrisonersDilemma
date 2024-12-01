package com.locfox.prisoners_dilemma.strategy.implementations;

import com.locfox.prisoners_dilemma.strategy_factory.implementations.AlwaysCooperateStrategyFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlwaysCooperateStrategyTest {

    @Test
    public void test() {
        var strategy = AlwaysCooperateStrategyFactory.instance();

        for (int i = 0; i < 50; i++) {
            assertTrue(strategy.cooperates(true));
        }

        for (int i = 0; i < 50; i++) {
            assertTrue(strategy.cooperates(false));
        }

    }
  
}