package com.apboutos.spooky.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;


/**
 * 
 * @author exophrenik
 *
 */

public class SettingsScreen {

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Sprite settingsScreen;
	//private Sprite settingsGear;
	private Sprite buttonsNormal;
	private Sprite buttonsPressed;
	private Sprite keysNormal;
	private Sprite keysPressed;
	private Sprite onNormal;
	private Sprite onPressed;
	private Sprite offNormal;
	private Sprite offPressed;
	
	private Settings settings;
	private Vector3 touchCoords;
	
	
	
	public SettingsScreen(SpriteBatch batch,OrthographicCamera camera)
	{
		this.batch = batch;
		this.camera = camera;
		touchCoords = new Vector3();
		settingsScreen = new Sprite(new Texture(Gdx.files.internal("Images/Screens/Settings/SettingsScreen.png")));
		//settingsGear = new Sprite(new Texture(Gdx.files.internal("Images/Screens/Settings/SettingsGear.png")));
		keysNormal = new Sprite(new Texture(Gdx.files.internal("Images/Screens/Settings/KeysNormal.png")));
		keysPressed = new Sprite(new Texture(Gdx.files.internal("Images/Screens/Settings/KeysPressed.png")));
		buttonsNormal = new Sprite(new Texture(Gdx.files.internal("Images/Screens/Settings/ButtonsNormal.png")));
		buttonsPressed = new Sprite(new Texture(Gdx.files.internal("Images/Screens/Settings/ButtonsPressed.png")));
        onNormal = new Sprite(new Texture(Gdx.files.internal("Images/Screens/Settings/OnNormal.png")));
        onPressed = new Sprite(new Texture(Gdx.files.internal("Images/Screens/Settings/OnPressed.png")));
        offNormal = new Sprite(new Texture(Gdx.files.internal("Images/Screens/Settings/OffNormal.png")));
        offPressed = new Sprite(new Texture(Gdx.files.internal("Images/Screens/Settings/OffPressed.png")));
		settingsScreen.setBounds(-280,-200,560,400);
		//settingsGear.setBounds(240,160,40,40);
		keysNormal.setPosition(46, 100);
		keysPressed.setPosition(46, 100);
		buttonsNormal.setPosition(136, 100);
		buttonsPressed.setPosition(136, 100);
		onNormal.setPosition(46, -10);
		onPressed.setPosition(46, -10);
		offNormal.setPosition(136, -10);
		offPressed.setPosition(136, -10);
		
		settings = new Settings();
		settings.controls = 0;
		settings.music = true;
		settings.sounds = true;
	}
	
	
	
	
	public boolean update()
	{
		if (Gdx.input.isTouched() == true)
		{
			touchCoords.x = Gdx.input.getX();
			touchCoords.y = Gdx.input.getY();
			camera.unproject(touchCoords);
		}	
		draw();		
		if(touchCoords.x > 240 && touchCoords.y < -160)
		{
			return false;
		}
		else if ((touchCoords.x > 46 && touchCoords.x < 126) &&
		    	 (touchCoords.y > 100 && touchCoords.y < 180)&&
	        	 (settings.controls != 0))
		{
			settings.controls = 0;
			return true;
		}
		else if ((touchCoords.x > 136 && touchCoords.x < 216) &&
	   	        (touchCoords.y > 100 && touchCoords.y < 180)&&
		        (settings.controls != 1))
    	{
			settings.controls = 1;
			return true;
		}
		else if ((touchCoords.x > 46 && touchCoords.x < 126) &&
       		     (touchCoords.y > -10 && touchCoords.y < 30)&&
			     (settings.music == false))
		{
			settings.music = true;
			return true;
		}
		else if ((touchCoords.x > 136 && touchCoords.x < 216) &&
			     (touchCoords.y > -10 && touchCoords.y < 30)&&
			     (settings.music == true))
		{
			settings.music = false;
			return true;
		}
		else if ((touchCoords.x > 46 && touchCoords.x < 126) &&
      		     (touchCoords.y > -95 && touchCoords.y < -55)&&
			     (settings.sounds == false))
		{
			settings.sounds = true;
			return true;
		}
		else if ((touchCoords.x > 136 && touchCoords.x < 216) &&
			     (touchCoords.y > -95 && touchCoords.y < -55)&&
			     (settings.sounds == true))
		{
			settings.sounds = false;
			return true;
		}
		else
		{
			return true;
		}
	}
	
	
	private void draw()
	{
		settingsScreen.draw(batch);
		//settingsGear.draw(batch);
		if (settings.controls == 0)
		{
			keysPressed.draw(batch);
			buttonsNormal.draw(batch);
		}
		else
		{
			keysNormal.draw(batch);
			buttonsPressed.draw(batch);
		}
		onNormal.setPosition(46, -10);
		onPressed.setPosition(46, -10);
		offNormal.setPosition(136, -10);
		offPressed.setPosition(136, -10);
		if (settings.music == true)
		{
			onPressed.draw(batch);
			offNormal.draw(batch);
		}
		else
		{
			onNormal.draw(batch);
			offPressed.draw(batch);
		}
		onNormal.setPosition(46, -95);
		onPressed.setPosition(46, -95);
		offNormal.setPosition(136, -95);
		offPressed.setPosition(136, -95);
		if (settings.sounds == true)
		{
			onPressed.draw(batch);
			offNormal.draw(batch);
		}
		else
		{
			onNormal.draw(batch);
			offPressed.draw(batch);
		}		
	}
	
	
	
	public void dispose()
	{
		settingsScreen.getTexture().dispose();
		//settingsGear.getTexture().dispose();
		buttonsNormal.getTexture().dispose();
		buttonsPressed.getTexture().dispose();
		keysNormal.getTexture().dispose();
		keysPressed.getTexture().dispose();
		onNormal.getTexture().dispose();
		onPressed.getTexture().dispose();
		offNormal.getTexture().dispose();
		offPressed.getTexture().dispose();
	}
	
	
	public Settings getSettings()
	{
		return settings;
	}
}
