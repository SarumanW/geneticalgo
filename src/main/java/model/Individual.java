package model;

import lombok.Data;

import java.util.Random;

@Data
public class Individual {
    private int value;
    private int fitness;
    private int[] genes;
    private Item[] items;

    public Individual(int chromosomeLength, Item[] items) {
        this.items = items;
        this.genes = new int[chromosomeLength];

        Random rn = new Random();
        for (int i = 0; i < genes.length; i++) {
            genes[i] = Math.abs(rn.nextInt() % 2);
        }
    }

    public void calcValue() {
        value = 0;

        for (int i = 0; i < genes.length; i++) {
            value += genes[i] * items[i].getValue();
        }
    }

    public void calcFitness() {
        fitness = 0;

        for (int i = 0; i < genes.length; i++) {
            fitness += genes[i] * items[i].getFitness();
        }
    }
}
