package visitors.commitvisitors;

import java.io.ByteArrayInputStream;
import java.io.File;

import br.com.metricminer2.domain.Commit;
import br.com.metricminer2.domain.Modification;
import br.com.metricminer2.parser.jdt.JDTRunner;
import br.com.metricminer2.persistence.PersistenceMechanism;
import br.com.metricminer2.scm.CommitVisitor;
import br.com.metricminer2.scm.SCMRepository;
import visitors.astvisitors.CustomASTVisitor;

public class JavaParserVisitor implements CommitVisitor {

	@Override
	public String name() {
		return "java-parser";
	}

	@Override
	public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
		
		for (Modification modification : commit.getModifications()) {

			if (modification.wasDeleted())
				continue;
			if (!modification.getFileName().endsWith("java"))
				continue;

			CustomASTVisitor astVisitor = new CustomASTVisitor();
			new JDTRunner().visit(astVisitor, new ByteArrayInputStream(modification.getSourceCode().getBytes()));

			WriteFrequencyParameter wParameter = new WriteFrequencyParameter(writer, repo, commit, modification);

			if (astVisitor.getLambdaExpressionFrequency() > 0) {
				this.writeFrequency(wParameter, "lambda expression", astVisitor.getLambdaExpressionFrequency());
			}
			
			if (astVisitor.getInnerClassFrequency() > 0) {
				this.writeFrequency(wParameter, "inner class", astVisitor.getInnerClassFrequency());
			}
			
			if (astVisitor.getGenericsFreq() > 0) {
				this.writeFrequency(wParameter, "generics", astVisitor.getGenericsFreq());
			}
			
			if (astVisitor.getAnnotationsFrequency() > 0) {
				this.writeFrequency(wParameter, "annotations", astVisitor.getAnnotationsFrequency());
			}
		}
	}

	private void writeFrequency(WriteFrequencyParameter parameterObject, String descriptor, int frequency) {
		String repoName = parameterObject.getRepo().getPath();
		repoName = repoName.substring(repoName.lastIndexOf(File.separator) + 1);

		parameterObject.getWriter().write(repoName, parameterObject.getCommit().getHash(),
				parameterObject.getModification().getFileName(), descriptor, frequency);
	}

}
