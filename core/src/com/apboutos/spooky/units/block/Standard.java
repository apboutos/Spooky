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
 * This class represents a standard block that can be pushed both by the player and
 * the enemies and can also spawn enemies.
 * 
 * @author exophrenik
 *
 */
@SuppressWarnings("DuplicatedCode")
public class Standard extends Block{

	
	public Standard(int x, int y, SpriteBatch batch, BlockType type) {
		
		super();
		this.type = type; //Set the paren't block type.		
		this.batch = batch;

		deadBlock = new Animation<TextureRegion>(1/10f, TextureLoader.deadStandardBlock.getRegions());
		block = new Sprite(TextureLoader.standardBlock);

		bounds.set((float)x* GameDimensions.unitWidth,(float)y*GameDimensions.unitHeight, GameDimensions.unitWidth, GameDimensions.unitHeight);
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
		
		if (iAmPushed)
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
			iAmMoving = false;
		}
		else if (iHaveCollidedWithBlock() && iAmMoving)
		{
			iAmMoving = false;
		}
		else if (iHaveCollidedWithMovingBlock() && iAmMoving)
		{
			bounce();				
		}
		moving();
		draw();
	}

}//Class ends here.
