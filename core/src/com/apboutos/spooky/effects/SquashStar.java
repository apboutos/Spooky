package com.apboutos.spooky.effects;

import java.util.Random;

import com.apboutos.spooky.level.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.apboutos.spooky.utilities.GameDimensions;
import com.apboutos.spooky.utilities.StarColor;



/**
 * This class represents the special effect of four stars that appear every time an enemy or the player is killed
 * by a moving block.
 * 
 * @author exophrenik
 *
 */
public class SquashStar {

	private float originX; //The original horizontal coordinate of the stars. This is the vertical coordinate where the unit died.
	private float originY; //The original vertical coordinate of the starts.
	private final float starWidth = 20; //Width of the star sprite.
	private final float starHeight = 20; //Height of the star sprite.
	private float x[]; // Horizontal coordinate of each star.
	private float y[]; // Vertical coordinate of each star.
	private boolean isDead[]; // Whether each star is dead or not.
	private float velocityX[]; // Horizontal speed of each star.
	private float velocityY[]; //Vertical speed of each star.
	private float acceleration = -2; //Vertical acceleration. It is the same for all stars.
	private float delta; // The time passed between frame.

	private Sprite star;
	private SpriteBatch batch;
	private Random randomGenerator;
	
	/**
	 * Constructor responsible for setting the original vertical and horizontal velocity
	 * of each of the four stars. The different velocities are produced by a random value
	 * generator. 
	 * 
	 * @param originX The horizontal coordinate from where the stars will appear.
	 * @param originY The vertical coordinate from where the stars will appear.
	 * @param color The color of the stars.
	 * @param batch The standard SpriteBatch.
	 */
	public SquashStar(float originX, float originY, StarColor color, SpriteBatch batch){
		
		this.batch = batch;
		this.originX = originX;
		this.originY = originY;
		randomGenerator = new Random();
		x = new float[4];
		y = new float[4];
		velocityX = new float[4];
		velocityY = new float[4];
		isDead = new boolean[4];
		
		//Initialize the velocity of each star. Horizontal velocities have values ranging from 6 to 15 while vertical velocities have
		//values ranging from 5 to 20. The bigger the horizontal initial velocity of a star the further it will go on the x axis, while
		//the bigger it's vertical velocity the higher it will go on the y axis before falling down.
		for (int i=0;i<4;i++)
		{
			x[i] = this.originX;
			y[i] = this.originY;
			isDead[i] = false;
		}	
		//We want the first star to go left while the second goes right.
		//The direction of the third and forth star is chosen randomly.
		//The horizontal velocity of a star going left must be negative.
		//The horizontal velocity of a star going right must be positive.
		velocityX[0] = -1*(randomGenerator.nextInt(9) + 6);
		velocityY[0] = randomGenerator.nextInt(15) + 5;	
		velocityX[1] = 1*(randomGenerator.nextInt(9) + 6);
		velocityY[1] = randomGenerator.nextInt(15) + 5;	
		if (randomGenerator.nextBoolean())
		{
			velocityX[2] = 1*(randomGenerator.nextInt(9) + 6);
		}
		else
		{
			velocityX[2] = -1*(randomGenerator.nextInt(9) + 6);
		}
		velocityY[2] = randomGenerator.nextInt(15) + 5;	
		if (randomGenerator.nextBoolean())
		{
			velocityX[3] = 1*(randomGenerator.nextInt(9) + 6);
		}
		else
		{
			velocityX[3] = -1*(randomGenerator.nextInt(9) + 6);
		}	
		velocityY[3] = randomGenerator.nextInt(15) + 5;	

		
		// Load the proper star texture according to it's color.
		// TODO Need to add different color textures.
		// TODO Need to add textures from texture loader.
		if (color == StarColor.Blue)
		{
			star = new Sprite(TextureLoader.starBlue);
		}
		else if (color == StarColor.Green)
		{
			star = new Sprite(TextureLoader.starGreen);
		}
		else if (color == StarColor.Grey)
		{
			star = new Sprite(TextureLoader.starGrey);
		}
		else if (color == StarColor.Red)
		{
			star = new Sprite(TextureLoader.starRed);
		}
		else if (color == StarColor.Yellow)
		{
			star = new Sprite(TextureLoader.starYellow);
		}
	}
	
	
	/**
	 * Updates the stars' position and draws them on the screen.
	 * <pre>WARNING: This method must be called between batch.begin() and batch.end()
	 *         as it uses a SpriteBatch to draw on the screen.</pre> 
	 * 
	 * @param delta The time passed between frames.
	 */
	public void update(float delta){
		
		this.delta = this.delta + delta;
		updatePositions();
		for(int i=0;i<4;i++)
		{
			if (x[i] != originX)
			{
				star.setBounds(x[i], y[i], starWidth, starHeight);
				star.scale((float)-0.001); //Makes the star sprite smaller as the time passes.
				star.draw(batch);
			}
		}
		
	}
	
	
	/**
	 * Updates the position of each star. Every time this method is called the position of each star on the x
	 * and y axis is incremented by an amount equal to it's velocity*delta. delta refers to the time that has
	 * passed since the last frame (delta = t2 - t1). The same things happens for the velocity of each star on
	 * the y axis. It is incremented by an amount equal to it's acceleration*delta. Every star besides the first
	 * one, starts moving after a certain delta time has passed.
	 */
	private void updatePositions(){
		
		for (int i=0;i<4;i++)
		{
			if (i == 1 && (this.delta > 0.1))
			{
				x[i] = x[i] + velocityX[i]*delta;
				y[i] = y[i] + velocityY[i]*delta;
				velocityY[i] = velocityY[i]+ acceleration*delta;
				if (x[i] <= GameDimensions.xAxisMinumum * GameDimensions.unitWidth)
				{
					velocityX[i] = -velocityX[i];
				}
				if (x[i] >= GameDimensions.xAxisMaximum * GameDimensions.unitWidth - GameDimensions.unitWidth)
				{
					velocityX[i] = -velocityX[i];
				}
			}
			else if (i == 2 && (this.delta > 0.2))
			{
				x[i] = x[i] + velocityX[i]*delta;
				y[i] = y[i] + velocityY[i]*delta;
				velocityY[i] = velocityY[i]+ acceleration*delta;
				if (x[i] <= GameDimensions.xAxisMinumum * GameDimensions.unitWidth)
				{
					velocityX[i] = -velocityX[i];
				}
				if (x[i] >= GameDimensions.xAxisMaximum * GameDimensions.unitWidth - GameDimensions.unitWidth)
				{
					velocityX[i] = -velocityX[i];
				}
			}
			else if (i == 3 && (this.delta > 0.3))
			{
				x[i] = x[i] + velocityX[i]*delta;
				y[i] = y[i] + velocityY[i]*delta;
				velocityY[i] = velocityY[i]+ acceleration*delta;
				if (x[i] <= GameDimensions.xAxisMinumum * GameDimensions.unitWidth)
				{
					velocityX[i] = -velocityX[i];
				}
				if (x[i] >= GameDimensions.xAxisMaximum * GameDimensions.unitWidth - GameDimensions.unitWidth)
				{
					velocityX[i] = -velocityX[i];
				}
			}
			else if (i == 0)
			{
				x[i] = x[i] + velocityX[i]*delta;
				y[i] = y[i] + velocityY[i]*delta;
				velocityY[i] = velocityY[i]+ acceleration*delta;
				if (x[i] <= GameDimensions.xAxisMinumum * GameDimensions.unitWidth)
				{
					velocityX[i] = -velocityX[i];
				} 
				if (x[i] >= GameDimensions.xAxisMaximum * GameDimensions.unitWidth - GameDimensions.unitWidth)
				{
					velocityX[i] = -velocityX[i];
				}
			}
			if (y[i] < GameDimensions.yAxisMinimum * GameDimensions.screenHeight)
			{
				isDead[i] = true;
			}
				
		}
	}
	
	
	/**
	 * If all the stars are dead which means they are outside the screen, this method returns the item that
	 * called it so it can be removed from the starList. Otherwise it returns null.
	 * @return SquashStar
	 */
	public SquashStar getDeadSquashStar(){
		
		boolean stopAnimation = true;
		for (int i=0;i<4;i++)
		{
			if (isDead[i] == false)
			{
				stopAnimation = false;
			}
		}
		if (stopAnimation == true)
		{
			return this;
		}
		else
		{
			return null;	
		}
	}
	
	
	/**
	 * Disposes any textures that are loaded.
	 */
	public void dispose(){
		star = null;
		batch = null;
		randomGenerator = null;
	}
}
