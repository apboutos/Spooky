package com.apboutos.spooky.units;

import com.apboutos.spooky.utilities.Direction;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import lombok.Setter;

/**
 * A Unit is the base class of all the objects that appear inside a level.
 */
@Getter
@Setter
public abstract class Unit {

    protected Rectangle bounds;
    protected Direction direction;
    protected Vector2 speed;

    protected boolean isDead;
    protected boolean isMoving;
    protected boolean isPushing;
    protected boolean isPushed;

    protected boolean deathTimerStarted;
    protected long deathTimer;
}
