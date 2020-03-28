package util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Individual;
import model.Population;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static model.Settings.ITEMS_SIZE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneticAlgoUtil {
    private Population population;

    //Get two different parents
    public synchronized Individual[] selection() {
        Individual[] parents = new Individual[2];

        int firstIndex = 0;
        int secondIndex = 0;

        while (firstIndex == secondIndex) {
            firstIndex = ThreadLocalRandom.current().nextInt(0, population.getIndividuals().length);
            secondIndex = ThreadLocalRandom.current().nextInt(0, population.getIndividuals().length);
        }

        parents[0] = population.getIndividuals()[firstIndex];
        parents[1] = population.getIndividuals()[secondIndex];

        return parents;
    }

    public synchronized Individual[] crossover(Individual[] parents) {
        Random rn = new Random();

        //Select a random crossover point
        int crossOverPoint = rn.nextInt(population.getIndividuals()[0].getGenes().length);

        //Swap values among parents
        for (int i = 0; i < crossOverPoint; i++) {
            int temp = parents[0].getGenes()[i];
            parents[0].getGenes()[i] = parents[1].getGenes()[i];
            parents[1].getGenes()[i] = temp;
        }

        return parents;
    }

    public synchronized void mutation(Individual[] children) {
        Random rn = new Random();

        for (Individual child : children) {
            for (int i = 0; i < ITEMS_SIZE / 2; i++) {
                int mutationPoint = rn.nextInt(population.getIndividuals()[0].getGenes().length);

                //Flip values at the mutation point
                if (child.getGenes()[mutationPoint] == 0) {
                    child.getGenes()[mutationPoint] = 1;
                } else {
                    child.getGenes()[mutationPoint] = 0;
                }
            }
        }
    }

    public void optimizePopulation(List<Individual> parentsAndChildren) {
        for (Individual individual : parentsAndChildren) {
            individual.calcValue();
        }

        List<Individual> newPopulation = parentsAndChildren.stream()
                .sorted(Comparator.comparingInt(Individual::getValue).reversed())
                .limit(population.getIndividuals().length)
                .collect(Collectors.toList());

        for (int i = 0; i < population.getIndividuals().length; i++) {
            population.getIndividuals()[i] = newPopulation.get(i);
        }
    }
}
