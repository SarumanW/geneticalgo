package model;

import lombok.Data;

@Data
public class Population {
    private Individual[] individuals;
    private int fitness = 0;

    public Population(int popSize) {
        individuals = new Individual[popSize];

        this.initializePopulation();
    }

    private void initializePopulation() {
        for (int i = 0; i < individuals.length; i++) {
            individuals[i] = new Individual();
        }
    }

    public Individual getFittestIndividual() {
        int maxFit = Integer.MIN_VALUE;
        int maxFitIndex = 0;

        for (int i = 0; i < individuals.length; i++) {
            if (maxFit <= individuals[i].getFitness()) {
                maxFit = individuals[i].getFitness();
                maxFitIndex = i;
            }
        }

        fitness = individuals[maxFitIndex].getFitness();
        return individuals[maxFitIndex];
    }

    //Get the second most fitness individual
    public Individual getSecondFittest() {
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

    //Get index of least fitness individual
    public int getLeastFittestIndex() {
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

        getFittestIndividual();
    }

}