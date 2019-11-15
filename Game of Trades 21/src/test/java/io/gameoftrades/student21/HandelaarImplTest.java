package io.gameoftrades.student21;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import io.gameoftrades.model.kaart.*;
import org.junit.Before;
import org.junit.Test;

import io.gameoftrades.model.Handelaar;
import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.algoritme.HandelsplanAlgoritme;
import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.algoritme.StedenTourAlgoritme;
import io.gameoftrades.model.lader.WereldLader;
import io.gameoftrades.model.markt.Handelsplan;
import io.gameoftrades.model.markt.actie.Actie;
import io.gameoftrades.model.markt.actie.BeweegActie;
import io.gameoftrades.model.markt.actie.HandelsPositie;

import static org.junit.Assert.*;

/**
 * Een verzameling eenvoudige tests om te kijken of de handelaar werkt.
 * 
 * Breid deze uit en of maak je eigen oplossing specifieke tests.
 */
public class HandelaarImplTest {

    private Handelaar handelaar;

    @Before
    public void init() {
        handelaar = new HandelaarImpl();
    }

    @Test
    public void zouWereldMoetenLaden() {
        WereldLader lader = handelaar.nieuweWereldLader();
        assertNotNull(lader);

        Wereld wereld = lader.laad("/kaarten/voorbeeld-kaart.txt");
        assertNotNull(wereld);
    }

    @Test(timeout=1500)
    public void zouEenPadMoetenVinden() {
        Wereld wereld = handelaar.nieuweWereldLader().laad("/kaarten/voorbeeld-kaart.txt");
        assertNotNull(wereld);
        
        Kaart kaart = wereld.getKaart();
        Stad van = wereld.getSteden().get(0);
        Stad naar = wereld.getSteden().get(1);

        SnelstePadAlgoritme algoritme = handelaar.nieuwSnelstePadAlgoritme();
        assertNotNull(algoritme);

        Pad pad = algoritme.bereken(kaart, van.getCoordinaat(), naar.getCoordinaat());

        assertNotNull(pad.getBewegingen());

        // 19 is de tijd voor de meest optimale route, om de bergen heen.

        assertEquals(19, pad.getTotaleTijd());

        // Heen

        Coordinaat bestemming = pad.volg(van.getCoordinaat());
        assertEquals(naar.getCoordinaat(), bestemming);

        // En Terug

        Coordinaat bron = pad.omgekeerd().volg(naar.getCoordinaat());
        assertEquals(van.getCoordinaat(), bron);
    }

    @Test(timeout=1500)
    public void zouEenRouteMoetenVinden() {
        Wereld wereld = handelaar.nieuweWereldLader().laad("/kaarten/voorbeeld-kaart.txt");
        assertNotNull(wereld);

        StedenTourAlgoritme algoritme = handelaar.nieuwStedenTourAlgoritme();
        assertNotNull(algoritme);

        List<Stad> result = algoritme.bereken(wereld.getKaart(), wereld.getSteden());

        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals(4, new HashSet<>(result).size()); // check duplicaten.
    }

    @Test(timeout=1500)
    public void zouEenHandelsplanMoetenMaken() {
        Wereld wereld = handelaar.nieuweWereldLader().laad("/kaarten/voorbeeld-kaart.txt");
        assertNotNull(wereld);
        
        Stad startStad = wereld.getSteden().get(0);
        
        HandelsplanAlgoritme algoritme = handelaar.nieuwHandelsplanAlgoritme();
        Handelsplan plan = algoritme.bereken(wereld, new HandelsPositie(wereld, startStad, 150, 10, 50));

        assertNotNull(plan);
        assertNotNull(plan.getActies());

        for (Actie a : plan.getActies()) {
            assertNotNull(a);
            assertFalse("BeweegActie gevonden, zet deze om in NavigeerActies", a instanceof BeweegActie);
        }
    }

