package it.polito.tesi;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tesi.model.HotspotAndArea;
import it.polito.tesi.model.Model;
import it.polito.tesi.model.Route;
import javafx.collections.FXCollections;
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
    private TableView<HotspotAndArea> tableView;    

    @FXML
    private TableColumn<HotspotAndArea, Integer> numareaColumn;

    @FXML
    private TableColumn<HotspotAndArea, String> locationColumn;

    @FXML
    private TableColumn<HotspotAndArea, String> streetColumn;

    @FXML
    private TableColumn<HotspotAndArea, String> cityColumn;

    @FXML
    private TableColumn<HotspotAndArea, String> SSIDColumn;

    @FXML
    private TableColumn<HotspotAndArea, String> infoColumn;

    @FXML
    private Button backButton;
    
    @FXML
    private Label labelinfo;
    
    @FXML
    private Label labelinfo2;

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
		stage.setResizable(false);
		
    }
    
    private void doSchedule(boolean approximate) {
    	
    	labelinfo.setText("Number of areas (connected components) : "+ model.getCC().size());
			
    	List<Route> routes = model.schedule(approximate);
    	
    	ObservableList<HotspotAndArea> oblist = FXCollections.observableArrayList();
    	for(Route r: routes) {
    		for(HotspotAndArea ha: r.getHotspots()) {
    			oblist.add(ha);
    		}
    		oblist.add(new HotspotAndArea());
    	}
			
		numareaColumn.setCellValueFactory(new PropertyValueFactory<HotspotAndArea, Integer>("numarea"));
		locationColumn.setCellValueFactory(new PropertyValueFactory<HotspotAndArea, String>("location"));
		streetColumn.setCellValueFactory(new PropertyValueFactory<HotspotAndArea, String>("street"));
		cityColumn.setCellValueFactory(new PropertyValueFactory<HotspotAndArea, String>("city"));
		SSIDColumn.setCellValueFactory(new PropertyValueFactory<HotspotAndArea, String>("SSID"));
		infoColumn.setCellValueFactory(new PropertyValueFactory<HotspotAndArea, String>("remark"));
		
		tableView.setItems(oblist);
		
		StringBuilder sb = new StringBuilder();
		double totTime = 0;
		for(Route r : routes) {
			sb.append("Area "+r.getArea());
			int numop = 0;
			if(r.getHotspots().size() == 1)
				numop = 1;
			else if(r.getHotspots().get(0).equals(r.getHotspots().get(r.getHotspots().size()-1)))
				numop = (r.getHotspots().size()-1);
			else
				numop = r.getHotspots().size();
			
			sb.append(" - Operations: "+numop);
			BigDecimal b = new BigDecimal(r.getRouteTime());
			sb.append(", moving time: " + b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+" min\n");
			totTime += r.getRouteTime();
			totTime += r.getRipTime();
		}
		BigDecimal bd = new BigDecimal(totTime/60.0);                //in ore
		sb.append("Total working time: " + bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + " hours.");  
		
		labelinfo2.setText(sb.toString());
		
   
    }

    @FXML
    void initialize() {
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'Untitled'.";
        assert numareaColumn != null : "fx:id=\"numareaColumn\" was not injected: check your FXML file 'TableView.fxml'.";
        assert locationColumn != null : "fx:id=\"locationColumn\" was not injected: check your FXML file 'Untitled'.";
        assert streetColumn != null : "fx:id=\"streetColumn\" was not injected: check your FXML file 'Untitled'.";
        assert cityColumn != null : "fx:id=\"cityColumn\" was not injected: check your FXML file 'Untitled'.";
        assert SSIDColumn != null : "fx:id=\"SSIDColumn\" was not injected: check your FXML file 'Untitled'.";
        assert infoColumn != null : "fx:id=\"infoColumn\" was not injected: check your FXML file 'Untitled'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'Untitled'.";
        assert labelinfo != null : "fx:id=\"labelinfo\" was not injected: check your FXML file 'Untitled'.";
        assert labelinfo2 != null : "fx:id=\"labelinfo2\" was not injected: check your FXML file 'Untitled'.";
    }

	public void setModel(Model model, boolean approximate) {
		this.model = model;
		
		this.doSchedule(approximate);
		
	}
}
