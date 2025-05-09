package org.the.killers.model;


import org.the.killers.model.entitys.Animal;
import org.the.killers.model.entitys.Plant;
import org.the.killers.statistics.Statistics;
import org.the.killers.utils.Gender;
import org.the.killers.settings.Settings;
import org.the.killers.utils.Randomizer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

// Класс ячейки (поля) острова. Хранит данные о всех видах животных и растениях находящихся на ячейке.
// Выполняет все действия по работе с этими данными.
// Имеет графическое представление - Тип животного с наибольшем количеством и его количество на ячейке.
// Также содержит метод (actionForRandomAnimal()) выбирающий случайное животное в ячейке
// После чего у этого животного вызываеются действия - есть, размножаться, ходить.

public class IslandCell {
    int x;
    int y;
    Map<Settings.AnimalType, List<Animal>> mapAnimals = new ConcurrentHashMap<>();
    List<Plant> plants;

    public IslandCell(int x, int y) {
        this.x = x;
        this.y = y;
        initialization();
    }

    private void initialization() {
        plants = new CopyOnWriteArrayList<>();
        growPlants();
        fillMapAnimals();
    }

    public synchronized void addAnimal(Animal animal) {
        mapAnimals.get(animal.getType()).add(animal);
    }

    public synchronized boolean removeAnimal(Animal animal) {
        return mapAnimals.get(animal.getType()).remove(animal);
    }

    public synchronized boolean isLimitEnd(Animal animal) {
        return mapAnimals.get(animal.getType()).size() >= animal.getMaxPersonOnOneCell();

    }

    public synchronized Animal getPersonForReproduce(Animal wishing) {
        Gender genderWishing = wishing.getGender();
        return mapAnimals.get(wishing.getType()).stream()
                .filter(candidate -> candidate.getGender() != genderWishing)
                .filter(Animal::isAlive)
                .filter(candidate -> !candidate.isPregnant()).findAny().get();

    }

    public boolean hasPlants() {
        return !plants.isEmpty();
    }

    public Plant removePlant() {
        return plants.remove(0);
    }

    public synchronized Animal findPrey(Settings.AnimalType typePrey) {
        List<Animal> listOfPrey = mapAnimals.get(typePrey);
        if (!listOfPrey.isEmpty()) {
            return listOfPrey.stream().filter(Animal::isAlive).findAny().get();
        }
        return null;
    }

    public synchronized Animal getRandomAnimal() {
        return Randomizer.getRandomFromList(mapAnimals.get(Randomizer.getRandomEnum(Settings.AnimalType.class)));
    }
    public synchronized boolean isContainThis(Animal animal) {
        return mapAnimals.get(animal.getType()).contains(animal);
    }

    void growPlants() {
        int size = plants.size();
        if (size < Settings.MAX_PLANT_ON_ONE_CELL) {
            int different = Settings.MAX_PLANT_ON_ONE_CELL - size;
            int count = different > Settings.GROW_PLANT_PER_DAY ? Settings.GROW_PLANT_PER_DAY : different;
            growCountPlants(count);
        }

    }

    synchronized void growCountPlants(int count) {
        for (int i = 0; i < count; i++) {
            plants.add(new Plant());
        }
    }

    private void fillMapAnimals() {
        for (Settings.AnimalType type : Settings.AnimalType.values()) {
            mapAnimals.put(type, new CopyOnWriteArrayList<>());
        }
    }

    synchronized void endDayCell() {
        growPlants();
        for (List<Animal> animals : mapAnimals.values()) {
            for (Animal animal : animals) {
                if (animal.isPregnant()) animal.born();
                animal.dailyLossSatiety();
            }
        }
        for (Settings.AnimalType type : Settings.AnimalType.values()) {
            Statistics.countsOfTypes.put(type, (Statistics.countsOfTypes.get(type) + mapAnimals.get(type).size()));
        }
    }

    public void actionForRandomAnimal() {
            Animal animal = getRandomAnimal();
            animal.eat();
            animal.reproduce();
            animal.move();
    }

    @Override
    public String toString() {
        Settings.AnimalType type = mapAnimals.entrySet().stream().max((x, y) -> x.getValue().size() - y.getValue().size()).get().getKey();
        return String.format("[%s:%d] ", type.image, mapAnimals.get(type).size());
    }
}
