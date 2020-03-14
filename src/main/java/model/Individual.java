package model;

import lombok.Data;

import java.util.Random;

@Data
public class Individual {
    private int fitness;
    private int[] genes = new int[5];
    private int geneLength = 5;

    public Individual() {
        Random rn = new Random();

        for (int i = 0; i < genes.length; i++) {
            genes[i] = Math.abs(rn.nextInt() % 2);
        }
    }

    public void calcFitness() {
        fitness = 0;

        for (int i = 0; i < 5; i++) {
            if (genes[i] == 1) {
                ++fitness;
            }
        }
    }
}
