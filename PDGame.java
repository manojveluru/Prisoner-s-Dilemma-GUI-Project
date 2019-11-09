package com.javaclass.assignments;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PDGame 
{
	private ArrayList<String> strategiesList = new ArrayList<>();  //ArrayList to store strategies
	private ArrayList<Integer> userHistoryList = new ArrayList<>(); //ArrayList to store User inputs
	private int strategy; //Instance variable
	private String scores; //Instance variable
	GameStat stats = new GameStat(); //GameStat object creation
	Scanner sc = null;
	public PDGame(String file)  //Constructor which stores strategies to ArrayList and Open file to read contents
	{
		strategiesList.add("Strategy from Input File");
		strategiesList.add("Tit-For-Tat");
		strategiesList.add("Tit-For-Two-Tats");
		strategiesList.add("Random");
		try {
			sc = new Scanner(new File(file));
		} catch (FileNotFoundException e) {	
			e.printStackTrace();
		}
	}
	
	/*Getter Methods*/
	public GameStat getStats() {
		return stats;
	}
	public ArrayList<String> getStrategiesList() {
		return strategiesList;
	}	

	public String getScores() 
	{
		int score1 = stats.getUserYrs();
		int score2 = stats.getComputerYrs();
		String jail = "year";
		if(score1>1 && score2>1) 
		{
			jail = "years";
		}
		String score = "---Your prison sentence is: "+score1+" "+jail+"\n---Your partner's/computer prison sentence is: "+score2+" "+jail;
		return score;
	}

	/*Setter Methods*/
	public void setStrategy(int strategy) {
		this.strategy = strategy;
		stats.setComputerStrategy(strategiesList.get(strategy-1));
		this.setStats(stats);
	}
	
	
	public void setStats(GameStat stats) {
		this.stats = stats;
	}
	
	/*PlayRound function which takes user decision and compares with computer decision and returns result in form of string*/
	public String playRound(int decision) 
	{
		userHistoryList.add(decision);
		int computerDecision = figureComputerDecision(strategy);
		if(decision==1 && computerDecision==1) 
		{
			stats.update(2, 2); //Updating prison years based on decision
			return "You and your partner remain silent\nYou both get 2 years in prison.";
		}
		else if(decision==2 && computerDecision==1) 
		{
			stats.update(1, 5); //Updating prison years based on decision
			return "You testify against your partner and they remain silent.\nYou get 1 year in prison and they get 5.";
		}
		else if(decision==1 && computerDecision==2) 
		{
			stats.update(5, 1); //Updating prison years based on decision
			return "You remain silent and they testify against you.\nYou get 5 years in prison and they get 1.";
		}
		else 
		{
			stats.update(3, 3); //Updating prison years based on decision
			return "You and your partner testify against eachother.\nYou both get 3 years in prison";
		}
	}
	
	/*Getting computer decision from different strategies chosen and returning*/
	private int figureComputerDecision(int Strategy) 
	{
		int decision = 0;
		switch(Strategy) 
		{
		case 1:
			decision = inputFromFile(); //Decision from file
			break;
		case 2:
			decision = titForTat(); //TitForTat 
			break;
		case 3:
			decision = titForTwoTat(); //TitForTwoTat
			break;
		case 4:
			decision = (int)((Math.random()*2)+1); //Random number decision
			break;
		}
		return decision;
	}
	
	/*Taking input from a file*/
	private int inputFromFile() {
		int decision = 0;
		while (sc.hasNextLine()) 
		{
			String i = sc.nextLine();
			decision  = Integer.parseInt(i);
			return decision;
		} 
		return decision;
	}
	
	/*Cooperate on the first 2 moves, otherwise betray if the player's last 2 moves were betrayal.*/
	private int titForTwoTat() {
		int decision=0;
		if(stats.getRoundsPlayed()==0 || stats.getRoundsPlayed()==1) //Cooperating on first two moves
		{
			decision=1;
		}
		else 
		{
			int lastMove1 = userHistoryList.get(userHistoryList.size()-2);
			int lastMove2 = userHistoryList.get(userHistoryList.size()-3);
			if(lastMove1==2 && lastMove2==2) 
			{
				decision=2;
			}
			else 
			{
				decision=1;
			}
			
		}
		return decision;
	}
	
	/*Cooperate on the first move, otherwise play the player's last move.*/
	private int titForTat() 
	{
		int decision = 0;
		if(stats.getRoundsPlayed()==0) //Cooperating on first move
		{
			decision = 1;
		}
		else 
		{
			decision = userHistoryList.get(userHistoryList.size()-2);
		}
		
		return decision;
	}

}
