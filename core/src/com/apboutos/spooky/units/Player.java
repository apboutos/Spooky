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

		playerMovingUp = new Animation<>(1 / 10f, TextureLoader.playerMovingUp.getRegions());
		playerMovingDown = new Animation<>(1 / 10f, TextureLoader.playerMovingDown.getRegions());
		playerMovingLeft = new Animation<>(1 / 10f, TextureLoader.playerMovingLeft.getRegions());
		playerMovingRight = new Animation<>(1 / 10f, TextureLoader.playerMovingRight.getRegions());

		squash = new Sprite(TextureLoader.squash);

		bounds = new Rectangle();
		speed  = Speed.PLAYER_SPEED;
		
		direction   = Direction.LEFT;

		bounds.x = x * GameDimensions.unitWidth;
		bounds.y = y * GameDimensions.unitHeight;
		bounds.width  = GameDimensions.unitWidth;
		bounds.height = GameDimensions.unitHeight;

		deathDuration = 1500;

	}

	/**
	 * Marks this Player as stopped. A stopped Player no longer has it's position changed automatically by it's speed and
	 * therefore will not move on the screen.
	 */
	public void stop(){
		isMoving = false;
	}

	/**
	 * Marks this Player as dying. When a Player is dying it's death animation is drawn and it no longer moves or takes
	 * part in collision detections. The Player will be removed from the unit list when it's death animation has ended
	 * and it is marked as dead.
	 */
	public void kill(){
		if(!this.isDying()) {
			isMoving = false;
			deathTimerStarted = true;
			deathTimer = TimeUtils.millis();
		}
	}

}