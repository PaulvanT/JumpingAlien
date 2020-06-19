package jumpingalien.model;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.util.Sprite;

/**
 * A class that initializes new Mazubs.
 * 
 * @invar	The velocity of each Mazub must be a valid velocity for any Mazub.
 * 		|	CanHaveAsVelocity(getVelocity())
 * 
 * @invar	The acceleration of each Mazub must be a valid velocity for any Mazub.
 * 		|	CanHaveAsVelocity(getVelocity())
 * 
 * @invar	The sprites of each Mazub must be valid sprites for any Mazub.
 * 		|	isValidSprites(getSprites())
 * 
 * @invar	The time duration be valid time duration.
 * 		|	isValidTimeduration(timeduration)
 * 
 * @version	3.0
 * 
 * @author 	Arthur van Meerbeeck, 2e Bachelor Burgerlijk Ingenieur, Computerwetenschappen-Elektrotechniek
 * 
 * @author	Paul van Tieghem de Ten Berghe, 2e Bachelor Burgerlijk Ingenieur, Elektrotechniek-Computerwetenschappen
 * 
 * Github repository: https://github.com/KUL-ogp/ogp1819-project-van-meerbeeck-van-tieghem-de-ten-berghe.git
 */
public class Mazub extends GameObject implements HorizontalMove, VerticalMove {
	/**
	 * Initializing a new alien Mazub with given (pixel)position and sprites.
	 * 
	 * @param  	pixelposition
	 *         	The pixel position (in centimeters) for this new Mazub.
	 * @param 	position
	 *         	The actual position (in meters) for this new Mazub.              
	 * @param 	sprites
	 * 			The sprites for this new Mazub
	 * @post 	The pixel position of this new Mazub is equal to the given pixel position.
	 * 		|	new.getPixelPosition() == pixelposition
	 * @post 	The position of this new Mazub is equal to the given position.
	 * 		|	new.getPosition() == position
	 * @post 	The sprites of this new Mazub is equal to the given sprites.
	 * 		|	new.getSprites() == sprites
	 * @post	The hitpoints of this Mazub are set to 100
	 * 		|	new.getHitPoints() == 100
	 */
	public Mazub(int[] pixelposition,double[] position,Sprite[] sprites) {
		
		this.setNewPosition(position);
		this.setPixelPosition(pixelposition);
		this.setSprites(sprites);
		this.setHitpoints(100);
	}

	/*******************
	 * HORIZONTAL MOVE *
	 *******************/

	/**
 	 * Return the horizontal position in m of this Mazub.
 	 */
	@Override @Basic
	public double getHorizontalPosition() {
		return this.horizontalPosition;
	}

	/**
 	 * Set the horizontal position of this Mazub to the given horizontal position.
 	 * 
 	 * @param  	horizontalPosition
 	 * 			The given horizontal position.
 	 * @post   	The new horizontal position of this Mazub is set to the given horizontal position
 	 *       | 	new.getHorizontalPosition == horizontalPosition;
 	 * @throws	IllegalArgumentException
 	 * 		 | 	!canHaveAsHorizontalPosition(horizontalPosition
 	 */
	@Override @Raw
	public void setHorizontalPosition(double horizontalPosition) throws IllegalArgumentException {
		if (!this.canHaveAsHorizontalPosition(horizontalPosition)) throw new IllegalArgumentException();
		this.horizontalPosition = horizontalPosition;
	}
	
	/**
 	 * Return the horizontal velocity in m/s of this Mazub.
 	 */
	@Override @Basic
	public double getHorizontalVelocity() {
		return this.horizontalVelocity;
	}

	/**
 	 * Check whether the given horizontal velocity is a valid horizontal velocity for any Mazub.
 	 *  
 	 * @param  	horizontalVelocity
 	 *         	The horizontal velocity to check.
 	 * @return 	The absolute value of the horizontal velocity should not exceed the maximal horizontal velocity 
 	 * 			nor be less than the minimal horizontal velocity, or should be zero
 	 *       | 	result == (((Math.abs(horizontalVelocity) <= getMaxHorizontalVelocity()) && 
	 *		 |	((Math.abs(horizontalVelocity) >= getMinHorizontalVelocity())))
	 *		 |	|| horizontalVelocity == 0.0)
 	 */
	@Override
	public boolean isValidHorizontalVelocity(double horizontalVelocity) {
		return (((Math.abs(horizontalVelocity) <= getMaxHorizontalVelocity()) && 
				((Math.abs(horizontalVelocity) >= getMinHorizontalVelocity())))
				|| horizontalVelocity == 0.0);
	}

	/**
 	 * Set the horizontal velocity of this Mazub to the given horizontal velocity.
 	 * 
 	 * @param  	horizontalVelocity
 	 * 			The given horizontal velocity.
 	 * @post   	If the given horizontal velocity is a valid horizontal velocity for this Mazub, the given
 	 * 			horizontal velocity will be set to it. Else the horizontal velocity will be set to the maximal 
 	 * 			or minimal velocity, or zero, according to it's value
 	 *       | 	if (isValidHorizontalVelocity(horizontalVelocity)
 	 *       |   	then new.getHorizontalVelocity == horizontalVelocity
 	 *       | 	else if (Math.abs(horizontalVelocity) > getMaxHorizontalVelocity())
 	 *       |  	then new.getHorizontalVelocity == getOrientation()*getMaxHorizontalVelocity()
 	 *       | 	else if (Math.abs(horizontalVelocity) < getMinHorizontalVelocity())
 	 *       |  	then new.getHorizontalVelocity == getOrientation()*getMinHorizontalVelocity()
 	 *       | 	else
 	 *       |  	then new.getHorizontalVelocity == 0.0
 	 */
	@Override @Raw
	public void setHorizontalVelocity(double horizontalVelocity) {
		if (this.isValidHorizontalVelocity(horizontalVelocity))
			this.horizontalVelocity = horizontalVelocity;
		else if (Math.abs(horizontalVelocity) > getMaxHorizontalVelocity())
			this.horizontalVelocity = getOrientation()*getMaxHorizontalVelocity();
		else if (Math.abs(horizontalVelocity) < getMinHorizontalVelocity())
			this.horizontalVelocity = getOrientation()*getMinHorizontalVelocity();
		else 
			this.horizontalVelocity = 0.0;
	}
	
