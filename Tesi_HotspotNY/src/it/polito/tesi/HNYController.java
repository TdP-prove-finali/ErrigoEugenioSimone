package it.polito.tesi;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tesi.model.Borough;
import it.polito.tesi.model.Model;
import it.polito.tesi.model.Provider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HNYController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Provider> comboProvider;

    @FXML
    private TextField txtDistance;

    @FXML
    private Slider sldFailure;

    @FXML
    private ComboBox<Borough> comboBorough;
    
    @FXML
    private CheckBox checkAllBorough;
    
    @FXML
    private Button btnResearch;

    @FXML
    private TextArea txtResult;

    @FXML
    void doSchedule(ActionEvent event) {
    	txtResult.clear();
    	
    	try {
			int maxDist = Integer.parseInt(txtDistance.getText());
			Provider prov = comboProvider.getValue();
			Borough boro = comboBorough.getValue();
			double failure = sldFailure.getValue();
			
			if(prov==null)
				txtResult.appendText("Choose a value in Provider box.\n");
			else if(!checkAllBorough.isSelected() && boro==null) 
				txtResult.appendText("Choose a value in Borough box.\n");
			else if(maxDist<500)
				txtResult.appendText("Insert a value greater than 500.\n");               //?
			else {
				model.createGraph(prov, boro, maxDist, failure);
				txtResult.appendText("Grafo creato correttamente.\n");
			}
			
		} catch (NumberFormatException e) {
			txtResult.appendText("Insert numeric value in Max. distance field.\n");
		}

    }

    @FXML
    void initialize() {
        assert comboProvider != null : "fx:id=\"comboProvider\" was not injected: check your FXML file 'HotspotNY.fxml'.";
        assert txtDistance != null : "fx:id=\"txtDistance\" was not injected: check your FXML file 'HotspotNY.fxml'.";
        assert sldFailure != null : "fx:id=\"sldFailure\" was not injected: check your FXML file 'HotspotNY.fxml'.";
        assert comboBorough != null : "fx:id=\"comboBorough\" was not injected: check your FXML file 'HotspotNY.fxml'.";
        assert btnResearch != null : "fx:id=\"btnResearch\" was not injected: check your FXML file 'HotspotNY.fxml'.";
        assert checkAllBorough != null : "fx:id=\"checkAllBorough\" was not injected: check your FXML file 'HotspotNY.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'HotspotNY.fxml'.";

    }
    
    public void setModel(Model model){
    	this.model = model;
    	
    	comboProvider.getItems().addAll(model.getAllProviders());
    	
    	comboBorough.getItems().addAll(model.getAllBoroughs());
    	
    	checkAllBorough.setOnAction(e -> {
    		if(checkAllBorough.isSelected())
    			comboBorough.setDisable(true);
    	});
    	
    	//se deseleziono checkbox, devo abilitare combo
    }
    
    
}
