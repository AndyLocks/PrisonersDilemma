[![gpl](https://img.shields.io/badge/gpl-fab387?style=for-the-badge&label=license&labelColor=1e1e2e)](https://github.com/AndyLocks/PrisonersDilemma/blob/master/LICENSE)

Это реализация популярной дилеммы заключенного.

# Краткая суть

**Дилемма заключённого** — это концепт из теории игр, где два игрока должны выбрать сотрудничество или предательство, не зная выбора другого.
Если оба сотрудничают, они получают умеренную выгоду; если один предаёт, а другой сотрудничает, предатель получает максимум, а второй — минимум; если оба предают, выгода минимальна для обоих.

На википедии также есть [страница посвященная этой дилемме](https://ru.wikipedia.org/wiki/%D0%94%D0%B8%D0%BB%D0%B5%D0%BC%D0%BC%D0%B0_%D0%B7%D0%B0%D0%BA%D0%BB%D1%8E%D1%87%D1%91%D0%BD%D0%BD%D0%BE%D0%B3%D0%BE).

# Добавление своей стратегии

Для добавления своей стратегии достаточно сделать небольшое количество шагов:

 - Реализовать интерфейс `Strategy` в папке `src/main/java/com/locfox/prisoners_dilemma/strategy/implementations`
 - Реализовать фабрику `StrategyFactory` в папке `src/main/java/com/locfox/prisoners_dilemma/strategy_factory/implementations`
 - Добавить аннотацию `@Component` к `StrategyFacotry`. Это нужно для Spring, чтобы он нашел эту фабрику и воспользовался ею для игры.

Важно следовать инструкциям, которые также описаны в документации:

 - Важно чтобы `Strategy` также переопределял методы `Object.hashCode()` и `Object.equals()`
 - Важно чтобы для каждой стратегии которую возвращает фабрика был всегда **только один** `ScoreCounter`. Это необходимо для правильного подсчета очков. Можно просто сделать статическое поле в классе стратегии:

```java
private static final ScoreCounter<DefaultStrategyInfoImpl> scoreCounter =
        ScoreCounters.withDefaults(DefaultStrategyInfoImpl.of(NAME, DESCRIPTION));
```

# Использование

## GameManager

`GameManager` отвечает за игровые правила и управляет ходом игры.

---

Также можно имплементировать интерфейс `GameManager` для создания своих игровых правил.
Также можно посмотреть в класс `GameManagers`. В нем могут быть несколько реализаций включая стандартную.

Позже этот игровой менеджер можно будет использовать так:

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

Также вместо использования `StrategyBuilder` можно использовать уже существующие стратегии:

```java
var factories = GameManagers.withDefaults().play(
        TitForTatStrategyFactory::instance,
        AlwaysDefectStrategyFactory::instance);
```

Позже результаты можно вывести в консоль:

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

Очень важно, чтобы игровой менеджер перед каждым новым раундом создавал новую стратегию с помощью предоставленных фабрик.
Каждая стратегия может иметь состояние, в котором запоминает поведение оппонента во время раунда. Для того, что-бы это состояние обнулялось, важно создавать каждый раз новую стратегию.
По этой причине у каждой стратегии определенного типа должен быть свой `ScoreCounter`. Например у всех стратегий `RandomStrategy` всегда один и тот же объект `ScoreCounter`.

## StrategyBuilder

Вы также можете создавать анонимные стратегии с помощью `StrategyBuilder`:

```java
var strategy = StrategyBuilder.builder()
        .initialStrategy(() -> true)
        .cooperates(opponentsPreviousAction -> true)
        .scoreCounter(someScoreCounter)
        .build();
```

Для такой реализации вам понадобится иметь ровно один `ScoreCounter` для определенных стратегий:

```java
var someScoreCounter = ScoreCounters.withDefaults(
        StrategyInfo.withDefaults("Some Strategy", "Some description"));
```

Также в интерфейсе `Strategy` есть метод для получения `StrategyBuilder`:

```java
var strategy = Strategy.builder()
        .initialStrategy(() -> true)
        .cooperates(opponentsPreviousAction -> true)
        .scoreCounter(someScoreCounter)
        .build();
```

Вы также можете сделать свой вариант `ScoreCounter`. 

## Factories

Есть enum `Factories` который содержит все существующие стандартные фабрики. В этом enum также есть метод `Factories.all()` который возвращает все фабрики списком и с ними можно удобно работать:

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

**Очень важно** перед каждой новой игрой обнулять очки, если вы хотите чтобы новая игра начиналась с чистого листа.
Так как некоторые стратегии могут иметь состояние и запоминать процесс игры, они также содержат один и тот же `ScoreCounter`

```java
Factories.all().stream()
    .map(Supplier::get)
    .forEach(strategy -> strategy.getScoreCounter().reset());
```

Этот список также можно модернизировать и добавлять свои стратегии:

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

Существует класс `ScoreCounters` где можно найти стандартные реализации `ScoreCounter`.

Для `ScoreCounter` нужен `StrategyInfo`. Который в свою очередь содержит описание стратегии.
Вот простой пример создания `ScoreCounter` в классе:

```java
private static final String NAME = "Always cooperate";
private static final String DESCRIPTION = "Always cooperates";
private static final ScoreCounter<DefaultStrategyInfoImpl> scoreCounter =
        ScoreCounters.withDefaults(DefaultStrategyInfoImpl.of(NAME, DESCRIPTION));
```

В интерфейсе `StrategyInfo` также есть метод `withDefaults(String name, String description)`. Его также можно использовать для создания `ScoreCounter`:

```java
private static final String NAME = "Always cooperate";
private static final String DESCRIPTION = "Always cooperates";
private static final ScoreCounter<? extends StrategyInfo> scoreCounter =
        ScoreCounters.withDefaults(StrategyInfo.withDefaults(NAME, DESCRIPTION));
```
