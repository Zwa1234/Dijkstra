package io.gameoftrades.student21;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Richting;

public class PadImpl implements Pad{

	private Richting[] richtingen;

	private int totaleTijd;

	private Richting[] omgekeerdeRichtingen;

	public PadImpl(Richting[] richtingen, int totaleTijd) {
		this.richtingen = richtingen;
		this.totaleTijd = totaleTijd;
	}

	public int getTotaleTijd() {
		return this.totaleTijd;
		}



	public Richting[] getBewegingen() {
		return this.richtingen;
	}

	public Pad omgekeerd() {

		if(this.omgekeerdeRichtingen.length != 0) {
			return  new PadImpl(this.omgekeerdeRichtingen, this.totaleTijd);
		}

		for(int i = 0; i < richtingen.length; i++) {
            this.omgekeerdeRichtingen[i] = richtingen[richtingen.length - i - 1];}

		return  new PadImpl(this.omgekeerdeRichtingen, this.totaleTijd);
	}


	public Coordinaat volg(Coordinaat start) {
		 for(Richting richting: this.richtingen)
	            start = start.naar(richting);


	        return start;
	}
	
	
	

}
