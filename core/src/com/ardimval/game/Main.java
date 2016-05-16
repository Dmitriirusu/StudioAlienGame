package com.ardimval.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends ApplicationAdapter {

	public static final int Width = 960;
	public static final int Heigth = 540;
	float dt;
	float step = 1/60f;

	@Override
	public void render () {

		dt+= Gdx.graphics.getDeltaTime();

		if(dt >= step) {
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			B2DValues.gsm.update();
			B2DValues.gsm.draw();
			dt = Gdx.graphics.getDeltaTime();
		}

	}
}
