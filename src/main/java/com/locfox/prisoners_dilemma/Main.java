package com.locfox.prisoners_dilemma;

import com.locfox.prisoners_dilemma.game_manager.GameManagers;
import com.locfox.prisoners_dilemma.score_counter.ScoreCounter;
import com.locfox.prisoners_dilemma.strategy.Strategy;
import com.locfox.prisoners_dilemma.strategy_factory.StrategyFactory;
import com.locfox.prisoners_dilemma.strategy_info.StrategyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

@Component
public class Main implements CommandLineRunner {

    @Autowired
    private List<StrategyFactory<? extends Strategy>> strategyFactories;

    @Override
    public void run(String... args) throws Exception {
        GameManagers.withDefaults().play(strategyFactories).stream()
                .map(Supplier::get)
                .flatMap(s -> s.getScoreCounter().asStream())
                .sorted(Comparator.<ScoreCounter<? extends StrategyInfo>>comparingInt(ScoreCounter::getPoints).reversed())
                .forEach(scoreCounter -> {
                    System.out.printf("%s (%s): %d%n", scoreCounter.getStrategyInfo().name(), scoreCounter.getStrategyInfo().description(), scoreCounter.getPoints());
                });
    }

}