	/**
 	 * Return the maximal horizontal velocity in m/s of this Mazub.
 	 */
	@Override @Basic
	public double getMaxHorizontalVelocity() {
		return this.maxHorizontalVelocity;
	}
	
	/**
 	 * Set the maximal horizontal velocity of this Mazub to the given maximal horizontal velocity.
 	 * 
 	 * @param  	maxHorizontalVelocity
 	 * 			The given maximal horizontal velocity.
 	 * @post   	The maximal horizontal velocity is set to the given maximal horizontal velocity
 	 *       | 	new.getMaxHorizontalVelocity == maxHorizontalVelocity;
 	 */
	public void setMaxHorizontalVelocity(double maxHorizontalVelocity) {
		this.maxHorizontalVelocity = maxHorizontalVelocity;
	}
	
	/**
 	 * Return the minimal horizontal velocity in m/s of this Mazub.
 	 */
	@Basic
	public double getMinHorizontalVelocity() {
		return this.minHorizontalVelocity;
	}
	
	/**
 	 * Set the minimal horizontal velocity of this Mazub to the given minimal horizontal velocity.
 	 * 
 	 * @param  	minHorizontalVelocity
 	 * 			The given minimal horizontal velocity.
 	 * @post   	The minimal horizontal velocity is set to the given minimal horizontal velocity
 	 *       | 	new.getMinHorizontalVelocity == minHorizontalVelocity;
 	 */
	public void setMinHorizontalVelocity(double minHorizontalVelocity) {
		this.minHorizontalVelocity = minHorizontalVelocity;
	}

	/**
 	 * Return the horizontal acceleration in m/s^2 of this Mazub.
 	 */
	@Override @Basic @Raw
	public double getHorizontalAcceleration() {
		return this.horizontalAcceleration;
	}


	/**
 	 * Set the horizontal acceleration of this Mazub to the given horizontal acceleration.
 	 * 
 	 * @param  	horizontalAcceleration
 	 * 			The given horizontal acceleration.
 	 * @post   	The horizontal acceleration is set to the given horizontal acceleration
 	 *       | 	new.getHorizontalAcceleration == horizontalAcceleration;
 	 */
	@Override
	public void setHorizontalAcceleration(double horizontalAcceleration) {
		this.horizontalAcceleration = horizontalAcceleration;
		
	}
	
	/*****************
	 * VERTICAL MOVE *
	 *****************/

	/**
 	 * Return the vertical position in m of this Mazub.
 	 */
	@Override @Basic
	public double getVerticalPosition() {
		return this.verticalPosition;
	}

	/**
 	 * Set the vertical position of this Mazub to the given vertical position.
 	 * 
 	 * @param  	verticalPosition
 	 * 			The given vertical position.
 	 * @post   	The new vertical position of this Mazub is set to the given vertical position
 	 *       | 	new.getVerticalPosition == verticalPosition;
 	 * @throws	IllegalArgumentException
 	 * 		 | 	!canHaveAsVerticalPosition(verticalPosition)
 	 */
	@Override @Raw
	public void setVerticalPosition(double verticalPosition) throws IllegalArgumentException {
		if (!this.canHaveAsVerticalPosition(verticalPosition)) throw new IllegalArgumentException();
		this.verticalPosition = verticalPosition;
	}

	/**
 	 * Return the vertical velocity in m/s of this Mazub.
 	 */
	@Override @Basic
	public double getVerticalVelocity() {
		return this.verticalVelocity;
	}

	/**
 	 * Check whether the given vertical velocity is a valid vertical velocity for any Mazub.
 	 *  
 	 * @param  verticalVelocity
 	 *         The vertical velocity to check.
 	 * @return The absolute value of the vertical velocity should not exceed the maximal vertical velocity
 	 *       | result == (Math.abs(verticalVelocity) <= getMaxVerticalVelocity())
 	 */
	@Override
	public boolean isValidVerticalVelocity(double verticalVelocity) {
		return (verticalVelocity <= getMaxVerticalVelocity());
	}

	/**
 	 * Set the vertical velocity of this Mazub to the given vertical velocity.
 	 * 
 	 * @param  	verticalVelocity
 	 * 			The given vertical velocity.
 	 * @post   	If the given vertical velocity is a valid vertical velocity, the given vertical velocity will be 
 	 * 			set to it. Else the vertical velocity will be set to the maximal vertical velocity
 	 *       | 	if (this.isValidVerticalVelocity(verticalVelocity))
 	 *       |   	then new.getVerticalVelocity == verticalVelocity
 	 *       | 	else if (verticalVelocity > getMaxVerticalVelocity())
 	 *       |  	then new.getVerticalVelocity == getMaxVerticalVelocity()
 	 */
	@Override @Raw
	public void setVerticalVelocity(double verticalVelocity) {
		if (this.isValidVerticalVelocity(verticalVelocity))
			this.verticalVelocity = verticalVelocity;
		else if (verticalVelocity > getMaxVerticalVelocity())
			this.verticalVelocity = getMaxVerticalVelocity();
	}

	/**
 	 * Return the maximal vertical velocity in m/s of this Mazub.
 	 */
	@Override @Basic
	public double getMaxVerticalVelocity() {
		return 8.0;
	}

	/**
 	 * Return the vertical acceleration in m/s^2 of this Mazub.
 	 */
	@Override @Basic @Raw
	public double getVerticalAcceleration() {
		return this.verticalAcceleration;
	}

