package com.ardimval.game;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.ardimval.game.B2DValues.PPM;

public abstract class GameState {
    OrthographicCamera camera;
    OrthographicCamera b2dCamera;
    MyContactListener cl;
    SpriteBatch batch;


    public GameState(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Main.Width,Main.Heigth);
        b2dCamera = new OrthographicCamera();
        b2dCamera.setToOrtho(false,Main.Width/PPM,Main.Heigth/PPM);
        cl = new MyContactListener();
        batch = new SpriteBatch();
    }

    public void update(){

    }

    public void draw(){

    }

}
