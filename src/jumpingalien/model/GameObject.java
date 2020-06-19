package jumpingalien.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.lang.Object;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import jumpingalien.util.Sprite;

/**
 * A class managing the GameObjects of the jumping alien game.
 * 
 * @invar	The position of each object must be a valid position for any object.
 * 		|	CanHaveAsPosition(getPosition())
 * 
 * @invar	The pixel position of each object must be a valid pixel position for any object.
 * 		|	isValidPixelPosition(getPixelPosition()) 
 * 
 * @invar	The orientation of each object must be a valid orientation for any object.
 * 		|	isValidOrientation(getOrientation())
 *  
 * @invar	The time duration must be a valid time duration
 * 		|	isValidTimeDuration(timeDuration)
 * 
 * @invar	The sprites must be valid sprites for this gameobject
 * 		|	isValidSprites(sprites)
 * 
 * @invar	The time after collision of this object must be a valid time after collision
 * 		|	isValidTimeAfterCollision(TimeAfterCollision)
 * 
 * @version	2.0
 * 
 * @author 	Arthur van Meerbeeck, 2e Bachelor Burgerlijk Ingenieur, Computerwetenschappen-Elektrotechniek
 * 
 * @author	Paul van Tieghem de Ten Berghe, 2e Bachelor Burgerlijk Ingenieur, Elektrotechniek-Computerwetenschappen
 * 
 * Github repository: https://github.com/KUL-ogp/ogp1819-project-van-meerbeeck-van-tieghem-de-ten-berghe.git
 */
public abstract class GameObject extends Object {
	
	//********************************** ACTUAL POSITION ***************************************//
	
	/**
     * Return the actual position in two dimension of this GameObject.
     */
	public abstract double[] getPosition();

	/**
     * Set the horizontal and vertical position of this GameObject
     */
	public abstract void setHorAndVerPosition(double[] position);
	
	/**
	 * Set the position of the GameObject to the given position in two dimensions.
	 * 
	 * @param 	position
	 * 		  	The new position to set.
	 * @effect	if the object is located in a world and the position is not in the boundaries of this world 
	 * 			the object will be terminated.
	 * 		|	if this.getWorld()!= null && ! isPositionInWorldBoundaries(position)
	 * 		|		then this.terminateObject()
	 * @post 	The position of this new object is equal to the given position in meters
	 * 		|	this.actualPosition = position
	 * @post 	The pixel position of this new object is set to the position in centimeters
	 * 		|	setPixelPosition(newPixelPosition);
	 * @throws	IllegalArgumentException
	 * 		   	the position is not a valid position.
	 * 		|  	!CanHaveAsPosition(position)
	 */
	@Raw
    public void setNewPosition(double[] position) throws IllegalArgumentException {  
	
    	if (!canHaveAsPosition(position)) throw new IllegalArgumentException("Illegal new position");
    	
    	if (this.getWorld()!= null) {
    		if (! isPositionInWorldBoundaries(position))
    			this.terminateObject();	
    	}
    	//Set the new pixel position
    	int[] newPixelPosition = new int[] {(int) (position[0]*10*10),(int) (position[1]*10*10)};
    	setPixelPosition(newPixelPosition);
    	
    	//Set the new actual position
    	ArrayList<Double> newActualPosition = new ArrayList<Double>(2);
		newActualPosition.add(0, position[0]);
		newActualPosition.add(1, position[1]);
		this.actualPosition.set(newActualPosition);
		
		//Set horizontal en vertical position of the gameObjects
    	setHorAndVerPosition(position);
	}
	
	/**
     * Array registering the actual position in meters of this gameobject in the game world in two dimensions.
     */
	GenericPosition<Double> actualPosition = new GenericPosition<Double>();
	
	public double horizontalPosition;
	public double verticalPosition;
	
	
	//********************************** PIXEL POSITION ***************************************//
	
	/**
	 * Return the pixel position in two dimensions of this GameObject.
	 * @return	the pixel position
	 * 		|	result == pixelPosition.get()
	 */
	@Basic @Raw
	public int[] getPixelPosition() {
		int[] result = new int[2];
		result[0] = pixelPosition.get().get(0);
		result[1] = pixelPosition.get().get(1);
		return result;
	}
	
    /**
     * Set the pixel position of the GameObject to the given pixel position.
     * 
     * @param	pixelPosition
     * 		 	The pixel position of this new object.
     * @post	The pixel position of this new object is equal to the given pixel position.
     * 		| 	this.pixelPosition = pixelPosition
     * @throws	IllegalArgumentException
     * 		  	the given position is not a valid pixel position for any object.
     * 		|	! isValidPixelPosition(pixelPosition)
     */
	@Raw
    public void setPixelPosition(int[] pixelPosition) throws IllegalArgumentException{
    	if (!isValidPixelPosition(pixelPosition)) {
    		throw new IllegalArgumentException();
    	}
    	ArrayList<Integer> newPixelPosition = new ArrayList<Integer>(2);
		newPixelPosition.add(0, pixelPosition[0]);
		newPixelPosition.add(1, pixelPosition[1]);
		this.pixelPosition.set(newPixelPosition);
    }
	
	/**
     * Array registering the pixel position of object in the game world in two dimensions.
     */
	GenericPosition<Integer> pixelPosition = new GenericPosition<Integer>();
	
	//********************************** VELOCITY ***************************************//

	/**
	 * Return the velocity in two dimensions of this GameObject.
	 */
	public abstract double[] getVelocity();

	/**
     * Set the velocity in two dimensions of this GameObject
     */
	public abstract void setVelocity(double[] velocity);
	
	public double horizontalVelocity;
	public double verticalVelocity;
	
	//********************************** ACCELERATION ***************************************//

	/**
	 * Return the acceleration in two dimensions of this GameObject.
	 */
	public abstract double[] getAcceleration();

	/**
     * Set acceleration in two dimensions of this GameObject
     */
	public abstract void setAcceleration(double[] acceleration);
	
	public double horizontalAcceleration;
	public double verticalAcceleration;
	
	//********************************** ORIENTATION ***************************************//

	/**
	 * Return the orientation of this GameObject.
	 */
	public abstract int getOrientation();

	/**
	 * Check whether the given orientation is valid for this GameObject.
	 */
	public abstract boolean isValidOrientation(int orientation);
	
	/**
     * Set the orientation of this GameObject
     */
	public abstract void setOrientation(int orientation);
	
	public int orientation;
	
//	//********************************** POSITION BOUNDARIES ***************************************//

	/**
	 * Return the minimal horizontal position in meters that objects can access.
	 *
	 * @return	If the object is located in a world the minimum horizontal position is zero, else it is negative infinity.
	 * 		|	if this.getWorld() != null
	 * 		|		then result == 0.0
	 * 		|	else result == Double.NEGATIVE_INFINITY
	 */
	@Basic @Raw
	@Immutable
	public double getMinHorizontalPosition() {
		if (this.getWorld()!= null)
			return 0.0;
		else
			return Double.NEGATIVE_INFINITY;
	}
	
