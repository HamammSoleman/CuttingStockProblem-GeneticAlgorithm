package ExperimentalCoursework.CuttingStockProblem;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CuttingStockTest {

    @Test
    void Debugging() {
        var cuttingStock = new CuttingStock();
        List<Integer> randomOrder = cuttingStock.generateRandom();
        System.out.println(cuttingStock.evaluate(randomOrder));
    }

    @Test
    void cuttingStockEvaluationCheck() {
        var cuttingStock = new CuttingStock();
        List<Integer> testOrder = Arrays.asList(20, 20, 20, 40, 42, 55, 63, 52);
        assertEquals(31.5f,cuttingStock.evaluate(testOrder));
    }

    @Test
    void generateRandomLengthIsCorrect() {
        var cuttingStock = new CuttingStock();
        assertEquals(395, cuttingStock.generateRandom().size());
    }
}