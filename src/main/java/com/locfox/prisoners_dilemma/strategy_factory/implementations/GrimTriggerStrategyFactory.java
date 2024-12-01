package com.locfox.prisoners_dilemma.strategy_factory.implementations;

import com.locfox.prisoners_dilemma.strategy.implementations.GrimTriggerStrategy;
import com.locfox.prisoners_dilemma.strategy_factory.StrategyFactory;
import org.springframework.stereotype.Component;

@Component
public class GrimTriggerStrategyFactory implements StrategyFactory<GrimTriggerStrategy> {

    public static GrimTriggerStrategy instance() {
        return new GrimTriggerStrategy();
    }

    @Override
    public GrimTriggerStrategy get() {
        return GrimTriggerStrategyFactory.instance();
    }
}
