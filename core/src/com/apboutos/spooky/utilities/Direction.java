package com.apboutos.spooky.utilities;

/**
 * Basic enumeration used to represent a unit's facing direction.
 * A unit can be facing UP,DOWN,LEFT or RIGHT.
 * 
 * @author exophrenik
 *
 */
@SuppressWarnings("DuplicatedCode")
public enum Direction {

	UP,
	DOWN,
	LEFT,
	RIGHT;

	public static Direction reverseDirection(Direction direction){
		switch (direction){
			case UP: return DOWN;
			case DOWN: return UP;
			case LEFT: return RIGHT;
			default: return LEFT;
		}
	}

	public static Direction shiftClockwise(Direction direction){
		switch (direction){
			case UP: return RIGHT;
			case DOWN: return LEFT;
			case LEFT: return UP;
			default: return DOWN;
		}
	}

	public static Direction shiftCounterClockwise(Direction direction){
		switch (direction){
			case UP: return LEFT;
			case DOWN: return RIGHT;
			case LEFT: return DOWN;
			default: return UP;
		}
	}
}
