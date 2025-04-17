import org.example.Graph;
import org.example.Helper;
import org.example.ShortestPath;
import org.example.TestResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DijkstraTest {
    private static final double DELTA = 1e-6;

    @Test
    void simpleGraph() {
        System.out.println("============================================================Simple Graph Test================================================");
        int source = 0;
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 2, 5);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 1, 3);
        graph.addEdge(2, 3, 8);
        graph.addEdge(2, 4, 2);
        graph.addEdge(3, 4, 4);
        graph.addEdge(4, 3, 6);

        Integer[] parent = new Integer[5];
        double[] expectedCost = {0.0, 8.0, 5.0, 9.0, 7.0};
        double[] costOptimal = new double[5];
        double[] costNormal = new double[5];

        TestResult testResultNormalDijkstra = Helper.testAlgorithm(graph, source, costNormal, parent, "dijkstra");
        TestResult testResultOptimalDijkstra = Helper.testAlgorithm(graph, source, costOptimal, parent, "optimalDijkstra");

        assertArrayEquals(expectedCost, costNormal, DELTA);
        assertArrayEquals(expectedCost, costOptimal, DELTA);

        System.out.println("Using Optimal Dijkstra => " + testResultOptimalDijkstra.stringTime);
        System.out.println("Using Normal Dijkstra => " + testResultNormalDijkstra.stringTime);


    }

