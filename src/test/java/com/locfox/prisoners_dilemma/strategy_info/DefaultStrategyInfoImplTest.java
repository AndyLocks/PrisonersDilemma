package com.locfox.prisoners_dilemma.strategy_info;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultStrategyInfoImplTest {

    @Test
    public void name_and_description_test() {
        var strategyInfo = new DefaultStrategyInfoImpl("name", "description");

        assertEquals("name", strategyInfo.name());
        assertEquals("description", strategyInfo.description());
    }

    @Test
    public void static_methods_test() {
        var strategyInfo = new DefaultStrategyInfoImpl("name", "description");
        var strategyInfo1 = DefaultStrategyInfoImpl.of("name", "description");

        assertEquals(strategyInfo, strategyInfo1);

        assertEquals("name", strategyInfo.name());
        assertEquals("description", strategyInfo.description());
    }

}