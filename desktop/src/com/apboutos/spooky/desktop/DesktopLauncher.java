package com.apboutos.spooky.desktop;


import com.apboutos.spooky.boot.Spooky;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Spooky 1.0");
		config.setWindowedMode(1280,720);//GameDimensions.screenWidth;GameDimensions.screenHeight;;
		new Lwjgl3Application(new Spooky(), config);
	}
}
