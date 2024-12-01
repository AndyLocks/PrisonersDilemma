package com.locfox.prisoners_dilemma.strategy_factory.factory_creator;

@FunctionalInterface
public interface CooperationStrategy {
    boolean cooperates(boolean opponentsPreviousAction);

    default boolean betray(boolean opponentsPreviousAction) {
        return !cooperates(opponentsPreviousAction);
    }
}
