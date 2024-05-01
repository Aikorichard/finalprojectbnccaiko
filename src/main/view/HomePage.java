package main.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import main.Main;
import main.menu.Menu;
import service.MenuService;


public class HomePage {
	private Stage stage;
	private BorderPane root = new BorderPane();
	private Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
	private Label headerLbl = new Label("Manage Your Pudding! ^^");
	private Label kodeLbl = new Label("Kode Menu");
	private Label namaLbl = new Label("Nama Menu");
	private Label hargaLbl = new Label("Harga Menu");
	private Label stokLbl = new Label("Stok Menu");
	private TextField kodeTf = new TextField();
	private TextField namaTf = new TextField();
	private TextField hargaTf = new TextField();
	private TextField stokTf = new TextField();
	private TableView<Menu> table = new TableView<>();
	private TableColumn<Menu, String> kodeCol = new TableColumn<>("Kode Menu");
	private TableColumn<Menu, String> namaCol = new TableColumn<>("Nama Menu");
	private TableColumn<Menu, Integer> stokCol = new TableColumn<>("Stok Menu");
	private TableColumn<Menu, Integer> hargaCol = new TableColumn<>("Harga Menu");
	private Button addBtn = new Button("Add New Menu");
	private Button updateBtn = new Button("Update Menu");
	private Button deleteBtn = new Button("Delete Menu");
	private HBox buttonBox = new HBox(addBtn, updateBtn, deleteBtn);
	private GridPane gp = new GridPane();
	private ObservableList<Menu> menuList = FXCollections.observableArrayList();
	private Menu selectedMenu;
	
	public HomePage(Stage stage) {
		this.stage = stage;
		this.initMenu();
		this.setComponent();
		this.setStyle();
		this.setTableColumns();
		this.populateTable();
		this.handleButton();
		this.handleTableListener();
	}
	
	private void initMenu() {
//	    menuList.add(new Menu("PD001", "Pudding Coklat", 10, 100000));
//	    menuList.add(new Menu("PD002", "Pudding Vanilla", 5, 50000));
//	    menuList.add(new Menu("PD003", "Pudding Strawberry", 4, 40000));
//	    menuList.add(new Menu("PD004", "Pudding RedVelvet", 3, 30000)); 
	}

	@SuppressWarnings("unchecked")
	private void setComponent() {
		gp.add(headerLbl, 0, 0, 2, 1);
		gp.add(kodeLbl, 0, 1);
		gp.add(kodeTf, 1, 1);
		gp.add(namaLbl, 0, 2);
		gp.add(namaTf, 1, 2);
		gp.add(stokLbl, 0, 3);
		gp.add(stokTf, 1, 3);
		gp.add(hargaLbl, 0, 4);
		gp.add(hargaTf, 1, 4);
		gp.add(buttonBox, 0, 5, 2, 1);
		
		table.getColumns().addAll(kodeCol, namaCol, stokCol, hargaCol);
		
		root.setTop(table);
		root.setCenter(gp);
		stage.setScene(scene);
	}
	
	@SuppressWarnings("deprecation")
	private void setStyle() {
		root.setPadding(new Insets(40));
		gp.setAlignment(Pos.CENTER);
		gp.setHgap(10);
		gp.setVgap(10);
		GridPane.setHalignment(headerLbl, HPos.CENTER);
		GridPane.setHalignment(addBtn, HPos.CENTER);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		stokTf.setMaxWidth(Double.MAX_VALUE);
		addBtn.setMinWidth(100);
		updateBtn.setMinWidth(100);
		deleteBtn.setMinWidth(100);
	}
	
	private void populateTable() {
		menuList = MenuService.getAllItems();
		table.setItems(FXCollections.observableArrayList(menuList));
		this.clearSelection();
	}
	
	private void clearSelection() {
		kodeTf.clear();
		namaTf.clear();
		stokTf.clear();
		hargaTf.clear();
	}
	
	private void setTableColumns() {
		kodeCol.setCellValueFactory(new PropertyValueFactory<Menu, String>("kode"));
		namaCol.setCellValueFactory(new PropertyValueFactory<Menu, String>("nama"));
		stokCol.setCellValueFactory(new PropertyValueFactory<Menu, Integer>("stok"));
		hargaCol.setCellValueFactory(new PropertyValueFactory<Menu, Integer>("harga"));
	}
	
	private void handleButton() {
		addBtn.setOnAction(button ->{
			String kode = kodeTf.getText();
			String nama = namaTf.getText();
			String stok = stokTf.getText();
			String harga = hargaTf.getText();
			
			if (kode.isEmpty() || nama.isEmpty() || stok.isEmpty() || harga.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR	);
				alert.initOwner(stage);
				alert.setTitle("Error");
				alert.setHeaderText("Validation Error");
				alert.setContentText("All fields must be filled!");
				alert.showAndWait(); 
				return;
			}
			
			try {
				Integer.valueOf(harga);
			} catch (Exception e) {
				alert(AlertType.ERROR, "Error", "Validation Error", "Price must be numeric!");
				return;
			}
			
			if (!(Integer.valueOf(harga) >= 30000)) {
				alert(AlertType.ERROR, "Error", "Validation Error", "Price cannot be lower than 30000!");
					return;
			}
			
			
			MenuService.save(new Menu(kode, nama, Integer.valueOf(stok), Integer.valueOf(harga)));
			this.populateTable();
			alert(AlertType.INFORMATION, "Message", "Information", "Menu Sucessfully Added!");
			return;
		});
		
		updateBtn.setOnAction(button -> {
			String kode = selectedMenu.getKode();
			String nama = namaTf.getText();
			String stok = stokTf.getText();
			String harga = hargaTf.getText();
			
			MenuService.update(new Menu(kode, nama, Integer.valueOf(stok), Integer.parseInt(harga)));
			this.populateTable();
			alert(AlertType.INFORMATION, "Message", "Information", "Menu Sucessfully Updated!");
		});
		
		deleteBtn.setOnAction(button -> {
			MenuService.delete(selectedMenu);
			this.populateTable();
			alert(AlertType.INFORMATION, "Message", "Information", "Menu Sucessfully Deleted!");
		});
	}
	
	private void handleTableListener() {
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
		if (newValue != null) {
			selectedMenu = newValue;
			kodeTf.setText(newValue.getKode());
			namaTf.setText(newValue.getNama());
			stokTf.setText(String.valueOf(newValue.getStok()));
			hargaTf.setText(String.valueOf(newValue.getHarga()));
			}	
		});
	}
		private void alert(AlertType alertType, String title, String header, String content) {
			Alert alert = new Alert(alertType);
			alert.initOwner(stage);
			alert.setTitle(title);
			alert.setHeaderText(header);
			alert.setContentText(content);
			alert.showAndWait(); 
	}
}
