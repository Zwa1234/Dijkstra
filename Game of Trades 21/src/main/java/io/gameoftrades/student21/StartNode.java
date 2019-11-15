package io.gameoftrades.student21;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Terrein;

public class StartNode extends Node {
    //Een startnode heeft ten alle tijde een gCost van 0.0
    private final double gCost = 0.0;


    public StartNode(Coordinaat einde, Terrein terrein) {
        super(einde, terrein);
    }


}
