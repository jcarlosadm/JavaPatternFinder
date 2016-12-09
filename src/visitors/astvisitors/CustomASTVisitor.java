package visitors.astvisitors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class CustomASTVisitor extends ASTVisitor {

	// private static final String GENERICS_REGEX = "<[A-Z][a-z]+>";
	private static final String GENERICS_REGEX = "<[\\s?A-Z?a-z?\\s?]*>";
	
	private static final String ANNOTATIONS_REGEX = "@";

	private int lambdaExpressionFrequency = 0;
	
	private int innerClassFrequency = 0;
	
	private int genericsFreq = 0;
	
	private int annotationsFrequency = 0;
	
	public int getAnnotationsFrequency() {
		return annotationsFrequency;
	}

	public int getGenericsFreq() {
		return genericsFreq;
	}

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
		} else {
			Pattern pattern = Pattern.compile(GENERICS_REGEX);
			Matcher matcher = pattern.matcher(node.toString());
			while (matcher.find()) {
				++this.genericsFreq;
			}
			
			pattern = Pattern.compile(ANNOTATIONS_REGEX);
			matcher = pattern.matcher(node.toString());
			while (matcher.find()) {
				++this.annotationsFrequency;
			}
		}
		
		return super.visit(node);
	}
}
