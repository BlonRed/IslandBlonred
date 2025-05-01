package org.the.killers.model.entitys;

import org.the.killers.model.Entity;
import org.the.killers.Settings.Settings;

public class Plant extends Entity {
    private double weight = Settings.WEIGHT_PLANT;
    private int maxPersonOnOneCell = Settings.MAX_PLANT_ON_ONE_CELL;
    public Plant (){
    }
    public double getWeight() {
        return weight;
    }
}
