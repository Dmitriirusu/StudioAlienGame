package com.ardimval.game;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.audio.Music;

public class GameStateManager {

    public static final int PLAY = 0;
    public static  final int MENU = 1;
    public static  final  int LEVEL_SELECT =2;

    GameState state;
    Music backgroundMusic;

    public GameStateManager(){
        setState(MENU);
/*
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("res/sounds/background.ogg"));
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                backgroundMusic.setVolume(0.5f);
                backgroundMusic.play();
                backgroundMusic.setLooping(true);
            }
        });
*/
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("res/sounds/background.ogg"));
                backgroundMusic.setVolume(0.5f);
                backgroundMusic.play();
                backgroundMusic.setLooping(true);
            }
        });
        thread.start();
    }


    public void setState(int state){
        if(state == PLAY)
            this.state = new PlayState();
        if(state == MENU)
            this.state = new Menu();
        if(state == LEVEL_SELECT)
            this.state = new LevelSelectState();
    }

    public void update(){
        state.update();
    }

    public void draw(){
        state.draw();
    }
}
