package com.apboutos.spooky.utilities;

import com.apboutos.spooky.effects.Explosion;
import com.apboutos.spooky.effects.SquashStar;
import com.apboutos.spooky.units.Player;
import com.apboutos.spooky.units.Unit;
import com.apboutos.spooky.units.block.Block;
import com.apboutos.spooky.units.enemy.Enemy;
import com.apboutos.spooky.units.enemy.Fish;
import com.apboutos.spooky.units.enemy.Shark;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import lombok.Setter;
import lombok.var;

import java.util.List;

public class CollisionDetector {

    private final List<SquashStar> stars;
    private final List<Explosion> explosions;
    private final SpriteBatch batch;

    @Setter
    private List<Unit> units;

    public CollisionDetector(List<Unit> units, List<SquashStar> stars ,List<Explosion> explosions,SpriteBatch batch){
        this.units = units;
        this.stars = stars;
        this.explosions = explosions;
        this.batch = batch;
    }


    public void detectCollision(Unit unit){

            if(unit instanceof Player){
                if(detectCollisionWithTheMapBorder(unit.getBounds(),unit.getDirection(),unit.getSpeed())){
                    unit.setMoving(false);
                }
                if(detectCollisionWithStaticBlock(unit.getBounds(),unit.getDirection(),unit.getSpeed()) != null){
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
                return;
            }
            if(unit instanceof Enemy){
                if(detectCollisionWithTheMapBorder(unit.getBounds(),unit.getDirection(),unit.getSpeed())){
                    unit.setMoving(false);
                    ((Enemy) unit).setCollidedWithMap(true);
                }
                var block = detectCollisionWithStaticBlock(unit.getBounds(),unit.getDirection(),unit.getSpeed());
                if( block != null){
                    unit.setMoving(false);
                    ((Enemy) unit).setCollidedWithBlock(true);
                    ((Enemy) unit).setLastBlockCollidedWith(block);
                }
                if(detectCollisionWithMovingBlock(unit)){
                    unit.setDead(true);
                    if(unit instanceof Fish)
                        stars.add(new SquashStar(unit.getBounds().x,unit.getBounds().y, StarColor.Blue,batch));
                    if(unit instanceof Shark)
                        stars.add(new SquashStar(unit.getBounds().x,unit.getBounds().y, StarColor.Grey,batch));
                }
                if(detectCollisionWithExplosion(unit)){
                    unit.setDead(true);
                    stars.add(new SquashStar(unit.getBounds().x,unit.getBounds().y, StarColor.Yellow,batch));
                }
            }

    }

    @SuppressWarnings("RedundantIfStatement")
    public boolean detectCollisionWithTheMapBorder(Rectangle bounds, Direction direction, Vector2 speed){
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
            boundsOnNextMove.setPosition(bounds.x ,bounds.y + bounds.y);
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

    public Block detectCollisionWithStaticBlock(Rectangle bounds, Direction direction, Vector2 speed){
        Rectangle boundsOnNextMove = new Rectangle(0,0,bounds.width,bounds.height);
        switch (direction){
            case    UP: boundsOnNextMove.setPosition(bounds.x,bounds.y + speed.y); break;
            case  DOWN: boundsOnNextMove.setPosition(bounds.x,bounds.y - speed.y); break;
            case  LEFT: boundsOnNextMove.setPosition(bounds.x  - speed.x,bounds.y); break;
            case RIGHT: boundsOnNextMove.setPosition(bounds.x  + speed.x,bounds.y); break;
        }
        for(Unit i : units){
            if(i instanceof Block && !i.isDead() &&boundsOnNextMove.overlaps(i.getBounds()))
                return (Block) i;
        }
        return null;
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




