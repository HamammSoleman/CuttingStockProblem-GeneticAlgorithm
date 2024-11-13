package ExperimentalCoursework;

import ExperimentalCoursework.GeneticAlgorithm.GeneticAlgorithm;
import ExperimentalCoursework.GeneticAlgorithm.orderOneCrossover;
import ExperimentalCoursework.GeneticAlgorithm.partiallyMappedCrossover;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    //Stores the output of every Genetic Algorithm, when they complete their thread
    public List<Float> Output;
    //Singleton instance that each separate thread reference when they complete
    public static Main INSTANCE;

    public static void main(String[] args) {
        //Sets Singleton Instance and variables, so all threads can reference this
        Main main = new Main();
        main.Output = new ArrayList<Float>();

        int simulationTime = 30;
        int populationSize = Main.getInstance().randomNumber(20,51);
        float mutationRate = 0.01f * Main.getInstance().randomNumber(0,51);
        float recombinationRate = 0.01f * Main.INSTANCE.randomNumber(30,101);

        System.out.printf("Executing both Genetic algorithms for %d seconds...\n", simulationTime);
        //Creates Threads and runs the Genetic Algorithm for the Baseline Solution
        for (int i = 0; i < 10; i++) {
            GeneticAlgorithm onePointCrossover = new orderOneCrossover(simulationTime, populationSize, mutationRate, recombinationRate);
            onePointCrossover.start();
        }

        //Creates Threads and runs the Genetic Algorithm for the Novel Solution
        for (int i = 0; i < 10; i++) {
            GeneticAlgorithm partiallyMappedCrossover = new partiallyMappedCrossover(simulationTime, 50, 0.1f, 0.55f);
            partiallyMappedCrossover.start();
        }
    }

    public Main() {
        INSTANCE = this;
    }

    /**
     * @return Singleton instance of Main
     */
    public static Main getInstance() {
        return INSTANCE;
    }

    /**
     * Executes the following code when a genetic algorithm finishes running
     */
    public void doOnThreadCompletion() {
        //Print Output Into Console when all threads finish
        if (Main.getInstance().Output.size() == 50) {
            System.out.print("Completed Execution of Genetic Algorithms, below are the results \n");
            System.out.println("Best solution costs are the combined from both algorithm executions, sorted from best cheapest to most expensive:\n");
            Collections.sort(Main.getInstance().Output);
            for (Float cost : Output) {
                System.out.print(cost + ",");
            }
        }
    }

    /**
     * @param min - Minimum value for the random number, Inclusive
     * @param max - Maximum value for the random number, Exclusive
     * @return - returns a random int
     */
    public int randomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