	/**
	 * Return the minimal horizontal pixel position in centimeters that objects can access.
	 *
	 * @return	If the object is located in a world the minimum horizontal pixel position is zero
	 * 			, else it is equal to negative infinity.
	 * 		|	if this.getWorld() != null
	 * 		|		then result == 0.0
	 * 		|	else result == (int) Double.NEGATIVE_INFINITY
	 */
	@Basic @Raw
	@Immutable
	public double getMinHorizontalPixelPosition() {
		if (this.getWorld()!= null)
			return 0;
		else
			return (int) Double.NEGATIVE_INFINITY;
	}
	
	/**
	 * Return the maximal horizontal position in meters that objects can access.
	 *
	 * @return	If the object is located in a world the maximum horizontal position is equal to the horizontal 
	 * 			size in pixels of the world divided by 100, else it is equal to positive infinity.
	 * 		|	if this.getWorld() != null
	 * 		|		then result == this.getWorld().getHorizontalSizeInPixels()/100
	 * 		|	else result == Double.POSITIVE_INFINITY
	 */
	@Basic @Raw
	@Immutable
	public double getMaxHorizontalPosition() {
		if (this.getWorld()!= null)
			return ((double) this.getWorld().getHorizontalSizeInPixels()/100);
		else
			return Double.POSITIVE_INFINITY;
	}
	
	/**
	 * Return the maximal horizontal pixel position in centimeters that objects can access.
	 *
	 * @return	If the object is located in a world the maximum horizontal pixel position is equal to the horizontal 
	 * 			size in pixels of the world, else it is equal to positive infinity.
	 * 		|	if this.getWorld() != null
	 * 		|		then result == this.getWorld().getHorizontalSizeInPixels()
	 * 		|	else result == Double.POSITIVE_INFINITY
	 */
	@Basic @Raw
	@Immutable
	public double getMaxHorizontalPixelPosition() {
		if (this.getWorld()!= null)
			return (this.getWorld().getHorizontalSizeInPixels());
		else
			return Double.POSITIVE_INFINITY;
	}
	
	/**
	 * Return the minimal vertical position in meters that objects can access.
	 *
	 * @return	If the object is located in a world the minimum vertical position is equal to zero
	 * 			, else it is equal to negative infinity.
	 * 		|	if this.getWorld() != null
	 * 		|		then result == 0.0
	 * 		|	else result == Double.NEGATIVE_INFINITY
	 */
	@Basic @Raw
	@Immutable
	public double getMinVerticalPosition() {
		if (this.getWorld()!= null)
			return 0.0;
		else
			return Double.NEGATIVE_INFINITY;
	}
	
	/**
	 * Return the minimal vertical pixel position in centimeters that objects can access.
	 *
	 * @return	If the object is located in a world the minimum vertical pixel position is equal to zero
	 * 			, else it is equal to negative infinity.
	 * 		|	if this.getWorld() != null
	 * 		|		then result == 0.0
	 * 		|	else result == Double.NEGATIVE_INFINITY
	 */
	@Basic @Raw
	@Immutable
	public double getMinVerticalPixelPosition() {
		if (this.getWorld()!= null)
			return 0.0;
		else
			return (int) Double.NEGATIVE_INFINITY;
	}
	
	/**
	 * Return the maximal vertical position in meters that objects can access.
	 *
	 * @return	If the object is located in a world the maximum vertical position is equal to the vertical size in pixels
	 * 			of this world divided by 100, else it is positive infinity.
	 * 		|	if this.getWorld() != null
	 * 		|		then result == this.getWorld().getVerticalSizeInPixels()/100
	 * 		|	else result == Double.POSITIVE_INFINITY
	 */
	@Basic @Raw
	@Immutable
	public double getMaxVerticalPosition() {
		if (this.getWorld()!= null)
			return (this.getWorld().getVerticalSizeInPixels()/100);
		else
			return Double.POSITIVE_INFINITY;
	}
	
	/**
	 * Return the maximal vertical pixel position in centimeters that objects can access.
	 *
	 * @return	If the object is located in a world the maximum vertical pixel position is equal to the vertical size in pixels
	 * 			of this world, else it is positive infinity.
	 * 		|	if this.getWorld() != null
	 * 		|		then result == this.getWorld().getVerticalSizeInPixels()
	 * 		|	else result == Double.POSITIVE_INFINITY
	 */
	@Basic @Raw
	@Immutable
	public double getMaxVerticalPixelPosition() {
		if (this.getWorld()!= null)
			return (this.getWorld().getVerticalSizeInPixels());
		else
			return (int) Double.POSITIVE_INFINITY;
	}
	
	/**
	 * Check whether the given horizontal position is a valid horizontal position.
	 * 
	 * @param 	hor_position
	 * 		  	The horizontal position to check.
	 * @return 	True if and only if the given horizontal position is within the boundaries of the game world.
	 * 		 |	result == (hor_position >= getMinHorizontalPosition()) &&
			 |		(hor_position <= getMaxHorizontalPosition())
	 */
	public boolean canHaveAsHorizontalPosition(double hor_position) {
			return (hor_position >= getMinHorizontalPosition()) &&
					(hor_position <= getMaxHorizontalPosition());
	}
	
	/**
	 * Check whether the given vertical position is a valid vertical position.
	 * 
	 * @param 	ver_position
	 * 		  	The vertical position to check.
	 * @return 	True if and only if the given vertical position is within the boundaries of the game world.
	 * 		|	result == ver_position >= getMinVerticalPosition() &&
					ver_position < getMaxVerticalPosition()
	 */
	public boolean canHaveAsVerticalPosition(double ver_position) {

			return (ver_position >= getMinVerticalPosition()) &&
					(ver_position <= getMaxVerticalPosition());
	}
	
	/**
	 * Check whether the given position is a valid position.
	 * 
	 * @param 	position
	 * 			The position to check.
	 * @return	True if and only if the object is a mazub located in a world and doesn't overlap with impassable terrain when located on his new position
	 * 			,the position is not equal to null, the length of position is equal to two and
	 * 			the given horizontal and vertical positions are valid numbers.
	 * 		| 	if this.getWorld()!= null && this instanceof Mazub
	 * 		|		then for x in 1..Xp-1 and for y in 1..Yp-1
	 * 		|		result == !(this.getWorld().getFeature(featureSymbol)).getType() == "IMPASSABLE")
	 * 		|	else
	 * 		|		result == (position!=null) && (position.length == 2) && (!Double.isNaN(actualPosition[0])) 
	 * 		|		&& (!Double.isNaN(actualPosition[1]))
	 */
	@Raw
	public boolean canHaveAsPosition(double[] position) {
		
		if (this.getWorld()!= null && this instanceof Mazub) {
			int Xp = this.getCurrentSprite().getWidth();
			int Yp = this.getCurrentSprite().getHeight();
			for (int x=1; x<= Xp-1; x++) {
				for (int y=1; y<= Yp-1; y++) {
					int PixelX = ((int)(position[0]*100))+x;
					int PixelY = ((int)(position[1]*100))+y;
					World world = this.getWorld();
					int featureSymbol = world.getFeatureSymbolAtLocation(PixelX,PixelY);
					if ( (this.getWorld().getFeature(featureSymbol)).getType() == "IMPASSABLE") {
						return false;
					}
				}
			}
		}
		return ((position!=null) && (position.length == 2) && 
				(! Double.isNaN(position[0])) && (!Double.isNaN(position[1])));
	}
	
