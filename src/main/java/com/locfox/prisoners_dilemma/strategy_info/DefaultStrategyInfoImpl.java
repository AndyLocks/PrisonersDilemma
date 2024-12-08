package com.locfox.prisoners_dilemma.strategy_info;

import java.util.Objects;

public record DefaultStrategyInfoImpl(String name, String description) implements StrategyInfo {

    public static DefaultStrategyInfoImpl of(final String name, final String description) {
        return new DefaultStrategyInfoImpl(name, description);
    }

    @Override
    public String toString() {
        return String.format("Name [%s], description [%s]", name(), description());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DefaultStrategyInfoImpl that = (DefaultStrategyInfoImpl) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

}
