package com.ardimval.game;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import static com.ardimval.game.B2DValues.PPM;
import static com.ardimval.game.B2DValues.tmap;
import static com.ardimval.game.B2DValues.world;


public class Ground {

    public Ground() {
        MapObjects polyLine = tmap.getLayers().get("ground1").getObjects();
        for (Object obj : polyLine) {
            float[] vertices = ((PolylineMapObject) obj).getPolyline().getTransformedVertices();
            int length = vertices.length;
            Vector2[] vector = new Vector2[length / 2];


            for (int i = 0, j = 0; i < length; i += 2, j++) {
                vector[j] = new Vector2(vertices[i] / PPM, vertices[i + 1] / PPM);
            }
            //box2d stuff
            BodyDef bdef = new BodyDef();
            FixtureDef fdef = new FixtureDef();
            ChainShape shape = new ChainShape();
            shape.createChain(vector);

            bdef.type = BodyDef.BodyType.StaticBody;

            fdef.shape = shape;
            fdef.friction = 1;
            fdef.filter.categoryBits = B2DValues.CB_GROUND;
            Body body = world.createBody(bdef);
            body.createFixture(fdef).setUserData("ground");

            shape.dispose();
        }
    }
}