	/**
 	 * Set the vertical acceleration of this Mazub to the given horizontal acceleration.
 	 * 
 	 * @param  	verticalAcceleration
 	 * 			The given vertical acceleration.
 	 * @post   	The vertical acceleration is set to the given vertical acceleration
 	 *       | 	new.getVerticalAcceleration == verticalAcceleration;
 	 */
	@Override
	public void setVerticalAcceleration(double verticalAcceleration) {
		this.verticalAcceleration = verticalAcceleration;	
	}
	
	/**************
	 * TOTAL MOVE *
	 **************/

	/**
 	 * Return the position two dimensions of this Mazub.
 	 */
	@Override @Basic
	public double[] getPosition() {
		return new double[] {getHorizontalPosition(), getVerticalPosition()};
	}
	
	/**
 	 * Set the position of this Mazub to the given position.
 	 * 
 	 * @param  	position
 	 * 			The given position.
 	 * @post   	The new horizontal and vertical position of this Mazub is set to the given position
 	 *       | 	new.getPosition == position;
 	 */
	@Override 
	public void setHorAndVerPosition(double[] position) {
		setHorizontalPosition(position[0]);
		setVerticalPosition(position[1]);
	}
	
	/**
 	 * Return the velocity in two dimensions in m/s of this Mazub.
 	 */
	@Override @Basic
	public double[] getVelocity() {
		return new double[] {getHorizontalVelocity(), getVerticalVelocity()};
	}
	
	/**
 	 * Set the velocity of this Mazub to the given velocity.
 	 * 
 	 * @param  	velocity
 	 * 			The given velocity.
 	 * @post   	The new horizontal and vertical velocity of this Mazub is set to the given velocity
 	 *       | 	new.getVelocity == velocity;
 	 */
	@Override @Raw
	public void setVelocity(double[] velocity) {
		setHorizontalVelocity(velocity[0]);
		setVerticalVelocity(velocity[1]);
	}
	
	/**
 	 * Return the acceleration in two dimensions in m/s^2 of this Mazub.
 	 */
	@Override @Basic
	public double[] getAcceleration() {
		return new double[] {getHorizontalAcceleration(), getVerticalAcceleration()};
	}
	
	/**
 	 * Set the acceleration of this Mazub to the given acceleration.
 	 * 
 	 * @param  	acceleration
 	 * 			The given acceleration.
 	 * @post   	The new horizontal and vertical acceleration of this Mazub is set to the given acceleration
 	 *       | 	new.getAcceleration == acceleration;
 	 */
	@Override
	public void setAcceleration(double[] acceleration) {
		setHorizontalAcceleration(acceleration[0]);
		setVerticalAcceleration(acceleration[1]);
	}

	/**
 	 * Return the orientation of this Mazub.
 	 */
	@Override @Basic
	public int getOrientation() {
		return this.orientation;
	}
	
	/**
	 * Check whether the given orientation is valid for this GameObject.
	 *  
 	 * @param  orientation
 	 *         The orientation to check.
 	 * @return The orientation should be one of the following integers: -1, 0 or +1
 	 *       | result == ((Math.abs(orientation) == 1) || orientation == 0)
	 */
	@Override
	public boolean isValidOrientation(int orientation) {
		return ((Math.abs(orientation) == 1) || orientation == 0);
	}

	/**
 	 * Set the orientation of this Mazub to the given orientation.
 	 * 
 	 * @param  	orientation
 	 * 			The given orientation.
 	 * @pre		The given orientation must be a valid orientation
 	 * 		 | 	isValidOrientation(orientation)
 	 * @post   	The new horizontal and vertical acceleration of this Mazub is set to the given acceleration
 	 *       | 	new.getAcceleration == acceleration;
 	 */
	@Override @Raw
	public void setOrientation(int orientation) {
		assert isValidOrientation(orientation);
		this.orientation = orientation;
	}


	/**
     * Variable registering the minimal horizontal velocity of this Mazub.
     */
	private double minHorizontalVelocity = 1.0;
	
	/**
     * Variable registering the maximal horizontal velocity of this Mazub.
     */
	private double maxHorizontalVelocity = 3.0;
	


//********************************************* RUNNING ********************************************************//
	
	/** 
	 * Start moving the new Mazub in the given orientation
	 * 
	 * @param 	orientation
	 * 		  	The orientation in which the new Mazub starts moving.
	 * @pre		The Mazub is not already running
	 * 		|	!isRunning()
	 * @pre		The Mazub is not dead
	 * 		|	!isDead()
	 * @effect 	The orientation of this new Mazub is set to the given orientation.
	 * 		|	setOrientation(orientation)
	 * @effect 	The horizontal velocity of this new Mazub is set to the minimum velocity
	 * 			and is given the sign of the orientation
	 * 		|	setHorizontalVelocity(getMinHorizontalVelocity()*orientation)
	 * @effect 	The horizontal acceleration of this new Mazub is set to RUNNING_ACCELERATION 
	 * 			and is given the sign of the orientation.
	 *		|	setHorizontalAcceleration(RUNNING_ACCELERATION*orientation)
	 */
	public  void startMove(int orientation) throws IllegalStateException {
		assert(!this.isRunning() || !this.isDead());
		elapsedTime = 0;
		counter = 0;
		setOrientation(orientation);
		setHorizontalVelocity(getMinHorizontalVelocity()*orientation);
		setHorizontalAcceleration(RUNNING_ACCELERATION*orientation);	
	}
	
	/** 
	 * Stop moving the Mazub.
	 * 
	 * @pre		The Mazub is running
	 * 		|	isRunning()
	 * @pre		The Mazub is not dead
	 * 		|	!isDead()
	 * @effect 	The horizontal velocity of this Mazub is set to 0.
	 * 		|	setHorizontalVelocity(0)
	 * @effect 	The horizontal acceleration of this  Mazub is set to 0.
	 *		|	setHorizontalAcceleration(0)
	 */
	public void endMove() throws IllegalStateException{
		assert(this.isRunning() || !this.isDead());
		elapsedTime = 0;
		counter = 0;
		setHorizontalVelocity(0);
		setHorizontalAcceleration(0);
	}
	
