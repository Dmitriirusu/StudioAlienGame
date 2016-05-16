package com.ardimval.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import static com.ardimval.game.B2DValues.*;

public class Key {

    TextureRegion key;
    float x,y;
    Body body;

    public Key() {
        key = new TextureRegion(new Texture(Gdx.files.internal("res/PNG/key_yellow.png")));
        MapObject obj = tmap.getLayers().get("key").getObjects().get(0);

        x = obj.getProperties().get("x",Float.class) ;
        y = obj.getProperties().get("y", Float.class) ;

        //Box2D stuff
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(x / PPM,y / PPM);

        shape.setAsBox( key.getRegionWidth()/2 / PPM, key.getRegionHeight()/2 / PPM);

        fdef.shape = shape;
        fdef.isSensor = true;

        body = world.createBody(bdef);
        body.createFixture(fdef).setUserData("key");

        shape.dispose();

    }

    public void draw(SpriteBatch batch){
        batch.begin();
        batch.draw(key,x- key.getRegionWidth()/2,y- key.getRegionHeight()/2);
        batch.end();
    }
}