	/**
	 * Check whether the position is in the boundaries of the world
	 * @param position
	 * 		  The position to check.
	 * @return	True is and only if the position is not equal to null and the horizontal position
	 * 			and the vertical position don't exceed the world boundaries.
	 * 		|	result ==(position[0] >= getMinHorizontalPosition()) && (position[0]< getMaxHorizontalPosition())
	 *		|				&& (position[1]>= getMinVerticalPosition()) && (position[1]< getMaxVerticalPosition()) 
	 *		|				&& (position != null)
	 */		
	public boolean isPositionInWorldBoundaries(double[] position) {
		return ((position[0] >= getMinHorizontalPosition()) && (position[0]< getMaxHorizontalPosition())
				&& (position[1] >= getMinVerticalPosition()) && (position[1]< getMaxVerticalPosition()) && (position != null));
	
	}
	
    
	/**
	 * Check whether the given position is a valid pixel position.
	 * 
	 * @param 	pixelPosition
	 * 			The pixel position to check.
	 * @return	True if and only if the pixel position is not equal to null and  the length of the pixel position is equal to two
	 * 		|	result == ( (pixelPosition!=null) && (pixelPosition.length == 2) 
	 *			 		
	 */
	public boolean isValidPixelPosition(int[] pixelPosition) {
		return (pixelPosition != null && pixelPosition.length == 2);
	}
	
	
	/**
	 * Check whether the pixel position is in the boundaries of the world
	 * @param pixelposition
	 * 		  The pixel position to check.
	 * @return	True is and only if the pixel position is not equal to null and the horizontal pixel position
	 * 			and the vertical pixel position don't exceed the world boundaries.
	 * 		|	result ==(pixelposition[0] >= getMinHorizontalPixelPosition()) && (pixelposition[0]< getMaxHorizontalPixelPosition())
	 *		|				&& (pixelposition[1]>= getMinVerticalPixelPosition()) && (pixelposition[1]< getMaxVerticalPixelPosition()) 
	 *		|				&& (pixelposition != null)
	 */
	public boolean IsPixelPositionInWorldBoundaries(int[] pixelposition) {
		return ((pixelposition[0] >= getMinHorizontalPixelPosition()) && (pixelposition[0]< getMaxHorizontalPixelPosition())
				&& (pixelposition[1]>= getMinVerticalPixelPosition()) && (pixelposition[1]< getMaxVerticalPixelPosition())
				&& (pixelposition != null));
	}
	

	
	//********************************** TERMINATE ***************************************//
	
	/**
	 * Terminate this object.
	 * @effect if the object to terminate is the player mazub the game wil be over and the player will have lost.
	 * 		|	if this = this.getWorld().getPlayerMazub()
	 * 		|		then this.getWorld().isGameOver = true;
	 *		|		this.getWorld().didPlayerWin = false
	 * @effect	The object will be removed from his world
	 * 		|	(this.getWorld()).removeObject(this);
	 *		|	this.world = null
	 * @effect  If the object is from the type Slime, the object will be removed from its school.
	 * 		| 	if (this instanceof Slime)
	 * 		|	((Slime) this).getSchool().removeSlime((Slime) this); 
	 *		|	((Slime) this).setSchool(null)
	 * @post    this object is terminated.
	 *       | new.isTerminated()
	 */
	public void terminateObject() {
		if (this.getWorld()!=null) {
			if (this == this.getWorld().getPlayerMazub()) {
				this.getWorld().isGameOver = true;
				this.getWorld().didPlayerWin = false;
			}
			(this.getWorld()).removeObject(this);
			this.world = null;
		}
		if (this instanceof Slime) {
			if (((Slime) this).getSchool() != null) {
				((Slime) this).getSchool().removeSlime((Slime) this); 
				((Slime) this).setSchool(null);
			}
		}
		this.isTerminated = true;
		//System.runFinalization();
	}

	/**
	 * Return a boolean indicating whether or not this object
	 * is terminated.
	 * 
	 * @return result == this.isTerminated
	 */
	@Basic @Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	/**
	 * Variable registering whether or not this object is terminated.
	 */
	private boolean isTerminated = false;
	
	

	//******************************************** WORLD *******************************************************//
	
	/**
	 * Return the world of this object.
	 */
	@Basic @Raw
	public World getWorld() {
		return this.world;
	}

	/**
	 * Set the world of this object to the given world.
	 * 
	 * @param  world
	 *         The new world for this object.
	 * @post   The world of this new object is equal to
	 *         the given world.
	 *       | new.getWorld() == world
	 */
	@Raw
	public void setWorld(World world) {
		this.world = world;
	}
	
	/**
	 * Variable registering the world of this object.
	 */
	private World world;
	
	
	//************************************************* HITPOINTS ***************************************************************//
	
	/**
	 * Return the hit points of this game object.
	 */
	@Basic @Raw
	public int getHitpoints() {
		return this.Hitpoints;
	}

	/**
	 * Check whether the given hit points are valid hit points for
	 * any game object.
	 *  
	 * @param  Hitpoints
	 *         The hit points to check.
	 * @return The hitpoints may not be greater than 500 and must be greater than zero.
	 *       | result == Hitpoints<= this.getMaxHitpoints() && Hitpoints >= 0
	*/
	public boolean CanHaveAsHitpoints(int Hitpoints) {
		return (Hitpoints<= this.getMaxHitpoints() && Hitpoints >= 0);
	}
	
	/**
	 * return the Maximum Hitpoints of the Gameobject
	 */
	protected abstract int getMaxHitpoints();

	/**
	 * Set the hit points of this game object to the given hit points.
	 * 
	 * @param  Hitpoints
	 *         The new hit points for this game object.
	 * @post   If the game object can have the hitpoints as its hitpoints,
	 *         the hit points of this new game object are equal to the given
	 *         hit points.
	 *       | if (CanHaveAsHitpoints(Hitpoints))
	 *       |   then new.getHitpoints() == Hitpoints
	 * @post   If the given hitpoints are greater than 500 the game object hitpoints will be set to 500 else
	 * 		   (the hit points are less than or equal to zero) the game object hitpoints will be set to zero.
	 * 		 | if Hitpoints>500
	 * 		 |	then this.Hitpoints = this.getMaxHitpoints()
	 * 		 |	else 
	 * 		 |	this.Hitpoints = 0
	 */
	@Raw
	public void setHitpoints(int Hitpoints) {
		if (CanHaveAsHitpoints(Hitpoints))
			this.Hitpoints = Hitpoints;
		else {
			if (Hitpoints > this.getMaxHitpoints())
				this.Hitpoints = this.getMaxHitpoints();
			else
				this.Hitpoints = 0;
		}
	}
	
	/**
	 * Variable registering the hit points of this game object.
	 */
	private int Hitpoints;
	
		
	//************************************************* IS DEAD ***************************************************************//

	/**
	 * Boolean which returns whether or not an object is dead.
	 * 
	 * @return The hit points of the object are equal to or less than zero.
	 * 		|    result == this.getHitpoints()<=0
	 */
	public boolean isDead() {
		return (this.getHitpoints()<=0);
	}
		
