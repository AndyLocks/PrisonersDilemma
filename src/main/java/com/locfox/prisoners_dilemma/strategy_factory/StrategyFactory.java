package com.locfox.prisoners_dilemma.strategy_factory;

import com.locfox.prisoners_dilemma.strategy.Strategy;

import java.util.function.Supplier;

/// Returns a new specific {@link Strategy}, that may be hard to configure.
/// This is important for objects that can have state.
///
/// After one round, it is recommended to create a new object for the next one.
/// The factory will help organize these objects.
///
/// ---
///
/// Later, all factories are assembled using the Spring Framework and strategies for the game are created using them.
///
/// ---
///
/// # Implementation
///
/// **Also add {@link org.springframework.stereotype.Component} annotation.**
///
/// @param <T> a strategy to return
public interface StrategyFactory<T extends Strategy> extends Supplier<T> {
    @Deprecated(forRemoval = true)
    static StrategyFactory<Strategy> instance(Supplier<Strategy> supplier) {
        return new StrategyFactory<Strategy>() {
            @Override
            public Strategy get() {
                return supplier.get();
            }
        };
    }
    /// Returns a new {@link Strategy} object ready for use.
    ///
    /// @return a {@link Strategy} object
    T get();
}
