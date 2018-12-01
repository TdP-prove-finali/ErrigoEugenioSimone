package it.polito.tesi;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tesi.model.Borough;
import it.polito.tesi.model.Model;
import it.polito.tesi.model.Provider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
    private CheckBox checkApproximate;
    
    @FXML
    private Button btnResearch;
    
    @FXML
    private Label labelinfo;
   
    @FXML
    void doSchedule(ActionEvent event) throws IOException{
    	
    	StringBuilder es = new StringBuilder();
    	boolean err = false;
    	
    	try {
			int maxDist = Integer.parseInt(txtDistance.getText().trim());
			Provider prov = comboProvider.getValue();
			Borough boro = comboBorough.getValue();
			double failure = sldFailure.getValue();
			boolean allIsSelected = checkAllBorough.isSelected();
			boolean approximate = checkApproximate.isSelected();
			
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
				System.out.println("Graph correctly created.\n");
				
				this.changeScene(event, approximate);
				
			}else
				labelinfo.setText(es.toString());
			
		} catch (NumberFormatException e) {
			labelinfo.setText("Insert numeric value in Max. distance field.\n");
		}
    }
    
    private void changeScene(ActionEvent event, boolean approximate) throws IOException {
    	
    	Stage stage = (Stage) btnResearch.getScene().getWindow();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("TableView.fxml"));
		BorderPane root = (BorderPane) loader.load();
		
		TableViewController TVcontroller = loader.getController();
		TVcontroller.setModel(model, approximate);

		Scene scene2 = new Scene(root);
		stage.setScene(scene2);
		stage.show();
		
    }

    @FXML
    void initialize() {
        assert comboProvider != null : "fx:id=\"comboProvider\" was not injected: check your FXML file 'HotspotNY.fxml'.";
        assert txtDistance != null : "fx:id=\"txtDistance\" was not injected: check your FXML file 'HotspotNY.fxml'.";
        assert sldFailure != null : "fx:id=\"sldFailure\" was not injected: check your FXML file 'HotspotNY.fxml'.";
        assert comboBorough != null : "fx:id=\"comboBorough\" was not injected: check your FXML file 'HotspotNY.fxml'.";
        assert btnResearch != null : "fx:id=\"btnResearch\" was not injected: check your FXML file 'HotspotNY.fxml'.";
        assert checkAllBorough != null : "fx:id=\"checkAllBorough\" was not injected: check your FXML file 'HotspotNY.fxml'.";
        assert checkApproximate != null : "fx:id=\"checkApproximate\" was not injected: check your FXML file 'HotspotNY.fxml'.";
        assert labelinfo != null : "fx:id=\"labelinfo\" was not injected: check your FXML file 'HotspotNY.fxml'.";

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
    	
    	checkApproximate.setSelected(true);
    	

    }
    
    
}
