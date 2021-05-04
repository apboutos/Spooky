package com.apboutos.spooky.units;

import com.apboutos.spooky.utilities.Direction;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * A Unit is the base class of all the objects that appear inside a level.
 */
@Getter
@Setter
public abstract class Unit {

    protected Rectangle bounds = null;
    protected Direction direction = Direction.LEFT;
    protected Vector2 speed = null;

    protected boolean isMoving = false;
    protected boolean isPushing = false;
    protected boolean isPushed = false;

    protected boolean deathTimerStarted = false;
    protected long deathTimer = 0;
    protected float deathDuration;

    public boolean isDead(){
        return deathTimerStarted && TimeUtils.timeSinceMillis(deathTimer) > deathDuration;
    }

    public boolean isDying(){
        return deathTimerStarted;
    }
}
