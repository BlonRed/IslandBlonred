package org.the.killers.simulations;

import org.the.killers.model.Island;
import org.the.killers.statistics.Statistics;
import org.the.killers.settings.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

// Класс отвечающий за симуляция мира.
// Создается пулл потоков. Собирается список (tasks) задач по ячейкам. Симуляциия длится Settings.AGE_DURATION "дней".
// Каждый день запускается processDay() в котором в пул потоков передается список задач.
// Это действие повторяется ""всего ячеек" * Settings.DAILY_COUNT_ITERATIONS" количество раз.
// Симуляция ждёт Settings.DAY_DURATION_SECONDS секунд выполнения переданых задач, после чего завершает день.
// В конце дня запускается endDay(), который запускает процесс подведения итогов дня и сбора статистики.
// Потом запусается отрисовка острова и выводится статистика за прошедший день на экран.

public class Simulation {
    private final Island island;
    private final ExecutorService executorService = Executors.newFixedThreadPool(8);
    private static final AtomicInteger dayCounter = new AtomicInteger(0);
    public static boolean isDayRunning = false;
    private static List<ActionTaskThread> tasks = new ArrayList<>();

    public Simulation(Island island) {
        this.island = island;
    }

    public void start() {
        for (int i = 0; i < Settings.ISLAND_HEIGHT; i++) {
            for (int j = 0; j < Settings.ISLAND_WIDTH; j++) {
                tasks.add(new ActionTaskThread(Island.getIslandCells()[i][j]));
            }
        }
        for (int i = 0; i < Settings.AGE_DURATION; i++) {
            try {
                processDay();
                dayCounter.incrementAndGet();
                endDay();
                printDailyStats();
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
        executorService.shutdownNow();
        Statistics.printTotalStat();
    }

    private void processDay() {
        setIsDayRunning(true);
        try {
            for (int i = 0; i < tasks.size() * Settings.DAILY_COUNT_ITERATIONS; i++) {
                for (ActionTaskThread task : tasks) {
                    executorService.submit(task);
                }
            }
            waitNSeconds(Settings.DAY_DURATION_SECONDS);
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
        }
        setIsDayRunning(false);
    }

    private void endDay() {
        island.islandEndDay();
        island.imagineIsland();
    }

    private void printDailyStats() {
        Statistics.printDailyStat();
        Statistics.actualizingAllStat();
        Statistics.refreshDayStat();
        Statistics.refreshCountOfTypes();
    }

    public static AtomicInteger getDayCounter() {
        return dayCounter;
    }

    public static void setIsDayRunning(boolean isDayRunning) {
        Simulation.isDayRunning = isDayRunning;
    }

    private static boolean waitNSeconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
        }
        return true;
    }
}
