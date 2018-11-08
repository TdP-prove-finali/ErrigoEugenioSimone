package it.polito.tesi;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tesi.model.Borough;
import it.polito.tesi.model.Hotspot;
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
    	
    	StringBuilder es = new StringBuilder();
    	boolean err = false;
    	
    	try {
			int maxDist = Integer.parseInt(txtDistance.getText().trim());
			Provider prov = comboProvider.getValue();
			Borough boro = comboBorough.getValue();
			double failure = sldFailure.getValue();
			boolean allIsSelected = checkAllBorough.isSelected();
			
			if(prov==null) {                   
				err = true;
				es.append("Choose a value in Provider box.\n");
			}
			
			if(!checkAllBorough.isSelected() && boro==null) { 
				err = true;
				es.append("Choose a value in Borough box.\n");
			}
    	
			if(maxDist<500) {
				err = true;
				es.append("Insert a value greater than 500.\n");       
			}
			
			if(!err){
				model.createGraph(prov, boro, maxDist, failure, allIsSelected);
				txtResult.appendText("Graph correctly created.\n");
				
				if(model.getCC().size()!=0) {
					txtResult.appendText("Connected components: "+ model.getCC().size()+"\n");
				
					for(Set<Hotspot> compconn : model.getCC()) {
						StringBuilder s = new StringBuilder();
						
						List<Hotspot> tsp = model.schedule(compconn);
						txtResult.appendText("---TSP---\n");
						
						for(Hotspot h: tsp)
							s.append(h.toString()+"\n");
						
						txtResult.appendText(s.toString());				
					}
				
				}else {
					txtResult.appendText("Graph has not connected components. Cannot schedule!");
					throw new IllegalArgumentException("Graph has not connected components. Cannot schedule!");
				}
				
			}else
				txtResult.appendText(es.toString());
			
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
    		else
    			comboBorough.setDisable(false);
    	});
    	

    }
    
    
}
