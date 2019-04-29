package calculator.git;

import java.util.List;
import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;

public class Model {
	private JShell jshell;
	
	public Model() {
		jshell = JShell.create();
	}
		
	public String calculate(String expr)
	{
		List<SnippetEvent> events = jshell.eval(expr);
		return events.get(0).value();
	}
}
