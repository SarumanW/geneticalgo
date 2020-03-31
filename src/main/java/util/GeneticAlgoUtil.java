package util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.DifferModel;
import model.Individual;
import model.Population;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static model.Settings.ITEMS_SIZE;
import static model.Settings.POPULATION_SIZE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneticAlgoUtil {
    private Population population;

    //Get two different parents
    public synchronized Individual[] selection() {
        Individual[] parents = new Individual[2];

        int firstIndex = ThreadLocalRandom.current().nextInt(0, population.getIndividuals().length);

        Individual firstParent = population.getIndividuals()[firstIndex];

        List<DifferModel> differModels = new ArrayList<>();

        for (int j = 0; j < population.getIndividuals().length; j++) {

            if (j != firstIndex) {

                int[] twoGenes = population.getIndividuals()[j].getGenes();

                int howManyDiffer = 0;

                for (int k = 0; k < ITEMS_SIZE; k++) {
                    if (firstParent.getGenes()[k] != twoGenes[k]) {
                        howManyDiffer++;
                    }
                }

                differModels.add(new DifferModel(firstIndex, j, howManyDiffer));
            }
        }

        DifferModel differModel = differModels.stream()
                .min(Comparator.comparingInt(DifferModel::getDifferLevel))
                .orElse(new DifferModel());

        parents[0] = firstParent;
        parents[1] = population.getIndividuals()[differModel.getJ()];

        return parents;
    }

    public synchronized Individual[] crossover(Individual[] parents) {
        int firstIndex = 0;
        int secondIndex = 1;

        for (int i = 0; i < parents[0].getGenes().length; i++) {
            parents[0].getGenes()[i] = parents[firstIndex].getGenes()[i];
            parents[1].getGenes()[i] = parents[secondIndex].getGenes()[i];

            if (firstIndex == 0) {
                firstIndex = 1;
            } else {
                firstIndex = 0;
            }

            if (secondIndex == 0) {
                secondIndex = 1;
            } else {
                secondIndex = 0;
            }
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

    public synchronized void addChildren(Individual[] children, List<Individual> parentsAndChildren) {
        parentsAndChildren.addAll(Arrays.asList(children));
    }

    public void optimizePopulation(List<Individual> parentsAndChildren) {
        for (Individual individual : parentsAndChildren) {
            individual.calcValue();
        }

        List<Individual> newPopulation = parentsAndChildren.stream()
                .sorted(Comparator.comparingInt(Individual::getValue).reversed())
                .limit(POPULATION_SIZE)
                .collect(Collectors.toList());

        for (int i = 0; i < population.getIndividuals().length; i++) {
            population.getIndividuals()[i] = newPopulation.get(i);
        }
    }
}
