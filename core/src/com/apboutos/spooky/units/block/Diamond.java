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
 * This class represents a diamond block. Diamond blocks are special blocks that grand an extra
 * life to the player when all are gathered in the same place. When a diamond block collides with 
 * another diamond block, they merge and they both transform into super diamond blocks. A super diamond 
 * block has all the properties on a normal diamond block but the player cannot push it any further.
 * The first diamond block that becomes a super diamond block also becomes the base of stacking 
 * and if the player wants the extra life he must gather all the other diamond blocks where the 
 * base is. A diamond block cannot be pushed by an enemy, it cannot be the spawn place of an enemy
 * and it cannot be destroyed by any means.
 * 
 * 
 * @author Apostolis Boutos
 *
 */
public class Diamond extends Block{

	@SuppressWarnings("unused")
	public static int numberOfDiamondBlocks = 0; //Keeps track of how many diamond blocks exist in the level.
	public static int numberOfSuperDiamondBlocks = 0; //Keeps track of how many super diamond blocks exist.
	public static int numberOfDiamondsOnBase = 0; // Keeps track of how many super diamond blocks are stacked on the base.

	//If the above three variables are equal at any point during the level except when they are all 0, then the player
	//must get an extra life.
	//TODO implement the extra life feature when all diamond blocks are stacked.
	
	public Diamond(int x, int y, SpriteBatch batch, BlockType type){
		
		super();
		this.type = type; //Set the paren't block type.		
		this.batch = batch;
		block = new Sprite(TextureLoader.diamondBlock);
		deadBlock = new Animation<TextureRegion>(1/10f,TextureLoader.deadStandardBlock.getRegions());
		bounds.set((float)x* GameDimensions.unitWidth,(float)y*GameDimensions.unitHeight, GameDimensions.unitWidth, GameDimensions.unitHeight);
		block.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);


	    tmpBounds.setSize( GameDimensions.unitWidth, GameDimensions.unitHeight);
	    numberOfSuperDiamondBlocks = 0;
	    numberOfDiamondsOnBase = 0;
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
		
		
		if (isPushed)
		{
			Movability tmp = iAmEligibleToMove();
			//If the diamond is super it must not move.
			if ( tmp == Movability.eligible && !iAmSuper)
			{
				isMoving = true;
			}
			else if (tmp == Movability.blocked)
			{
				isMoving = false;
			}
			else if (tmp == Movability.blockedByDiamond && !iAmSuper)
			{
				isMoving = true;
			}
			isPushed = false;
		}
		if( iHaveCollidedWithMap() && isMoving)
		{
			isMoving = false;
		}
		else if (iHaveCollidedWithBlock() && isMoving)
		{
			if(!iHaveCollidedWithDiamondBlock())
			{
				isMoving = false;
			}		
		}
		else if (iHaveCollidedWithMovingBlock() && isMoving && !iAmSuper)
		{
			bounce();				
		}
		//The following if statement will be true only once, when a diamond block that
		//is not super stacks on top of another diamond block.
		if(iAmOnTopOfaDiamond() && !iAmSuper)
		{
			isMoving = false;
			iAmSuper = true;
			block.setTexture(TextureLoader.superDiamondBlock);
			numberOfSuperDiamondBlocks++;
			boolean flag = true;
			for (Block i: blockList)
			{
				if(i.isBase())
				{
					flag = false;
				}
			}
			//Checks if a base diamond exists and if not sets this diamond as the base of stacking.
			if (flag){
				iAmBase = true;
			}
			if (iAmOnTopOfaBaseDiamond())
			{
				numberOfDiamondsOnBase++;
			}
		}
		moving();
		draw();
	}
	
	/**
	 * As the name suggests this method tells you whether or not this diamond block
	 * is stacked on top of another diamond block.
	 * 
	 * @return boolean
	 */
	private boolean iAmOnTopOfaDiamond(){
		
		for (Block i : blockList)
		{
			if (bounds.x == i.getBounds().x && bounds.y == i.getBounds().y && i!=this)
			{
				return true;
			}
		}
		return false;
	
	}
	
	/**
	 * As the name suggests this methods tells you whether this diamond block is stacked
	 * on top of the base diamond block.
	 * 
	 * @return boolean
	 */
	private boolean iAmOnTopOfaBaseDiamond(){
		
		for (Block i : blockList)
		{
			if (bounds.x == i.getBounds().x && bounds.y == i.getBounds().y && i.isBase())
			{
				return true;
			}
		}
		return false;
	}
	
	
}//Class ends here.
