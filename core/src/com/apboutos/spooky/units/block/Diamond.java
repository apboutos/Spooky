package com.apboutos.spooky.units.block;

import com.apboutos.spooky.level.TextureLoader;
import com.apboutos.spooky.utilities.BlockType;
import com.apboutos.spooky.utilities.GameDimensions;
import com.apboutos.spooky.utilities.Movability;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;




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

	public static int numberOfDiamondBlocks = 0; //Keeps track of how many diamond blocks exist in the level.
	public static int numberOfSuperDiamondBlocks = 0; //Keeps track of how many super diamond blocks exist.
	public static int numberOfDiamondsOnBase = 0; // Keeps track of how many super diamond blocks are stacked on the base.
	
	//If the above three variables are equal at any point during the level except when they are all 0, then the player
	//must get an extra life.
	//TODO implement the extra life feature when all diamond blocks are stacked.
	
	public Diamond(int x, int y, SpriteBatch batch, BlockType type, TextureLoader textureLoader){
		
		super();
		this.type = type; //Set the paren't block type.		
		this.batch = batch;
		this.textureLoader = textureLoader;
		block = new Sprite(textureLoader.getDiamondBlock());
		bounds.set((float)x* GameDimensions.unitWidth,(float)y*GameDimensions.unitHeight, GameDimensions.unitWidth, GameDimensions.unitHeight);
		block.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
		
		deadBlockAtlas = textureLoader.getDeadStandardBlock();
	    deadBlock = new Animation(1/10f,deadBlockAtlas.getRegions());

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
		
		
		if (iAmPushed == true)
		{
			Movability tmp = iAmEligibleToMove();
			//If the diamond is super it must not move.
			if ( tmp == Movability.eligible && iAmSuper == false)
			{
				iAmMoving = true;
			}
			else if (tmp == Movability.blocked)
			{
				iAmMoving = false;
			}
			else if (tmp == Movability.blockedByDiamond && iAmSuper == false)
			{
				iAmMoving = true;
			}
			iAmPushed = false;
		}
		if( iHaveCollidedWithMap() == true && iAmMoving == true)
		{
			iAmMoving = false;
		}
		else if (iHaveCollidedWithBlock() == true && iAmMoving == true)
		{
			if(iHaveCollidedWithDiamondBlock() == false)
			{
				iAmMoving = false;
			}		
		}
		else if (iHaveCollidedWithMovingBlock() == true && iAmMoving == true && iAmSuper == false)
		{
			bounce();				
		}
		//The following if statement will be true only once, when a diamond block that
		//is not super stacks on top of another diamond block.
		if(iAmOnTopOfaDiamond() == true && iAmSuper == false)
		{
			iAmMoving = false;
			iAmSuper = true;
			block.setTexture(textureLoader.getSuperDiamondBlock());
			numberOfSuperDiamondBlocks++;
			boolean flag = true;
			for (Block i: blockList)
			{
				if(i.isBase() == true)
				{
					flag = false;
				}
			}
			//Checks if a base diamond exists and if not sets this diamond as the base of stacking.
			if (flag == true){
				iAmBase = true;
			}
			if (iAmOnTopOfaBaseDiamond() == true)
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
			if (bounds.x == i.getBounds().x && bounds.y == i.getBounds().y && i.isBase() == true)
			{
				return true;
			}
		}
		return false;
	}
	
	
}//Class ends here.
