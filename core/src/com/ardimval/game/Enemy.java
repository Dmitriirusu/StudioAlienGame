package com.ardimval.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import java.util.ArrayList;
import static com.ardimval.game.B2DValues.*;


public class Enemy {

    TextureRegion walkRight[];
    TextureRegion walkLeft[];
    TextureRegion deadRight;
    TextureRegion deadLeft;
    Animation animationRight,animationLeft;
    ArrayList<Body> enemies;
    float originX,originY;


    public Enemy(){
        //get tiled oblects
        MapObjects objects = tmap.getLayers().get("enemy").getObjects();
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("res/PNG/enemies/enemy.pack"));
        walkLeft = new TextureRegion[2];
        walkLeft[0] = atlas.findRegion("slime_normal_left");
        walkLeft[1] = atlas.findRegion("slime_walk_left");
        deadLeft = atlas.findRegion("slime_dead_left");

        walkRight = new TextureRegion[2];
        walkRight[0] = atlas.findRegion("slime_normal_right");
        walkRight[1] = atlas.findRegion("slime_walk_right");
        deadRight = atlas.findRegion("slime_dead_right");

        animationRight = new Animation(1/3f,walkRight);
        animationLeft = new Animation(1/3f,walkLeft);

        enemies = new ArrayList<Body>();
        //Box2D stuff
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();


        for(MapObject obj : objects){
            bdef.type = BodyDef.BodyType.DynamicBody;
            bdef.position.set((obj.getProperties().get("x", Float.class) / PPM), obj.getProperties().get("y", Float.class)/ PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = CB_ENEMY;
            fdef.filter.maskBits = CB_BORDER | CB_GROUND | CB_PLAYER;
            shape.setAsBox(43 /2/ PPM,28/2 / PPM);
            Body body = world.createBody(bdef);
            body.createFixture(fdef).setUserData("enemy");
            //making sensor for collison with player
            shape.setAsBox(18 / PPM, 3/ PPM,new Vector2(0,14 / PPM),0);
            fdef.shape = shape;
            fdef.filter.categoryBits = CB_ENEMY;
            fdef.filter.maskBits = CB_PLAYER_SENSOR;
            body.createFixture(fdef).setUserData("enemySensor");
            enemies.add(body);
        }
        shape.dispose();

    }

    public void draw(SpriteBatch batch,float time){
        batch.begin();
        for(Body i : enemies){
            if(enemyLeft) {
                i.setLinearVelocity(-150 / PPM, 0);
                batch.draw(animationLeft.getKeyFrame(time,true), (float) (i.getPosition().x  * PPM- 21.5),i.getPosition().y  * PPM- 14);
            }
            else {
                i.setLinearVelocity(150 / PPM, 0);
                batch.draw(animationRight.getKeyFrame(time,true), (float) (i.getPosition().x  * PPM- 21.5),i.getPosition().y  * PPM- 14);
            }
        }
        batch.end();
    }
}
