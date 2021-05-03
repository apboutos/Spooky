package com.apboutos.spooky.units;


import com.apboutos.spooky.level.TextureLoader;
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

import java.sql.Time;


/**
 * Model class that represents the player's unit.
 *
 * @author Apostolis Boutos
 */

@Setter
@Getter
public class Player extends Unit{


	private Animation<TextureRegion> playerMovingLeft;
	private Animation<TextureRegion> playerMovingRight;
	private Animation<TextureRegion> playerMovingUp;
	private Animation<TextureRegion> playerMovingDown;
	private Sprite squash;

	public Player(float x,float y){

		playerMovingUp = new Animation<TextureRegion>(1/10f,TextureLoader.playerMovingUp.getRegions());
		playerMovingDown = new Animation<TextureRegion>(1/10f,TextureLoader.playerMovingDown.getRegions());
		playerMovingLeft = new Animation<TextureRegion>(1/10f,TextureLoader.playerMovingLeft.getRegions());
		playerMovingRight = new Animation<TextureRegion>(1/10f,TextureLoader.playerMovingRight.getRegions());

		squash = new Sprite(TextureLoader.squash);

		bounds = new Rectangle();
		speed  = Speed.PLAYER_SPEED;
		
		direction   = Direction.LEFT;

		bounds.x = x * GameDimensions.unitWidth;
		bounds.y = y * GameDimensions.unitHeight;
		bounds.width  = GameDimensions.unitWidth;
		bounds.height = GameDimensions.unitHeight;

	}

	public void stop(){
		isMoving = false;
	}

	public void kill(){
		isMoving = false;
		isDead = true;
		deathTimerStarted = true;
		deathTimer = TimeUtils.millis();
	}

	@Override
	public boolean isDead(){
		return isDead && TimeUtils.timeSinceMillis(deathTimer) > 1500;
	}

	public boolean isDying(){
		return deathTimerStarted;
	}

}