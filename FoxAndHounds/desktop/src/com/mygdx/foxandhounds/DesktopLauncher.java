package com.mygdx.foxandhounds;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Fox and Hounds");
		config.setWindowIcon("textures/fox.png");
		config.setWindowedMode(800,800);
		config.useVsync(true);
		config.setResizable(false);
		new Lwjgl3Application(new FoxAndHounds(), config);

	}
}
