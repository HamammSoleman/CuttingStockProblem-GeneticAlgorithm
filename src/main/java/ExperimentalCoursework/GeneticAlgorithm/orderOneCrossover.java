package ExperimentalCoursework.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the Baseline implementation of the genetic algorithm,
 * Utilising the  One Point crossover we learnt in Week 3's lecture
 */
public class orderOneCrossover extends GeneticAlgorithm {

    /**
     * @param simulationTime    - Allocated time the genetic algorithm will run for, In SECONDS
     * @param populationSize    - Initial population size, which will be kept constant
     * @param mutationRate      - Probability of offspring mutating
     * @param recombinationRate - Probability of offspring to have mixed genotypes from parents
     */
    public orderOneCrossover(int simulationTime, int populationSize, float mutationRate, float recombinationRate) {
        super(simulationTime, populationSize, mutationRate, recombinationRate);
    }

    /**
     * Implementation of the OnePoint crossover,
     * Copies a substring (of genes) from one parent to the child, the remaining positions
     * are filled by the other parent in their relative order, by also excluding duplicates
     * @return - List containing the genes of the new child
     */
    @Override
    public List<Integer> crossover(List<Integer> parent1, List<Integer> parent2) {
        //Construct offspring and copy
        List<Integer> offspring = new ArrayList<Integer>();
        List<Integer> parent2Copy = new ArrayList<>(parent2);

        for (int i = 0; i < parent1.size(); i++) {offspring.add(-1);}
        //Generate two random numbers, r2 being larger than r1
        int r1 = super.randomNumber(0, parent1.size() - 1);
        int r2 = super.randomNumber(r1 + 1, parent1.size());
        //List to remove duplicates, and map genes to Offspring, r1 and r2 inclusive
        List<Integer> genesToBeMapped = new ArrayList<>();
        for (int i = r1; i <= r2; i++) {
            genesToBeMapped.add(parent1.get(i));
            offspring.set(i, parent1.get(i));
        }
        //Remove mapped genetic information from other parent, handle duplicates
        //parent2.removeAll(genesToBeMapped); Only Here For Debug Purposes
        int index = 0;
        while (genesToBeMapped.size() != 0) {
            if (parent2Copy.get(index).equals(genesToBeMapped.get(0))) {
                parent2Copy.remove(index);
                genesToBeMapped.remove(0);
                index = 0;
                continue;
            }
            index++;
        }
        //Map remaining genetic data from parent 2 to the offspring, r1 and r2 excluded
        //Elements before crossover
        for (int i = 0; i < r1; i++) {
            offspring.set(i, parent2Copy.get(0));
            parent2Copy.remove(0);
        }
        //Elements after crossover
        for (int i = r2 + 1; i < offspring.size(); i++) {
            offspring.set(i, parent2Copy.get(0));
            parent2Copy.remove(0);
        }
        return offspring;
    }
}
