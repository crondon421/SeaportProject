import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import com.sun.org.apache.xerces.internal.util.Status;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import sun.nio.cs.ext.ISCII91;

public class Job extends Thing implements Runnable{
	
	Double jobDuration;
	ArrayList<String> requirements = new ArrayList<>();
	ArrayList<Person> workers = new ArrayList<>();
	JProgressBar pbar = new JProgressBar(0,100);
	Ship parent;
	enum Status {RUNNING, BUSY, SUSPENDED, NOTDOCKED, CANCELLED, NOWORKERS, DONE};
	
	public Job(Scanner lineSc, HashMap<Integer, Thing> elements) {
		
		super(lineSc, elements);
		jobDuration = lineSc.nextDouble();
		//populate requirements
		while (lineSc.hasNext()) {
			requirements.add(lineSc.next());
		}
		this.parent = (Ship)super.parent;
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
		
		while (parent.isBusy() || workers == null || workers.size() != requirements.size() || !parent.isDocked()) {
			if(!parent.isBusy()) {
				showStatus(Status.BUSY);
			}
			if(!parent.getParentPort().checkAvail(requirements)) {
				showStatus(Status.NOWORKERS);
			}
			if(!parent.getParentPort().pendingJobs.contains(this)){
				parent.getParentPort().pendingJobs.add(this);
				System.out.println(parent.getParentPort().pendingJobs.size());
			}
		}
		
		
	}

	private void showStatus(Status status) {
		//TODO: Implement status 
		
	}
	
}
	

	
	
	
	
	

