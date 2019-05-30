import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class Job extends Thing{
	
	Double jobDuration;
	ArrayList<String> requirements = new ArrayList<>();
	ArrayList<Person> workers = new ArrayList<>();
	JPanel containerPanel = new JPanel();
	JProgressBar pbar = new JProgressBar(0,100);
	
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
		pbar.setValue (0);
		containerPanel.add(new JLabel(name), JLabel.CENTER);
		containerPanel.add(pbar);
		((Ship)parent).jobPanel.add(containerPanel);
		((Ship)parent).jobPanel.revalidate();
	}
	
	
}
