package org.the.killers.utils;

import org.the.killers.Settings.Settings;
import org.the.killers.model.Island;
import org.the.killers.model.IslandCell;
import org.the.killers.model.entitys.Animal;

public class Coordinate {
    int x;
    int y;
    int xOld;
    int yOld;
    IslandCell islandCell;

    public Coordinate(Animal animal) {
        setRandomCoordinate(animal);
    }

    public IslandCell moveRandomDirection(Animal animal) {
        xOld = x;
        yOld = y;
        setRandomDirection(Randomizer.getRandomInt(4));
        validCoordinate();
        IslandCell newIslandCell = Island.getIslandCell(x, y);
        if (newIslandCell.isLimitEnd(animal)) {
            x = xOld;
            y = yOld;
        } else {
            islandCell.removeAnimal(animal);
            newIslandCell.addAnimal(animal);
            islandCell = newIslandCell;
        }
        return islandCell;
    }

    public IslandCell setRandomCoordinate(Animal animal) {
        for (int i = 0; i < (Settings.ISLAND_HEIGHT * Settings.ISLAND_WIDTH); i++) {
            x = Randomizer.getRandomInt(Settings.ISLAND_WIDTH);
            y = Randomizer.getRandomInt(Settings.ISLAND_HEIGHT);
            islandCell = Island.getIslandCell(x, y);
            if (!islandCell.isLimitEnd(animal)) {
                islandCell.addAnimal(animal);
                return islandCell;
            }
        }
        throw new RuntimeException();
    }

    private void setRandomDirection(int direction) {
        switch (direction) {
            case 0:
                x++;
                break;
            case 1:
                y++;
                break;
            case 2:
                x--;
                break;
            case 3:
                y--;
                break;
        }
    }

    private void validCoordinate() {
        if (x >= Settings.ISLAND_WIDTH) {
            x -= 2;
        } else if (x < 0) {
            x += 2;
        }
        if (y >= Settings.ISLAND_HEIGHT) {
            y -= 2;
        } else if (y < 0) {
            y += 2;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public IslandCell getIslandCell() {
        return islandCell;
    }
}
