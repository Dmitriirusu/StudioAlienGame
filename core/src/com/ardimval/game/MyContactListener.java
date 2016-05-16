package com.ardimval.game;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import static com.ardimval.game.B2DValues.*;

public class MyContactListener implements ContactListener {
    Fixture fa;
    Fixture fb;
    ArrayList<Body> delete= new ArrayList<Body>();
    ArrayList<Body> enemyDelete= new ArrayList<Body>();


    @Override
    public void beginContact(Contact contact) {
        fa = contact.getFixtureA();
        fb = contact.getFixtureB();

        if(fa.getUserData() == "sensor")
            onGround++;
        if(fb.getUserData() == "sensor")
            onGround++;

        if(fa.getUserData() == "enemy")
            enemyLeft = !enemyLeft;
        if(fb.getUserData() == "enemy")
            enemyLeft = !enemyLeft;

        if(fa.getUserData() == "player" && fb.getUserData() == "enemy") {
            jumpButton = true;

        }
        if(fa.getUserData() == "enemy" && fb.getUserData() == "player") {
            jumpButton = true;
        }

        if(fa.getUserData() == "player" && fb.getUserData() == "key") {
            B2DValues.currentState = 2;
            B2DValues.gsm.setState(2);
        }
        if(fa.getUserData() == "key" && fb.getUserData() == "player") {
            B2DValues.currentState = 2;
            B2DValues.gsm.setState(2);
        }

        if(fa.getUserData() == "enemySensor") {
            enemyDelete.add(fa.getBody());
        }
        if(fb.getUserData() == "enemySensor") {
            enemyDelete.add(fb.getBody());
        }

        if(fa.getUserData() == "coin" && fb.getUserData() == "player") {

            delete.add(fa.getBody());
            Player.coinsCounter++;

        }
        else if(fb.getUserData() == "coin" && fa.getUserData() == "player") {

            delete.add(fb.getBody());
            Player.coinsCounter++;

        }
    }

    @Override
    public void endContact(Contact contact) {
        fa = contact.getFixtureA();
        fb = contact.getFixtureB();

        if(fa.getUserData() == "sensor")
            onGround--;
        if(fb.getUserData() == "sensor")
            onGround--;

        if(fa.getUserData() == "player" && fb.getUserData() == "enemy") {
            jumpButton = false;
            Player.lifes--;
        }
        if(fa.getUserData() == "enemy" && fb.getUserData() == "player"){
            jumpButton = false;
            Player.lifes--;
        }
    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
