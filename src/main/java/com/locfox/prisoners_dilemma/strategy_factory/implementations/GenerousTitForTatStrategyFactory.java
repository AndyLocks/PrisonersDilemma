package com.locfox.prisoners_dilemma.strategy_factory.implementations;

import com.locfox.prisoners_dilemma.strategy.implementations.GenerousTitForTatStrategy;
import com.locfox.prisoners_dilemma.strategy_factory.StrategyFactory;
import org.springframework.stereotype.Component;

@Component
public class GenerousTitForTatStrategyFactory implements StrategyFactory<GenerousTitForTatStrategy> {
    public static GenerousTitForTatStrategy instance() {
        return new GenerousTitForTatStrategy();
    }

    @Override
    public GenerousTitForTatStrategy get() {
        return GenerousTitForTatStrategyFactory.instance();
    }
}
