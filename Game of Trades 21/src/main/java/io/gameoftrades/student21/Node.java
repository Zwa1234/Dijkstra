package io.gameoftrades.student21;

import java.util.Comparator;


import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Terrein;


public class Node {

    public static Comparator<Node> sortByMoveCost;

    private Terrein terrein;

    private Node parentNode;

    //H
    private double heurCost;

    //G
    private double gCost;

    //F
    private double moveCost;

    private Coordinaat coordinaat;

    public Node(Coordinaat einde, Terrein terrein) {
        this.terrein = terrein;
        this.coordinaat = terrein.getCoordinaat();

        if (parentNode != null) {
            this.gCost = parentNode.getGCost()+ terrein.getTerreinType().getBewegingspunten();
        } else {
            this.gCost = terrein.getTerreinType().getBewegingspunten();
        }

        this.heurCost = coordinaat.afstandTot(einde);
//        if(parentNode == null){
//            this.moveCost = 0.00;
//        }
//        else{this.moveCost = this.gCost + this.heurCost;}
        this.moveCost = this.gCost + this.heurCost;
    }

    public Node(Coordinaat einde, Terrein terrein, Node parent) {
        this.terrein = terrein;

        this.coordinaat = terrein.getCoordinaat();

        if (parent != null) {
            this.gCost = parent.getGCost() + terrein.getTerreinType().getBewegingspunten();
        } else {
            this.gCost = terrein.getTerreinType().getBewegingspunten();
        }

        this.heurCost = coordinaat.afstandTot(einde);
        this.moveCost = this.gCost + this.heurCost;


        this.parentNode = parent;

    }

    public Terrein getTerrein() {
        return this.terrein;
    }

    public Node getParentNode() {
        return this.parentNode;
    }

    public double getHeurCost() {
        return this.heurCost;
    }

    public double getGCost() {
        return this.gCost;
    }

    public double getMoveCost() {
        return this.moveCost;
    }

    public Coordinaat getCoordinaat() {
        return this.coordinaat;
    }

    public void setgCost(double gCost) {
       this.gCost = gCost;
    }

    public void setMoveCost(double moveCost) {
       this.moveCost = moveCost;
    }

//
//    @Override
//    public int compareTo(Node node) {
//
//        return Double.compare(this.getMoveCost(), node.getMoveCost());
//
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (o instanceof Node) {
//            if (((Node) o).getCoordinaat() == this.getCoordinaat()) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//        return false;
//    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Node)) {
            return false;
        }
        Node other = (Node) o;

        return other.coordinaat.equals(this.coordinaat);
    }

    @Override
    public String toString() {
        return "[" + this.coordinaat.getX() + ", " + this.coordinaat.getY() + "]";
    }

}








