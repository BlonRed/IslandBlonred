package org.the.killers.utils;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

// Класс отвечающий за генирацию случайных значений.

public final class Randomizer {
    private Randomizer() {}
    public static boolean getProbability(int percent){
        return ThreadLocalRandom.current().nextInt(100) < percent;
    }

    public static int getRandomInt(int value){
        return ThreadLocalRandom.current().nextInt(value);
    }

    public static int getRandomInt(int from, int to){
        return ThreadLocalRandom.current().nextInt(from, to);
    }
    public static Gender getRandomGender() {
        int gender = ThreadLocalRandom.current().nextInt(2);
        if (gender == 1) {
            return Gender.MALE;
        }
        return Gender.FEMALE;
    }

    public static <T> T getRandomFromList(List<T> list) {
        if (list == null && list.isEmpty()) {
            throw new IllegalArgumentException("Список или пустой или не инициализован");
        }
        return list.get(getRandomInt(list.size()));
    }

    public static <T> T getRandomEnum(Class<T> enumClass){
        T[] values = enumClass.getEnumConstants();
        return values[getRandomInt(values.length)];
    }
}
