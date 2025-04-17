import org.example.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComparisonTest {

@Test
void APSP(){
    System.out.println("============================================================= APSP TEST =========================================================================");
    int size = 5;
    System.out.println("Graph Size : " + size);
    Graph graph = new Graph(size);
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

    double[][] costFloyd = new double[size][size];

    double totalTimeOptimal = 0;
    double totalTimeBellman = 0;
    Integer[][] predecessors = new Integer[size][size];
    TestResult testFloyd = Helper.testAlgorithm(graph,costFloyd,predecessors);
    for(int source = 0; source < size; source++) {
        Integer[] parent = new Integer[size];
        double[] costOptimal = new double[size];
        Integer[] parentBell = new Integer[size];
        double[] costBell = new double[size];

        TestResult testResultOptimalDijkstra = Helper.testAlgorithm(graph, source, costOptimal, parent, "optimalDijkstra");
        TestResult testResultBellman = Helper.testAlgorithm(graph, source, costBell, parentBell, "bellman");
        totalTimeOptimal += testResultOptimalDijkstra.spendTime;
        totalTimeBellman += testResultBellman.spendTime;
        assertArrayEquals(costBell ,costFloyd[source] , "Test Fails With at Source "+source +" BellMan-Ford");
        assertArrayEquals(costOptimal ,costFloyd[source],"Test Fails With at Source "+source +" Dijkstra-Optimal");


    }


    System.out.println("Total time for all-pairs Using Optimal Dijkstra: " + totalTimeOptimal + " ms");
    System.out.println("Total time for all-pairs Using Bellman-ford: " + totalTimeBellman + " ms");
    System.out.println("Total time for all-pairs Using Floyd: " + testFloyd.spendTime + " ms");




}
@Test
void SampleTestCase() //sheet ex 2
{
    System.out.println("===================================================== Graph with Negative Edges Test 2 ==================================================================");
        int size = 6;
    System.out.println("Graph Size : " + size);
        Graph graph = new Graph(size);
        graph.addEdge(1, 2, 3);
        graph.addEdge(1, 3, 8);
        graph.addEdge(1, 5, -4);
        graph.addEdge(2, 5, 7);
        graph.addEdge(2, 4, 1);
        graph.addEdge(3, 2, 4);
        graph.addEdge(3, 4, -5);
        graph.addEdge(4, 1, 2);
        graph.addEdge(5, 4, 6);

        double[][] costFloyd = new double[size][size];
        Integer[][] predecessors = new Integer[size][size];
        TestResult testResultFloyd = Helper.testAlgorithm(graph,costFloyd,predecessors);
        double totalTime=0;
        double singleSourceBelman = 0;
        for(int i =0 ;i<size;i++){
            Integer[] parentBell = new Integer[size];
            double[] costBell = new double[size];
            TestResult testResultBellman = Helper.testAlgorithm(graph, i, costBell, parentBell, "bellman");
            totalTime+=testResultBellman.spendTime;
            singleSourceBelman = Math.max(testResultBellman.spendTime,singleSourceBelman);
            assertArrayEquals(costBell,costFloyd[i],"Failed at source " + i);
            assertArrayEquals(parentBell,predecessors[i]);
        }


    System.out.println("Single Source");
    System.out.println("Total time for Large graph Using Bellman-ford: " + singleSourceBelman+ " ms");
    System.out.println("Total time for Large graph Using Floyd: " + testResultFloyd.spendTime + " ms");
    System.out.println("All Pairs");
    System.out.println("Total time for Large graph Using Bellman-ford: " + totalTime+ " ms");
    System.out.println("Total time for Large graph Using Floyd: " + testResultFloyd.spendTime + " ms");


    }

@Test
    void SSP(){
    System.out.println("=============================================================== SSP TEST ============================================================");
    int source = 0;
    int size = 5;
    System.out.println("Graph Size : " + size);
    Graph graph = new Graph(size);
    graph.addEdge(0, 1, 10);
    graph.addEdge(0, 2, 5);
    graph.addEdge(1, 3, 1);
    graph.addEdge(2, 1, 3);
    graph.addEdge(2, 3, 8);
    graph.addEdge(2, 4, 2);
    graph.addEdge(3, 4, 4);
    graph.addEdge(4, 3, 6);
    Integer[] parent = new Integer[size];
    double[] costOptimal = new double[size];
    Integer[] parentBell = new Integer[size];
    double[] costBell = new double[size];
    double[][] costFloyd = new double[size][size];
    Integer[][] predecessors = new Integer[size][size];

    TestResult testResultOptimalDijkstra = Helper.testAlgorithm(graph, source, costOptimal, parent, "optimalDijkstra");
    TestResult testResultBellman = Helper.testAlgorithm(graph, source, costBell, parentBell, "bellman");
    TestResult testResultFloyd = Helper.testAlgorithm(graph,costFloyd,predecessors);

    System.out.println("Total time for Single-Source Using Dijkstra: " + testResultOptimalDijkstra .spendTime+ " ms");
    System.out.println("Total time for Single-Source Using Bellman-ford: " + testResultBellman.spendTime+ " ms");
    System.out.println("Total time for Single-Source Using Floyd: " + testResultFloyd.spendTime + " ms");


}
    @Test
    void largeGraphTest() {
        System.out.println("===================================================== Large Graph Test ======================================================================");
        int size = 1000;
        System.out.println("Graph Size : " + size);

        Graph graph = new Graph(size);

        for(int i = 0; i < size-1; i++) {
            for(int j = 1; j <= 10 && i+j < size; j++) {
                graph.addEdge(i, i+j, j);
            }
        }

        double[][] costFloyd = new double[size][size];
        Integer[][] predecessors = new Integer[size][size];
        TestResult testResultFloyd = Helper.testAlgorithm(graph,costFloyd,predecessors);
        double totalTimeOptimal = 0;
        double totalTimeBellman = 0;
        double singleSourceOptimal = 0;
        double singleSourceBelman = 0;

        for(int source =  0 ; source < size; source++){
            Integer[] parent = new Integer[size];
            double[] costOptimal = new double[size];
            Integer[] parentBell = new Integer[size];
            double[] costBell = new double[size];
            TestResult testResultOptimalDijkstra = Helper.testAlgorithm(graph, source, costOptimal, parent, "optimalDijkstra");
            TestResult testResultBellman = Helper.testAlgorithm(graph, source, costBell, parentBell, "bellman");
            totalTimeOptimal += testResultOptimalDijkstra.spendTime;
            totalTimeBellman += testResultBellman.spendTime;
            singleSourceOptimal = Math.max(singleSourceOptimal,testResultOptimalDijkstra.spendTime);
            singleSourceBelman = Math.max(singleSourceBelman,testResultBellman.spendTime);
            assertArrayEquals(costBell ,costFloyd[source] , "Test Fails With at Source "+source +" BellMan-Ford");
            assertArrayEquals(costOptimal ,costFloyd[source],"Test Fails With at Source "+source +" Dijkstra");

        }
        System.out.println("Single Source");
        System.out.println("Total time for Large graph Using Dijkstra: " + singleSourceOptimal + " ms");
        System.out.println("Total time for Large graph Using Bellman-ford: " + singleSourceBelman+ " ms");
        System.out.println("Total time for Large graph Using Floyd: " + testResultFloyd.spendTime + " ms");
        System.out.println("All Pairs");
        System.out.println("Total time for Large graph Using Dijkstra: " + totalTimeOptimal + " ms");
        System.out.println("Total time for Large graph Using Bellman-ford: " + totalTimeBellman+ " ms");
        System.out.println("Total time for Large graph Using Floyd: " + testResultFloyd.spendTime + " ms");

    }
    @Test
    void NegativeEdge() { // sheet example 1
        System.out.println("===================================================== Graph with Negative Edges Test ==================================================================");
        int source = 0;
        Graph graph = new Graph(7);
        System.out.println("Graph Size : " + 7);
        graph.addEdge(0, 1, 2.0);
        graph.addEdge(0, 2, 7.0);
        graph.addEdge(0, 4, 12.0);
        graph.addEdge(2, 1, 3.0);
        graph.addEdge(1, 3, 2.0);
        graph.addEdge(2, 3, -1.0);
        graph.addEdge(2, 4, 2.0);
        graph.addEdge(3, 5, 2.0);
        graph.addEdge(4, 6, -7.0);
        graph.addEdge(5, 6, 2.0);
        graph.addEdge(4, 0, -4.0);
        graph.addEdge(6, 3, 1.0);

        double[][] costBellman = new double[7][7];
        double[][] costFloyd = new double[7][7];
        Integer[][] precBellman = new Integer[7][7];
        Integer[][] precFloyd = new Integer[7][7];

        TestResult floydTestResult = Helper.testAlgorithm(graph,costFloyd, precFloyd);
        double totalTime=0;
        double singleSourceBelman = 0;
        for(int i = 0;i<7;i++){
            TestResult test =  Helper.testAlgorithm(graph,i,costBellman[i],precBellman[i],"bellman");
            totalTime+=test.spendTime;
            singleSourceBelman = Math.max(singleSourceBelman,test.spendTime);
        }
        for(int i=0;i<7;i++){
            assertArrayEquals(costBellman[i],costFloyd[i]);
            assertArrayEquals(precBellman[i],precFloyd[i]);
        }
        System.out.println("Single Source");
        System.out.println("Total time for Large graph Using Bellman-ford: " + singleSourceBelman+ " ms");
        System.out.println("Total time for Large graph Using Floyd: " + floydTestResult.spendTime + " ms");
        System.out.println("All Pairs");
        System.out.println("Total time for Large graph Using Bellman-ford: " + totalTime+ " ms");
        System.out.println("Total time for Large graph Using Floyd: " + floydTestResult.spendTime + " ms");
    }


    @Test
    void correctnessComaparsion(){
        System.out.println("===================================================== Large Graph Correctness Test ==================================================================");
        System.out.println("Graph Size : " + 15);
        double[][] adjMatrix = {
                {0, 32, 3, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 3, 0, 0, 2, 4, 0, 0, 0, 0, 0, 0, 3, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 3},
                {0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 2, 0, 43, 0, 0, 0, 0, 0, 2, 55, 0, 3, 0},
                {0, 0, 0, 0, 0, 0, 3, 0, 2, 5, 0, 0, 0, 0, 0},
                {3, 2, 0, 0, 0, 0, 0, 2, 0, 1, 0, 0, 0, 0, 0},
                {22, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 2, 32, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 35, 32, 0},
                {1, 2, 0, 0, 0, 4, 0, 0, 33, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 1, 0, 2, 0, 0}
        };


        double[][] cost = {
                {0, 7, 3, 9, 4, 2, 5, 5, 5, 6, 3, 60, 9, 6, 7},
                {11, 0, 14, 10, 5, 13, 16, 13, 11, 12, 9, 71, 10, 7, 8},
                {11, 10, 0, 12, 7, 5, 8, 8, 8, 9, 6, 63, 12, 9, 10},
                {4, 3, 7, 0, 2, 4, 7, 7, 7, 8, 5, 62, 3, 4, 5},
                {6, 7, 9, 5, 0, 8, 11, 8, 6, 7, 4, 66, 5, 2, 3},
                {6, 5, 5, 7, 2, 0, 3, 3, 3, 4, 1, 58, 7, 4, 5},
                {7, 6, 2, 7, 3, 7, 0, 6, 4, 5, 2, 55, 7, 3, 5},
                {5, 4, 5, 10, 6, 7, 3, 0, 2, 3, 5, 58, 10, 6, 8},
                {3, 2, 6, 12, 7, 5, 5, 2, 0, 1, 6, 60, 12, 8, 10},
                {22, 29, 25, 31, 26, 24, 27, 27, 27, 0, 25, 82, 31, 28, 29},
                {5, 4, 8, 6, 1, 7, 7, 4, 2, 3, 0, 62, 6, 3, 4},
                {8, 7, 11, 9, 4, 10, 10, 7, 5, 6, 3, 0, 9, 6, 7},
                {1, 2, 4, 10, 5, 3, 6, 6, 6, 7, 4, 61, 0, 7, 8},
                {5, 6, 8, 4, 4, 7, 10, 7, 5, 6, 3, 65, 4, 0, 2},
                {3, 4, 6, 2, 2, 5, 8, 5, 3, 4, 1, 63, 2, 4, 0}
        };


        ArrayList<ArrayList<Edge>> adjList = new ArrayList<>(15);
        for(int i = 0;i<15;i++){
            adjList.add(new ArrayList<>());
        }
        for(int i =0 ;i<15;i++){
            for(int j =0 ;j<15;j++){
                if(adjMatrix[i][j] != 0){
                    adjList.get(i).add(new Edge(i,j,adjMatrix[i][j]));
                }
            }

        }
        Graph graph = new Graph(15,adjMatrix,adjList,false,false);

        double[][] costBellman = new double[15][15];
        double[][] costFloyd = new double[15][15];
        double[][] costDijkesrtra = new double[15][15];

        Integer[][] precBellman = new Integer[15][15];
        Integer[][] precFloyd = new Integer[15][15];
        Integer[][] precdijkestra = new Integer[15][15];

        TestResult floydTestResult = Helper.testAlgorithm(graph,costFloyd, precFloyd);
        double totalTimeOptimal = 0;
        double totalTimeBellman = 0;
        double singleSourceOptimal = 0;
        double singleSourceBelman = 0;
        for(int i = 0;i<15;i++){
            TestResult test =  Helper.testAlgorithm(graph,i,costBellman[i],precBellman[i],"bellman");
            totalTimeBellman+=test.spendTime;
            singleSourceBelman =  Math.max(singleSourceBelman,test.spendTime);
            TestResult test2  = Helper.testAlgorithm(graph,i,costDijkesrtra[i],precdijkestra[i],"optimalDijkstra");
            totalTimeOptimal+= test2.spendTime;
            singleSourceOptimal = Math.max(singleSourceOptimal,test2.spendTime);
        }
        for(int i=0;i<15;i++){
            assertArrayEquals(costBellman[i],cost[i]);
            assertArrayEquals(costDijkesrtra[i],cost[i]);
            assertArrayEquals(costFloyd[i],cost[i]);

        }
        System.out.println("Single Source");
        System.out.println("Total time for Large graph Using Dijkstra: " + singleSourceOptimal + " ms");
        System.out.println("Total time for Large graph Using Bellman-ford: " + singleSourceBelman+ " ms");
        System.out.println("Total time for Large graph Using Floyd: " + floydTestResult.spendTime + " ms");
        System.out.println("All Pairs");
        System.out.println("Total time for Large graph Using Dijkstra: " + totalTimeOptimal + " ms");
        System.out.println("Total time for Large graph Using Bellman-ford: " + totalTimeBellman+ " ms");
        System.out.println("Total time for Large graph Using Floyd: " + floydTestResult.spendTime + " ms");

    }
}
