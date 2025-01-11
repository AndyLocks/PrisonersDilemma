package com.locfox.prisoners_dilemma.strategy_info;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StrategyInfoTest {

    @Test
    public void default_strategy_static_method() {
        var strategyInfo = StrategyInfo.withDefaults("name", "description");
        var strategyInfo1 = new DefaultStrategyInfoImpl("name", "description");

        assertEquals(strategyInfo, strategyInfo1);
        assertSame(DefaultStrategyInfoImpl.class, strategyInfo.getClass());
    }

}