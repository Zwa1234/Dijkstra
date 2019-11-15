package io.gameoftrades.student21;

import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.algoritme.HandelsplanAlgoritme;
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.HandelType;
import io.gameoftrades.model.markt.Handelswaar;
import io.gameoftrades.model.markt.actie.HandelsPositie;

import java.util.*;

public class Handelsplan implements HandelsplanAlgoritme {

    private Wereld wereld;

    private HandelsPositie handelsPositie;

    private AStar aStar;

    private ArrayList<Handel> handels = new ArrayList<Handel>();

    private ArrayList<Handelswaar> handelswarenInWereld;

    @Override
    public io.gameoftrades.model.markt.Handelsplan bereken(Wereld wereld, HandelsPositie positie) {
        this.handelsPositie = positie;
        this.wereld = wereld;
        //this.aStar = new AStar();
        this.handels = getHandelWereld();
        this.handelswarenInWereld = addHandelswarenInWereld();


        return null;
    }

    public ArrayList<Handel> getHandelWereld() {
        ArrayList<Handel> handelInWereld;

        handelInWereld = (ArrayList<Handel>) this.wereld.getMarkt().getHandel();

        return handelInWereld;
    }

    public int getFCostPathToCity(Coordinaat stadCoordinaat) {
        Coordinaat start = handelsPositie.getCoordinaat();
        Coordinaat einde = stadCoordinaat;

        int fCost = aStar.bereken(this.wereld.getKaart(), start, einde).getTotaleTijd();

        return fCost;
    }

    public HashMap<Stad, Coordinaat> getCoordinatenVanStedenInWereld() {
        HashMap<Stad, Coordinaat> coordinatenVanDeSteden = new HashMap<Stad, Coordinaat>();

        for (Handel handel : handels) {
            if (!coordinatenVanDeSteden.containsKey(handel.getStad())) {
                coordinatenVanDeSteden.put(handel.getStad(), handel.getStad().getCoordinaat());
            }

        }
        return coordinatenVanDeSteden;
    }

    public ArrayList<Handelswaar> addHandelswarenInWereld() {
        ArrayList<Handelswaar> alleHandelswaar = new ArrayList<Handelswaar>();
        for (Handel handel : handels) {
            if (!alleHandelswaar.contains(handel.getHandelswaar())) {
                alleHandelswaar.add(handel.getHandelswaar());
            }
        }
        return alleHandelswaar;
    }

    public HashMap<ArrayList, Integer> berekenWinsten() {
        ArrayList<Handel> gebodenHandel = new ArrayList<Handel>();
        ArrayList<Handel> gevraagdeHandel = new ArrayList<Handel>();
        HashMap<ArrayList, Integer> handelswinsten = new HashMap<ArrayList, Integer>();
        ArrayList<Handel> tweestedenHandel;


        for (Handel handel : handels) {
            if (handel.getHandelType().equals(HandelType.valueOf("VRAAGT"))) {
                gevraagdeHandel.add(handel);
            } else if (handel.getHandelType().equals(HandelType.valueOf("BIEDT"))) {
                gebodenHandel.add(handel);
            }
        }
        for (Handel handel : gevraagdeHandel) {
            int winst;
            for (Handel handelGeboden : gebodenHandel) {
                if (handel.getHandelswaar().equals(handelGeboden.getHandelswaar())) {
                    winst = handel.getPrijs() - handelGeboden.getPrijs();
                    tweestedenHandel = new ArrayList<Handel>();
                    tweestedenHandel.add(handel);
                    tweestedenHandel.add(handelGeboden);
                    handelswinsten.put(tweestedenHandel, winst);
                }
            }
        }


        return handelswinsten;
    }

    public HashMap<ArrayList, Integer> berekenEfficiency() {
        HashMap<ArrayList, Integer> winsten = berekenWinsten();
        double efficiency;
        HashMap<ArrayList, Integer> Efficiency = new HashMap<ArrayList, Integer>();
        Iterator it = winsten.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();


            it.remove();
        }
   return winsten;}




}
