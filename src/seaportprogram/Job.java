import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import sun.nio.cs.ext.ISCII91;

public class Job extends Thing implements Runnable{
	
	Double jobDuration;
	ArrayList<String> requirements = new ArrayList<>();
	ArrayList<Person> workers = new ArrayList<>();
	JPanel containerPanel = new JPanel();
	JProgressBar pbar = new JProgressBar(0,100);
	Ship parent;
	
	public Job(Scanner lineSc, HashMap<Integer, Thing> elements) {
		
		super(lineSc, elements);
		jobDuration = lineSc.nextDouble();
		//populate requirements
		while (lineSc.hasNext()) {
			requirements.add(lineSc.next());
		}
		elements.put(this.id, this);
		((Ship)parent).jobs.add(this);
		
		pbar.setStringPainted(true);
		pbar.setValue(0);
	}

	@Override
	public void run() {
		
		long time = System.currentTimeMillis();
		long startTime = time;
		double stopTime = time + (1000 * jobDuration);
		double duration = stopTime - time;
		
		
	}
	
	
	
	
}
