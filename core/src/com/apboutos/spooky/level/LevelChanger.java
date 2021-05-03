package com.apboutos.spooky.level;

import com.apboutos.spooky.boot.Spooky;
import com.badlogic.gdx.Screen;


/**
 * This class is used as a temporary screen to help the Level class reinitialize itself
 * after the player completes a level.
 * 
 * @author exophrenik
 *
 */
public class LevelChanger implements Screen{

	private final Spooky spooky;
	private int level = 1;
	
	
	public LevelChanger(Spooky spooky){
		this.spooky = spooky;
	}
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		
		if (spooky.level.goToNextLevel)
		{
			level++;
			if (level > 1)
			{
				level = 1;
			}
		}
		spooky.level.setLevelID(level);
		spooky.setScreen(spooky.level);
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
