package com.apboutos.spooky.units;

import com.apboutos.spooky.level.TextureLoader;
import com.apboutos.spooky.utilities.BlockType;
import com.apboutos.spooky.utilities.Direction;
import com.apboutos.spooky.utilities.GameDimensions;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * This is the base class of every block in the game. It only contains variables and
 * methods that are the same in every block type. All block classes must inherit this
 * base class.
 * 
 * @author Apostolis Boutos
 *
 */
@Setter
@Getter
public class Block extends Unit {

	private final BlockType type;

	private Animation<TextureRegion> deathAnimation;
	private Sprite block;
	private boolean isSuperDiamond;
	private int numberOfBounces;
	private int maxNumberOfBounces;

	public Block(float x, float y, BlockType type){

		bounds.set( x * GameDimensions.unitWidth,
				    y * GameDimensions.unitHeight,
				     GameDimensions.unitWidth,
				     GameDimensions.unitHeight);

		this.type = type;
		loadTexturesByType();
		block.setBounds(bounds.x,bounds.y,bounds.width,bounds.height);

		switch (type){
			case Bouncing: maxNumberOfBounces = 2; break;
			case BigBouncing: maxNumberOfBounces = 3; break;
			case Dynamite:
			case BigDynamite: maxNumberOfBounces = 0; break;
			default: maxNumberOfBounces = 1; break;
		}
	}

	public void explode(){

	}

	public void push(Direction direction){
		if(!isMoving){
			this.direction = direction;
			isPushed = true;
		}
	}

	public void move(){
		if(!isSuperDiamond){
			isMoving = true;
		}
	}

	public void kill(){
		if(!deathTimerStarted){
			deathTimerStarted = true;
			isMoving = false;
			isDead = true;
		}
	}

	public void stop(){

	}

	public void merge(){

	}

	public void bounce(){
		direction = Direction.reverseDirection(direction);
	}

	public void makeSuperDiamond(){
		if(!isSuperDiamond){
			isSuperDiamond = true;
			block.setTexture(TextureLoader.superDiamondBlock);
		}
	}

	@Override
	public boolean isDead() {
		return super.isDead() && TimeUtils.timeSinceMillis(deathTimer) > deathAnimation.getAnimationDuration()*1000;
	}

	private void loadTexturesByType(){
		switch (type){
			case Standard:
				deathAnimation = new Animation<>(1 / 10f, TextureLoader.deadStandardBlock.getRegions());
				block = new Sprite(TextureLoader.standardBlock);
				break;
			case Bouncing:
				deathAnimation = new Animation<>(1 / 10f, TextureLoader.deadBouncingBlock.getRegions());
				block = new Sprite(TextureLoader.bouncingBlock);
				break;
			case BigBouncing:
				deathAnimation = new Animation<>(1 / 10f, TextureLoader.deadBigBouncingBlock.getRegions());
				block = new Sprite(TextureLoader.bigBouncingBlock);
				break;
			case Dynamite:
				deathAnimation = new Animation<>(1 / 10f, TextureLoader.deadDynamiteBlock.getRegions());
				block = new Sprite(TextureLoader.dynamiteBlock);
				break;
			case BigDynamite:
				deathAnimation = new Animation<>(1 / 10f, TextureLoader.deadBigDynamiteBlock.getRegions());
				block = new Sprite(TextureLoader.bigDynamiteBlock);
				break;
			case Diamond:
				deathAnimation = new Animation<>(1 / 10f, TextureLoader.deadStandardBlock.getRegions());
				block = new Sprite(TextureLoader.dynamiteBlock);
				break;
		}
	}


























