package org.the.killers.simulations;

import org.the.killers.model.Island;
import org.the.killers.statistics.Statistics;
import org.the.killers.Settings.Settings;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Simulation {
    private final Island island;
    private final ExecutorService executorS = Executors.newFixedThreadPool(8);
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
        executorS.shutdownNow();
        Statistics.printTotalStat();
    }

    private void processDay() {
        isDayRunning = true;
        for (int i = 0; i < Settings.ISLAND_HEIGHT; i++) {
            executorS.submit(new ActionTaskThread(Island.getIslandCells()[i]));
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
        island.imagine();
    }
    public static AtomicInteger getDayCounter() {
        return dayCounter;
    }
}
