package com.apboutos.spooky.level;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.apboutos.spooky.utilities.PlayerInfo;

public class Interface {
	
	private PlayerInfo playerInfo;
	
	
	public Interface(SpriteBatch batch){
		
		
		
	}
	
	
	
	public void update(){
		draw();
	}

	
	private void draw(){
		
		
	}
	
	public void dispose(){
		
		
		
	}
	
	public void setPlayerInfo(PlayerInfo playerInfo)
	{
		this.playerInfo = playerInfo;
	}

}
