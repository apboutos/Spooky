package com.apboutos.spooky.utilities;

import com.apboutos.spooky.effects.SquashStar;
import com.apboutos.spooky.units.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.AllArgsConstructor;

import java.util.List;

import static com.apboutos.spooky.utilities.BlockType.*;
import static com.apboutos.spooky.utilities.Direction.reverseDirection;

@AllArgsConstructor
public class BehaviorController {

    private List<SquashStar> stars;
    private final SpriteBatch batch;

    private final CollisionDetector collisionDetector;

    public void determineUnitBehavior(Unit unit){
        if(unit instanceof Player){
            determinePlayerBehavior((Player) unit);
        }
        else if(unit instanceof Enemy){
            //determineEnemyBehavior((Enemy) unit);
        }
        else{
            determineBlockBehavior((Block) unit);
        }
    }


    private void determinePlayerBehavior(Player player){

       determinePlayerBehaviorOnCollisionWithMap(player);

       determinePlayerBehaviorOnCollisionWithStaticBlock(player);

       determinePlayerBehaviorOnCollisionWithMovingBlockOrEnemyOrExplosion(player);
    }

    private void determinePlayerBehaviorOnCollisionWithMap(Player player){
        if(collisionDetector.detectCollisionWithTheMapBorderOnNextMove(player.getBounds(),player.getDirection(),player.getSpeed())){
            System.out.println("Collided with map");
            player.stop();
        }
    }

    private void determinePlayerBehaviorOnCollisionWithStaticBlock(Player player){
        if(collisionDetector.detectCollisionWithStaticBlockOnNextMove(player.getBounds(),player.getDirection(),player.getSpeed()) != null){
            player.stop();
        }
    }

    private void determinePlayerBehaviorOnCollisionWithMovingBlockOrEnemyOrExplosion(Player player){
        if(collisionDetector.detectCollisionWithMovingBlock(player) || collisionDetector.detectCollisionWithEnemy(player) || collisionDetector.detectCollisionWithExplosion(player)){
            if(!player.isDying())
                stars.add(new SquashStar(player.getBounds().x,player.getBounds().y, StarColor.Yellow,batch));
            //if(!player.isDying())
                player.kill();
        }
    }

    private void determineBlockBehavior(Block block){

            determineBlockBehaviorOnBeingPushed(block);

            determineBlockBehaviorOnCollisionWithMap(block);

            determineBlockBehaviorOnCollisionWithStaticBlock(block);

            determineBlockBehaviorOnCollisionWithMovingBlock(block);

    }

    private void determineBlockBehaviorOnBeingPushed(Block block){

            if(block.isPushed()){
                if(blockCanMove(block))
                    block.move();
                else
                    block.kill();

                block.setPushed(false);
            }
    }

    private void determineBlockBehaviorOnCollisionWithStaticBlock(Block block){
        Block objectOfCollision = collisionDetector.detectCollisionWithStaticBlockOnNextMove(block);
        if(objectOfCollision != null)
            switch (block.getType()){
                case Standard: block.stop(); break;
                case Dynamite:
                case BigDynamite: block.explode(); break;
                case Bouncing:
                case BigBouncing: block.bounce(); break;
                case Diamond: if(objectOfCollision.getType() == Diamond) block.merge(); else block.stop(); break;
            }
    }

    private void determineBlockBehaviorOnCollisionWithMovingBlock(Block block){
        if(collisionDetector.detectCollisionWithMovingBlockOnNextMove(block) != null)
            block.freeBounce();
    }

    private void determineBlockBehaviorOnCollisionWithMap(Block block){
        if(collisionDetector.detectCollisionWithTheMapBorderOnNextMove(block.getBounds(),block.getDirection(),block.getSpeed()))
            switch (block.getType()){
                case Dynamite:
                case BigDynamite: block.explode(); break;
                case Standard:
                case Diamond: block.stop(); break;
                case Bouncing:
                case BigBouncing: block.bounce(); break;
            }
    }

    private boolean blockCanMove(Block block){
        return  !collisionDetector.detectCollisionWithTheMapBorderOnNextMove(block.getBounds(),block.getDirection(),block.getSpeed()) &&
                collisionDetector.detectCollisionWithStaticBlockOnNextMove(block) == null; //&&
                //collisionDetector.detectCollisionWithMovingBlockOnNextMove(block) == null;
    }

    public void determineEnemyBehavior(Enemy enemy){

        determineEnemyBehaviorOnCollisionWihMap(enemy);

        determineEnemyBehaviorOnCollisionWithStaticBlock(enemy);

        determineEnemyBehaviorOnCollisionWithMovingBlockOrExplosion(enemy);

        switch (enemy.getEnemyType()){
            case Fish: determineFishActions(enemy); break;
            case Shark: determineSharkActions(enemy); break;
        }
    }

    private void determineEnemyBehaviorOnCollisionWihMap(Enemy enemy){
        if(collisionDetector.detectCollisionWithTheMapBorderOnNextMove(enemy.getBounds(),enemy.getDirection(),enemy.getSpeed())){
            //System.out.println("enemy collided with border");
            enemy.stopDueToCollisionWithMap();
        }
    }

    private void determineEnemyBehaviorOnCollisionWithStaticBlock(Enemy enemy){
        Block objectOfCollision = collisionDetector.detectCollisionWithStaticBlockOnNextMove(enemy.getBounds(),enemy.getDirection(),enemy.getSpeed());
        if( objectOfCollision != null){
            System.out.println("enemy collided with block");
            enemy.stopDueToCollisionWithBlock(objectOfCollision);
        }
    }

    private void determineEnemyBehaviorOnCollisionWithMovingBlockOrExplosion(Enemy enemy){
        if(collisionDetector.detectCollisionWithMovingBlock(enemy) || collisionDetector.detectCollisionWithExplosion(enemy) && !enemy.isDeathTimerStarted()){
            System.out.println("enemy collided with moving");
            enemy.kill();
            switch (enemy.getEnemyType()){
                case Fish:   stars.add(new SquashStar(enemy.getBounds().x,enemy.getBounds().y, StarColor.Blue,batch)); break;
                case Shark:  stars.add(new SquashStar(enemy.getBounds().x,enemy.getBounds().y, StarColor.Grey,batch)); break;
            }
        }
    }

    //TODO
    private void determineSharkActions(Enemy shark){

    }

    /**
     * Determines the actions of the fish according to the following rules: <br>
     * <br>1. The fish changes direction randomly after moving 4 blocks. <br>
     * <br>2. The fish changes direction randomly after colliding with the map. <br>
     * <br>3. When the fish collides with a static standard block it has a 50% chance to push it
     *    or a 50% chance to randomly change direction.
     */
    private void determineFishActions(Enemy fish){

        fish.setMoving(true);
        if(fish.getNumberOfBlocksMoved()%400 == 0){
            plotNewCourse(fish);
        }
        if(fish.isCollidedWithMap()){
            plotNewCourse(fish);
            fish.setCollidedWithMap(false);
        }
        if(fish.isCollidedWithBlock()){
            if(fish.getLastBlockCollidedWith().getType() == Standard
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

        if(collisionDetector.detectCollisionWithTheMapBorderOnNextMove(enemy.getBounds(),direction,enemy.getSpeed()))
            return false;
        if(collisionDetector.detectCollisionWithStaticBlockOnNextMove(enemy.getBounds(),direction,enemy.getSpeed()) != null)
            return false;
        return true;
    }



    public int produceRandomInRange(int start, int end){
        return ((int)Math.round(Math.random()*100))%(end - start + 1) + start;
    }
}
