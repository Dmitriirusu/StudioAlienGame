package com.ardimval.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static com.ardimval.game.B2DValues.*;
import static com.ardimval.game.B2DValues.world;
import static com.badlogic.gdx.scenes.scene2d.ui.ImageButton.*;


public class Menu extends GameState {

    Box2DDebugRenderer b2dRenderer;
    TiledMapRenderer tmapRenderer;
    Player player;
    Texture texture,play;
    float time;
    boolean border;
    int width,playWidth;
    Stage stage;
    ImageButton playButton;

    public Menu(){
        world = new World(new Vector2(0,-9.81f),true);
        world.setContactListener(cl);

        tmap = new TmxMapLoader().load("res/maps/main.tmx");
        b2dRenderer = new Box2DDebugRenderer();
        tmapRenderer = new OrthogonalTiledMapRenderer(tmap);

        texture = new Texture(Gdx.files.internal("res/PNG/font/font.png"));
        play = new Texture(Gdx.files.internal("res/PNG/font/newPlay2.png"));
        play.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        stage = new Stage();
        Skin skin = new Skin();
        skin.add("play",play,Texture.class);
        ImageButtonStyle style = new ImageButtonStyle();
        style.up = skin.getDrawable("play");
        style.down = skin.getDrawable("play");
        playButton = new ImageButton(style);
        playButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                B2DValues.currentState = 2;
                B2DValues.gsm.setState(2);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        stage.addActor(playButton);
        width = texture.getWidth();
        playWidth = play.getWidth();
        Gdx.input.setInputProcessor(stage);
        new Ground();
        player = new Player(null);
    }


    @Override
    public void update() {
        handleInput();
        world.step(1 / 60f, 6, 2);

    }

    private void handleInput() {

        if(onGround > 0) {

            float x = player.player.getPosition().x*PPM;

            if (x<1100 && x> 1090 ){
                border = true;
            }

            if (x <220 && x > 210 ){
                border = false;
            }
            if (!border) {
                player.player.setLinearVelocity(200/PPM,0);
                right = true;
                left = false;
                previous = false;
            }

            else {
                player.player.setLinearVelocity(-200/PPM,0);
                left = true;
                right = false;
                previous = true;
            }
        }
    }

    public void checkCamera(OrthographicCamera cam){
        int mapLeft =0;
        int mapRight =tmap.getProperties().get("width",Integer.class)*70;
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
    @Override
    public void draw() {
        tmapRenderer.setView(camera);
        tmapRenderer.render();
        time += Gdx.graphics.getDeltaTime();
        player.draw(batch,time,camera);
        batch.setProjectionMatrix(camera.combined);
        camera.position.set(player.player.getPosition().x * PPM + Main.Width / 4, Main.Heigth / 2, 0);
        checkCamera(camera);
        camera.update();
        batch.begin();
        batch.draw(texture,camera.position.x-width/2,camera.position.y*3/2);
        batch.end();
        playButton.setPosition(Gdx.graphics.getWidth()/2-playWidth/2,Gdx.graphics.getHeight()/2 - play.getHeight()/3);
        stage.act();
        stage.draw();
    }
}
