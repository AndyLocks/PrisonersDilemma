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

The `GameManager` is responsible for the game rules and manages the flow of the game.

---

You can also implement the `GameManager` interface to create your own game rules.
You can also look in the `GameManagers` class. It can have several implementations including the standard one.

Later, this game manager can be used like this:

```java
var factories = GameManagers.withDefaults().play(
                 () -> StrategyBuilder.builder()
                         .initialStrategy(() -> true)
                         .cooperates(opponentsPreviousAction -> true)
                         .info(StrategyInfo.withDefaults("Always cooperate", "Always cooperates"))
                         .build(),
                 () -> StrategyBuilder.builder()
                         .initialStrategy(() -> false)
                         .cooperates(opponentsPreviousAction -> false)
                         .info(StrategyInfo.withDefaults("Always defect", "Never cooperates"))
                         .build(),
                 () -> StrategyBuilder.builder()
                         .initialStrategy(() -> true)
                         .cooperates(opponentsPreviousAction -> opponentsPreviousAction)
                         .info(StrategyInfo.withDefaults("Tit for tat", "Returns exactly the opponent's previous answer"))
                         .build());
```

You can also use existing strategies instead of using `StrategyBuilder`:

```java
var factories = GameManagers.withDefaults().play(
        TitForTatStrategyFactory::instance,
        AlwaysDefectStrategyFactory::instance);
```

Later, the results can be output to the console:

```java
strategyFactories.stream()
        .map(Supplier::get)
        .flatMap(s -> s.getScoreCounter().asStream())
        .sorted(Comparator.<ScoreCounter<? extends StrategyInfo>>comparingInt(ScoreCounter::getPoints).reversed())
        .forEach(scoreCounter -> {
            System.out.printf("%s (%s): %d%n", scoreCounter.getStrategyInfo().name(), scoreCounter.getStrategyInfo().description(), scoreCounter.getPoints());
        });
```

---

It is very important that the game manager creates a new strategy before each new round using the provided factories.
Each strategy can have a state in which it remembers the opponent's behaviour during the round. In order to reset this state, it is important to create a new strategy each time.
For this reason, each strategy of a certain type must have its own `ScoreCounter`. For example, all `RandomStrategy` strategies always have the same `ScoreCounter` object.

## StrategyBuilder

You can also create anonymous strategies using `StrategyBuilder`:

```java
var titForTat = StrategyBuilder.builder()
        .initialStrategy(() -> true)
        .cooperates(opponentsPreviousAction -> opponentsPreviousAction)
        .info(StrategyInfo.withDefaults("Tit for tat", "Returns exactly the opponent's previous answer"))
        .build();
```

There is also a method in the `Strategy` interface to get the `StrategyBuilder`:

```java
var titForTat = Strategy.builder()
        .initialStrategy(() -> true)
        .cooperates(opponentsPreviousAction -> opponentsPreviousAction)
        .info(StrategyInfo.withDefaults("Tit for tat", "Returns exactly the opponent's previous answer"))
        .build();
```

`StrategyBuilder` also has the ability to change `ScoreCounterProvider`:

```java
var titForTatStrategy = StrategyBuilder.builder()
                .info(StrategyInfo.withDefaults("Tit for tat", "made with builder"))
                .ScoreCounterProvider(ScoreCounters::withDefaults)
                .initialStrategy(() -> true)
                .cooperates(opponentsPreviousAction -> opponentsPreviousAction)
                .build();
```

### ScoreCounterProvider

Here, in the `scoureCounterCreator` method, you can pass one of the implementations. They can be found in the `ScoreCounterProviders` class
or described using a lambda expression, since this class is essentially a function.

`ScoreCounterProvider` is used to convert from information (`StrategyInfo`) to `ScoreCounter`,
which in turn counts the scores. Each type of strategy should have the same counter.
`ScoreCounterProvider` essentially remembers which `ScoreCounter` belongs to which `StrategyInfo` and gives it out or creates it.

In an ideal implementation, `ScoreCounterProvider` should return the same `ScoreCounter` for each `StrategyInfo` compared using the `Object#equals(Object)` method.

## Factories

`Factories` contains all existing standard factories.

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

factories.add(() -> Strategy.builder()
                .initialStrategy(() -> true)
                .cooperates(opponentsPreviousAction -> true)
                .info(StrategyInfo.withDefaults("Tit for tat", "made with builder"))
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

---

```java
var factories = Factories.all().stream()
        .filter(s -> s.getClass() == TitForTatStrategyFactory.class ||
                s.getClass() == AlwaysDefectStrategyFactory.class)
        .toList();

GameManagers.withDefaults().play(factories);

factories.stream()
        .map(Supplier::get)
        .flatMap(s -> s.getScoreCounter().asStream())
        .sorted(Comparator.<ScoreCounter<? extends StrategyInfo>>comparingInt(ScoreCounter::getPoints).reversed())
        .forEach(scoreCounter -> {
            System.out.printf("%s (%s): %d%n", scoreCounter.getStrategyInfo().name(), scoreCounter.getStrategyInfo().description(), scoreCounter.getPoints());
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
