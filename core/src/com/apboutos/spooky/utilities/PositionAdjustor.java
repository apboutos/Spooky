package com.apboutos.spooky.utilities;

import com.apboutos.spooky.units.Player;
import com.apboutos.spooky.units.Unit;
import com.badlogic.gdx.utils.TimeUtils;

public class PositionAdjustor {

    public void adjustPosition(Unit unit){

        if(unit instanceof Player){

            if(unit.isIAmDead()){
                unit.setIAmMoving(false);

                if (!unit.isDeathTimerStarted())
                {
                    startDeathTimer(unit);
                }
            }
            else if (unit.isIAmMoving()) {

                switch (unit.getDirection()) {
                    case UP: unit.getBounds().y += unit.getSpeed().y;
                             if ((int)unit.getBounds().y%GameDimensions.unitHeight == 0)
                                 unit.setIAmMoving(false);
                             break;
                    case DOWN:  unit.getBounds().y -= unit.getSpeed().y;
                             if ((int)unit.getBounds().y%GameDimensions.unitHeight == 0)
                                 unit.setIAmMoving(false);
                             break;
                    case LEFT: unit.getBounds().x -= unit.getSpeed().x;
                             if ((int)unit.getBounds().x%GameDimensions.unitWidth == 0)
                                 unit.setIAmMoving(false);
                             break;
                    case RIGHT:  unit.getBounds().x += unit.getSpeed().x;
                             if ((int)unit.getBounds().x%GameDimensions.unitWidth == 0)
                                 unit.setIAmMoving(false);
                             break;
                }

            }

        }
    }

    private void startDeathTimer(Unit unit){
        unit.setDeathTimer(TimeUtils.millis());
        unit.setDeathTimerStarted(true);
    }

}
