package com.apboutos.spooky.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Button {

	private Rectangle bounds;
	private Sprite button;
	private SpriteBatch batch;
	
	
	
	
	
	public Button(){
		
	}
	
	public Button(float x, float y, Texture texture, SpriteBatch batch){
		
		bounds = new Rectangle(x,y,texture.getWidth(),texture.getHeight());
		button = new Sprite();
		button.setBounds(x, y, texture.getWidth(), texture.getHeight());
		this.batch = batch;
		
		
	}
	
	public boolean isClicked(float x, float y){
		
		if(x >= bounds.x && x <= bounds.x + bounds.width && 
			y>= bounds.y && y <= bounds.y + bounds.height)
			return true;
		else
			return true;
	}
	
	public void update(){
				
		button.draw(batch);
	}
	
	public void dispose(){
		
		bounds = null;
		button = null;
	}
	
}
