package com.apboutos.spooky.utilities;

/**
 * This enumeration represents the type of each block. Depending on it's type a block
 * might have different properties.
 * 
 * <pre> Standard: A normal block. Can be pushed by both player and enemies and can
 *           also be destroyed.
 *           
 * Bouncing: A bouncing block is just like a standard block except that when it
 *           gets pushed it will bounce back after it reaches the end of the map or 
 *           collides with another block. 
 *           
 * BigBouncing: Just like a bouncing block. The only difference is the number of 
 *              bounces. A bigbouncing block will bounce twice instead of once.
 *              
 * Dynamite: A dynamite block will create an explosion when it collides with other blocks.
 *   	   The range of the explosion is 1 which means it will affects only the blocks
 *           around the collision block. If a players tries to push a dynamite block and it
 *           cannot move, it's fuse will be lighten instead and the block will explode in
 *           3 seconds.
 * 
 * BigDynamite: Just like a dynamite block but with a range of 2 instead of 1.
 * 
 * </pre>
 * @author exophrenik
 *
 */
public enum BlockType {
	Standard,
	Bouncing,
	BigBouncing,
	Dynamite,
	BigDynamite,
	Diamond
}
