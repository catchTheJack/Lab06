package it.polito.tdp.meteo;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.Combo;
import it.polito.tdp.meteo.bean.SimpleCity;
import it.polito.tdp.meteo.db.MeteoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	
	private MeteoDAO mdao = new MeteoDAO();
	public ArrayList<Citta> city ;
	public ArrayList<Combo> combinazioni;
	
	Citta c1 = new Citta("Torino");
	Citta c2 = new Citta("Milano");
	Citta c3 = new Citta("Genova");

	
	public Model() {
		city = new ArrayList<Citta>();
		city.add(c1);
		city.add(c2);
		city.add(c3);
		combinazioni = new ArrayList<Combo>();
	}
	
	
	//metodo per settare la choicebox
	public ObservableList<String> getChoice(){
		List<String> mesi = new ArrayList<String>();
		
		for(int i = 0; i<13; i++) {
			mesi.add(""+i);
		}
		ObservableList<String> l = FXCollections.observableList(mesi);
		return l;
	}
	
	//metodo ricorsivo per lanciare la ricorsione
	
	public void cercaComboPubblico() {
		Combo parziale = new Combo();
		cercaCombinazioni(parziale,0);
	}
	
	//metodo ricorsivo
	
	private void cercaCombinazioni(Combo parziale,int L){
		//condizione terminale
		if(L==NUMERO_GIORNI_TOTALI){
			if(parziale.isCorrect()==true && parziale.getCombo().get(0).equals(parziale.getCombo().get(1))&&
					   parziale.getCombo().get(0).equals(parziale.getCombo().get(2)) &&
			   parziale.getCombo().get(14).equals(parziale.getCombo().get(13))&&
			   parziale.getCombo().get(14).equals(parziale.getCombo().get(12))) {
			combinazioni.add(parziale);
			System.out.println(parziale.getValue());
			return;
			}
			else return;
		}
		
				for(Citta temp:city) {
					if(isAvaiable(parziale,temp)==true) {
						parziale.addCombo(temp);
						cercaCombinazioni(parziale.clone(),L+1);
						parziale.remove(temp);
					}
				}
			}
			

	public String getUmiditaMedia(int mese) {
		String s="UMIDITA' MEDIE \n";
		s=s+"Torino: "+mdao.getAvgRilevamentiLocalitaMese(mese,"Torino")+"\n";
		s=s+"Milano: "+mdao.getAvgRilevamentiLocalitaMese(mese,"Milano")+"\n";
		s=s+"Genova: "+mdao.getAvgRilevamentiLocalitaMese(mese,"Genova");

		return s;
	}
	

	public String trovaMigliore(int mese) {
		punteggioCombinazioni(mese);
		Combo best = new Combo();
		best.setCosto(100000.0);
		for(Combo cx : combinazioni) {
			if(cx.getCosto()<best.getCosto()) {
				best=cx;
			}
		}

		return best.getValue()+"\n COSTO TOTALE= "+best.getCosto();
	}

	private void punteggioCombinazioni(int mese) {
		for(Combo cx : combinazioni) {
		double costo=0;
		ArrayList<Citta> sol = cx.getCombo();
		for(int i=0;i<15;i++) {
			costo += mdao.getDay(sol.get(i).getNome(),mese,i+1);
		}
		costo = costo + (cx.tripCount()*COST);
		cx.setCosto(costo);
		}
	}	

	

	//metodo che ritorna se è possibile inserire la scelta della città
	//TESTATO, funziona! cntrollare come viene richiamato nella ricorsione
	
	public boolean isAvaiable(Combo parziale, Citta chosen) {
		boolean av = false;
		int cont=1;
		int level = parziale.getCombo().size();
		
		if (level<NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN)
			av=true;
	
		//controlliamo le città precedenti
		else if(level>=NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN) {
			for(int i=1; i<NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN;i++) {
					if(parziale.getCombo().get(level-1).equals(parziale.getCombo().get((level-1)-i))) {
					cont++;
				}
			}
			if(cont==NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN) {
			av=true;
		}
			else if(cont<3 && parziale.getCombo().get(level-1).equals(chosen)==true) {
			av=true;
			}
		}
		return av;
	}
	
		
	
}
