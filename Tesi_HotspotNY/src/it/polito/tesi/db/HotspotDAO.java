package it.polito.tesi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tesi.model.Borough;
import it.polito.tesi.model.BoroughIdMap;
import it.polito.tesi.model.Hotspot;
import it.polito.tesi.model.Provider;

public class HotspotDAO {
	
	/**
	 * Metodo per caricare tutti i providers dal db
	 * @return List<Provider>
	 */
	public List<Provider> getAllProviders() {
		
		List<Provider> result = new ArrayList<Provider>();
		
		String sql = "SELECT DISTINCT Provider FROM nyc_wifi_hotspot_locations ORDER BY Provider ";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				result.add(new Provider(rs.getString("Provider")));

			}

			st.close();
			conn.close();

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Database error", e);
		}
	}

	/**
	 * Metodo per caricare tutti i dati relativi ai distretti 
	 * @return List of Borough
	 */
	public List<Borough> getAllBoroughs() {
		
		List<Borough> result = new ArrayList<Borough>();
		
		String sql = "SELECT DISTINCT Borough, BoroName, BoroCode FROM nyc_wifi_hotspot_locations ORDER BY BoroName ";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				String sigla = rs.getString("Borough");
				boolean flag = false;
				
				for(Borough b : result) {                               //controllo necessario perchè alcuni borough contengono nomi e codici non corrispondenti alla sigla
					if(b.getSigla().compareTo(sigla)==0) 
						flag = true;
				}
				
				if(flag==false)
					result.add(new Borough(sigla, rs.getString("BoroName"), rs.getInt("BoroCode")));
				
			}

			st.close();
			conn.close();

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Database error", e);
		}
	}

	/**
	 * Metodo per trovare i vertici del grafo, dai parametri impostati dall'utente
	 * @param 
	 * @param
	 * @return List of Hotspot
	 */
	public List<Hotspot> getVertex(Provider provider, Borough borough) {
		
		List<Hotspot> result = new ArrayList<>();
		
		String sql = "SELECT * FROM nyc_wifi_hotspot_locations WHERE Borough = ? AND Provider = ? ORDER BY OBJECTID ";
	
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, borough.getSigla());
			st.setString(2, provider.getName());
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				result.add(new Hotspot(rs.getInt("OBJECTID"), borough, rs.getString("Type"), provider,
										rs.getString("Name"), rs.getString("Location"), rs.getDouble("Latitude"), 
										rs.getDouble("Longitude"), rs.getString("City"), rs.getString("Remarks"), rs.getString("SSID")));
				
			}

			st.close();
			conn.close();

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Database error", e);
		}
	}
	
	/**
	 * Metodo per caricare tutti i vertici su tutta la citta'
	 * @param provider
	 * @param bmap
	 * @return
	 */
	public List<Hotspot> getAllVertex(Provider provider, BoroughIdMap bmap) {
		
		List<Hotspot> result = new ArrayList<>();
		
		String sql = "SELECT * FROM nyc_wifi_hotspot_locations WHERE Provider = ? ORDER BY OBJECTID ";
	
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, provider.getName());
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				result.add(new Hotspot(rs.getInt("OBJECTID"), bmap.get(rs.getString("Borough")), rs.getString("Type"), provider,
										rs.getString("Name"), rs.getString("Location"), rs.getDouble("Latitude"), 
										rs.getDouble("Longitude"), rs.getString("City"), rs.getString("Remarks"), rs.getString("SSID")));
				
			}

			st.close();
			conn.close();

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Database error", e);
		}
	}
	

}