	/** 
	 * Check of the Mazub is running.
	 * 
	 * @return 	True if and only if the horizontal velocity doesn't equal 0.
	 * 		|	getHorizontalVelocity() != 0
	 */
	public boolean isRunning() {
		return (getHorizontalVelocity() != 0);
	}
	
	/**
	 * Variable storing the running acceleration.
	 */
	private final double RUNNING_ACCELERATION = 0.9;

//********************************************* JUMPING ********************************************************//
	
	/** 
	 * Start a new jump for the Mazub.
	 * 
	 * @effect 	Register that the Mazub is jumping.
	 * 		|	isJumping = true
	 * @effect 	The vertical velocity is set to 8.
	 * 		|	setVerticalVelocity(8.0)
	 * @effect 	The vertical acceleration is set to -10.
	 * 		| 	setVerticalAcceleration(-10.0)
	 * @throws 	IllegalStateException
	 * 			The Mazub is already jumping.
	 * 		|	isJumping
	 */
	public void startJump() throws IllegalStateException{
		if (isJumping || this.isDead()) throw new IllegalStateException();
		isJumping = true;
		setVerticalVelocity(8.0);
		setVerticalAcceleration(-10.0);
	}
	
	/** 
	 * End a jump for the Mazub.
	 * 
	 * @effect 	Register that the Mazub is not jumping.
	 * 		|	isJumping = false
	 * @effect 	The elapsedTime and counter are equal to zero.
	 * 		|	elapsedTime = 0
	 * 		| 	counter = 0
	 * @effect 	The vertical velocity is set to zero.
	 * 		|	setVerticalVelocity(0.0)
	 * @throws 	IllegalStateException
	 * 			The Mazub is not jumping.
	 * 		|	!isJumping
	 */
	public void endJump() throws IllegalStateException {
		if (!isJumping) throw new IllegalStateException();
		isJumping = false;
		elapsedTime = 0;
		counter = 0;
		if (getVerticalVelocity() > 0.0)
			setVerticalVelocity(0.0);
	}
	
	/**
	 * Boolean registering whether the Mazub is jumping or not.
	 */
	public boolean isJumping = false;
	
	/**
	 * Boolean registering whether the Mazub is falling or not.
	 * 
	 * @return 	The Mazub shouldn't be jumping and it's vertical position should be greater than 0
	 * 		  | return ((!isJumping) && (getVerticalPosition() > 0))
	 */
	public boolean isFalling() {
		if (this.getWorld() == null || restingOnSolidGround())
			return false;
		else
			return ((this.getVerticalVelocity() < 0) || (!isJumping));
	}
	
//********************************************* DUCKING ********************************************************//
	
	/**
	 * This will set the Mazub in ducking mode.
	 * 
	 * @effect 	The boolean 'isDucking' is set to true
	 * 		  |	isDucking = true
	 * @effect	The maximal horizontal velocity is set to 1.0 m/s
	 * 		  |	setMaxHorizontalVelocity(1.0)
	 * @effect 	If the Mazub is running, set it's speed to 1.0 m/s multiplied by it's orientation, so the Mazub 
	 * 			keeps running in the correct orientation.
	 * 		  | if (isRunning())
	 *		  |		then setHorizontalVelocity(getOrientation()*1.0)
	 */
	public void startDucking() {
		if (! this.isDead()) {
			isDucking = true;
			setHorizontalAcceleration(0.0);
			setMaxHorizontalVelocity(1.0);
			if (isRunning())
				setHorizontalVelocity(getOrientation()*1.0);
		}
		else 
			return;
	}
	/**
	 * Return whether the Mazub can end his duck or not.
	 * 
	 * @return	The Mazub may not overlap with impassable terrain or with an other object except plants when the Mazub 
	 * 			stands up.
	 * 		|	!this.OverlapsWithImpassableTerrain(this.getWorld())) && (!this.OverlapsWithOtherMazub(this.getWorld())
	 */
	public boolean canEndDucking() {
		
		isDucking = false;
		if ((!this.overlapsWithImpassableTerrain(this.getWorld())) && (!this.overlapsWithObjectNotPlant(this.getWorld()))) {
			isDucking = true;
			return true;
		}
		else {
			isDucking = true;
			return false;
		}	
	}
	
	/**
	 * This will end the Mazub's ducking mode if the mazub can end his duck and is positioned in a world.
	 * 
	 * @effect 	The boolean 'isDucking' is set to false
	 * 		  |	isDucking = false
	 * @effect 	The elapsedTime is reset to 0 seconds
	 * 		  | elapsedTime = 0
	 * @effect 	The counter is reset to 0
	 * 		  | counter = 0
	 * @effect	The maximal horizontal velocity is set to 3.0 m/s
	 * 		  |	setMaxHorizontalVelocity(3.0)
	 */
	public void endDucking() {
		if (this.canEndDucking() && this.getWorld()!=null) {
			isDucking = false;
			elapsedTime = 0;
			counter = 0;
			setMaxHorizontalVelocity(3.0);
			setHorizontalAcceleration(0.9*getOrientation());
		}
		else 
			isDucking = true;
	}
	
