package org.example;

import java.io.File;
import java.util.ArrayList;

public class Graph {
    int v;
    int e;
    int[][] adjMatrix;
    ArrayList<ArrayList<Integer>> edgeList;

    boolean containNegativeEdge;

    public Graph(int v, int e, int[][] adjMatrix, ArrayList<ArrayList<Integer>> edgeList, boolean containNegativeEdge) {
        this.v = v;
        this.e = e;
        this.adjMatrix = adjMatrix;
        this.edgeList = edgeList;
        this.containNegativeEdge = containNegativeEdge;
    }
    public Graph(File file){}
    public Graph(int n){}
    public void addEdge(int from,int to ,int cost){

    }

    public void dijkestra(int s, int[] cost, int[] parent){

    }
    public boolean BellmanFord(int s,int[] cost,int[] parent){
        return false;
    }
    public boolean Floyd(int[][] cost,int[][] predecessors){
        return false;
    }






}
