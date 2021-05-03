package com.apboutos.spooky.units;

import com.apboutos.spooky.level.TextureLoader;
import com.apboutos.spooky.utilities.Direction;
import com.apboutos.spooky.utilities.EnemyType;
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
 * Represents an enemy.
 * @author Apostolos Boutos
 */
@Getter
@Setter
public class Enemy extends Unit {


	protected Animation<TextureRegion> animationLeft;
	protected Animation<TextureRegion> animationRight;
	protected Animation<TextureRegion> animationUp;
	protected Animation<TextureRegion> animationDown;
	protected Sprite squash;

	protected EnemyType enemyType;

	protected int numberOfBlocksMoved;
	protected boolean collidedWithMap;
	protected boolean collidedWithBlock;
	protected Block lastBlockCollidedWith;

	public Enemy(float x,float y,EnemyType enemyType){

		this.enemyType = enemyType;
		direction   = Direction.LEFT;
		
		bounds = new Rectangle();
		
		bounds.x = x * GameDimensions.unitWidth;
		bounds.y = y * GameDimensions.unitHeight;
		bounds.width  = GameDimensions.unitWidth;
		bounds.height = GameDimensions.unitHeight;

		loadTexturesByType(enemyType);
		loadSpeedByType(enemyType);
	}

	public void kill(){
		isMoving = false;
		isDead = true;
		deathTimerStarted = true;
		deathTimer = TimeUtils.millis();
	}

	public void stopDueToCollisionWithMap(){
		isMoving = false;
		collidedWithMap = true;
	}

	public void stopDueToCollisionWithBlock(Block objectOfCollision){
		isMoving = false;
		collidedWithBlock = true;
		lastBlockCollidedWith = objectOfCollision;
	}

	public void loadTexturesByType(EnemyType type){
		switch (type){
			case Fish:
				animationUp = new Animation<>(1 / 10f, TextureLoader.fishMovingUp.getRegions());
				animationDown = new Animation<>(1 / 10f, TextureLoader.fishMovingDown.getRegions());
				animationLeft = new Animation<>(1 / 10f, TextureLoader.fishMovingLeft.getRegions());
				animationRight = new Animation<>(1 / 10f, TextureLoader.fishMovingRight.getRegions());
				squash = new Sprite(TextureLoader.squash);
				break;
			case Shark:
				animationUp = new Animation<>(1 / 10f, TextureLoader.sharkUp.getRegions());
				animationDown = new Animation<>(1 / 10f, TextureLoader.sharkDown.getRegions());
				animationLeft = new Animation<>(1 / 10f, TextureLoader.sharkLeft.getRegions());
				animationRight = new Animation<>(1 / 10f, TextureLoader.sharkRight.getRegions());
				squash = new Sprite(TextureLoader.squash);
				break;
		}
	}

	public void loadSpeedByType(EnemyType type){
		switch (type){
			case Fish: 	speed = Speed.FISH_SPEED; break;
			case Shark: speed = Speed.SHARK_SPEED; break;
		}
	}
	/**
	 * Returns true if the enemy is dead and 500 milliseconds have passed since his death.
	 */
	@Override
	public boolean isDead(){
		return isDead && TimeUtils.timeSinceMillis(deathTimer) > 500;
	}

	public boolean isDying(){
		return deathTimerStarted;
	}

}
