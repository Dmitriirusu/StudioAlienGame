package com.ardimval.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ardimval.game.Main;
import org.lwjgl.opengl.Display;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Main.Width;
		config.height = Main.Heigth;
		config.resizable = false;
		//config.foregroundFPS = 30;
		new LwjglApplication(new Main(), config);
	}
}
