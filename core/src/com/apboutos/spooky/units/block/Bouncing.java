package com.apboutos.spooky.units.block;

import com.apboutos.spooky.level.TextureLoader;
import com.apboutos.spooky.utilities.BlockType;
import com.apboutos.spooky.utilities.GameDimensions;
import com.apboutos.spooky.utilities.Movability;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * This class represents a block that can bounce back when it collides with another block
 * or wall. There are two types of bouncing blocks, the blue block that bounces back once
 * and the pink block that bounces back twice. Those bounces do not take in account the
 * free bounces that all blocks perform when they collide with another moving block.
 * 
 * @author Apostolis Boutos
 *
 */
@SuppressWarnings("DuplicatedCode")
public class Bouncing extends Block{
	
	private int currentBounces;
	private int maxBounces;
	
	/**
	 * Constructor that initializes the object.
	 */
	public Bouncing(int x, int y, SpriteBatch batch, BlockType type){
		
		super();
		this.type = type; //Set the paren't block type.
		this.batch = batch;
		
		if(type == BlockType.Bouncing)
		{
			deadBlock = new Animation<TextureRegion>(1/10f, TextureLoader.deadBouncingBlock.getRegions());
			block = new Sprite(TextureLoader.bouncingBlock);
			currentBounces = 0;
			maxBounces = 1;
		}
		else if (type == BlockType.BigBouncing)
		{
			deadBlock = new Animation<TextureRegion>(1/10f, TextureLoader.deadBigBouncingBlock.getRegions());
			block = new Sprite(TextureLoader.bigBouncingBlock);
			currentBounces = 0;
			maxBounces = 2;
		}
		
		bounds.set((float)x*GameDimensions.unitWidth,(float)y*GameDimensions.unitHeight, GameDimensions.unitWidth, GameDimensions.unitHeight);		
		block.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);

	    tmpBounds.setSize( GameDimensions.unitWidth, GameDimensions.unitHeight);
		
	}

	
	/**
	 * Call this method to update the logic of the block and also
	 * to draw it on the screen. WARNING must be placed between 
	 * SpriteBatch.begin()
	 * and
	 * SpriteBatch.end() 
	 */
	@ Override
	public void update(){
				
		if (iAmPushed && !iAmMoving)
		{
			Movability tmp = iAmEligibleToMove();
			if ( tmp == Movability.eligible )
			{
				iAmMoving = true;
			}
			else if (tmp == Movability.blocked || tmp == Movability.blockedByDiamond)
			{
				iAmMoving = false;
				iAmDead = true;
			}
			iAmPushed = false;
		}
		if( iHaveCollidedWithMap() && iAmMoving)
		{
			if(currentBounces == maxBounces)
			{
				iAmMoving = false;
				currentBounces = 0;
			}
			else
			{
				currentBounces++;
				bounce();
			}
			
		}
		else if (iHaveCollidedWithBlock() && iAmMoving)
		{
			if(currentBounces == maxBounces)
			{
				iAmMoving = false;
				currentBounces = 0;
			}
			else
			{
				currentBounces++;
				bounce();
			}
		}
		else if (iHaveCollidedWithMovingBlock() && iAmMoving)
		{
			bounce();
		}
		moving();
		draw();
	}

}//Class ends here.
