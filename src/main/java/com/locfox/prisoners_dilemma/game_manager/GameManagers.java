package com.locfox.prisoners_dilemma.game_manager;

import com.locfox.prisoners_dilemma.score_counter.ScoreCounter;

/// This class contains static methods for managing {@link GameManager}
///
/// This class also contains some implementations.
public class GameManagers {
    /// @return the default game manager
    /// @see DefaultGameManager
    public static DefaultGameManager withDefaults() {
        return new DefaultGameManager();
    }

}
