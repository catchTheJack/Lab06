package it.polito.tdp.meteo;

import java.net.URL;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class MeteoController {
	 Model model = new Model();
	
	 public void setModel(Model model) {
		 this.model = model;
		 boxMese.setItems(model.getChoice());
	 }

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ChoiceBox<String> boxMese;

	@FXML
	private Button btnCalcola;

	@FXML
	private Button btnUmidita;

	@FXML
	private TextArea txtResult;

	@FXML
	void doCalcolaSequenza(ActionEvent event) {
		int mese =Integer.parseInt(boxMese.getValue());
		long start = System.nanoTime();
		model.cercaComboPubblico();
		txtResult.clear();
		txtResult.setText("PERCORSO MIGLIORE PER IL MESE "+mese+" =\n"+model.trovaMigliore(mese));
		long stop = System.nanoTime();
		txtResult.appendText("\ntempo di esecuzione="+((stop-start)/1e6));
	}

	@FXML
	void doCalcolaUmidita(ActionEvent event) {
		String mese =boxMese.getValue();
		txtResult.setText(model.getUmiditaMedia(Integer.parseInt(mese)));

	}

	@FXML
	void initialize() {
		assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Meteo.fxml'.";
	}

}
