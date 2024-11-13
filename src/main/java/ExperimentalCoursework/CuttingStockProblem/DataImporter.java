package ExperimentalCoursework.CuttingStockProblem;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Responsible for importing the required data so that different solutions can be evaluated in
 * the CuttinStock class
 */
public class DataImporter {
    private final String PATH = "src/main/java/ExperimentalCoursework/CuttingStockProblem/Input.txt";
    private final String TEST_PATH = "src/main/java/ExperimentalCoursework/CuttingStockProblem/TestInput.txt";
    private Scanner reader;

    //Constructing Variables
    public Map<Integer, Float> stockLengths; //K - Stock Length, V - Cost
    public Map<Integer, Integer> pieceLengths; //K - Required Piece Length, V - Quantities

    public DataImporter() {
        try {
            reader = new Scanner(new File(PATH));
            //Construct Variables
            stockLengths = new LinkedHashMap<Integer, Float>();
            pieceLengths = new HashMap<Integer, Integer>();
            //Skip first 2 lines
            reader.nextLine();
            reader.nextLine();
            //Importing Data
            readInStockLengthsAndCosts();
            readInPieceLengthsAndQuantities();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Reads in the available Stock Lengths and Costs that the final solution must be made
     *  up of from the Input.txt file
     */
    private void readInStockLengthsAndCosts() {
        String stockInput = reader.nextLine();
        String stockPriceInput = reader.nextLine();
        //Filter Input Strings
        List<Integer> stockInputList = formatInteger(stockInput);
        List<Float> stockPriceList = formatFloat(stockPriceInput);
        //create Hashmap
        for (int i = 0; i < stockInputList.size(); i++) {
            stockLengths.put(stockInputList.get(i), stockPriceList.get(i));
        }
    }

    /**
     * Reads in the required Piece Lengths and Quantities from the Input.txt file
     */
    private void readInPieceLengthsAndQuantities() {
        String pieceInput = reader.nextLine();
        String pieceQuantityInput = reader.nextLine();
        //Filter Input Settings
        List<Integer> pieceList = formatInteger(pieceInput);
        List<Integer> pieceQuantityList = formatInteger(pieceQuantityInput);
        //create Hashmap
        for (int i = 0; i < pieceList.size(); i++) {
            pieceLengths.put(pieceList.get(i), pieceQuantityList.get(i));
        }
    }

    //Helper Function
    private List<Float> formatFloat(String s) {
        s = s.substring(s.indexOf(":") + 1);
        s = s.replaceAll("\\s", "");
        String[] sList = s.split(",");

        List<Float> out = new ArrayList<Float>();
        return out = Arrays.stream(sList).map(Float::valueOf).toList();
    }

    //Helper Function
    private List<Integer> formatInteger(String s) {
        s = s.substring(s.indexOf(":") + 1);
        s = s.replaceAll("\\s", "");
        String[] sList = s.split(",");

        List<Integer> out = new ArrayList<Integer>();
        return out = Arrays.stream(sList).map(Integer::valueOf).toList();
    }
}




