package patternFinder;

import java.io.File;

import org.apache.log4j.xml.DOMConfigurator;

import br.com.metricminer2.MetricMiner2;
import br.com.metricminer2.RepositoryMining;
import br.com.metricminer2.Study;
import br.com.metricminer2.persistence.csv.CSVFile;
import br.com.metricminer2.scm.GitRepository;
import br.com.metricminer2.scm.commitrange.Commits;
import util.PropertiesManager;
import util.folders.Folders;
import util.repos.Repository;
import visitors.commitvisitors.JavaParserVisitor;

public class Main implements Study {

	private static boolean CLONE_REPOS = true;

	public static void main(String[] args) {

		DOMConfigurator.configure("log4j.xml");

		if (!Folders.makeFolders() || (CLONE_REPOS && !Repository.cloneRepos())) {
			return;
		}

		new MetricMiner2().start(new Main());
	}

	public void execute() {

		new RepositoryMining().in(GitRepository.allProjectsIn("repos")).through(Commits.all())
				.withThreads(this.getnumberOfThreads())
				.process(new JavaParserVisitor(), new CSVFile(Folders.OUTPUTS_FOLDER + File.separator + "out.csv"))
				.mine();
	}

	private int getnumberOfThreads() {
		String numberOfThreads = PropertiesManager.getProperty("number.of.threads");
		int value = 1;
		try {
			value = Integer.parseInt(numberOfThreads);
		} catch (Exception e) {
			value = 1;
		}
		return value;
	}
}
