package com.apboutos.spooky.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


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



public class TextureLoader {

	private TextureAtlas playerMovingLeft;
	private TextureAtlas playerMovingRight;
	private TextureAtlas playerMovingUp;
	private TextureAtlas playerMovingDown;
	
	private TextureAtlas deadStandardBlock;
	private TextureAtlas deadBouncingBlock;
	private TextureAtlas deadBigBouncingBlock;
	private TextureAtlas deadDynamiteBlock;
	private TextureAtlas deadBigDynamiteBlock;
	private TextureAtlas explosion;
	
	private Texture standardBlock;
	private Texture bouncingBlock;
	private Texture bigBouncingBlock;
	private Texture diamondBlock;
	private Texture superDiamondBlock;
	private Texture dynamiteBlock;
	private Texture bigDynamiteBlock;
	
	private TextureAtlas fishMovingLeft;
	private TextureAtlas fishMovingRight;
	private TextureAtlas fishMovingUp;
	private TextureAtlas fishMovingDown;
	
	private TextureAtlas sharkLeft;
	private TextureAtlas sharkRight;
	private TextureAtlas sharkUp;
	private TextureAtlas sharkDown;
	
	private Texture starRed;
	private Texture starBlue;
	private Texture starYellow;
	private Texture starGrey;
	
	private Texture squash;
	private Texture bubblesBackground;
	
	private Texture newGameButton;
	
	
	
	/**
	 * Constructor that loads all the game's textures in the memory
	 * TODO For even more efficiency this class should be customized to load into memory only the
	 * textures required for the level the player is currently in.
	 */
	public TextureLoader(){
		
		playerMovingLeft  = new TextureAtlas(Gdx.files.internal("Images/Unit/Player/SpookyLeft.atlas"));
		playerMovingRight = new TextureAtlas(Gdx.files.internal("Images/Unit/Player/SpookyRight.atlas"));
		playerMovingUp    = new TextureAtlas(Gdx.files.internal("Images/Unit/Player/SpookyUp.atlas"));
		playerMovingDown  = new TextureAtlas(Gdx.files.internal("Images/Unit/Player/SpookyDown.atlas"));
		
		deadStandardBlock    = new TextureAtlas(Gdx.files.internal("Images/Unit/Block/Standard/DeadStandardBlock.atlas"));
		deadBouncingBlock    = new TextureAtlas(Gdx.files.internal("Images/Unit/Block/Bouncing/DeadBouncingBlock.atlas"));
		deadBigBouncingBlock = new TextureAtlas(Gdx.files.internal("Images/Unit/Block/BigBouncing/DeadBigBouncingBlock.atlas"));
		deadDynamiteBlock    = new TextureAtlas(Gdx.files.internal("Images/Unit/Block/Dynamite/DeadDynamiteBlock.atlas"));
		deadBigDynamiteBlock = new TextureAtlas(Gdx.files.internal("Images/Unit/Block/BigDynamite/DeadBigDynamiteBlock.atlas"));
		explosion            = new TextureAtlas(Gdx.files.internal("Images/Effect/Explosion.atlas"));
		
		standardBlock     = new Texture(Gdx.files.internal("Images/Unit/Block/Standard/StandardBlock.png"));
		bouncingBlock     = new Texture(Gdx.files.internal("Images/Unit/Block/Bouncing/BouncingBlock.png"));
		bigBouncingBlock  = new Texture(Gdx.files.internal("Images/Unit/Block/BigBouncing/BigBouncingBlock.png"));
		diamondBlock      = new Texture(Gdx.files.internal("Images/Unit/Block/Diamond/DiamondBlock.png"));
		superDiamondBlock = new Texture(Gdx.files.internal("Images/Unit/Block/Diamond/SuperDiamondBlock.png"));
		dynamiteBlock     = new Texture(Gdx.files.internal("Images/Unit/Block/Dynamite/DynamiteBlock.png"));
		bigDynamiteBlock  = new Texture(Gdx.files.internal("Images/Unit/Block/BigDynamite/BigDynamiteBlock.png"));
		
		
		fishMovingLeft  = new TextureAtlas(Gdx.files.internal("Images/Unit/Enemy/Fish/FishLeft.atlas"));
		fishMovingRight = new TextureAtlas(Gdx.files.internal("Images/Unit/Enemy/Fish/FishRight.atlas"));
		fishMovingUp    = new TextureAtlas(Gdx.files.internal("Images/Unit/Enemy/Fish/FishUp.atlas"));
		fishMovingDown  = new TextureAtlas(Gdx.files.internal("Images/Unit/Enemy/Fish/FishDown.atlas"));
	
		sharkLeft  = new TextureAtlas(Gdx.files.internal("Images/Unit/Enemy/Shark/SharkLeft.atlas"));
		sharkRight = new TextureAtlas(Gdx.files.internal("Images/Unit/Enemy/Shark/SharkRight.atlas"));
		sharkUp    = new TextureAtlas(Gdx.files.internal("Images/Unit/Enemy/Shark/SharkUp.atlas"));
		sharkDown  = new TextureAtlas(Gdx.files.internal("Images/Unit/Enemy/Shark/SharkDown.atlas"));
		
		
		starRed    = new Texture(Gdx.files.internal("Images/Effect/SquashStarRed.png"));
		starBlue   = new Texture(Gdx.files.internal("Images/Effect/SquashStarBlue.png"));
		starYellow = new Texture(Gdx.files.internal("Images/Effect/SquashStarYellow.png"));
		starGrey   = new Texture(Gdx.files.internal("Images/Effect/SquashStarGrey.png"));
		
		squash = new Texture(Gdx.files.internal("Images/Effect/Squash.png"));
		bubblesBackground = new Texture(Gdx.files.internal("Images/Background/BubblesBackground2.jpg"));
		
		newGameButton = new Texture(Gdx.files.internal("Images/Background/BubblesBackground2.jpg"));
	}

