package visitors.commitvisitors;

import java.io.ByteArrayInputStream;
import java.io.File;

import br.com.metricminer2.domain.Commit;
import br.com.metricminer2.domain.Modification;
import br.com.metricminer2.parser.jdt.JDTRunner;
import br.com.metricminer2.persistence.PersistenceMechanism;
import br.com.metricminer2.scm.CommitVisitor;
import br.com.metricminer2.scm.SCMRepository;
import visitors.astvisitors.LambdaExpressionVisitor;

public class JavaParserVisitor implements CommitVisitor {

	@Override
	public String name() {
		return "java-parser";
	}

	@Override
	public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
		// TODO Auto-generated method stub
		for(Modification m : commit.getModifications()) {
			
			if(m.wasDeleted()) continue;
			if(!m.getFileName().endsWith("java")) continue;
			
			LambdaExpressionVisitor lambdaVisitor = new LambdaExpressionVisitor();
			new JDTRunner().visit(lambdaVisitor, new ByteArrayInputStream(m.getSourceCode().getBytes()));
			
			if (lambdaVisitor.getFrequency() > 0) {
				writer.write(
						repo.getPath().substring(repo.getPath().lastIndexOf(File.separator)+1),
						commit.getHash(),
						m.getFileName(),
						lambdaVisitor.getDescription(),
						lambdaVisitor.getFrequency()
					);
			}
			
		}
	}

}
