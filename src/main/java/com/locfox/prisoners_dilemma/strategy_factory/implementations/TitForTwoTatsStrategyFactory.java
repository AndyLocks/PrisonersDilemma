package com.locfox.prisoners_dilemma.strategy_factory.implementations;

import com.locfox.prisoners_dilemma.strategy.implementations.TitForTwoTatsStrategy;
import com.locfox.prisoners_dilemma.strategy_factory.StrategyFactory;
import org.springframework.stereotype.Component;

@Component
public class TitForTwoTatsStrategyFactory implements StrategyFactory<TitForTwoTatsStrategy> {
    public static TitForTwoTatsStrategy instance() {
        return new TitForTwoTatsStrategy();
    }

    @Override
    public TitForTwoTatsStrategy get() {
        return TitForTwoTatsStrategyFactory.instance();
    }
}
