import model.Individual;
import model.Item;
import model.Population;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Test {
    private static final int POPULATION_SIZE = 2000;
    private static final int ITEMS_SIZE = 2000;

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        Item[] items = Item.generateItemsList(ITEMS_SIZE);
        //Item[] items = Item.generateItemsList();

        Population population = new Population(POPULATION_SIZE, ITEMS_SIZE, items);

        GeneticAlgoUtil geneticAlgoUtil = new GeneticAlgoUtil(population);

        int generationCount = 0;

        Random rn = new Random();

        //Calculate value of each individual
        population.calculateValueForEachIndividual();

        System.out.println("Generation: " + generationCount + " Value: " + population.getValue());

        //While population gets an individual with maximum value
        while (!population.checkWhetherValueIsGlobalOptimum(++generationCount)) {

            population.checkFitness();

            List<Individual> parentsAndChildren = new ArrayList<>(
                    Arrays.asList(population.getIndividuals()));

            for(int i = 0; i < POPULATION_SIZE; i++) {
                Individual[] parents = geneticAlgoUtil.selection();

                Individual[] children = geneticAlgoUtil.crossover(parents);

                if (rn.nextInt() % 7 < 5) {
                    geneticAlgoUtil.mutation(children);
                }

                parentsAndChildren.addAll(Arrays.asList(children));
            }

            geneticAlgoUtil.optimizePopulation(parentsAndChildren);

            population.calculateValueForEachIndividual();

            System.out.println("Generation: " + generationCount + " Value: " + population.getValue());
        }

        long endTime = System.nanoTime();

        long duration = (endTime - startTime) / 1000000;
        System.out.println(duration);
    }
}
