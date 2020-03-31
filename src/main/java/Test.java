import model.Individual;
import model.Item;
import model.Population;
import tasks.SelectionCrossoverMutation;
import util.GeneticAlgoUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static model.Settings.ITEMS_SIZE;
import static model.Settings.PARENTS_PAIRS_COUNT;

public class Test {

    private static final int
            POOL_SIZE = Runtime.getRuntime().availableProcessors();

    private static final ExecutorService exec = Executors.newFixedThreadPool(POOL_SIZE);

    public static void main(String[] args) throws InterruptedException {

        Item[] items = Item.generateItemsList(ITEMS_SIZE);
        //Item[] items = Item.generateItemsList();

        Population population = new Population(ITEMS_SIZE, items);

        GeneticAlgoUtil geneticAlgoUtil = new GeneticAlgoUtil(population);

        testSystem(population, geneticAlgoUtil);
        parallelTestSystem(population, geneticAlgoUtil);
    }

    private static void testSystem(Population population, GeneticAlgoUtil geneticAlgoUtil) {
        long startTime = System.nanoTime();

        int generationCount = 0;
        Random rn = new Random();

        population.calculateValueForEachIndividual();

        //System.out.println("Generation: " + generationCount + " Value: " + population.getValue());

        while (!population.checkWhetherValueIsGlobalOptimum(++generationCount)) {

            population.checkFitness();

            List<Individual> parentsAndChildren = new ArrayList<>(
                    Arrays.asList(population.getIndividuals()));

            for (int i = 0; i < PARENTS_PAIRS_COUNT; i++) {
                Individual[] parents = geneticAlgoUtil.selection();

                Individual[] children = geneticAlgoUtil.crossover(parents);

                if (rn.nextInt() % 7 < 5) {
                    geneticAlgoUtil.mutation(children);
                }

                parentsAndChildren.addAll(Arrays.asList(children));
            }

            geneticAlgoUtil.optimizePopulation(parentsAndChildren);

            population.valueAndFitness();

            //System.out.println("Generation: " + generationCount + " Value: " + population.getValue());
        }

        long endTime = System.nanoTime();

        long duration = (endTime - startTime) / 1000000;
        System.out.println(duration);
    }

    private static void parallelTestSystem(Population population, GeneticAlgoUtil geneticAlgoUtil) throws InterruptedException {
        long startTime = System.nanoTime();

        int generationCount = 0;

        population.calculateValueForEachIndividual();

        //System.out.println("Generation: " + generationCount + " Value: " + population.getValue());

        while (!population.checkWhetherValueIsGlobalOptimum(++generationCount)) {

            population.checkFitness();

            List<Individual> parentsAndChildren = new ArrayList<>(
                    Arrays.asList(population.getIndividuals()));

            List<SelectionCrossoverMutation> tasks = new ArrayList<>();

            for (int i = 0; i < PARENTS_PAIRS_COUNT / 40; i++) {
                tasks.add(new SelectionCrossoverMutation(geneticAlgoUtil, parentsAndChildren, 40));
            }

            exec.invokeAll(tasks);

            geneticAlgoUtil.optimizePopulation(parentsAndChildren);

            population.valueAndFitness();

            //System.out.println("Generation: " + generationCount + " Value: " + population.getValue());
        }

        long endTime = System.nanoTime();

        long duration = (endTime - startTime) / 1000000;
        System.out.println(duration);
    }
}
