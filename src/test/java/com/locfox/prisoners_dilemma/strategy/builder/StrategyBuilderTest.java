package com.locfox.prisoners_dilemma.strategy.builder;

import com.locfox.prisoners_dilemma.score_counter.DefaultScoreCounterImpl;
import com.locfox.prisoners_dilemma.score_counter.ScoreCounters;
import com.locfox.prisoners_dilemma.strategy_factory.implementations.TitForTatStrategyFactory;
import com.locfox.prisoners_dilemma.strategy_info.StrategyInfo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StrategyBuilderTest {

    @Test
    public void cooperates_test() {
        var titForTatStrategyMadeWithBuilder = StrategyBuilder.builder()
                .info(StrategyInfo.withDefaults("Tit for tat", "made with builder"))
                .initialStrategy(() -> true)
                .cooperates(opponentsPreviousAction -> opponentsPreviousAction)
                .build();

        var titForTatStrategy = TitForTatStrategyFactory.instance();

        assertTrue(titForTatStrategyMadeWithBuilder.cooperates(true));
        assertTrue(titForTatStrategy.cooperates(true));
        assertEquals(titForTatStrategy.cooperates(true), titForTatStrategyMadeWithBuilder.cooperates(true));

        assertFalse(titForTatStrategyMadeWithBuilder.cooperates(false));
        assertFalse(titForTatStrategy.cooperates(false));
        assertEquals(titForTatStrategy.cooperates(false), titForTatStrategyMadeWithBuilder.cooperates(false));
    }

    @Test
    public void initial_cooperates_test() {
        var titForTatStrategyMadeWithBuilder = StrategyBuilder.builder()
                .info(StrategyInfo.withDefaults("Tit for tat", "made with builder"))
                .initialStrategy(() -> true)
                .cooperates(opponentsPreviousAction -> opponentsPreviousAction)
                .build();

        var titForTatStrategy = TitForTatStrategyFactory.instance();

        assertEquals(titForTatStrategy.initialCooperatesStrategy(), titForTatStrategyMadeWithBuilder.initialCooperatesStrategy());
        assertTrue(titForTatStrategy.initialCooperatesStrategy());
        assertTrue(titForTatStrategyMadeWithBuilder.initialCooperatesStrategy());
    }

    @Test
    public void score_counter_creator_test() {
        var titForTat1 = StrategyBuilder.builder()
                .initialStrategy(() -> true)
                .cooperates(opponentsPreviousAction -> opponentsPreviousAction)
                .info(StrategyInfo.withDefaults("Tit for tat", "Returns exactly the opponent's previous answer"))
                .build();

        var titForTat2 = StrategyBuilder.builder()
                .initialStrategy(() -> true)
                .cooperates(opponentsPreviousAction -> opponentsPreviousAction)
                .info(StrategyInfo.withDefaults("Tit for tat", "Returns exactly the opponent's previous answer"))
                .build();

        var alwaysCooperates = StrategyBuilder.builder()
                .initialStrategy(() -> true)
                .cooperates(opponentsPreviousAction -> opponentsPreviousAction)
                .info(StrategyInfo.withDefaults("Always Cooperates", "Always Cooperates"))
                .build();

        assertSame(titForTat1.getScoreCounter(), titForTat2.getScoreCounter());
        assertNotSame(titForTat1.getScoreCounter(), alwaysCooperates.getScoreCounter());
        assertNotSame(titForTat2.getScoreCounter(), alwaysCooperates.getScoreCounter());
    }

    @Test
    public void score_counter_creator_test2() {
        var titForTat1 = StrategyBuilder.builder()
                .initialStrategy(() -> true)
                .scoreCounterCreator(ScoreCounters::withDefaults)
                .cooperates(opponentsPreviousAction -> opponentsPreviousAction)
                .info(StrategyInfo.withDefaults("Tit for tat", "Returns exactly the opponent's previous answer"))
                .build();

        var titForTat2 = StrategyBuilder.builder()
                .initialStrategy(() -> true)
                .scoreCounterCreator(ScoreCounters::withDefaults)
                .cooperates(opponentsPreviousAction -> opponentsPreviousAction)
                .info(StrategyInfo.withDefaults("Tit for tat", "Returns exactly the opponent's previous answer"))
                .build();

        var alwaysCooperates = StrategyBuilder.builder()
                .initialStrategy(() -> true)
                .scoreCounterCreator(ScoreCounters::withDefaults)
                .cooperates(opponentsPreviousAction -> opponentsPreviousAction)
                .info(StrategyInfo.withDefaults("Always Cooperates", "Always Cooperates"))
                .build();

        assertNotSame(titForTat1.getScoreCounter(), titForTat2.getScoreCounter());
        assertNotSame(titForTat1.getScoreCounter(), alwaysCooperates.getScoreCounter());
        assertNotSame(titForTat2.getScoreCounter(), alwaysCooperates.getScoreCounter());
    }

    @AfterAll
    public static void afterAll() {
        TitForTatStrategyFactory.instance().getScoreCounter().reset();
    }

}