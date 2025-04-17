package org.example;

public class Helper {

    public static TestResult testAlgorithm(Graph graph, double[][] cost, Integer[][] predecessors) {
        TestResult testResult = new TestResult();
        long startTime = System.nanoTime();
        graph.Floyd(cost, predecessors);
        long endTime = System.nanoTime();
        long nanoTime = (endTime - startTime);
        double microTime = ((double) (endTime - startTime)) / 10E3;
        double milliTime = ((double) (endTime - startTime)) / 10E6;
        testResult.spendTime = milliTime;
        testResult.stringTime = "{" + nanoTime + " ns, " + microTime + " micro, " + milliTime + " ms}";

        return testResult;
    }


    public static TestResult testAlgorithm(Graph graph, int source, double[] cost, Integer[] parent, String method) {
        TestResult testResult = new TestResult();
        long startTime = System.nanoTime();
        execute(graph, source, cost, parent, method);
        long endTime = System.nanoTime();
        long nanoTime = (endTime - startTime);
        double microTime = ((double) (endTime - startTime)) / 10E3;
        double milliTime = ((double) (endTime - startTime)) / 10E6;
        testResult.spendTime = milliTime;
        testResult.stringTime = "{" + nanoTime + " ns, " + microTime + " micro, " + milliTime + " ms}";

        return testResult;
    }


    private static void execute(Graph graph, int source, double[] cost, Integer[] parent, String method) {
        if (method.equals("dijkstra")) {
            graph.lazyDijkestra(source, cost, parent);
        } else if (method.equals("bellman")) {
            graph.BellmanFord(source, cost, parent);
        }
        else if (method.equals("optimalDijkstra")){
            graph.dijkestra(source, cost, parent);
        }
    }


}
