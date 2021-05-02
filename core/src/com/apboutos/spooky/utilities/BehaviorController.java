package com.apboutos.spooky.utilities;

import com.apboutos.spooky.units.Unit;
import com.apboutos.spooky.units.enemy.Enemy;
import com.apboutos.spooky.units.enemy.Fish;
import com.apboutos.spooky.units.enemy.Shark;
import lombok.AllArgsConstructor;

import static com.apboutos.spooky.utilities.Direction.reverseDirection;

@AllArgsConstructor
public class BehaviorController {

    private final CollisionDetector collisionDetector;

    public void determineEnemyBehavior(Unit enemy){
        if(enemy instanceof Enemy)
        switch (((Enemy)enemy).getEnemyType()){
            case Fish: determineFishBehavior((Fish) enemy);
            case Shark: determineSharkBehavior((Shark) enemy);
        }
    }

    private void determineSharkBehavior(Shark shark){

    }

    /**
     * Determines the actions of the fish according to the following rules: <br>
     * <br>1. The fish changes direction randomly after moving 4 blocks. <br>
     * <br>2. The fish changes direction randomly after colliding with the map. <br>
     * <br>3. When the fish collides with a static standard block it has a 50% chance to push it
     *    or a 50% chance to randomly change direction.
     */
    private void determineFishBehavior(Fish fish){

        fish.setMoving(true);
        if(fish.getNumberOfBlocksMoved()%400 == 0){
            plotNewCourse(fish);
        }
        if(fish.isCollidedWithMap()){
            plotNewCourse(fish);
            fish.setCollidedWithMap(false);
        }
        if(fish.isCollidedWithBlock()){
            if(fish.getLastBlockCollidedWith().getBlockType() == BlockType.Standard
                    && !fish.isDeathTimerStarted()
                    && !fish.getLastBlockCollidedWith().isMoving()
                    && produceRandomInRange(0,1) == 0)

                fish.getLastBlockCollidedWith().kill();
            else
                plotNewCourse(fish);

            fish.setCollidedWithBlock(false);
        }


    }

    private void plotNewCourse(Enemy enemy){
        turnClockwiseOrCounterClockWiseIfPossibleElseReverse(enemy);
    }

    private void turnClockwiseOrCounterClockWiseIfPossibleElseReverse(Enemy enemy){
        int random = produceRandomInRange(0,1);
        Direction direction;
        if (random == 1) {
            direction = Direction.shiftClockwise(enemy.getDirection());
        }
        else {
            direction = Direction.shiftCounterClockwise(enemy.getDirection());
        }
        if (!enemyCanMoveTowardsDirection(enemy,direction)) {
            direction = Direction.reverseDirection(direction);
        }
        if (!enemyCanMoveTowardsDirection(enemy,direction)) {
            direction = reverseDirection(enemy.getDirection());
        }
        enemy.setDirection(direction);
    }

    private boolean enemyCanMoveTowardsDirection(Enemy enemy , Direction direction){

        if(collisionDetector.detectCollisionWithTheMapBorder(enemy.getBounds(),direction,enemy.getSpeed()))
            return false;
        if(collisionDetector.detectCollisionWithStaticBlock(enemy.getBounds(),direction,enemy.getSpeed()) != null)
            return false;
        return true;
    }

    public int produceRandomInRange(int start, int end){
        return ((int)Math.round(Math.random()*100))%(end - start + 1) + start;
    }
}
