package com.locfox.prisoners_dilemma.strategy_factory.implementations;

import com.locfox.prisoners_dilemma.strategy.implementations.AlwaysDefectStrategy;
import com.locfox.prisoners_dilemma.strategy_factory.StrategyFactory;
import org.springframework.stereotype.Component;

@Component
public class AlwaysDefectStrategyFactory implements StrategyFactory<AlwaysDefectStrategy> {
    public static AlwaysDefectStrategy instance() {
        return new AlwaysDefectStrategy();
    }

    @Override
    public AlwaysDefectStrategy get() {
        return AlwaysDefectStrategyFactory.instance();
    }
}