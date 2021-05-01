package com.apboutos.spooky.boot;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.apboutos.spooky.level.Level;
import com.apboutos.spooky.level.LevelChanger;
import com.apboutos.spooky.level.SettingsScreen;
import com.apboutos.spooky.screen.MainMenu;
import com.apboutos.spooky.screen.Splash;
import com.apboutos.spooky.utilities.GameDimensions;

public class Spooky extends Game {
	public Splash splash; // The splash screen
	public MainMenu mainmenu; // The MainMenu screen
	public Level level; // The level screen
	public SpriteBatch batch; // The standard renderer
	public OrthographicCamera camera; // The standard camera
	public SettingsScreen settingsScreen; // The settings screen
	public LevelChanger levelChanger; // The screen responsible for moving the player from one level to another.


	@Override
	public void create() {

		batch = new SpriteBatch();
		camera = new OrthographicCamera(GameDimensions.screenWidth,GameDimensions.screenHeight);
		splash = new Splash(this);
		mainmenu = new MainMenu(this);
		settingsScreen = new SettingsScreen(batch,camera);
		levelChanger = new LevelChanger(this);
		level = new Level(this);
		setScreen(mainmenu);

	}

	@Override
	public void render () {
		super.render();
	}
	@Override
	public void dispose(){
		super.dispose();
	}
}
