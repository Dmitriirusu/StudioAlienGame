package com.ardimval.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


import static com.ardimval.game.B2DValues.selectedLevel;
import static com.ardimval.game.B2DValues.tmap;



public class LevelSelectState extends GameState {

    private Stage stage;
    TextButton.TextButtonStyle  style;
    TiledMapRenderer tmapRenderer;

    TextButton buttons[];

    public LevelSelectState() {
        super();

        tmap = new TmxMapLoader().load("res/maps/main.tmx");
        tmapRenderer = new OrthogonalTiledMapRenderer(tmap);
        selectedLevel = "";

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("res/PNG/font/level.pack"));
        Skin skin = new Skin(atlas);
        stage = new Stage();

        Table table = new Table();
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        style = new TextButton.TextButtonStyle();
        style.up = skin.getDrawable("level");
        style.down = skin.getDrawable("level");
        style.font = new BitmapFont();
        stage = new Stage();

        buttons = new TextButton[9];

        for(int i =0;i<9;i++){
            final TextButton button = new TextButton(String.valueOf((i+1)),style);
            button.pad(20);
            button.getLabel().setFontScale(2,2);
            button.getLabel().setColor(Color.BLUE);
            table.add(button).pad(20, 50, 20, 50);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    selectedLevel = String.valueOf(button.getText().toString());
                    if(selectedLevel.equals("1")) {
                        B2DValues.currentState = 0;
                        B2DValues.gsm.setState(0);
                    }
                }
            });
            buttons[i] = button;

            if((i+1)%3 == 0)
                table.row();
        }

        Gdx.input.setInputProcessor(stage);
        stage.addActor(table);

    }



    @Override
    public void update() {
        super.update();
        stage.act();
    }

    @Override
    public void draw() {
        super.draw();
        tmapRenderer.setView(camera);
        tmapRenderer.render();
        stage.draw();
    }

}
