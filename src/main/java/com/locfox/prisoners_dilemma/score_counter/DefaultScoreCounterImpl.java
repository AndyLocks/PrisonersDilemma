package com.locfox.prisoners_dilemma.score_counter;

import com.locfox.prisoners_dilemma.strategy_info.StrategyInfo;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/// Standard score counter
///
/// # Points
///
/// - Both cooperate: **3**
/// - Both betray: **1**
/// - I was betrayed while I was cooperating: **0**
/// - I betrayed while my opponent was cooperating: **5**
public class DefaultScoreCounterImpl<T extends StrategyInfo> implements ScoreCounter<T> {

    private final T strategyInfo;
    private final AtomicInteger points = new AtomicInteger(0);
    private static final int BOTH_COOPERATE_POINTS = 3;
    private static final int BOTH_BETRAY_POINTS = 1;
    private static final int I_WAS_BETRAYED_POINTS = 0;
    private static final int I_BETRAYED_POINTS = 5;

    public DefaultScoreCounterImpl(T strategyInfo) {
        this.strategyInfo = strategyInfo;
    }

    /// @param strategyInfo information about the strategy
    /// @return a new {@link DefaultScoreCounterImpl}
    public static <T extends StrategyInfo> DefaultScoreCounterImpl<T> of(T strategyInfo) {
        return new DefaultScoreCounterImpl<T>(strategyInfo);
    }

    @Override
    public void bothCooperate() {
        points.addAndGet(BOTH_COOPERATE_POINTS);
    }

    @Override
    public void bothBetrayed() {
        points.addAndGet(BOTH_BETRAY_POINTS);
    }

    @Override
    public void iWasBetrayed() {
        points.addAndGet(I_WAS_BETRAYED_POINTS);
    }

    @Override
    public void iBetrayed() {
        points.addAndGet(I_BETRAYED_POINTS);
    }

    @Override
    public T getStrategyInfo() {
        return this.strategyInfo;
    }

    @Override
    public void reset() {
        points.set(0);
    }

    @Override
    public int getPoints() {
        return points.get();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DefaultScoreCounterImpl<?> that = (DefaultScoreCounterImpl<?>) o;
        return Objects.equals(strategyInfo, that.strategyInfo) && points.get() == that.points.get();
    }

    @Override
    public int hashCode() {
        return Objects.hash(strategyInfo, points);
    }

}
