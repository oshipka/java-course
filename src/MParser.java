import org.mariuszgromada.math.mxparser.Expression;

public class MParser {
	private  String _expression;
	
	public MParser(String expr) {
		_expression = expr;
	}
	
	public double CalculateExpression() {
		String cExpression = _expression;
		Expression equation = new Expression(cExpression);
		return equation.calculate();
	}
}
