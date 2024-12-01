package com.locfox.prisoners_dilemma.strategy_factory.implementations;

import com.locfox.prisoners_dilemma.strategy.implementations.FriedmanStrategy;
import com.locfox.prisoners_dilemma.strategy_factory.StrategyFactory;
import org.springframework.stereotype.Component;

@Component
public class FriedmanStrategyFactory implements StrategyFactory<FriedmanStrategy> {

    public static FriedmanStrategy instance() {
        return new FriedmanStrategy();
    }

    @Override
    public FriedmanStrategy get() {
        return FriedmanStrategyFactory.instance();
    }
}
