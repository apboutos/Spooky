package com.apboutos.spooky.level;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.apboutos.spooky.boot.Spooky;
import com.apboutos.spooky.effects.Explosion;
import com.apboutos.spooky.effects.SquashStar;
import com.apboutos.spooky.units.Player;
import com.apboutos.spooky.units.block.Block;
import com.apboutos.spooky.units.block.Bouncing;
import com.apboutos.spooky.units.block.Diamond;
import com.apboutos.spooky.units.block.Dynamite;
import com.apboutos.spooky.units.block.Standard;
import com.apboutos.spooky.units.enemy.Enemy;
import com.apboutos.spooky.units.enemy.Fish;
import com.apboutos.spooky.units.enemy.Shark;
import com.apboutos.spooky.utilities.BlockType;
import com.apboutos.spooky.utilities.EnemyType;
import com.apboutos.spooky.utilities.PlayerInfo;




public class Level implements Screen {

	private Spooky spooky; // The main class, passed in to enable screen swapping from inside the level.
	private Player player; // The player.
	private ArrayList<Block> blockList; // The ArrayList where every alive Block in the level is stored.
	private ArrayList<Enemy> enemyList; // The ArrayList where every alive enemy in the level is stored.
	private ArrayList<SquashStar> squashList; // The ArrayList where every alive squash star is stored.
	private ArrayList<Explosion> explosionList; // The ArrayList where every explosion animation is stored.
	private Interface gamehud; // The user's interface 
	
	private Block tmpBlock; // A temporary Block object, used for removing Blocks from the blockList.
	private Enemy tmpEnemy; // A temporary Enemy object, used for removing Enemies from the enemyList.
	private SquashStar tmpStar; // A temporary SquashStar object, used for removing SquashStars from the squashList.
	private Explosion tmpExplosion;
	private float deltaTime = 0; // The time passed between frames. It is used for animations.
	private boolean gearIsPressed = false; // Whether the settings gear inside the level's interface is pressed.
	private Vector3 touchCoords; // The coordinates where the player touched the screen.
	public boolean goToNextLevel = false; // Flag that signals the level change.
	private int level=1; //The level's number i.e. 1 for the first level, 2 for the second and so on.
	private PlayerInfo playerInfo; //The player's information like lives, score etc.
	private TextureLoader textureLoader;
	private Sprite background;
	
	public Level(Spooky spooky){		
		this.spooky = spooky;
		
		textureLoader = new TextureLoader();
		blockList = new ArrayList<Block>();
		enemyList = new ArrayList<Enemy>();
		squashList = new ArrayList<SquashStar>();
		explosionList = new ArrayList<Explosion>();
		tmpBlock = new Standard(0, 0, null,BlockType.Standard,textureLoader);
		tmpEnemy = new Fish(0, 0, null,EnemyType.Fish,textureLoader);
		tmpStar = new SquashStar(0,0,null,null,null);
		tmpExplosion = new Explosion(0,0,spooky.batch,textureLoader);
		gamehud = new Interface(spooky.batch);
		touchCoords = new Vector3();
		playerInfo = new PlayerInfo();
	}
	
