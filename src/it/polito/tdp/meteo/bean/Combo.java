package it.polito.tdp.meteo.bean;

import java.util.ArrayList;

public class Combo {
	ArrayList<Citta> combo ;
	boolean correct= false;
	double costo;
	
	public Combo(){
		combo = new ArrayList<Citta>();
	}
	
	public void setCosto(double cost) {
		this.costo= cost;
	}
	
	public double getCosto() {
		return this.costo;
	}
	
	public Citta getLast() {
		Citta cx = combo.get(combo.size()-1);
		return cx;
	}

	public ArrayList<Citta> getCombo() {
		return combo;
	}

	public void setCombo(ArrayList<Citta> combo) {
		this.combo = combo;
	}
	
	public void addCombo(Citta cx) {
		combo.add(cx);
	}
	
	public Combo clone() {
		Combo cx = new Combo();
		cx.combo = new ArrayList<Citta>(this.combo);
		return cx;
	}
	
	public void remove(Citta cx) {
		combo.remove(cx);
	}
	
	public String getValue() {
		String s="";
		for(Citta cx :combo) {
			s=s+" "+cx;
		}
		return s;
	}
	//metodo per verificare che la città non
	//venga visitata più di 6 giorni limite superiore
	private int counterCitta(String nome) {
		int counter=0;
		Citta cx = new Citta(nome);
		for(Citta temp:combo) {
			if(temp.equals(cx)) {
				counter++;
			}	
		}
		return counter;
	}
	
	

	//contatore del cambio città per aggiungere la funzione costo
	public int tripCount() {
		int trip=0;
		for(int i = 0;i<14;i++) {
			if(combo.get(i).equals(combo.get(i+1))==false){
				trip++;
			}
		}
		return trip;
	}
	
	
	//metodo pubblico per verificare che la sequenza generata dalla ricorsione rispetti
	//i vincoli di giorni sia vincolo superiore che vincolo inferiore
	public boolean isCorrect() {
		if((counterCitta("Torino")<7) &&(counterCitta("Milano")<7)&&(counterCitta("Genova")<7)) {
			this.correct=true;
		}
		return this.correct;
	}
	
	
	
	
	
}
