package com.locfox.prisoners_dilemma.strategy_factory.implementations;

import com.locfox.prisoners_dilemma.strategy.implementations.RandomStrategy;
import com.locfox.prisoners_dilemma.strategy_factory.StrategyFactory;
import org.springframework.stereotype.Component;

@Component
public class RandomStrategyFactory implements StrategyFactory<RandomStrategy> {
    public static RandomStrategy instance() {
        return new RandomStrategy();
    }

    @Override
    public RandomStrategy get() {
        return RandomStrategyFactory.instance();
    }
}
