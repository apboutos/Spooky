package com.apboutos.spooky.utilities;

/**
 * This enumeration represents the type of the enemy unit. Each enemy type has different properties
 * ranging from textures and animation to speed and AI. Each enemy unit has a normal and a super mode.
 * Units go in super mode if the level's timer expires and the player has not completed it. Enemies 
 * that can push blocks must focus first, which means the must stand still for a certain amount of time
 * ,facing the block before they push it. The amount of time they need to spend before pushing the block
 * depends of their type. 
 * 
 * <pre>
 * Fish:  The most basic enemy. It has a speed of 2 and it is aimlessly wandering 
 *        around. Ocationaly it can push a standard block after spending 3 seconds
 *        focusing. In super mode the fish has a speed of 4 and has to spend only
 *        1.5 seconds focusing.
 *       
 * Shark: The shark is normally a slow enemy with a basic speed of 2. Alas if the
 *        player gets in the line of sight of a shark, the shark's speed increases
 *        to 8 and he moves to the player's direction, until he kills the player
 *        or he loses sight of him. Sharks cannot push blocks. In super mode the
 *        shark's basic speed doubles to 4 but his hunting speed (8) remains the
 *        same.
 *
 * Starfish: The starfish is clever but cowardly enemy. It has a basic speed of 4 
 *           just like the player. The starfish will will try to kill the player
 *           by pushing blocks. It can push both standard and bouncing blocks. In 
 *           normal mode it can push a block after 1s of focusing while in super
 *           mode it can push it instantly. 
 *           
 * Clamp: The clamp is a stationary enemy thus is has a basic speed of 0. If the
 *        player is in a Clamp's line of sight it will throw pearls towards him.
 *        Pearls can kill the player and they can pass through enemies but not 
 *        through blocks. A Clamp throws a pearl every 3 seconds and every 1.5
 *        seconds if it is in super mode. Unlike other enemies Clamps will be
 *        destroyed if the collide with the player.
 *        
 * Squid: 
 * </pre>
 * @author exophrenik
 *
 */
public enum EnemyType {

	Fish,
	Shark,
	Starfish,
	Watersnake,
	Clamp,
	Squid,
	Octopus,
	Medusa
}
