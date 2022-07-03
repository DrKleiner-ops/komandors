package ru.komandor.komandors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
	public final static ObservableList<Cart> usersData = FXCollections.observableArrayList();
	public final static ObservableList<CheckLinesData> releaseData = FXCollections.observableArrayList();
	public static Float sum = (float) 0;
	private final ObservableList<Goods> dataList = FXCollections.observableArrayList();
	private ObservableList<String> hiddenList;

	@FXML
	private Label sumId;
	@FXML
	private TableView<Goods> tvMain;
	@FXML
	private TableView<Cart> cartTable;
	@FXML
	private TextField filterField;

	@FXML
	void onBtnOpenClick() {

		int count = 0;
		CartElement cartElement = new CartElement(tvMain.getSelectionModel().getSelectedItem().getName(), tvMain.getSelectionModel().getSelectedItem().getCoast());

		String str = cartElement.getName();
		Float plusSum = cartElement.getCoast();
		hiddenList.add(str);
		for (String res : hiddenList) {
			if (Objects.equals(res, str)) count++;
		}
		if (count > 1) {
			for (int i = 0; i < usersData.size(); i++) {
				if (Objects.equals(usersData.get(i).getName(), str)) {
					usersData.remove(i);
				}
			}
		}
		usersData.add(new Cart(cartElement.getName(), count, cartElement.getCoast()));
		cartTable.setItems(usersData);
		sum = sum + plusSum;
		String result = String.format("%.2f", sum);
		sumId.setText(result);
	}

	@FXML
	void onBtnDeleteClick() {
		Cart cart = new Cart(cartTable.getSelectionModel().getSelectedItem().getName(), cartTable.getSelectionModel().getSelectedItem().getCount(), cartTable.getSelectionModel().getSelectedItem().getCoast());
		int counter = cart.getCount();
		if (counter > 1) {
			cart.setCount(counter - 1);
			for (int i = 0; i < usersData.size(); i++) {
				if (Objects.equals(usersData.get(i).getName(), cart.getName())) {
					usersData.set(i, cart);
					hiddenList.remove(cart.getName());
				}

			}
		} else
			for (int i = 0; i < usersData.size(); i++) {
				if (Objects.equals(usersData.get(i).getName(), cart.getName())) {
					usersData.remove(i);
					hiddenList.remove(cart.getName());
				}

			}
		cartTable.setItems(usersData);
		sum = sum - cart.getCoast();
		String result = String.format("%.2f", sum);
		sumId.setText(result);
	}


	public void changeScreenButtonPushed(ActionEvent event) throws IOException {
		int goodsId = 0;
		int countLine;
		int count;
		float sum;
		for (int i = 0; i < usersData.size(); i++) {
			for (Goods goods : dataList) {
				if (Objects.equals(usersData.get(i).getName(), goods.getName()))
					goodsId = goods.getId();

			}
			countLine = i;
			count = usersData.get(i).getCount();
			sum = count * usersData.get(i).getCoast();
			CheckLinesData checkLinesData = new CheckLinesData(goodsId, countLine, count, sum);
			releaseData.add(checkLinesData);
		}


		Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("pay-view.fxml")));
		Scene scene = new Scene(parent);

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {

			cartTable.getColumns().clear();
			cartTable.getItems().clear();
			tvMain.getColumns().clear();
			tvMain.getItems().clear();

			DataAccessor dataAccessor = DataAccessor.getDataAccessor();

			TableColumn<Goods, String> idCol = new TableColumn<>("id");
			TableColumn<Goods, String> nameCol = new TableColumn<>("name");
			TableColumn<Goods, String> coastCol = new TableColumn<>("coast");

			TableColumn<Cart, String> cartNameCol = new TableColumn<>("name");
			TableColumn<Cart, String> cartCountCol = new TableColumn<>("count");
			TableColumn<Cart, String> cartCoastCol = new TableColumn<>("coast");

			cartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
			cartCountCol.setCellValueFactory(new PropertyValueFactory<>("count"));
			cartCoastCol.setCellValueFactory(new PropertyValueFactory<>("coast"));

			idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
			nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
			coastCol.setCellValueFactory(new PropertyValueFactory<>("coast"));


			tvMain.getColumns().addAll(idCol, nameCol, coastCol);
			try {
				dataList.addAll(dataAccessor.getGoods());

			} catch (SQLException e) {
				e.printStackTrace();
			}
			FilteredList<Goods> filteredData = new FilteredList<>(dataList, b -> true);

			filterField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(employee -> {

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();

				return employee.getName().toLowerCase().contains(lowerCaseFilter);
			}));
			SortedList<Goods> sortedData = new SortedList<>(filteredData);

			sortedData.comparatorProperty().bind(tvMain.comparatorProperty());
			tvMain.setItems(sortedData);

			cartTable.getColumns().addAll(cartNameCol, cartCountCol, cartCoastCol);
			hiddenList = FXCollections.observableArrayList();
		} catch (Exception ignored) {

		}

	}
}