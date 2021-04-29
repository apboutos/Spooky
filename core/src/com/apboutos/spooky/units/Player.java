package com.apboutos.spooky.units;


import com.apboutos.spooky.effects.Explosion;
import com.apboutos.spooky.effects.SquashStar;
import com.apboutos.spooky.level.Settings;
import com.apboutos.spooky.level.TextureLoader;
import com.apboutos.spooky.units.block.Block;
import com.apboutos.spooky.units.enemy.Enemy;
import com.apboutos.spooky.utilities.Direction;
import com.apboutos.spooky.utilities.GameDimensions;
import com.apboutos.spooky.utilities.StarColor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;


/**
 * Represents the player's unit
 * 
 *         
 * @author Apostolis Boutos
 *		
 */


public class Player {

	private TextureLoader textureLoader;
	
	private TextureAtlas atlasleft;
	private TextureAtlas atlasright;
	private TextureAtlas atlasup;
	private TextureAtlas atlasdown;
	
	private Animation<TextureRegion> spookyleft;
	private Animation<TextureRegion> spookyright;
	private Animation<TextureRegion> spookyup;
	private Animation<TextureRegion> spookydown;
	
	private Sprite squash;
	
	private SpriteBatch batch; // The game's SpriteBatch.
	private OrthographicCamera camera; // The game's camera.
	
	
	private Rectangle bounds; //The palyer's position (x,y) and the sprites width and height.
	private Rectangle tmpBounds; // Temporary Rectangle used for collision detection.
	private Direction direction; // The direction the player is facing.
	private Vector3 coords; //This Vector3 is used to store the coordinates of the player's touch event.
	private Vector2 speed; // The speed of the player's fish.
	private float delta; // The time passed between frames, required for animations	.
	private ArrayList<Block> blockList; // The array of Blocks from the LeveL, used in collision detection.
	private ArrayList<Enemy> enemyList; // The array of Enemies from the Level, used in collision detection.
	private ArrayList<SquashStar> squashList; // The array of SquashStars from the Level, used to create stars when player is dead.
	private ArrayList<Explosion> explosionList;
	private boolean iAmMoving; // Whether the player is moving or not.
	private boolean iAmPushing; // Whether the player has issued a push command or not.
	private boolean iAmDead; // Whether the player has been killed or not.
	private Settings settings; // The game's settings.
	private boolean deathTimerStarted = false; // Whether the death timer has started or not (default value false).
	private long deathtimer; // The starting time of the death animation.
	
	
	/**
	 * Constructor
	 * 
	 * @param x Original position x in units
	 * @param y Original position y in units
	 * @param batch A SpriteBatch object that will do the rendering
	 * @param camera The game's OrthographicCamera
	 */
	
	public Player(float x,float y,SpriteBatch batch,OrthographicCamera camera,Settings settings,TextureLoader textureLoader){
		
		this.textureLoader = textureLoader;
		atlasleft   = textureLoader.getPlayerMovingLeft();//new TextureAtlas(Gdx.files.internal("Images/Animations/Spooky/SpookyLeft.atlas"));
		atlasright  = textureLoader.getPlayerMovingRight();//new TextureAtlas(Gdx.files.internal("Images/Animations/Spooky/SpookyRight.atlas"));
		atlasup     = textureLoader.getPlayerMovingUp();//new TextureAtlas(Gdx.files.internal("Images/Animations/Spooky/SpookyUp.atlas"));
		atlasdown   = textureLoader.getPlayerMovingDown();//new TextureAtlas(Gdx.files.internal("Images/Animations/Spooky/SpookyDown.atlas"));
		spookyleft  = new Animation(1/10f, atlasleft.getRegions());
		spookyright = new Animation(1/10f,atlasright.getRegions());
		spookyup    = new Animation(1/10f,atlasup.getRegions());
		spookydown  = new Animation(1/10f,atlasdown.getRegions());
		bounds      = new Rectangle(); 
		tmpBounds   = new Rectangle();
		
		direction   = Direction.LEFT; 
		this.batch  = batch;
		this.camera = camera;
		this.settings = settings;
		bounds.x = x* GameDimensions.unitWidth;
		bounds.y = y*GameDimensions.unitHeight;
		bounds.width  = GameDimensions.unitWidth;
		bounds.height = GameDimensions.unitHeight;
		tmpBounds.x = 0;
		tmpBounds.y = 0;
		tmpBounds.width  = GameDimensions.unitWidth;
		tmpBounds.height = GameDimensions.unitWidth;
		
		squash = new Sprite(textureLoader.getSquash());
		
		coords = new Vector3(); 
		speed  = new Vector2(6,6); 
		iAmPushing = false; 
		iAmDead    = false;
	}
	
