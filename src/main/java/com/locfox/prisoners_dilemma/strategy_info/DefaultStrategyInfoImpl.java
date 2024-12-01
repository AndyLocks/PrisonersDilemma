package com.locfox.prisoners_dilemma.strategy_info;

public record DefaultStrategyInfoImpl(String name, String description) implements StrategyInfo {

    public static DefaultStrategyInfoImpl of(final String name, final String description) {
        return new DefaultStrategyInfoImpl(name, description);
    }

    @Override
    public String toString() {
        return String.format("Name [%s], description [%s]", name(), description());
    }

}
