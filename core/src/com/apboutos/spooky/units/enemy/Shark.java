package com.apboutos.spooky.units.enemy;

import com.apboutos.spooky.level.TextureLoader;
import com.apboutos.spooky.utilities.BlockType;
import com.apboutos.spooky.utilities.Direction;
import com.apboutos.spooky.utilities.EnemyType;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


/**
 * 
 * This class represents the shark enemy. The shark is one of the smarter enemies.
 * He can push blocks and increase his moving speed when he is close to the player.
 * 
 * @author Apostolis Boutos
 *
 */
@SuppressWarnings("DuplicatedCode")
public class Shark extends Enemy{
	


	public Shark(float x, float y, SpriteBatch batch, EnemyType enemyType) {
		super(x, y, batch, enemyType);

		animationUp = new Animation<TextureRegion>(1/10f, TextureLoader.sharkUp.getRegions());
		animationDown = new Animation<TextureRegion>(1/10f, TextureLoader.sharkDown.getRegions());
		animationLeft = new Animation<TextureRegion>(1/10f, TextureLoader.sharkLeft.getRegions());
		animationRight = new Animation<TextureRegion>(1/10f, TextureLoader.sharkRight.getRegions());
		squash = new Sprite(TextureLoader.squash);

		speed  = new Vector2(6,6); 
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
	@ Override
	public void update(float delta){
		
		this.delta = delta;
		artificialIntelligence();
		collisionDetect();
		draw();
	}
	
	/**
	 * Generates the logic which the unit will be using in order to move,
	 * push blocks and generally try to antagonize the player.
	 */
	private void artificialIntelligence(){
		iAmMoving = true;
		/* The logic of the fish is very simple. It will randomly change directions
		 * when it collides with another unit like a block, or when it has moved 4 times.
		 * It will not push any blocks.
		 */
		if (numberOfBlocksMoved%400 == 0)
		{
			//plotNewCourse();
		}
		if (iHaveCollidedWithMap)
		{
			plotNewCourse();
			iHaveCollidedWithMap = false;
		}	
		if(iHaveCollidedWithBlock)
		{	
			if(tmpCollisionBlock.getBlockType() == BlockType.Standard && !deathTimerStarted && !tmpCollisionBlock.isMoving())
			{
				if(tmpCollisionBlock.canMove(direction))
				{
					tmpCollisionBlock.push(direction);
				}
				else
				{
					plotNewCourse();
				}
			}
			else
			{
				plotNewCourse();
			}
			iHaveCollidedWithBlock = false;
		}
		
	}		

	
private void plotNewCourse(){
		
		Direction tmpDirection = direction;
		if (tmpDirection == Direction.LEFT)
		{
			int b = ((int)Math.round(Math.random()*100))%2 + 1;
			if (b == 1)
			{
				tmpDirection = Direction.UP;
				if (!this.canMove(tmpDirection))
				{
					tmpDirection = Direction.DOWN;
				}
				if (!this.canMove(tmpDirection))
				{
					tmpDirection = Direction.RIGHT;
				}
			}
			else
			{
				tmpDirection = Direction.DOWN;
				if (!this.canMove(tmpDirection))
				{
					tmpDirection = Direction.UP;
				}
				if (!this.canMove(tmpDirection))
				{
					tmpDirection = Direction.RIGHT;
				}
			}
		}
		else if (tmpDirection == Direction.RIGHT)
		{
			int b = ((int)Math.round(Math.random()*100))%2 + 1;
			if (b == 1)
			{
				tmpDirection = Direction.UP;
				if (!this.canMove(tmpDirection))
				{
					tmpDirection = Direction.DOWN;
				}
				if (!this.canMove(tmpDirection))
				{
					tmpDirection = Direction.RIGHT;
				}
			}
			else
			{
				tmpDirection = Direction.DOWN;
				if (!this.canMove(tmpDirection))
				{
					tmpDirection = Direction.UP;
				}
				if (!this.canMove(tmpDirection))
				{
					tmpDirection = Direction.LEFT;
				}
			}
		}
		else if (tmpDirection == Direction.UP)
		{
			int b = ((int)Math.round(Math.random()*100))%2 + 1;
			if (b == 1)
			{
				tmpDirection = Direction.LEFT;
				if (!this.canMove(tmpDirection))
				{
					tmpDirection = Direction.RIGHT;
				}
				if (!this.canMove(tmpDirection))
				{
					tmpDirection = Direction.DOWN;
				}
			}
			else
			{
				tmpDirection = Direction.RIGHT;
				if (!this.canMove(tmpDirection))
				{
					tmpDirection = Direction.LEFT;
				}
				if (!this.canMove(tmpDirection))
				{
					tmpDirection = Direction.DOWN;
				}
			}
		}
		else if (tmpDirection == Direction.DOWN)
		{
			int b = ((int)Math.round(Math.random()*100))%2 + 1;
			if (b == 1)
			{
				tmpDirection = Direction.LEFT;
				if (!this.canMove(tmpDirection))
				{
					tmpDirection = Direction.RIGHT;
				}
				if (!this.canMove(tmpDirection))
				{
					tmpDirection = Direction.UP;
				}
			}
			else
			{
				tmpDirection = Direction.RIGHT;
				if (!this.canMove(tmpDirection))
				{
					tmpDirection = Direction.LEFT;
				}
				if (!this.canMove(tmpDirection))
				{
					tmpDirection = Direction.DOWN;
				}
			}
		}
		direction = tmpDirection;
   }
}//Class ends here.