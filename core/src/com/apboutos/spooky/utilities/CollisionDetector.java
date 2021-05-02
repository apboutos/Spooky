package com.apboutos.spooky.utilities;

import com.apboutos.spooky.effects.Explosion;
import com.apboutos.spooky.effects.SquashStar;
import com.apboutos.spooky.units.Player;
import com.apboutos.spooky.units.Unit;
import com.apboutos.spooky.units.block.Block;
import com.apboutos.spooky.units.enemy.Enemy;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;

public class CollisionDetector {

    private final List<SquashStar> stars;
    private final List<Explosion> explosions;
    private final SpriteBatch batch;

    private final List<Unit> units;

    public CollisionDetector(List<Unit> units, List<SquashStar> stars ,List<Explosion> explosions,SpriteBatch batch){
        this.units = units;
        this.stars = stars;
        this.explosions = explosions;
        this.batch = batch;
    }


    public void detectCollision(Unit unit){

            if(unit instanceof Player){
                if(detectCollisionWithTheMapBorder(unit)){
                    unit.setMoving(false);
                }
                if(detectCollisionWithStaticBlock(unit)){
                    unit.setMoving(false);
                }
                if(detectCollisionWithEnemy(unit)){
                    unit.setDead(true);
                    stars.add(new SquashStar(unit.getBounds().x,unit.getBounds().y, StarColor.Yellow,batch));
                }
                if(detectCollisionWithMovingBlock(unit)){
                    unit.setDead(true);
                    stars.add(new SquashStar(unit.getBounds().x,unit.getBounds().y, StarColor.Yellow,batch));
                }
                if(detectCollisionWithExplosion(unit)){
                    unit.setDead(true);
                    stars.add(new SquashStar(unit.getBounds().x,unit.getBounds().y, StarColor.Yellow,batch));
                }
            }

    }

    @SuppressWarnings("RedundantIfStatement")
    private boolean detectCollisionWithTheMapBorder(Unit unit){
        Rectangle boundsOnNextMove = new Rectangle(0,0,unit.getBounds().width,unit.getBounds().height);
        if(unit.getDirection() == Direction.LEFT){
            boundsOnNextMove.setPosition(unit.getBounds().x - unit.getSpeed().x,unit.getBounds().y);
            if(boundsOnNextMove.x < GameDimensions.xAxisMinumum * GameDimensions.unitWidth)
                return true;
        }
        if(unit.getDirection() == Direction.RIGHT){
            boundsOnNextMove.setPosition(unit.getBounds().x + unit.getSpeed().x,unit.getBounds().y);
            if(boundsOnNextMove.x + boundsOnNextMove.width > GameDimensions.xAxisMaximum * GameDimensions.unitWidth)
                return true;
        }
        if(unit.getDirection() == Direction.UP){
            boundsOnNextMove.setPosition(unit.getBounds().x ,unit.getBounds().y + unit.getSpeed().y);
            if(boundsOnNextMove.y + boundsOnNextMove.height > GameDimensions.yAxisMaximum * GameDimensions.unitHeight)
                return true;
        }
        if(unit.getDirection() == Direction.DOWN){
            boundsOnNextMove.setPosition(unit.getBounds().x ,unit.getBounds().y - unit.getSpeed().y);
            if(boundsOnNextMove.y < GameDimensions.yAxisMinimum * GameDimensions.unitHeight)
                return true;
        }

        return false;
    }

    private boolean detectCollisionWithStaticBlock(Unit unit){
        Rectangle boundsOnNextMove = new Rectangle(0,0,unit.getBounds().width,unit.getBounds().height);
        switch (unit.getDirection()){
            case    UP: boundsOnNextMove.setPosition(unit.getBounds().x,unit.getBounds().y + unit.getSpeed().y); break;
            case  DOWN: boundsOnNextMove.setPosition(unit.getBounds().x,unit.getBounds().y - unit.getSpeed().y); break;
            case  LEFT: boundsOnNextMove.setPosition(unit.getBounds().x  - unit.getSpeed().x,unit.getBounds().y); break;
            case RIGHT: boundsOnNextMove.setPosition(unit.getBounds().x  + unit.getSpeed().x,unit.getBounds().y); break;
        }
        for(Unit i : units){
            if(i instanceof Block && !i.isDead() &&boundsOnNextMove.overlaps(i.getBounds()))
                return true;
        }
        return false;
    }

    private boolean detectCollisionWithEnemy(Unit unit){
        for(Unit i : units){
            if(i instanceof Enemy && i.getBounds().overlaps(unit.getBounds()) &&!((Enemy) i).isDead() && !unit.isDead())
                return true;
        }
        return false;
    }

    private boolean detectCollisionWithMovingBlock(Unit unit){
        for(Unit i : units){
            if(i instanceof Block && i.isMoving() && !i.isDead() && unit.getBounds().overlaps(i.getBounds()) && !unit.isDead()){
                return true;
            }
        }
        return false;
    }

    private boolean detectCollisionWithExplosion(Unit unit){
        for (Explosion explosion : explosions){
            if(unit.getBounds().overlaps(explosion.getBounds()) && !unit.isDead())
                return true;
        }
        return false;
    }
}




