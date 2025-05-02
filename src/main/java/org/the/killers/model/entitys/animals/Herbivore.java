package org.the.killers.model.entitys.animals;

import org.the.killers.model.IslandCell;
import org.the.killers.model.entitys.Animal;
import org.the.killers.statistics.Statistics;
import org.the.killers.settings.Settings;

// Абстрактный класс для Травоядных.

public abstract class Herbivore extends Animal {
    public Herbivore(Settings.AnimalType type) {
        super(type);
    }

    @Override
    public void eat() {
        IslandCell islandCell = this.getIslandCell();
        while (islandCell.hasPlants() && this.isNeedEat()) {
            setSatiety(islandCell.removePlant().getWeight());
            Statistics.dayEatenPlants.incrementAndGet();
        }
    }
}
