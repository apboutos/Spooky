package com.apboutos.spooky.screen;

import com.apboutos.spooky.boot.Spooky;
import com.apboutos.spooky.utilities.GameDimensions;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Splash implements Screen {


    private Spooky spooky;
    private Sprite btlogo;
    private float timepassed = 0;

    public Splash(Spooky spooky){
        this.spooky = spooky;
        btlogo = new Sprite(new Texture(Gdx.files.internal("Images/Logo/btlogo.png")));
        btlogo.setPosition(GameDimensions.xAxisMinumum * GameDimensions.unitWidth,GameDimensions.yAxisMinimum * GameDimensions.unitHeight);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spooky.batch.setProjectionMatrix(spooky.camera.combined);
        spooky.batch.begin();
        btlogo.draw(spooky.batch);
        spooky.batch.end();
        timepassed = timepassed + delta;
        if (timepassed > 3)
        {
            spooky.setScreen(spooky.mainmenu);
        }

    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        btlogo.getTexture().dispose();
    }

    @Override
    public void dispose() {

    }
}
