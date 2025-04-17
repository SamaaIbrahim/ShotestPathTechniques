import org.example.Graph;
import org.example.Helper;
import org.example.TestResult;
import org.junit.jupiter.api.Test;

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

    double[][] cost = new double[size][size];
    double totalTimeNormal = 0;
    double totalTimeOptimal = 0;
    double totalTimeBellman = 0;

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


    }
    Integer[][] predecessors = new Integer[size][size];
    TestResult testFloyd = Helper.testAlgorithm(graph,cost,predecessors);
    System.out.println("Total time for all-pairs Using Normal Dijkstra: " + totalTimeNormal + " ms");
    System.out.println("Total time for all-pairs Using Optimal Dijkstra: " + totalTimeOptimal + " ms");
    System.out.println("Total time for all-pairs Using Bellman-ford: " + totalTimeBellman + " ms");
    System.out.println("Total time for all-pairs Using Floyd: " + testFloyd.spendTime + " ms");



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
        Integer[] parentBell = new Integer[size];
        double[] costBell = new double[size];
        double[][] costFloyd = new double[size][size];
        Integer[][] predecessors = new Integer[size][size];
        TestResult testResultNormalDijkstra = Helper.testAlgorithm(graph, source, costNormal, parent, "dijkstra");
        TestResult testResultOptimalDijkstra = Helper.testAlgorithm(graph, source, costOptimal, parent, "optimalDijkstra");
        TestResult testResultBellman = Helper.testAlgorithm(graph, source, costBell, parentBell, "bellman");
        TestResult testResultFloyd = Helper.testAlgorithm(graph,costFloyd,predecessors);
        System.out.println("Total time for Large graph Using Normal Dijkstra: " + testResultNormalDijkstra.spendTime + " ms");
        System.out.println("Total time for Large graph Using Optimal Dijkstra: " + testResultOptimalDijkstra .spendTime+ " ms");
        System.out.println("Total time for Large graph Using Bellman-ford: " + testResultBellman.spendTime+ " ms");
        System.out.println("Total time for Large graph Using Floyd: " + testResultFloyd.spendTime + " ms");

    }


@Test
void UnWeightEdges(){

}
}
