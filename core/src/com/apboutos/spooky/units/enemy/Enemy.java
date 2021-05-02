package com.apboutos.spooky.units.enemy;

import com.apboutos.spooky.units.Unit;
import com.apboutos.spooky.units.block.Block;
import com.apboutos.spooky.utilities.Direction;
import com.apboutos.spooky.utilities.EnemyType;
import com.apboutos.spooky.utilities.GameDimensions;
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

	}

	/**
	 * Returns true if the enemy is dead and 500 milliseconds have passed since his death.
	 */
	@Override
	public boolean isDead(){
		return isDead && TimeUtils.timeSinceMillis(deathTimer) > 500;
	}

}
