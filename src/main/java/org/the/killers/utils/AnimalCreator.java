package org.the.killers.utils;

import org.the.killers.settings.Settings;
import org.the.killers.model.entitys.Animal;
import org.the.killers.model.entitys.animals.herbivores.*;
import org.the.killers.model.entitys.animals.predators.*;

// Класс содержащий еднственный метод фабрику создания животных по переданному типу.

public class AnimalCreator {
    public static Animal createAnimal(Settings.AnimalType type) {
        return switch (type) {
            case WOLF -> new Wolf(type);
            case BOA -> new Boa(type);
            case FOX -> new Fox(type);
            case BEAR -> new Bear(type);
            case EAGLE -> new Eagle(type);
            case HORSE -> new Horse(type);
            case DEER -> new Deer(type);
            case RABBIT -> new Rabbit(type);
            case MOUSE -> new Mouse(type);
            case GOAT -> new Goat(type);
            case SHEEP -> new Sheep(type);
            case DUCK -> new Duck(type);
            case CATERPILLAR -> new Caterpillar(type);
        };
    }
}
