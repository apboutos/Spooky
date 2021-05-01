package com.apboutos.spooky.units;


import com.apboutos.spooky.effects.SquashStar;
import com.apboutos.spooky.level.TextureLoader;
import com.apboutos.spooky.units.block.Block;
import com.apboutos.spooky.units.enemy.Enemy;
import com.apboutos.spooky.utilities.Direction;
import com.apboutos.spooky.utilities.GameDimensions;
import com.apboutos.spooky.utilities.StarColor;
import com.apboutos.spooky.effects.Explosion;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


/**
 * Represents the player's unit
 * 
 *         
 * @author Apostolis Boutos
 *		
 */

@Setter
@Getter
public class Player extends Unit{

	@Setter
	private Animation<TextureRegion> playerMovingLeft;
	@Setter
	private Animation<TextureRegion> playerMovingRight;
	@Setter
	private Animation<TextureRegion> playerMovingUp;
	@Setter
	private Animation<TextureRegion> playerMovingDown;
	@Setter
	private Sprite squash;
	
	private final SpriteBatch batch; // The game's SpriteBatch.
	//private final OrthographicCamera camera; // The game's camera.
	
	
	private final Rectangle tmpBounds; // Temporary Rectangle used for collision detection.
	private final Vector3 coords; //This Vector3 is used to store the coordinates of the player's touch event.
	private float delta; // The time passed between frames, required for animations	.
	private ArrayList<Block> blockList; // The array of Blocks from the LeveL, used in collision detection.
	private ArrayList<Enemy> enemyList; // The array of Enemies from the Level, used in collision detection.
	private ArrayList<SquashStar> squashList; // The array of SquashStars from the Level, used to create stars when player is dead.
	private ArrayList<Explosion> explosionList;

	//private final Settings settings; // The game's settings.

	
	
	/**
	 * Constructor
	 * 
	 * @param x Original position x in units
	 * @param y Original position y in units
	 * @param batch A SpriteBatch object that will do the rendering
	 */
	
	public Player(float x,float y,SpriteBatch batch){

		playerMovingUp = new Animation<TextureRegion>(1/10f,TextureLoader.playerMovingUp.getRegions());
		playerMovingDown = new Animation<TextureRegion>(1/10f,TextureLoader.playerMovingDown.getRegions());
		playerMovingLeft = new Animation<TextureRegion>(1/10f,TextureLoader.playerMovingLeft.getRegions());
		playerMovingRight = new Animation<TextureRegion>(1/10f,TextureLoader.playerMovingRight.getRegions());

		squash = new Sprite(TextureLoader.squash);

		bounds      = new Rectangle();
		tmpBounds   = new Rectangle();
		
		direction   = Direction.LEFT;
		this.batch  = batch;
		//this.camera = camera;
		//this.settings = settings;
		bounds.x = x* GameDimensions.unitWidth;
		bounds.y = y*GameDimensions.unitHeight;
		bounds.width  = GameDimensions.unitWidth;
		bounds.height = GameDimensions.unitHeight;
		tmpBounds.x = 0;
		tmpBounds.y = 0;
		tmpBounds.width  = GameDimensions.unitWidth;
		tmpBounds.height = GameDimensions.unitHeight;

		coords = new Vector3(); 
		speed  = new Vector2(6,6); 
		iAmPushing = false; 
		iAmDead    = false;
	}
	
	/** 
	 *  Updates the player's character logic and draws him on the screen.
	 *  
	 *  <pre>
	 *  WARNING Must be placed between
	 *  
	 *  SpriteBatch.begin()
	 *  (here)
	 *  SpriteBatch.end()
	 *  </pre>
	 *
	 */
	
	public void update(float delta){
		
		this.delta = delta;
		collisionDetect();
		draw();

	}
	
	/**
	 * Draws the correct sprite or animation depending on direction
	 */
	private void draw()
	{
		if (iAmDead)
		{
			iAmMoving = false;
			squash.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
			squash.draw(batch);
			if (!deathTimerStarted)
			{
				deathTimer = TimeUtils.millis(); //Start the death timer
				deathTimerStarted = true;
			}
		}
		else
		{
			if (direction == Direction.LEFT)
			{
				if (iAmMoving)
				{
					bounds.x = bounds.x - speed.x;
					batch.draw(playerMovingLeft.getKeyFrame(delta, true),bounds.x,bounds.y);
				}
				else
				{
					batch.draw(playerMovingLeft.getKeyFrame(0),bounds.x,bounds.y);
				}
				if ((int)bounds.x%GameDimensions.unitWidth == 0)
				{
					iAmMoving = false;
				}
			}
			if (direction == Direction.RIGHT)
			{
				if (iAmMoving)
				{
					bounds.x = bounds.x + speed.x;
					batch.draw(playerMovingRight.getKeyFrame(delta, true),bounds.x,bounds.y);
				}
				else
				{
					batch.draw(playerMovingRight.getKeyFrame(0),bounds.x,bounds.y);
				}
				if ((int)bounds.x%GameDimensions.unitWidth == 0)
				{
					iAmMoving = false;
				}
			}
			if (direction == Direction.DOWN)
			{
				if (iAmMoving)
				{
					bounds.y = bounds.y - speed.y;
					batch.draw(playerMovingDown.getKeyFrame(delta, true),bounds.x,bounds.y);
				}
				else
				{
					batch.draw(playerMovingDown.getKeyFrame(0),bounds.x,bounds.y);
				}
				if ((int)bounds.y%GameDimensions.unitHeight == 0)
				{
					iAmMoving = false;
				}
			}
			if (direction == Direction.UP)
			{
				if (iAmMoving)
				{
					bounds.y = bounds.y + speed.y;
					batch.draw(playerMovingUp.getKeyFrame(delta, true),bounds.x,bounds.y);
				}
				else
				{
					batch.draw(playerMovingUp.getKeyFrame(0),bounds.x,bounds.y);
				}
				if ((int)bounds.y%GameDimensions.unitHeight == 0)
				{
					iAmMoving = false;
				}
			} 
		}
	}

