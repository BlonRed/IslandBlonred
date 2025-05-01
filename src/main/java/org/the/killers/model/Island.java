package org.the.killers.model;

import org.the.killers.utils.AnimalCreator;
import org.the.killers.utils.Randomizer;
import org.the.killers.settings.Settings;

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

    public void islandEndDay(){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                islandCells[y][x].endDayCell();

            }
        }
    }

    public void imagine(){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(islandCells[y][x].toString());
            }
            System.out.printf("%n%n");
        }
    }
}
