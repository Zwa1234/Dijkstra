package io.gameoftrades.student21;

import io.gameoftrades.debug.Debuggable;
import io.gameoftrades.debug.Debugger;
import io.gameoftrades.debug.DummyDebugger;
import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.kaart.*;

import java.util.ArrayList;

class AStar implements SnelstePadAlgoritme, Debuggable {

    private Debugger debug = new DummyDebugger();
    private ArrayList<Node> openList;
    private ArrayList<Node> closedList;
    private ArrayList<Richting> richtingArrayList;
    private Kaart kaart;
    private Coordinaat startPunt;
    private Coordinaat eindPunt;
    private Coordinaat vorigeNode;
    private Coordinaat vorigeParentNode;
    private StartNode startNode;
    private Node eindNode;
    private Terrein startTerrain;

    //Single instance aanmaken van AStar
    private static AStar astar_instance = null;

    /**
     * Constructor
     */
    private AStar() {
        closedList = new ArrayList<>();
        openList = new ArrayList<>();
        richtingArrayList = new ArrayList<>();
        kaart = null;
        startPunt = null;
        eindPunt = null;
    }

    public Pad bereken(Kaart kaart, Coordinaat start, Coordinaat end) {

        initNieuwPad(kaart, start, end);
        this.startPunt = start;
        startTerrain = kaart.getTerreinOp(startPunt);
        startNode = new StartNode(eindPunt, startTerrain);
        openList.add(startNode);


        while (openList.size() > 0) {
            berekenPad();
        }

        return getPath();
    }

    /**
     * Maakt ArrayLists leeg en initialiseerd fields
     * @param kaart de nieuwe kaart
     * @param start het nieuwe start coordinaat
     * @param end het nieuwe eind coordinaat
     */
    private void initNieuwPad(Kaart kaart, Coordinaat start, Coordinaat end){
        openList = new ArrayList<>();
        closedList = new ArrayList<>();
        richtingArrayList = new ArrayList<>();

        this.kaart = kaart;
        this.startPunt = start;
        this.eindPunt = end;
    }

    /**
     * Berekent het efficientste pad
     * verwijderd currentNode van openList
     * voegt deze toe aan closedList
     * checkt of currenNode op het eindpunt staat
     */
    private void berekenPad() {
        Node currentNode = getNodeMetLaagsteF();
        openList.remove(currentNode);
        closedList.add(currentNode);

        Coordinaat currentCoordinate = currentNode.getCoordinaat();
        Terrein eindTerrein = kaart.getTerreinOp(eindPunt);
        if (checkEndLocation(currentCoordinate, currentNode, eindTerrein)) {
            return;
        }
        Richting[] richtingen = currentNode.getTerrein().getMogelijkeRichtingen();

        checkRichtingen(currentNode, richtingen);
    }

    /**
     * Controleerd voor iedere righting tussen currentNode en buurNode
     * @param currentNode Node waarop richtingen worden gecontroleerd
     * @param richtingen mogelijke richtingen
     */
    private void checkRichtingen(Node currentNode, Richting[] richtingen) {
        for (int i = 0; i < richtingen.length; i ++) {
            Richting richting = richtingen[i];

            Terrein buurNodeTerrain = kaart.kijk(currentNode.getTerrein(), richting);
            Node buurNode = new Node(eindPunt, buurNodeTerrain, currentNode);

            setBuurNode(buurNode);
        }
    }

    /**
     * Controleerd of buurNode al in de closedList voorkomt
     * @return als dit het geval is
     * Zo niet, dan check of buurNode in openList zit
     * Als dit niet het geval is, wordt buurNode toegevoegd
     * @param buurNode
     */
    private void setBuurNode(Node buurNode){
        if (closedList.contains(buurNode))
            return;

        if (!openList.contains(buurNode)) {
            openList.add(buurNode);
        }
    }

    /**
     * Zet richtingArrayList om naar een Array
     * Voert reversePath uit op deze Array
     * Voert verplichte debugger uit
     *
     * @return path, nadat deze is gereversed
     */
    private Pad getPath() {
        int size = richtingArrayList.size();
        Richting[] padArray = richtingArrayList.toArray(new Richting[size]);
        Padje reversePath = new Padje(padArray);

        Pad path = reversePath.omgekeerd();

        debug.debugPad(kaart, startPunt, path);

        return path;
    }

    /**
     * Ondezoekt welke Node de laagste fCost bedraagd
     * @return currentNode
     */
    private Node getNodeMetLaagsteF() {
        double currentCost = 0.00;
        Node currentNode = null;

        for(int i = 0; i < openList.size(); i ++){
            Node checkNode = openList.get(i);
            if (currentCost > 0.00 && checkNode.getMoveCost() >= currentCost )
                continue;
            currentNode = checkNode;
            currentCost = currentNode.getMoveCost();
        }

        return currentNode;
    }

    /**
     * Definieerd de laatse Node op het pad
     * @param currentCoordinate coordinaat van CurrentNode
     * @param currentNode
     * @param eindTerrein terrein van eindpunt
     * @return true
     * @return false
     */
    private boolean checkEndLocation(Coordinaat currentCoordinate, Node currentNode, Terrein eindTerrein) {
        eindNode = new Node(eindPunt, eindTerrein);

        if (currentCoordinate.equals(eindNode.getCoordinaat())) {
            while (currentNode.getParentNode() != null) {
                currentNode = getVorigeNode(currentNode);
            }
            return true;
        }
        return false;
    }

    /**
     * Zoekt voorgande node
     * Voegd righting toe aan rightingArrayList
     * @param currentNode
     * @return nieuwe parrentNode
     */
    private Node getVorigeNode(Node currentNode) {
        vorigeParentNode = currentNode.getParentNode().getTerrein().getCoordinaat();
        vorigeNode = currentNode.getTerrein().getCoordinaat();
        Richting richting = Richting.tussen(vorigeNode, vorigeParentNode);
        richtingArrayList.add(richting);

        return currentNode.getParentNode();
    }


    /**
     * Override toString om in GUI juiste verwijzing te zien (i.p.v object adres)
     * @return correcte verwijzing naar A* algortime
     */
    @Override
    public String toString() {
        return "A* ";
    }

    /**
     * Zet debugger (benodigd uit Librabry)
     * @param debugger
     */
    @Override
    public void setDebugger(Debugger debugger) {
        debug = new DummyDebugger();
        this.debug = debugger;
    }

    //Static method voor het maken van de singleton instance
    public static synchronized AStar getInstance(){
        if(astar_instance == null)
            astar_instance = new AStar();

        return astar_instance;
    }
}