package com.apboutos.spooky.screen;

import com.apboutos.spooky.boot.Spooky;
import com.apboutos.spooky.utilities.GameDimensions;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class MainMenu implements Screen {

    private Spooky spooky;
    private Sprite mainmenu;


    public MainMenu(Spooky spooky){
        this.spooky = spooky;
    }


    @Override
    public void show() {
        mainmenu = new Sprite(new Texture(Gdx.files.internal("Images/Screens/MainMenu.png")));
        mainmenu.setPosition( -GameDimensions.screenWidth/2 ,-GameDimensions.screenHeight/2);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spooky.batch.setProjectionMatrix(spooky.camera.combined);
        spooky.batch.begin();
        mainmenu.draw(spooky.batch);
        spooky.batch.end();

        if (Gdx.input.isTouched() == true)
        {
            spooky.setScreen(spooky.levelChanger);
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
        mainmenu.getTexture().dispose();
        mainmenu = null;

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }
}
