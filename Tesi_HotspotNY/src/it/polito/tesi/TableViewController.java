package it.polito.tesi;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tesi.model.Hotspot;
import it.polito.tesi.model.Model;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TableViewController {

	private Model model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Hotspot> tableView;   

    @FXML
    private TableColumn<Hotspot, String> locationColumn;

    @FXML
    private TableColumn<Hotspot, String> streetColumn;

    @FXML
    private TableColumn<Hotspot, String> cityColumn;

    @FXML
    private TableColumn<Hotspot, String> SSIDColumn;

    @FXML
    private TableColumn<Hotspot, String> infoColumn;

    @FXML
    private Button backButton;
    
    @FXML
    private Label labelinfo;

    @FXML
    void goBackScene(ActionEvent event) throws IOException {

    	Stage stage = (Stage) backButton.getScene().getWindow();
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("HotspotNY.fxml"));
		BorderPane root = (BorderPane) loader.load();
		
		HNYController controller = loader.getController();
		controller.setModel(model);
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
    }
    
    private void doSchedule(boolean approximate) {
    	
    	labelinfo.setText("Number of areas (connected components) : "+ model.getCC().size()+"\n");
//    	if(approximate)
//    		labelinfo.setText("*approximated");
		
			ObservableList<Hotspot> oblist = model.schedule(approximate);
			locationColumn.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("Location"));
	        streetColumn.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("Street"));
	        cityColumn.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("City"));
	        SSIDColumn.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("SSID"));
	        infoColumn.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("Remark"));
			tableView.setItems(oblist);
			
   
    }

    @FXML
    void initialize() {
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'Untitled'.";
        assert locationColumn != null : "fx:id=\"locationColumn\" was not injected: check your FXML file 'Untitled'.";
        assert streetColumn != null : "fx:id=\"streetColumn\" was not injected: check your FXML file 'Untitled'.";
        assert cityColumn != null : "fx:id=\"cityColumn\" was not injected: check your FXML file 'Untitled'.";
        assert SSIDColumn != null : "fx:id=\"SSIDColumn\" was not injected: check your FXML file 'Untitled'.";
        assert infoColumn != null : "fx:id=\"infoColumn\" was not injected: check your FXML file 'Untitled'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'Untitled'.";
        assert labelinfo != null : "fx:id=\"labelinfo\" was not injected: check your FXML file 'Untitled'.";
    }

	public void setModel(Model model, boolean approximate) {
		this.model = model;
		
		this.doSchedule(approximate);
		
	}
}
