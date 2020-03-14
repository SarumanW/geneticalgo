import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Individual;
import model.Population;

import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneticAlgoUtil {
    private Individual fittest;
    private Individual secondFittest;
    private Population population;

    public void selection() {
        //Select the most fitness individual
        fittest = population.getFittestIndividual();

        //Select the second most fitness individual
        secondFittest = population.getSecondFittest();
    }

    //Crossover
    public void crossover() {
        Random rn = new Random();

        //Select a random crossover point
        int crossOverPoint = rn.nextInt(population.getIndividuals()[0].getGeneLength());

        //Swap values among parents
        for (int i = 0; i < crossOverPoint; i++) {
            int temp = fittest.getGenes()[i];
            fittest.getGenes()[i] = secondFittest.getGenes()[i];
            secondFittest.getGenes()[i] = temp;

        }

    }

    public void mutation() {
        Random rn = new Random();

        //Select a random mutation point
        int mutationPoint = rn.nextInt(population.getIndividuals()[0].getGeneLength());

        //Flip values at the mutation point
        if (fittest.getGenes()[mutationPoint] == 0) {
            fittest.getGenes()[mutationPoint] = 1;
        } else {
            fittest.getGenes()[mutationPoint] = 0;
        }

        mutationPoint = rn.nextInt(population.getIndividuals()[0].getGeneLength());

        if (secondFittest.getGenes()[mutationPoint] == 0) {
            secondFittest.getGenes()[mutationPoint] = 1;
        } else {
            secondFittest.getGenes()[mutationPoint] = 0;
        }
    }

    Individual getFittestOffspring() {
        if (fittest.getFitness() > secondFittest.getFitness()) {
            return fittest;
        }
        return secondFittest;
    }

    void addFittestOffspring() {
        fittest.calcFitness();
        secondFittest.calcFitness();

        int leastFittestIndex = population.getLeastFittestIndex();

        population.getIndividuals()[leastFittestIndex] = getFittestOffspring();
    }
}
