package com.ardimval.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;

import java.awt.Font;

import static com.ardimval.game.B2DValues.*;


public class Player {

    TextureRegion[] textureRegionRight,textureRegionLeft;
    BitmapFont font;
    TextureRegion jumpLeft,jumpRight,front;
    Animation animationRight,animationLeft;
    Sound jump;
    Body player;
    Texture lifeTexture,allCoins;
    int heigth,width,totalCoins;
    public static int lifes;
    boolean jumpSound;
    static int coinsCounter;

    public Player(PlayState state){

        left = false;
        right = false;
        jumpButton = false;
        onGround = 0;
        lifes =3;
        if(state != null)
            totalCoins = state.getAllCoins();
        coinsCounter = 0;

        //Load texture
        Texture texture = new Texture(Gdx.files.internal("res/PNG/character/walk/walk3.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        lifeTexture = new Texture(Gdx.files.internal("res/PNG/character/live.png"));
        lifeTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        jump = Gdx.audio.newSound(Gdx.files.internal("res/sounds/jump.wav"));
        allCoins = new Texture(Gdx.files.internal("res/front.png"));
        allCoins.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        font = new BitmapFont();
        font.setColor(Color.YELLOW);

        //Split texture into textureRegions
        textureRegionRight = TextureRegion.split(texture, 83, 104)[0];
        textureRegionLeft = TextureRegion.split(texture, 83, 104)[1];
        front = new TextureRegion(texture, 81, 217, 84, 96);
        jumpRight = new TextureRegion(texture, 0, 212, 87, 96);
        jumpLeft = new TextureRegion(texture, 159, 214, 87, 96);
        width = textureRegionRight[0].getRegionWidth();
        heigth = textureRegionRight[0].getRegionHeight();
        animationRight = new Animation(1 / 24f, textureRegionRight);
        animationLeft = new Animation(1 / 24f, textureRegionLeft);

        //Create Box2D Body for Player
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearVelocity.set(1, 0);
        bdef.position.set(240 / PPM, 300 / PPM);

        player = world.createBody(bdef);

        CircleShape circle = new CircleShape();
        circle.setRadius(34 / PPM);
        circle.setPosition(new Vector2(1 / PPM, 10 / PPM));
        fdef.shape = circle;
        fdef.friction = 1;
        fdef.filter.categoryBits = CB_PLAYER;
        player.createFixture(fdef).setUserData("player");

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(18 / PPM, 23 / PPM, new Vector2(0, -23 / PPM), 0);
        fdef.shape = shape;
        fdef.filter.categoryBits = CB_PLAYER;
        player.createFixture(fdef).setUserData("playerBody");

        shape.setAsBox(15 / PPM, 5 / PPM, new Vector2(0, -49 / PPM), 0);
        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.friction = 1;
        fdef.filter.categoryBits = B2DValues.CB_PLAYER_SENSOR;
        fdef.filter.maskBits = B2DValues.CB_GROUND | CB_ENEMY;
        player.createFixture(fdef).setUserData("sensor");

        shape.dispose();
    }

    public void handleInput() {
        //  left = false;
        //  right = false;
        //  jumpButton = false;

/*
        if(onGround == 0) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || left && onGround == 0) {
                // player.setLinearVelocity(-100/PPM,0);
                player.applyLinearImpulse(-0.1f, 0, 0, 0, true);
                previous = true;
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || right && onGround == 0) {
                // player.setLinearVelocity(100/PPM,0);
                player.applyLinearImpulse(0.1f, 0, 0, 0, true);
                previous = false;
            }

        }*/

        if (jumpButton & onGround > 0) {
            player.applyForceToCenter(0, 150, true);
            if (!jumpSound) {
                jump.play();
                jumpSound = true;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        jumpSound = false;
                    }
                }, 0.3f);
            }
        }
        if (left & onGround >0) {
            player.setLinearVelocity(-350 / PPM, 0);
            //  player.applyLinearImpulse(-0.1f, 0, 0, 0, true);
            left = true;
            previous = true;
        }
        if (right & onGround >0) {
            player.setLinearVelocity(350 / PPM, 0);
            //  player.applyLinearImpulse(0.1f, 0, 0, 0, true);
            right = true;
            previous = false;
        }
        //   jumpButton = true;
        if (left & onGround ==0) {
           // player.setLinearVelocity(-200 / PPM, 0);
              player.applyLinearImpulse(-0.1f, 0, 0, 0, true);
            left = true;
            previous = true;
        }
        if (right & onGround ==0) {
           // player.setLinearVelocity(200 / PPM, 0);
              player.applyLinearImpulse(0.1f, 0, 0, 0, true);
            right = true;
            previous = false;
        }

        if (left & jumpButton & onGround > 0) {
            player.applyLinearImpulse(-0.1f, 0, 0, 0, true);
            player.applyForceToCenter(0, 250, true);
            if (!jumpSound) {
                jump.play();
                jumpSound = true;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        jumpSound = false;
                    }
                }, 0.3f);
            }
            left = true;
            previous = true;
        }

        if (right & jumpButton & onGround > 0) {
            player.applyLinearImpulse(0.1f, 0, 0, 0, true);
            player.applyForceToCenter(0, 250, true);
            if (!jumpSound) {
                jump.play();
                jumpSound = true;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        jumpSound = false;
                    }
                }, 0.3f);
            }
            left = false;
            previous = false;
        }

    }




    public void draw(SpriteBatch batch,float dt,OrthographicCamera camera){

        float y = player.getPosition().y*PPM-heigth/2f;
        float x = player.getPosition().x*PPM-width/2f;

        batch.begin();

        if(onGround == 0){
            if(previous)
                batch.draw(jumpLeft,x,y);
            else
                batch.draw(jumpRight,x,y);
        }
        else if(right) {
            batch.draw(animationRight.getKeyFrame(dt, true),x,y);
        }
        else if(left){
            batch.draw(animationLeft.getKeyFrame(dt, true),x,y);
        }

        else {
            batch.draw(front,x,y);
        }
        if(currentState == GameStateManager.PLAY) {
            for (int i = 0; i < lifes; i++) {
                batch.draw(lifeTexture, camera.position.x - 470 + (i * 35), camera.position.y + 220, 42, 33);
            }

            font.draw(batch,coinsCounter + " / " + totalCoins, camera.position.x - 345, camera.position.y + 240);
            batch.draw(allCoins, camera.position.x - 300, camera.position.y + 220, 30, 30);
        }
        batch.end();
    }
}
