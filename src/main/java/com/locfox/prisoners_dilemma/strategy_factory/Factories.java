package com.locfox.prisoners_dilemma.strategy_factory;

import com.locfox.prisoners_dilemma.strategy.Strategy;
import com.locfox.prisoners_dilemma.strategy_factory.implementations.*;

import java.util.ArrayList;
import java.util.List;

public enum Factories {

    ALWAYS_COOPERATE_STRATEGY_FACTORY(new AlwaysCooperateStrategyFactory()),
    ALWAYS_DEFECT_STRATEGY_FACTORY(new AlwaysDefectStrategyFactory()),
    FRIEDMAN_STRATEGY_FACTORY(new FriedmanStrategyFactory()),
    GENEROUS_TIT_FOR_STRATEGY_FACTORY(new GenerousTitForTatStrategyFactory()),
    GRIM_TRIGGER_STRATEGY_FACTORY(new GrimTriggerStrategyFactory()),
    RANDOM_STRATEGY_FACTORY(new RandomStrategyFactory()),
    TIT_FOR_TAT_STRATEGY_FACTORY(new TitForTatStrategyFactory()),
    TIT_FOR_TWO_TATS_STRATEGY_FACTORY(new TitForTwoTatsStrategyFactory());

    private final StrategyFactory<? extends Strategy> strategyFactory;

    Factories(StrategyFactory<? extends Strategy> strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    private static final List<? extends StrategyFactory<? extends Strategy>> ALL_STANDARD_STRATEGY_FACTORIES = List.of(
            new AlwaysCooperateStrategyFactory(),
            new AlwaysDefectStrategyFactory(),
            new FriedmanStrategyFactory(),
            new GenerousTitForTatStrategyFactory(),
            new GrimTriggerStrategyFactory(),
            new RandomStrategyFactory(),
            new TitForTatStrategyFactory(),
            new TitForTwoTatsStrategyFactory()
    );

    /// @return a copy of the list of all standard strategy implementations
    public static List<? extends StrategyFactory<? extends Strategy>> all() {
        return new ArrayList<>(ALL_STANDARD_STRATEGY_FACTORIES);
    }

    public StrategyFactory<? extends Strategy> getStrategyFactory() {
        return strategyFactory;
    }

}
