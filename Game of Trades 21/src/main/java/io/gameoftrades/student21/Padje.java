package io.gameoftrades.student21;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Richting;

import java.util.ArrayList;

public class Padje implements Pad  {

    private Richting[] bewegingen;


    public Padje(Richting[] bewegingen) {
        this.bewegingen = bewegingen;
    }


    public Richting[] getBewegingen(){
        return bewegingen;

    }

    public int getTotaleTijd(){

        return bewegingen.length;

    }


    /**
     * Keert het pad om (nebodigd voor eind pad)
     * @return lijst van bewegingen, achterstevoren
     */
    public Richting[] reversePath() {
        int a = 0;
        int b = this.bewegingen.length - 1;
        Richting tmp;

        while (b > a) {
            tmp = this.bewegingen[b];
            this.bewegingen[b] = this.bewegingen[a];
            this.bewegingen[a] = tmp;
            b--;
            a++;
        }
        return this.bewegingen;
    }

    /**
     * Keert de richtingen om
     * Moet omgezet worden naar een Array
     * @return
     */
    public Pad omgekeerd(){

        ArrayList<Richting> container = new ArrayList<>();
        for (Richting richting : reversePath()) {
            container.add(richting.omgekeerd());
        }

        Richting[] pad = container.toArray(new Richting[container.size()]);

        return new Padje(pad);

    }


    /**
     * volgt richting to coordinaat
     * @param coordinaat
     * @return coordinaat
     */
    public Coordinaat volg(Coordinaat coordinaat){
        for (Richting richting : bewegingen) {
            coordinaat = coordinaat.naar(richting);
        }
        return coordinaat;
    }
}
