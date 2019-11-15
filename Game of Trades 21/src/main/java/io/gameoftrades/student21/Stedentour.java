package io.gameoftrades.student21;

import io.gameoftrades.debug.Debuggable;
import io.gameoftrades.debug.Debugger;
import io.gameoftrades.debug.DummyDebugger;
import io.gameoftrades.model.algoritme.StedenTourAlgoritme;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Stad;

import java.util.ArrayList;
import java.util.List;

public class Stedentour implements StedenTourAlgoritme, Debuggable {

    AStar b = AStar.getInstance();
    List<List<Stad>> opties;
    int tijd = 0;
    List<Stad> snelste;
    private Kaart kaart;
    private Debugger debug = new DummyDebugger();


    /** Dit is de constructor van de klassen
     *
     */

    public Stedentour(){
        //AStar.resetInstance();
        List<Stad> snelste = new ArrayList<>();
        List<List<Stad>> opties = new ArrayList<>();
        List<Stad> steden = new ArrayList<>();
        int tijd = 0;
    }

    /**
     * In deze methode wordt uiteindelijk de route berekent. Hier worden wel nog meerdere andere methodes aangeroepen die er voor nodig zijn hem ook te berkenen.
     * @param kaart zodat het duidelijk voor welke kaart er een route moet worden berekend
     * @param list Deze lijst staat vol met alle steden die op de kaart staan
     * @return uiteindelijk wordt er een lijst met de snelste route teruggegeven
     */


    @Override
    public List<Stad> bereken(Kaart kaart, List<Stad> list) {
        List<Stad> steden = new ArrayList<>(list);

        initNieuweRoute(kaart, steden);

        opties = permutation(steden);
        System.out.println(opties);

        return getSnelste(opties);

    }

    /**
     * Deze methode gaat alle mogelijke routes af om te kijken of het de snelste is
     * @param list Een lijst met daar in lijsten met mogelijke routes
     * @return geeft de snelste route terug
     */


    public List<Stad> getSnelste(List<List<Stad>> list){

        for(int i = 0; i < list.size(); i++){
            routeBerekenen(kaart, opties.get(i));
        }
        debug.debugSteden(kaart, snelste);
        return snelste;
    }



    /**
     * Maakt ArrayLists leeg en initialiseerd fields
     * @param kaart de nieuwe kaart
     * @param list vult steden met alle steden van de kaart
     */
    private void initNieuweRoute(Kaart kaart, List<Stad> list){
        List<Stad> snelste = new ArrayList<>();
        List<List<Stad>> opties = new ArrayList<>();
        this.kaart = kaart;
        List<Stad> steden = list;
    }


    /**
     * deze methode voert de permutatie nog niet uit maar geeft wel de lijsten mee waar in het gaat gebeuren.
     * @param list Geeft een lijst met alle steden die worden gebruikt voor permutaties
     * @return geeft de lijst terug met daar in alle mogelijke routes
     */


    public List<List<Stad>> permutation(List<Stad> list){
        List<Stad> steden = new ArrayList<>(list);
        List<List<Stad>> overig = new ArrayList<>();
        permuteHelper(overig, new ArrayList<>(), steden);
        return overig;
    }

    /**
     * De methode voert daadwerkelijk het permuteren uit
     * @param list De lijst waar alle mogelijke routes in worden opgeslagen
     * @param result Een lege lijst
     * @param overig De lijst met steden
     */

    public void permuteHelper(List<List<Stad>> list, List<Stad> result, List<Stad> overig) {

        if (result.size() == overig.size()) {
            list.add(new ArrayList<>(result));
        } else {
            for (int i = 0; i < overig.size(); i++) {
                if(result.contains(overig.get(i))){
                    continue;
                }

                result.add(overig.get(i));
                permuteHelper(list, result, overig);
                result.remove(result.size() - 1);
            }
        }
    }


    /**
     * Hier wordt de bereken route en getTotaleTijd van AStar aangeroepen om zo tot een snelste tijd te komen
     * @param kaart De kaart die wordt gebruikt
     * @param list De lijst met steden
     * @return Geeft de snelste route terug als hij onder de huidige snelste tijd zit.
     */

    public List<Stad> routeBerekenen(Kaart kaart, List<Stad> list){
        int i = 0;
        int tmp = 0;
        for (int j = 1; j < list.size(); j++){
            int tmp2 = b.bereken(kaart, list.get(i).getCoordinaat(), list.get(j).getCoordinaat()).getTotaleTijd();;
            tmp = tmp + tmp2;
            i++;

        }

        if(tmp < tijd || tijd == 0){
            tijd = tmp;
            snelste = new ArrayList<>(list);
        }
        return snelste;
    }

    /**
     * De debugger (benodigd uit library)
     * @param debugger
     */
    @Override
    public void setDebugger(Debugger debugger) {

        debug = new DummyDebugger();
        this.debug = debugger;
    }


    /**
     * Zorgt er voor dat er Stedentour komt te staan in de ui
     * @return
     */
    public String toString(){
        return "Stedentour";
    }

    public List<List<Stad>> getOpties(){
        return this.opties;
    }
}
