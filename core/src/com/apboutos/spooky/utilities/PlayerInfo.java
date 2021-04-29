package com.apboutos.spooky.utilities;

public class PlayerInfo {

	
	private int lives;
	private int score;
	private int timeLeft;
	private boolean e;
	private boolean x;
	private boolean t;
	private boolean r;
	private boolean a;
	
	public PlayerInfo()
	{
		lives = 3;
		score = 0;
		timeLeft = 4000;
		e = x = t = r = a = false;
	}
	
	public void setLives(int lives)
	{
		this.lives = lives;
	}
	
	public void setScore(int score)
	{
		this.score = score;
	}
	
	public void setTimeLeft(int timeLeft)
	{
		this.timeLeft = timeLeft;
	}
	
	public void resetExtra()
	{
		e = x = t = r = a  = false;
	}
	
	public void activateE()
	{
		e = true;
	}
	
	public void activateX()
	{
		x = true;
	}
	
	public void activateT()
	{
		t = true;
	}
	
	public void activateR()
	{
		r = true;
	}
	
	public void activateA()
	{
		a = true;
	}
	
	
	
	
}
