package io.gameoftrades.student21;

import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Richting;
import io.gameoftrades.model.kaart.Terrein;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Dijkstra implements SnelstePadAlgoritme {

    private Kaart kaart;
    private Coordinaat start;
    private Coordinaat eind;
    private StartNode startNode;
    private Terrein startTerrein;

    //Single instance aanmaken van Dijkstra
    private static Dijkstra dijkstra_instance = null;

    private Dijkstra(){
        Edge[] edges = A
    }

    public PadImpl bereken(Kaart kaart, Coordinaat start, Coordinaat eind){
        this.kaart = kaart;
        this.start = start;
        this.eind = eind;

        return null;
    }

    public int[] dijkstra(Kaart kaart, Coordinaat start, Coordinaat eind){
        int dist = Integer.MAX_VALUE;
        startNode = new StartNode(eind, startTerrein);
    }

    @Override
    public String toString() {
        return "Dijkstra ";
    }

    public static synchronized Dijkstra getInstance(){
        if(dijkstra_instance == null)
            dijkstra_instance = new Dijkstra();

        return dijkstra_instance;
    }
}