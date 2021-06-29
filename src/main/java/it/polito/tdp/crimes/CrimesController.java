/**
 * Sample Skeleton for 'Crimes.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.EdgeAndWeight;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class CrimesController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxCategoria"
    private ComboBox<String> boxCategoria; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="boxArco"
    private ComboBox<EdgeAndWeight> boxArco; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	if(boxCategoria.getValue()==null) {
    		txtResult.appendText("Seleziona una categoria di reato");
    		return;
    	}
    	if(boxMese.getValue()==null) {
    		txtResult.appendText("Seleziona un mese");
    		return;
    	}
    	String msg = model.doCreaGrafo((int) boxMese.getValue(), boxCategoria.getValue());
    	txtResult.appendText(msg);
    	boxArco.getItems().clear();
    	boxArco.getItems().addAll(model.getEdgeOverAvg());
    	
    }
    
    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	if(boxArco.getValue()==null) {
    		txtResult.appendText("\n\nSeleziona un arco del grafo precedentemente creato!\n\n");
    		return;
    	}
    	String msg = model.doCalcolaPercorso(boxArco.getValue());
    	txtResult.appendText(msg);
    }
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxArco != null : "fx:id=\"boxArco\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	boxCategoria.getItems().clear();
    	boxCategoria.getItems().addAll(model.getCategoryID());
    	boxMese.getItems().clear();
    	boxMese.getItems().addAll(model.getMonth());
    }
}
