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
        Individual fittest = new Individual(ITEMS_SIZE, items);
        Individual secondFittest = new Individual(ITEMS_SIZE, items);

        GeneticAlgoUtil geneticAlgoUtil = new GeneticAlgoUtil(fittest, secondFittest, population);

        int generationCount = 0;

        Random rn = new Random();

        //Calculate fitness of each individual
        population.calculateFitness();

        System.out.println("Generation: " + generationCount + " Fitness: " + population.getFitness());

        //While population gets an individual with maximum fitness
        while (!population.checkWhetherFitnessIsGlobalOptimum()) {
            ++generationCount;

            //Select two the best individuals
            geneticAlgoUtil.selection();

            //Generate children
            geneticAlgoUtil.crossover();

            if (rn.nextInt() % 7 < 5) {
                geneticAlgoUtil.mutation();
            }

            //Add fitness offspring to population
            geneticAlgoUtil.addChildToPopulation();

            population.calculateFitness();

            System.out.println("Generation: " + generationCount + " Fittest: " + population.getFitness());
        }

        System.out.println("\nSolution found in generation " + generationCount);
        System.out.println("Fitness: " + population.getBestIndividual().getFitness());
        System.out.print("Genes: ");
        for (int i = 0; i < 5; i++) {
            System.out.print(population.getBestIndividual().getGenes()[i]);
        }

        System.out.println();

    }
}
