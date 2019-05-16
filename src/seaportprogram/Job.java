/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seaportprogram;

/**
 *
 * @author UncleJester
 */
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
public class Job extends Thing implements Runnable {
    Thread t;
    double totalDuration;
    ArrayList<String> requirements;
    ArrayList<Person> workers;
    int jobsFilled = 0;
    boolean running = true;
    boolean isCanceled = false;
    boolean isPaused = false;
    
    Status status = Status.NOTDOCKED;
    enum Status {RUNNING, BUSY, SUSPENDED, NOTDOCKED, CANCELLED, NOWORKERS, DONE};
    Ship parentShip;
    JPanel containerPanel;
    JProgressBar jobBar;
    JButton jbCancel;
    JButton jbPause;
    
    //constructor
    public Job(Scanner sc, HashMap<Integer, Thing> thingMap, JPanel jobPanel){
        super(sc);
        requirements = new ArrayList<>();
        totalDuration = sc.nextDouble();
        String req = "";
        jobBar = new JProgressBar(0, 100);
        jobBar.setStringPainted(true);
        jobBar.setValue (0);
        jbCancel = new JButton("Cancel");
        jbPause = new JButton("Pause");
        setParentObject(thingMap.get(parent));
        parentShip = (Ship)thingMap.get(parent);
        containerPanel = new JPanel();
        containerPanel.add(new JLabel(thingMap.get(this.parent).name), JLabel.CENTER);
        containerPanel.add(new JLabel(this.name, JLabel.CENTER));
        containerPanel.add(jobBar);
        containerPanel.add(jbPause);
        containerPanel.add(jbCancel);
        jobPanel.add(containerPanel);
        containerPanel.revalidate();
        
        
        //populating requirements
        while(sc.hasNext()){
            req = sc.next();
            if (req != null){
                requirements.add(req);
            }
        }  
        
        //ActionListener for jbCancel
        jbPause.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                togglePause();
            }
        });
        
        jbCancel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                cancelProgress();
            }
        });
        
        //create and start a thread
        t = new Thread(this, this.name);
        t.start();
        

    }
    
    //showStatus changes the UI according to jobs statuses
    public void showStatus(Status st){
        status = st;
        switch (status){
            case RUNNING:
                jbPause.setEnabled(true);
                jbPause.setText("Pause");
                jbPause.setBackground(null);
                jbCancel.setText("Cancel");
                jbCancel.setEnabled(true);
                jbCancel.setBackground(null);
                break;
            case BUSY:
                jbPause.setBackground(Color.ORANGE);
                jbPause.setText("WAITING");
                jbPause.setEnabled(false);
                jbCancel.setText("WAITING");
                jbCancel.setBackground(Color.ORANGE);
                jbCancel.setEnabled(false);
                break;
            case SUSPENDED:
                jbPause.setEnabled(true);
                jbPause.setBackground(Color.YELLOW);
                jbPause.setText("Resume");
                jbCancel.setEnabled(true);
                jbCancel.setBackground(null);
                jbCancel.setText("Cancel");
                break;
            case NOTDOCKED:
                jbPause.setEnabled(false);
                jbPause.setBackground(Color.ORANGE);
                jbPause.setText("Not docked");
                jbCancel.setEnabled(false);
                jbCancel.setBackground(Color.ORANGE);
                jbCancel.setText("Not docked");
                break;
            case CANCELLED:
                jbPause.setText("Start");
                jbPause.setBackground(null);
                jbCancel.setEnabled(false);
                jbCancel.setBackground(Color.RED);
                jbCancel.setText("Cancelled");
                break;
            case NOWORKERS:
                jbPause.setText("Not enough workers");
                jbPause.setBackground(Color.MAGENTA);
                jbPause.setEnabled(false);
                jbCancel.setEnabled(false);
                jbCancel.setBackground(Color.MAGENTA);
                jbCancel.setText("Not enough workers");
                break;
            case DONE:
                jbCancel.setBackground(Color.LIGHT_GRAY);
                jbCancel.setEnabled(false);
                jbCancel.setText("Done");
                jbPause.setEnabled(false);
                jbPause.setBackground(Color.LIGHT_GRAY);
                jbPause.setText("Done");
                break;
        }
        
    }
   
    
    //togglePause pauses the process
    public void togglePause(){
        if (isCanceled){
            isPaused = false;
        }
        else{
            isPaused = !isPaused;
        }
        parentShip.isBusy = false;
        isCanceled = false;
    }
    
    //cancelProgress() to cancel job progress and return to 0%
    public void cancelProgress(){
        running = false;
        isPaused = false;
        isCanceled = true;
        parentShip.isBusy = false;
    }
    
    //run method implemented by Runnable
    @Override
    public void run() {
        long time = System.currentTimeMillis();
        long startTime = time;
        double stopTime = time  + (1000 * totalDuration);
        double duration = stopTime - time;
        
        

        //go through conditions, if all satisfied, acquire workers and continue  
        while (parentShip.isBusy || workers == null || workers.size()
                != requirements.size() || !parentShip.isDocked){

            synchronized(parentShip.parentPort){
                if (parentShip.isDocked){
                    if (parentShip.isBusy){ 
                        showStatus(Status.BUSY);
                    }
                    if (!parentShip.parentPort.checkAvail(requirements)){
                        showStatus(Status.NOWORKERS);
                        if (!parentShip.parentPort.pendingJobs.contains(this)){
                            parentShip.parentPort.pendingJobs.add(this);
                        }
                    }
                    if (!parentShip.isBusy && (workers == null ||
                            parentShip.parentPort.checkAvail(requirements))){
                        workers = parentShip.acquireWorkers(requirements, this); 
                        if (parentShip.parentPort.pendingJobs.contains(this)){
                            parentShip.parentPort.pendingJobs.remove(this);
                        }
                        break;
                    }                    
                }

                else{
                    showStatus(Status.NOTDOCKED);

                }
                try {
                parentShip.parentPort.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Job.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
            
        
            
        //while there is still time left in the job
        while (time < stopTime && parentShip.isDocked){



            if (!isPaused && !isCanceled){
                parentShip.isBusy = true;



                //mandatory try/catch
                try{
                    Thread.sleep(100);
                }
                catch (InterruptedException ie){}
                time += 100;
                jobBar.setValue((int)(((time - startTime) / duration) * 100));
                showStatus(Status.RUNNING);

            }
            while (isPaused && !parentShip.isBusy && !isCanceled){
                showStatus(Status.SUSPENDED);


            }
            while (isPaused && parentShip.isBusy && !isCanceled){
                showStatus(Status.BUSY);

            }
            while (isCanceled){
                time = startTime;
                jobBar.setValue(0);
                showStatus(Status.CANCELLED);


            }
        }//while statement is done, so job is done
            
        

        //setprogressbar and corresponding UIs to done
        jobBar.setString("DONE");
        showStatus(Status.DONE);
        
        //no more working job on ship, allow others to check conditions
        parentShip.isBusy = false;
         

        //fireWorkers, removeJob, updateResources() and notifyAll other threads
        //when job is done.
        synchronized(parentShip.parentPort){
            
            parentShip.fireWorkers(workers);
            parentShip.removeJob(this);
            parentShip.parentPort.updateResources();
            parentShip.parentPort.notifyAll();
            
        }
        
    }
    
}