	//************************************************* OVERLAP ***************************************************************//
	
	/**
	 * Boolean which returns whether or not this object is overlapping with a given object.
	 * 
	 * @param 	object
	 * 			The given object to check overlap with
	 * @return 	If the given object is this object, they're not overlapping because they're the same,
	 * 			if not this object overlaps an object if any pixel of this object overlaps any pixel of the other object.
	 * 		|   if (this == object)
	 * 		| 		then result == false
	 *		|	else then
	 *		|		result == !(x+(Xp-1)<x1) && !(x1+(Xp1-1)<x) && !(y+(Yp-1)<y1) && !(y1+(Yp1-1)<y)
	 */
	public boolean overlapsWithObject(Object object) {
		if (this == object)
			return false;
		int x = this.getPixelPosition()[0];
		int y = this.getPixelPosition()[1];
		int Xp = this.getCurrentSprite().getWidth();
		int Yp = this.getCurrentSprite().getHeight();
		
		int x1 = ((GameObject) object).getPixelPosition()[0];
		int y1 = ((GameObject) object).getPixelPosition()[1];
		int Xp1 = ((GameObject) object).getCurrentSprite().getWidth();
		int Yp1 = ((GameObject) object).getCurrentSprite().getHeight();
		
		return ( !(x+(Xp-1)<x1) && !(x1+(Xp1-1)<x) && !(y+(Yp-1)<y1) && !(y1+(Yp1-1)<y) );
		
	}
	
	/**
	 * Boolean which returns whether or not this object is overlapping with impassable terrain in the given world.
	 * 
	 * @param 	world
	 * 			The given world to check the overlap in.
	 * @return 	If any of this object's pixels (except the bottom row) overlaps with impassable terrain, the object overlaps with impassable terrain.
	 * 		|   for any i in x..XP+x-1
	 * 		| 		for any j in y+1..Yp+y-1
	 * 		|   		result == feature.getType() == "IMPASSABLE"
	 */
	public boolean overlapsWithImpassableTerrain(World world) {
	
		int x = this.getPixelPosition()[0];
		int y = this.getPixelPosition()[1];
		int Xp = this.getCurrentSprite().getWidth();
		int Yp = this.getCurrentSprite().getHeight();
		
		for (int i = x; i<=(Xp+x-1); i++) {
			for (int j = y+1; j<=(Yp+y-1);j++) {	
				int featureSymbol = world.getFeatureSymbolAtLocation(i, j);
				Feature feature = world.getFeature(featureSymbol);
				if (feature.getType() == "IMPASSABLE" )
					return true;	
			}
		}
		return false;
	}
	
	/**
	 * Arraylist which returns all the features with which this game objects overlaps.
	 * @effect	add all features who overlap at least one of the pixels of this game object
	 * 		|	for i in x..Xp+x-1
	 * 		|		for j in y +Yp+y-1
	 * 		|			allOverlappingFeatures.add(this.getWorld().getFeatureSymbolAtLocation(i, j))
	 * @return	|	result == allOverlappingFeatures
	 */
	public Set<Integer> getOverlappingFeatures() {
		
		int x = this.getPixelPosition()[0];
		int y = this.getPixelPosition()[1];
		int Xp = this.getCurrentSprite().getWidth();
		int Yp = this.getCurrentSprite().getHeight();
		
		Set<Integer> allOverlappingFeatures = new HashSet<Integer>();
		for (int i = x; i<=Xp+x-1; i++) {
			for (int j = y; j<=Yp+y-1;j++) {
				allOverlappingFeatures.add(this.getWorld().getFeatureSymbolAtLocation(i, j));
			}
		}
		
		return allOverlappingFeatures;
	}
	
	
	/**
	 * Boolean which returns whether or not this object is overlapping with water.
	 * 
	 * @return 	If any of this object's pixels overlaps with a water tile, the object overlaps with water.
	 * 		|   result == this.getOverlappingFeatures().contains(Feature.WATER.getSymbol())
	 */
	public boolean overlapsWithWater() {
		
		return (this.getOverlappingFeatures().contains(Feature.WATER.getSymbol()));
						
	}
	
	/**
	 * Boolean which returns whether or not this object is overlapping with magma.
	 * 
	 * @return 	If any of this object's pixels overlaps with a magma tile, the object overlaps with magma.
	 * 		|   result == this.getOverlappingFeatures().contains(Feature.MAGMA.getSymbol())
	 */
	public boolean overlapsWithMagma() {
		
		return (this.getOverlappingFeatures().contains(Feature.MAGMA.getSymbol()));			
	}
	
	/**
	 * Boolean which returns whether or not this object is overlapping with gas.
	 * 
	 * @return 	If any of this object's pixels overlaps with a gas tile, the object overlaps with gas.
	 * 		|   result == this.getOverlappingFeatures().contains(Feature.GAS.getSymbol())
	 */
	public boolean overlapsWithGas() {
		
		return (this.getOverlappingFeatures().contains(Feature.GAS.getSymbol()));			
	}
	
	/**
	 * Return all the overlapping objects with this object in a given world.
	 * 
	 * @param 	world
	 * 			The given world to check the overlapping objects in
	 * @return 	If the world is null, there are no overlapping objects, so an empty set is returned.
	 * 		|   if (world == null)
	 * 		| 		then result = AllOverlappingObjects
	 * @return	Else the method iterates over every object in the given world and adds them to the set if they overlap with this object
	 * 		|   else
	 * 		| 		while worldIt.hasnext() do
	 * 		|			if (this.overlapsWithObject(object))
	 *		|				then AllOverlappingObjects.add(object)
	 * 		|	result == AllOverlappingobjects	
	 */
	public Set<Object> allOverlappingObjects(World world) {
		
		Set<Object> AllOverlappingObjects = new HashSet<Object>();
		
		if (world == null)
			return AllOverlappingObjects;
		
		@SuppressWarnings("rawtypes")
		Iterator worldIt = world.iterator();
		while (worldIt.hasNext()) {
			Object object = (Object) worldIt.next();
			if (this.overlapsWithObject(object))
				AllOverlappingObjects.add(object);
		}
		
		return AllOverlappingObjects;
	}
	
	/**
	 * Boolean which returns whether this object overlaps with a Mazub.
	 * 
	 * @param 	world
	 * 			The given world to check the overlapping objects in
	 * @return 	True if and only if this object overlaps with a Mazub.
	 * 		| 	result == this.allOverlappingObjects(world).stream().anyMatch(object-> object instanceof Mazub )
	 */
	public boolean overlapsWithMazub(World world) {
		return this.allOverlappingObjects(world).stream().anyMatch(object-> object instanceof Mazub );
	}
	
	/**
	 * Boolean which returns whether this object overlaps with a Plant.
	 * 
	 * @param 	world
	 * 			The given world to check the overlapping objects in
	 * @return 	True if and only if this object overlaps with a Plant.
	 * 		| 	result == this.allOverlappingObjects(world).stream().anyMatch(object-> object instanceof Plant )
	 */
	public boolean overlapsWithPlant(World world) {
		return this.allOverlappingObjects(world).stream().anyMatch(object-> object instanceof Plant );

	}
	
