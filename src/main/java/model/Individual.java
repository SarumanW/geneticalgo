package model;

import lombok.Data;

import java.util.Random;

@Data
public class Individual {
    private int value;
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

        for (int i = 0; i < 5; i++) {
            if (genes[i] == 1) {
                ++value;
            }
        }
    }
}
