package tasks;

import model.Individual;
import util.GeneticAlgoUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SelectionCrossoverMutation extends Thread {

    private GeneticAlgoUtil geneticAlgoUtil;
    private List<Individual> parentsAndChildren;

    public SelectionCrossoverMutation(GeneticAlgoUtil geneticAlgoUtil, List<Individual> parentsAndChildren) {
        this.geneticAlgoUtil = geneticAlgoUtil;
        this.parentsAndChildren = parentsAndChildren;
    }

    public void run() {
        Random rn = new Random();

        Individual[] parents = geneticAlgoUtil.selection();
        Individual[] children = geneticAlgoUtil.crossover(parents);

        if (rn.nextInt() % 7 < 5) {
            geneticAlgoUtil.mutation(children);
        }

        parentsAndChildren.addAll(Arrays.asList(children));
    }
}