	/*
	@Setter
	protected Animation<TextureRegion> deadBlock; // The block's death animation
	@Setter
	protected Sprite block; // The block's sprite
	protected SpriteBatch batch; // The game's SpriteBatch

	protected ArrayList<Block> blockList; // The array where all block of the same level are stored
	protected ArrayList<Explosion> explosionList; //The array where all the dynamite explosion animations are stored.

	protected Rectangle tmpBounds; // Used for collision detection

	protected float delta = 0; // The time between frames, used for animations
	protected BlockType type; // The block's type.
	
	protected Block tmpBlock;
	protected boolean iAmSuper;
	protected boolean iAmBase;
	



	public Block(){
		
		isMoving = false;
		isDead = false;
		isPushed = false;
	    deathTimerStarted = false;
	    deathTimer = 0;
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
	

	public void kill(){
		
		isMoving = false;
		isDead = true;
	}
	
	
	public void push(Direction direction){
		
		this.direction = direction;
		isPushed = true;
		
	}
	

	protected Movability iAmEligibleToMove(){
				
			/* When the push command is issued by the player on this block the following
			   statement checks if the block is eligible to move or if it should be destroyed
			   instead.
			*/
			/*
			if (!isMoving && isPushed)
			{
				
				//Checks if the pushed block can't move due to being near a wall.
				if (direction == Direction.LEFT)
				{
					tmpBounds.setPosition(bounds.x - speed.x, bounds.y);
					if(tmpBounds.x < GameDimensions.xAxisMinumum*GameDimensions.unitWidth)
					{	
						return Movability.blocked;
					}
				}
				else if (direction == Direction.RIGHT)
				{
					tmpBounds.setPosition(bounds.x + speed.x, bounds.y);
					if(tmpBounds.x + tmpBounds.width > GameDimensions.xAxisMaximum*GameDimensions.unitWidth)
					{
						return Movability.blocked;
					}
				}
				else if (direction == Direction.UP)
				{
					tmpBounds.setPosition(bounds.x, bounds.y + speed.y);
					if(tmpBounds.y + tmpBounds.height > GameDimensions.yAxisMaximum * GameDimensions.unitHeight)
					{
						return Movability.blocked;
					}
				}
				else if (direction == Direction.DOWN)
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
					if (direction == Direction.UP && i!=this)
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
					else if (direction == Direction.DOWN && i!=this)
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
					else if (direction == Direction.LEFT && i!=this)
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
					else if (direction == Direction.RIGHT && i!=this)
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
			if (isPushed)
			{
				
				return Movability.eligible;
			}
			else
			{
				return Movability.notPushed;
			}
		
			
	}
	
		

	protected boolean iHaveCollidedWithMap(){
		
		tmpBounds.setPosition(bounds.x, bounds.y);
		// Checking if block collides with the map borders
		if (direction == Direction.LEFT)
		{
			return tmpBounds.x - 4 < GameDimensions.xAxisMinumum * GameDimensions.unitWidth;
		}
		else if (direction == Direction.RIGHT)
		{
			return tmpBounds.x + tmpBounds.width + 4 > GameDimensions.xAxisMaximum * GameDimensions.unitWidth;
		}
		else if (direction == Direction.UP)
		{
			return tmpBounds.y + tmpBounds.height + 4 > GameDimensions.yAxisMaximum * GameDimensions.unitHeight;
		}
		else if (direction == Direction.DOWN)
		{
			return tmpBounds.y - 4 < GameDimensions.yAxisMinimum * GameDimensions.unitHeight;
		}
		return false;
	}
	
	

	protected boolean iHaveCollidedWithBlock(){
		//Checking if block collides with other blocks.
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
			if (i != this)
			{
			    if (tmpBounds.overlaps(i.bounds) && (!i.isMoving()))
				{
					return true;					
				}
			}	
		}
		return false;
	}

	

	protected boolean iHaveCollidedWithMovingBlock(){
		
		if (direction == Direction.UP)
		{
			tmpBounds.setPosition(bounds.x, bounds.y + 2*speed.y);
		}
		else if (direction == Direction.DOWN)
		{
			tmpBounds.setPosition(bounds.x, bounds.y - 2*speed.y);
		}
		else if (direction == Direction.LEFT)
		{
			tmpBounds.setPosition(bounds.x - 2*speed.x, bounds.y);
		}
		else if (direction == Direction.RIGHT)
		{
			tmpBounds.setPosition(bounds.x + 2*speed.x, bounds.y);
		}
		for (Block i : blockList)
		{
			if (i != this)
			{
			    if (tmpBounds.overlaps(i.bounds) && (i.isMoving()))
				{
			    	tmpBlock = i;
					return true;		
				}
			}	
		}
		return false;
	}
	

	protected boolean iHaveCollidedWithDiamondBlock(){
		
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
				if(i.getBounds().overlaps(tmpBounds))
				{
					return false;
				}
			}
		}
		return true;
	}
	
	
	
	

	protected void moving(){
		if (isMoving && !isDead)
		{
			if (direction == Direction.UP)
			{
				bounds.y = bounds.y + speed.y;
			}
			else if (direction == Direction.DOWN)
			{
				bounds.y = bounds.y - speed.y;
			}
			else if (direction == Direction.LEFT)
			{
				bounds.x = bounds.x - speed.x;
			}
			else if (direction == Direction.RIGHT)
			{
				bounds.x = bounds.x + speed.x;
			}			
		}
		
	}

	
	

	protected void draw(){
		block.setPosition(bounds.x, bounds.y);
		if (isDead)
		{
			delta += Gdx.graphics.getDeltaTime();
			batch.draw(deadBlock.getKeyFrame(delta),bounds.x,bounds.y);
			if (!deathTimerStarted)
			{
				deathTimer = TimeUtils.millis(); //Start the death timer
				deathTimerStarted = true;
			}
		}
		else
		{
			block.draw(batch);
		}
	}

	protected void bounce(){
		
		switch (direction)
		{
			case    UP: direction = Direction.DOWN;  break;
			case  DOWN: direction = Direction.UP;    break;
			case  LEFT: direction = Direction.RIGHT;  break;
			case RIGHT: direction = Direction.LEFT; break;
		}
		if(tmpBlock != null)
		{
			switch (tmpBlock.direction)
			{
				case    UP: tmpBlock.direction = Direction.DOWN;  break;
				case  DOWN: tmpBlock.direction = Direction.UP;    break;
				case  LEFT: tmpBlock.direction = Direction.RIGHT;  break;
				case RIGHT: tmpBlock.direction = Direction.LEFT; break;
			}
		}
			
	}
	

	public void dispose(){	

	}	
	
	

	public Rectangle getBounds(){
		return bounds;
	}
	
	
	

	public void setBlockList(ArrayList<Block> blockList){
		this.blockList = blockList;		
	}
	

	public void setExplosionList(ArrayList<Explosion> explosionList){
		this.explosionList = explosionList;
	}
	
	

	public Block getDeadBlock()
	{
		if (isDead && TimeUtils.timeSinceMillis(deathTimer) > deadBlock.getAnimationDuration()*1000)
		{
			return this;
		}
		else
		{
			return null;
		}
	}


	public BlockType getBlockType()
	{
		return type;
	}

	public boolean isMoving()
	{
		return this.isMoving;
	}
	

	

	public Direction getDirection()
	{
		return direction;
	}

	

	public boolean isBase(){
		return iAmBase;
	}
	*/
}
