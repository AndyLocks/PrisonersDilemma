package com.locfox.prisoners_dilemma.game_manager;

import com.locfox.prisoners_dilemma.strategy_factory.implementations.AlwaysCooperateStrategyFactory;
import com.locfox.prisoners_dilemma.strategy_factory.implementations.AlwaysDefectStrategyFactory;
import com.locfox.prisoners_dilemma.strategy_factory.implementations.TitForTatStrategyFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultGameManagerTest {

    @BeforeAll
    public static void beforeAll() {
        TitForTatStrategyFactory.instance().getScoreCounter().reset();
        AlwaysDefectStrategyFactory.instance().getScoreCounter().reset();
        AlwaysCooperateStrategyFactory.instance().getScoreCounter().reset();
    }

    @Test
    public void test() {
        var gameManager = new DefaultGameManager();

        gameManager.play(
                TitForTatStrategyFactory::instance,
                AlwaysDefectStrategyFactory::instance
        );

        assertEquals(199, TitForTatStrategyFactory.instance().getScoreCounter().getPoints());
        assertEquals(204, AlwaysDefectStrategyFactory.instance().getScoreCounter().getPoints());

        TitForTatStrategyFactory.instance().getScoreCounter().reset();
        AlwaysDefectStrategyFactory.instance().getScoreCounter().reset();

        gameManager.play(
                TitForTatStrategyFactory::instance,
                AlwaysCooperateStrategyFactory::instance
        );

        assertEquals(600, TitForTatStrategyFactory.instance().getScoreCounter().getPoints());
        assertEquals(600, AlwaysCooperateStrategyFactory.instance().getScoreCounter().getPoints());
    }

    @AfterAll
    public static void afterAll() {
        TitForTatStrategyFactory.instance().getScoreCounter().reset();
        AlwaysDefectStrategyFactory.instance().getScoreCounter().reset();
        AlwaysCooperateStrategyFactory.instance().getScoreCounter().reset();
    }

}