package patternFinder;

import java.io.File;

import org.apache.log4j.xml.DOMConfigurator;

import br.com.metricminer2.MetricMiner2;
import br.com.metricminer2.RepositoryMining;
import br.com.metricminer2.Study;
import br.com.metricminer2.persistence.csv.CSVFile;
import br.com.metricminer2.scm.GitRepository;
import br.com.metricminer2.scm.commitrange.Commits;
import util.folders.Folders;
import util.repos.Repository;
import visitors.commitvisitors.JavaParserVisitor;

public class Main implements Study {
	public static void main(String[] args) {

		DOMConfigurator.configure("log4j.xml");

		if (!Folders.makeFolders() || !Repository.cloneRepos()) {
			System.out.println("error before analysis");
			return;
		}

		new MetricMiner2().start(new Main());
	}

	public void execute() {
		new RepositoryMining().in(GitRepository.allProjectsIn("repos")).through(Commits.all()).withThreads(3)
				.process(new JavaParserVisitor(), new CSVFile(Folders.OUTPUTS_FOLDER + File.separator + "out.csv"))
				.mine();
	}
}
