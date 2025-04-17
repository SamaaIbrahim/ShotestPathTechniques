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
            graph.dijkestra(source, cost, parent);
        } else if (method.equals("bellman")) {
            graph.BellmanFord(source, cost, parent);
        }
        else if (method.equals("optimalDijkstra")){
            graph.optDijkestra(source, cost, parent);
        }
    }

    public static void printResultComparsion(int n,double[][]costBellman,double[][]costFloyd,Integer[][]precBellman,Integer[][]precFloyd){
        for(int i=0;i<n;i++){
            System.out.println("from " + i);
            System.out.println("Bellman");
            for(int j =0 ;j<n;j++){
                System.out.print("to : "+ j+" " + costBellman[i][j]+" ");
                ShortestPath.printPath(ShortestPath.getPath(i,j,precBellman[i]));
            }
            System.out.println("Floyd");
            for(int j =0 ;j<7;j++){
                System.out.print("to : "+ j+ " " + costBellman[i][j]+" ");
                ShortestPath.printPath(ShortestPath.getPath(i,j,precFloyd[i]));
            }
        }
    }

}
