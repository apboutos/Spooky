package com.apboutos.spooky.utilities;

import com.apboutos.spooky.effects.Explosion;
import com.apboutos.spooky.units.Block;
import com.apboutos.spooky.units.Enemy;
import com.apboutos.spooky.units.Unit;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import lombok.Setter;

import java.util.List;

public class CollisionDetector {

    private final List<Explosion> explosions;

    @Setter
    private List<Unit> units;

    public CollisionDetector(List<Unit> units,List<Explosion> explosions){
        this.units = units;
        this.explosions = explosions;
    }

    @SuppressWarnings("RedundantIfStatement")
    public boolean detectCollisionWithTheMapBorderOnNextMove(Rectangle bounds, Direction direction, Vector2 speed){
        Rectangle boundsOnNextMove = new Rectangle(0,0,bounds.width,bounds.height);
        if(direction == Direction.LEFT){
            boundsOnNextMove.setPosition(bounds.x - speed.x,bounds.y);
            if(boundsOnNextMove.x < GameDimensions.xAxisMinumum * GameDimensions.unitWidth)
                return true;
        }
        if(direction == Direction.RIGHT){
            boundsOnNextMove.setPosition(bounds.x + speed.x,bounds.y);
            if(boundsOnNextMove.x + boundsOnNextMove.width > GameDimensions.xAxisMaximum * GameDimensions.unitWidth)
                return true;
        }
        if(direction == Direction.UP){
            boundsOnNextMove.setPosition(bounds.x ,bounds.y + speed.y);
            if(boundsOnNextMove.y + boundsOnNextMove.height > GameDimensions.yAxisMaximum * GameDimensions.unitHeight)
                return true;
        }
        if(direction == Direction.DOWN){
            boundsOnNextMove.setPosition(bounds.x ,bounds.y - speed.y);
            if(boundsOnNextMove.y < GameDimensions.yAxisMinimum * GameDimensions.unitHeight)
                return true;
        }

        return false;
    }

    public Block detectCollisionWithStaticBlockOnNextMove(Rectangle bounds, Direction direction, Vector2 speed){
        Rectangle boundsOnNextMove = new Rectangle(0,0,bounds.width,bounds.height);
        switch (direction){
            case    UP: boundsOnNextMove.setPosition(bounds.x,bounds.y + speed.y); break;
            case  DOWN: boundsOnNextMove.setPosition(bounds.x,bounds.y - speed.y); break;
            case  LEFT: boundsOnNextMove.setPosition(bounds.x  - speed.x,bounds.y); break;
            case RIGHT: boundsOnNextMove.setPosition(bounds.x  + speed.x,bounds.y); break;
        }
        for(Unit i : units){
            if(i instanceof Block && boundsOnNextMove.overlaps(i.getBounds()) && !i.isMoving())
                return (Block) i;
        }
        return null;
    }

    public Block detectCollisionWithStaticBlockOnNextMove(Unit unit){
        Rectangle boundsOnNextMove = new Rectangle(0,0,unit.getBounds().width,unit.getBounds().height);
        switch (unit.getDirection()){
            case    UP: boundsOnNextMove.setPosition(unit.getBounds().x,unit.getBounds().y + unit.getSpeed().y); break;
            case  DOWN: boundsOnNextMove.setPosition(unit.getBounds().x,unit.getBounds().y - unit.getSpeed().y); break;
            case  LEFT: boundsOnNextMove.setPosition(unit.getBounds().x  - unit.getSpeed().x,unit.getBounds().y); break;
            case RIGHT: boundsOnNextMove.setPosition(unit.getBounds().x  + unit.getSpeed().x,unit.getBounds().y); break;
        }
        for(Unit i : units){
            if(i instanceof Block && boundsOnNextMove.overlaps(i.getBounds()) && !i.equals(unit) && !i.isMoving())
                return (Block) i;
        }
        return null;
    }

    public boolean detectCollisionWithEnemy(Unit unit){
        for(Unit i : units){
            if(i instanceof Enemy && i.getBounds().overlaps(unit.getBounds()) &&!i.isDead() && !unit.isDead())
                return true;
        }
        return false;
    }

    public boolean detectCollisionWithMovingBlock(Unit unit){
        for(Unit i : units){
            if(i instanceof Block && i.isMoving() && !i.isDead() && unit.getBounds().overlaps(i.getBounds()) && !unit.isDead()){
                return true;
            }
        }
        return false;
    }

    public Block detectCollisionWithMovingBlockOnNextMove(Unit unit){
        for(Unit i : units){
            if(i instanceof Block && i.isMoving() && unit.getBounds().overlaps(i.getBounds())&& !unit.equals(i)){
                return (Block) i;
            }
        }
        return null;
    }

    public boolean detectCollisionWithExplosion(Unit unit){
        for (Explosion explosion : explosions){
            if(unit.getBounds().overlaps(explosion.getBounds()) && !unit.isDead())
                return true;
        }
        return false;
    }
}




