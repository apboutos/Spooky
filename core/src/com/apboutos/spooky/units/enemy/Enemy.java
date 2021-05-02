package com.apboutos.spooky.units.enemy;

import com.apboutos.spooky.effects.SquashStar;
import com.apboutos.spooky.effects.Explosion;
import com.apboutos.spooky.units.Unit;
import com.apboutos.spooky.units.block.Block;
import com.apboutos.spooky.utilities.Direction;
import com.apboutos.spooky.utilities.EnemyType;
import com.apboutos.spooky.utilities.GameDimensions;
import com.apboutos.spooky.utilities.StarColor;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Represents an enemy.
 * @author Apostolos Boutos
 *
 */
@SuppressWarnings("DuplicatedCode")
public class Enemy extends Unit {

	@Setter
	protected Animation<TextureRegion> animationLeft;
	@Setter
	protected Animation<TextureRegion> animationRight;
	@Setter
	protected Animation<TextureRegion> animationUp;
	@Setter
	protected Animation<TextureRegion> animationDown;
	@Setter
	protected Sprite squash;
	
	protected SpriteBatch batch;

	protected Rectangle tmpBounds;
	protected float delta;
	protected ArrayList<Block> blockList;
	protected ArrayList<SquashStar> squashList;
	protected ArrayList<Explosion> explosionList;
	protected EnemyType enemyType;
	protected int numberOfBlocksMoved;

	
	
	protected Block tmpCollisionBlock = null;
	protected boolean iHaveCollidedWithMap = false;
	protected boolean iHaveCollidedWithBlock = false;
		
	public Enemy(float x,float y,SpriteBatch batch,EnemyType enemyType){
		
		this.batch = batch;
		this.enemyType = enemyType;
		
		
		direction   = Direction.LEFT;
		
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
		
		
		isPushing = false;
		isDead = false;
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
		//Unit enemy = new Enemy(0,0,null,null);
	}
	
	
	/**
	 * Draws the correct sprite or animation depending on direction
	 */
	protected void draw()
	{
		if (isDead)
		{
			isMoving = false;
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
			numberOfBlocksMoved+= speed.x;
			if (direction == Direction.LEFT)
			{
				if (isMoving)
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
					isMoving = false;
				}
			}
			else if (direction == Direction.RIGHT)
			{
				if (isMoving)
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
					isMoving = false;
				}
			}
			else if (direction == Direction.DOWN)
			{
				if (isMoving)
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
					isMoving = false;
				}
			}
			else if (direction == Direction.UP)
			{
				if (isMoving)
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
					isMoving = false;
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
			if (bounds.overlaps(i.getBounds()) && !isDead)
			{
				isMoving = false;
				isDead = true;
				if (enemyType == EnemyType.Fish)
				{
					squashList.add(new SquashStar(bounds.x,bounds.y,StarColor.Blue,batch));
				}
				else if (enemyType == EnemyType.Shark)
				{
					squashList.add(new SquashStar(bounds.x,bounds.y,StarColor.Grey,batch));
				}
				break;
			}
		}
		
		//Check if the player is colliding with an explosion. If it does, this unit must die.
		for (Explosion i: explosionList)
		{
			if (bounds.overlaps(i.getBounds()) && !isDead)
			{
				isMoving = false;
				isDead = true;
				if (enemyType == EnemyType.Fish)
				{
					squashList.add(new SquashStar(bounds.x,bounds.y,StarColor.Blue,batch));
				}
				else if (enemyType == EnemyType.Shark)
				{
					squashList.add(new SquashStar(bounds.x,bounds.y,StarColor.Grey,batch));
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
				isMoving = false;
				iHaveCollidedWithMap = true;
			}
		}
		else if (direction == Direction.RIGHT)
		{
			tmpBounds.setPosition(bounds.x + speed.x, bounds.y);
			if(tmpBounds.x + tmpBounds.width > GameDimensions.xAxisMaximum * GameDimensions.unitWidth)
			{
				isMoving = false;
				iHaveCollidedWithMap = true;
			}
		}
		else if (direction == Direction.UP)
		{
			tmpBounds.setPosition(bounds.x, bounds.y + speed.y);
			if(tmpBounds.y + tmpBounds.height > GameDimensions.yAxisMaximum * GameDimensions.unitHeight)
			{
				isMoving = false;
				iHaveCollidedWithMap = true;
			}
		}
		else if (direction == Direction.DOWN)
		{
			tmpBounds.setPosition(bounds.x, bounds.y - speed.y);
			if(tmpBounds.y < GameDimensions.yAxisMinimum * GameDimensions.unitHeight)
			{
				isMoving = false;
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
				isMoving = false;
				iHaveCollidedWithBlock = true;
				break;
			}	
		}		
	}
	
	
	
	/**
	 * Determines if the enemy can move towards the specified direction.
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
				if(i.getBounds().overlaps(tmpBounds))
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

	}

	
	/**
	 * If the unit has been killed, this method returns that unit, otherwise it returns null.
	 */
	public Enemy getDeadEnemy()
	{
		if (isDead && TimeUtils.timeSinceMillis(deathTimer) > 500)
		{
			return this;
		}
		else
		{
			return null;
		}
	}
	
	public boolean isDead(){
		return isDead;
	}

}
