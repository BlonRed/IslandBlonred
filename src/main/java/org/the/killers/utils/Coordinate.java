package org.the.killers.utils;

import org.the.killers.settings.Settings;
import org.the.killers.model.Island;
import org.the.killers.model.IslandCell;
import org.the.killers.model.entitys.Animal;

// Уникальный для каждого экземпляра животного класс отвечающий за передвижени.

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
            if (!newIslandCell.isContainThis(animal)) {
                newIslandCell.addAnimal(animal);
                islandCell = newIslandCell;
            }
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
        return ifRandomNoExist(animal);
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

    private IslandCell ifRandomNoExist(Animal animal) {
        for (int yy = 0; yy < Settings.ISLAND_HEIGHT ; yy++) {
            for (int xx = 0; xx < Settings.ISLAND_WIDTH; xx++) {
                islandCell = Island.getIslandCell(xx, yy);
                if (!islandCell.isLimitEnd(animal)) {
                    islandCell.addAnimal(animal);
                    return islandCell;
                }
            }
        }
        throw new RuntimeException(String.format("Лиимит %s на острове близок к пределу. Симуляция будет завершена.", animal.getType()));
    }
}
