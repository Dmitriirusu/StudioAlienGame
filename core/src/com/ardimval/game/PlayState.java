package com.ardimval.game;

import static com.ardimval.game.B2DValues.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PlayState extends GameState {

    TiledMapRenderer mapRenderer;
    Box2DDebugRenderer b2dRenderer;
    float time;
    TextureRegion gameOver;

    ArrayList<Body> b,c;

    Arrows arrows;
    Player player;
    Coins coins;
    Enemy enemies;
    Key key;
    boolean update,lost;

    public PlayState(){
        world = new World(new Vector2(0,-9.81f),true);
        world.setContactListener(cl);

        tmap = new  TmxMapLoader().load("res/maps/level" + selectedLevel + ".tmx");
        update =true;

        mapRenderer = new OrthogonalTiledMapRenderer(tmap);
        b2dRenderer = new Box2DDebugRenderer();
        gameOver  = new TextureRegion(new Texture(Gdx.files.internal("res/PNG/font/gameOver2.png")));

        new Ground();
        key = new Key();

        coins = new Coins();
        arrows = new Arrows();
        player = new Player(this);
        enemies = new Enemy();

        float width = tmap.getProperties().get("width",Integer.class)*70/PPM;
        float height = tmap.getProperties().get("height",Integer.class)*70/PPM;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        ChainShape shape = new ChainShape();
        Vector2[] vector = new Vector2[4];
        vector[0] = new Vector2(0,0);
        vector[1] = new Vector2(0,height);
        vector[2] = new Vector2(width,height);
        vector[3] = new Vector2(width,0);
        shape.createChain(vector);

        bdef.type = BodyDef.BodyType.StaticBody;
        fdef.shape = shape;
        fdef.filter.categoryBits = CB_BORDER;
        world.createBody(bdef).createFixture(fdef).setUserData("border");

        shape.dispose();
    }

    public void handleInput(){
        player.handleInput();
    }

    public void checkCamera(OrthographicCamera cam){
        int mapLeft = 0;
        int mapRight = tmap.getProperties().get("width",Integer.class)*70;
        int mapBottom = 0;
        int mapUp = tmap.getProperties().get("height",Integer.class)*70;
        if(cam.position.x <= mapLeft + cam.viewportWidth/2){
            cam.position.x = mapLeft + cam.viewportWidth/2;

        }
        else if(cam.position.x >= mapRight - cam.viewportWidth/2){
            cam.position.x = mapRight - cam.viewportWidth/2;

        }
        if(cam.position.y <= mapBottom + cam.viewportHeight/2){
            cam.position.y = mapBottom + cam.viewportHeight/2;
        }
        if(cam.position.y >= mapUp - cam.viewportHeight/2){
            cam.position.y = mapUp - cam.viewportHeight/2;
        }
    }

    public void deleteCoins(){
        if(!world.isLocked() ) {
            b = cl.delete;
            for (Body aB : b) {
                if (coins.coins.remove(aB))
                    world.destroyBody(aB);
            }

            c = cl.enemyDelete;
            for (Body aB : c) {
                if (enemies.enemies.remove(aB))
                    world.destroyBody(aB);
            }
            b.clear();
        }
    }

    public void gameOver(){
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        batch.draw(gameOver, camera.position.x - gameOver.getRegionWidth() / 2, camera.position.y);
        batch.end();


        if(!lost) {
            lost = true;
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    B2DValues.currentState =2 ;
                    gsm.setState(2);
                    lost = false;
                }
            });
        }
    }

    @Override
    public void update() {

            handleInput();
        if(update)
            world.step(1/60f,6,2);
        deleteCoins();

    }

    public int getAllCoins(){
       return coins.coins.size();
    }

    @Override
    public void draw() {

        batch.setProjectionMatrix(camera.combined);
        camera.position.set(player.player.getPosition().x * PPM + Main.Width / 4, player.player.getPosition().y*PPM, 0);
        checkCamera(camera);
        camera.update();


        mapRenderer.setView(camera);
        mapRenderer.render();
        batch.setProjectionMatrix(b2dCamera.combined);
        b2dCamera.position.set(player.player.getPosition().x + Main.Width/4/PPM,Main.Heigth/2/PPM ,0);
        b2dCamera.update();
//        b2dRenderer.render(world,b2dCamera.combined);

        time+=Gdx.graphics.getDeltaTime();

        batch.setProjectionMatrix(camera.combined);


        //draw key
        key.draw(batch);

        if(player.player.getPosition().y > Main.Heigth)
            gameOver();
        if(player.player.getPosition().y < 0)
            gameOver();
        if(Player.lifes < 1)
            gameOver();
        enemies.draw(batch,time);
        //draw all coins
        coins.draw(batch,time);

        //draw player
        player.draw(batch,time,camera);
        //draw arrows
        arrows.draw();
        if(Player.lifes < 1) {
            gameOver();
            update = false;
        }
    }
}
