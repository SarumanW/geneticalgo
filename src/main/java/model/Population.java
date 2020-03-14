package model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Population {
    private Individual[] individuals;
    private int fitness = 0;
    private List<Integer> fitnessValuesList;

    public Population(int popSize, int chromosomeSize, Item[] items) {
        this.individuals = new Individual[popSize];
        this.fitnessValuesList = new ArrayList<>();

        for (int i = 0; i < individuals.length; i++) {
            individuals[i] = new Individual(chromosomeSize, items);
        }
    }

    public Individual getBestIndividual() {
        int maxFit = Integer.MIN_VALUE;
        int maxFitIndex = 0;

        for (int i = 0; i < individuals.length; i++) {
            if (maxFit <= individuals[i].getFitness()) {
                maxFit = individuals[i].getFitness();
                maxFitIndex = i;
            }
        }

        fitness = individuals[maxFitIndex].getFitness();
        fitnessValuesList.add(fitness);

        return individuals[maxFitIndex];
    }

    public Individual getSecondBestIndividual() {
        int maxFit1 = 0;
        int maxFit2 = 0;

        for (int i = 0; i < individuals.length; i++) {
            if (individuals[i].getFitness() > individuals[maxFit1].getFitness()) {
                maxFit2 = maxFit1;
                maxFit1 = i;
            } else if (individuals[i].getFitness() > individuals[maxFit2].getFitness()) {
                maxFit2 = i;
            }
        }

        return individuals[maxFit2];
    }

    public int getWorstIndividualIndex() {
        int minFitVal = Integer.MAX_VALUE;
        int minFitIndex = 0;

        for (int i = 0; i < individuals.length; i++) {
            if (minFitVal >= individuals[i].getFitness()) {
                minFitVal = individuals[i].getFitness();
                minFitIndex = i;
            }
        }

        return minFitIndex;
    }

    //Calculate fitness of each individual
    public void calculateFitness() {
        for (Individual individual : individuals) {
            individual.calcFitness();
        }

        getBestIndividual();
    }

    public boolean checkWhetherFitnessIsGlobalOptimum() {
        int count = 0;

        for (Integer fitnessValue : fitnessValuesList) {
            if (fitnessValue == fitness) {
                count++;
            } else {
                count = 0;
            }
        }

        return count >= 30;
    }

}
