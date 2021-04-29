package com.apboutos.spooky.units.block;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
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
import com.apboutos.spooky.level.TextureLoader;
import com.apboutos.spooky.utilities.BlockType;
import com.apboutos.spooky.utilities.Direction;
import com.apboutos.spooky.utilities.GameDimensions;
import com.apboutos.spooky.utilities.Movability;

/**
 * 
 * This is the base class of every block in the game. It only contains variables and
 * methods that are the same in every block type. All block classes must inherit this
 * base class.
 * 
 * @author Apostolis Boutos
 *
 */
public class Block {

	protected TextureAtlas deadBlockAtlas; // The block's death animation textures
	protected Animation<TextureRegion> deadBlock; // The block's death animation
	protected Sprite block; // The block's sprite
	protected SpriteBatch batch; // The game's SpriteBatch
	protected Rectangle bounds; // The block's sprite boundaries (x,y coordinate, sprites width and height)
	protected Direction myDirection; // The block's  facing direction
	
	protected boolean iAmPushed; // Whether the block is the one pushed by the player
	protected boolean iAmMoving; // Whether the block is moving or not
	protected boolean iAmDead; // Whether the block should be destroyed or not
	
	protected ArrayList<Block> blockList; // The array where all block of the same level are stored
	protected ArrayList<Explosion> explosionList; //The array where all the dynamite explosion animations are stored.

	protected Vector2 speed; // The blocks moving speed
	protected Rectangle tmpBounds; // Used for collision detection
	
	protected long deathtimer; // The starting time of the death animation
	protected boolean deathTimerStarted;
	protected float delta = 0; // The time between frames, used for animations
	protected BlockType type; // The block's type.
	
	protected Block tmpBlock;
	protected TextureLoader textureLoader;
	protected boolean iAmSuper;
	protected boolean iAmBase;
	
	/**
	 * Constructor
	 * This constructor initializes only the variables that are common for every block type.
	 * The other variables must be initialized at the constructor of the class that extends 
	 * this one.
	 */
	public Block(){
		
		iAmMoving = false;
		iAmDead   = false;
		iAmPushed = false;
	    deathTimerStarted = false;
	    deathtimer = 0;
		bounds = new Rectangle();
		speed  = new Vector2();
		speed.x = 6;
		speed.y = 6;
		tmpBounds = new Rectangle();
		tmpBlock = null;
		iAmSuper = false;
		iAmBase  = false;
	}
	
	
	public void update(){
		
	}
	
	/**
	 * Destroys the block.
	 */
	public void kill(){
		
		iAmMoving = false;
		iAmDead = true;
	}
	
	
	public void push(Direction direction){
		
		myDirection = direction;
		iAmPushed = true;
		
	}
	
