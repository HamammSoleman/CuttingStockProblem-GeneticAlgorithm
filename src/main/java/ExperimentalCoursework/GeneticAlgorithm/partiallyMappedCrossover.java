package ExperimentalCoursework.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the Novel solution variant of the genetic algorithm,
 * Utilises my own implementation of a Partially Mapped Crossover
 */
public class partiallyMappedCrossover extends GeneticAlgorithm{
    /**
     * @param simulationTime    - Allocated time the genetic algorithm will run for, In SECONDS
     * @param populationSize    - Initial population size, which will be kept constant
     * @param mutationRate      - Probability of offspring mutating
     * @param recombinationRate - Probability of offspring to have mixed genotypes from parents
     */
    public partiallyMappedCrossover(int simulationTime, int populationSize, float mutationRate, float recombinationRate) {
        super(simulationTime, populationSize, mutationRate, recombinationRate);
    }

    /**
     * Implementation of the Partially Mapped Crossover(PMX),
     * Copies a substring from one parent to the corresponding position of the other parent,
     * but then Maps the remaining genes from the second parent to the child (along with the Corresponding segment)
     * @return - List containing the new genes of the new child
     */
    @Override
    public List<Integer> crossover(List<Integer> parent1, List<Integer> parent2) {
        //offspring
        List<Integer> offspring = new ArrayList<Integer>(parent1.size());
        for (int i = 0; i < parent1.size(); i++) {
            offspring.add(-1);
        }
        //Generate two random numbers, r2 being larger than r1
        int r1 = super.randomNumber(0, parent1.size() - 1);
        int r2 = super.randomNumber(r1 + 1, parent1.size());
        //Index where genes overlap
        List<Integer> sameValue = sameValue(parent1, parent2, r1, r2);
        //adds elements of crossover, and resolve conflicts
        for (int i = r1; i <= r2; i++) {
            offspring.set(i, parent1.get(i));
            if (sameValue.contains(parent2.get(i))) {
                offspring.set(i, parent2.get(i));
            } else {
                int newIndex = resolveConflict(parent1, parent2, i);
                while (newIndex >= r1 & newIndex <= r2) {
                    newIndex = resolveConflict(parent1, parent2, newIndex);
                }
                offspring.set(newIndex, parent2.get(i));
            }
        }
        //adds rest of elements
        for (int i = 0; i < parent1.size(); i++) {
            if (offspring.get(i) == -1) {
                offspring.set(i, parent2.get(i));
            }
        }
        return offspring;
    }

    //Helper Function, returns the indexes in the segment, where genes
    // from corresponding segment of the other parent are the same
    private List<Integer> sameValue(List<Integer> p1, List<Integer> p2, int r1, int r2) {
        List<Integer> sameValues = new ArrayList<Integer>();
        for (int i = r1; i <= r2; i++) {
            if (p1.get(i).equals(p2.get(i))) {
                sameValues.add(i);
            }
        }
        return sameValues;
    }

    //Helper Function, Returns the index of the gene that got replaced
    private int resolveConflict(List<Integer> p1, List<Integer> p2, int index) {
        int value = p2.get(index);
        int whereToGo = p1.get(index);
        return p2.indexOf(whereToGo);
    }
}