	/**
	 * Boolean which returns whether this object overlaps with a Shark.
	 * 
	 * @param 	world
	 * 			The given world to check the overlapping objects in
	 * @return 	True if and only if this object overlaps with a Shark.
	 * 		| 	result == this.allOverlappingObjects(world).stream().anyMatch(object-> object instanceof Shark)
	 */
	public boolean overlapsWithShark(World world) {
		return this.allOverlappingObjects(world).stream().anyMatch(object-> object instanceof Shark);
	}
	
	/**
	 * Boolean which returns whether this object overlaps with a Slime.
	 * 
	 * @param 	world
	 * 			The given world to check the overlapping objects in
	 * @return 	True if and only if this object overlaps with a Slime.
	 * 		| 	result == this.allOverlappingObjects(world).stream().anyMatch(object-> object instanceof Slime)
	 */
	public boolean overlapsWithSlime(World world) {
		return this.allOverlappingObjects(world).stream().anyMatch(object-> object instanceof Slime);
	}
	
	/**
	 * Return all the overlapping Plants with this object in a given world.
	 * 
	 * @param 	world
	 * 			The given world to check the overlapping objects in
	 * @return 	The method iterates over every overlapping object with this object in the given world and 
	 * 			adds them to the arraylist if they are a plant
	 * 		| 	this.allOverlappingObjects(world).stream().filter(object->object instanceof Plant)
	 *		|		.forEach(object -> overlappingPlants.add((Plant) object))
	 * 		|	result == overlappingPlants
	 */	
	public ArrayList<Plant> getOverlappingPlants(World world) {
		
		ArrayList<Plant> overlappingPlants = new ArrayList<Plant>();
		this.allOverlappingObjects(world).stream().filter(object->object instanceof Plant)
				.forEach(object -> overlappingPlants.add((Plant) object));
		
		return overlappingPlants;
	}
	

	/**
	 * return whether or not an object is overlapping with another object except plants.
	 * @param world
	 * @return	True if and only if the object overlaps with an object of type Mazub, Shark or Slime.
	 * 		|	result == this.overlapsWithMazub(world) || this.overlapsWithSlime(world) || this.overlapsWithShark(world)
	 */
	public boolean overlapsWithObjectNotPlant(World world) {
		return (this.overlapsWithMazub(world) || this.overlapsWithSlime(world) || this.overlapsWithShark(world));
	}
	
	//************************************************* EATING PLANT ***************************************************************//

	/**
	 * method for when Mazub is eating a plant
	 * 
	 * @param	plant 	The plant which will be eaten.
	 * @param	mazub	The Mazub which will eat the plant.
	 * 
	 * @effect	if Mazub or the plant are already terminated this method will do nothing
	 * 		|	if (mazub.isTerminated() || plant.isTerminated())
	 *		|	return;
	 * @effect	If the plant is a dead plant Mazubs hit points will be deducted by 20 and the plant is removed from the world and terminated.
	 *		|	else if (plant.isDead())
	 *		|	mazub.setHitpoints(mazub.getHitpoints()-20)
	 *		|	plant.getWorld().removeObject(plant)
	 *		|	plant.terminateObject()
	 * @effect	else Mazubs hit points will be increased by 50 if it's hitpoints is lowser than 500
	 *		|	else 
	 *		|	mazub.setHitpoints(mazub.getHitpoints()+50)
	 * @effect	If the plant is of the type Skullcab it hitpoints will be deducted with 1 hit point
	 * 		|	if (plant instanceof Skullcab)
	 *		|		then plant.setHitpoints(plant.getHitpoints()-1)
	 *
	 * @effect	else the plant was of type sneezewort and will be removed from the world and will be terminated.
	 * 		|	plant.getWorld().removeObject(plant)
	 *		|	plant.terminateObject()
	 */
	public void eatingPlant(Plant plant, Mazub mazub) {
		if (mazub.isTerminated() || plant.isTerminated())
			return;
		
		else if (plant.isDead()) {
			mazub.setHitpoints(mazub.getHitpoints()-20);
			plant.getWorld().removeObject(plant);
			plant.terminateObject();
		}
		
		else if (mazub.getHitpoints()<500) {
			mazub.setHitpoints(mazub.getHitpoints()+50);
			
			if (plant instanceof Skullcab) {
				plant.setHitpoints(plant.getHitpoints()-1);
			}
			else {
				plant.setHitpoints(0);
				plant.getWorld().removeObject(plant);
				plant.terminateObject();
			}
		}
	}
	
	//******************* TERMINATE DEAD OBJECT *******************//

	
	/**
	 * Terminate a dead object if its dead for longer than 0.6 seconds
	 * @param dt
	 * 		  The time that is advanced in the AdvanceTime methods.
	 * @effect if the object is dead the object will be terminated if it's dead longer than 0.6 seconds.
	 * 		|	if (this.isDead())
	 * 		|		then if timeWhileDead >=0.6
	 * 		|			then this.terminateObject.
	 */
	public void terminateDeadObject(double dt) {
		if (this.isDead()) {
			TimeWhileDead += dt;
			
			if (TimeWhileDead >= 0.6) {
				this.terminateObject();
				TimeWhileDead = 0;
			}
		}
		else
			TimeWhileDead=0.0;
	}
	
	/**
	 * variable registering for how long an object is already dead.
	 */
	private double TimeWhileDead = 0;
	
	//************************************************* ADVANCE TIME ***************************************************************//

	/**
	 * Check whether the given time duration is a valid time duration for any GameObject.
	 *  
	 * @param 	timeDuration
	 *         	The time duration to check.
	 * @return	The time duration of any GameObject shall always be greater than zero and never be greater than 0.2 seconds.
	 *       |	result == (timeDuration > 0 && timeDuration <= 0.2)
	*/	
	public boolean isValidTimeDuration(double timeDuration) {
		return (timeDuration > 0.0 && timeDuration<=0.2);
	}
	
	/**
	 * Advance the time for this GameObject incrementally so that each advancement of time (dtObject) at most moves the GameObject by one pixel
	 * @param 	dt
	 * 			The given time duration to advance the time with
	 * @effect The object will be advanced pixel by pixel until the total dt is advanced.
	 * 		   | while (timeDuration > 0)
	 *		   |	if (dtObject > timeDuration) then
	 *		   |		advanceTimePartially(timeDuration)
	 *		   |		timeDuration == 0
	 *		   | 	else
	 *		   | 		advanceTimePartially(dtObject)
	 *		   | 		timeDuration -= dtObject
	 */
	public void advanceObjectsTime(double dt) {
		if (!isValidTimeDuration(dt))
			return;
		
		double timeDuration = dt;
			while (timeDuration>0) {
				
				double dtObject = 0.01/(Math.sqrt((Math.pow(this.getVelocity()[0],2) + 
						Math.pow(this.getVelocity()[1], 2))+(Math.sqrt((Math.pow(this.getAcceleration()[0], 2)+
								Math.pow(this.getAcceleration()[1], 2))))));
				
				if (dtObject > timeDuration) {
		
						this.advanceTimePartially(timeDuration);
						timeDuration = 0.0;
				}
				else {
					this.advanceTimePartially(dtObject);
					timeDuration -= dtObject;
				}
			}
	}
	