	/**
	 * Clean up dispose method.
	 */
	public void dispose(){
		
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

	public TextureAtlas getPlayerMovingLeft() {
		return playerMovingLeft;
	}


	public TextureAtlas getPlayerMovingRight() {
		return playerMovingRight;
	}


	public TextureAtlas getPlayerMovingUp() {
		return playerMovingUp;
	}


	public TextureAtlas getPlayerMovingDown() {
		return playerMovingDown;
	}


	public TextureAtlas getDeadStandardBlock() {
		return deadStandardBlock;
	}


	public TextureAtlas getDeadBouncingBlock() {
		return deadBouncingBlock;
	}

	
	public TextureAtlas getDeadBigBouncingBlock(){
		return deadBigBouncingBlock;
	}

	
	public TextureAtlas getDeadDynamiteBlock(){
		return deadDynamiteBlock;
	}
	
	
	public TextureAtlas getDeadBigDynamiteBlock(){
		return deadBigDynamiteBlock;
	}
	
	
	public Texture getStandardBlock() {
		return standardBlock;
	}


	public Texture getBouncingBlock() {
		return bouncingBlock;
	}

	
	public Texture getBigBouncingBlock(){
		return bigBouncingBlock;
	}
	
	
	public Texture getDiamondBlock() {
		return diamondBlock;
	}
	
	public Texture getSuperDiamondBlock(){
		return superDiamondBlock;
	}
	
	public Texture getDynamiteBlock(){
		return dynamiteBlock;
	}
	
	public Texture getBigDynamiteBlock(){
		return bigDynamiteBlock;
	}
	
	public TextureAtlas getFishMovingLeft() {
		return fishMovingLeft;
	}


	public TextureAtlas getFishMovingRight() {
		return fishMovingRight;
	}


	public TextureAtlas getFishMovingUp() {
		return fishMovingUp;
	}


	public TextureAtlas getFishMovingDown() {
		return fishMovingDown;
	}
	
	
	public TextureAtlas getSharkLeft(){
		return sharkLeft;
	}
	
	
	public TextureAtlas getSharkRight(){
		return sharkRight;
	}
	
	
	public TextureAtlas getSharkUp(){
		return sharkUp;
	}
	
	
	public TextureAtlas getSharkDown(){
		return sharkDown;
	}
	
	
	public Texture getStarRed() {
		return starRed;
	}

	public Texture getStarBlue() {
		return starBlue;
	}

	public Texture getStarYellow() {
		return starYellow;
	}
	
	public Texture getStarGrey() {
		return starGrey;
	}
	
	public Texture getSquash() {
		return squash;
	}
	
	public TextureAtlas getExplosion(){
		return explosion;
	}
	
	public Texture getBubblesBackground(){
		return bubblesBackground;
	}
	
	public Texture getNewGameButton(){
		return newGameButton;
	}
}
