package calculator.git;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class Controller {
	@FXML
	private Button seven;

	@FXML
	private Button add;

	@FXML
	private Button equals;

	@FXML
	private Button sub;

	@FXML
	private Button three;

	@FXML
	private Button comma;

	@FXML
	private Button two;

	@FXML
	private Button mul;

	@FXML
	private Button six;

	@FXML
	private Button five;

	@FXML
	private Button div;

	@FXML
	private Button nine;

	@FXML
	private Button zero;

	@FXML
	private Button one;

	@FXML
	private Button four;

	@FXML
	private Button eight;

	@FXML
	private Button root;

	@FXML
	private Button rec;

	@FXML
	private Button sign;

	@FXML
	private Button ac;

	@FXML
	private TextField display;

	@FXML
	private TextField operations;

	private String operator = "";
	private String expression = "";
	Model model = new Model();
	private Alert alert = new Alert(AlertType.ERROR);
	private boolean division = false;

	@FXML
	void initialize() {
		display.setText("0");
		zero.setOnAction(e -> writeDigit("0"));
		one.setOnAction(e -> writeDigit("1"));
		two.setOnAction(e -> writeDigit("2"));
		three.setOnAction(e -> writeDigit("3"));
		four.setOnAction(e -> writeDigit("4"));
		five.setOnAction(e -> writeDigit("5"));
		six.setOnAction(e -> writeDigit("6"));
		seven.setOnAction(e -> writeDigit("7"));
		eight.setOnAction(e -> writeDigit("8"));
		nine.setOnAction(e -> writeDigit("9"));

		ac.setOnAction(e -> {
			display.setText("0");
			comma.setDisable(false);
			if (ac.getText() == "AC") {
				expression = "";
				operations.setText(expression);
			}
			else
				ac.setText("AC");
		});

		comma.setOnAction(e -> {
			display.setText(display.getText() + ".");
			comma.setDisable(true);
			if (ac.getText() == "AC")
				ac.setText("C");
		});

		add.setOnAction(e -> processTwoArgOperator("+"));
		sub.setOnAction(e -> processTwoArgOperator("-"));
		mul.setOnAction(e -> processTwoArgOperator("*"));
		div.setOnAction(e -> processTwoArgOperator("/"));
		root.setOnAction(e -> processOneArgOperator(e));
		rec.setOnAction(e -> processOneArgOperator(e));
		sign.setOnAction(e -> processOneArgOperator(e));
		equals.setOnAction(e -> processEquality(e));

		alert.setHeaderText("The argument is invalid");
	}

	@FXML
	private void writeDigit(String sign) {
		if (display.getText().equals("0")) {
			display.setText(sign);
		} else
			display.setText(display.getText() + sign);

		if (ac.getText() == "AC")
			ac.setText("C");
	}

	@FXML
	private void processTwoArgOperator(String op) {
		if (division && Double.parseDouble(display.getText()) == 0) {
			showErrorAlert("That operation (division by zero) is not permitted.");
			return;
		}
		operator = op;
		expression = (expression + display.getText()) + "d" + operator;
		display.setText("0");
		operations.setText(expression.replace("d", ""));
		comma.setDisable(false);
		division = (op == "/") ? true : false;

	}

	@FXML
	private void processOneArgOperator(ActionEvent event) {
		String number = display.getText();
		switch (((Button) event.getSource()).getId()) {
		case "root":
			if (Double.parseDouble(number) < 0)
				showErrorAlert("That operation (square root of a negative number) is not permitted.");
			else
				display.setText(model.calculate("Math.sqrt(" + number + "d)"));
			break;
		case "rec":
			if (Double.parseDouble(number) == 0)
				showErrorAlert("That operation (division by zero) is not permitted.");
			else
				display.setText(model.calculate("1/" + number + "d"));
			break;
		case "sign":
			display.setText(model.calculate("(-1)*" + number + "d"));
			break;
		}

		if (display.getText().contains("."))
			comma.setDisable(true);
		else
			comma.setDisable(false);
		operations.setText(expression.replace("d", ""));

	}

	@FXML
	private void processEquality(ActionEvent event) {
		if (operator.equals(""))
			return;

		if (division && Double.parseDouble(display.getText()) == 0) {
			showErrorAlert("That operation (division by zero) is not permitted.");
			return;
		}

		expression = expression + display.getText() + "d";
		operations.setText(expression.replace("d", "") + "=");
		display.setText(model.calculate(expression));
		operator = "";
		expression = "";
		division = false;

		if (display.getText().contains("."))
			comma.setDisable(true);
		else
			comma.setDisable(false);
	}

	private void showErrorAlert(String reason) {
		display.setText("ERROR");
		alert.setContentText(reason);
		alert.showAndWait();
		display.setText("0");
	}

}