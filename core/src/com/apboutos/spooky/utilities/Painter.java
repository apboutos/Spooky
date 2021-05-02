package com.apboutos.spooky.utilities;

import com.apboutos.spooky.units.Player;
import com.apboutos.spooky.units.Unit;
import com.apboutos.spooky.units.enemy.Enemy;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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



    public void draw(Unit unit, float delta){

        if(unit.isDead()){

            if(unit instanceof Player) drawPlayerSquash((Player) unit);
            if(unit instanceof Enemy) drawEnemySquash((Enemy) unit);
        }
        else {
            if(unit instanceof Player) drawPlayer((Player) unit , delta);
            if(unit instanceof Enemy) drawEnemy((Enemy) unit, delta);
        }

    }

    private void drawPlayerSquash(Player player){
        player.getSquash().setBounds(player.getBounds().x,player.getBounds().y,player.getBounds().width,player.getBounds().height);
        player.getSquash().draw(batch);
    }

    private void drawEnemySquash(Enemy enemy){
        enemy.getSquash().setBounds(enemy.getBounds().x,enemy.getBounds().y,enemy.getBounds().width,enemy.getBounds().height);
        enemy.getSquash().draw(batch);
    }


    private void drawPlayer(Player player , float delta){

        switch (player.getDirection()){
            case UP:    if(player.isMoving()) batch.draw(player.getPlayerMovingUp().getKeyFrame(delta,true),player.getBounds().x,player.getBounds().y);
            else batch.draw(player.getPlayerMovingUp().getKeyFrame(0,true),player.getBounds().x,player.getBounds().y);
                break;
            case DOWN:  if(player.isMoving()) batch.draw(player.getPlayerMovingDown().getKeyFrame(delta,true),player.getBounds().x,player.getBounds().y);
            else batch.draw(player.getPlayerMovingDown().getKeyFrame(0,true),player.getBounds().x,player.getBounds().y);
                break;
            case LEFT:  if(player.isMoving()) batch.draw(player.getPlayerMovingLeft().getKeyFrame(delta,true),player.getBounds().x,player.getBounds().y);
            else batch.draw(player.getPlayerMovingLeft().getKeyFrame(0,true),player.getBounds().x,player.getBounds().y);
                break;
            case RIGHT: if(player.isMoving()) batch.draw(player.getPlayerMovingRight().getKeyFrame(delta,true),player.getBounds().x,player.getBounds().y);
            else batch.draw(player.getPlayerMovingRight().getKeyFrame(0,true),player.getBounds().x,player.getBounds().y);
                break;
        }
    }

    private void drawEnemy(Enemy enemy, float delta){

        switch (enemy.getDirection()){
            case UP:    if(enemy.isMoving()) batch.draw(enemy.getAnimationUp().getKeyFrame(delta,true),enemy.getBounds().x,enemy.getBounds().y);
            else batch.draw(enemy.getAnimationUp().getKeyFrame(0,true),enemy.getBounds().x,enemy.getBounds().y);
                break;
            case DOWN:  if(enemy.isMoving()) batch.draw(enemy.getAnimationDown().getKeyFrame(delta,true),enemy.getBounds().x,enemy.getBounds().y);
            else batch.draw(enemy.getAnimationDown().getKeyFrame(0,true),enemy.getBounds().x,enemy.getBounds().y);
                break;
            case LEFT:  if(enemy.isMoving()) batch.draw(enemy.getAnimationLeft().getKeyFrame(delta,true),enemy.getBounds().x,enemy.getBounds().y);
            else batch.draw(enemy.getAnimationLeft().getKeyFrame(0,true),enemy.getBounds().x,enemy.getBounds().y);
                break;
            case RIGHT: if(enemy.isMoving()) batch.draw(enemy.getAnimationRight().getKeyFrame(delta,true),enemy.getBounds().x,enemy.getBounds().y);
            else batch.draw(enemy.getAnimationRight().getKeyFrame(0,true),enemy.getBounds().x,enemy.getBounds().y);
                break;
        }
    }


}