	/**
	 * Boolean registering whether the Mazub is Ducking or not.
	 */
	public boolean isDucking;
	

	
//********************************************* ADVANCETIME ********************************************************//

		
	/**
	 * Advance the time in the game for this Mazub partially with the given time duration.
	 * 
	 * @param 	timeDuration
	 * 			The given time duration with whom the method will advance the time	
	 * 
	 * @effect 	If the Mazub is dead, it's movement is stopped
	 * 		  |	stopMovingIfDead()
	 * 
	 * @effect	If the Mazub is dead, it's termination delay of 0.6 seconds is updated
	 * 		  |	terminateDeadObject(timeDuration)
	 * 
	 * @effect 	If the Mazub overlaps with plants, it will get hitpoints for eating it
	 * 		  |	eatPlants()
	 * 
	 * @effect 	If there are multiple sprites for one kind of movement, these will be updated every 75 ms
	 * 		  |	updateSprites(timeDuration)
	 * 
	 * @effect 	If the Mazub overlaps with features like water and magma, hitpoints are deducted 
	 * 		  |	overlappingWithFeatures(timeDuration)
	 * 
	 * @effect 	Check if the Mazub is colliding with impassable terrain, and if so adjust it's velocity and acceleration
	 * 		  |	checkImpassableTerrainCollision()
	 * 
	 * @effect 	Check if the Mazub is colliding with other GameObjects, and if so adjust it's velocity and acceleration
	 * 		  |	checkObjectCollision()
	 * 
	 * @effect	Check if the mazub collides with another GameObject and if so decrease it's hitpoints with the specified amount.
	 * 		  |	collisionWithObject(timeDuration)
	 * 
	 * @effect 	The position and velocity of this Mazub gets updated with the given time duration, 
	 * 			given it's current velocity and acceleration
	 * 		  |	updatePositionAndVelocity(timeDuration)
	 * 
	 * @effect 	If this Mazub is the player Mazub, check if he has won the game
	 * 		  |	checkIfPlayerHasWon()
	 */
	@Override
	public void advanceTimePartially(double timeDuration) {

		stopMovingIfDead();
		terminateDeadObject(timeDuration);
		eatPlants(timeDuration);
		updateSprites(timeDuration);
		overlappingWithFeatures(timeDuration);
		checkImpassableTerrainCollision();
		checkObjectCollision();
		collisionWithObject(timeDuration);
		updatePositionAndVelocity(timeDuration);
		checkIfPlayerHasWon();
		
	}	
	
	//********************************************* EATPLANTS ********************************************************//
	/**
	 * Let the Mazub eat all the plants with which he collides.
	 * 
	 * @effect	If the Mazub is in a world and overlaps with at least 1 plant, he will eat all the plants with which he overlaps for the first time
	 * 			or the plants with which he is overlapping longer than 0.6 seconds.
	 * 		|	if this.getWorld() != null
	 * 		|		then if this.OverlapsWithPlant(this.getWorld())
	 * 		|			then for plant in this.getOverlappingPlants(this.getWorld())
	 * 		|				if (plant.getTimeAfterCollision()>=0.6)
	 * 		|					this.eatingPlant(plant,this) && plant.setTimeAfterCollision(0.0)
	 * 		|				else plant.setTimeAfterCollision(plant.getTimeAfterCollision()+timeduration)
	 */
	public void eatPlants(double timeduration) {
		if (this.getWorld()!= null) {
			
			if (this.overlapsWithPlant(this.getWorld())) {
				
				for (Plant plant: this.getOverlappingPlants(this.getWorld())) {
					if (plant.getTimeAfterCollision()>=0.6) {
						this.eatingPlant(plant, this);
						plant.setTimeAfterCollision(0.0);
					}
					else 
						plant.setTimeAfterCollision(plant.getTimeAfterCollision()+timeduration);
				}
			}
		}
	}
	
	
	//********************************************* OVERLAPWITHFEATURES ***********************************************//

	/**
	 * Check how long the Mazub is overlapping with magma, gas or water and reduce his hit points with the right amount.
	 * @param dt 
	 * 		  the time duration to check if the Mazub is overlapping with magma or water.
	 * 
	 * @effect	The mazub needs to be located in a world.
	 * 		|	if this.getWorld == null
	 * 		|		return;
	 * @effect	if the Mazub is overlapping with magma his hit points will be immediately reduces by 50
	 * 			and from then on reduced by 50 hit points each 0.2 seconds it stays in the magma.
	 * 		|	if this.OverlapsWithMagma() then
	 * 		|		timeOverlappingMagma += dt
	 * 		|		timeOverlappingWater.equals(0.0)
	 * 		|		if timeOverlappingMagma >= 0.2 then
	 * 		|			this.setHitpoints(this.getHitpoints()-50)
	 * 		|			timeOverlappingMagma -= 0.2
	 * @effect 	If the Mazub is overlapping with gas (and not with magma) his hit points will be immediately reduces by 4 
	 * 			and from then on reduced by 4 hit points each 0.2 seconds it stays in the gas.
	 * 		|	if (timeOverlappingGas >= 0.2)
	 * 		|		this.setHitpoints(this.getHitpoints()-4)
	 * @effect 	if the Mazub is overlapping with water (and not with magma or gas) his hit points will be reduces by 2
	 * 			every 0.2 seconds it stays in the water.
	 * 		|	if this.OverlapsWithWater() then
	 *		|		timeOverlappingMagma = 0.2
	 *		|		timeOverlappingWater += dt
	 *		|		if timeOverlappingWater >= 0.2 then
	 *		|			this.setHitpoints(this.getHitpoints()-2)
	 *		|			timeOverlappingWater-=0.2
	 * @effect 	If the Mazub is not overlapping with water or magma the overlappingtimers will be set to their initial value.
	 * 		|	timeOverlappingMagma = 0.2
	 * 		|	timeOverlappinggGas  = 0.2
	 *		|	timeOverlappingWater = 0.0
	 */	
	public void overlappingWithFeatures(double dt) {
		if (this.getWorld() == null)
			return;
		
		if (this.overlapsWithMagma()) {
			timeOverlappingMagma += dt;
			timeOverlappingWater = 0.0;
			timeOverlappingGas = 0.2;
			if (timeOverlappingMagma >= 0.2) {
				this.setHitpoints(this.getHitpoints()-50);
				timeOverlappingMagma -= 0.2;
			}	
		}
		else if (this.overlapsWithGas()) {
			timeOverlappingGas+=dt;
			timeOverlappingMagma = 0.2;
			timeOverlappingWater = 0.0;
			if (timeOverlappingGas >= 0.2) {
				this.setHitpoints(this.getHitpoints()-4);
				timeOverlappingGas -= 0.2;
			}
		}
		else if (this.overlapsWithWater()) {
			timeOverlappingMagma = 0.2;
			timeOverlappingGas = 0.2;
			timeOverlappingWater += dt;
			if (timeOverlappingWater >= 0.2) {
				this.setHitpoints(this.getHitpoints()-2);
				timeOverlappingWater-=0.2;
			}
		}
		else {
			timeOverlappingMagma = 0.2;
			timeOverlappingWater = 0.0;
			timeOverlappingGas = 0.2;
			
		}
	}
	
