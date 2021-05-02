package com.apboutos.spooky.utilities;

import com.apboutos.spooky.level.Settings;
import com.apboutos.spooky.units.Player;
import com.apboutos.spooky.units.Unit;
import com.apboutos.spooky.units.block.Block;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.List;


public class InputHandler {

    private final Player player;
    private final Vector3 touchCoords;
    private Boolean gearIsPressed;
    private final Settings settings;
    private final Camera camera;
    private final List<Unit> units;

    public InputHandler(Player player, Camera camera, Boolean gearIsPressed, Settings settings, List<Unit> units){

        this.player = player;
        touchCoords = new Vector3();
        this.gearIsPressed = gearIsPressed;
        this.settings = settings;
        this.units = units;
        this.camera = camera;
    }

    public void handleInput(){

        touchCoords.x = Gdx.input.getX();
        touchCoords.y = Gdx.input.getY();
        camera.unproject(touchCoords);

        if (touchCoords.x > 320 && touchCoords.y < -200)//TODO coord remake
        {
            gearIsPressed = true;
        }

        if(settings.controls == 0){
            handleKeyboardInput();
        }
        if(settings.controls == 1 && Gdx.input.isTouched()){
            handleAndroidInput();
        }

    }

    private void handleKeyboardInput(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
        {
            push();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)&& !player.isMoving())
        {
            player.setMoving(true);
            player.setDirection(Direction.UP);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)&& !player.isMoving())
        {
            player.setMoving(true);
            player.setDirection(Direction.DOWN);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && !player.isMoving())
        {
            player.setMoving(true);
            player.setDirection(Direction.LEFT);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)&& !player.isMoving())
        {
            player.setMoving(true);
            player.setDirection(Direction.RIGHT);
        }
    }

    private void handleAndroidInput(){
        if (!player.isMoving())
        {
            player.setPushing(false);
            if ( touchCoords.x <= -168)
            {
                player.setMoving(true);
                player.setDirection(Direction.LEFT);
            }
            else if(touchCoords.x > 168 && touchCoords.y > -120)
            {
                player.setMoving(true);
                player.setDirection(Direction.RIGHT);
            }
            else if((touchCoords.x > -168 && touchCoords.x<= 168) && touchCoords.y < 0)
            {
                player.setMoving(true);
                player.setDirection(Direction.DOWN);
            }
            else if((touchCoords.x > -168 && touchCoords.x<= 168) && touchCoords.y > 0)
            {
                player.setMoving(true);
                player.setDirection(Direction.UP);
            }
            else if((touchCoords.x > 240 && touchCoords.y < -120))
            {
                player.setPushing(true);
            }
        }
    }

    private void push(){

        for(Unit unit : units)
        {
            // If I don't have this if, the player will be able to push blocks
            // that are already moving.
            if(unit instanceof Block && !unit.isMoving())
            {
                if (player.getDirection() == Direction.UP)
                {
                    if (unit.getBounds().overlaps(new Rectangle(player.getBounds().x, player.getBounds().y + player.getSpeed().y, GameDimensions.unitWidth, GameDimensions.unitHeight)))
                    {
                        ((Block)unit).push(player.getDirection());
                        break;
                    }
                }
                else if (player.getDirection() == Direction.DOWN)
                {
                    if (unit.getBounds().overlaps(new Rectangle(player.getBounds().x, player.getBounds().y - player.getSpeed().y, GameDimensions.unitWidth, GameDimensions.unitHeight)))
                    {
                        ((Block)unit).push(player.getDirection());
                        break;
                    }
                }
                else if (player.getDirection() == Direction.LEFT)
                {
                    if (unit.getBounds().overlaps(new Rectangle(player.getBounds().x - player.getSpeed().x, player.getBounds().y, GameDimensions.unitWidth, GameDimensions.unitHeight)))
                    {
                        ((Block)unit).push(player.getDirection());
                        break;
                    }
                }
                else if (player.getDirection() == Direction.RIGHT)
                {
                    if (unit.getBounds().overlaps(new Rectangle(player.getBounds().x + player.getSpeed().x, player.getBounds().y, GameDimensions.unitWidth, GameDimensions.unitHeight)))
                    {
                        ((Block)unit).push(player.getDirection());
                        break;
                    }
                }
            }
        }

    }

}