//
    @Test
    void disconnectedGraph() {
        System.out.println("======================================================= Disconnected Graph Test ==============================================");
        int source = 0;
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 3);

        Integer[] parent = new Integer[5];
        double[] costOptimal = new double[5];
        double[] costNormal = new double[5];
        TestResult testResultOptimalDijkstra = Helper.testAlgorithm(graph, source, costOptimal, parent, "optimalDijkstra");
        TestResult testResultNormalDijkstra = Helper.testAlgorithm(graph, source, costNormal, parent, "dijkstra");


        double[] expectedCost = {0.0, 1.0, 3.0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY};
        assertArrayEquals(expectedCost, costNormal, DELTA);
        assertArrayEquals(expectedCost, costOptimal, DELTA);

        System.out.println("Using Optimal Dijkstra => " + testResultOptimalDijkstra.stringTime);
        System.out.println("Using Normal Dijkstra => " + testResultNormalDijkstra.stringTime);

        for(int i = 0; i < 5; i++) {
            if (costNormal[i] == Double.POSITIVE_INFINITY) {
                System.out.println("Node "+i+" Unreachable from Source "+source);
            }
        }
    }

    @Test
    void graphWithCycle() {
        System.out.println("================================================= Graph with Cycle Test ===================================================================");
        int source = 0;
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 2);
        graph.addEdge(2, 3, 3);
        graph.addEdge(3, 0, 4);

        Integer[] parent = new Integer[4];
        double[] costOptimal = new double[4];
        double[] costNormal = new double[4];
        TestResult testResultOptimalDijkstra = Helper.testAlgorithm(graph, source, costOptimal, parent, "optimalDijkstra");
        TestResult testResultNormalDijkstra = Helper.testAlgorithm(graph, source, costNormal, parent, "dijkstra");

        double[] expectedCost = {0.0, 1.0, 3.0, 6.0};
        assertArrayEquals(expectedCost, costNormal, DELTA);
        assertArrayEquals(expectedCost, costOptimal, DELTA);

        System.out.println("Using Optimal Dijkstra => " + testResultOptimalDijkstra.stringTime);
        System.out.println("Using Normal Dijkstra => " + testResultNormalDijkstra.stringTime);

        for(int i = 0; i < 4; i++) {
            System.out.println("Distance From Source to " + i + " Equal = " + costNormal[i]);
            ShortestPath.printPath(ShortestPath.getPath(0, i, parent));
        }
    }

    @Test
    void APSP() {
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


        double totalTimeNormal = 0;
        double totalTimeOptimal = 0;

        for(int source = 0; source < 5; source++) {
            Integer[] parent = new Integer[5];
            double[] costOptimal = new double[5];
            double[] costNormal = new double[5];
            TestResult testResultNormalDijkstra = Helper.testAlgorithm(graph, source, costNormal, parent, "dijkstra");
            TestResult testResultOptimalDijkstra = Helper.testAlgorithm(graph, source, costOptimal, parent, "optimalDijkstra");
            assertArrayEquals(expectedCosts[source], costNormal, DELTA, "Failed at Source "+source);
            assertArrayEquals(expectedCosts[source], costOptimal, DELTA, " (optimal)");

            totalTimeNormal += testResultNormalDijkstra.spendTime;
            totalTimeOptimal += testResultOptimalDijkstra.spendTime;

            System.out.println("From source " + source + ":");
            System.out.println("Time Using Optimal Dijkstra => " + testResultOptimalDijkstra.stringTime);
            System.out.println("Time Using Normal Dijkstra => " + testResultNormalDijkstra.stringTime);


        }

        System.out.println("Total time for all-pairs Using Normal Dijkstra: " + totalTimeNormal + " ms");
        System.out.println("Total time for all-pairs Using Optimal Dijkstra: " + totalTimeOptimal + " ms");
    }

    @Test
    void graphWithUnweightedEdges() {
        System.out.println("============================================================= Unweighted Edges Test =======================================================");
        int source = 0;
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 0);
        graph.addEdge(1, 2, 0);
        graph.addEdge(2, 3, 0);
        graph.addEdge(0, 3, 1);

        Integer[] parent = new Integer[4];
        double[] costOptimal = new double[4];
        double[] costNormal = new double[4];

        TestResult testResultNormalDijkstra = Helper.testAlgorithm(graph, source, costNormal, parent, "dijkstra");
        TestResult testResultOptimalDijkstra = Helper.testAlgorithm(graph, source, costOptimal, parent, "optimalDijkstra");

        double[] expectedCost = {0.0, 0.0, 0.0, 0.0};
        assertArrayEquals(expectedCost, costNormal, DELTA);
        assertArrayEquals(expectedCost, costOptimal, DELTA);

        System.out.println("Using Optimal Dijkstra => " + testResultOptimalDijkstra.stringTime);
        System.out.println("Using Normal Dijkstra => " + testResultNormalDijkstra.stringTime);

    }

    @Test
    void graphWithNegativeEdges(){
        System.out.println("========================================================== Graph with Negative Edges Test ===========================================================");
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

        Integer[] parent = new Integer[6];
        double[] costOptimal = new double[6];
        double[] costNormal = new double[6];

        TestResult testResultNormalDijkstra = Helper.testAlgorithm(graph, source, costNormal, parent, "dijkstra");
        TestResult testResultOptimalDijkstra = Helper.testAlgorithm(graph, source, costOptimal, parent, "optimalDijkstra");

        assertTrue(graph.containNegativeEdge);

        System.out.println("Using Optimal Dijkstra => " + testResultOptimalDijkstra.stringTime);
        System.out.println("Using Normal Dijkstra => " + testResultNormalDijkstra.stringTime);

    }
    @Test
    void largeGraphTest() {
        System.out.println("Large Graph Test");
        int size = 10000;
        int source = 0;
        Graph graph = new Graph(size);

        for(int i = 0; i < size-1; i++) {
            for(int j = 1; j <= 10 && i+j < size; j++) {
                graph.addEdge(i, i+j, j);
            }
        }


        Integer[] parent = new Integer[size];
        double[] costOptimal = new double[size];
        double[] costNormal = new double[size];

        TestResult testResultNormalDijkstra = Helper.testAlgorithm(graph, source, costNormal, parent, "dijkstra");
        TestResult testResultOptimalDijkstra = Helper.testAlgorithm(graph, source, costOptimal, parent, "optimalDijkstra");

        System.out.println("Using Normal Dijkstra => " + testResultNormalDijkstra.stringTime);
        System.out.println("Using Optimal Dijkstra => " + testResultOptimalDijkstra.stringTime);

    }


}