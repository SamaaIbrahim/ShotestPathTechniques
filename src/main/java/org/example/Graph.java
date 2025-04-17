package org.example;

import java.io.*;
import java.util.*;

public class Graph {
    int n;

    double[][] adjMatrix;
    ArrayList<ArrayList<Edge>> edgeList;

    boolean containNegativeEdge;
    boolean containNegativeCycle;

    public Graph(int n, double[][] adjMatrix, ArrayList<ArrayList<Edge>> edgeList, boolean containNegativeEdge, boolean containNegativeCycle) {
        this.n = n;
        this.adjMatrix = adjMatrix;
        this.edgeList = edgeList;
        this.containNegativeEdge = containNegativeEdge;
        this.containNegativeCycle = containNegativeCycle;
    }

    public Graph(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
        int e;
        String [] token = line.split(" ");
        if(token.length!=2){
            throw new IllegalArgumentException();
        }else{
            try {
                n = Integer.parseInt(token[0]);
                e = Integer.parseInt(token[1]);
            }catch (Exception x){
                throw new IllegalArgumentException();
            }
        }
        adjMatrix = new double[n][n];
        edgeList = new ArrayList<>();
        for(int i =0; i<n; i++){
            edgeList.add(new ArrayList<>());
        }
        for(int i =0;i<e;i++){
            line = bufferedReader.readLine();
            token = line.split(" ");
            if(token.length!=3){
                throw new IllegalArgumentException();
            }else{
                try {
                    int from = Integer.parseInt(token[0]);
                    int to  = Integer.parseInt(token[1]);
                    double cost = Double.parseDouble(token[2]);
                    addEdge(from,to,cost);
                }catch (Exception x){
                    throw new IllegalArgumentException();
                }
            }

        }

    }
    public Graph(int n){
        this.n = n;
        this.adjMatrix = new double[n][n];
        this.edgeList = new ArrayList<>(n);
        for(int i = 0; i < n; i++){
            edgeList.add(new ArrayList<>());
        }
    }
    public void addEdge(int from,int to ,double cost){
        if(from >=n || to >= n || from < 0 || to < 0){
            throw new IllegalArgumentException();
        }
        if(cost < 0 ){
            containNegativeEdge = true;
        }
        adjMatrix[from][to] = cost;
        edgeList.get(from).add(new Edge(from,to,cost));

    }
    public void dijkestra(int s,double[] cost ,Integer[] parent){
        int n=cost.length;
        Arrays.fill(parent,null); // parent[i] = null -> unreached
        Arrays.fill(cost, Double.POSITIVE_INFINITY);
        cost[s]=0.0;
        Boolean[]Sure=new Boolean[n];
        Arrays.fill(Sure, false);

        for (int i=0;i<this.n;i++){
            //get the index with the smallest cost
            double minCost = Double.POSITIVE_INFINITY;
            int v=-1;
            for (int j = 0; j < n; j++) {
                if (!Sure[j] && cost[j] < minCost) {
                    minCost = cost[j];
                    v = j;
                }

            }
            if(v==-1) break;
            Sure[v]=true;
            for(Edge edge: this.edgeList.get(v)){
                int to=edge.to,from=edge.from;double weight=edge.cost;
                if(!Sure[to]&& weight+cost[from]<cost[to]) {
                    cost[to] = weight + cost[from];
                    parent[to]=from;
                }
            }
        }


    }
    public void optdijkestra(int s, double[] cost, Integer[] parent){
        int n=cost.length;
        Arrays.fill(parent,null); // parent[i] = null -> unreached
        Arrays.fill(cost, Double.POSITIVE_INFINITY);
        cost[s]=0.0;
        IndexPriorityQueue ipq=new IndexPriorityQueue(n);
        ipq.insertInPq(s,cost[s]);
        HashMap<Integer,Boolean> Sure=new HashMap<>();
        for(int i=0;i<n;i++){
            Sure.put(i,false);
        }

        while (!ipq.isEmpty()){
            int ver=ipq.poll();
            Sure.put(ver,true);
            for(Edge edge: this.edgeList.get(ver)){
                int to=edge.to,from=edge.from;double weight=edge.cost;
                if(!Sure.get(to)&& weight+cost[from]<cost[to]) {
                    cost[to] = weight + cost[from];
                    parent[to]=from;
                    if (ipq.contains(to)) {
                        ipq.change(to, cost[to]);
                    } else {
                        ipq.insertInPq(to, cost[to]);
                    }
                }
            }


        }}
    public boolean BellmanFord(int s,double[] cost,Integer[] parent){
        Arrays.fill(parent,null); // parent[i] = null -> unreached
        Arrays.fill (cost,Double.POSITIVE_INFINITY);
        cost[s]=0;
        for(int i = 0 ; i < n-1 ; i++){
            for( ArrayList<Edge> vertexEdge: this.edgeList){
                for(Edge e : vertexEdge){
                    int from = e.from;
                    int to =e.to;
                    double weight = e.cost;
                    if(  cost[from] + weight < cost[to]){// Relax Edge
                        cost[to]=cost[from]+weight;
                        parent[to]=from;

                    }

                }
            }
        }
        for(int i = 0 ; i < n-1 ; i++){
            for( ArrayList<Edge> vertexEdge: this.edgeList){
                for(Edge e : vertexEdge){
                    int from = e.from;
                    int to =e.to;
                    double weight = e.cost;
                    if( cost[from] + weight < cost[to]){
                        this.containNegativeCycle=true;
                        return true;

                    }

                }
            }
        }


        return false;
    }
    public boolean Floyd(double[][] cost,Integer[][] predecessors){
        //initialize
        for(Integer[] row : predecessors){
            Arrays.fill(row,null);
        }
        //setup
        for(int i = 0;i<n;i++){
            for(int j =0 ;j<n;j++){
                if(adjMatrix[i][j]!=0){
                    cost[i][j] = adjMatrix[i][j];
                    predecessors[i][j] = i;
                } else if (i==j) {
                    cost[i][j] = 0;

                } else{
                    cost[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }
        //floyd
        for(int k =0;k<n;k++){
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    if(cost[i][k]+cost[k][j] < cost[i][j]){
                        cost[i][j] = cost[i][k]+cost[k][j];
                        predecessors[i][j] = predecessors[k][j];//i,k
                    }
                }
            }
        }
        //detect negative cycle
        for(int k =0;k<n;k++){
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    if(cost[i][k]+cost[k][j] < cost[i][j]){
                        containNegativeCycle = true;
                        return false;
                    }
                }
            }
        }

