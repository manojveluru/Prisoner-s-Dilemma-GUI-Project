package com.javaclass.assignments;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Date;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PDGameGui extends JFrame implements ActionListener, ListSelectionListener {

	 //instance variables for PDGameAPP , all methods can access these  
	
	private final DefaultListModel<String> listModelPtr = new DefaultListModel<String>(); // Default List model is the standard “mode for how a Jlist will be operated, will put in next statement below
	private JList<String> finishedGamesListPtr= new JList<String>(listModelPtr); // this  is list on top left side and will  show times of games played that user will click to see stats of a game 
	private  JComboBox<Object> computerStrategyCB=null; //combo box on right side, pointer will be filled in constructor   
	private final JTextArea gameResultsTA = new JTextArea(15, 30); //this is  large text area on right side
	private PDGame currentPDGame = null; //PDGame class reference
	private String gameStartTimeStr = null; 
	private final HashMap<String, GameStat> stats = new HashMap<>(); //keeping same hash map for games played

	//below are some sample text fields, more are needed…….
	private final JTextField roundsTF = new JTextField(10);
	private final JTextField computerStrategyTF = new JTextField(10);
	private final JTextField playerSentenceTF = new JTextField(10);
	private final JTextField computerSentenceTF = new JTextField(10);
	private final JTextField winnerTF = new JTextField(10);

	//below are some sample labels, more are needed…….
	private final JLabel roundsL = new JLabel("Rounds Played");
	private final JLabel computerStrategyLabel = new JLabel("Computer Strategy");
	private final JLabel playerSentenceL = new JLabel("Player Sentence");
	private final JLabel computerSentenceL = new JLabel("Computer Sentence");
	private final JLabel winnerL = new JLabel("Winner");
	
	private final JLabel computerStrategyL = new JLabel("Computer Strategy-Combo Box");

	//below are some sample Buttons, more are needed…….  
	private final JButton startB = new JButton("Start New Game");
	private final JLabel decisionL = new JLabel("Your decision this round?");
	private final JButton option1 = new JButton("Remain Silent");
	private final JButton option2 = new JButton("Testify");

	private int computerStrategy = 1; //this will be filled in by the choice made by user in combo box
	private int count = 0; //to count the number of rounds
	
	private String currentDirectory = System.getProperty("user.dir");
	//CONSTRUCTOR WHERE YOU BUILD THE SWING INTERFACE 
	public PDGameGui() 
	{
		super("Prisoner's Dilemma");
		currentPDGame = new PDGame(currentDirectory+"\\inputs.txt");
		
		setLayout(new BorderLayout()); //Default layout of JFrame
		
		JPanel panel1 = new JPanel(new BorderLayout()); //Panel1 with border layout
		add(panel1, BorderLayout.WEST); //adding to the west of JFrame
		panel1.setBorder(BorderFactory.createTitledBorder("List of Games")); //creating titled border
		finishedGamesListPtr.setFont(new Font("SansSerif", Font.BOLD, 24)); //setting the font
		finishedGamesListPtr.setVisibleRowCount(10); //setting the size
		finishedGamesListPtr.setFixedCellWidth(550); //setting the size
		finishedGamesListPtr.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //only single selection is allowed
		panel1.add(new JScrollPane(finishedGamesListPtr), BorderLayout.NORTH); //adding JList to the north of west panel
		
		JPanel panel3 = new JPanel(new GridLayout(5,2)); //Panel 3 with Grid layout
		panel1.add(panel3, BorderLayout.SOUTH); //adding to the south of west panel
		
		/*Adding labels, text fields to panel 3 and setting its font*/
		panel3.add(roundsL);
		roundsL.setFont(new Font("SansSerif", Font.BOLD, 20));
		panel3.add(roundsTF);
		panel3.add(computerStrategyLabel);
		computerStrategyLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
		panel3.add(computerStrategyTF);
		panel3.add(playerSentenceL);
		playerSentenceL.setFont(new Font("SansSerif", Font.BOLD, 20));
		panel3.add(playerSentenceTF);
		panel3.add(computerSentenceL);
		computerSentenceL.setFont(new Font("SansSerif", Font.BOLD, 20));
		panel3.add(computerSentenceTF);
		panel3.add(winnerL);
		winnerL.setFont(new Font("SansSerif", Font.BOLD, 20));
		panel3.add(winnerTF);
		
		
		
		JPanel panel2 = new JPanel(new BorderLayout()); //Panel2 with Border layout
		add(panel2, BorderLayout.EAST); //Adding panel to the east of JFrame
		//panel2.setBorder(BorderFactory.createLineBorder(Color.black));
		/*Combo Box to hold the strategies*/
		Object[] strategyArray = currentPDGame.getStrategiesList().toArray();
		computerStrategyCB = new JComboBox<Object>(strategyArray);
		computerStrategyCB.setEditable(false);
		computerStrategyCB.setSelectedIndex(0);
		
		JPanel panel4 = new JPanel(new FlowLayout()); //Panel4 with Flow layout
		JPanel panel5 = new JPanel(new FlowLayout()); //Panel5 with Flow layout
		JPanel panel6 = new JPanel(new GridLayout(2,1)); //Panle6 with Grid layout
		panel6.add(panel4); //Adding Panel4 and panel 5 to panel6
		panel6.add(panel5);
		panel2.add(panel6, BorderLayout.NORTH); //Adding panel 6 to the north of panel2
		
		/*Adding Labels and Buttons to panel 4 and panel5*/
		panel4.add(computerStrategyL);
		panel4.add(computerStrategyCB);	
		panel4.add(startB);
		panel5.add(decisionL);
		panel5.add(option1);
		panel5.add(option2);
		panel2.add(new JScrollPane(gameResultsTA), BorderLayout.SOUTH); //Adding ScrollPane to the south of panel2
		
		//Disable decision buttons initially
		decisionL.setEnabled(false);
		option1.setEnabled(false);
		option2.setEnabled(false);
		
		//Coloring to GUI
		Color c1 = new Color(255,255,0);  //higher numbers means lighter colors
		panel1.setBackground(c1); 
		panel3.setBackground(c1);//sets the color
		Color c2 = new Color(51, 204, 255);
		Color c3 = new Color(255, 153, 0);
		panel4.setBackground(c2);
		panel5.setBackground(c3);

		
	}
	/*The place where program starts*/
	public static void main(String[] args) 
	{
		createAndShowGUI();
	}

	/*Creating PDGameGui object and calling addListeners and packing up the JFrame and making JFrame visible*/
	public static void createAndShowGUI() 
	{
		PDGameGui pdg1 = new PDGameGui();
		pdg1.addListeners();
		
		pdg1.pack();
		pdg1.setVisible(true);
		
	}
	private void addListeners() 
	{
		/*Everything needs to perform a task while we select them*/
		computerStrategyCB.addActionListener(this);
		startB.addActionListener(this);
		option1.addActionListener(this);
		option2.addActionListener(this);
		finishedGamesListPtr.addListSelectionListener(this); 
	}
	@Override
	public void valueChanged(ListSelectionEvent e) 
	{
		
		String searchKey = "";
		if(!finishedGamesListPtr.isSelectionEmpty())
		{
			/*retrieved selected key and getting corresponding results from hash map*/
	         searchKey = (String) finishedGamesListPtr.getSelectedValue(); 
	         roundsTF.setText(new Integer(stats.get(searchKey).getRoundsPlayed()).toString());
	         roundsTF.setFont( new Font("SansSerif", Font.PLAIN, 12));
	         
	         computerStrategyTF.setText(stats.get(searchKey).getComputerStrategy());
	         computerStrategyTF.setFont( new Font("SansSerif", Font.PLAIN, 12));
	         
	         playerSentenceTF.setText(String.format("%d %s", stats.get(searchKey).getUserYrs(), ((stats.get(searchKey).getUserYrs() > 1) ? " years" : " year")));
	         playerSentenceTF.setFont( new Font("SansSerif", Font.PLAIN, 12));
	         
	         computerSentenceTF.setText(String.format("%d %s", stats.get(searchKey).getComputerYrs(), ((stats.get(searchKey).getComputerYrs() > 1) ? " years" : " year")));
	         computerSentenceTF.setFont( new Font("SansSerif", Font.PLAIN, 12));
	         
	         winnerTF.setText(stats.get(searchKey).getWinner());
	         winnerTF.setFont( new Font("SansSerif", Font.PLAIN, 12));
		}
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		/*Calling the corresponding methods upon selecting the particular action event*/
		if(e.getSource() == startB) 
		{
			startGame();
		}
		else if(e.getSource() == option1) 
		{
			cooperate();
		}
		else if(e.getSource() == option2) 
		{
			betray();
		}
		else if(e.getSource() == computerStrategyCB) 
		{
			computerStrategy = computerStrategyCB.getSelectedIndex()+1;
		}
		
	}
	
	private void betray() 
	{
		/*When clicked on testify */
		String result = currentPDGame.playRound(2);
		gameResultsTA.append("\n");
		gameResultsTA.append(result);
		gameResultsTA.append("\n");
		promptPlayer();
	}
	private void cooperate() 
	{
		/*When clicked on silent*/
		String result = currentPDGame.playRound(1);
		gameResultsTA.append("\n");
		gameResultsTA.append(result);
		gameResultsTA.append("\n");
		promptPlayer();
	}
	private void startGame() 
	{
		gameResultsTA.setText(null);
		//Disabling the start game option
		computerStrategyL.setEnabled(false);
		computerStrategyCB.setEnabled(false);
		startB.setEnabled(false);
		//Enabling the buttons
		decisionL.setEnabled(true);
		option1.setEnabled(true);
		option2.setEnabled(true);
		currentPDGame = new PDGame(currentDirectory+"\\inputs.txt");
		currentPDGame.setStrategy(computerStrategy);
		
		gameStartTimeStr = (new Date()).toString();
		stats.put(gameStartTimeStr, currentPDGame.getStats());
		gameResultsTA.append("***Prisoner's Dilemma ***");
		gameResultsTA.append("\n\n");
		promptPlayer();
	}
	private void promptPlayer() 
	{
		//Ending the game after 5 rounds
		gameResultsTA.append("\n1. Remain silent.\n2. Betray and testify against.\n\n----What is your decision in this round?\n");
		count++;
		if(count == 6) 
		{
			endGame();
		}
	
	}
	private void endGame() 
	{
		//Adding stats key to the left panel 
		String scores = currentPDGame.getScores(); //Getting final scores
		gameResultsTA.append("\nEND OF ROUNDS, GAME OVER\n");
		gameResultsTA.append(scores);
		listModelPtr.addElement(gameStartTimeStr);
		count = 0;
		//Enabling the start game option and Disabling the decision buttons
		computerStrategyL.setEnabled(true);
		computerStrategyCB.setEnabled(true);
		startB.setEnabled(true);
		decisionL.setEnabled(false);
		option1.setEnabled(false);
		option2.setEnabled(false);
	}

}
