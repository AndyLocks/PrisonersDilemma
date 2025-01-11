package com.locfox.prisoners_dilemma.score_counter.score_counter_creator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreCounterProvidersTest {

    @Test
    public void default_provider_test() {
        assertSame(DefaultScoreCounterProviderImpl.class, ScoreCounterProviders.withDefaults().getClass());
    }

}