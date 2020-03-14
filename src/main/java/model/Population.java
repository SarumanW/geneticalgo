package model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Population {
    private static final int OPTIMUM_PARAM = 1000;
    private static final int MAX_FITNESS = 4000;

    private Individual[] individuals;
    private int value = 0;
    private List<Integer> valuesList;

    public Population(int popSize, int chromosomeSize, Item[] items) {
        this.individuals = new Individual[popSize];
        this.valuesList = new ArrayList<>();

        for (int i = 0; i < individuals.length; i++) {
            individuals[i] = new Individual(chromosomeSize, items);
        }
    }

    public Individual getBestIndividual() {
        int maxFit = Integer.MIN_VALUE;
        int maxFitIndex = 0;

        for (int i = 0; i < individuals.length; i++) {
            if (maxFit <= individuals[i].getValue()) {
                maxFit = individuals[i].getValue();
                maxFitIndex = i;
            }
        }

        value = individuals[maxFitIndex].getValue();
        valuesList.add(value);

        return individuals[maxFitIndex];
    }

    public void calculateValueForEachIndividual() {
        for (Individual individual : individuals) {
            individual.calcValue();
        }

        this.getBestIndividual();
    }

    public void checkFitness() {
        for (Individual individual : individuals) {
            individual.calcFitness();
        }

        this.fillNotFitWithZero();
    }

    private void fillNotFitWithZero() {
        for (Individual individual : individuals) {
            if (individual.getFitness() > MAX_FITNESS) {
                for (int i = 0; i < individual.getGenes().length; i++) {
                    individual.getGenes()[i] = 0;
                }
            }
        }
    }

    public boolean checkWhetherValueIsGlobalOptimum(int generationCount) {
        boolean isMax = true;

        if (generationCount >= OPTIMUM_PARAM) {
            Integer valueToCheck = valuesList.get(generationCount - OPTIMUM_PARAM);

            for (int j = generationCount - OPTIMUM_PARAM; j < generationCount; j++) {
                isMax = isMax && valueToCheck >= valuesList.get(j);
            }
            if (isMax) {
                System.out.println("\nSolution found in generation " + (generationCount - OPTIMUM_PARAM));
                System.out.println("Value: " + valueToCheck);
                System.out.println();

                return true;
            }
        }

        return false;
    }

}
