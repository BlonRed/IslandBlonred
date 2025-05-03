package org.the.killers.simulations;
import org.the.killers.model.IslandCell;

import java.util.concurrent.Callable;

// Класс исполнения действия для животных.
// Получает ячеку острова.
// И на протяжении дня (DAY_DURATION_SECONDS) инициирует выполнение у случайного животного набор дествий.
// Набор - есть, размножаться, ходить.

public class ActionTaskThread implements Callable<Boolean> {
    private final IslandCell islandCell;

    ActionTaskThread(IslandCell islandCell){
        this.islandCell = islandCell;
    }

    @Override
    public Boolean call() {
        while (Simulation.isDayRunning) {
                islandCell.actionForRandomAnimal();
        }
        return true;
    }

}
