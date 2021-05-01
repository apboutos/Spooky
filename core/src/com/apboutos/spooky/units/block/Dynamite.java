package com.apboutos.spooky.units.block;

import com.apboutos.spooky.level.TextureLoader;
import com.apboutos.spooky.utilities.BlockType;
import com.apboutos.spooky.utilities.GameDimensions;
import com.apboutos.spooky.utilities.Movability;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;

import com.apboutos.spooky.effects.Explosion;

/**
 * This class represents a dynamite block. A dynamite block is a special type of block
 * that explodes and destroys all the enemies and players in an area around it. In order
 * for the dynamite block to explode the player must either push it onto other blocks or
 * map walls, or if the dynamite block is blocked and cannot be pushed, instead of being 
   destroyed it becomes ignited and blows up after 3 seconds. There are two types of 
 * dynamite blocks, normal and big ones, the only difference being the explosion's area of
 * effect. Dynamite blocks can be pushed or ignited only by the player and enemies cannot
 * spawn inside them.
 * 
 * @author Apostolis Boutos
 *
 */
@SuppressWarnings("DuplicatedCode")
public class Dynamite extends Block{

	private boolean bang = false;

	public Dynamite(int x, int y, SpriteBatch batch, BlockType type){
		
		super();
		this.type = type; //Set the paren't block type.		
		this.batch = batch;

		if(type == BlockType.Dynamite){
			deadBlock = new Animation<TextureRegion>(1/10f, TextureLoader.deadDynamiteBlock.getRegions());
			block = new Sprite(TextureLoader.dynamiteBlock);
		}
		else {
			deadBlock = new Animation<TextureRegion>(1/10f, TextureLoader.deadBigDynamiteBlock.getRegions());
			block = new Sprite(TextureLoader.bigDynamiteBlock);
		}

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
			explode();
		}
		else if (iHaveCollidedWithBlock() && iAmMoving)
		{
			iAmMoving = false;
			explode();
		}
		else if (iHaveCollidedWithMovingBlock() && iAmMoving)
		{
			bounce();				
		}
		moving();
		draw();
		
	}
	
	
	private void explode(){
		
		if (!bang)
		{
			if (type == BlockType.Dynamite)
			{
				explosionList.add(new Explosion(bounds.x,bounds.y,batch));   //Explosion at block.
				explosionList.add(new Explosion(bounds.x-40,bounds.y,batch)); //Explosion at left side.
				explosionList.add(new Explosion(bounds.x+40,bounds.y,batch)); //Explosion at right side.
				explosionList.add(new Explosion(bounds.x,bounds.y+40,batch)); //Explosion at top side.
				explosionList.add(new Explosion(bounds.x,bounds.y-40,batch)); //Explosion at bottom side.
				explosionList.add(new Explosion(bounds.x-40,bounds.y+40,batch)); //Explosion at top left corner.
				explosionList.add(new Explosion(bounds.x+40,bounds.y+40,batch)); //Explosion at top right corner.
				explosionList.add(new Explosion(bounds.x-40,bounds.y-40,batch)); //Explosion at bottom left corner.
				explosionList.add(new Explosion(bounds.x+40,bounds.y-40,batch)); //Explosion at bottom right corner.
			}
			else if (type == BlockType.BigDynamite)
			{
				explosionList.add(new Explosion(bounds.x,bounds.y,batch));   //Explosion at block.
				explosionList.add(new Explosion(bounds.x-1,bounds.y,batch)); //Explosion at left side.
				explosionList.add(new Explosion(bounds.x+1,bounds.y,batch)); //Explosion at right side.
				explosionList.add(new Explosion(bounds.x,bounds.y+1,batch)); //Explosion at top side.
				explosionList.add(new Explosion(bounds.x,bounds.y-1,batch)); //Explosion at bottom side.
				explosionList.add(new Explosion(bounds.x-1,bounds.y+1,batch)); //Explosion at top left corner.
				explosionList.add(new Explosion(bounds.x+1,bounds.y+1,batch)); //Explosion at top right corner.
				explosionList.add(new Explosion(bounds.x-1,bounds.y-1,batch)); //Explosion at bottom left corner.
				explosionList.add(new Explosion(bounds.x+1,bounds.y-1,batch)); //Explosion at bottom right corner.
				//Outer ring of explosions.
				//Left side.
				explosionList.add(new Explosion(bounds.x-80,bounds.y+40,batch));
				explosionList.add(new Explosion(bounds.x-80,bounds.y,batch));
				explosionList.add(new Explosion(bounds.x-80,bounds.y-40,batch));
				//Right side.
				explosionList.add(new Explosion(bounds.x+80,bounds.y+40,batch));
				explosionList.add(new Explosion(bounds.x+80,bounds.y,batch));
				explosionList.add(new Explosion(bounds.x+80,bounds.y-40,batch));
				//Top side.
				explosionList.add(new Explosion(bounds.x-40,bounds.y+80,batch));
				explosionList.add(new Explosion(bounds.x,bounds.y+80,batch));
				explosionList.add(new Explosion(bounds.x+40,bounds.y+80,batch));
				//Bottom side.
				explosionList.add(new Explosion(bounds.x-40,bounds.y-80,batch));
				explosionList.add(new Explosion(bounds.x,bounds.y-80,batch));
				explosionList.add(new Explosion(bounds.x+40,bounds.y-80,batch));
				
			}
		}	
		bang = true;
	}
	
	
	/**
	 * When the block is destroyed this method returns this
	 * block back to the level, so the level can know which block 
	 * to remove from it's blockList. 
	 */
	@ Override
	public Block getDeadBlock()
	{
		if (iAmDead && TimeUtils.timeSinceMillis(deathTimer) > deadBlock.getAnimationDuration()*6000)
		{
			explode();
			return this;
		}
		else if (bang)
		{
			return this;
		}
		else
		{
			return null;
		}
	}
	
}//Class ends here.
