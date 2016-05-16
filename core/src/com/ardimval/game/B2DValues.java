package com.ardimval.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

public class B2DValues {
    public static final float PPM = 100;
    public static final short CB_GROUND = 2;
    public static final short CB_PLAYER = 4;
    public static final short CB_BORDER = 8;
    public static final short CB_ENEMY = 16;
    public static final short CB_PLAYER_SENSOR = 32;

    static boolean left,right,jumpButton,previous,enemyLeft;
    static String selectedLevel;
    static World world;
    static int onGround = 0;
    static TiledMap tmap;
    static TextureAtlas atlas = new TextureAtlas("res/atlas.atlas");
    static GameStateManager gsm = new GameStateManager();
    static int currentState=1;

}
