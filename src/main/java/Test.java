import model.Individual;
import model.Item;
import model.Population;
import tasks.SelectionCrossoverMutation;
import util.GeneticAlgoUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static model.Settings.ITEMS_SIZE;
import static model.Settings.POPULATION_SIZE;

public class Test {

    private static final int
            POOL_SIZE = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws InterruptedException {

        Item[] items = Item.generateItemsList(ITEMS_SIZE);
        //Item[] items = Item.generateItemsList();

        Population population = new Population(POPULATION_SIZE, ITEMS_SIZE, items);

        GeneticAlgoUtil geneticAlgoUtil = new GeneticAlgoUtil(population);

        testSystem(population, geneticAlgoUtil);
        parallelTestSystem(population, geneticAlgoUtil);
    }

    private static void testSystem(Population population, GeneticAlgoUtil geneticAlgoUtil) {
        long startTime = System.nanoTime();

        int generationCount = 0;
        Random rn = new Random();

        //Calculate value of each individual
        population.calculateValueForEachIndividual();

        //System.out.println("Generation: " + generationCount + " Value: " + population.getValue());

        //While population gets an individual with maximum value
        while (!population.checkWhetherValueIsGlobalOptimum(++generationCount)) {

            population.checkFitness();

            List<Individual> parentsAndChildren = new ArrayList<>(
                    Arrays.asList(population.getIndividuals()));

            for (int i = 0; i < POPULATION_SIZE; i++) {
                Individual[] parents = geneticAlgoUtil.selection();

                Individual[] children = geneticAlgoUtil.crossover(parents);

                if (rn.nextInt() % 7 < 5) {
                    geneticAlgoUtil.mutation(children);
                }

                parentsAndChildren.addAll(Arrays.asList(children));
            }

            geneticAlgoUtil.optimizePopulation(parentsAndChildren);

            population.calculateValueForEachIndividual();

            //System.out.println("Generation: " + generationCount + " Value: " + population.getValue());
        }

        long endTime = System.nanoTime();

        long duration = (endTime - startTime) / 1000000;
        System.out.println(duration);
    }

    private static void parallelTestSystem(Population population, GeneticAlgoUtil geneticAlgoUtil) throws InterruptedException {
        long startTime = System.nanoTime();

        int generationCount = 0;

        //Calculate value of each individual
        population.calculateValueForEachIndividual();

        //System.out.println("Generation: " + generationCount + " Value: " + population.getValue());

        //While population gets an individual with maximum value
        while (!population.checkWhetherValueIsGlobalOptimum(++generationCount)) {

            population.checkFitness();

            List<Individual> parentsAndChildren = new ArrayList<>(
                    Arrays.asList(population.getIndividuals()));

            List<SelectionCrossoverMutation> tasks = new ArrayList<>();

            for (int i = 0; i < POOL_SIZE; i++) {
                tasks.add(new SelectionCrossoverMutation(geneticAlgoUtil, parentsAndChildren));
            }

            for (int i = 0; i < POOL_SIZE; i++) {
                tasks.get(i).start();
            }

            for (int i = 0; i < POOL_SIZE; i++) {
                tasks.get(i).join();
            }

            geneticAlgoUtil.optimizePopulation(parentsAndChildren);

            population.calculateValueForEachIndividual();

            //System.out.println("Generation: " + generationCount + " Value: " + population.getValue());
        }

        long endTime = System.nanoTime();

        long duration = (endTime - startTime) / 1000000;
        System.out.println(duration);
    }
}
