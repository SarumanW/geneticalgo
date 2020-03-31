package tasks;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.Individual;
import util.GeneticAlgoUtil;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

@Data
@AllArgsConstructor
public class SelectionCrossoverMutation implements Callable<Boolean> {

    private GeneticAlgoUtil geneticAlgoUtil;
    private List<Individual> parentsAndChildren;

    private int pairsToGenerate;

    public Boolean call() {
        Random rn = new Random();

        for (int i = 0; i < pairsToGenerate; i++) {
            Individual[] parents = geneticAlgoUtil.selection();
            Individual[] children = geneticAlgoUtil.crossover(parents);

            if (rn.nextInt() % 7 < 5) {
                geneticAlgoUtil.mutation(children);
            }

            geneticAlgoUtil.addChildren(children, parentsAndChildren);
        }

        return true;
    }
}
