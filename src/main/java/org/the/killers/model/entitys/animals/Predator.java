package org.the.killers.model.entitys.animals;

import org.the.killers.model.IslandCell;
import org.the.killers.model.entitys.Animal;
import org.the.killers.statistics.Statistics;
import org.the.killers.utils.Randomizer;
import org.the.killers.settings.Settings;

import java.util.Map;

// Абстрактный класс для Хищников. Содержит список рациона для вида.

public abstract class Predator extends Animal {
    Map<Settings.AnimalType, Integer> foodList = Settings.FOOD_LIST.get(this.getType());

    public Predator(Settings.AnimalType type) {
        super(type);
    }

    @Override
    public void eat() {
        if (!this.isNeedEat()) {
            return;
        }
        IslandCell islandCell = this.getIslandCell();
        synchronized (islandCell) {
            for (Map.Entry<Settings.AnimalType, Integer> animalType : foodList.entrySet()) {
                if (Randomizer.getProbability(animalType.getValue())) {
                    Animal prey = islandCell.findPrey(animalType.getKey());
                    if (prey != null) {
                        synchronized (prey) {
                            this.setSatiety(prey.getWeight());
                            prey.die();
                            Statistics.dayEatenCount.incrementAndGet();
                        }
                    }
                }
            }
            if (!isNeedEat()) {
                return;
            }
        }
    }
}
