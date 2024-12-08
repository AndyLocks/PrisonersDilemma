package com.locfox.prisoners_dilemma.game_manager;

/// This class contains static methods for managing {@link GameManager}
///
/// This class also contains some implementations.
public class GameManagers {
    /// @return the default game manager
    /// @see DefaultGameManager
    public static DefaultGameManager withDefaults() {
        return new DefaultGameManager();
    }

    public static NoiseDefaultGameManager defaultWithNoises() {
        return new NoiseDefaultGameManager();
    }

}