	/**
	 * Variable registering the time Mazub is overlapping with magma.
	 */
	private double timeOverlappingMagma = 0.2;
	
	/**
	 * Variable registering the time Mazub is overlapping with gas.
	 */
	private double timeOverlappingGas = 0.2;
	
	/**
	 * Variable registering the time Mazub is overlapping with water.
	 */
	private double timeOverlappingWater = 0;
	
	
	
	//********************************************* UPDATE SPRITES ***************************************************//

	public void updateSprites(double timeDuration) {
	
		elapsedTime += timeDuration;
		counter = (int) (elapsedTime/0.075);
		
		if (isRunning() == false) {
			second += timeDuration;
			int current_orientation = this.getOrientation();
			
			if (second >= 1.0) {
				setOrientation(0);
				
				if (this.getWorld()!= null) {
					if (! this.canHaveAsPosition(this.getPosition()))
						this.setOrientation(current_orientation);
					
					else
						second = 0;
				}
				else 
					second = 0;
			}
		}
	}
	
	//***************************************** COLLISION WITH IMPASSABLE TERRAIN *************************************//

	/**
	 * Check whether the Mazub is colliding with impassable terrain and if so change his movement.
	 * 
	 * @effect The Mazub needs to be located in a world
	 * 		|	if this.getWorld() == null 
	 * 		|		then result == null
	 * @effect If the Mazub is colliding with impassable terrain to the right and 
	 * 			his horizontal velocity is greater than zero he will end his movement
	 * 		|	if this.CollidesWithITToTheRight() && this.getHorizontalVelocity()>0
	 *		|		then this.endmove()
	 * @effect	If the Mazub is colliding with impassable terrain to the left and 
	 * 			his horizontal velocity is less than zero he will end his movement
	 * 		|	if this.CollidesWithITToTheLeft() && this.getHorizontalVelocity()<0
	 *		|		then this.endmove()
	 * @effect	If the Mazub is colliding with impassable terrain at his top and 
	 * 			his vertical velocity is greater than zero, the Mazub will end his jump, his vertical velocity will be
	 * 			set to zero and his vertical acceleration will be set to -10.
	 * 		|	if this.CollidesWithITAtTheTop() && this.getVerticalVelocity()>0
	 * 		|		then this.isJumping = false
	 *		|			this.setVerticalVelocity(0.0)
	 *		|			this.setVerticalAcceleration(-10.0);
	 * @effect	If the Mazub is colliding with impassable terrain at his bottom and 
	 * 			his vertical velocity is less than zero, the Mazubs vertical velocity and vertical acceleration will be
	 * 			set to zero.
	 * 		|	if this.CollidesWithITAtTheBottom() && this.getVerticalVelocity()<0
	 * 		|		then this.setVerticalVelocity(0.0)
	 *		|			this.setVerticalAcceleration(0.0)
	 */
	public void checkImpassableTerrainCollision() {
		if (this.getWorld()==null) {
			return;
		}
		if (this.collidesWithITToTheRight() && this.getHorizontalVelocity()>0) {
			this.endMove();
			
			if (!this.restingOnSolidGround()) 
				this.setVerticalAcceleration(-10);
			
		}
		else if (this.collidesWithITToTheLeft() && this.getHorizontalVelocity()<0) {
			this.endMove();
			
			if (!this.restingOnSolidGround()) 
				this.setVerticalAcceleration(-10);
			
		}
		if (this.collidesWithITAtTheTop() && this.getVerticalVelocity()>0) {
			this.isJumping = false;
			this.setVerticalVelocity(0.0);
			this.setVerticalAcceleration(-10.0);
		}
		if (this.collidesWithITAtTheBottom() && this.getVerticalVelocity()<0) {
			this.setVerticalVelocity(0.0);
			this.setVerticalAcceleration(0.0);
		}
		
	}
	
	//***************************************** COLLISION WITH OTHER OBJECTS ********************************************//
	
