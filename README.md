**Documentation is also available in Russian!!!**: Check `ru/README.md`

---

[![gpl](https://img.shields.io/badge/gpl-fab387?style=for-the-badge&label=license&labelColor=1e1e2e)](https://github.com/AndyLocks/PrisonersDilemma/blob/master/LICENSE)

This is a realisation of the popular prisoner's dilemma.

# Brief gist

**A prisoner's dilemma** is a concept from game theory where two players must choose to cooperate or betray without knowing the other's choice.
If both co-operate, they get a moderate benefit; if one betrays and the other co-operates, the betrayer gets the maximum and the other the minimum; if both betray, the benefit is minimal for both.

Wikipedia also has a [page dedicated to this dilemma](https://ru.wikipedia.org/wiki/%D0%94%D0%B8%D0%BB%D0%B5%D0%BC%D0%BC%D0%B0_%D0%B7%D0%B0%D0%BA%D0%BB%D1%8E%D1%87%D1%91%D0%BD%D0%BD%D0%BE%D0%B3%D0%BE).

# Adding your strategy

There are only a small number of steps to add your strategy:

- Implement the `Strategy` interface in the `src/main/java/com/locfox/prisoners_dilemma/strategy/implementations` folder
- Implement the `StrategyFactory` in the `src/main/java/com/locfox/prisoners_dilemma/strategy_factory/implementations` folder
- Add the `@Component` annotation to `StrategyFacotry`. This is needed for Spring to find this factory and use it for the game.

It is important to follow the instructions, which are also described in the documentation:

- It is important that `Strategy` also overrides the `Object.hashCode()` and `Object.equals()` methods
- It is important that there is always **only one** `ScoreCounter` for each strategy that the factory returns. This is necessary for correct scoring. You can just make a static field in the strategy class:

```java
private static final ScoreCounter<DefaultStrategyInfoImpl> scoreCounter =
        ScoreCounters.withDefaults(DefaultStrategyInfoImpl.of(NAME, DESCRIPTION));
```

# Usage

## GameManager

You can also implement the `GameManager` interface to create your own game rules.
You can also look in the `GameManagers` class. It can have several implementations including the standard one.

Later, this game manager can be used like this:

```java
var alwaysCooperateScoreCounter = ScoreCounters.withDefaults(
        StrategyInfo.withDefaults("Always cooperate", "Always cooperates"));
var friedmanScoreCounter = ScoreCounters.withDefaults(
        StrategyInfo.withDefaults("Friedman", "Never cooperates"));
var titForTatScoreCounter = ScoreCounters.withDefaults(
        StrategyInfo.withDefaults("Tit for tat", "Returns exactly the opponent's previous answer"));

var factories = GameManagers.withDefaults().play(
        () -> StrategyBuilder.builder()
                .initialStrategy(() -> true)
                .cooperates(opponentsPreviousAction -> true)
                .scoreCounter(alwaysCooperateScoreCounter)
                .build(),
        () -> StrategyBuilder.builder()
                .initialStrategy(() -> false)
                .cooperates(opponentsPreviousAction -> false)
                .scoreCounter(friedmanScoreCounter)
                .build(),
        () -> StrategyBuilder.builder()
                .initialStrategy(() -> true)
                .cooperates(opponentsPreviousAction -> opponentsPreviousAction)
                .scoreCounter(titForTatScoreCounter)
                .build());
```

It is very important that the game manager creates a new strategy before each new round using the provided factories.
Each strategy can have a state in which it remembers the opponent's behaviour during the round. In order to reset this state, it is important to create a new strategy each time.
For this reason, each strategy of a certain type must have its own `ScoreCounter`. For example, all `RandomStrategy` strategies always have the same `ScoreCounter` object.

## StrategyBuilder

You can also create anonymous strategies using `StrategyBuilder`:

```java
var strategy = StrategyBuilder.builder()
        .initialStrategy(() -> true)
        .cooperates(opponentsPreviousAction -> true)
        .scoreCounter(someScoreCounter)
        .build();
```

For such an implementation you would need to have exactly one `ScoreCounter` for certain strategies:

```java
var someScoreCounter = ScoreCounters.withDefaults(
        StrategyInfo.withDefaults("Some Strategy", "Some description"));
```

You can also make your own variant of `ScoreCounter`.

## Factories

There is an enum `Factories` which contains all existing standard factories.
This enum also has a method `Factories.all()` which returns all factories as a list and can be conveniently handled:

```java
GameManagers.withDefaults().play(Factories.all());

Factories.all().stream()
    .map(Supplier::get)
    .flatMap(s -> s.getScoreCounter().asStream())
    .sorted(Comparator.comparingInt(ScoreCounter::getPoints))
    .forEach(scoreCounter -> {
        System.out.println(
            String.format("%s (%s): %d", scoreCounter.getStrategyInfo().name(), scoreCounter.getStrategyInfo().description(), scoreCounter.getPoints())
        );
    });
```

**It is very important** to reset the scores before each new game if you want the new game to start from scratch.
Since some strategies can have a state and memorise the game process, they also contain the same `ScoreCounter`.

```java
Factories.all().stream()
    .map(Supplier::get)
    .forEach(strategy -> strategy.getScoreCounter().reset());
```

This list can also be modernised and you can add your own strategies:

```java
var factories = Factories.all();

factories.add(
        () -> StrategyBuilder.builder()
                .initialStrategy(() -> true)
                .cooperates(opponentsPreviousAction -> true)
                .scoreCounter(someScoreCounter)
                .build());

GameManagers.withDefaults().play(factories);

factories.stream()
        .map(Supplier::get)
        .flatMap(s -> s.getScoreCounter().asStream())
        .sorted(Comparator.comparingInt(ScoreCounter::getPoints))
        .forEach(scoreCounter -> {
            System.out.println(
                    String.format("%s (%s): %d", scoreCounter.getStrategyInfo().name(), scoreCounter.getStrategyInfo().description(), scoreCounter.getPoints())
            );
        });
```

## ScoreCounter

There is a `ScoreCounters` class where you can find standard implementations of `ScoreCounter`.

For `ScoreCounter` you need `StrategyInfo`. Which in turn contains a description of the strategy.
Here is a simple example of creating `ScoreCounter` in a class:

```java
private static final String NAME = "Always cooperate";
private static final String DESCRIPTION = "Always cooperates";
private static final ScoreCounter<DefaultStrategyInfoImpl> scoreCounter =
        ScoreCounters.withDefaults(DefaultStrategyInfoImpl.of(NAME, DESCRIPTION));
```

The `StrategyInfo` interface also has a `withDefaults(String name, String description)` method. It can also be used to create a `ScoreCounter`:

```java
private static final String NAME = "Always cooperate";
private static final String DESCRIPTION = "Always cooperates";
private static final ScoreCounter<? extends StrategyInfo> scoreCounter =
        ScoreCounters.withDefaults(StrategyInfo.withDefaults(NAME, DESCRIPTION));
```
