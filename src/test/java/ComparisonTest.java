import org.example.Graph;
import org.example.Helper;
import org.example.ShortestPath;
import org.example.TestResult;
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
    double totalTimeNormal = 0;
    double totalTimeOptimal = 0;
    double totalTimeBellman = 0;
    Integer[][] predecessors = new Integer[size][size];
    TestResult testFloyd = Helper.testAlgorithm(graph,costFloyd,predecessors);
    for(int source = 0; source < size; source++) {
        Integer[] parent = new Integer[size];
        double[] costOptimal = new double[size];
        double[] costNormal = new double[size];
        Integer[] parentBell = new Integer[size];
        double[] costBell = new double[size];
        TestResult testResultNormalDijkstra = Helper.testAlgorithm(graph, source, costNormal, parent, "dijkstra");
        TestResult testResultOptimalDijkstra = Helper.testAlgorithm(graph, source, costOptimal, parent, "optimalDijkstra");
        TestResult testResultBellman = Helper.testAlgorithm(graph, source, costNormal, parent, "bellman");
        totalTimeNormal += testResultNormalDijkstra.spendTime;
        totalTimeOptimal += testResultOptimalDijkstra.spendTime;
        totalTimeBellman += testResultBellman.spendTime;
        assertArrayEquals(costBell ,costFloyd[source] , "Test Fails With at Source "+source +" BellMan-Ford");
        assertArrayEquals(costNormal ,costFloyd[source],"Test Fails With at Source "+source +" Dijkstra-Normal");
        assertArrayEquals(costOptimal ,costFloyd[source],"Test Fails With at Source "+source +" Dijkstra-Optimal");


    }

    System.out.println("Total time for all-pairs Using Normal Dijkstra: " + totalTimeNormal + " ms");
    System.out.println("Total time for all-pairs Using Optimal Dijkstra: " + totalTimeOptimal + " ms");
    System.out.println("Total time for all-pairs Using Bellman-ford: " + totalTimeBellman + " ms");
    System.out.println("Total time for all-pairs Using Floyd: " + testFloyd.spendTime + " ms");




}
@Test
void SampleTestCase() //sheet ex 2
{
        int size = 6;
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
        for(int i =0 ;i<size;i++){
            Integer[] parentBell = new Integer[size];
            double[] costBell = new double[size];
            TestResult testResultBellman = Helper.testAlgorithm(graph, i, costBell, parentBell, "bellman");
            totalTime+=testResultBellman.spendTime;
            assertArrayEquals(costBell,costFloyd[i],"Failed at source " + i);
        }


        System.out.println("Total time for Test One Using Bellman-ford: " +totalTime+ " ms");
        System.out.println("Total time for Test One Using Floyd: " + testResultFloyd.spendTime + " ms");


    }

