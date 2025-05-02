package org.the.killers.model.entitys.animals.herbivores;

import org.the.killers.model.IslandCell;
import org.the.killers.model.entitys.Animal;
import org.the.killers.model.entitys.animals.Herbivore;
import org.the.killers.settings.Settings;
import org.the.killers.statistics.Statistics;
import org.the.killers.utils.Randomizer;

public class Mouse extends Herbivore {
    public Mouse(Settings.AnimalType type) {
        super(type);
    }

    @Override
    public void eat() {
        super.eat();
        if (this.getSatiety() < Settings.NORMAL_SATIETY_PERCENT) {
            IslandCell islandCell = this.getIslandCell();
            synchronized (islandCell) {
                if (Randomizer.getProbability(Settings.CHANCE_EAT_CATERPILLAR_FOR_HERBIVORE)) {
                    Animal prey = islandCell.findPrey(Settings.AnimalType.CATERPILLAR);
                    if (prey != null) {
                        this.setSatiety(prey.getWeight());
                        prey.die();
                        Statistics.dayEatenCount.incrementAndGet();
                        Statistics.dayEatenCaterpillar.incrementAndGet();
                    }
                }
            }
        }
    }
}
