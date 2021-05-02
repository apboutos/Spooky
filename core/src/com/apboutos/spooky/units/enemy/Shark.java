package com.apboutos.spooky.units.enemy;

import com.apboutos.spooky.level.TextureLoader;
import com.apboutos.spooky.utilities.EnemyType;
import com.apboutos.spooky.utilities.Speed;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;


/**
 * 
 * This class represents the shark enemy. The shark is one of the smarter enemies.
 * He can push blocks and increase his moving speed when he is close to the player.
 * 
 * @author Apostolis Boutos
 *
 */

public class Shark extends Enemy{

	public Shark(float x, float y) {
		super(x, y, EnemyType.Shark);

		animationUp = new Animation<>(1 / 10f, TextureLoader.sharkUp.getRegions());
		animationDown = new Animation<>(1 / 10f, TextureLoader.sharkDown.getRegions());
		animationLeft = new Animation<>(1 / 10f, TextureLoader.sharkLeft.getRegions());
		animationRight = new Animation<>(1 / 10f, TextureLoader.sharkRight.getRegions());
		squash = new Sprite(TextureLoader.squash);

		speed  = Speed.SHARK_SPEED;
	}

}