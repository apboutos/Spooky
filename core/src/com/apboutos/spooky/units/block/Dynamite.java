package com.apboutos.spooky.units.block;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.utils.TimeUtils;

import com.apboutos.spooky.effects.Explosion;
import com.apboutos.spooky.level.TextureLoader;
import com.apboutos.spooky.utilities.BlockType;
import com.apboutos.spooky.utilities.GameDimensions;
import com.apboutos.spooky.utilities.Movability;

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
public class Dynamite extends Block{

	private boolean bang = false;

	public Dynamite(int x, int y, SpriteBatch batch, BlockType type, TextureLoader textureLoader){
		
		super();
		this.textureLoader = textureLoader;
		this.type = type; //Set the paren't block type.		
		this.batch = batch;

		
		if (type == BlockType.Dynamite)
		{
			block = new Sprite(textureLoader.getDynamiteBlock());
			deadBlockAtlas = textureLoader.getDeadDynamiteBlock();
		}
		else if (type == BlockType.BigDynamite)
		{
			block = new Sprite(textureLoader.getBigDynamiteBlock());
			deadBlockAtlas = textureLoader.getDeadBigDynamiteBlock();
		}
		
		bounds.set((float)x*GameDimensions.unitWidth,(float)y*GameDimensions.unitHeight, GameDimensions.unitWidth, GameDimensions.unitHeight);	
		block.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
		
		
	    deadBlock = new Animation(1/10f,deadBlockAtlas.getRegions(),PlayMode.LOOP);

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
			explode();
		}
		else if (iHaveCollidedWithBlock() == true && iAmMoving == true)
		{
			iAmMoving = false;
			explode();
		}
		else if (iHaveCollidedWithMovingBlock() == true && iAmMoving == true)
		{
			bounce();				
		}
		moving();
		draw();
		
	}
	
	
	private void explode(){
		
		if (bang == false)
		{
			if (type == BlockType.Dynamite)
			{
				explosionList.add(new Explosion(bounds.x,bounds.y,batch,textureLoader));   //Explosion at block.
				explosionList.add(new Explosion(bounds.x-40,bounds.y,batch,textureLoader)); //Explosion at left side.
				explosionList.add(new Explosion(bounds.x+40,bounds.y,batch,textureLoader)); //Explosion at right side.
				explosionList.add(new Explosion(bounds.x,bounds.y+40,batch,textureLoader)); //Explosion at top side.
				explosionList.add(new Explosion(bounds.x,bounds.y-40,batch,textureLoader)); //Explosion at bottom side.
				explosionList.add(new Explosion(bounds.x-40,bounds.y+40,batch,textureLoader)); //Explosion at top left corner.
				explosionList.add(new Explosion(bounds.x+40,bounds.y+40,batch,textureLoader)); //Explosion at top right corner.
				explosionList.add(new Explosion(bounds.x-40,bounds.y-40,batch,textureLoader)); //Explosion at bottom left corner.
				explosionList.add(new Explosion(bounds.x+40,bounds.y-40,batch,textureLoader)); //Explosion at bottom right corner.
			}
			else if (type == BlockType.BigDynamite)
			{
				explosionList.add(new Explosion(bounds.x,bounds.y,batch,textureLoader));   //Explosion at block.
				explosionList.add(new Explosion(bounds.x-1,bounds.y,batch,textureLoader)); //Explosion at left side.
				explosionList.add(new Explosion(bounds.x+1,bounds.y,batch,textureLoader)); //Explosion at right side.
				explosionList.add(new Explosion(bounds.x,bounds.y+1,batch,textureLoader)); //Explosion at top side.
				explosionList.add(new Explosion(bounds.x,bounds.y-1,batch,textureLoader)); //Explosion at bottom side.
				explosionList.add(new Explosion(bounds.x-1,bounds.y+1,batch,textureLoader)); //Explosion at top left corner.
				explosionList.add(new Explosion(bounds.x+1,bounds.y+1,batch,textureLoader)); //Explosion at top right corner.
				explosionList.add(new Explosion(bounds.x-1,bounds.y-1,batch,textureLoader)); //Explosion at bottom left corner.
				explosionList.add(new Explosion(bounds.x+1,bounds.y-1,batch,textureLoader)); //Explosion at bottom right corner.
				//Outer ring of explosions.
				//Left side.
				explosionList.add(new Explosion(bounds.x-80,bounds.y+40,batch,textureLoader));
				explosionList.add(new Explosion(bounds.x-80,bounds.y,batch,textureLoader));
				explosionList.add(new Explosion(bounds.x-80,bounds.y-40,batch,textureLoader));
				//Right side.
				explosionList.add(new Explosion(bounds.x+80,bounds.y+40,batch,textureLoader));
				explosionList.add(new Explosion(bounds.x+80,bounds.y,batch,textureLoader));
				explosionList.add(new Explosion(bounds.x+80,bounds.y-40,batch,textureLoader));
				//Top side.
				explosionList.add(new Explosion(bounds.x-40,bounds.y+80,batch,textureLoader));
				explosionList.add(new Explosion(bounds.x,bounds.y+80,batch,textureLoader));
				explosionList.add(new Explosion(bounds.x+40,bounds.y+80,batch,textureLoader));
				//Bottom side.
				explosionList.add(new Explosion(bounds.x-40,bounds.y-80,batch,textureLoader));
				explosionList.add(new Explosion(bounds.x,bounds.y-80,batch,textureLoader));
				explosionList.add(new Explosion(bounds.x+40,bounds.y-80,batch,textureLoader));
				
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
		if (iAmDead == true && TimeUtils.timeSinceMillis(deathtimer) > deadBlock.getAnimationDuration()*6000)
		{
			explode();
			return this;
		}
		else if (bang == true)
		{
			return this;
		}
		else
		{
			return null;
		}
	}
	
}//Class ends here.
