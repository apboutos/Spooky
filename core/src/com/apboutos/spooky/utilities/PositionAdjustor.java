package com.apboutos.spooky.utilities;

import com.apboutos.spooky.units.Enemy;
import com.apboutos.spooky.units.Unit;

public class PositionAdjustor {

    public void adjustPosition(Unit unit) {

        if (unit.isMoving() && !unit.isDying()) {
            switch (unit.getDirection()) {
                case UP:
                    unit.getBounds().y += unit.getSpeed().y;
                    if ((int) unit.getBounds().y % GameDimensions.unitHeight == 0)
                        unit.setMoving(false);
                    break;
                case DOWN:
                    unit.getBounds().y -= unit.getSpeed().y;
                    if ((int) unit.getBounds().y % GameDimensions.unitHeight == 0)
                        unit.setMoving(false);
                    break;
                case LEFT:
                    unit.getBounds().x -= unit.getSpeed().x;
                    if ((int) unit.getBounds().x % GameDimensions.unitWidth == 0)
                        unit.setMoving(false);
                    break;
                case RIGHT:
                    unit.getBounds().x += unit.getSpeed().x;
                    if ((int) unit.getBounds().x % GameDimensions.unitWidth == 0)
                        unit.setMoving(false);
                    break;
            }
            if (unit instanceof Enemy)
                ((Enemy) unit).setNumberOfBlocksMoved((int) (((Enemy) unit).getNumberOfBlocksMoved() + unit.getSpeed().x));
        }
    }
}