	/**
	 * Advance the time partially for this GameObject.
	 * 
	 * @param timeDuration
	 * 		  The time to advance.
	 * 
	 * @effect 	If the GameObject is dead, it's movement is stopped
	 * 		  |	stopMovingIfDead()
	 * 
	 * @effect	If the GameObject is dead, it's termination delay of 0.6 seconds is updated
	 * 		  |	terminateDeadObject(timeDuration)
	 * 
	 */
	public abstract void advanceTimePartially(double timeDuration);
	
	//************************************************* SPRITES ***************************************************************//

	@Basic
	public Sprite[] getSprites() {
		return Arrays.copyOf(this.sprites, this.sprites.length);
	}
	
	public boolean isValidSprites(Sprite[] sprites) {
		if (this instanceof Mazub)
			return Mazub.isValidMazubSprites(sprites);
		else if (this instanceof Shark)
			return Shark.isValidSharkSprites(sprites);
		else if (this instanceof Slime)
			return Slime.isValidSlimeSprites(sprites);
		else 
			return Plant.isValidPlantSprites(sprites);
	}
	@Raw
	public void setSprites(Sprite[] sprites) throws IllegalArgumentException {
		if (!isValidSprites(sprites)) throw new IllegalArgumentException();
		this.sprites = Arrays.copyOf(sprites, sprites.length);
	}
	
	
	@Basic @Raw
	public abstract Sprite getCurrentSprite(); 
	
	private Sprite[] sprites;

	
	//********************************************* COLLISION ********************************************************//

	
		//********** COLLISION WITH GIVEN OBJECT ******************//
		
		/**
		 * Return whether or not the game object is colliding with the given object to the right.
		 * @param object
		 * 		  The object to check.
		 * @return One or more pixels of the right outer layer of the game object overlaps with one or 
		 * 		   more pixels of the left outer layer from the given object.
		 * 		|  if OuterRightX != OuterLeftXobject
		 * 		|  	then result== false
		 * 		|  	else result == 
		 * 		|		for any i in OuterRightY..OuterRightY+ this.getCurrentSprite().getHeight()-2:
		 * 		|				i>=OuterLeftYObject && i <= OuterLeftYObject+((GameObject) object).getCurrentSprite().getHeight()-2
		 */
		public boolean collisionWithObjectToTheRight(Object object) {
			int OuterRightX = (this.getPixelPosition()[0]+ this.getCurrentSprite().getWidth()+1);
			int OuterRightY = (this.getPixelPosition()[1]);
			int OuterLeftXObject = (((GameObject) object).getPixelPosition()[0]);
			int OuterLeftYObject = (((GameObject) object).getPixelPosition()[1]);
			
			if (OuterRightX != OuterLeftXObject)
				return false;
			else {
				for (int i = OuterRightY; i<=(OuterRightY+ this.getCurrentSprite().getHeight()-1); i++) {
					if (i>=OuterLeftYObject && i <= OuterLeftYObject+((GameObject) object).getCurrentSprite().getHeight()-1)
						return true;
				}
				return false;
			}
		}
		
		/**
		 * Return whether or not the game object is colliding with the given object to the left.
		 * @param object
		 * 		  The object to check.
		 * @return One or more pixels of the left outer layer of the game object overlaps with one or 
		 * 		   more pixels of the right outer layer from the given object.
		 * 		|  if OuterLeftX != OuterRightXobject
		 * 		|  	then result == false
		 * 		|  	else result == 
		 * 		|		for any i in OuterLeftY+1..OuterLeftY+ this.getCurrentSprite().getHeight()-2:
		 * 		|			i>= OuterRightYObject && i<= OuterRightYObject+((GameObject) object).getCurrentSprite().getHeight()-2
		 */
		public boolean collisionWithObjectToTheLeft(Object object) {
			int OuterLeftX = (this.getPixelPosition()[0]);
			int OuterLeftY = (this.getPixelPosition()[1]);
			int OuterRightXObject = (((GameObject) object).getPixelPosition()[0]+ ((GameObject) object).getCurrentSprite().getWidth());
			int OuterRightYObject = (((GameObject) object).getPixelPosition()[1]);
			
			if (OuterLeftX != OuterRightXObject)
				return false;
			else {
				for (int i = OuterLeftY+1; i<=(OuterLeftY+ this.getCurrentSprite().getHeight()-1); i++) {
					if (i>= OuterRightYObject && i<= OuterRightYObject+((GameObject) object).getCurrentSprite().getHeight()-1)
						return true;
				}
				return false;
			}
		}
		
		/**
		 * Return whether or not the game object is colliding with the given object at his top.
		 * @param object
		 * 		  The object to check.
		 * @return One or more pixels of the top outer layer of the game object overlaps with one or 
		 * 		   more pixels of the bottom outer layer from the given object.
		 * 		|  if TopX != BottomXobject
		 * 		|  	then result == false
		 * 		| 	else result == 
		 * 		|		for any i in TopX..TopX+this.getCurrentSprite().getWidth()-1:
		 * 		|			i>=BottomXObject && i<= BottomXObject+((GameObject) object).getCurrentSprite().getWidth()-1
		 */
		public boolean collisionWithObjectAtTheTop(Object object) {
			int TopY = this.getPixelPosition()[1]+ this.getCurrentSprite().getHeight()+1;
			int TopX = this.getPixelPosition()[0];
			int BottomYObject = ((GameObject) object).getPixelPosition()[1];
			int BottomXObject = ((GameObject) object).getPixelPosition()[0];
			
			if (TopY != BottomYObject)
				return false;
			else {
				for (int i = TopX; i<=(TopX+this.getCurrentSprite().getWidth()-1); i++) {
					if (i>=BottomXObject && i<= BottomXObject+((GameObject) object).getCurrentSprite().getWidth()-1)
						return true;
				}
				return false;
			}
			
		}
		
		/**
		 * Return whether or not the game object is colliding with the given object at his bottom.
		 * @param object
		 * 		  The object to check.
		 * @return One or more pixels of the bottom outer layer of the game object overlaps with one or 
		 * 		   more pixels of the top outer layer from the given object.
		 * 		|  if BottomX != TopXobject
		 * 		|  			then result == false
		 * 		|  	else result==
		 * 		|		for any i in BottomX..BottomX+this.getCurrentSprite().getWidth()-1:
		 * 		|			i>= TopXObject && i<=TopXObject+((GameObject) object).getCurrentSprite().getWidth()-1
		 */			
		public boolean collisionWithObjectAtTheBottom(Object object) {
			int BottomY = this.getPixelPosition()[1];
			int BottomX = this.getPixelPosition()[0];
			int TopYObject = ((GameObject) object).getPixelPosition()[1] + ((GameObject) object).getCurrentSprite().getHeight()-1;
			int TopXObject = ((GameObject) object).getPixelPosition()[0];
			
			if (BottomY != TopYObject)
				return false;
			else {
				for (int i = BottomX; i<=(BottomX+this.getCurrentSprite().getWidth()-1); i++) {
					if (i>= TopXObject && i<=TopXObject+((GameObject) object).getCurrentSprite().getWidth()-1 )
						return true;
				}
				return false;
			}
			
		}
		
