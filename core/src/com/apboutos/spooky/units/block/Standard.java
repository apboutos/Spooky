package com.apboutos.spooky.units.block;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import com.apboutos.spooky.level.TextureLoader;
import com.apboutos.spooky.utilities.BlockType;
import com.apboutos.spooky.utilities.GameDimensions;
import com.apboutos.spooky.utilities.Movability;


/**
 * This class represents a standard block that can be pushed both by the player and
 * the enemies and can also spawn enemies.
 * 
 * @author exophrenik
 *
 */
public class Standard extends Block{

	
	public Standard(int x, int y, SpriteBatch batch, BlockType type, TextureLoader textureLoader) {
		
		super();
		this.type = type; //Set the paren't block type.		
		this.batch = batch;
		
		block = new Sprite(textureLoader.getStandardBlock());
		bounds.set((float)x*GameDimensions.unitWidth,(float)y*GameDimensions.unitHeight, GameDimensions.unitWidth, GameDimensions.unitHeight);		
		block.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
		
		deadBlockAtlas = textureLoader.getDeadStandardBlock();
	    deadBlock = new Animation(1/10f,deadBlockAtlas.getRegions());

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
		
		if (iAmPushed == true)
		{
			Movability tmp = iAmEligibleToMove();
			if ( tmp == Movability.eligible )
			{
				iAmMoving = true;
			}
			else if (tmp == Movability.notPushed)
			{
				
				
			}
			else if (tmp == Movability.blocked || tmp == Movability.blockedByDiamond)
			{
				iAmMoving = false;
				iAmDead = true;
			}
			iAmPushed = false;
		}
		if( iHaveCollidedWithMap() == true && iAmMoving == true)
		{
			iAmMoving = false;
		}
		else if (iHaveCollidedWithBlock() == true && iAmMoving == true)
		{
			iAmMoving = false;
		}
		else if (iHaveCollidedWithMovingBlock() == true && iAmMoving == true)
		{
			bounce();				
		}
		moving();
		draw();
	}

}//Class ends here.