	/** 
	 *  Updates the player's character logic and draws him on the screen.
	 *  
	 *  <pre>
	 *  WARNING Must be placed between
	 *  
	 *  SpriteBatch.begin()
	 *  (here)
	 *  SpriteBatch.end()
	 *  </pre>
	 *
	 */
	
	public void update(float delta){
		
		this.delta = delta;
		inputHandle();
		collisionDetect();
		draw();

	}
	
	/**
	 * Draws the correct sprite or animation depending on direction
	 */
	private void draw()
	{
		if (iAmDead == true)
		{
			iAmMoving = false;
			squash.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
			squash.draw(batch);
			if (deathTimerStarted == false)
			{
				deathtimer = TimeUtils.millis(); //Start the death timer
				deathTimerStarted = true;
			}
		}
		else
		{
			if (direction == Direction.LEFT)
			{
				if (iAmMoving == true)
				{
					bounds.x = bounds.x - speed.x;
					batch.draw(spookyleft.getKeyFrame(delta, true),bounds.x,bounds.y);
				}
				else
				{
					batch.draw(spookyleft.getKeyFrame(0),bounds.x,bounds.y);
				}
				if ((int)bounds.x%GameDimensions.unitWidth == 0)
				{
					iAmMoving = false;
				}
			}
			if (direction == Direction.RIGHT)
			{
				if (iAmMoving == true)
				{
					bounds.x = bounds.x + speed.x;
					batch.draw(spookyright.getKeyFrame(delta, true),bounds.x,bounds.y);
				}
				else
				{
					batch.draw(spookyright.getKeyFrame(0),bounds.x,bounds.y);
				}
				if ((int)bounds.x%GameDimensions.unitWidth == 0)
				{
					iAmMoving = false;
				}
			}
			if (direction == Direction.DOWN)
			{
				if (iAmMoving == true)
				{
					bounds.y = bounds.y - speed.y;
					batch.draw(spookydown.getKeyFrame(delta, true),bounds.x,bounds.y);
				}
				else
				{
					batch.draw(spookydown.getKeyFrame(0),bounds.x,bounds.y);
				}
				if ((int)bounds.y%GameDimensions.unitHeight == 0)
				{
					iAmMoving = false;
				}
			}
			if (direction == Direction.UP)
			{
				if (iAmMoving == true)
				{
					bounds.y = bounds.y + speed.y;
					batch.draw(spookyup.getKeyFrame(delta, true),bounds.x,bounds.y);
				}
				else
				{
					batch.draw(spookyup.getKeyFrame(0),bounds.x,bounds.y);
				}
				if ((int)bounds.y%GameDimensions.unitHeight == 0)
				{
					iAmMoving = false;
				}
			} 
		}
	}
	/**
	 * Handles player's input
	 * Keys handled:LEFT,RIGHT,UP,DOWN
	 * 
	 * @return Returns true if a handled key is being pressed
	 */
	
