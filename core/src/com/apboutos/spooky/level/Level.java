package com.apboutos.spooky.level;

import com.apboutos.spooky.boot.Spooky;
import com.apboutos.spooky.effects.Explosion;
import com.apboutos.spooky.effects.SquashStar;
import com.apboutos.spooky.units.Enemy;
import com.apboutos.spooky.units.Player;
import com.apboutos.spooky.units.Unit;
import com.apboutos.spooky.utilities.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Level implements Screen {

	private final Spooky spooky; // The main class, passed in to enable screen swapping from inside the level.
	private Player player; // The player.
	private List<Unit> units;

	private final ArrayList<SquashStar> squashList; // The ArrayList where every alive squash star is stored.
	private final ArrayList<Explosion> explosionList; // The ArrayList where every explosion animation is stored.
	private final Interface gameHud; // The user's interface

	private SquashStar tmpStar; // A temporary SquashStar object, used for removing SquashStars from the squashList.
	private Explosion tmpExplosion;
	private float deltaTime = 0; // The time passed between frames. It is used for animations.
	private Boolean gearIsPressed = false; // Whether the settings gear inside the level's interface is pressed.
	public boolean goToNextLevel = false; // Flag that signals the level change.
	private int levelID =1; //The level's number i.e. 1 for the first level, 2 for the second and so on.
	private final PlayerInfo playerInfo; //The player's information like lives, score etc.
	private Sprite background;

	private long enemiesAliveInLevel;

	private InputHandler inputHandler;
	private CollisionDetector collisionDetector;
	private PositionAdjustor positionAdjustor;
	private Painter painter;
	private BehaviorController behaviorController;
	
	public Level(Spooky spooky){		
		this.spooky = spooky;

		squashList = new ArrayList<>();
		explosionList = new ArrayList<>();

		tmpStar = new SquashStar(0,0,null,null);
		tmpExplosion = new Explosion(0,0,spooky.batch);
		gameHud = new Interface(spooky.batch);

		playerInfo = new PlayerInfo();
	}
	
	@Override
	public void show() {
		
		//Initialize all the units of the level.
		units = LevelInitializer.initializeUnits(levelID);
		enemiesAliveInLevel = units.stream().filter(unit -> unit instanceof Enemy).count();

		collisionDetector = new CollisionDetector(units,explosionList);
		
		background = new Sprite(TextureLoader.bubblesBackground);
		background.setBounds(-540, -360, 1080, 720);

		player = new Player(-7, -5);
		units.add(player);

		inputHandler = new InputHandler(player, spooky.camera, gearIsPressed,spooky.settingsScreen.getSettings(),units,collisionDetector);
		positionAdjustor = new PositionAdjustor();
		painter = new Painter(spooky.batch, spooky.camera);
		behaviorController = new BehaviorController(squashList, spooky.batch, collisionDetector);
		goToNextLevel = false;

		if (levelID == 1)
		{
			playerInfo.setLives(3);
			playerInfo.setScore(0);
			playerInfo.resetExtra();
		}
		playerInfo.setTimeLeft(4000);
		System.out.println("Unit list has " + units.size() + " units");
	}

	
	@Override
	public void render(float delta) {
		//System.out.println("Unit list has " + units.size() + " units");
		deltaTime += delta; // Counting time passed between frames.
				
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		painter.beginDrawing();

		inputHandler.handleInput();

		if (gearIsPressed)
		{
			gearIsPressed = spooky.settingsScreen.update();
		}
		else
		{
			gameHud.update(); // Updates the game's hud

			painter.drawBackground(background);

			units.forEach(behaviorController::determineUnitBehavior);

			units.forEach(positionAdjustor::adjustPosition);

			units.forEach(unit-> painter.draw(unit,deltaTime));

			units = units.stream().filter(unit -> !unit.isDead()).collect(Collectors.toList());

			enemiesAliveInLevel = units.stream().filter(unit -> unit instanceof Enemy).count();

			updateUnitListReference(units);



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
		painter.endDrawing();
		
		if (player.isDead())
		{
			System.out.println("player dead");
			spooky.setScreen(spooky.mainmenu);
		}
		
		if (enemiesAliveInLevel == 0 && !player.isDead())
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

		squashList.clear();
		explosionList.clear();
		//gamehud.dispose();

	}

	
	@Override
	public void dispose() {
		
	}

    
    public void setLevelID(int levelID) {
		this.levelID = levelID;
	}

	/**
	 *  Java uses call by value on objects therefore when you pass a list to an object
	 *  and afterwards you assign a new list to that variable, the object still has the old list.
	 *  Since I'm using streams to clear the dead units from the unit list this method must be
	 *  called so all the objects that depend on the list have the renewed list reference.
	 */
	private void updateUnitListReference(List<Unit> units){
		inputHandler.setUnits(units);
		collisionDetector.setUnits(units);
	}
    
    
}//Class end.
