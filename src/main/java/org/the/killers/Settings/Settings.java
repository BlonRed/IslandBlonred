package org.the.killers.Settings;

import org.the.killers.model.entitys.Animal;
import org.the.killers.model.entitys.animals.herbivors.*;
import org.the.killers.model.entitys.animals.predators.*;

import java.util.*;

public class Settings {
    //World
    public static final int ISLAND_WIDTH = 10;
    public static final int ISLAND_HEIGHT = 10;
    public static final int DAY_DURATION_SECONDS = 10;
    public static final int AGE_DURATION = 10;

    //Plants
    public static final double WEIGHT_PLANT = 1;
    public static final int MAX_PLANT_ON_ONE_CELL = 300;
    public static final int GROW_PLANT_PER_DAY = 100;

    //Animals
    public static final double START_SATIETY_PERCENT = 0.9;
    public static final double NORMAL_SATIETY_PERCENT = 0.8;
    public static final double MAX_SATIETY_PERCENT = 1.2;
    public static final double LOSS_SATIETY_PER_DAY = 0.1;
    public static final int REPRODUCE_CHANCE = 80;

    public enum AnimalType {
        WOLF (50., 30, 3, 8., Wolf.class, "\uD83D\uDC3A"),
        BOA (15., 30, 1, 3., Boa.class, "\uD83D\uDC0D"),
        FOX (8., 30, 2, 2., Fox.class, "\uD83E\uDD8A"),
        BEAR (500., 5, 2, 80., Bear.class, "\uD83D\uDC3B"),
        EAGLE (6., 20, 3, 1., Eagle.class, "\uD83E\uDD85"),
        HORSE (400., 20, 4, 60., Horse.class, "\uD83D\uDC0E"),
        DEER (300., 20, 4, 50., Deer.class, "\uD83E\uDD8C"),
        RABBIT (2., 150, 2, .45, Rabbit.class, "\uD83D\uDC07"),
        MOUSE (.05, 200, 1, .01, Mouse.class, "\uD83D\uDC01"),
        GOAT (60., 140, 3, 10., Goat.class, "\uD83D\uDC10"),
        SHEEP (70., 140, 3, 15., Sheep.class, "\uD83D\uDC11"),
        DUCK (1., 200, 4, .15, Duck.class, "\uD83E\uDD86"),
        CATERPILLAR (.01, 100, 0, 0, Caterpillar.class, "\uD83D\uDC1B");

        AnimalType (double weight, int maxPersonOnOneCell, int speed, double foodRequired, Class<? extends Animal> clazz, String image){
            this.weight = weight;
            this.maxPersonOnOneCell = maxPersonOnOneCell;
            this.speed = speed;
            this.foodRequired = foodRequired;
            this.clazz = clazz;
            this.image = image;
        }

        public final double weight;
        public final int maxPersonOnOneCell;
        public final int speed;
        public final double foodRequired;
        public final Class<? extends Animal> clazz;
        public final String image;

        @Override
        public String toString() {
            return image;
        }
    }


    public static final Map<AnimalType, Map<AnimalType, Integer>> FOOD_LIST = new EnumMap<>(AnimalType.class);
    public static final Map<AnimalType, Integer> FOOD_LIST_WOLF = new EnumMap<>(AnimalType.class);
    public static final Map<AnimalType, Integer> FOOD_LIST_BOA = new EnumMap<>(AnimalType.class);
    public static final Map<AnimalType, Integer> FOOD_LIST_FOX = new EnumMap<>(AnimalType.class);
    public static final Map<AnimalType, Integer> FOOD_LIST_BEAR = new EnumMap<>(AnimalType.class);
    public static final Map<AnimalType, Integer> FOOD_LIST_EAGLE = new EnumMap<>(AnimalType.class);

    static {
        FOOD_LIST_WOLF.put(AnimalType.HORSE, 10);
        FOOD_LIST_WOLF.put(AnimalType.DEER, 15);
        FOOD_LIST_WOLF.put(AnimalType.RABBIT, 60);
        FOOD_LIST_WOLF.put(AnimalType.MOUSE, 80);
        FOOD_LIST_WOLF.put(AnimalType.GOAT, 60);
        FOOD_LIST_WOLF.put(AnimalType.SHEEP, 70);
        FOOD_LIST_WOLF.put(AnimalType.DUCK, 40);

        FOOD_LIST_BOA.put(AnimalType.FOX, 15);
        FOOD_LIST_BOA.put(AnimalType.RABBIT, 20);
        FOOD_LIST_BOA.put(AnimalType.MOUSE, 40);
        FOOD_LIST_BOA.put(AnimalType.DUCK, 10);

        FOOD_LIST_FOX.put(AnimalType.RABBIT, 70);
        FOOD_LIST_FOX.put(AnimalType.MOUSE, 90);
        FOOD_LIST_FOX.put(AnimalType.DUCK, 60);
        FOOD_LIST_FOX.put(AnimalType.CATERPILLAR, 40);

        FOOD_LIST_BEAR.put(AnimalType.BOA, 80);
        FOOD_LIST_BEAR.put(AnimalType.HORSE, 40);
        FOOD_LIST_BEAR.put(AnimalType.DEER, 80);
        FOOD_LIST_BEAR.put(AnimalType.RABBIT, 80);
        FOOD_LIST_BEAR.put(AnimalType.MOUSE, 90);
        FOOD_LIST_BEAR.put(AnimalType.GOAT, 70);
        FOOD_LIST_BEAR.put(AnimalType.SHEEP, 70);
        FOOD_LIST_BEAR.put(AnimalType.DUCK, 10);

        FOOD_LIST_EAGLE.put(AnimalType.FOX, 10);
        FOOD_LIST_EAGLE.put(AnimalType.RABBIT, 90);
        FOOD_LIST_EAGLE.put(AnimalType.MOUSE, 90);
        FOOD_LIST_EAGLE.put(AnimalType.DUCK, 80);

        FOOD_LIST.put(AnimalType.WOLF, FOOD_LIST_WOLF);
        FOOD_LIST.put(AnimalType.BOA, FOOD_LIST_BOA);
        FOOD_LIST.put(AnimalType.FOX, FOOD_LIST_WOLF);
        FOOD_LIST.put(AnimalType.BEAR, FOOD_LIST_WOLF);
        FOOD_LIST.put(AnimalType.EAGLE, FOOD_LIST_WOLF);
    }

}
