package com.locfox.prisoners_dilemma.strategy_factory.implementations;

import com.locfox.prisoners_dilemma.strategy.implementations.AlwaysCooperateStrategy;
import com.locfox.prisoners_dilemma.strategy_factory.StrategyFactory;
import org.springframework.stereotype.Component;

@Component
public class AlwaysCooperateStrategyFactory implements StrategyFactory<AlwaysCooperateStrategy> {

    public static AlwaysCooperateStrategy instance() {
        return new AlwaysCooperateStrategy();
    }

    @Override
    public AlwaysCooperateStrategy get() {
        return AlwaysCooperateStrategyFactory.instance();
    }

}
