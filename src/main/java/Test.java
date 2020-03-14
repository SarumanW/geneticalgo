import model.Individual;
import model.Population;

import java.util.Random;

public class Test {
    private static final int POPULATION_SIZE = 10;

    public static void main(String[] args) {
        Population population = new Population(POPULATION_SIZE);

        Individual fittest = new Individual();
        Individual secondFittest = new Individual();

        GeneticAlgoUtil geneticAlgoUtil = new GeneticAlgoUtil(fittest, secondFittest, population);

        int generationCount = 0;

        Random rn = new Random();

        //Calculate fitness of each individual
        population.calculateFitness();

        System.out.println("Generation: " + generationCount + " Fitness: " + population.getFitness());

        //While population gets an individual with maximum fitness
        while (population.getFitness() < 5) {
            ++generationCount;

            geneticAlgoUtil.selection();

            geneticAlgoUtil.crossover();

            if (rn.nextInt() % 7 < 5) {
                geneticAlgoUtil.mutation();
            }

            //Add fitness offspring to population
            geneticAlgoUtil.addFittestOffspring();

            population.calculateFitness();

            System.out.println("Generation: " + generationCount + " Fittest: " + population.getFitness());
        }

        System.out.println("\nSolution found in generation " + generationCount);
        System.out.println("Fitness: " + population.getFittestIndividual().getFitness());
        System.out.print("Genes: ");
        for (int i = 0; i < 5; i++) {
            System.out.print(population.getFittestIndividual().getGenes()[i]);
        }

        System.out.println();

    }
}