	private void inputHandle(){
		coords.x = Gdx.input.getX();
		coords.y = Gdx.input.getY();
		camera.unproject(coords);
		iAmPushing = false;
		if (Gdx.input.isKeyJustPressed(Keys.SPACE) && settings.controls == 0)
		{
			push();
			//iAmPushing = true;
		}
		if (Gdx.input.isKeyPressed(Keys.UP) && settings.controls == 0 && iAmMoving == false)
		{
			iAmMoving = true;
			direction = Direction.UP;
		}
		else if (Gdx.input.isKeyPressed(Keys.DOWN) && settings.controls == 0 && iAmMoving == false)
		{
			iAmMoving = true;
			direction = Direction.DOWN;
		}
		else if (Gdx.input.isKeyPressed(Keys.LEFT) && settings.controls == 0 && iAmMoving == false)
		{
			iAmMoving = true;
			direction = Direction.LEFT;
		}
		else if (Gdx.input.isKeyPressed(Keys.RIGHT) && settings.controls == 0 && iAmMoving == false)
		{
			iAmMoving = true;
			direction = Direction.RIGHT;
		}
		else if (Gdx.input.isTouched() == true && settings.controls == 1) //TODO Remake button coordinates
		{	
			if (iAmMoving == false)
			{
				iAmPushing = false;
				if ( coords.x <= -168)
				{
					iAmMoving = true;
					direction = Direction.LEFT;
				}
				else if(coords.x > 168 && coords.y > -120)
				{
					iAmMoving = true;
					direction = Direction.RIGHT;
				}
				else if((coords.x > -168 && coords.x<= 168) && coords.y < 0)
				{
					iAmMoving = true;
					direction = Direction.DOWN;
				}
				else if((coords.x > -168 && coords.x<= 168) && coords.y > 0)
				{
					iAmMoving = true;
					direction = Direction.UP;
				}
				else if((coords.x > 240 && coords.y < -120))
				{
					iAmPushing = true;
				}
			}	
		}
	}
	
	
	/**
	 * Detects if the player is going to collide with a map border
	 * or a block in his next move and prevents him from doing so.
	 */
	
	private void collisionDetect(){
		// Check if player will collide with the map border in his next move
		if (direction == Direction.LEFT)
		{
			tmpBounds.setPosition(bounds.x - speed.x, bounds.y);
			if(tmpBounds.x < GameDimensions.xAxisMinumum * GameDimensions.unitWidth)
			{
				iAmMoving = false;
			} 
		}
		else if (direction == Direction.RIGHT)
		{
			tmpBounds.setPosition(bounds.x + speed.x, bounds.y);
			if(tmpBounds.x + tmpBounds.width > GameDimensions.xAxisMaximum*GameDimensions.unitWidth)
			{
				iAmMoving = false;
			}
		}
		else if (direction == Direction.UP)
		{
			tmpBounds.setPosition(bounds.x, bounds.y + speed.y);
			if(tmpBounds.y + tmpBounds.height > GameDimensions.yAxisMaximum * GameDimensions.unitHeight)
			{
				iAmMoving = false;
			}
		}
		else if (direction == Direction.DOWN)
		{
			tmpBounds.setPosition(bounds.x, bounds.y - speed.y);
			if(tmpBounds.y < GameDimensions.yAxisMinimum * GameDimensions.unitHeight)
			{
				iAmMoving = false;
			}
		}
		// Check if the player will collide with a Block in his next move
		if (direction == Direction.UP)
		{
			tmpBounds.setPosition(bounds.x, bounds.y + speed.y);
		}
		else if (direction == Direction.DOWN)
		{
			tmpBounds.setPosition(bounds.x, bounds.y - speed.y);
		}
		else if (direction == Direction.LEFT)
		{
			tmpBounds.setPosition(bounds.x - speed.x, bounds.y);
		}
		else if (direction == Direction.RIGHT)
		{
			tmpBounds.setPosition(bounds.x + speed.x, bounds.y);
		}		
		for (Block i : blockList)
		{
			if (tmpBounds.overlaps(i.getBounds()))
			{
				iAmMoving = false;
			}	
		}
		
		//Check if the player is colliding with an enemy.
		for (Enemy i: enemyList)
		{
			if (bounds.overlaps(i.getBounds()) && i.isDead() == false && iAmDead == false)
			{
				iAmDead = true;
				squashList.add(new SquashStar(bounds.x,bounds.y, StarColor.Yellow,batch,textureLoader));
				break;
			}
		}
		
		//Check if the player is colliding with a moving block.
		for (Block i: blockList)
		{
			if (bounds.overlaps(i.getBounds()) && iAmDead == false)
			{
				iAmDead = true;
				squashList.add(new SquashStar(bounds.x,bounds.y,StarColor.Yellow,batch,textureLoader));
				break;
			}
		}
		
		//Check if the player is colliding with an explosion.
		for (Explosion i: explosionList)
		{
			if (bounds.overlaps(i.getBounds()) && iAmDead == false)
			{
				iAmDead = true;
				squashList.add(new SquashStar(bounds.x,bounds.y,StarColor.Yellow,batch,textureLoader));
				break;
			}
		}
		
	}
	

	
	/**
	 * Finds the correct block and pass a push command to it. 
	 */
	private void push(){
		
		for(Block i : blockList)
		{
			// If I don't have this if, the player will be able to push blocks
			// that are already moving.
			if(i.isMoving() == false)
			{
				if (direction == Direction.UP)
				{
					if (i.getBounds().overlaps(new Rectangle(bounds.x, bounds.y + speed.y, GameDimensions.unitWidth, GameDimensions.unitHeight)))
					{
						i.push(direction);
						break;
					}
				}
				else if (direction == Direction.DOWN)
				{
					if (i.getBounds().overlaps(new Rectangle(bounds.x, bounds.y - speed.y, GameDimensions.unitWidth, GameDimensions.unitHeight)))
					{
						i.push(direction);
						break;
					}
				}
				else if (direction == Direction.LEFT)
				{
					if (i.getBounds().overlaps(new Rectangle(bounds.x - speed.x, bounds.y, GameDimensions.unitWidth, GameDimensions.unitHeight)))
					{
						i.push(direction);
						break;
					}
				}
				else if (direction == Direction.RIGHT)
				{
					if (i.getBounds().overlaps(new Rectangle(bounds.x + speed.x, bounds.y, GameDimensions.unitWidth, GameDimensions.unitHeight)))
					{
						i.push(direction);
						break;
					}
				}
			}	
		}
			
	}
	
	
	/**
	 * Dispose any resources used.
	 */
	public void dispose(){
		
		textureLoader = null;
		atlasleft = null;
		atlasright = null;
		atlasup = null;
		atlasdown = null;
		
		spookyleft = null;
		spookyright = null;
		spookyup = null;
		spookydown = null;
		
		squash = null;
		batch = null;
		camera = null;
		
		bounds = null;
		tmpBounds = null;
		direction = null;
		coords = null;
		speed = null;
		
		blockList = null;
		enemyList = null;
		squashList = null;
		explosionList = null;
		settings = null;
	}
	