	/**
	 * Check whether the Mazub is colliding with an other object and if so change his movement.
	 * 
	 * @effect The Mazub needs to be located in a world
	 * 		|	if this.getWorld() == null 
	 * 		|		then result == null
	 * @effect If the Mazub is colliding with an object to the right and 
	 * 			his horizontal velocity is greater than zero he will end his movement
	 * 		|	if this.CollidesWithanObjectToTheRight() && this.getHorizontalVelocity()>0
	 *		|		then this.endmove()
	 * @effect	If the Mazub is colliding with an object to the left and 
	 * 			his horizontal velocity is less than zero he will end his movement
	 * 		|	if this.CollidesWithanObjectToTheLeft() && this.getHorizontalVelocity()<0
	 *		|		then this.endmove()
	 * @effect	If the Mazub is colliding with an object at his top and 
	 * 			his vertical velocity is greater than zero, the Mazub will end his jump, his vertical velocity will be
	 * 			set to zero and his vertical acceleration will be set to -10.
	 * 		|	if this.CollidesWithObjectAtTheTop() && this.getVerticalVelocity()>0
	 * 		|		then this.isJumping = false
	 *		|			this.setVerticalVelocity(0.0)
	 *		|			this.setVerticalAcceleration(-10.0);
	 * @effect	If the Mazub is colliding with an object at his bottom and 
	 * 			his vertical velocity is less than zero, the Mazubs vertical velocity and vertical acceleration will be
	 * 			set to zero.
	 * 		|	if this.CollidesWithObjectAtTheBottom() && this.getVerticalVelocity()<0
	 * 		|		then this.setVerticalVelocity(0.0)
	 *		|			this.setVerticalAcceleration(0.0)
	 */
	public void checkObjectCollision() {
		if (this.getWorld()== null) 
			return;

		if (this.collidesWithanObjectToTheRight() && this.getHorizontalVelocity()>0) {
			this.endMove();
		}
		else if (this.collidesWithanObjectToTheLeft() && this.getHorizontalVelocity()<0) {
			this.endMove();
		}
		if (this.collidesWithanObjectAtTheTop() && this.getVerticalVelocity()>0) {
			this.isJumping = false;
			this.setVerticalVelocity(0.0);
			this.setVerticalAcceleration(-10.0);
		}
		if (this.collidesWithanObjectAtTheBottom() && this.getVerticalVelocity()<0) {
			this.setVerticalVelocity(0.0);
			this.setVerticalAcceleration(0.0);
		}
		
	}
	
	//***************************************** HITPOINT REDUCTION BECAUSE OF COLLISION ********************************************//
	/**
	 * Check if the Mazub is colliding with specific objects and if so reduce his hit points with the specified amount.
	 * @param time 
	 * 		  the time which is advanced.
	 * @effect the method will check all the colliding not dead object with which Mazub collides if it's not dead itself.
	 * 		|	for object in this.allCollidingObjects(this.getWorld())
	 * 		|		if (!((GameObject) object).isDead() && object != this && !this.isDead())
	 * @effect The Mazub will only lose hit points if the previous collision with the object is longer ago than 0.6 seconds.
	 * 		|	if !(((GameObject) object).getTimeAfterCollision()>=0.6)
	 * 		|		((GameObject) object).setTimeAfterCollision(((GameObject) object).getTimeAfterCollision()+time)
	 * @effect if the Mazub collides with a game object of type shark it hit points will be reduces by 50 and the timer 
	 * 			for the time after collision will be reset.
	 * 		|	if (object instanceof Shark)
	 * 		|		this.setHitpoints(this.getHitpoints()-50)
	 * 		|		((Shark) object).setTimeAfterCollision(0.0)
	 * @effect if the Mazub collides with a game object of type Slime it hit points will be reduces by 20 and the timer 
	 * 			for the time after collision will be reset.
	 * 		|	if (object instanceof Slime)
	 * 		|		this.setHitpoints(this.getHitpoints()-20)
	 * 		|		((Shark) object).setTimeAfterCollision(0.0)
	 */
	public void collisionWithObject(double time) {
		if (this.getWorld()==null)
			return;
		
		else {
			for (Object object: this.allCollidingObjects(this.getWorld())) {
				if (!((GameObject) object).isDead() && object != this && !this.isDead()) {
					
					if (!(((GameObject) object).getTimeAfterCollision()>=0.6)) {
						((GameObject) object).setTimeAfterCollision(((GameObject) object).getTimeAfterCollision()+time);
					}
					else {
						if (object instanceof Shark) {
							this.setHitpoints(this.getHitpoints()-50);
							((Shark) object).setTimeAfterCollision(0.0);
						}
						else if (object instanceof Slime) {
							this.setHitpoints(this.getHitpoints()-20);
							((Slime) object).setTimeAfterCollision(0.0);
						}
					}
					
				}
			}
		}
	}
	
	
	//***************************************** UPDATE POSITION AND VELOCITY ********************************************//
	/**
	 * Update the position and velocity of the Mazub after the given time duration is advanced.
	 * 
	 * @param timeDuration
	 * 		  The time duration for which the mazub will be advanced.
	 * @effect	If the Mazub is falling, his vertical acceleration will be set to -10.
	 * 		|	if this.isFalling()
	 * 		|		then setVerticalAcceleration(-10);
	 * @effect If the Mazub is moving in a vertical direction his vertical position will be set to the new vertical position
	 * 		   and his vertical velocity will be updated.
	 * 		|	if this.isFalling() or this.isJumping
	 * 		|		then ver_pos.equals(getVerticalPosition() + getVerticalVelocity()*timeDuration + 0.5*getVerticalAcceleration()*(Math.pow(timeDuration,2)))
	 *		|			 this.setVerticalVelocity((getVerticalVelocity() + getVerticalAcceleration()*timeDuration)
	 * @effect If the Mazub is moving in a horizontal direction his horizontal position will be set to the new horizontal position
	 * 			and his vertical velocity will be updated.
	 * 		|	if this.isrunningn()
	 * 		|		then hor_pos.equals(getHorizontalPosition() + getHorizontalVelocity()*timeDuration + 0.5*getHorizontalAcceleration()*(Math.pow(timeDuration,2)))
	 * 		|			this.setHorizontalVelocity((getHorizontalVelocity() + getHorizontalAcceleration()*timeDuration)
	 * @effect The position will be set to the new horizontal and vertical position
	 * 		|	this.setPosition({hor_pos,ver_pos})
	 */			
	public void updatePositionAndVelocity(double timeDuration) {
		double hor_pos = this.getHorizontalPosition();
		double ver_pos = this.getVerticalPosition();
		
		if (this.isFalling())
			setVerticalAcceleration(-10);
		
		if (this.isFalling() || this.isJumping) {
			ver_pos = (getVerticalPosition() + getVerticalVelocity()*timeDuration + 0.5*getVerticalAcceleration()*(Math.pow(timeDuration,2)));
			this.setVerticalVelocity((getVerticalVelocity() + getVerticalAcceleration()*timeDuration));
		}
		
		if (this.isRunning()) {
			hor_pos = (getHorizontalPosition() + getHorizontalVelocity()*timeDuration + 0.5*getHorizontalAcceleration()*(Math.pow(timeDuration,2)));
			this.setHorizontalVelocity((getHorizontalVelocity() + getHorizontalAcceleration()*timeDuration));
		}
	
		this.setNewPosition(new double[] {hor_pos,ver_pos});
	}
	
