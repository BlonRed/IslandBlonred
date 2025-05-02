package org.the.killers.simulations;

import org.the.killers.model.Island;
import org.the.killers.statistics.Statistics;
import org.the.killers.settings.Settings;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

// Класс отвечающий за симуляция мира.
// Создает пулл потоков. Симуляциия длится Settings.AGE_DURATION "дней".
// Каждый день запускается processDay(), который длится Settings.DAY_DURATION_SECONDS.
// В конце дня запускается endDay(), который запускает процесс подведения итогов дня и сбора статистики.
// Потом выводит статистику за день на экран и запусает отрисовку остова.

public class Simulation {
    private final Island island;
    private final ExecutorService executorService = Executors.newFixedThreadPool(8);
    private static final AtomicInteger dayCounter = new AtomicInteger(0);
    public static boolean isDayRunning = false;

    public Simulation(Island island) {
        this.island = island;
    }

    public void start() {
        for (int i = 0; i < Settings.AGE_DURATION; i++) {
            processDay();
            dayCounter.incrementAndGet();
            endDay();
            Statistics.printDailyStat();
            Statistics.actualizingAllStat();
            Statistics.refreshDayStat();
            Statistics.refreshCountOfTypes();
        }
        executorService.shutdownNow();
        Statistics.printTotalStat();
    }

    private void processDay() {
        isDayRunning = true;
        for (int i = 0; i < Settings.ISLAND_HEIGHT; i++) {
            executorService.submit(new ActionTaskThread(Island.getIslandCells()[i]));
        }
        try {
            TimeUnit.SECONDS.sleep(Settings.DAY_DURATION_SECONDS);
            isDayRunning = false;
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
        }
    }

    private void endDay() {
        island.islandEndDay();
        island.imagineIsland();
    }
    public static AtomicInteger getDayCounter() {
        return dayCounter;
    }
}
