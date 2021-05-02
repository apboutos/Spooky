package com.apboutos.spooky.units.enemy;

import com.apboutos.spooky.level.TextureLoader;
import com.apboutos.spooky.utilities.EnemyType;
import com.apboutos.spooky.utilities.Speed;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * 
 * This class represents the fish enemy. This is the most basic enemy. It has
 * very a simple AI which just moves and turns when it collides with a block.
 * 
 * @author Apostolis Boutos
 *
 */

public class Fish extends Enemy{

	public Fish(float x, float y) {
		super(x, y, EnemyType.Fish);

		animationUp = new Animation<>(1 / 10f, TextureLoader.fishMovingUp.getRegions());
		animationDown = new Animation<>(1 / 10f, TextureLoader.fishMovingDown.getRegions());
		animationLeft = new Animation<>(1 / 10f, TextureLoader.fishMovingLeft.getRegions());
		animationRight = new Animation<>(1 / 10f, TextureLoader.fishMovingRight.getRegions());
		squash = new Sprite(TextureLoader.squash);

		speed  = Speed.FISH_SPEED;
	}
}