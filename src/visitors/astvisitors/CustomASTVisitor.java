package visitors.astvisitors;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class CustomASTVisitor extends ASTVisitor {

	private int lambdaExpressionFrequency = 0;
	
	private int innerClassFrequency = 0;
	
	public int getInnerClassFrequency() {
		return innerClassFrequency;
	}
	
	public int getLambdaExpressionFrequency() {
		return lambdaExpressionFrequency;
	}

	public CustomASTVisitor() {
	}
	
	@Override
	public boolean visit(LambdaExpression node) {
		
		++this.lambdaExpressionFrequency;
		
		return super.visit(node);
	}
	
	@Override
	public boolean visit(TypeDeclaration node) {
		if (((ASTNode) node.getParent()).getNodeType() == ASTNode.TYPE_DECLARATION) {
			++this.innerClassFrequency;
		}
		
		return super.visit(node);
	}
}
