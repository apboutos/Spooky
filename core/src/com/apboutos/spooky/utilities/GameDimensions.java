package com.apboutos.spooky.utilities;

/**
 * Contains all the dimensions used in the game. 
 * @author exophrenik
 *
 */
public class GameDimensions {
	
	/**
	 * After some testing 1320*720 seems like an ideal resolution for screen graphics and 160x160 for unit graphics.
	 * It is very good on large resolution devices.
	 */

	/**
	 * The total height of the screen in pixels. 720
	 */
	public static final int   screenHeight = 720;//480;
	/**
	 * The total width of the screen in pixels. 1320
	 */
	public static final int   screenWidth  = 1320; //720; 
	/**
	 * The width of each unit (block,player,enemy etc.) in pixels.
	 */
	public static final int   unitWidth    =  60;
	/**
	 * The height of each unit (block,player,enemy etc.) in pixels.
	 */
	public static final int   unitHeight   =  60;
	/**
	 * The minimum value the x axis is allowed to take in unit meters. 
	 * Anything smaller than this value will be off screen. (1 unit meter = 60 pixels).
	 */
	public static final int   xAxisMinumum =  -11;
	/**
	 * The maximum value the x axis is allowed to take in unit meters. 
	 * Anything larger than this value will be off screen. (1 unit meter = 60 pixels).
	 */
	public static final int   xAxisMaximum =   11;
	/**
	 * The minimum value the y axis is allowed to take in unit meters. 
	 * Anything smaller than this value will be off screen. (1 unit meter = 60 pixels).
	 */
	public static final int   yAxisMinimum =  -5;
	/**
	 * The maximum value the y axis is allowed to take in unit meters. 
	 * Anything larger than this value will be off screen. (1 unit meter = 60 pixels).
	 */
	public static final int   yAxisMaximum =   6;
}


/** NOTES:  X axis will have 22 possible spots. Since the default resolution will be 1320 width
 * 			each spot and thus each unit will have a width of 60
 * 
 *  		Y axis will have 12 possible spots (1 for score). Since the default resolution will
 *  		be 720 height each spot and thus each unit will have a height of 60.
 * 
 * 			60 is perfectly divisible by 6,5,4,3,2 this is useful for different unit speeds.
 * 
 * 			2560x1440
 */







