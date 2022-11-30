package org.generation.italy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

	private static final String URL = "jdbc:mysql://localhost:8889/db_nations";
	private static final String USER = "root";
	private static final String PSW = "root";
	
	public static void main(String[] args) {
		
		query1();
	}

	
	
	// Ottenere lista di nazioni mostrando nome, id, nome della regione e del continente
	// ordinata per nome della nazione
	// Richiedere poi all'utende di ricercare i nomi delle nazioni da lui inserite
	
	
	private static void query1() {
		
		try (Connection con = DriverManager.getConnection(URL, USER, PSW)){
			
			Scanner sc = new Scanner(System.in);
			
			System.out.println("Inserisci il nome della nazione: ");
			String countryName = sc.next();

			final String sql = 
					"SELECT countries.country_id, countries.name, regions.name, continents.name\n"
					+ "FROM countries\n"
					+ "	JOIN regions\n"
					+ "		ON regions.region_id = countries.region_id\n"
					+ "	JOIN continents\n"
					+ "		ON continents.continent_id = regions.continent_id\n"
					+ "WHERE countries.name = ?"
					+ "ORDER BY countries.name ";
			
			
			try (PreparedStatement ps = con.prepareStatement(sql)) {
				
				ps.setString(1, countryName);
				
				try (ResultSet rs = ps.executeQuery()) {
					
					while(rs.next()) {
						
						final int id = rs.getInt(1);
						final String name = rs.getString(2);
						final String regionName= rs.getString(3);
						final String continentName= rs.getString(3);
						
						System.out.println(
								"\n" + id 
								+ " - " + name
								+ " - " + regionName
								+ " - " + continentName);
					}
				}
				
				sc.close();
			}
		} catch (SQLException ex) {
			
			ex.printStackTrace();
		}
	}
}