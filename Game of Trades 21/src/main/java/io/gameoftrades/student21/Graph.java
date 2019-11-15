package io.gameoftrades.student21;

import java.util.ArrayList;

public class Graph {

    private Node2[] nodes;
    private int noOfNodes;
    private Edge[] edges;
    private int noOfEdges;

    public Graph(Edge[] edges){
        this.edges = edges;

        this.noOfNodes = calculateNoOfNodes(edges);
        this.nodes = new Node2[this.noOfNodes];

        for (int i = 0; i < this.noOfNodes; i++) {
            this.nodes[i] = new Node2();
        }

        this.noOfEdges = edges.length;

        for (int edgeToAdd = 0; edgeToAdd < this.noOfEdges; edgeToAdd++) {
            this.nodes[edges[edgeToAdd].getFromNodeIndex()].getEdges().add(edges[edgeToAdd]);
            this.nodes[edges[edgeToAdd].getToNodeIndex()].getEdges().add(edges[edgeToAdd]);
        }

    }

    private int calculateNoOfNodes(Edge[] edges) {
        int noOfNodes = 0;

        for (Edge e : edges) {
            if(e.getToNodeIndex() > noOfNodes)
                noOfNodes = e.getToNodeIndex();
            if(e.getFromNodeIndex() > noOfNodes)
                noOfNodes = e.getFromNodeIndex();
        }

        noOfEdges++;

        return noOfNodes;
    }

    public void calculateShortestDistances(){
        this.nodes[0].setDistanceFromSource(0);
        int nextNode = 0;

        for (int i = 0; i < this.nodes.length; i++) {
            ArrayList<Edge> currentNodeEdges = this.nodes[nextNode].getEdges();

            for (int joinedEdge = 0; joinedEdge < currentNodeEdges.size(); joinedEdge++) {
                int neighbourIndex = currentNodeEdges.get(joinedEdge).getNeighbourIndex(nextNode);

                if(!this.nodes[neighbourIndex].isVisited()){
                    int tentative = this.nodes[nextNode].getDistanceFromSource() + currentNodeEdges.get(joinedEdge).getLength();

                    if(tentative < nodes[neighbourIndex].getDistanceFromSource()){
                        nodes[neighbourIndex].setDistanceFromSource(tentative);
                    }
                }
            }

            nodes[nextNode].setVisited(true);

            nextNode = getNodeShortestDistanced();
        }
    }

    private int getNodeShortestDistanced(){
        int storedNodeIndex = 0;
        int storedDist = Integer.MAX_VALUE;

        for (int i = 0; i < this.nodes.length; i++) {
            int currentDist = this.nodes[i].getDistanceFromSource();

            if(!this.nodes[i].isVisited() && currentDist < storedDist){
                storedDist = currentDist;
                storedNodeIndex = i;
            }
        }

        return storedNodeIndex;
    }

    public void printResult(){
        String output = "Number of nodes = " + this.noOfNodes;
        output += "\nNumber of edges = " + this.noOfEdges;

        for (int i = 0; i < this.nodes.length; i++) {
            output += ("\nThe shortest distance from node = to node " + i + " is " + nodes[i].getDistanceFromSource());
        }

        System.out.println(output);
    }

    public Node2[] getNodes() {
        return nodes;
    }

    public int getNoOfNodes() {
        return noOfNodes;
    }

    public Edge[] getEdges() {
        return edges;
    }

    public int getNoOfEdges() {
        return noOfEdges;
    }
}