	//*************************************** CHECK IF PLAYER HAS WON ***************************************************//
	
	/**
	 * Check whether the player has won or not.
	 * 
	 * @effect If the Mazub overlaps with the target tile in his world the player will have won the game and the game will be over
	 * 		|	if (this.getWorld() != null) && (MazubOverlapsWithTargetTile()) && (this.getWorld().getTargetTile() != null)
	 *		|		&& (this.getWorld().getMazub() == this)
	 *		|		then	this.getWorld().didPlayerWin = true
	 *		|				this.getWorld().isGameOver = true
	 */
	public void checkIfPlayerHasWon() {
		if ( (this.getWorld() != null) && (MazubOverlapsWithTargetTile()) && (this.getWorld().getTargetTileCoordinates() != null)
				&& (this.getWorld().getPlayerMazub() == this)) {
			this.getWorld().didPlayerWin = true;
			this.getWorld().isGameOver = true;
		}
	}
		
	/**
	 * Variable registering the elapsed time.
	 */
	private double elapsedTime = 0.0;
	
	/**
	 * Variable registering the amount of times 75 ms fit in the variable elapsedTime.
	 */
	private int counter = 0;
	
	/**
	 * Variable registering the passing of a second in time.
	 */
	private double second;


	//************************************************* SPRITES ******************************************************************
	

	
	public static boolean isValidMazubSprites(Sprite[] sprites) {
		if (sprites == null)
			return false;
		for (int i = 0; i < sprites.length; i++) {
			if ((sprites[i] == null))
				return false;
		}
		
		if ((sprites.length < 10) || ((sprites.length)%2 != 0))
			return false;
		return true;
	}
	
	@Override
	public Sprite getCurrentSprite() {
		Sprite[] sprites = this.getSprites();
		int m = (sprites.length-10)/2;
		int spriteIndex = counter%(m+1);
		Sprite currentSprite = sprites[0];
		// is not moving horizontally, has not moved horizontally within the last second of in-game time and is not ducking.
		if ((!isRunning()) && (!isDucking) && (getOrientation()==0))
			currentSprite = sprites[0];	
		// is not moving horizontally, has not moved horizontally within the last second of in-game time and is ducking.
		if ((!isRunning()) && (isDucking) && (getOrientation()==0))
			currentSprite = sprites[1];	
		// is not moving horizontally but its last horizontal movement was to the right (within 1s), and the character is not ducking.
		if ((!isRunning()) && (!isDucking) && (getOrientation()==1))
			currentSprite = sprites[2];	
		// is not moving horizontally but its last horizontal movement was to the left (within 1s), and the character is not ducking.
		if ((!isRunning()) && (!isDucking) && (getOrientation()==-1))
			currentSprite = sprites[3];
		// is moving to the right and jumping and not ducking.
		if ((isRunning()) && (!isDucking) && (getOrientation()==1) && (isJumping))
			currentSprite = sprites[4];
		// is moving to the left and jumping and not ducking.
		if ((isRunning()) && (!isDucking) && (getOrientation()==-1) && (isJumping))
			currentSprite = sprites[5];
		// is ducking and moving to the right or was moving to the right (within 1s).
		if ((isRunning()) && (isDucking) && (getOrientation()==1))
			currentSprite = sprites[6];
		// is ducking and moving to the left or was moving to the left (within 1s).
		if ((isRunning()) && (isDucking) && (getOrientation()==-1))
			currentSprite = sprites[7];
		// the character is neither ducking nor jumping and moving to the right.
		if ((isRunning()) && (!isDucking) && (getOrientation()==1) && (!isJumping)) {
			currentSprite = sprites[8+spriteIndex];
		}
		// the character is neither ducking nor jumping and moving to the left.
		if ((isRunning()) && (!isDucking) && (getOrientation()==-1) && (!isJumping)) {
			currentSprite = sprites[9+m+spriteIndex];
		}
		return currentSprite;
	}
	
		
	//************************************************* HITPOINTS ********************************************************************
	/**
	 * return the maximum hit points of this Mazub.
	 */
	@Override
	protected int getMaxHitpoints() {
		return 500;
	}
	
	
//************************************************* OVERLAP WITH TARGET TILE **************************************************//
	/**
	 * return whether or not the Mazub is overlapping with the target tile of his world.
	 * 
	 * @return One or more pixels of this Mazub are overlapping with the pixels of the target tile.
	 * 		|	result ==  !(x+(Xp)<x1) && !(x1+(Xp1)<x) && !(y+(Yp)<y1) && !(y1+(Yp1)<y)
	 */
	public boolean MazubOverlapsWithTargetTile() {
		//Player coordinates & size
		int x = this.getPixelPosition()[0];
		int y = this.getPixelPosition()[1];
		int Xp = this.getCurrentSprite().getWidth();
		int Yp = this.getCurrentSprite().getHeight();
		
		//Target tile coordinates & size
		int tileLength = this.getWorld().getTileLength();
		int x1 = (this.getWorld().getTargetTileCoordinates()[0])*tileLength;
		int y1 = (this.getWorld().getTargetTileCoordinates()[1])*tileLength;
		int Xp1 = tileLength;
		int Yp1 = tileLength;
		
		
		return ( !(x+(Xp)<x1) && !(x1+(Xp1)<x) && !(y+(Yp)<y1) && !(y1+(Yp1)<y) );

	}

	
}