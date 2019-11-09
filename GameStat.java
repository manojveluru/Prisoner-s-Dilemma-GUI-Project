package com.javaclass.assignments;

public class GameStat 
{
	/*Instance Variables*/
	private String ComputerStrategy;
	private int roundsPlayed;
	private int userYrs;
	private int computerYrs;
	
	/*Getter and Setter methods*/
	public String getComputerStrategy() {
		return ComputerStrategy;
	}

	public void setComputerStrategy(String computerStrategy) {
		ComputerStrategy = computerStrategy;
	}

	public int getUserYrs() {
		return userYrs;
	}

	public void setUserYrs(int userYrs) {
		this.userYrs = userYrs;
	}

	public int getComputerYrs() {
		return computerYrs;
	}

	public void setComputerYrs(int computerYrs) {
		this.computerYrs = computerYrs;
	}

	
	public int getRoundsPlayed() {
		return roundsPlayed;
	}

	public void setRoundsPlayed(int roundsPlayed) {
		this.roundsPlayed = roundsPlayed;
	}

	/*Update method to update prison years of both user and computer*/
	public void update(int userSentence, int compSentence ) 
	{
		roundsPlayed++; //Rounds of play increment
		userYrs += userSentence;
		computerYrs +=compSentence;
		setUserYrs(userYrs);
		setComputerYrs(computerYrs);
	}
	
	/*Method to return winner of the game*/
	public String getWinner() 
	{
		if(userYrs<computerYrs) 
		{
			return "You the game player";
		}
		else if(userYrs == computerYrs)
		{
			return "Tie game";
		}
		else 
		{
			return "Computer";
		}
		
	}

}
