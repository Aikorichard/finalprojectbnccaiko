package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.menu.Menu;



public class MenuService {
	
	private static Database db = Database.getInstance();
	
	public static void save(Menu menu) {
		String query  = "INSERT INTO menu VALUES (?, ?, ?, ?)";
		
		try {
			PreparedStatement ps = db.prepareStatement(query);
			ps.setString(1, menu.getKode());
			ps.setString(2, menu.getNama());
			ps.setInt(3, menu.getStok());
			ps.setInt(4, menu.getHarga());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();	
		}
	}
	
	public static void update(Menu menu) {
		String query  = "UPDATE menu SET nama = ?, stok_kode = ?, harga = ? WHERE kode = ?";
		try {
			PreparedStatement ps = db.prepareStatement(query);
			ps.setString(1, menu.getNama());
			ps.setInt(2, menu.getStok());
			ps.setInt(3, menu.getHarga());
			ps.setString(4, menu.getKode());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();	
		}
	}
	
	public static void delete(Menu menu) {
		String query  = "DELETE FROM menu WHERE kode = ?";
		
		try {
			PreparedStatement ps = db.prepareStatement(query);
			ps.setString(1, menu.getKode());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();	
		}
	}

	public static ObservableList<Menu> getAllItems(){
		String query = "SELECT * FROM menu";
		ObservableList<Menu> menuList = FXCollections.observableArrayList();
		try {
			PreparedStatement ps = db.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				menuList.add(new Menu(rs.getString("kode"), rs.getString("nama"), (rs.getInt("stok_kode")), rs.getInt("harga")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return menuList;
	}
}
