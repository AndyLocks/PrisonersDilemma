package com.locfox.prisoners_dilemma.game_manager;

import com.locfox.prisoners_dilemma.exceptions.TooFewStrategies;
import com.locfox.prisoners_dilemma.strategy.Strategy;
import com.locfox.prisoners_dilemma.strategy_factory.StrategyFactory;

import java.util.Collection;
import java.util.List;
import java.util.Random;

/// Conducts 200 rounds.
///
/// In each round, strategies do not compete with themselves.
///
/// There is a 10 percent chance that cooperation will be seen as betrayal.
public class NoiseDefaultGameManager implements GameManager {

    private static final int ITERATIONS = 200;
    /// Chance in percent
    private static final int chance = 10;
    private final Random random = new Random();

    @Override
    public Collection<? extends StrategyFactory<? extends Strategy>> play(final Collection<? extends StrategyFactory<? extends Strategy>> factories) {
        if (factories.size() < 2)
            throw new TooFewStrategies("There must be at least two strategies for the game.");

        var factoryList = List.copyOf(factories);

        for (int i = 0; i < factoryList.size(); i++) {
            for (int j = i + 1; j < factoryList.size(); j++) {
                play(factoryList.get(i).get(), factoryList.get(j).get());
            }
        }

        return factories;
    }

    private void play(Strategy s1, Strategy s2) {
        var firstStrategyInitialCooperatesStrategy = s1.initialCooperatesStrategy();
        var secondStrategyInitialCooperatesStrategy = s2.initialCooperatesStrategy();

        if (firstStrategyInitialCooperatesStrategy) firstStrategyInitialCooperatesStrategy = random.nextInt(100) > chance;
        if (secondStrategyInitialCooperatesStrategy) secondStrategyInitialCooperatesStrategy = random.nextInt(100) > chance;

        countPoints(s1, s2, firstStrategyInitialCooperatesStrategy, secondStrategyInitialCooperatesStrategy);

        var firstStrategyCooperates = s1.cooperates(secondStrategyInitialCooperatesStrategy);
        var secondStrategyCooperates = s2.cooperates(firstStrategyInitialCooperatesStrategy);

        if (firstStrategyCooperates) firstStrategyCooperates = random.nextInt(100) > chance;
        if (secondStrategyCooperates) secondStrategyCooperates = random.nextInt(100) > chance;


        countPoints(s1, s2, firstStrategyCooperates, secondStrategyCooperates);

        for (int i = 2; i < ITERATIONS; i++) {
            firstStrategyCooperates = s1.cooperates(secondStrategyCooperates);
            secondStrategyCooperates = s2.cooperates(firstStrategyCooperates);

            if (firstStrategyCooperates) firstStrategyCooperates = random.nextInt(100) > chance;
            if (secondStrategyCooperates) secondStrategyCooperates = random.nextInt(100) > chance;

            countPoints(s1, s2, firstStrategyCooperates, secondStrategyCooperates);
        }
    }

    private void countPoints(Strategy s1, Strategy s2, boolean firstStrategyCooperates, boolean secondStrategyCooperates) {
        if (firstStrategyCooperates && secondStrategyCooperates) {
            s1.getScoreCounter().bothCooperate();
            s2.getScoreCounter().bothCooperate();
        } else if (firstStrategyCooperates) {
            s1.getScoreCounter().iWasBetrayed();
            s2.getScoreCounter().iBetrayed();
        } else if (secondStrategyCooperates) {
            s1.getScoreCounter().iBetrayed();
            s2.getScoreCounter().iWasBetrayed();
        } else {
            s1.getScoreCounter().bothBetrayed();
            s2.getScoreCounter().bothBetrayed();
        }
    }

}
