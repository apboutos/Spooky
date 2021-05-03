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

		bounds = new Rectangle(x * GameDimensions.unitWidth,y * GameDimensions.unitHeight, GameDimensions.unitWidth,GameDimensions.unitHeight);
		this.type = type;
		loadTexturesByType();
		block.setBounds(bounds.x,bounds.y,bounds.width,bounds.height);
		speed = Speed.BLOCK_SPEED;
		switch (type){
			case Bouncing: maxNumberOfBounces = 2; break;
			case BigBouncing: maxNumberOfBounces = 3; break;
			case Dynamite:
			case BigDynamite: maxNumberOfBounces = 0; break;
			default: maxNumberOfBounces = 1; break;
		}
		deathDuration = (long)deathAnimation.getAnimationDuration()*1000;
	}

	public void explode(){
		if(type == BlockType.Dynamite || type == BlockType.BigDynamite){
			kill();
		}
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
		if(!this.isDying()){
			deathTimerStarted = true;
			deathTimer = TimeUtils.millis();
			isMoving = false;
		}
	}

	public void stop(){
		isMoving = false;
	}

	public void merge(){

	}

	public void bounce(){
		if(numberOfBounces < maxNumberOfBounces) {
			direction = Direction.reverseDirection(direction);
			numberOfBounces++;
		}
	}

	public void makeSuperDiamond(){
		if(!isSuperDiamond){
			isSuperDiamond = true;
			block.setTexture(TextureLoader.superDiamondBlock);
		}
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
				block = new Sprite(TextureLoader.diamondBlock);
				break;
		}
	}

}
