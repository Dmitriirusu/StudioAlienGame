package com.ardimval.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;


public class B2dSprite {

    Body body;
    Animation animation;

    public B2dSprite(Body body){
        this.body = body;

    }

    public void setTexture(TextureRegion[] region){
        animation = new Animation(1/30,region);
    }

    public void draw(SpriteBatch batch){
        batch.begin();
        // batch.draw(animation.getKeyFrame());
    }
}
