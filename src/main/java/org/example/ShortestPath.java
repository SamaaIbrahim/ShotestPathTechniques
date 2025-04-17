package org.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShortestPath {


    public static List<Integer> getPath(int start, int end, Integer[][] predecessors) {
        if (predecessors[start][end] == null) {
            return null;
        }

        ArrayList<Integer> path = new ArrayList<>();
        path.add(end);

        while (end != start) {
            end = predecessors[start][end];
            path.addFirst(end);
        }
        return path;
    }
    public static List<Integer> getPath(int start, int end, Integer [] parent) {
        if (parent[end] == null) { // no path found
            return null;
        }

        ArrayList<Integer> path = new ArrayList<>();
        path.add(end);

        while (end != start) {
            end = parent[end];
            path.addFirst(end);
        }
        return path;
    }
    public void printPath(List<Integer> path){
        if(path == null){
            System.out.println("No Path Exist");
        }else{
            for(int i=0;i<path.size();i++){
                if(i != path.size()-1){
                    System.out.print(path.get(i)+" -> ");
                }
                else{
                    System.out.println(path.get(i));
                }
            }
        }
    }


    public void mainMenu() {
        Scanner in = new Scanner(System.in);
        System.out.println("Shortest Paths Algorithms\n");
        // still ask for input graph
        while (true){
            System.out.println("Enter exit to terminate");
            System.out.print("Enter File Path Containing Graph : ");
            String path = in.nextLine();
            if(path.compareToIgnoreCase("exit") == 0){
                break;
            }
            File file = new File(path);
            Graph graph;
            try{
                graph = new Graph(file);
            }catch (IOException e){
                System.out.println("Invalid File");
                continue;

            }catch (IllegalArgumentException e){
                System.out.println("Invalid Input File");
                continue;
            }
            // main menu
            while(true){
                if(!graph.BellmanFord(0,new double[graph.n],new Integer[graph.n])){
                    System.out.println("Graph contain -ve Cycle");
                    continue;
                }
                String input;
                do{
                    System.out.println("Enter Back to Enter New Graph");
                    System.out.println("Enter 1 to enter Single Source Path Sub Menu");
                    System.out.println("Enter 2 to enter All Pair Shortest Path Sub Menu");
                    System.out.print("Input : ");
                    input = in.nextLine();
                    if(input.compareToIgnoreCase("back")==0){
                        break;
                    }
                }while (input.compareToIgnoreCase("1") != 0 && input.compareToIgnoreCase("2")!=0);
                if(input.compareToIgnoreCase("back")==0){
                    break;
                }
                if(input.compareToIgnoreCase("1")==0){
                    // enter single source menu
                    singleSourceSubMenu(graph,in);
                }else {
                    // enter all pair menu
                    allPairSubMenu(graph,in);
                }


            }

        }


    }
    public void singleSourceSubMenu(Graph graph,Scanner in){
        boolean validSource  = true;
        String input;
        int s = 0;
        do{
            System.out.print("Enter Source Node : ");
            input = in.nextLine();
            try {
                s = Integer.parseInt(input);
                if(s < 0 || s >= graph.n){
                    System.out.println("Invalid Source(enter source from 0 -> "+(graph.n-1)+")");
                    validSource =false;
                }

            }
            catch (Exception e){
                System.out.println("Invalid Source(enter source from 0 -> "+(graph.n-1)+")");
                validSource = false;
            }
        }while (!validSource); // get valid source
        // source done now initialize cost and parent to save them one time
        double[] cost = new double[graph.n];
        Integer[] parent = new  Integer[graph.n];
        // ask for bellman and floyd only until correct input given
        if(graph.containNegativeEdge){
            // ask for bellman and floyd only until correct input given
            System.out.println("This Graph Contain Negative Edge Cost So Can't Use Dijkestra");
            do{
                System.out.println("Enter 1 to Use Bellman Ford");
                System.out.println("Enter 2 to Use Floyd");
                System.out.print("Input : ");
                input = in.nextLine();
            }while (input.compareToIgnoreCase("1") != 0 && input.compareToIgnoreCase("2")!=0);
            if(input.compareToIgnoreCase("1") == 0){
                graph.BellmanFord(s,cost,parent);
            }else{
                double[][] pairCost = new double[graph.n][graph.n];
                Integer [][] prec = new Integer[graph.n][graph.n];
                graph.Floyd(pairCost,prec);
                cost = pairCost[s];
                parent = prec[s];
            }
        }
        // ask for all algorithms
        else{
            do{
                System.out.println("Enter back to Return to ShortestPath Menu");
                System.out.println("Enter 1 to Use Dijkestra");
                System.out.println("Enter 2 to Use Bellman Ford");
                System.out.println("Enter 3 to Use Floyd");
                System.out.print("Input : ");
                input = in.nextLine();
                if(input.compareToIgnoreCase("back") == 0){
                    return;
                }
            }while (input.compareToIgnoreCase("1") != 0 && input.compareToIgnoreCase("2")!=0 && input.compareToIgnoreCase("3")!=0);
            if(input.compareToIgnoreCase("1") == 0){
                graph.dijkestra(s,cost,parent);
            }else if(input.compareToIgnoreCase("2") == 0){
                graph.BellmanFord(s,cost,parent);
            }
            else{
                double[][] pairCost = new double[graph.n][graph.n];
                Integer [][] prec = new Integer[graph.n][graph.n];
                graph.Floyd(pairCost,prec);
                cost = pairCost[s];
                parent = prec[s];
            }
        }
        // now the two arrays saved user can enter any query
        do {

            boolean validNode = true;
            int node = 0;
            do {
                System.out.println("Enter back to Return to ShortestPath Menu");
                System.out.print("Enter node : ");
                input = in.nextLine();
                if (input.compareToIgnoreCase("back") == 0) {
                    return;
                }
                try {
                    node = Integer.parseInt(input);
                    if (node < 0 || node >= graph.n) {
                        System.out.println("Invalid Node(enter node from 0 -> " + (graph.n - 1) + ")");
                        validNode = false;
                    }
                    else{
                        validNode = true;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid Node(enter node from 0 -> " + (graph.n - 1) + ")");
                    validNode = false;
                }
            } while (!validNode);
            System.out.println("Shortest path : "+cost[node]);
            printPath(getPath(s,node,parent));
        } while (input.compareToIgnoreCase("back") != 0);

    }
    public void allPairSubMenu(Graph graph,Scanner in){
        String input;
        double[][] cost = new double[graph.n][graph.n];
        Integer[][] prec = new  Integer[graph.n][graph.n];
        // ask for bellman and floyd only until correct input given
        if(graph.containNegativeEdge){
            // ask for bellman and floyd only until correct input given
            System.out.println("This Graph Contain Negative Edge Cost So Can't Use Dijkestra");
            do{
                System.out.println("Enter 1 to Use Bellman Ford");
                System.out.println("Enter 2 to Use Floyd");
                System.out.print("Input : ");
                input = in.nextLine();
            }while (input.compareToIgnoreCase("1") != 0 && input.compareToIgnoreCase("2")!=0);
            if(input.compareToIgnoreCase("1") == 0){
                for(int i = 0;i< graph.n;i++){
                    graph.BellmanFord(i,cost[i],prec[i]);
                }
            }else{
                graph.Floyd(cost,prec);

            }
        }
        // ask for all algorithms
        else{
            do{
                System.out.println("Enter back to Return to ShortestPath Menu");
                System.out.println("Enter 1 to Use Dijkestra");
                System.out.println("Enter 2 to Use Bellman Ford");
                System.out.println("Enter 3 to Use Floyd");
                System.out.print("Input : ");
                input = in.nextLine();
                if(input.compareToIgnoreCase("back") == 0){
                    return;
                }
            }while (input.compareToIgnoreCase("1") != 0 && input.compareToIgnoreCase("2")!=0 && input.compareToIgnoreCase("3")!=0);
            if(input.compareToIgnoreCase("1") == 0){
                for(int i = 0;i< graph.n;i++){
                    graph.dijkestra(i,cost[i],prec[i]);
                }
            }else if(input.compareToIgnoreCase("2") == 0){
                for(int i = 0;i< graph.n;i++){
                    graph.BellmanFord(i,cost[i],prec[i]);
                }
            }
            else{
                graph.Floyd(cost,prec);
            }
        }
        // now the two arrays saved user can enter any query
        do {

            boolean validInput = true;
            int s = 0;
            int d = 0;
            do {
                System.out.println("Enter back to Return to ShortestPath Menu");
                System.out.print("Enter Source and Destenation space separated : ");
                input = in.nextLine();
                if (input.compareToIgnoreCase("back") == 0) {
                    return;
                }
                try {
                    String[] token = input.split(" ");
                    if(token.length!=2){
                        validInput = false;
                        continue;
                    }
                    s = Integer.parseInt(token[0]);
                    d = Integer.parseInt(token[1]);
                    if (s < 0 || s >= graph.n || d< 0 || d >= graph.n ) {
                        System.out.println("Invalid Node(enter node from 0 -> " + (graph.n - 1) + ")");
                        validInput = false;
                    }
                    else{
                        validInput = true;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid Node(enter node from 0 -> " + (graph.n - 1) + ")");
                    validInput = false;
                }
            } while (!validInput);
            System.out.println("Shortest path : "+cost[s][d]);
            printPath(getPath(s,d,prec));
        } while (input.compareToIgnoreCase("back") != 0);


    }



    public static void main(String[] args) {

        ShortestPath shortestPath = new ShortestPath();
        shortestPath.mainMenu();


    }

}