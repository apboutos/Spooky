package com.apboutos.spooky.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import lombok.Getter;


/**
 * 
 * @author Apostolis Boutos
 *
 *	The purpose of this class is to load into memory all the textures that are going
 *	to be used during the game and pass down the handles to these textures to all 
 *	other classes that need them. This way if a level contains 50 Block objects there
 *	is only one Block texture loaded into memory instead of 50 which reduces the
 *	application's memory footprint.
 */


@SuppressWarnings("DuplicatedCode")
@Getter
public class TextureLoader {

	public static final TextureAtlas playerMovingLeft = new TextureAtlas(Gdx.files.internal("Images/Unit/Player/SpookyLeft.atlas"));
	public static final TextureAtlas playerMovingRight = new TextureAtlas(Gdx.files.internal("Images/Unit/Player/SpookyRight.atlas"));
	public static final TextureAtlas playerMovingUp    = new TextureAtlas(Gdx.files.internal("Images/Unit/Player/SpookyUp.atlas"));
	public static final TextureAtlas playerMovingDown  = new TextureAtlas(Gdx.files.internal("Images/Unit/Player/SpookyDown.atlas"));

	public static final TextureAtlas deadStandardBlock    = new TextureAtlas(Gdx.files.internal("Images/Unit/Block/Standard/DeadStandardBlock.atlas"));
	public static final TextureAtlas deadBouncingBlock    = new TextureAtlas(Gdx.files.internal("Images/Unit/Block/Bouncing/DeadBouncingBlock.atlas"));
	public static final TextureAtlas deadBigBouncingBlock = new TextureAtlas(Gdx.files.internal("Images/Unit/Block/BigBouncing/DeadBigBouncingBlock.atlas"));
	public static final TextureAtlas deadDynamiteBlock    = new TextureAtlas(Gdx.files.internal("Images/Unit/Block/Dynamite/DeadDynamiteBlock.atlas"));
	public static final TextureAtlas deadBigDynamiteBlock = new TextureAtlas(Gdx.files.internal("Images/Unit/Block/BigDynamite/DeadBigDynamiteBlock.atlas"));
	public static final TextureAtlas explosion            = new TextureAtlas(Gdx.files.internal("Images/Effect/Explosion.atlas"));

	public static final Texture standardBlock     = new Texture(Gdx.files.internal("Images/Unit/Block/Standard/StandardBlock.png"));
	public static final Texture bouncingBlock     = new Texture(Gdx.files.internal("Images/Unit/Block/Bouncing/BouncingBlock.png"));
	public static final Texture bigBouncingBlock  = new Texture(Gdx.files.internal("Images/Unit/Block/BigBouncing/BigBouncingBlock.png"));
	public static final Texture diamondBlock      = new Texture(Gdx.files.internal("Images/Unit/Block/Diamond/DiamondBlock.png"));
	public static final Texture superDiamondBlock = new Texture(Gdx.files.internal("Images/Unit/Block/Diamond/SuperDiamondBlock.png"));
	public static final Texture dynamiteBlock     = new Texture(Gdx.files.internal("Images/Unit/Block/Dynamite/DynamiteBlock.png"));
	public static final Texture bigDynamiteBlock  = new Texture(Gdx.files.internal("Images/Unit/Block/BigDynamite/BigDynamiteBlock.png"));

	public static final TextureAtlas fishMovingLeft  = new TextureAtlas(Gdx.files.internal("Images/Unit/Enemy/Fish/FishLeft.atlas"));
	public static final TextureAtlas fishMovingRight = new TextureAtlas(Gdx.files.internal("Images/Unit/Enemy/Fish/FishRight.atlas"));
	public static final TextureAtlas fishMovingUp    = new TextureAtlas(Gdx.files.internal("Images/Unit/Enemy/Fish/FishUp.atlas"));
	public static final TextureAtlas fishMovingDown  = new TextureAtlas(Gdx.files.internal("Images/Unit/Enemy/Fish/FishDown.atlas"));

	public static final TextureAtlas sharkLeft  = new TextureAtlas(Gdx.files.internal("Images/Unit/Enemy/Shark/SharkLeft.atlas"));
	public static final TextureAtlas sharkRight = new TextureAtlas(Gdx.files.internal("Images/Unit/Enemy/Shark/SharkRight.atlas"));
	public static final TextureAtlas sharkUp    = new TextureAtlas(Gdx.files.internal("Images/Unit/Enemy/Shark/SharkUp.atlas"));
	public static final TextureAtlas sharkDown  = new TextureAtlas(Gdx.files.internal("Images/Unit/Enemy/Shark/SharkDown.atlas"));


	public static final Texture starRed    = new Texture(Gdx.files.internal("Images/Effect/SquashStarRed.png"));
	public static final Texture starBlue   = new Texture(Gdx.files.internal("Images/Effect/SquashStarBlue.png"));
	public static final Texture starYellow = new Texture(Gdx.files.internal("Images/Effect/SquashStarYellow.png"));
	public static final Texture starGrey   = new Texture(Gdx.files.internal("Images/Effect/SquashStarGrey.png"));
	public static final Texture starGreen  = new Texture(Gdx.files.internal("Images/Effect/SquashStarGrey.png"));

	public static final Texture squash = new Texture(Gdx.files.internal("Images/Effect/Squash.png"));
	public static final Texture bubblesBackground = new Texture(Gdx.files.internal("Images/Background/BubblesBackground2.jpg"));

	public static final Texture newGameButton = new Texture(Gdx.files.internal("Images/Background/BubblesBackground2.jpg"));

	private TextureLoader(){

	}

	/**
	 * Clean up dispose method.
	 */
	public static void dispose(){
		
		playerMovingLeft.dispose();
		playerMovingRight.dispose();
		playerMovingUp.dispose();
		playerMovingDown.dispose();
		
		deadStandardBlock.dispose();
		deadBouncingBlock.dispose();
		deadBigBouncingBlock.dispose();
		deadDynamiteBlock.dispose();
		deadBigDynamiteBlock.dispose();
		explosion.dispose();
		
		standardBlock.dispose();
		bouncingBlock.dispose();
		bigBouncingBlock.dispose();
		diamondBlock.dispose();
		superDiamondBlock.dispose();
		dynamiteBlock.dispose();
		bigDynamiteBlock.dispose();
		
		fishMovingLeft.dispose();
		fishMovingRight.dispose();
		fishMovingUp.dispose();
		fishMovingDown.dispose();
		
		sharkLeft.dispose();
		sharkRight.dispose();
		sharkUp.dispose();
		sharkDown.dispose();
		
		starRed.dispose();
		starBlue.dispose();
		starYellow.dispose();
		squash.dispose();
		starGrey.dispose();
		
		bubblesBackground.dispose();
		
		newGameButton.dispose();
		
	}
}
