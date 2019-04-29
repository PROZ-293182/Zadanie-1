package calculator.git;

import java.util.List;
import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;

/**
 * Gets a mathematics expression,  calculates it and returns the final value.
 * 
 * @author Magdalena Zych
 *
 */
public class Model {
	
	/**
	 * JShell object which enables to do calculations using JShell. 
	 */
	private JShell jshell;
	
	/**
	 * While creating a new Model object, a new JShell object is created.
	 */
	public Model() {
		jshell = JShell.create();
	}

	/**
	 * Calculates the value of an expression using JShell.
	 * 
	 * @param expr String mathematical expression which must be calculated.
	 * @return Calculated value of the expression.
	 */
	public String calculate(String expr)
	{
		List<SnippetEvent> events = jshell.eval(expr);
		return events.get(0).value();
	}
}
