package com.apboutos.spooky.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.apboutos.spooky.level.TextureLoader;

public class Explosion {

	private Rectangle bounds;
	private Animation<TextureRegion> explosion;
	private TextureAtlas explosionAtlas;
	private SpriteBatch batch;
	private long deathTimer = 1000;
	private boolean deathTimerStarted = false;
	private float delta = 0;
	private boolean iAmDead = false;
	
	
	public Explosion(float x, float y, SpriteBatch batch, TextureLoader textureLoader){

		System.out.println( "x = " + x + " y = " + y);
		this.batch = batch;
		bounds = new Rectangle();
		explosionAtlas = textureLoader.getExplosion();
		explosion = new Animation(1/10f,explosionAtlas.getRegions(),PlayMode.LOOP);
		
		bounds.set(x , y, 40 , 40);

		deathTimer = 0;
	}
	
	
	public void update(){
		
		System.out.println("Im in update");
		delta += Gdx.graphics.getDeltaTime();
		batch.draw(explosion.getKeyFrame(delta),bounds.x,bounds.y);
		
		if(deathTimerStarted == false)
		{
			deathTimer = TimeUtils.millis(); //Start the death timer
			deathTimerStarted = true;
		}
		
				
		if(iAmDead == false)
		{
			if(TimeUtils.timeSinceMillis(deathTimer) > explosion.getAnimationDuration()*1000)
			{
				iAmDead = true;
			}
		}
	}
	
	public Explosion getDeadExplosion(){
		
		if(iAmDead == true)
		{
			return this;
		}
		else
		{
			return null;
		}
	}	

	public void dispose(){
		
		bounds = null;
		explosion = null;
		explosionAtlas = null;
		batch = null;
	}
	
	public Rectangle getBounds(){
		return bounds;
	}

}//Class ends here.