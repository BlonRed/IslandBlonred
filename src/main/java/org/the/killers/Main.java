package org.the.killers;

import org.the.killers.model.Island;
import org.the.killers.simulations.Simulation;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Island island = new Island();
        Simulation simulation = new Simulation(island);
        simulation.start();
    }

}