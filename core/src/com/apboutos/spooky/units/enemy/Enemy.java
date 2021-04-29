package com.apboutos.spooky.units.enemy;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import com.apboutos.spooky.effects.Explosion;
import com.apboutos.spooky.effects.SquashStar;
import com.apboutos.spooky.level.TextureLoader;
import com.apboutos.spooky.units.block.Block;
import com.apboutos.spooky.utilities.Direction;
import com.apboutos.spooky.utilities.EnemyType;
import com.apboutos.spooky.utilities.GameDimensions;
import com.apboutos.spooky.utilities.StarColor;

/**
 * Represents an enemy.
 * @author 
 *
 */
public class Enemy {

	protected TextureLoader textureLoader;
	protected TextureAtlas atlasLeft;
	protected TextureAtlas atlasRight;
	protected TextureAtlas atlasUp;
	protected TextureAtlas atlasDown;
	
	protected Animation<TextureRegion> animationLeft;
	protected Animation<TextureRegion> animationRight;
	protected Animation<TextureRegion> animationUp;
	protected Animation<TextureRegion> animationDown;
	
	protected Sprite squash;
	
	protected SpriteBatch batch;
	
	protected Rectangle bounds;
	protected Rectangle tmpBounds;
	protected Direction direction;
	protected Vector2 speed;
	protected float delta;
	protected ArrayList<Block> blockList;
	protected ArrayList<SquashStar> squashList;
	protected ArrayList<Explosion> explosionList;
	protected boolean iAmMoving; // Whether this unit is moving or not
	protected boolean iAmPushing; // Whether this unit has issued a push command or not
	protected boolean iAmDead; // Whether this unit has been killed or not
	protected EnemyType enemyType;
	protected int numberOfBlocksMoved = 0;
	protected boolean deathTimerStarted = false; // Whether the death timer has started or not (default value false);
	protected long deathtimer; // The starting time of the death animation
	
	
	protected Block tmpCollisionBlock = null;
	protected boolean iHaveCollidedWithMap = false;
	protected boolean iHaveCollidedWithBlock = false;
		
	public Enemy(float x,float y,SpriteBatch batch,EnemyType enemyType){
		
		this.batch = batch;
		this.enemyType = enemyType;
		
		
		direction   = Direction.LEFT; 
		this.batch  = batch;
		
		bounds = new Rectangle();
		tmpBounds = new Rectangle();
		
		bounds.x = x * GameDimensions.unitWidth;
		bounds.y = y * GameDimensions.unitHeight;
		bounds.width  = GameDimensions.unitWidth;
		bounds.height = GameDimensions.unitHeight;
		tmpBounds.x = 0;
		tmpBounds.y = 0;
		tmpBounds.width  = bounds.width;
		tmpBounds.height = bounds.height;
		
		
		iAmPushing = false; 
		iAmDead    = false;
		numberOfBlocksMoved = 0;	
	}
	
	
	/** 
	 *  Updates the player's character logic and draws him on the screen
	 *  
	 *  WARNING Must be placed between
	 *  SpriteBatch.begin()
	 *  and
	 *  SpriteBatch.end()
	 * 
	 */
	public void update(float delta){

	}
	
	
	/**
	 * Draws the correct sprite or animation depending on direction
	 */
	protected void draw()
	{
		if (iAmDead == true)
		{
			iAmMoving = false;
			squash.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
			squash.draw(batch);
			if (deathTimerStarted == false)
			{
				deathtimer = TimeUtils.millis(); //Start the death timer
				deathTimerStarted = true;
			}
		}
		else
		{
			numberOfBlocksMoved+= speed.x;
			if (direction == Direction.LEFT)
			{
				if (iAmMoving == true)
				{
					bounds.x = bounds.x - speed.x;
					batch.draw(animationLeft.getKeyFrame(delta, true),bounds.x,bounds.y);
				}
				else
				{
					batch.draw(animationLeft.getKeyFrame(0),bounds.x,bounds.y);
				}
				if (((int)bounds.x)%40 == 0)
				{
					iAmMoving = false;
				}
			}
			else if (direction == Direction.RIGHT)
			{
				if (iAmMoving == true)
				{
					bounds.x = bounds.x + speed.x;
					batch.draw(animationRight.getKeyFrame(delta, true),bounds.x,bounds.y);
				}
				else
				{
					batch.draw(animationRight.getKeyFrame(0),bounds.x,bounds.y);
				}
				if (((int)bounds.x)%40 == 0)
				{
					iAmMoving = false;
				}
			}
			else if (direction == Direction.DOWN)
			{
				if (iAmMoving == true)
				{
					bounds.y = bounds.y - speed.y;
					batch.draw(animationDown.getKeyFrame(delta, true),bounds.x,bounds.y);
				}
				else
				{
					batch.draw(animationDown.getKeyFrame(0),bounds.x,bounds.y);
				}
				if (((int)bounds.y)%40 == 0)
				{
					iAmMoving = false;
				}
			}
			else if (direction == Direction.UP)
			{
				if (iAmMoving == true)
				{
					bounds.y = bounds.y + speed.y;
					batch.draw(animationUp.getKeyFrame(delta, true),bounds.x,bounds.y);
				}
				else
				{
					batch.draw(animationUp.getKeyFrame(0),bounds.x,bounds.y);
				}
				if (((int)bounds.y)%40 == 0)
				{
					iAmMoving = false;
				}
			}
			
		}
	}

	
		
