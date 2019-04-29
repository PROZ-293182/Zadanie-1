package calculator.git;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * Reacts on user's actions, shows alerts, creates model and makes it does calculations.
 * 
 * @author Magdalena Zych
 *
 */
public class Controller {
	
	/**
	 * A button with "7".    
	 */
	@FXML
	private Button seven;
	
	/**
	 * A button with "+".    
	 */
	@FXML
	private Button add;

	/**
	 * A button with "=".    
	 */
	@FXML
	private Button equals;

	/**
	 * A button with "-".    
	 */
	@FXML
	private Button sub;

	/**
	 * A button with "3".    
	 */
	@FXML
	private Button three;
	
	/**
	 * A button with ".".    
	 */
	@FXML
	private Button comma;

	/**
	 * A button with "2".    
	 */
	@FXML
	private Button two;

	/**
	 * A button with "x".   
	 */
	@FXML
	private Button mul;

	/**
	 * A button with "6".    
	 */
	@FXML
	private Button six;
	
	/**
	 * A button with "5".    
	 */
	@FXML
	private Button five;

	/**
	 * A button with "/".    
	 */
	@FXML
	private Button div;

	/**
	 * A button with "9".    
	 */
	@FXML
	private Button nine;

	/**
	 * A button with "0".    
	 */
	@FXML
	private Button zero;

	/**
	 * A button with "1".    
	 */
	@FXML
	private Button one;

	/**
	 * A button with "4".    
	 */
	@FXML
	private Button four;

	/**
	 * A button with "8".    
	 */
	@FXML
	private Button eight;

	/**
	 * A button with the root sign.    
	 */
	@FXML
	private Button root;

	/**
	 * A button with "1/x".    
	 */
	@FXML
	private Button rec;

	/**
	 * A button with "+/-".    
	 */
	@FXML
	private Button sign;

	/**
	 * A button with "C"/"AC".    
	 */
	@FXML
	private Button ac;

	/**
	 * A text field showing the current number.     
	 */
	@FXML
	private TextField display;

	/**
	 * A text field showing the current operations.     
	 */
	@FXML
	private TextField operations;

	/**
	 * String object which contains the latest 2-argument-operation sign.       
	 */
	private String operator = "";

	/**
	 * String object which contains current mathematics expression. 
	 */
	private String expression = "";

	/**
	 * Model object which does the calculations.      
	 */
	private Model model = new Model();

	/**
	 * Alert object which enables to show alerts.      
	 */
	private Alert alert = new Alert(AlertType.ERROR);

	/**
	 *  Boolean variable which shows if division was the last operation.
	 */
	private boolean division = false;


	/**
	 *  Called to initialize a controller after its root element has been completely processed.
	 */
	@FXML
	private void initialize() {
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

	/**
	 *  Sets the display text after pressing a digit button.
	 *  
	 *  @param sign The digit which was pressed.
	 */
	@FXML
	private void writeDigit(String sign) {
		if (display.getText().equals("0")) {
			display.setText(sign);
		} else
			display.setText(display.getText() + sign);

		if (ac.getText() == "AC")
			ac.setText("C");
	}

	/**
	 *  Updates the expression after pressing an operation button.
	 *  
	 *  @param op The sign of an operator which was pressed.
	 */
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

	/**
	 *  Sets the display text after calculation.
	 *  
	 *  @param event Action event which caused calling this method.
	 */
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
			if (Double.parseDouble(number) == 0) 
				return;
			display.setText(model.calculate("(-1)*" + number + "d"));
			break;
		}

		if (display.getText().contains("."))
			comma.setDisable(true);
		else
			comma.setDisable(false);
		operations.setText(expression.replace("d", ""));

	}

	/**
	 *  Shows the result of a math expression. 
	 *  
	 *  @param event Action event which caused calling this method.
	 */
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

	/**
	 *  Shows the alert window.
	 *  
	 *  @param reason Text set as a content in an alert window.
	 */
	private void showErrorAlert(String reason) {
		display.setText("ERROR");
		alert.setContentText(reason);
		alert.showAndWait();
		display.setText("0");
	}

}