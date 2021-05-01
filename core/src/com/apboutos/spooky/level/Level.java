package com.apboutos.spooky.level;

import com.apboutos.spooky.boot.Spooky;
import com.apboutos.spooky.effects.SquashStar;
import com.apboutos.spooky.units.block.Block;
import com.apboutos.spooky.units.enemy.Enemy;
import com.apboutos.spooky.utilities.PlayerInfo;
import com.apboutos.spooky.effects.Explosion;
import com.apboutos.spooky.units.Player;
import com.apboutos.spooky.units.Unit;
import com.apboutos.spooky.units.block.Standard;
import com.apboutos.spooky.units.enemy.Fish;
import com.apboutos.spooky.utilities.BlockType;
import com.apboutos.spooky.utilities.EnemyType;
import com.apboutos.spooky.utilities.LevelInitializer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;


public class Level implements Screen {

	private final Spooky spooky; // The main class, passed in to enable screen swapping from inside the level.
	private Player player; // The player.
	private final List<Unit> units;
	private final ArrayList<Block> blockList; // The ArrayList where every alive Block in the level is stored.
	private final ArrayList<Enemy> enemyList; // The ArrayList where every alive enemy in the level is stored.
	private final ArrayList<SquashStar> squashList; // The ArrayList where every alive squash star is stored.
	private final ArrayList<Explosion> explosionList; // The ArrayList where every explosion animation is stored.
	private final Interface gameHud; // The user's interface
	
	private Block tmpBlock; // A temporary Block object, used for removing Blocks from the blockList.
	private Enemy tmpEnemy; // A temporary Enemy object, used for removing Enemies from the enemyList.
	private SquashStar tmpStar; // A temporary SquashStar object, used for removing SquashStars from the squashList.
	private Explosion tmpExplosion;
	private float deltaTime = 0; // The time passed between frames. It is used for animations.
	private boolean gearIsPressed = false; // Whether the settings gear inside the level's interface is pressed.
	private final Vector3 touchCoords; // The coordinates where the player touched the screen.
	public boolean goToNextLevel = false; // Flag that signals the level change.
	private int level=1; //The level's number i.e. 1 for the first level, 2 for the second and so on.
	private final PlayerInfo playerInfo; //The player's information like lives, score etc.
	private Sprite background;
	
	public Level(Spooky spooky){		
		this.spooky = spooky;

		blockList = new ArrayList<>();
		enemyList = new ArrayList<>();
		squashList = new ArrayList<>();
		explosionList = new ArrayList<>();
		units = LevelInitializer.initializeUnits(level,spooky);

		for (Unit unit : units){
			if (unit instanceof Block){
				blockList.add((Block)unit);
			}
			else if (unit instanceof Enemy){
				enemyList.add((Enemy) unit);
			}
		}
		tmpBlock = new Standard(0, 0, null,BlockType.Standard);
		tmpEnemy = new Fish(0, 0, null,EnemyType.Fish);
		tmpStar = new SquashStar(0,0,null,null);
		tmpExplosion = new Explosion(0,0,spooky.batch);
		gameHud = new Interface(spooky.batch);
		touchCoords = new Vector3();
		playerInfo = new PlayerInfo();
	}
	
	@Override
	public void show() {
		
		//Initialize all the units of the level.

		
		background = new Sprite(TextureLoader.bubblesBackground);
		background.setBounds(-540, -360, 1080, 720);

		player = new Player(-7, -5, spooky.batch,spooky.camera,spooky.settingsScreen.getSettings());
		player.setBlockList(blockList);
		player.setEnemyList(enemyList);
		player.setSquashList(squashList);
		player.setExplosionList(explosionList);
		units.add(player);
		goToNextLevel = false;
		
		for (Block i : blockList)
		{
			i.setBlockList(blockList);
			i.setExplosionList(explosionList);
		}
		for (Enemy i : enemyList)
		{
			i.setBlockList(blockList);
			i.setSquashList(squashList);
			i.setExplosionList(explosionList);
		}
		
		if (level == 1)
		{
			playerInfo.setLives(3);
			playerInfo.setScore(0);
			playerInfo.resetExtra();
		}
		playerInfo.setTimeLeft(4000);
		
	}

	
	@Override
	public void render(float delta) {
		
		
		deltaTime += delta; // Counting time passed between frames.
				
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		touchCoords.x = 0;
		touchCoords.y = 0;
		if (Gdx.input.isTouched())
		{
			touchCoords.x = Gdx.input.getX();
			touchCoords.y = Gdx.input.getY();
			spooky.camera.unproject(touchCoords);
			System.out.println("x = "+ touchCoords.x);
			System.out.println("y = "+ touchCoords.y);
		}
		if (touchCoords.x > 320 && touchCoords.y < -200)//TODO coord remake
		{
			gearIsPressed = true;
		}
		spooky.batch.setProjectionMatrix(spooky.camera.combined);
		spooky.batch.begin();
		
		if (gearIsPressed)
		{
			gearIsPressed = spooky.settingsScreen.update();
		}
		else
		{
			
			background.draw(spooky.batch);
			gameHud.update(); // Updates the game's hud
			player.update(deltaTime); // Updates the player
			
			for (Block i : blockList) // Updates all the blocks
			{
				
				i.update();
				if (i.getDeadBlock() != null)
				{
					tmpBlock = i;
				}
			}
			
			if (tmpBlock.getDeadBlock() != null)
			{
				blockList.remove(tmpBlock);
				tmpBlock.dispose();
			}
			
			for (Enemy i : enemyList) // Updates all the enemies
			{
				i.update(deltaTime);
				if (i.getDeadEnemy() != null)
				{
					tmpEnemy = i;
				}
			}
			if (tmpEnemy.getDeadEnemy() != null)
			{
				enemyList.remove(tmpEnemy);
				tmpEnemy.dispose();	
			}
			
			for (Explosion i: explosionList)//Updates all the explosions.
			{
				i.update();
				if(i.getDeadExplosion() != null)
				{
					tmpExplosion = i;
				}
			}
			if (tmpExplosion.getDeadExplosion() != null)
			{
				explosionList.remove(tmpExplosion);
				tmpExplosion.dispose();	
			}
			
			for (SquashStar i: squashList)// Updates all the squash stars.
			{
				i.update(delta);
				if (i.getDeadSquashStar() != null)
				{
					tmpStar = i;	
				}
			}
			if (tmpStar.getDeadSquashStar() != null)
			{
				squashList.remove(tmpStar);
				tmpStar.dispose();
			}
			
			
		}			
		spooky.batch.end();
		
		if (player.isDead())
		{
			System.out.println("player dead");
			player.dispose();
			spooky.setScreen(spooky.mainmenu);
		}
		
		if (enemyList.size() == 0 && !player.isDead())
		{
			System.out.println("go to next level");
			goToNextLevel = true;
			spooky.setScreen(spooky.levelChanger);
		}
	}
	
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void hide() {
		
		enemyList.clear();
		blockList.clear();
		squashList.clear();
		explosionList.clear();
		player.dispose();
		//gamehud.dispose();
		tmpBlock.dispose();
		
		for (Block i : blockList)
		{
			i.dispose();
		}
		for (Enemy i : enemyList)
		{
			i.dispose();
		}
	}

	
	@Override
	public void dispose() {
		
	}

    
    public void setLevel(int level) {
		this.level = level;
	}
    
    
    
    
}//Class end.
