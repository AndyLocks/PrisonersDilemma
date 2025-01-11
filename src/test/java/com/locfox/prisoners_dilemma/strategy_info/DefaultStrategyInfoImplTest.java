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
        assertSame(DefaultStrategyInfoImpl.class, strategyInfo.getClass());

        assertEquals("name", strategyInfo.name());
        assertEquals("description", strategyInfo.description());
    }

    @Test
    public void equals_test() {
        var info1 = new DefaultStrategyInfoImpl("aboba", "aboba");
        var info2 = new DefaultStrategyInfoImpl("aboba", "aboba");
        var info3 = new DefaultStrategyInfoImpl("Aboba", "Aboba");

        assertEquals(info1, info2);
        assertNotEquals(info1, info3);
        assertNotEquals(info2, info3);
    }

    @Test void hashcode_test() {
        var info1 = new DefaultStrategyInfoImpl("aboba", "aboba");
        var info2 = new DefaultStrategyInfoImpl("aboba", "aboba");
        var info3 = new DefaultStrategyInfoImpl("Aboba", "Aboba");

        assertEquals(info1.hashCode(), info2.hashCode());
        assertNotEquals(info1.hashCode(), info3.hashCode());
        assertNotEquals(info2.hashCode(), info3.hashCode());
    }

}