	/**
	 * Detects if the player is going to collide with a map border
	 * or a block in his next move and prevents him from doing so.
	 */
	
	private void collisionDetect(){
		// Check if player will collide with the map border in his next move
		if (direction == Direction.LEFT)
		{
			tmpBounds.setPosition(bounds.x - speed.x, bounds.y);
			if(tmpBounds.x < GameDimensions.xAxisMinumum * GameDimensions.unitWidth)
			{
				iAmMoving = false;
			} 
		}
		else if (direction == Direction.RIGHT)
		{
			tmpBounds.setPosition(bounds.x + speed.x, bounds.y);
			if(tmpBounds.x + tmpBounds.width > GameDimensions.xAxisMaximum*GameDimensions.unitWidth)
			{
				iAmMoving = false;
			}
		}
		else if (direction == Direction.UP)
		{
			tmpBounds.setPosition(bounds.x, bounds.y + speed.y);
			if(tmpBounds.y + tmpBounds.height > GameDimensions.yAxisMaximum * GameDimensions.unitHeight)
			{
				iAmMoving = false;
			}
		}
		else if (direction == Direction.DOWN)
		{
			tmpBounds.setPosition(bounds.x, bounds.y - speed.y);
			if(tmpBounds.y < GameDimensions.yAxisMinimum * GameDimensions.unitHeight)
			{
				iAmMoving = false;
			}
		}
		// Check if the player will collide with a Block in his next move
		if (direction == Direction.UP)
		{
			tmpBounds.setPosition(bounds.x, bounds.y + speed.y);
		}
		else if (direction == Direction.DOWN)
		{
			tmpBounds.setPosition(bounds.x, bounds.y - speed.y);
		}
		else if (direction == Direction.LEFT)
		{
			tmpBounds.setPosition(bounds.x - speed.x, bounds.y);
		}
		else if (direction == Direction.RIGHT)
		{
			tmpBounds.setPosition(bounds.x + speed.x, bounds.y);
		}		
		for (Block i : blockList)
		{
			if (tmpBounds.overlaps(i.getBounds()))
			{
				iAmMoving = false;
			}	
		}
		
		//Check if the player is colliding with an enemy.
		for (Enemy i: enemyList)
		{
			if (bounds.overlaps(i.getBounds()) && !i.isDead() && !iAmDead)
			{
				iAmDead = true;
				squashList.add(new SquashStar(bounds.x,bounds.y, StarColor.Yellow,batch));
				break;
			}
		}
		
		//Check if the player is colliding with a moving block.
		for (Block i: blockList)
		{
			if (bounds.overlaps(i.getBounds()) && !iAmDead)
			{
				iAmDead = true;
				squashList.add(new SquashStar(bounds.x,bounds.y,StarColor.Yellow,batch));
				break;
			}
		}
		
		//Check if the player is colliding with an explosion.
		for (Explosion i: explosionList)
		{
			if (bounds.overlaps(i.getBounds()) && !iAmDead)
			{
				iAmDead = true;
				squashList.add(new SquashStar(bounds.x,bounds.y,StarColor.Yellow,batch));
				break;
			}
		}
		
	}
	
	/**
	 * Dispose any resources used.
	 */
	public void dispose(){

	}
	
	/**
	 * Returns player's bounds
	 * x,y coordinate
	 * width and height
	 */
	public Rectangle getBounds(){
		return bounds;
	}

	/**
	 * Returns the player's facing direction
	 * @return Direction 
	 */
	public Direction getDirection(){
		return direction;
	}
	
	
	/**
	 * 
	 * @param blockList an ArrayList<Block>
	 */
	public void setBlockList(ArrayList<Block> blockList)
	{
		this.blockList = blockList;
	}
	
	
	/**
	 * 
	 * @param enemyList an ArrayList<Block>
	 */
	public void setEnemyList(ArrayList<Enemy> enemyList)
	{
		this.enemyList = enemyList;
	}
	
	/**
	 * 
	 * @param explosionList an ArrayList<Block>
	 */
	public void setExplosionList(ArrayList<Explosion> explosionList)
	{
		this.explosionList = explosionList;
	}
	
	/**
	 * 
	 * @return boolean: true if the player has been killed, false otherwise.
	 */
	public boolean isDead(){
		return iAmDead && TimeUtils.timeSinceMillis(deathTimer) > 1500;
	}
	
	
	/**
	 * 
	 * @param squashList an ArrayList<Block>
	 */
	public void setSquashList(ArrayList<SquashStar> squashList)
	{
		this.squashList = squashList;
	}
	

}