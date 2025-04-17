import org.example.Graph;
import org.example.Helper;
import org.example.ShortestPath;
import org.example.TestResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FloydTest {
    private static final double DELTA = 1e-6;

    @Test
    void SPP(){
        System.out.println("================================================ SSP TEST ====================================================");
        int source = 0;
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 6);
        graph.addEdge(0, 2, 7);
        graph.addEdge(1, 2, 8);
        graph.addEdge(1, 3, 5);
        graph.addEdge(1, 4, -4);
        graph.addEdge(2, 3, -3);
        graph.addEdge(2, 4, 9);
        graph.addEdge(3, 1, -2);
        graph.addEdge(4, 0, 2);
        graph.addEdge(4, 3, 7);
        double[][] cost = new double[5][5];
        Integer[][] predecessors = new Integer[5][5];
        TestResult testResult = Helper.testAlgorithm(graph,cost,predecessors);
        double[] expectedCost = {0.0, 2.0, 7.0, 4.0, -2.0};
        assertArrayEquals(expectedCost, cost[0], DELTA);

        System.out.println(testResult.stringTime);
    }
    @Test
    void APSP(){ // All pair Shortest Path
        int source = 0;
        System.out.println("============================================= APSP Test ===========================================================");
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 2);
        graph.addEdge(0, 3, 8);
        graph.addEdge(1, 2, 3);
        graph.addEdge(1, 3, 2);
        graph.addEdge(1, 4, 3);
        graph.addEdge(2, 4, 1);
        graph.addEdge(3, 2, 1);
        graph.addEdge(3, 4, 4);


        double[][] expectedCosts = {
                {0.0, 2.0, 5.0, 4.0, 5.0}, // from node 0
                {Double.POSITIVE_INFINITY, 0.0, 3.0, 2.0, 3.0}, // from node 1
                {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0.0, Double.POSITIVE_INFINITY, 1.0}, // from node 2
                {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 1.0, 0.0, 2.0}, // from node 3
                {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0.0}// from node 4
        };
        double[][] cost = new double[5][5];
        Integer[][] predecessors = new Integer[5][5];
        TestResult testResult = Helper.testAlgorithm(graph,cost,predecessors);
        assertArrayEquals(expectedCosts,cost);
        System.out.println(testResult.stringTime);


    }
    @Test
    void negativeCycleDetection() {
        System.out.println("===================================================== Graph with Negative Cycle Test ==================================================================");
        int source = 0;
        Graph graph = new Graph(6);
        graph.addEdge(0, 1, 5);
        graph.addEdge(0, 2, 9);
        graph.addEdge(1, 2, 2);
        graph.addEdge(1, 3, 6);
        graph.addEdge(2, 3, 3);
        graph.addEdge(2, 4, -2);
        graph.addEdge(3, 5, 1);
        graph.addEdge(4, 5, 4);
        graph.addEdge(4, 1, -1);

        double[][] cost = new double[6][6];
        Integer[][] predecessors = new Integer[6][6];
        TestResult testResult = Helper.testAlgorithm(graph,cost,predecessors);
        assertTrue(graph.containNegativeCycle);

        System.out.println(testResult.stringTime);
    }


}
