package org.the.killers.statistics;

import org.the.killers.simulations.Simulation;
import org.the.killers.Settings.Settings;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Statistics {

    public static int bornCount = 0;
    public static AtomicInteger dayBornCount = new AtomicInteger(0);
    public static int reproduceCount = 0;
    public static AtomicInteger dayReproduceCount = new AtomicInteger(0);
    public static int diedCount = 0;
    public static AtomicInteger dayDiedCount = new AtomicInteger(0);
    public static int eatenCount = 0;
    public static AtomicInteger dayEatenCount = new AtomicInteger(0);
    public static int eatenPlants = 0;
    public static AtomicInteger dayEatenPlants = new AtomicInteger(0);
    public static Map<Settings.AnimalType, Integer> countsOfTypes = new EnumMap<>(Settings.AnimalType.class);

    static {
        refreshCountOfTypes();
    }

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
    }

    public static void refreshAllStat() {
        bornCount = 0;
        reproduceCount = 0;
        diedCount = 0;
        eatenCount = 0;
        eatenPlants = 0;
        refreshDayStat();
    }

    public static void actualizingAllStat() {
        bornCount += dayBornCount.get();
        reproduceCount += dayReproduceCount.get();
        diedCount += dayDiedCount.get();
        eatenCount += dayEatenCount.get();
        eatenPlants += dayEatenPlants.get();

    }

    public static void printDailyStat() {
        System.out.printf("День %d.%n" +
                "Статистика за день:%n", Simulation.getDayCounter().get());
        System.out.printf("Рождено: %d | ", dayBornCount.get());
        System.out.printf("Зачато: %d | ", dayReproduceCount.get());
        System.out.printf("Умерло: %d | ", dayDiedCount.get());
        System.out.printf("Умерло от голода: %d | ", dayDiedCount.get() - dayEatenCount.get());
        System.out.printf("Было съедено животных: %d | ", dayEatenCount.get());
        System.out.printf("Было съедено растений: %d.%n", dayEatenPlants.get());
        System.out.printf("Всего на острове:%n");
        for (Settings.AnimalType type : countsOfTypes.keySet()) {
            System.out.printf("{%s:%d}", type, countsOfTypes.get(type));
        }
        System.out.printf("%n%n");
    }

    public static void printTotalStat() {
        System.out.printf("Прошло: %d дней. Осталось: %d дней.%n" +
                "Общая статистика:%n", Simulation.getDayCounter().get(), (Settings.AGE_DURATION - Simulation.getDayCounter().get()));
        System.out.printf("Рождено: %d | ", bornCount);
        System.out.printf("Зачато: %d | ", reproduceCount);
        System.out.printf("Умерло: %d | ", diedCount);
        System.out.printf("Было съедено животных: %d | ", eatenCount);
        System.out.printf("Было съедено растений: %d.%n%n", eatenPlants);
    }
}
