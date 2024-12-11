# Cutting Stock Optimisation

## Problem Overview  
Industries such as metal, wood, glass, and paper face a recurring challenge in optimally processing  
incoming orders that require cutting materials from standard stock lengths. These orders may consist  
of various piece sizes and quantities, and the goal is to minimize waste, inventory costs, and holding  
time while meeting order requirements. 

### Key Challenges  
1. **Order Complexity**:  
   - Daily operations often involve processing tens to thousands of orders, each requiring multiple pieces of varying lengths and quantities.  
2. **Optimisation**:  
   - The solution must minimize wastage and cost while ensuring operational efficiency.  
3. **Multiple Stock Lengths**:  
   - When multiple stock lengths are available, determining the optimal way to cut pieces becomes a combinatorial optimisation problem.  

### Example Scenario  

A request order:  
- **3 different piece lengths**: `{20, 30, 25}`  
- **Associated quantities**: `{5, 5, 7}`  

Stock lengths available:  
- **Sizes**: `{50, 80, 100}`  
- **Costs**: `{100, 175, 250}`  

The solution must determine the optimal combination of Stock cuts to minimize the material usage and cost while fulfilling the order.  
The **TestInput.txt** file located in src/main/java/ExperimentalCoursework , is the training data the model was trained on,
And the **Input.txt** file is the test data and actual given scenario that needs an optimal solution.

## Solution Overview  
This project implements a genetic algorithm with two crossover methods to address the **cutting stock problem**:  
- The base genetic algorithm that creates the initial population, mutates and runs the algorithm can be found in GeneticAlgorithm folder.
- Its sub-class crossover implementations can also be found in the GeneticAlgorithm folder.  
- The CuttingStock class is used to evaluate the fitness of each individual solution; it also ensures that they take the right form. 