    @Test(timeout=1500)
    public void zouEenPadMoetenVindenWesterosKaart() {

        Wereld wereld = handelaar.nieuweWereldLader().laad("/kaarten/westeros-kaart.txt");
        assertNotNull(wereld);

        List<Stad> cities = wereld.getSteden();
        Kaart map = wereld.getKaart();


        //AStar algorithm = new AStar();
        AStar algorithm = AStar.getInstance();

        //Get the first two cities
        Stad van = cities.get(0);
        Stad naar = cities.get(1);



        //get distance from city one to city two
        Pad pad = algorithm.bereken(map, van.getCoordinaat(), naar.getCoordinaat());
        int totaleTijd = pad.getTotaleTijd();

        // 12 is the most optimal time between the first two cities
        assertEquals(12,totaleTijd);

        // Heen

        Coordinaat bestemming = pad.volg(van.getCoordinaat());
        assertEquals(naar.getCoordinaat(), bestemming);

        // En Terug

        Coordinaat bron = pad.omgekeerd().volg(naar.getCoordinaat());
        assertEquals(van.getCoordinaat(), bron);
    }

    @Test(timeout=1500)
    public void zouEenRouteMoetenVindenOpWesterosKaart() {
        Wereld wereld = handelaar.nieuweWereldLader().laad("/kaarten/westeros-kaart.txt");
        assertNotNull(wereld);

        StedenTourAlgoritme algoritme = handelaar.nieuwStedenTourAlgoritme();
        assertNotNull(algoritme);

        List<Stad> result = algoritme.bereken(wereld.getKaart(), wereld.getSteden());

        assertNotNull(result);
        assertEquals(21, result.size());
        assertEquals(21, new HashSet<>(result).size()); // check duplicaten.
    }

    @Test(timeout=1500)
    public void AStarVindDeJuisteRichtingenTussenSteden(){
        Wereld wereld = handelaar.nieuweWereldLader().laad("/kaarten/voorbeeld-kaart.txt");
        assertNotNull(wereld);

        //kaart initializeren en de eerste twee steden krijgen
        Kaart kaart = wereld.getKaart();
        Stad van = wereld.getSteden().get(0);
        Stad naar = wereld.getSteden().get(1);

        AStar aStar = AStar.getInstance();


        // de meest efficiente richtingen naar het doel in een ArrayList stoppen
        ArrayList<Richting> richtingen = new ArrayList<Richting>();

        richtingen.add(Richting.ZUID);
        richtingen.add(Richting.WEST);
        richtingen.add(Richting.ZUID);
        richtingen.add(Richting.ZUID);
        richtingen.add(Richting.ZUID);
        richtingen.add(Richting.ZUID);
        richtingen.add(Richting.ZUID);
        richtingen.add(Richting.ZUID);
       richtingen.add(Richting.OOST);
       richtingen.add(Richting.OOST);
       richtingen.add(Richting.OOST);
       richtingen.add(Richting.NOORD);
        richtingen.add(Richting.NOORD);
        richtingen.add(Richting.NOORD);
        richtingen.add(Richting.NOORD);
        richtingen.add(Richting.NOORD);
        richtingen.add(Richting.NOORD);
        richtingen.add(Richting.OOST);
        richtingen.add(Richting.NOORD);

        // checken of het algoritme het meest efficiente pad volgt
        Pad aStarPad = aStar.bereken(kaart, van.getCoordinaat(), naar.getCoordinaat());
        Richting[] padRichtingen =  aStarPad.getBewegingen();

        assertArrayEquals(richtingen.toArray(),padRichtingen);
    }
    @Test(timeout=1500)
    public void StedenTourZouAlleStedenMoetenZien(){
        Wereld wereld = handelaar.nieuweWereldLader().laad("/kaarten/voorbeeld-kaart.txt");
        assertNotNull(wereld);

        List<Stad> cities = wereld.getSteden();
        Kaart map = wereld.getKaart();

        Stedentour stedentour = new Stedentour();

        stedentour.bereken(map, cities);

        List<List<Stad>> gevondenSteden = stedentour.getOpties();

        assertTrue(gevondenSteden.get(0).containsAll(cities));
    }
}


