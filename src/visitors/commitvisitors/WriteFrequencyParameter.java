package visitors.commitvisitors;

import br.com.metricminer2.domain.Commit;
import br.com.metricminer2.domain.Modification;
import br.com.metricminer2.persistence.PersistenceMechanism;
import br.com.metricminer2.scm.SCMRepository;

public class WriteFrequencyParameter {
	private PersistenceMechanism writer;
	private SCMRepository repo;
	private Commit commit;
	private Modification modification;

	public WriteFrequencyParameter(PersistenceMechanism writer, SCMRepository repo, Commit commit,
			Modification modification) {
		this.writer = writer;
		this.repo = repo;
		this.commit = commit;
		this.modification = modification;
	}

	public PersistenceMechanism getWriter() {
		return writer;
	}

	public SCMRepository getRepo() {
		return repo;
	}

	public Commit getCommit() {
		return commit;
	}

	public Modification getModification() {
		return modification;
	}
}