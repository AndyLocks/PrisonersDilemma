package com.locfox.prisoners_dilemma.score_counter.score_counter_creator;

import com.locfox.prisoners_dilemma.score_counter.ScoreCounter;
import com.locfox.prisoners_dilemma.score_counter.ScoreCounters;
import com.locfox.prisoners_dilemma.strategy_info.StrategyInfo;

import java.util.HashMap;
import java.util.Map;

/// Default implementation of [ScoreCounterCreator].
///
/// It saves all [StrategyInfo] in [HashMap] (`StrategyInfo` is the key in this case) and returns [ScoreCounter] by key.
/// If key does not exist -> puts a new key and value
public class DefaultScoreCounterCreatorImpl implements ScoreCounterCreator {

    private static final Map<StrategyInfo, ScoreCounter<? extends StrategyInfo>> scoreCounterHashMap = new HashMap<>();

    @Override
    public ScoreCounter<? extends StrategyInfo> apply(StrategyInfo strategyInfo) {
        if (!scoreCounterHashMap.containsKey(strategyInfo))
            scoreCounterHashMap.put(strategyInfo, ScoreCounters.withDefaults(strategyInfo));

        return scoreCounterHashMap.get(strategyInfo);
    }

}
