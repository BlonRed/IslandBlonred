package org.the.killers.model.entitys;

import org.the.killers.settings.Settings;
import org.the.killers.model.Entity;
import org.the.killers.model.IslandCell;
import org.the.killers.statistics.Statistics;
import org.the.killers.utils.*;

// Основой абстрактный класс который реализуют все животные.
// Содержит уникальный для каждого экземпляра животного класс - Coordinate, отвичающий за передвижение.
// Содержит реализации методов передвижения, размножения и смерти, общие для всех видов животных.
// Реализация метода питания - находится в абстрактных классах Herbivore и Predator.
// Виды животных Утки и Мыши содержат дополнение к основному методу питания.
public abstract class Animal extends Entity {
    private final Settings.AnimalType type;
    private final double weight;
    private final int maxPersonOnOneCell;
    private final int speed;
    private final double foodRequired;
    private Double satiety;
    private boolean isAlive;
    private Gender gender;
    private boolean isPregnant = false;
    Coordinate coordinate;
    IslandCell islandCell;

    public Animal(Settings.AnimalType type) {
        this.type = type;
        this.weight = type.weight;
        this.maxPersonOnOneCell = type.maxPersonOnOneCell;
        this.speed = type.speed;
        this.foodRequired = type.foodRequired;
        this.isAlive = true;
        this.coordinate = new Coordinate(this);
        this.gender = Randomizer.getRandomGender();
        islandCell = coordinate.getIslandCell();
        satiety = Settings.START_SATIETY_PERCENT;
    }

    public abstract void eat();

    public void die() {
        islandCell.removeAnimal(this);
        isAlive = false;
        Statistics.dayDiedCount.incrementAndGet();
        if (satiety < 0) {
            Statistics.dayHungryDiedCount.incrementAndGet();
        }
    }

    public void move() {
        for (int i = 0; i < speed; i++) {
            islandCell = coordinate.moveRandomDirection(this);
        }
    }

    public void reproduce() {
        if (gender == Gender.FEMALE && isPregnant) {
            return;
        }
        Animal candidate = islandCell.getPersonForReproduce(this);
        if (candidate != null) {
            synchronized (candidate) {
                if (Randomizer.getProbability(Settings.REPRODUCE_CHANCE)) {
                    Statistics.dayReproduceCount.incrementAndGet();
                    if (gender == Gender.FEMALE) {
                        isPregnant = true;
                    } else {
                        candidate.isPregnant = true;
                    }
                }
            }
        }
    }

    public void setSatiety(double weightFood) {
        double percent = weightFood / foodRequired;
        double temp = satiety + percent;
        satiety = temp > 1 ? Settings.MAX_SATIETY_PERCENT : temp;
    }

    public boolean isNeedEat() {
        return satiety <= Settings.NORMAL_SATIETY_PERCENT;
    }

    public void dailyLossSatiety() {
        this.satiety -= Settings.LOSS_SATIETY_PER_DAY;
        if (satiety < 0.0) {
            this.die();
        }
    }

    public void born() {
        isPregnant = false;
        Statistics.dayBornCount.incrementAndGet();
        AnimalCreator.createAnimal(type);
    }

    public int getMaxPersonOnOneCell() {
        return maxPersonOnOneCell;
    }

    public Gender getGender() {
        return gender;
    }

    public boolean isPregnant() {
        return isPregnant;
    }

    public Settings.AnimalType getType() {
        return type;
    }

    public IslandCell getIslandCell() {
        return islandCell;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public double getWeight() {
        return weight;
    }

    public Double getSatiety() {
        return satiety;
    }
}
