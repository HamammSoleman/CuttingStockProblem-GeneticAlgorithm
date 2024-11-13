package ExperimentalCoursework.CuttingStockProblem;

import com.sun.source.tree.Tree;

import java.util.*;

public class CuttingStock {

    //Stock piece lengths, along with their prices
    Map<Integer, Float> stockLengths;
    //Required piece lengths, along with the quantities required
    Map<Integer, Integer> requiredPieceLengths;

    public CuttingStock() {
        DataImporter dataImporter = new DataImporter();
        //Setting values and Constructing objects
        stockLengths = dataImporter.stockLengths;
        requiredPieceLengths = dataImporter.pieceLengths;
    }

    /**
     * Evaluates the give Order-Based Genotype, calculating its fitness level
     * Applies penalties to the novel variant of the solution
     * @param order - Genotype, order-based representation for the cutting stock problem
     * @return - Cost of the given Genotype, represents the Fitness level
     */
    public Float evaluate(List<Integer> order) {
        List<Integer> stockPiecesOrdered = new ArrayList<Integer>(stockLengths.keySet());
        List<Integer> stockPiecesRequired = new ArrayList<Integer>();
        //Pointer and Required length tracker
        int pointer = 0;
        int temporaryLength = 0;
        int penalty = 0;
        //Works out all the stock Pieces Required, calculated
        while (pointer <= order.size() - 1) {
            //traverse order, add them up until it's higher than the largest stock length
            if (temporaryLength > stockPiecesOrdered.get(0)) {
                pointer--;
                temporaryLength -= order.get(pointer);
                stockPiecesRequired.add(getSmallestLength(stockPiecesOrdered, temporaryLength));
                penalty += 0.1 * stockLengths.get(getSmallestLength(stockPiecesOrdered,temporaryLength));
                //reset counters
                temporaryLength = 0;
            }
            //Append length Tracker
            temporaryLength += order.get(pointer);
            //Handle Edge Cases, TO NOT CAUSE LOGIC ERROR
            if (pointer == order.size() - 1) {
                if (temporaryLength <= stockPiecesOrdered.get(0)) {
                    stockPiecesRequired.add(getSmallestLength(stockPiecesOrdered, temporaryLength));
                } else if (temporaryLength > stockPiecesOrdered.get(0)) {
                    temporaryLength -= order.get(pointer);
                    stockPiecesRequired.add(getSmallestLength(stockPiecesOrdered, temporaryLength));
                    temporaryLength = order.get(pointer);
                    stockPiecesRequired.add(getSmallestLength(stockPiecesOrdered, temporaryLength));
                }
            }
            pointer++;
        }

        //Fetches cost, and adds it to Final cost
        Float cost = 0f;
        for (Integer stockLength : stockPiecesRequired) {
            cost += stockLengths.get(stockLength);
        }
        return cost;
    }

    /**
     * @return - Generates a random order based on the required piece lengths
     */
    public List<Integer> generateRandom() {

        List<Integer> allRequiredPieces = new ArrayList<Integer>();

        for (Integer length : requiredPieceLengths.keySet()) {
            for (int i = 0; i < requiredPieceLengths.get(length);i++){
                allRequiredPieces.add(length);
            }
        }
        Collections.shuffle(allRequiredPieces);
        return allRequiredPieces;
    }

    /**
     * @param orderedStockPieces
     * @param currentLength
     * @return
     */
    private Integer getSmallestLength(List<Integer> orderedStockPieces, int currentLength) {
        //go back 1 order, start from the smallest length, until lengths isn't too short
        for (int i = orderedStockPieces.size() - 1; i >= 0; i--) {
            if (currentLength <= orderedStockPieces.get(i)) {
                return orderedStockPieces.get(i);
            }
        }
        return null;
    }
}