	@Override
	public void show() {
		
		//Initialize all the units of the level.
		this.initialize();
		
		background = new Sprite(textureLoader.getBubblesBackground());
		background.setBounds(-540, -360, 1080, 720);
		player = new Player(-7, -5, spooky.batch,spooky.camera,spooky.settingsScreen.getSettings(),textureLoader);		
		player.setBlockList(blockList);
		player.setEnemyList(enemyList);
		player.setSquashList(squashList);
		player.setTextureLoader(textureLoader);
		player.setExplosionList(explosionList);
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
		if (Gdx.input.isTouched() == true)
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
		
		if (gearIsPressed == true)
		{
			gearIsPressed = spooky.settingsScreen.update();
		}
		else if(gearIsPressed == false)
		{
			
			background.draw(spooky.batch);
			gamehud.update(); // Updates the game's hud
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
		
		if (enemyList.size() == 0 && player.isDead() == false)
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
		// TODO Auto-generated method stub
		
	}

	String s = "bee";
	

	/**
     * Reads the appropriate Level Constructor file and initializes the level by creating the blocks, enemies etc.
     */
    private void initialize()
    {
        FileHandle blockFile; // Handler for the blocks file
        String tmp=""; // Temporary string used to assist in file reading
        BufferedReader fileReader; // This object will read the files line by line
        int x=0,y=0; // Temporary x,y coordinates for each unit
        int numberOfBlocks; // The number of blocks described in the file
        int numberOfEnemies; // The number of enemies described in the file
        EnemyType enemyType = EnemyType.Shark;
        BlockType blockType = BlockType.Standard;
        blockFile = Gdx.files.internal("Levels/Level " + String.valueOf(level) + " Constructor.txt");
     	fileReader = blockFile.reader(2000);    
     	
    	try
	    {
	    	tmp = fileReader.readLine();
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    }
    	/*
    	 * Stores the first integer of the file.
    	 * This integer represents the number of blocks
    	 * described in the file.
    	 */
        numberOfBlocks = Integer.parseInt(tmp);
		
        
        /*
         * Reads the x and y coordinate of each block, creates a new
         * Block object with those coordinates and adds it to the
         * blockList.
         */
        for (int i=0;i<numberOfBlocks;i++)
        {
        	try
        	{
        		tmp = fileReader.readLine();
        	    x = Integer.parseInt(tmp);
        		tmp = fileReader.readLine();
        		y = Integer.parseInt(tmp);
        		tmp = fileReader.readLine();
        		if (tmp.matches("Standard"))
        		{
        			blockType = BlockType.Standard;
        			blockList.add(new Standard(x,y,spooky.batch,blockType,textureLoader));
        		}
        		else if (tmp.matches("Bouncing"))
        		{
        			blockType = BlockType.Bouncing;
        			blockList.add(new Bouncing(x,y,spooky.batch,blockType,textureLoader));
        		}
        		else if (tmp.matches("BigBouncing"))
        		{
        			blockType = BlockType.BigBouncing;
        			blockList.add(new Bouncing(x,y,spooky.batch,blockType,textureLoader));
        		}
        		else if (tmp.matches("Dynamite"))
        		{
        			blockType = BlockType.Dynamite;
        			blockList.add(new Dynamite(x,y,spooky.batch,blockType,textureLoader));
        		}
        		else if (tmp.matches("BigDynamite"))
        		{
        			blockType = BlockType.BigDynamite;
        			blockList.add(new Dynamite(x,y,spooky.batch,blockType,textureLoader));
        		}
        		else if (tmp.matches("Diamond"))
        		{
        			blockType = BlockType.Diamond;
        			blockList.add(new Diamond(x,y,spooky.batch,blockType,textureLoader));
        		}
        	}
        	catch(Exception e){
    	    	e.printStackTrace();
    	    }
        	
        }
        
        /*
         * This integer represents the number of enemies described in the file.
         */
        try
	    {
	    	tmp = fileReader.readLine();
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    }
        numberOfEnemies = Integer.parseInt(tmp);
        
        for (int i=0;i<numberOfEnemies;i++)
        {
        	try
        	{
        		tmp = fileReader.readLine();
        	    x = Integer.parseInt(tmp);
        		tmp = fileReader.readLine();
        		y = Integer.parseInt(tmp);
        		tmp = fileReader.readLine();
        		if (tmp.matches("Fish"))
        		{
        			enemyType = EnemyType.Fish;
        			enemyList.add(new Fish(x,y,spooky.batch,enemyType,textureLoader));
        		}
        		else if (tmp.matches("Shark"))
        		{
        			enemyType = EnemyType.Shark;
        			enemyList.add(new Shark(x,y,spooky.batch,enemyType,textureLoader));
        		}
        		else if (tmp.matches("Starfish"))
        		{
        			enemyType = EnemyType.Starfish;
        		}
        		else if (tmp.matches("Watersnake"))
        		{
        			enemyType = EnemyType.Watersnake;
        		}
        		else if (tmp.matches("Clamp"))
        		{
        			enemyType = EnemyType.Clamp;
        		}
        		else if (tmp.matches("Squid"))
        		{
        			enemyType = EnemyType.Squid;
        		}
        		else if (tmp.matches("Octopus"))
        		{
        			enemyType = EnemyType.Octopus;
        		}
        		else if (tmp.matches("Medusa"))
        		{
        			enemyType = EnemyType.Medusa;
        		}
        	}
        	catch(Exception e){
    	    	e.printStackTrace();
    	    }
        }
       
        try 
        {
			fileReader.close();
		} 
        catch (IOException e) {
			e.printStackTrace();
		}
        
        blockFile = null;
        tmp = null;
        fileReader = null;
        enemyType = null;
        blockType = null;
        blockFile = null;
    }

	
    
    public void setLevel(int level) {
		this.level = level;
	}
    
    
    
    
}//Class end.
