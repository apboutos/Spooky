package com.apboutos.spooky.utilities;

import com.apboutos.spooky.units.Player;
import com.apboutos.spooky.units.Unit;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

@SuppressWarnings("unused")
public class Painter {

    private final SpriteBatch batch;


    public Painter(SpriteBatch batch){
        this.batch = batch;
    }



    public void beginDrawing(){
        batch.begin();
    }

    public void endDrawing(){
        batch.end();
    }

    public void draw(List<Unit> units){



    }

    public void draw(Unit unit, float delta){

        if(unit.isIAmDead()){

            ((Player)unit).getSquash().setBounds(unit.getBounds().x,unit.getBounds().y,unit.getBounds().width,unit.getBounds().height);
            ((Player)unit).getSquash().draw(batch);
            return;
        }

        switch (unit.getDirection()){
            case UP: if(unit.isIAmMoving()) batch.draw(((Player)unit).getPlayerMovingUp().getKeyFrame(delta,true),unit.getBounds().x,unit.getBounds().y);
                     else batch.draw(((Player)unit).getPlayerMovingUp().getKeyFrame(0,true),unit.getBounds().x,unit.getBounds().y);
                     break;
            case DOWN:  if(unit.isIAmMoving()) batch.draw(((Player)unit).getPlayerMovingDown().getKeyFrame(delta,true),unit.getBounds().x,unit.getBounds().y);
                        else batch.draw(((Player)unit).getPlayerMovingDown().getKeyFrame(0,true),unit.getBounds().x,unit.getBounds().y);
                        break;
            case LEFT:  if(unit.isIAmMoving()){
                        batch.draw(((Player)unit).getPlayerMovingLeft().getKeyFrame(delta,true),unit.getBounds().x,unit.getBounds().y);
                        System.out.println("MOVING LEFT");
                        System.out.println("delta = " + delta);
                        }
                        else {
                            batch.draw(((Player)unit).getPlayerMovingLeft().getKeyFrame(0,true),unit.getBounds().x,unit.getBounds().y);
                            //System.out.println("SITING LEFT");
                        }
                        break;
            case RIGHT: if(unit.isIAmMoving()) batch.draw(((Player)unit).getPlayerMovingRight().getKeyFrame(delta,true),unit.getBounds().x,unit.getBounds().y);
                        else batch.draw(((Player)unit).getPlayerMovingRight().getKeyFrame(0,true),unit.getBounds().x,unit.getBounds().y);
                        break;
        }

    }

}