	/**
	 * Detects collision between this unit and other elements such as blocks,
	 * map borders, or the player.
	 */
	protected void collisionDetect(){
		
		// Check if any block overlaps with this unit. If it does, this unit must die.
		for (Block i : blockList)
		{
			if (bounds.overlaps(i.getBounds()) && iAmDead == false)
			{
				iAmMoving = false;
				iAmDead = true;
				if (enemyType == EnemyType.Fish)
				{
					squashList.add(new SquashStar(bounds.x,bounds.y,StarColor.Blue,batch,textureLoader));
				}
				else if (enemyType == EnemyType.Shark)
				{
					squashList.add(new SquashStar(bounds.x,bounds.y,StarColor.Grey,batch,textureLoader));
				}
				break;
			}
		}
		
		//Check if the player is colliding with an explosion. If it does, this unit must die.
		for (Explosion i: explosionList)
		{
			if (bounds.overlaps(i.getBounds()) && iAmDead == false)
			{
				iAmMoving = false;
				iAmDead = true;
				if (enemyType == EnemyType.Fish)
				{
					squashList.add(new SquashStar(bounds.x,bounds.y,StarColor.Blue,batch,textureLoader));
				}
				else if (enemyType == EnemyType.Shark)
				{
					squashList.add(new SquashStar(bounds.x,bounds.y,StarColor.Grey,batch,textureLoader));
				}
				break;
			}
		}
		
		// Check if the unit will collide with the map border in it's next move, in order to stop it.
		if (direction == Direction.LEFT)
		{
			tmpBounds.setPosition(bounds.x - speed.x, bounds.y);
			if(tmpBounds.x < GameDimensions.xAxisMinumum * GameDimensions.unitWidth)
			{
				iAmMoving = false;
				iHaveCollidedWithMap = true;
			}
		}
		else if (direction == Direction.RIGHT)
		{
			tmpBounds.setPosition(bounds.x + speed.x, bounds.y);
			if(tmpBounds.x + tmpBounds.width > GameDimensions.xAxisMaximum * GameDimensions.unitWidth)
			{
				iAmMoving = false;
				iHaveCollidedWithMap = true;
			}
		}
		else if (direction == Direction.UP)
		{
			tmpBounds.setPosition(bounds.x, bounds.y + speed.y);
			if(tmpBounds.y + tmpBounds.height > GameDimensions.yAxisMaximum * GameDimensions.unitHeight)
			{
				iAmMoving = false;
				iHaveCollidedWithMap = true;
			}
		}
		else if (direction == Direction.DOWN)
		{
			tmpBounds.setPosition(bounds.x, bounds.y - speed.y);
			if(tmpBounds.y < GameDimensions.yAxisMinimum * GameDimensions.unitHeight)
			{
				iAmMoving = false;
				iHaveCollidedWithMap = true;
			}
		}
		// Check if the unit will collide with a Block in it's next move, in order to stop it.
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
				tmpCollisionBlock = i;
				iAmMoving = false;
				iHaveCollidedWithBlock = true;
				break;
			}	
		}		
	}
	
	
	
	/**
	 * Determines if the enemy can move towards the specified direction.
	 * 
	 * @param
	 * @return True if the enemy is free to move and false otherwise.
	 */
	public boolean canMove(Direction direction){
		
		Rectangle tmpBounds = new Rectangle(bounds.x,bounds.y + speed.y, bounds.width,bounds.height);	
		
		switch (direction){
		
			case    UP: tmpBounds = new Rectangle(bounds.x,bounds.y + speed.y, bounds.width,bounds.height);break;
			case  DOWN: tmpBounds = new Rectangle(bounds.x,bounds.y - speed.y, bounds.width,bounds.height);break;
			case  LEFT: tmpBounds = new Rectangle(bounds.x - speed.x,bounds.y, bounds.width,bounds.height);break;
			case RIGHT: tmpBounds = new Rectangle(bounds.x + speed.x,bounds.y, bounds.width,bounds.height);break;
		}
		// Checking if block collides with the map borders
		if (direction == Direction.LEFT)
		{
			if(tmpBounds.x - 4 < GameDimensions.xAxisMinumum * GameDimensions.unitWidth)
			{
				return false;
			}		
		}
		else if (direction == Direction.RIGHT)
		{
			if(tmpBounds.x + tmpBounds.width + 4 > GameDimensions.xAxisMaximum*GameDimensions.unitWidth)
			{
				return false;
			}
		}
		else if (direction == Direction.UP)
		{
			if(tmpBounds.y + tmpBounds.height + 4 > GameDimensions.yAxisMaximum * GameDimensions.unitHeight)
			{
				return false;
			}
		}
		else if (direction == Direction.DOWN)
		{
			if(tmpBounds.y - 4 < GameDimensions.yAxisMinimum * GameDimensions.unitHeight)
			{
				return false;
			}
		}
		
		//Check if block collides with other blocks.
		for (Block i : blockList)
		{	
				if(i.getBounds().overlaps(tmpBounds) == true)
				{
					return false;
				}	
		}
		return true;
	}
	
	
	/**
	 * Returns a Rectangle containing the unit's bounds
	 * x,y coordinate
	 * width and height
	 */
	public Rectangle getBounds(){
		return bounds;
	}
	
	
	/**
	 * Returns if the player has issued a push command or not
	 * @return Boolean 
	 */
	public boolean getIsPushing(){
		return iAmPushing;
	}
	
	
	/**
	 * Returns the units's facing direction
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
	 * @param explosionList an ArrayList<Block>
	 */
	public void setExplosionList(ArrayList<Explosion> explosionList)
	{
		this.explosionList = explosionList;
	}
	
	
	/**
	 * 
	 * @param squashList an ArrayList<Block>
	 */
	public void setSquashList(ArrayList<SquashStar> squashList)
	{
		this.squashList = squashList;
	}

	
	/**
	 * Disposes textures to deallocate memory.
	 */

	public void dispose() {

		textureLoader = null;
		atlasLeft = null;
		atlasRight = null;
		atlasUp = null;
		atlasDown = null;
		
		animationLeft = null;
		animationRight = null;
		animationUp = null;
		animationDown = null;
	
		squash = null;
		batch = null;
		bounds = null;
		tmpBounds = null;
		direction = null;
		speed = null;
		blockList = null;
		squashList = null;
		enemyType = null;
	}

	
	/**
	 * If the unit has been killed, this method returns that unit, otherwise it returns null.
	 * @return
	 */
	public Enemy getDeadEnemy()
	{
		if (iAmDead == true && TimeUtils.timeSinceMillis(deathtimer) > 500)
		{
			return this;
		}
		else
		{
			return null;
		}
	}
	
	public boolean isDead(){
		return iAmDead;
	}
	
	public void setDead(boolean isDead){
		iAmDead = isDead;
	}
	
}
