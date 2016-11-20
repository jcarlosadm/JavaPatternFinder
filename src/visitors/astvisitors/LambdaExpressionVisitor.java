package visitors.astvisitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.LambdaExpression;

public class LambdaExpressionVisitor extends ASTVisitor {

	private int total = 0;
	
	public LambdaExpressionVisitor() {
	}
	
	@Override
	public boolean visit(LambdaExpression node) {
		
		++this.total;
		
		return super.visit(node);
	}
	
	public int getFrequency() {
		return this.total;
	}
	
	public String getDescription() {
		return "lambda expression";
	}
}
