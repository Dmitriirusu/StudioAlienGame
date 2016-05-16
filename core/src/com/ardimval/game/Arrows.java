package com.ardimval.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.*;


public class Arrows {

    private Stage stage;
    Skin skin;

    ImageButton btnLeft,btnRight,btnUp;

    public Arrows(){
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("res/PNG/character/arrows/arrows.atlas"));

        stage = new Stage();
        skin = new Skin(atlas);

        btnLeft = setBtnStyle("Left");
        btnRight = setBtnStyle("Right");
        btnUp = setBtnStyle("Up");

        Gdx.input.setInputProcessor(stage);

        stage.addActor(btnLeft);
        stage.addActor(btnRight);
        stage.addActor(btnUp);
    }

    public ImageButton setBtnStyle(final String btnTexture){
        //Setting button image
        ImageButtonStyle style = new ImageButtonStyle();
        style.up = skin.getDrawable("arrow" + btnTexture);
        style.down = skin.getDrawable("arrow" + btnTexture);
        ImageButton button = new ImageButton(style);

        //setting button ClickListener
        button.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                switch (btnTexture){
                    case "Left":
                        B2DValues.left = true;
                        break;
                    case "Right":
                        B2DValues.right = true;
                        break;
                    case "Up":
                        B2DValues.jumpButton = true;
                        break;
                }
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                switch (btnTexture){
                    case "Left":
                        B2DValues.left = false;
                        break;
                    case "Right":
                        B2DValues.right = false;
                        break;
                    case "Up":
                        B2DValues.jumpButton = false;
                        break;
                }
            }
        });
        return button;
    }

    public void draw(){
        btnLeft.setPosition(Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/8);
        btnRight.setPosition(Gdx.graphics.getWidth()/15 + 200,Gdx.graphics.getHeight()/8);
        btnUp.setPosition(Gdx.graphics.getWidth()  - Gdx.graphics.getWidth()/6 ,Gdx.graphics.getHeight()/8);
        stage.act();
        stage.draw();
    }
}
