package model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Population {
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

    public Individual getSecondBestIndividual() {
        int maxFit1 = 0;
        int maxFit2 = 0;

        for (int i = 0; i < individuals.length; i++) {
            if (individuals[i].getValue() > individuals[maxFit1].getValue()) {
                maxFit2 = maxFit1;
                maxFit1 = i;
            } else if (individuals[i].getValue() > individuals[maxFit2].getValue()) {
                maxFit2 = i;
            }
        }

        return individuals[maxFit2];
    }

    public int getWorstIndividualIndex() {
        int minFitVal = Integer.MAX_VALUE;
        int minFitIndex = 0;

        for (int i = 0; i < individuals.length; i++) {
            if (minFitVal >= individuals[i].getValue()) {
                minFitVal = individuals[i].getValue();
                minFitIndex = i;
            }
        }

        return minFitIndex;
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
            if (individual.getFitness() > 30) {
                for (int i = 0; i < individual.getGenes().length; i++) {
                    individual.getGenes()[i] = 0;
                }
            }
        }
    }

    public boolean checkWhetherFitnessIsGlobalOptimum() {
        int count = 0;

        for (Integer fitnessValue : valuesList) {
            if (fitnessValue == value) {
                count++;
            } else {
                count = 0;
            }
        }

        return count >= 15;
    }

}
