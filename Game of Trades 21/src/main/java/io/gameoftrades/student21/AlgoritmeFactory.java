package io.gameoftrades.student21;

import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;

//Algoritme factory om de juiste algoritme door de geven aan de studentenUI

public class AlgoritmeFactory {
    public static SnelstePadAlgoritme getA(String AType){

        //Als er niks wordt doorgegeven, dan null teruggeven
        if(AType == null)
            return null;

        //Als Astar wordt doorgegeven, instantie van Astar teruggeven
        if(AType.equalsIgnoreCase("ASTAR")){
            AStar a = AStar.getInstance();
            return a;
        }

        //Als Dijkstra wordt doorgegeven, instantie van Dijkstra teruggeven
        if(AType.equalsIgnoreCase("DIJKSTRA")){
            Dijkstra d = Dijkstra.getInstance();
            return d;
        }
        return null;
    }
}
