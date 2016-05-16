package com.ardimval.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import java.util.ArrayList;

import static com.ardimval.game.B2DValues.*;


public class Coins {

    TextureRegion coinDefault;
    Animation coinAnimation;
    ArrayList<Body> coins;
    int coinTimer;

    public Coins(){

        //Get objects from Tiled Map
        MapObjects objects = tmap.getLayers().get("coins").getObjects();
        coins = new ArrayList<>();
        coinTimer = 1;

        //Set Box2D stuff
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        //Set Textures and Animation
        Texture texture = new Texture(Gdx.files.internal("res/PNG/Coin/frames.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion[] region = TextureRegion.split(texture,51,54)[0];
        coinDefault = region[0];
        coinAnimation = new Animation(1/12f,region);

        for(MapObject obj : objects){
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((obj.getProperties().get("x", Float.class))/PPM, (obj.getProperties().get("y", Float.class))/PPM);
            shape.setRadius(25 / PPM);

            fdef.shape = shape;
            fdef.isSensor = true;
            Body body = world.createBody(bdef);
            coins.add(body);
            body.createFixture(fdef).setUserData("coin");
        }
        shape.dispose();
    }
    public void draw(SpriteBatch batch,float time){
        batch.begin();
        if(coinTimer <= 41){
            for(Body i : coins){
                batch.draw(coinAnimation.getKeyFrame(time,true),i.getPosition().x * PPM - 25, i.getPosition().y * PPM- 25);

            }
            coinTimer++;
        }
        else{
            if(coinTimer >= 250)
                coinTimer = 0;
            for(Body i : coins) {
                batch.draw(coinDefault, i.getPosition().x * PPM - 25, i.getPosition().y * PPM - 25);

            }
            coinTimer++;
        }

        batch.end();
    }
}