@Test
    void SSP(){
    System.out.println("=============================================================== SSP TEST ============================================================");
    int source = 0;
    int size = 5;
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
    double[] costNormal = new double[size];
    Integer[] parentBell = new Integer[size];
    double[] costBell = new double[size];
    double[][] costFloyd = new double[size][size];
    Integer[][] predecessors = new Integer[size][size];
    TestResult testResultNormalDijkstra = Helper.testAlgorithm(graph, source, costNormal, parent, "dijkstra");
    TestResult testResultOptimalDijkstra = Helper.testAlgorithm(graph, source, costOptimal, parent, "optimalDijkstra");
    TestResult testResultBellman = Helper.testAlgorithm(graph, source, costBell, parentBell, "bellman");
    TestResult testResultFloyd = Helper.testAlgorithm(graph,costFloyd,predecessors);
    System.out.println("Total time for Single-Source Using Normal Dijkstra: " + testResultNormalDijkstra.spendTime + " ms");
    System.out.println("Total time for Single-Source Using Optimal Dijkstra: " + testResultOptimalDijkstra .spendTime+ " ms");
    System.out.println("Total time for Single-Source Using Bellman-ford: " + testResultBellman.spendTime+ " ms");
    System.out.println("Total time for Single-Source Using Floyd: " + testResultFloyd.spendTime + " ms");


}
    @Test
    void largeGraphTest() {
        System.out.println("===================================================== Large Graph Test ======================================================================");
        int size = 1000;

        Graph graph = new Graph(size);

        for(int i = 0; i < size-1; i++) {
            for(int j = 1; j <= 10 && i+j < size; j++) {
                graph.addEdge(i, i+j, j);
            }
        }

        Integer[] parent = new Integer[size];
        double[] costOptimal = new double[size];
        double[] costNormal = new double[size];
        Integer[] parentBell = new Integer[size];
        double[] costBell = new double[size];
        double[][] costFloyd = new double[size][size];
        Integer[][] predecessors = new Integer[size][size];
        double totalTimeNormal = 0;
        double totalTimeOptimal = 0;
        double totalTimeBellman = 0;
        TestResult testResultFloyd = Helper.testAlgorithm(graph,costFloyd,predecessors);

        for(int source =  0 ; source < size; source++){
            TestResult testResultNormalDijkstra = Helper.testAlgorithm(graph, source, costNormal, parent, "dijkstra");
            TestResult testResultOptimalDijkstra = Helper.testAlgorithm(graph, source, costOptimal, parent, "optimalDijkstra");
            TestResult testResultBellman = Helper.testAlgorithm(graph, source, costBell, parentBell, "bellman");
            totalTimeNormal += testResultNormalDijkstra.spendTime;
            totalTimeOptimal += testResultOptimalDijkstra.spendTime;
            totalTimeBellman += testResultBellman.spendTime;
            assertArrayEquals(costBell ,costFloyd[source] , "Test Fails With at Source "+source +" BellMan-Ford");
            assertArrayEquals(costNormal ,costFloyd[source],"Test Fails With at Source "+source +" Dijkstra-Normal");
            assertArrayEquals(costOptimal ,costFloyd[source],"Test Fails With at Source "+source +" Dijkstra-Optimal");
        }


        System.out.println("Total time for Large graph Using Normal Dijkstra: " + totalTimeNormal+ " ms");
        System.out.println("Total time for Large graph Using Optimal Dijkstra: " + totalTimeOptimal + " ms");
        System.out.println("Total time for Large graph Using Bellman-ford: " + totalTimeBellman+ " ms");
        System.out.println("Total time for Large graph Using Floyd: " + testResultFloyd.spendTime + " ms");

    }
    @Test
    void NegativeEdge() { // sheet example 1
        System.out.println("===================================================== Graph with Negative Edges Test ==================================================================");
        int source = 0;
        Graph graph = new Graph(7);
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
        double bellmanTime = 0;
        for(int i = 0;i<7;i++){
            TestResult test =  Helper.testAlgorithm(graph,i,costBellman[i],precBellman[i],"bellman");
            bellmanTime+=test.spendTime;
        }
        for(int i=0;i<7;i++){
            assertArrayEquals(costBellman[i],costFloyd[i]);
            assertArrayEquals(precBellman[i],precFloyd[i]);
        }
        Helper.printResultComparsion(7,costBellman,costFloyd,precBellman,precFloyd);
        System.out.println("Total time for graph Using Bellman-ford: " + bellmanTime+ " ms");
        System.out.println("Total time for Large graph Using Floyd: " + floydTestResult.spendTime + " ms");
    }


    @Test
    void hello(){
        double[][] adjList = {
                {0, 32, 3, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 3, 0, 0, 2, 4, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 22, 3, 0, 0, 0},
                {0, 0, 0, 3, 3, 0, 0, 1, 0, 0, 0, 0, 0, 0, 2, 0},
                {43, 0, 0, 0, 0, 0, 2, 55, 0, 3, 0, 0, 0, 0, 0, 0},
                {0, 3, 0, 2, 5, 0, 0, 0, 0, 0, 3, 2, 0, 0, 0, 0},
                {0, 2, 0, 1, 0, 0, 0, 0, 0, 22, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 2, 32, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 35, 32, 0, 1, 2, 0},
                {0, 0, 0, 4, 0, 0, 33, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0},
                {0, 0, 0, 0, 0, 0, 3, 0, 2, 0, 0}
        };
//        ArrayList<ArrayList<E>>
//        Graph graph = new Graph(15,adjList,new ArrayList<>());
//        for(int i =0 ;i<15;i++)



    }
}
