package org.the.killers.statistics;

import org.the.killers.simulations.Simulation;
import org.the.killers.settings.Settings;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

// Клас содержащий статистические переменные, методы по их выводу и по их обновлению.

public class Statistics {
    private static int bornCount = 0;
    public static final AtomicInteger dayBornCount = new AtomicInteger(0);
    private static int reproduceCount = 0;
    public static final AtomicInteger dayReproduceCount = new AtomicInteger(0);
    private static int diedCount = 0;
    public static final AtomicInteger dayDiedCount = new AtomicInteger(0);
    private static int eatenCount = 0;
    public static final AtomicInteger dayEatenCount = new AtomicInteger(0);
    private static int hungryDiedCount = 0;
    public static final AtomicInteger dayHungryDiedCount = new AtomicInteger(0);
    private static int eatenPlants = 0;
    public static final AtomicInteger dayEatenCaterpillar = new AtomicInteger(0);
    private static int eatenCaterpillar = 0;
    public static final AtomicInteger dayEatenPlants = new AtomicInteger(0);
    public static final Map<Settings.AnimalType, Integer> countsOfTypes = new EnumMap<>(Settings.AnimalType.class);
    private static int dayCountAnimal = 0;

    static {
        refreshCountOfTypes();
    }
    private Statistics(){}

    public static void refreshCountOfTypes() {
        for (Settings.AnimalType animalType : Settings.AnimalType.values()) {
            countsOfTypes.put(animalType, 0);
        }
    }

    public static void refreshDayStat() {
        dayEatenCount.set(0);
        dayDiedCount.set(0);
        dayEatenPlants.set(0);
        dayReproduceCount.set(0);
        dayBornCount.set(0);
        dayEatenCaterpillar.set(0);
        dayCountAnimal = 0;
        dayHungryDiedCount.set(0);
    }

    public static void refreshAllStat() {
        bornCount = 0;
        reproduceCount = 0;
        diedCount = 0;
        eatenCount = 0;
        eatenPlants = 0;
        eatenCaterpillar = 0;
        hungryDiedCount = 0;
        refreshDayStat();
    }

    public static void actualizingAllStat() {
        bornCount += dayBornCount.get();
        reproduceCount += dayReproduceCount.get();
        diedCount += dayDiedCount.get();
        hungryDiedCount += dayHungryDiedCount.get();
        eatenCount += dayEatenCount.get();
        eatenPlants += dayEatenPlants.get();
        eatenCaterpillar += dayEatenCaterpillar.get();
    }

    public static void printDailyStat() {
        System.out.println("Статистика за день:");
        System.out.printf("Рождено: %d | ", dayBornCount.get());
        System.out.printf("Зачато: %d | ", dayReproduceCount.get());
        System.out.printf("Умерло: %d | ", dayDiedCount.get());
        System.out.printf("Умерло от голода: %d | ", dayHungryDiedCount.get());
        System.out.printf("Было съедено животных: %d | ", dayEatenCount.get());
        System.out.printf("Было съедено растений: %d | ", dayEatenPlants.get());
        System.out.printf("Было съедено %s: %d.%n", Settings.AnimalType.CATERPILLAR, dayEatenCaterpillar.get());
        System.out.printf("Всего на острове:%n");
        for (Settings.AnimalType type : countsOfTypes.keySet()) {
            System.out.printf("{%s:%d}", type, countsOfTypes.get(type));
            dayCountAnimal += countsOfTypes.get(type);
        }
        System.out.printf(" Общее количество: %d%n%n%n", dayCountAnimal);

    }

    public static void printTotalStat() {
        System.out.printf("Прошло: %d дней. Осталось: %d дней.%n" +
                "Общая статистика:%n", Simulation.getDayCounter().get(), (Settings.AGE_DURATION - Simulation.getDayCounter().get()));
        System.out.printf("Рождено: %d | ", bornCount);
        System.out.printf("Зачато: %d | ", reproduceCount);
        System.out.printf("Умерло: %d | ", diedCount);
        System.out.printf("Умерло от голода: %d | ", hungryDiedCount);
        System.out.printf("Было съедено животных: %d | ", eatenCount);
        System.out.printf("Было съедено растений: %d | ", eatenPlants);
        System.out.printf("Было съедено %s: %d.%n%n", Settings.AnimalType.CATERPILLAR, eatenCaterpillar);
    }
}
