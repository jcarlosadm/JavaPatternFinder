package patternFinder;

import org.apache.log4j.xml.DOMConfigurator;

import br.com.metricminer2.MetricMiner2;
import br.com.metricminer2.Study;

public class Main implements Study {
	public static void main(String[] args) {
		DOMConfigurator.configure("log4j.xml");
		new MetricMiner2().start(new Main());
	}

	public void execute() {
		// TODO Auto-generated method stub
		
	}
}
