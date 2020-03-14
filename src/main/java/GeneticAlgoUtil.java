import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Individual;
import model.Population;

import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
class GeneticAlgoUtil {
    private Individual bestIndividual;
    private Individual secondBestIndividual;
    private Population population;

    void selection() {
        //Select the most value individual
        bestIndividual = population.getBestIndividual();

        //Select the second most value individual
        secondBestIndividual = population.getSecondBestIndividual();
    }

    //Crossover
    void crossover() {
        Random rn = new Random();

        //Select a random crossover point
        int crossOverPoint = rn.nextInt(population.getIndividuals()[0].getGenes().length);

        //Swap values among parents
        for (int i = 0; i < crossOverPoint; i++) {
            int temp = bestIndividual.getGenes()[i];
            bestIndividual.getGenes()[i] = secondBestIndividual.getGenes()[i];
            secondBestIndividual.getGenes()[i] = temp;
        }

    }

    void mutation() {
        Random rn = new Random();

        //Select a random mutation point
        int mutationPoint = rn.nextInt(population.getIndividuals()[0].getGenes().length);

        //Flip values at the mutation point
        if (bestIndividual.getGenes()[mutationPoint] == 0) {
            bestIndividual.getGenes()[mutationPoint] = 1;
        } else {
            bestIndividual.getGenes()[mutationPoint] = 0;
        }

        mutationPoint = rn.nextInt(population.getIndividuals()[0].getGenes().length);

        if (secondBestIndividual.getGenes()[mutationPoint] == 0) {
            secondBestIndividual.getGenes()[mutationPoint] = 1;
        } else {
            secondBestIndividual.getGenes()[mutationPoint] = 0;
        }
    }

    private Individual getBestFromTwoChildren() {
        if (bestIndividual.getValue() > secondBestIndividual.getValue()) {
            return bestIndividual;
        }
        return secondBestIndividual;
    }

    void addChildToPopulation() {
        bestIndividual.calcValue();
        secondBestIndividual.calcValue();

        int worstIndividualIndex = population.getWorstIndividualIndex();

        population.getIndividuals()[worstIndividualIndex] = getBestFromTwoChildren();
    }
}