		//********** COLLISION WITH OTHER OBJECT (NOT PLANT) ******************//

		/**
		 * Return whether or not the game object is colliding with an object which is not a plant to the right.
		 * 
		 * @return The game object collides with at least one other object which is not a plant from his world to the right.
		 * 		|	if this.getWorld() == null
		 * 		|		then result == false
		 * 		|	else result ==this.getWorld().getObjects().stream().anyMatch(o->
		 * 		|		(!(o instanceof Plant) && this.collisionWithObjectToTheRight(o)))
		 */
		public boolean collidesWithanObjectToTheRight() {
			if (this.getWorld() == null)
				return false;
			else {
				return this.getWorld().getObjects().stream().anyMatch(o->(!(o instanceof Plant) && this.collisionWithObjectToTheRight(o)));

			}
		}
		
		/**
		 * Return whether or not the game object is colliding with an object which is not a plant to the left.
		 * 
		 * @return The game object collides with at least one other object which is not a plant from his world to the left.
		 * 		|	if this.getWorld() == null
		 * 		|		then result == false
		 * 		|	else result ==
		 * 		|		this.getWorld().getObjects().stream().anyMatch(o->
		 * 		|			(!(o instanceof Plant) && this.collisionWithObjectToTheLeft(o)))
		 */
		public boolean collidesWithanObjectToTheLeft() {
			if (this.getWorld() == null)
				return false;
			else {
				return this.getWorld().getObjects().stream().anyMatch(o->(!(o instanceof Plant) && this.collisionWithObjectToTheLeft(o)));

			}
		}
		
		/**
		 * Return whether or not the game object is colliding with an object which is not a plant at his top.
		 * 
		 * @return The game object collides with at least one other object which is not a plant from his world at his top.
		 * 		|	if this.getWorld() == null
		 * 		|		then result == false
		 * 		|	else result == this.getWorld().getObjects().stream().anyMatch(o->
		 * 		|		(!(o instanceof Plant) && this.collisionWithObjectAtTheTop(o)))
		 */
		public boolean collidesWithanObjectAtTheTop() {
			if (this.getWorld() == null)
				return false;
			else {
				return this.getWorld().getObjects().stream().anyMatch(o->(!(o instanceof Plant) && this.collisionWithObjectAtTheTop(o)));

			}
		}
		
		/**
		 * Return whether or not the game object is colliding with an object which is not a plant at his bottom.
		 * 
		 * @return The game object collides with at least one other object which is not a plant from his world at his bottom.
		 * 		|	if this.getWorld() == null
		 * 		|		then result == false
		 * 		|		else result ==this.getWorld().getObjects().stream().anyMatch(o->
		 * 		|			(!(o instanceof Plant) && this.collisionWithObjectAtTheBottom(o)))
		 */
		public boolean collidesWithanObjectAtTheBottom() {
			if (this.getWorld() == null)
				return false;
			else 
				return this.getWorld().getObjects().stream().anyMatch(o->(!(o instanceof Plant) && this.collisionWithObjectAtTheBottom(o)));
		}
		
		/**
		 * return an arraylist of all the objects which are colliding with this game object
		 * @param world
		 * 
		 * @return	if the world is null an empty arraylist will be returned
		 * 		|	if world == null
		 * 		|		result == AllCollidingObjects
		 * @effect if the world is not null all the colliding objects will be put in an arraylist
		 * 		|	for object in world.getobjects
		 * 		|		if this.collisionWithObjectAtTheBottom(object) || this.collisionWithObjectAtTheTop(object) 
		 *		|			|| this.collisionWithObjectToTheLeft(object) || this.collisionWithObjectToTheRight(object)
		 *		|			then AllCollidingObjects.add(object)
		 * @return all the colliding objects
		 * 		|	result == AllCollidingObjects
		 * 
		 */
		public ArrayList<Object> allCollidingObjects(World world) {
			ArrayList<Object> AllCollidingObjects = new ArrayList<Object>();
			
			if (world == null)
				return AllCollidingObjects;
			
			else {	
				for (Object object: world.getObjects()) {
					if (this.collisionWithObjectAtTheBottom(object) || this.collisionWithObjectAtTheTop(object) 
							|| this.collisionWithObjectToTheLeft(object) || this.collisionWithObjectToTheRight(object))
						AllCollidingObjects.add(object);
				}
				return AllCollidingObjects;
			}
		}
		
		/**
		 * return an arraylist of all the objects with which this game object is colliding and facing
		 * @param world
		 * @return	if the world is null an empty arraylist will be returned
		 * 		|	if world == null
		 * 		|		result == AllCollidingObjects
		 * 
		 * @effect if the world is not null objects with which this game object is colliding and facing will be put in an arraylist
		 * 		|	for object in world.getobjects
		 * 		|		if this.collisionWithObjectAtTheBottom(object) || this.collisionWithObjectAtTheTop(object) 
		 *		|			|| (this.collisionWithObjectToTheLeft(object) && this.getOrientation()==-1) || 
		 *		|			(this.collisionWithObjectToTheRight(object) && this.getOrientation()==1))
		 *		|			then AllCollidingObjects.add(object)
		 * @return all the colliding objects
		 * 		|	result == AllCollidingObjects
		 */
		public ArrayList<Object> allCollidingObjectsWithOrientation(World world) {
			ArrayList<Object> AllCollidingObjects = new ArrayList<Object>();
			if (world == null)
				return AllCollidingObjects;
			else {
				for (Object object: world.getObjects()) {
					if (this.collisionWithObjectAtTheBottom(object) || this.collisionWithObjectAtTheTop(object) 
							|| (this.collisionWithObjectToTheLeft(object) && this.getOrientation()==-1) || 
							(this.collisionWithObjectToTheRight(object) && this.getOrientation()==1))
						AllCollidingObjects.add(object);
				}
				return AllCollidingObjects;
			}
		}
		
		//********** COLLISION WITH IMPASSABLE TERRAIN ******************//
		
		/**
		 * Return whether or not the gameobject is colliding with impassable terrain to the right.
		 * 
		 * @return	One or more pixels of the right outer layer of this gameobject are overlapping with impassable terrain
		 * 			in his world.
		 * 		|	if this.getWorld() == null
		 * 		|		then result == false
		 * 		|		else result ==
		 * 		|			for any PixelY in this.getPixelPosition()[1]+1..this.getPixelPosition()[1]+this.getCurrentSprite().getHeight()-2:
		 * 		|				this.getWorld().getFeature(featureSymbol)).getType() == "IMPASSABLE"
		 */
		public boolean collidesWithITToTheRight() {
			if (this.getWorld() == null)
				return false;
			else {
				int PixelX = this.getPixelPosition()[0] + this.getCurrentSprite().getWidth();
				for (int PixelY = this.getPixelPosition()[1]+1; PixelY<= this.getPixelPosition()[1]+this.getCurrentSprite().getHeight()-2
						;PixelY++) {
					int featureSymbol = this.getWorld().getFeatureSymbolAtLocation(PixelX, PixelY);
					if ( (this.getWorld().getFeature(featureSymbol)).getType() == "IMPASSABLE" ) 
						return true;
				}
				return false;
			}
		}
		
