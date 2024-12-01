package com.locfox.prisoners_dilemma.strategy_factory.implementations;

import com.locfox.prisoners_dilemma.strategy.implementations.TitForTatStrategy;
import com.locfox.prisoners_dilemma.strategy_factory.StrategyFactory;
import org.springframework.stereotype.Component;

@Component
public class TitForTatStrategyFactory implements StrategyFactory<TitForTatStrategy> {

    public static TitForTatStrategy instance() {
        return new TitForTatStrategy();
    }

    @Override
    public TitForTatStrategy get() {
        return TitForTatStrategyFactory.instance();
    }

}
