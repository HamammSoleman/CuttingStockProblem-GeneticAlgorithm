package ExperimentalCoursework.GeneticAlgorithm;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeneticAlgorithmTest {

    @Test
    void debugging() {
        var orderOneGeneticAlgorithm = new orderOneCrossover(20, 50, 0.05f, 0.6f);
        orderOneGeneticAlgorithm.start();
    }

    @Test
    void debugOrderOneCrossover() {
        var orderOneCrossver = new orderOneCrossover(4, 20, 1, 1);
        List<Integer> order1 = Arrays.asList(1,2,3,4,4,5);
        List<Integer> order2 = new ArrayList<Integer>();
        order2.addAll(Arrays.asList(5, 4, 3, 4, 2, 1));
        orderOneCrossver.crossover(order1, order2);
    }

    @Test
    void checkIfPopulationSizeIsCorrect() {
        var geneticAlgorithm = new orderOneCrossover(4, 20, 1, 1);
        assertEquals(20, geneticAlgorithm.population.size());
    }

    @Test
    void mutationCheckNotEqual() {
        var geneticAlgorithm = new orderOneCrossover(4,20,1,1);
        List<Integer> testOrder = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(geneticAlgorithm.mutate(testOrder));
    }
}