	/**
	 * When the player issues a move command this method checks if this block
	 * is the one the player wanted to push and sets the direction it should be
	 * moving to. 
	 * @return A Movability Enumeration.
	 */
	protected Movability iAmEligibleToMove(){
				
			/* When the push command is issued by the player on this block the following
			   statement checks if the block is eligible to move or if it should be destroyed
			   instead. 
			*/
			
			if (iAmMoving == false && iAmPushed == true)
			{
				
				//Checks if the pushed block can't move due to being near a wall.
				if (myDirection == Direction.LEFT)
				{
					tmpBounds.setPosition(bounds.x - speed.x, bounds.y);
					if(tmpBounds.x < GameDimensions.xAxisMinumum*GameDimensions.unitWidth)
					{	
						return Movability.blocked;
					}
				}
				else if (myDirection == Direction.RIGHT)
				{
					tmpBounds.setPosition(bounds.x + speed.x, bounds.y);
					if(tmpBounds.x + tmpBounds.width > GameDimensions.xAxisMaximum*GameDimensions.unitWidth)
					{
						return Movability.blocked;
					}
				}
				else if (myDirection == Direction.UP)
				{
					tmpBounds.setPosition(bounds.x, bounds.y + speed.y);
					if(tmpBounds.y + tmpBounds.height > GameDimensions.yAxisMaximum * GameDimensions.unitHeight)
					{
						return Movability.blocked;
					}
				}
				else if (myDirection == Direction.DOWN)
				{
					tmpBounds.setPosition(bounds.x, bounds.y - speed.y);
					if(tmpBounds.y < GameDimensions.yAxisMinimum * GameDimensions.unitHeight)
					{
						return Movability.blocked;
					}
				}	
				//Checks if the pushed block can't move due to being near another block.
	
				for (Block i : blockList)
				{
					if (myDirection == Direction.UP && i!=this)
					{
						tmpBounds.setPosition(bounds.x, bounds.y + 1);
						if (tmpBounds.overlaps(i.bounds))
						{
							if (i.getBlockType() == BlockType.Diamond)
							{
								return Movability.blockedByDiamond;						
							}
							else
							{
								return Movability.blocked;
							}
						}					
					}
					else if (myDirection == Direction.DOWN && i!=this)
					{
						tmpBounds.setPosition(bounds.x, bounds.y - 1);
						if (tmpBounds.overlaps(i.bounds))
						{
							if (i.getBlockType() == BlockType.Diamond)
							{
								return Movability.blockedByDiamond;						
							}
							else
							{
								return Movability.blocked;
							}
						}					
					}
					else if (myDirection == Direction.LEFT && i!=this)
					{
						tmpBounds.setPosition(bounds.x - 1, bounds.y);
						if (tmpBounds.overlaps(i.bounds))
						{
							if (i.getBlockType() == BlockType.Diamond)
							{
								return Movability.blockedByDiamond;						
							}
							else
							{
								return Movability.blocked;
							}
						}					
					}	
					else if (myDirection == Direction.RIGHT && i!=this)
					{
						tmpBounds.setPosition(bounds.x + 1, bounds.y);
						if (tmpBounds.overlaps(i.bounds))
						{
							if (i.getBlockType() == BlockType.Diamond)
							{
								return Movability.blockedByDiamond;						
							}
							else
							{
								return Movability.blocked;
							}
						}					
					}
				}
				
			}
			if (iAmPushed == true)
			{
				
				return Movability.eligible;
			}
			else
			{
				return Movability.notPushed;
			}
		
			
	}
	
		
	/**
	 * This methods returns true if the block is has collided with a map wall.
	 * 
	 * @return boolean
	 */
	protected boolean iHaveCollidedWithMap(){
		
		tmpBounds.setPosition(bounds.x, bounds.y);
		// Checking if block collides with the map borders
		if (myDirection == Direction.LEFT)
		{
			if(tmpBounds.x - 4 < GameDimensions.xAxisMinumum * GameDimensions.unitWidth)
			{
				return true;
			}		
		}
		else if (myDirection == Direction.RIGHT)
		{
			if(tmpBounds.x + tmpBounds.width + 4 > GameDimensions.xAxisMaximum*GameDimensions.unitWidth)
			{
				return true;
			}
		}
		else if (myDirection == Direction.UP)
		{
			if(tmpBounds.y + tmpBounds.height + 4 > GameDimensions.yAxisMaximum * GameDimensions.unitHeight)
			{
				return true;
			}
		}
		else if (myDirection == Direction.DOWN)
		{
			if(tmpBounds.y - 4 < GameDimensions.yAxisMinimum * GameDimensions.unitHeight)
			{
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * This method returns true if the block has collided with another block
	 * that is not currently moving.
	 * 
	 * @return boolean
	 */
	protected boolean iHaveCollidedWithBlock(){
		//Checking if block collides with other blocks.
		if (myDirection == Direction.UP)
		{
			tmpBounds.setPosition(bounds.x, bounds.y + speed.y);
		}
		else if (myDirection == Direction.DOWN)
		{
			tmpBounds.setPosition(bounds.x, bounds.y - speed.y);
		}
		else if (myDirection == Direction.LEFT)
		{
			tmpBounds.setPosition(bounds.x - speed.x, bounds.y);
		}
		else if (myDirection == Direction.RIGHT)
		{
			tmpBounds.setPosition(bounds.x + speed.x, bounds.y);
		}
		for (Block i : blockList)
		{
			if (i != this)
			{
			    if (tmpBounds.overlaps(i.bounds) && (i.isMoving() == false))
				{
					return true;					
				}
			}	
		}
		return false;
	}

	
	/**
	 * This method returns true if the block has collided with another block
	 * that is currently moving.
	 * 
	 * @return boolean
	 */
	protected boolean iHaveCollidedWithMovingBlock(){
		
		if (myDirection == Direction.UP)
		{
			tmpBounds.setPosition(bounds.x, bounds.y + 2*speed.y);
		}
		else if (myDirection == Direction.DOWN)
		{
			tmpBounds.setPosition(bounds.x, bounds.y - 2*speed.y);
		}
		else if (myDirection == Direction.LEFT)
		{
			tmpBounds.setPosition(bounds.x - 2*speed.x, bounds.y);
		}
		else if (myDirection == Direction.RIGHT)
		{
			tmpBounds.setPosition(bounds.x + 2*speed.x, bounds.y);
		}
		for (Block i : blockList)
		{
			if (i != this)
			{
			    if (tmpBounds.overlaps(i.bounds) && (i.isMoving() == true))
				{
			    	tmpBlock = i;
					return true;		
				}
			}	
		}
		return false;
	}
	
	/**
	 * This method returns true if the block has collided with a diamond block
	 * that is not currently moving.
	 * 
	 * @return boolean
	 */
	protected boolean iHaveCollidedWithDiamondBlock(){
		
		if (myDirection == Direction.UP)
		{
			tmpBounds.setPosition(bounds.x, bounds.y + speed.y);
		}
		else if (myDirection == Direction.DOWN)
		{
			tmpBounds.setPosition(bounds.x, bounds.y - speed.y);
		}
		else if (myDirection == Direction.LEFT)
		{
			tmpBounds.setPosition(bounds.x - speed.x, bounds.y);
		}
		else if (myDirection == Direction.RIGHT)
		{
			tmpBounds.setPosition(bounds.x + speed.x, bounds.y);
		}
		for (Block i : blockList)
		{
			if (i != this)
			{
			    if (tmpBounds.overlaps(i.getBounds()) && (i.getBlockType() == BlockType.Diamond))
				{
					return true;
					
				}
			}	
		}
		return false;
	}
	
	
	
	
	/**
	 * Determines if the block can move towards the specified direction.
	 * 
	 * @param
	 * @return True if the block is free to move and false otherwise.
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
			if (i!=this)
			{
				if(i.getBounds().overlaps(tmpBounds) == true)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	
	
	
	/**
	 *  If the block is moving, this method translates the block's
	 *  x,y coordinate, according to the block's facing direction and it's
	 *  speed.
	 */
	protected void moving(){
		if (iAmMoving == true && iAmDead == false)
		{
			if (myDirection == Direction.UP)
			{
				bounds.y = bounds.y + speed.y;
			}
			else if (myDirection == Direction.DOWN)
			{
				bounds.y = bounds.y - speed.y;
			}
			else if (myDirection == Direction.LEFT)
			{
				bounds.x = bounds.x - speed.x;
			}
			else if (myDirection == Direction.RIGHT)
			{
				bounds.x = bounds.x + speed.x;
			}			
		}
		
	}

	
	
	/**
	 * Draws the block's various animations on the screen
	 */
	protected void draw(){
		block.setPosition(bounds.x, bounds.y);
		if (iAmDead == true)
		{
			delta += Gdx.graphics.getDeltaTime();
			batch.draw(deadBlock.getKeyFrame(delta),bounds.x,bounds.y);
			if (deathTimerStarted == false)
			{
				deathtimer = TimeUtils.millis(); //Start the death timer
				deathTimerStarted = true;
			}
		}
		else
		{
			block.draw(batch);
		}
	}
	
	/**
	 * Reverses the direction of the block and also the direction of
	 * the block it has collided with.
	 */
	protected void bounce(){
		
		switch (myDirection)
		{
			case    UP: myDirection = Direction.DOWN;  break;
			case  DOWN: myDirection = Direction.UP;    break;
			case  LEFT: myDirection = Direction.RIGHT;  break;
			case RIGHT: myDirection = Direction.LEFT; break;
		}
		if(tmpBlock != null)
		{
			switch (tmpBlock.myDirection)
			{
				case    UP: tmpBlock.myDirection = Direction.DOWN;  break;
				case  DOWN: tmpBlock.myDirection = Direction.UP;    break;
				case  LEFT: tmpBlock.myDirection = Direction.RIGHT;  break;
				case RIGHT: tmpBlock.myDirection = Direction.LEFT; break;
			}
		}
			
	}
	
	/**
	 * Disposes any resources that were loaded like textures
	 * and sounds.
	 */	

	public void dispose(){	
		block = null;
		deadBlockAtlas = null;
		//deadBlock = null; //Causes a null pointer exception if enabled.
		batch = null;
		bounds = null;
		myDirection = null;
		blockList = null;
		speed = null;
		tmpBounds = null;
		type = null;
	}	
	
	
	
	/**
	 * Returns the block's bounds
	 * x,y coordinate
	 * width and height
	 */	

	public Rectangle getBounds(){
		return bounds;
	}
	
	
	
	/**
	 * Retrieves the ArrayList where all the blocks are stored.
	 * Each block must have this list in order to be able to do
	 * collision checks with the other blocks.
	 */
	public void setBlockList(ArrayList<Block> blockList){
		this.blockList = blockList;		
	}
	
	/**
	 * 
	 * Sets the block's explosion list. Each dynamite block must have
	 * this list in order to be able to create explosion animations when
	 * it explodes.
	 * @param explosionList
	 */
	public void setExplosionList(ArrayList<Explosion> explosionList){
		this.explosionList = explosionList;
	}
	
	
	/**
	 * When the block is destroyed this method returns this
	 * block back to the level, so the level can know which block 
	 * to remove from it's blockList. 
	 */
	public Block getDeadBlock()
	{
		if (iAmDead == true && TimeUtils.timeSinceMillis(deathtimer) > deadBlock.getAnimationDuration()*1000)
		{
			return this;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 
	 * @return the block's type.
	 */
	public BlockType getBlockType()
	{
		return type;
	}
	
	/**
	 * 
	 * @return true if the block is moving, false otherwise.
	 */
	public boolean isMoving()
	{
		return this.iAmMoving;
	}
	

	
	/**
	 * 
	 * @return the direction the block is facing.
	 */
	public Direction getDirection()
	{
		return myDirection;
	}
	
	void setDirection(Direction direction){
		myDirection = direction;
	}
		
	/**
	 * Returns true if the current block is a super diamond block.
	 * @return boolean
	 */
	public boolean isSuperDiamond(){
		return iAmSuper;
	}
	
	
	/**
	 * Returns true if the current block is the base of diamond stacking.
	 * @return boolean
	 */
	public boolean isBase(){
		return iAmBase;
	}
	
	/**
	 * Set true to transform this diamond block to a super diamond block.
	 */
	public void setSuperDiamond(boolean iAmSuper)
	{
		this.iAmSuper = iAmSuper;
	}
}
