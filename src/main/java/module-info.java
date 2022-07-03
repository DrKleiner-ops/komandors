module ru.komandor.komandors {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;


	opens ru.komandor.komandors to javafx.fxml;
	exports ru.komandor.komandors;
}