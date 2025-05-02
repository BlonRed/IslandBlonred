package org.the.killers.model;

import org.the.killers.simulations.Simulation;
import org.the.killers.statistics.Statistics;
import org.the.killers.utils.AnimalCreator;
import org.the.killers.utils.Randomizer;
import org.the.killers.settings.Settings;

// Класс острова, на котором происходит симуляция. Содержит массив ячеек острова.
// Запускает процесс наполнения острова "флорой и фауной".
// Содержит метод отрисовки острова.

public class Island {
    private final int height;
    private final int width;
    private static IslandCell[][] islandCells;

    public Island() {
        this.height = Settings.ISLAND_HEIGHT;
        this.width = Settings.ISLAND_WIDTH;

        islandCells = new IslandCell[height][width];

        initialize();
    }

    private void initialize() {
        createIslandCells();
        createFlora();
    }

    private void createIslandCells() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                islandCells[y][x] = new IslandCell(x, y);
            }
        }
    }

    private void createFlora() {
        for (Settings.AnimalType type : Settings.AnimalType.values()) {
            for (int i = 0; i < width * height; i++) {
                for (int j = 0; j < Randomizer.getRandomInt(1, type.maxPersonOnOneCell); j++) {
                    AnimalCreator.createAnimal(type);
                }
            }
        }
    }

    public static IslandCell[][] getIslandCells() {
        return islandCells;
    }

    public static IslandCell getIslandCell(int x, int y) {
        return islandCells[y][x];
    }

    public void islandEndDay() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                islandCells[y][x].endDayCell();
            }
        }
    }

    public void imagineIsland() {
        printStartImagine();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(islandCells[y][x].toString());
            }
            if (y == height - 1) {
                System.out.printf("%n");
            } else {
                System.out.printf("%n%n");
            }
        }
        printEndImagine();
    }

    private void printStartImagine() {
        System.out.printf("День %d%n", Simulation.getDayCounter().get());
        for (int i = 0; i < width; i++) {
            System.out.print("--------");
        }
        System.out.printf("%n");
    }

    private void printEndImagine() {
        for (int i = 0; i < width; i++) {
            System.out.print("--------");
        }
        System.out.printf("%n");
    }
}
