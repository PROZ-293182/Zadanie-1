module calculator {
	exports calculator.git;

	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires jdk.jshell;

	opens calculator.git to javafx.fxml;
}