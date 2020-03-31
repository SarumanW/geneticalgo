package model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static model.Settings.*;

@Data
public class Population {
    private Individual[] individuals;
    private int value = 0;
    private List<Integer> valuesList;

    public Population(int chromosomeSize, Item[] items) {
        this.individuals = new Individual[POPULATION_SIZE];
        this.valuesList = new ArrayList<>();

        for (int i = 0; i < individuals.length; i++) {
            individuals[i] = new Individual(chromosomeSize, items);
        }
    }

    private void computeBestIndividual() {
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
    }

    public void calculateValueForEachIndividual() {
        for (Individual individual : individuals) {
            individual.calcValue();
        }

        this.computeBestIndividual();
    }

    public void valueAndFitness() {
        for (Individual individual : individuals) {
            individual.calcValue();
            individual.calcFitness();
        }

        this.computeBestIndividual();
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

                individual.setValue(0);
            }
        }
    }

    public boolean checkWhetherValueIsGlobalOptimum(int generationCount) {
        boolean isMax = true;

        if (generationCount >= OPTIMUM_PARAM) {
            Integer valueToCheck = valuesList.get(generationCount - OPTIMUM_PARAM);

            for (int j = generationCount - OPTIMUM_PARAM; j < generationCount; j++) {
                isMax = isMax && (valueToCheck >= valuesList.get(j));
            }

            if (isMax && valueToCheck < MAX_FITNESS) {
                System.out.println("\nSolution found in generation " + (generationCount - OPTIMUM_PARAM));
                System.out.println("Value: " + valueToCheck);
                System.out.println();

                return true;
            }
        }

        return false;
    }

}
