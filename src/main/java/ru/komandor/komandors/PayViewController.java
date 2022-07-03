package ru.komandor.komandors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PayViewController extends HelloController {
	@FXML
	public Label sumIdd;
	@FXML
	private TextArea setPay;

	public void changeScreenButtonPushed(ActionEvent event) throws IOException {
		String cash = setPay.getText();
		String price = sumIdd.getText();
		int idCount = 0;


		if (Objects.equals(cash, price)) {
			try {
				DataAccessor dataAccessor = DataAccessor.getDataAccessor();

				while (true) try {
					idCount++;
					dataAccessor.checks(idCount, sum);
					break;
				} catch (Exception e) {
					for (CheckLinesData releaseDatum : releaseData) {
						dataAccessor.checklines(releaseDatum.getGoodsId(),
								releaseDatum.getCountLine(),
								releaseDatum.getCount(),
								releaseDatum.getSum());

					}
				}
			} catch (Exception ignored) {
			}
			System.out.println("good");
		}


		sum = (float) 0;
		usersData.removeAll();
		Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
		Scene scene = new Scene(parent);

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		String result = String.format("%.2f", sum);
		sumIdd.setText(result);

	}


}
