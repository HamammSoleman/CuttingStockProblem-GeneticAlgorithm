package ExperimentalCoursework.GeneticAlgorithm;

import ExperimentalCoursework.CuttingStockProblem.CuttingStock;
import ExperimentalCoursework.Main;

import java.util.*;

public abstract class GeneticAlgorithm extends Thread {
    //Defining variables a typical genetic algorithm would use
    public List<Order> population;
    private CuttingStock cuttingStock;
    //Most efficient Cost
    public Float bestCost;
    //Order-based genotype of best Cost, given in terms of activity per Stock length
    //Rather than the string order, it allows me to see what's happening
    private Map<Integer, List<Integer>> bestGenotype;
    //Best order, in its natrual form, before decoding
    public List<Integer> bestOrder;
    //Defining Parameters
    int simulationTime;
    int populationSize;
    float mutationRate;
    float recombinationRate;

    /**
     * @param simulationTime    - Allocated time the genetic algorithm will run for, In SECONDS
     * @param populationSize    - Initial population size, which will be kept constant
     * @param mutationRate      - Probability of offspring mutating
     * @param recombinationRate - Probability of offspring to have mixed genotypes from parents
     */
    public GeneticAlgorithm(int simulationTime, int populationSize, float mutationRate, float recombinationRate) {
        //Set All values
        this.simulationTime = simulationTime;
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.recombinationRate = recombinationRate;
        //Construct objects
        cuttingStock = new CuttingStock();
        population = new ArrayList<Order>();
        bestGenotype = new HashMap<Integer, List<Integer>>();
        //Create initial population
        initialise();
    }

    /**
     * Responsible for running the genetic algorithm,
     * Overrides Thread's run function to enable multithreading
     */
    @Override
    public void run() {
        //Ensures the simulations runs for the expected time
        long start = System.currentTimeMillis();
        long end = start + simulationTime * 1000L;
        //Holds next population
        List<Order> nextPopulation = new ArrayList<Order>();
        //Run for simulationTime seconds
        while (System.currentTimeMillis() < end) {
            //Select parents
            Order parent1 = population.get(0);
            Order parent2 = population.get(1);
            Float newCost = parent1.cost;
            //Set Global Best Orders
            if (newCost < bestCost) {
                bestOrder = new ArrayList<Integer>(parent1.order);
                bestCost = parent1.cost;
//                System.out.println("Best Order Is: " + bestOrder);
//                System.out.println("Best Cost Is: " + bestCost);
            }
            //Clear current population
            nextPopulation.clear();
            //Generate the next population
            for (int i = 0; i < populationSize; i++) {
                List<Integer> newOrder = cuttingStock.generateRandom();
                //Perform recombination of the two parents, based on probability
                float probability = (float) Math.random();
                if (probability < recombinationRate) {
                    newOrder = crossover(parent1.order, parent2.order);
                }
                //Mutate this offspring, based on probability
                newOrder = mutate(newOrder);
                //Add to new population
                Order orderHolder = new Order(newOrder, cuttingStock.evaluate(newOrder));
                nextPopulation.add(orderHolder);
            }
            try {
                Collections.sort(nextPopulation);
            } catch (IllegalArgumentException ignored) {
            }
            //Set next population
            population = new ArrayList<>(nextPopulation);
        }
        Main.getInstance().Output.add(bestCost);
        Main.getInstance().doOnThreadCompletion();
    }

    /**
     * Initialises the initial population, and sorts them in order of fitness
     */
    private void initialise() {
        //Creates a population of populationSize
        for (int i = 0; i < populationSize; i++) {
            List<Integer> newOrder = cuttingStock.generateRandom();
            Float cost = cuttingStock.evaluate(newOrder);
            Order orderHolder = new Order(newOrder, cost);
            population.add(orderHolder);
        }
        //Sort Orders in fitness cost
        Collections.sort(population);
        bestOrder = population.get(0).order;
        bestCost = population.get(0).cost;
    }

    public abstract List<Integer> crossover(List<Integer> parent1, List<Integer> parent2);

    /**
     * @param order - Order-based Genotype that's going to be mutated
     * @return - mutated version of the Genotype
     */
    public List<Integer> mutate(List<Integer> order) {
        //Initialise variables
        int r1 = randomNumber(0, order.size());
        int r2 = randomNumber(0, order.size());
        float r3 = (float) Math.random();
        //Ensure that r1 and r2 are not the same index
        while (r1 == r2) {
            r1 = randomNumber(0, order.size());
            r2 = randomNumber(0, order.size());
        }
        //Perform mutation based on given probability
        if (r3 < mutationRate) {
            int indexHolder = order.get(r2);
            order.set(r2, order.get(r1));
            order.set(r1, indexHolder);
        }
        return order;
    }

    /**
     * @param min - Minimum value for the random number, Inclusive
     * @param max - Maximum value for the random number, Exclusive
     * @return - returns a random int
     */
    public int randomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    /**
     * Nested class, allows each order to have its respective cost
     * so that the population can be ordered in terms of fitness,
     * Only used in this class.
     */
    private static class Order implements Comparable<Order> {
        //Genotype of this particular solution
        public List<Integer> order;
        //The fitness of this solution
        public Float cost;

        public Order(List<Integer> order, Float cost) {
            this.order = order;
            this.cost = cost;
        }

        @Override
        public int compareTo(Order o) {
            return (int) (this.cost - o.cost);
        }
    }
}
