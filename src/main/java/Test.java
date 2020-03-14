import model.Individual;
import model.Item;
import model.Population;

import java.util.Random;

public class Test {
    private static final int POPULATION_SIZE = 10;
    private static final int ITEMS_SIZE = 10;

    public static void main(String[] args) {
        Item[] items = Item.generateItemsList(ITEMS_SIZE);

        Population population = new Population(POPULATION_SIZE, ITEMS_SIZE, items);
        Individual bestIndividual = new Individual(ITEMS_SIZE, items);
        Individual secondBestIndividual = new Individual(ITEMS_SIZE, items);

        GeneticAlgoUtil geneticAlgoUtil = new GeneticAlgoUtil(bestIndividual, secondBestIndividual, population);

        int generationCount = 0;

        Random rn = new Random();

        //Calculate value of each individual
        population.calculateValueForEachIndividual();

        System.out.println("Generation: " + generationCount + " Value: " + population.getValue());

        //While population gets an individual with maximum value
        while (!population.checkWhetherFitnessIsGlobalOptimum()) {
            ++generationCount;

            population.checkFitness();

            //Select two the best individuals
            geneticAlgoUtil.selection();

            //Generate children
            geneticAlgoUtil.crossover();

            if (rn.nextInt() % 7 < 5) {
                geneticAlgoUtil.mutation();
            }

            //Add value offspring to population
            geneticAlgoUtil.addChildToPopulation();

            population.calculateValueForEachIndividual();

            System.out.println("Generation: " + generationCount + " Value: " + population.getValue());
        }

        System.out.println("\nSolution found in generation " + generationCount);
        System.out.println("Value: " + population.getBestIndividual().getValue());
        System.out.print("Genes: ");
        for (int i = 0; i < population.getBestIndividual().getGenes().length; i++) {
            System.out.print(population.getBestIndividual().getGenes()[i]);
        }

        System.out.println();
    }
}
