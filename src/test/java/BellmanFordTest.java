import org.example.Graph;
import org.example.Helper;
import org.example.ShortestPath;
import org.example.TestResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BellmanFordTest {
    private static final double DELTA = 1e-6;

    @Test
    void simpleGraph() {
        System.out.println("===============================================Simple Graph Test================================================");
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

        double[] cost = new double[5];
        Integer[] parent = new Integer[5];
        TestResult testResult = Helper.testAlgorithm(graph, source, cost, parent, "bellman");

        double[] expectedCost = {0.0, 2.0, 7.0, 4.0, -2.0};
        assertArrayEquals(expectedCost, cost, DELTA);

        for(int i=0; i <5 ; i++){
            System.out.println("Distance From Source to "+ i+" Equal = "+ cost[i]);
            ShortestPath.printPath(ShortestPath.getPath(0, i, parent));
        }
        System.out.println(testResult.stringTime);
    }



    @Test
    void APSP() {
        System.out.println("============================================= APSP Test ===========================================================");
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 2);
        graph.addEdge(0, 2, 4);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 7);
        graph.addEdge(2, 3, 3);


        double[][] expectedCosts = {
                {0, 2, 3, 6},    // from node 0
                {Double.POSITIVE_INFINITY, 0, 1, 4},    // from node 1
                {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0, 3},    // from node 2
                {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0}     // from node 3
        };

        double totalTime = 0;
        for(int source = 0; source < 4; source++){
            double[] cost = new double[4];
            Integer[] parent = new Integer[4];
            TestResult testResult = Helper.testAlgorithm(graph, source, cost, parent, "bellman");
            assertArrayEquals(expectedCosts[source], cost, DELTA, "Failed at source node: " + source);
            totalTime += testResult.spendTime;

            System.out.println("From source " + source + ":");
            for(int i = 0; i < 4; i++) {
                System.out.println("  Distance to " + i + ": " + cost[i]);
            }
        }

        System.out.println("Total time for all-pairs Bellman-Ford: " + totalTime + " ms");
    }



    @Test
    void disconnectedGraph() {
        System.out.println("======================================================= Disconnected Graph Test ==============================================");
        int source = 0;
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 3);
        graph.addEdge(1, 0, 2);
        graph.addEdge(2, 3, 7);
        graph.addEdge(3, 4, 1);

        double[] cost = new double[5];
        Integer[] parent = new Integer[5];
        TestResult testResult = Helper.testAlgorithm(graph, source, cost, parent, "bellman");

        double[] expectedCost = {0.0, 3.0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY};
        assertArrayEquals(expectedCost, cost, DELTA);

        for(int i = 0; i < 5; i++) {
            System.out.println("Distance From Source to " + i + " Equal = " + cost[i]);
            if (cost[i] == Double.POSITIVE_INFINITY) {
                System.out.println("Node "+i+" Unreachable from Source "+source);
            }
        }

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

        double[] cost = new double[6];
        Integer[] parent = new Integer[6];
        TestResult testResult = Helper.testAlgorithm(graph, source, cost, parent, "bellman");
        assertTrue(graph.containNegativeCycle);


       System.out.println(testResult.stringTime);
    }

    @Test
    void graphWithUnweightedEdges() {
        System.out.println("============================================================= Unweighted Edges Test =======================================================");
        int source = 0;
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 0);
        graph.addEdge(1, 2, 0);
        graph.addEdge(2, 3, 0);
        graph.addEdge(0, 3, 5);

        double[] cost = new double[4];
        Integer[] parent = new Integer[4];
        TestResult testResult = Helper.testAlgorithm(graph, source, cost, parent, "bellman");

        double[] expectedCost = {0.0, 0.0, 0.0, 0.0};
        assertArrayEquals(expectedCost, cost, DELTA);

        for(int i = 0; i < 4; i++) {
            System.out.println("Distance From Source to " + i + " Equal = " + cost[i]);
            ShortestPath.printPath(ShortestPath.getPath(0, i, parent));
        }

        System.out.println(testResult.stringTime);
    }
}