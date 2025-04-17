package org.example;

public class Edge {
    int from;
    int to;
    double cost;

    public Edge(int from, int to, double cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }
    @Override
    public String toString() {
        return from + ", " + to + ", " + cost;
    }

}