	/**
	 * Returns player's bounds
	 * x,y coordinate
	 * width and height
	 */
	public Rectangle getBounds(){
		return bounds;
	}
	
	/**
	 * Returns if the player has issued a push command or not
	 * @return Boolean 
	 */
	public boolean getIsPushing(){
		return iAmPushing;
	}
	
	/**
	 * Returns the player's facing direction
	 * @return Direction 
	 */
	public Direction getDirection(){
		return direction;
	}
	
	
	/**
	 * 
	 * @param blockList an ArrayList<Block>
	 */
	public void setBlockList(ArrayList<Block> blockList)
	{
		this.blockList = blockList;
	}
	
	
	/**
	 * 
	 * @param enemyList an ArrayList<Block>
	 */
	public void setEnemyList(ArrayList<Enemy> enemyList)
	{
		this.enemyList = enemyList;
	}
	
	/**
	 * 
	 * @param explosionList an ArrayList<Block>
	 */
	public void setExplosionList(ArrayList<Explosion> explosionList)
	{
		this.explosionList = explosionList;
	}
	
	/**
	 * 
	 * @return boolean: true if the player has been killed, false otherwise.
	 */
	public boolean isDead(){
		if (iAmDead == true && TimeUtils.timeSinceMillis(deathtimer) > 1500)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	/**
	 * 
	 * @param squashList an ArrayList<Block>
	 */
	public void setSquashList(ArrayList<SquashStar> squashList)
	{
		this.squashList = squashList;
	}
	
	
	/**
	 * Every unit needs access to the game's textureLoader in order to
	 * have access to the textures it requires.
	 * 
	 * @param textureLoader The level's TextureLoader.
	 */
	public void setTextureLoader(TextureLoader textureLoader)
	{
		this.textureLoader = textureLoader;
	}
	
	public void setDead(boolean isDead){
		iAmDead = isDead;
	}
}