		/**
		 * Return whether or not the gameobject is colliding with impassable terrain to the left.
		 * 
		 * @return	One or more pixels of the left outer layer of this gameobject are overlapping with impassable terrain
		 * 			in his world.
		 * 		|	if this.getWorld() == null
		 * 		|		then result == false
		 * 		|		else result ==
		 * 		|			for any PixelY in this.getPixelPosition()[1]+1..this.getPixelPosition()[1]+this.getCurrentSprite().getHeight()-2:
		 * 		|				(this.getWorld().getFeature(featureSymbol)).getType() == "IMPASSABLE"
		 */
		public boolean collidesWithITToTheLeft() {
			if (this.getWorld() == null)
				return false;
			else {
				int PixelX = this.getPixelPosition()[0];
				for (int PixelY = this.getPixelPosition()[1]+1; PixelY<= this.getPixelPosition()[1]+this.getCurrentSprite().getHeight()-2
						;PixelY++) {
					int featureSymbol = this.getWorld().getFeatureSymbolAtLocation(PixelX, PixelY);
					if ( (this.getWorld().getFeature(featureSymbol)).getType() == "IMPASSABLE" ) 
						return true;
				}
				return false;
			}
		}
		
		/**
		 * Return whether or not the gameobject is colliding with impassable terrain at his top.
		 * 
		 * @return	One or more pixels of the top outer layer of this gameobject are overlapping with impassable terrain
		 * 			in his world.
		 * 		|	if this.getWorld() == null
		 * 		|		then result == false
		 * 		|		else result ==
		 * 		|			for any PixelX in this.getPixelPosition()[0]+1..this.getPixelPosition()[0]+this.getCurrentSprite().getWidth()-1:
		 * 		|				(this.getWorld().getFeature(featureSymbol)).getType() == "IMPASSABLE"
		 */
		public boolean collidesWithITAtTheTop() {
			if (this.getWorld() == null)
				return false;
			else {
				int PixelY = this.getPixelPosition()[1]+this.getCurrentSprite().getHeight()+1;
				for (int PixelX = this.getPixelPosition()[0]+1; PixelX <= this.getPixelPosition()[0] + this.getCurrentSprite().getWidth()-1
						;PixelX++) {
					int featureSymbol = this.getWorld().getFeatureSymbolAtLocation(PixelX, PixelY);
					if ( (this.getWorld().getFeature(featureSymbol)).getType() == "IMPASSABLE" ) 
						return true;
				}
				return false;
			}
		}
		
		/**
		 * Return whether or not the gameobject is colliding with impassable terrain at his bottom.
		 * 
		 * @return	One or more pixels of the bottom outer layer of this gameobject are overlapping with impassable terrain
		 * 			in his world.
		 * 		|	if this.getWorld() == null
		 * 		|		then result == false
		 * 		|		else result ==
		 * 		|			for any PixelX in this.getPixelPosition()[0]+1..this.getPixelPosition()[0]+this.getCurrentSprite().getWidth()-1:
		 * 		|				(this.getWorld().getFeature(featureSymbol)).getType() == "IMPASSABLE"
		 */
		public boolean collidesWithITAtTheBottom() {
			if (this.getWorld() == null)
				return false;
			else {
				int PixelY = this.getPixelPosition()[1];
				for (int PixelX = this.getPixelPosition()[0]+1; PixelX <= this.getPixelPosition()[0] + this.getCurrentSprite().getWidth()-1
						;PixelX++) {
					int featureSymbol = this.getWorld().getFeatureSymbolAtLocation(PixelX, PixelY);
					if ( (this.getWorld().getFeature(featureSymbol)).getType() == "IMPASSABLE" ) 
						return true;
				}
				return false;
			}
		}
		
		/**
		 * Return whether or not the gameobject is resting on solid ground, i.e. it's bottom layer of pixels is on top of impassable terrain.
		 * 
		 * @return	One or more pixels of the bottom outer layer of this gameobject are on top of impassable terrain in his world.
		 * 		|	if this.getWorld() == null
		 * 		|		then result == false
		 * 		|		else result ==
		 * 		|			for any PixelX in this.getPixelPosition()[0]+1..this.getPixelPosition()[0]+this.getCurrentSprite().getWidth()-1:
		 * 		|				(this.getWorld().getFeature(featureSymbol)).getType() == "IMPASSABLE"
		 */
		public boolean restingOnSolidGround() {
			if (this.getWorld() == null)
				return false;
			else {
				int PixelY = this.getPixelPosition()[1];
				for (int PixelX = this.getPixelPosition()[0]+1; PixelX <= this.getPixelPosition()[0] + this.getCurrentSprite().getWidth()-1
						;PixelX++) {
					int featureSymbol = this.getWorld().getFeatureSymbolAtLocation(PixelX, PixelY-1);
					if ( (this.getWorld().getFeature(featureSymbol)).getType() == "IMPASSABLE" ) 
						return true;
				}
				return false;
			}
		}
		
		
		
	//************************************ STOP MOVING IF THE OBJECT IS DEAD *******************************************//

	/**
	 * Stop the movement of the Object when it's dead.
	 * 
	 * @effect	If this Object is dead the velocity and the acceleration of this Object will be set to zero.
	 * 		|	if (isDead()) 
			|		then setVelocity(0, 0) && setAcceleration(0, 0)
	 */
	public void stopMovingIfDead() {
		if (this.isDead()) {
			setVelocity(new double[ ]{0.0, 0.0});
			setAcceleration(new double[ ]{0.0, 0.0});
		}
	}
	
	//************************************ TIME AFTER COLLISION *******************************************//
		
		
	
	/**
	 * Return the time after collision of this gameobject.
	 */
	@Basic @Raw
	public double getTimeAfterCollision() {
		return this.TimeAfterCollision;
	}
	
	/**
	 * Check whether the given time after collision is a valid time after collision for
	 * any gameobject.
	 *  
	 * @param  double
	 *         The time after collision to check.
	 * @return 
	 *       | result == TimeAfterCollision>=0.0
	*/
	public static boolean isValidTimeAfterCollision(double TimeAfterCollision) {
		return (TimeAfterCollision>=0.0);
	}
	
	/**
	 * Set the time after collision of this gameobject to the given time after collision.
	 * 
	 * @param  TimeAfterCollision
	 *         The new time after collision for this gameobject.
	 * @post   If the given time after collision is a valid time after collision for any gameobject,
	 *         the time after collision of this new gameobject is equal to the given
	 *         time after collision.
	 *       | if (isValidTimeAfterCollision(double))
	 *       |   then this.TimeAfterCollision = TimeAfterCollision
	 */
	@Raw
	public void setTimeAfterCollision(double TimeAfterCollision) {
		if (isValidTimeAfterCollision(TimeAfterCollision))
			this.TimeAfterCollision = TimeAfterCollision;
	}
	
	/**
	 * Variable registering the time after collision of this gameobject.
	 */
	private double TimeAfterCollision = 0.6;

		
}