        return true;
    }
    public static void DijkstraTest() {
        Graph g = new Graph(5);
//        g.addEdge(0, 1, 2);   // A -> B
//        g.addEdge(0, 2, 7);   // A -> C
//        g.addEdge(2, 1, 3);   // C -> B
//        g.addEdge(1, 3, 2);   // B -> D
//        g.addEdge(2, 3, -1);  // C -> D
//        g.addEdge(2, 4, 2);   // C -> E
//        g.addEdge(3, 5, 2);   // D -> F
//        g.addEdge(0, 4, 12);  // A -> E
//        g.addEdge(4, 0, -4);  // E -> A
//        g.addEdge(4, 6, -7);  // E -> G
//        g.addEdge(5, 6, 2);   // F -> G
//        g.addEdge(6, 3, 1);   // G -> D
//
        g.addEdge(0, 1, 10);
        g.addEdge(0, 4, 5);
        g.addEdge(1, 2, 1);
        g.addEdge(1, 4, 2);
        g.addEdge(2, 3, 4);
        g.addEdge(3, 0, 7);
        g.addEdge(4, 1, 3);
        g.addEdge(4, 2, 9);
        g.addEdge(4, 3, 2);
        double[] cost = new double[g.n];
        Integer[] parent = new Integer[g.n];
        g.dijkestra(0, cost, parent);
        System.out.println("using normal Dijkstra");

        for (int i=0;i<g.n;i++){
            System.out.println("the cost from 0 to "+i+" is "+cost[i]);
        }
        g.optdijkestra(0, cost, parent);
        System.out.println("using optimized Dijkstra");
        for (int i=0;i<g.n;i++){
            System.out.println("the cost from 0 to "+i+" is "+cost[i]);
        }
    }
    public  static  void BellmanFordTest(){
        int n = 4;
        Graph g = new Graph(n);

       /* // Add edges
        g.addEdge(0, 3, 2.0);  // 0 -> 3
        g.addEdge(0, 1, 4.0);  // 0 -> 1
        g.addEdge(4, 2, 8.0);  // 4 -> 2
        g.addEdge(1, 3, 5.0);  // 1 -> 3
        g.addEdge(3, 2, 1.0);  // 3 -> 2
        g.addEdge(2, 4, 3.0);  // 2 -> 4
        g.addEdge(3, 4, 7.0);  // 3 -> 4
        */

        g.addEdge(0, 1, 1);     // 0 → 1 (1)
        g.addEdge(1, 2, -1);    // 1 → 2 (-1)
        g.addEdge(2, 3, -1);    // 2 → 3 (-1)
        g.addEdge(3, 1, -1);    // 3 → 1 (-1) → forms negative cycle


        double[] cost2 = new double[n];
        Integer[] parent = new Integer[n];
        g.BellmanFord(0, cost2, parent);
        System.out.println( g.containNegativeCycle?("Graph has Negative Cycle"):("Graph does not have Negative Cycle"));
        if(!g.containNegativeCycle){
            for (int i = 0 ; i < n ;i ++ ){
                System.out.println("Distance From Source to "+ i+" Equal =  "+ cost2[i]);
                System.out.println("Parent of "+i+" Is = "+ parent[i]);
                ShortestPath.printPath (ShortestPath.getPath(0,i,parent));}
        }
    }
    public static void floydTest() {
        Graph g = new Graph(5);
        // Add more edges to make a comprehensive test case
        g.addEdge(0, 3, 2.0);  // 0 -> 3 with weight 2
        g.addEdge(0, 1, 4.0);  // 0 -> 1 with weight 4
        g.addEdge(4, 2, 8.0);  // 4 -> 2 with weight 8
        g.addEdge(1, 3, 5.0);  // 1 -> 3 with weight 5
        g.addEdge(3, 2, 1.0);  // 3 -> 2 with weight 1
        g.addEdge(2, 4, 3.0);  // 2 -> 4 with weight 3
        g.addEdge(3, 4, 7.0);  // 3 -> 4 with weight 7

        double[][] cost = new double[5][5];
        Integer[][] predecessors = new Integer[5][5];

        g.Floyd(cost, predecessors);

        System.out.println("Cost matrix after Floyd algorithm:");
        for(double[] a : cost) {
            System.out.println(Arrays.toString(a));
        }

        System.out.println("\nPredecessor matrix after Floyd algorithm:");
        for(Integer[] b : predecessors) {
            System.out.println(Arrays.toString(b));
        }

        System.out.println("\nExpected Cost matrix:");
        System.out.println("[0.0, 4.0, 3.0, 2.0, 6.0]");
        System.out.println("[Infinity, 0.0, 6.0, 5.0, 9.0]");
        System.out.println("[Infinity, Infinity, 0.0, Infinity, 3.0]");
        System.out.println("[Infinity, Infinity, 1.0, 0.0, 4.0]");
        System.out.println("[Infinity, Infinity, 8.0, Infinity, 0.0]");

        System.out.println("\nExpected Predecessor matrix:");
        System.out.println("[null, 0, 3, 0, 2]");
        System.out.println("[null, null, 3, 1, 2]");
        System.out.println("[null, null, null, null, 2]");
        System.out.println("[null, null, 3, null, 3]");
        System.out.println("[null, null, 4, null, null]");

    }
    // can use to test dijkestra and bellman ford and compare out

    public static void constructorFileTest(){
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        File file = new File(name);
        try {
            Graph g = new Graph(file);
            for(double [] d : g.adjMatrix){
                System.out.println(Arrays.toString(d));
            }
            System.out.println();
            for(List<Edge> node : g.edgeList){
                for(Edge e : node ) {
                    System.out.println(e.toString());
                }
            }
        }catch (IllegalArgumentException e){
            System.out.println("unexpected input");
        }catch (IOException e){
            System.out.println("file not found");
        }
        in.close();


    }


    public static void main(String[] args) {
//       floydTest();
   // constructorFileTest();
//       DijkstraTest();
       // BellmanFordTest();

    }






}