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
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.*;

public class SeaPortProgram extends JFrame{
    
    //Combo Box Options
    String[] sortOptions = {"Ports by name", "Docks by name", "Ships by name",
         "Ships in Que by name", "Ships in Que by width", "Ships in Que by length"
            , "Ships in Que by draft", "Ships in que by weight"};
    String[] searchOptions = {"Index", "Name", "Skill"};
    DefaultMutableTreeNode worldNode;
       
    String file;
    World world = new World();
    
    //create constructor
    public SeaPortProgram() throws FileNotFoundException{
        super("Seaport Program");
        setFrame(1800, 700);
        
        //initialize JFileChooser
        JFileChooser tester = new JFileChooser(".");
        tester.setFileFilter(new FileNameExtensionFilter("TEXT FILES", "txt", "text"));
        tester.setDialogTitle("Choose a SeaPort Data File");
        tester.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int testerStatus = tester.showOpenDialog(null);
        
        //Check to make sure the user did not click cancel
        if (testerStatus == JFileChooser.CANCEL_OPTION){
            System.exit(0);
        } //end if
        else if (testerStatus == JFileChooser.APPROVE_OPTION){
            
            //while loop to check to see if user selected an existing file
            boolean success = false;
            while (!success){
                file = tester.getSelectedFile().toString();
                try{
                    Scanner testerSc = new Scanner(new File(file));
                    success = true;
                    testerSc.close();
                } //end try
                catch (FileNotFoundException nfe){
                    JOptionPane.showMessageDialog(null, "Error: File not Found.");
                    if (tester.showOpenDialog(null) == JFileChooser.CANCEL_OPTION){
                        System.exit(0);
                    }
                } //end catch
            }//end while
        } //end else if
        
        Scanner sc = new Scanner(new File(file));
        //set up Container for the JProgressBars
        JPanel barPanel = new JPanel();
        barPanel.setLayout(new GridLayout(0, 1, 5, 5));

        JScrollPane progressPanel = new JScrollPane(barPanel);
        progressPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        
        //set up searchPanel components
        JComboBox<Object> searchCBox = new JComboBox<>(searchOptions);
        searchCBox.setRenderer((ListCellRenderer<? super Object>)new MyComboBoxRenderer<>("Search Target"));
        searchCBox.setSelectedIndex(-1);
        JButton searchBtn = new JButton("Search");
        JTextField searchField = new JTextField("");
        
        //set up sortPanel components
        JComboBox<Object> sortCBox = new JComboBox<>(sortOptions);
        sortCBox.setRenderer((ListCellRenderer<? super Object>)new MyComboBoxRenderer<>("Sorting Options"));
        sortCBox.setSelectedIndex(-1);
        JButton sortButton = new JButton("Sort");

        //initiate searchPanel and add components
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(1, 3, 3, 3));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        searchPanel.add(searchCBox);
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        
        //sortPanel initiation and component addition
        JPanel sortPanel = new JPanel();
        sortPanel.setLayout(new GridLayout(1, 2, 3, 3));
        sortPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        sortPanel.add(sortCBox);
        sortPanel.add(sortButton);
        
        /*northPanel adds both searchPanel and sortPanel for
        * layout purposes */
        JPanel northPanel = new JPanel();
        northPanel.add(searchPanel);
        northPanel.add(sortPanel);
        northPanel.setLayout(new GridLayout(1, 2, 3, 3));


        
        
        //set up resultArea with a scrollpane
        JTextArea resultArea = new JTextArea();
        JScrollPane resultPane = new JScrollPane(resultArea);
        resultArea.setEditable(false);
        resultPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        resultArea.setBorder(BorderFactory.createTitledBorder("World View"));
        resultArea.setText(world.toString());
        
        


        //set up southPanel for resource pools
        JPanel southPanel = new JPanel();
        southPanel.setLayout (new GridLayout(0, 1, 5, 5));
        southPanel.setBorder(new EmptyBorder(20, 0, 20, 0));


        world.readFile(sc, barPanel, southPanel);
        buildTree(world);
        
        //set up treePane        
        JTree worldTree = new JTree(worldNode);
        JScrollPane treePane = new JScrollPane(worldTree);
        worldTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        //set up centerPanel with both treePane and scrollPane (layout)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 2, 5, 5));
        centerPanel.add(treePane);
        centerPanel.add(resultPane);
        centerPanel.add(progressPanel);
        
        //adding northPanel, southPanel and centerPanel to JFrame
        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
        
        revalidate();
        repaint();
        
        
        
        
        //Action listeners for sort and Search buttons
        searchBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String keyword = searchField.getText();
                int searchTarget = searchCBox.getSelectedIndex();
                resultArea.setBorder(BorderFactory.createTitledBorder("Search Results"));
                resultArea.setText(world.performSearch(searchTarget, keyword));
            }
        });//end searchBtnActionListener
        
        //worldTree selectionListener
        worldTree.addTreeSelectionListener(new TreeSelectionListener(){
           public void valueChanged(TreeSelectionEvent e){
               DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)
                       worldTree.getLastSelectedPathComponent();
               Object nodeInfo = selectedNode.getUserObject();
               resultArea.setBorder(BorderFactory.createTitledBorder("World View"));
               resultArea.setText(nodeInfo.toString());
               resultArea.setCaretPosition(0);
               
           } 
        });//end worldTree listener
        
        //sortButton actionListener
        sortButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int sortTarget = sortCBox.getSelectedIndex();
                resultArea.setText(world.performSort(sortTarget));
            }
    });//end sortButton actionListener
      
    } //end SeaPortProgram constructor
    
    
    //display the GUI
    public void display(){
        setVisible(true);
    } //end display()
    
    //Build Tree method
    public void buildTree(World world){
        System.out.println("building tree.");
        DefaultMutableTreeNode portNode;
        DefaultMutableTreeNode dockNode;
        DefaultMutableTreeNode shipNode; //will shipNode ever be needed?
        worldNode = new AliasTreeNode(world, "World");
        for (SeaPort port : world.ports){
            portNode = new AliasTreeNode(port, port.getName());
            worldNode.add(portNode);
            for (Dock dock : port.docks){
                dockNode = new AliasTreeNode(dock, dock.getName());
                portNode.add(dockNode);
            }
        }
        System.out.println("Done building tree");
    }

    //setFrame for GUI
    public void setFrame(int width, int height){
        display();
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(Color.lightGray);
    } //end setFrame
    
    /*VERY useful comboBoxRenderer, allows you to title a comboBox with
    * instructions about the comboBox*/
    class MyComboBoxRenderer<E> extends JLabel implements ListCellRenderer<E>
    {
        private String title;

        public MyComboBoxRenderer(String title)
        {
            this.title = title;
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean hasFocus)
        {
            if (index == -1 && value == null) setText(title);
            else setText(value.toString());
            return this;
        }
    }
    
    /*Also very useful allows you to name node in JTree something other than
    * what the objects toString() method returns*/
    public class AliasTreeNode extends DefaultMutableTreeNode {
        private String alias;

        public AliasTreeNode(Object userObject,     String alias) {
            super(userObject);
            this.alias = alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        @Override
        public String toString() {
            return (alias != null) ? alias : super.toString();
        }
    }
    //main method
    public static void main(String[] args) throws FileNotFoundException {
        SeaPortProgram sp = new SeaPortProgram();
        for (SeaPort port : sp.world.ports){
            port.updateResources();
        }
        
    }
}
   

