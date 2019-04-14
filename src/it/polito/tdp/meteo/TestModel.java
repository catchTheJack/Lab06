package it.polito.tdp.meteo;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.Combo;

public class TestModel {

	public static void main(String[] args) {

		Model m = new Model();
		
		System.out.println(m.getUmiditaMedia(12));
		
		
		
//		System.out.println(m.trovaSequenza(4));
		
		Citta c1 = new Citta("Torino");
		Citta c2 = new Citta("Milano");
		Citta c3 = new Citta("Genova");
		
		Combo c = new Combo();
		c.addCombo(c1);
	
	

		m.cercaComboPubblico();
	}

}
