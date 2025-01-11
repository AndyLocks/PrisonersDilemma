package com.locfox.prisoners_dilemma.score_counter.score_counter_creator;

import com.locfox.prisoners_dilemma.strategy_info.StrategyInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultScoreCounterProviderImplTest {

    @Test
    public void contract_test() {
        var scoreCounterProvider = new DefaultScoreCounterProviderImpl();
        var info1 = StrategyInfo.withDefaults("aboba", "aboba");
        var info2 = StrategyInfo.withDefaults("aboba", "aboba");
        var info3 = StrategyInfo.withDefaults("Aboba", "Aboba");

        assertSame(scoreCounterProvider.createOrGet(info1), scoreCounterProvider.createOrGet(info2));
        assertSame(scoreCounterProvider.createOrGet(info1), scoreCounterProvider.createOrGet(info2));
        assertNotSame(scoreCounterProvider.createOrGet(info1), scoreCounterProvider.createOrGet(info3));
        assertNotSame(scoreCounterProvider.createOrGet(info2), scoreCounterProvider.createOrGet(info3));
    }

}