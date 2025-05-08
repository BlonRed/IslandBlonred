package org.the.killers.simulations;
import org.the.killers.model.IslandCell;

import java.util.concurrent.Callable;

// Класс задача. Исполненяет действия для животных.
// Получает ячейку острова.
// И если день продолжается инициирует выполнение у случайного животного в ячейке набор дествий.
// Набор - есть, размножаться, ходить.

public class ActionTaskThread implements Callable<Boolean> {
    private final IslandCell islandCell;

    ActionTaskThread(IslandCell islandCell){
        this.islandCell = islandCell;
    }

    @Override
    public Boolean call() {
        if (Simulation.isDayRunning) {
                islandCell.actionForRandomAnimal();
        }
        return true;
    }

}
