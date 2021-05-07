package com.apboutos.spooky.units;

import com.apboutos.spooky.level.TextureLoader;
import com.apboutos.spooky.utilities.BlockType;
import com.apboutos.spooky.utilities.Direction;
import com.apboutos.spooky.utilities.GameDimensions;
import com.apboutos.spooky.utilities.Speed;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Model class representing a Block. Contains only the Block's state and method for changing that state.
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

		bounds = new Rectangle(x * GameDimensions.unitWidth,y * GameDimensions.unitHeight, GameDimensions.unitWidth,GameDimensions.unitHeight);
		this.type = type;
		loadTexturesByType();
		block.setBounds(bounds.x,bounds.y,bounds.width,bounds.height);
		speed = Speed.BLOCK_SPEED;
		switch (type){
			case Bouncing: maxNumberOfBounces = 1; break;
			case BigBouncing: maxNumberOfBounces = 2; break;
			default: maxNumberOfBounces = 0; break;
		}
		numberOfBounces = 0;
		deathDuration = deathAnimation.getAnimationDuration()*1000;
	}

	//TODO This needs work.
	public void explode(){
		if(type == BlockType.Dynamite || type == BlockType.BigDynamite){
			kill();
		}
	}

	/**
	 * Marks the Block as pushed, triggering the respective Block behavior.
	 * @param direction The Direction the Block is being push towards.
	 */
	public void push(Direction direction){
		if(!isMoving && !isPushed){
			this.direction = direction;
			isPushed = true;
		}
	}

	/**
	 * Marks this Block as moving. A moving Block will have it's position automatically changed according to it's speed
	 * and will appear as moving on the screen. Blocks that are marked as Super Diamonds cannot move.
	 */
	public void move(){
		if(!isSuperDiamond){
			isMoving = true;
		}
	}

	/**
	 * Marks this Block as dying. When a Block is dying it's death animation is drawn and it no longer moves or takes
	 * part in collision detections. The Block will be removed from the unit list when it's death animation has ended
	 * and it is marked as dead. A Block of type Diamond cannot be marked as dying.
	 */
	public void kill(){
		if(!this.isDying() && type != BlockType.Diamond){
			deathTimerStarted = true;
			deathTimer = TimeUtils.millis();
			isMoving = false;
			isPushed = false;
		}
	}

	/**
	 * Marks this Block as stopped. A stopped Block no longer has it's position changed automatically by it's speed and
	 * therefore will not move on the screen.
	 */
	public void stop(){
		isMoving = false;
		isPushed = false;
	}

	//TODO This needs to be implemented.
	/**
	 * Merges this Block with another diamond Block. This Block is marked as a Super Diamond from now on.
	 */
	public void merge(){

	}

	/**
	 * Tells the Block to bounce. A Block that bounces has it's direction reversed. This bounce action does not consume
	 * the block's bounce actions.
	 */
	public void freeBounce(){
		direction = Direction.reverseDirection(direction);
	}

	/**
	 * Tells the Block to bounce if it has bounce actions left. A Block that bounces has it's direction reversed.
	 */
	public void bounce(){
		if(numberOfBounces < maxNumberOfBounces){
		System.out.println("Block type: " + type + " numberOfBounces = " + numberOfBounces + " maxNumberOfBounces = " + maxNumberOfBounces );
			direction = Direction.reverseDirection(direction);
			numberOfBounces++;
		}else{
			stop();
			numberOfBounces = 0;
		}
	}

	/**
	 * Marks this Block as a SuperDiamond. A Super Diamond cannot be killed or moved awards the Player an extra life
	 * if it exists when the level ends.
	 */
	public void makeSuperDiamond(){
		if(!isSuperDiamond){
			isSuperDiamond = true;
			block.setTexture(TextureLoader.superDiamondBlock);
		}
	}

	/**
	 * Load's the Block's textures and animations by it's BlockType.
	 */
	private void loadTexturesByType(){
		switch (type){
			case Standard:
				deathAnimation = new Animation<>(1 / 30f, TextureLoader.deadStandardBlock.getRegions());
				block = new Sprite(TextureLoader.standardBlock);
				break;
			case Bouncing:
				deathAnimation = new Animation<>(1 / 30f, TextureLoader.deadBouncingBlock.getRegions());
				block = new Sprite(TextureLoader.bouncingBlock);
				break;
			case BigBouncing:
				deathAnimation = new Animation<>(1 / 30f, TextureLoader.deadBigBouncingBlock.getRegions());
				block = new Sprite(TextureLoader.bigBouncingBlock);
				break;
			case Dynamite:
				deathAnimation = new Animation<>(1 / 30f, TextureLoader.deadDynamiteBlock.getRegions());
				block = new Sprite(TextureLoader.dynamiteBlock);
				break;
			case BigDynamite:
				deathAnimation = new Animation<>(1 / 30f, TextureLoader.deadBigDynamiteBlock.getRegions());
				block = new Sprite(TextureLoader.bigDynamiteBlock);
				break;
			case Diamond:
				deathAnimation = new Animation<>(1 / 30f, TextureLoader.deadStandardBlock.getRegions());
				block = new Sprite(TextureLoader.diamondBlock);
				break;
		}
	}

}
