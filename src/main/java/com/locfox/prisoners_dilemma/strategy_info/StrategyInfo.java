package com.locfox.prisoners_dilemma.strategy_info;

/// Strategy Information
public interface StrategyInfo {
    /// @return name of the strategy
    String name();

    /// The description should contain a description of how the strategy works.
    ///
    /// @return description of the strategy
    String description();

    boolean equals(Object obj);
    int hashCode();

    /// Returns an implementation of an object containing strategy information.
    /// This implementation is standard for the entire application.
    ///
    /// @param name        a name of the strategy
    /// @param description a description of the strategy.
    ///                                       The description should contain a description of how the strategy works
    /// @return a default implementation
    static StrategyInfo withDefaults(String name, String description) {
        return DefaultStrategyInfoImpl.of(name, description);
    }
}
