package org.the.killers.simulations;
import org.the.killers.model.IslandCell;
import org.the.killers.model.entitys.Animal;
import org.the.killers.utils.Randomizer;
import org.the.killers.settings.Settings;

import java.util.concurrent.Callable;

// Класс исполнения действия для животных.
// Получает строку ячеек острова.
// И на протяжении дня (DAY_DURATION_SECONDS) выбирает случайную ячейку из строки и случайное животное.
// После чего выполняет все доступные действия - есть, размножаться, ходить.

public class ActionTaskThread implements Callable<Boolean> {
    private final IslandCell[] rowOfMap;

    ActionTaskThread(IslandCell[] rowOfMap){
        this.rowOfMap = rowOfMap;
    }

    @Override
    public Boolean call() {
        while (Simulation.isDayRunning) {
            IslandCell islandCell = rowOfMap[Randomizer.getRandomInt(rowOfMap.length)];
            Settings.AnimalType type = Randomizer.getRandomEnum(Settings.AnimalType.class);
            Animal animal = null;
            while (animal == null) {
                animal = islandCell.getRandomForType(type);
            }
            animal.eat();
            animal.reproduce();
            animal.move();
        }
        return true;
    }
}
