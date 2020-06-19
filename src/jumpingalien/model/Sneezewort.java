package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import jumpingalien.util.Sprite;

/**
 * A class that initializes new sneezeworts.
 *
 * @version	1.0
 * 
 * @author 	Arthur van Meerbeeck, 2e Bachelor Burgerlijk Ingenieur, Computerwetenschappen-Elektrotechniek
 * 
 * @author	Paul van Tieghem de Ten Berghe, 2e Bachelor Burgerlijk Ingenieur, Elektrotechniek-Computerwetenschappen
 * 
 * Github repository: https://github.com/KUL-ogp/ogp1819-project-van-meerbeeck-van-tieghem-de-ten-berghe.git
 */
public class Sneezewort extends Plant implements HorizontalMove {
	
	

	/**
	 * @param pixelposition
	 * @param sprites
	 * @post	| new.getNewPosition() == position
	 * @post 	| new.getOrientation() == -1
	 * @post 	| new.getHorizontalVelocity() == -0.5
	 * @post 	| new.getHitpoints() == 1
	 * @post 	| new.getSprites() == sprites
	 * @post 	| new.getTimeToDie() == 10.0
	 */
	public Sneezewort(double[] position,Sprite[] sprites) {
		this.setNewPosition(position);
		this.setOrientation(-1);
		this.setHorizontalVelocity(-0.5);
		this.setHitpoints(1);
		this.setSprites(sprites);
		this.setTimeToDie(10.0);
	}
	

	//*********************************** SPRITES ******************************************//

	
	/**
	 * @return | if (getOrientation()<0)
	 * 		   |	then result == getSprites()[0]
	 * 		   | else
	 * 		   |	then result == getSprites()[1]
	*/
	@Basic @Raw
	@Override
	public Sprite getCurrentSprite() {
		Sprite[] sprites = getSprites();			
			if (getOrientation() < 0) 
				return sprites[0];
			else 
				return sprites[1];
		}
	

//*********************************** ADVANCE TIME ******************************************//

	/**
	 * @param timeDuration
	 * @effect | while (timeDuration>0.0)
	 *		   | 	if (timeDuration>this.getTimeToDie)
	 *		   | 		then advanceTimePartially2(this.getTimeToDie)
	 *		   |		then setHitpoints(0)
	 * @effect | else if (timeDuration>timeToSwitch)
	 *		   | 	then advanceTimePartially2(timeToSwitch)
	 *		   |	then if (!isDead())
	 *		   |		then setVelocity(new double[] {getVelocity()[0],getVelocity()[1]});
	 *		   | 		then setOrientation(-getOrientation());
	 * @effect | else
	 *		   | 	then this.advanceTimePartially2(timeDuration)
	 *		   | 	then timeDuration = 0.0
	 */
	@Override
	public void advanceTimePartially(double timeDuration) {
		
		while (timeDuration>0.0) {
			
			if (timeDuration>this.getTimeToDie() && this.getTimeToDie() < timeToSwitch) {
				this.advanceTimePartially2(this.getTimeToDie());
				this.setHitpoints(0);
				timeDuration-=this.getTimeToDie();
				this.setTimeToDie(10.0);
			}
			else if (timeDuration>timeToSwitch) {
				this.advanceTimePartially2(timeToSwitch);
				if (!isDead()) {
					this.setHorizontalVelocity(-this.getHorizontalVelocity());
					this.setOrientation(-this.getOrientation());
				}
				timeDuration -= timeToSwitch;
				this.setTimeToDie(this.getTimeToDie()-timeToSwitch);

				timeToSwitch = 0.5;
			}
			else {
				this.advanceTimePartially2(timeDuration);
				timeToSwitch-=timeDuration;
				this.setTimeToDie(this.getTimeToDie()-timeDuration);
				timeDuration = 0.0;
			}
		}
	}
	/**
	 * @param timeDuration
	 * @effect | if (getWorld() != null) 
	 *		   | 	for (Object object: getWorld().getObjects())
	 *		   | 		if (object instanceof Mazub) 
	 *		   |			if (object.OverlapsWithObject(this)) then
	 *		   | 				object.EatingPlant(this,(Mazub) object)
	 * @effect | this.terminateDeadObject(timeDuration)
	 * @effect | if (!isDead())
	 *		   | 	then setNewPosition(new double[] {(getHorizontalPosition() + getVelocity()[0]*timeDuration),getPosition()[1]})
	 */
	public void advanceTimePartially2(double timeDuration) {
		if (this.getWorld() != null) {
			for (Object object: this.getWorld().getObjects()){
				if (object instanceof Mazub) {
					if (((GameObject) object).overlapsWithObject(this)){
						((GameObject) object).eatingPlant(this,(Mazub) object);
					}
				}	
			}
		}
		this.terminateDeadObject(timeDuration);
		if (! this.isDead()) {
			double new_hor_pos = (this.getHorizontalPosition() + this.getHorizontalVelocity()*timeDuration);
			double[] position = new double[] {new_hor_pos, this.verticalPosition};
			this.setNewPosition(position);
		}
	}
		
	private double timeToSwitch = 0.5;
	
	/**
	 * @return	| result == 1
	 */
	@Override @Basic
	protected int getMaxHitpoints() {
		return 1;
	}


	/*******************
	 * HORIZONTAL MOVE *
	 *******************/


	@Override @Basic
	public double getHorizontalPosition() {
		return this.horizontalPosition;
	}


	/**
 	 * @param  	horizontalPosition
 	 * @post | 	new.getHorizontalPosition == horizontalPosition;
 	 * @throws	IllegalArgumentException
 	 * 		 | 	!canHaveAsHorizontalPosition(horizontalPosition
 	 */
	@Override @Raw
	public void setHorizontalPosition(double horizontalPosition) {
		if (this.canHaveAsHorizontalPosition(horizontalPosition))
			this.horizontalPosition = horizontalPosition;
	}


	@Override @Basic
	public double getHorizontalVelocity() {
		return this.horizontalVelocity;
	}


	/**
 	 * @param  	horizontalVelocity
 	 * @return
 	 *       | 	result == (Math.abs(horizontalVelocity) <= getMaxHorizontalVelocity())
 	 */
	@Override
	public boolean isValidHorizontalVelocity(double horizontalVelocity) {
		return (Math.abs(horizontalVelocity) <= getMaxHorizontalVelocity());
	}


	/**
 	 * @param  	horizontalVelocity
 	 * @post | 	if (isValidHorizontalVelocity(horizontalVelocity)
 	 * 		 | 		then new.getHorizontalVelocity == horizontalVelocity
 	 */
	@Override @Raw
	public void setHorizontalVelocity(double horizontalVelocity) {
		if (this.isValidHorizontalVelocity(horizontalVelocity))
			this.horizontalVelocity = horizontalVelocity;
	}


	@Override @Basic
	public double getMaxHorizontalVelocity() {
		return 0.5;
	}


	@Override @Basic
	public double getHorizontalAcceleration() {
		return this.horizontalAcceleration;
	}


	/**
 	 * @param  	horizontalAcceleration
 	 * @post | 	new.getHorizontalAcceleration == horizontalAcceleration;
 	 */
	@Override
	public void setHorizontalAcceleration(double horizontalAcceleration) {
		this.horizontalAcceleration = horizontalAcceleration;
		
	}
	
	/**************
	 * TOTAL MOVE *
	 **************/
	
	@Override @Basic
	public double[] getPosition() {
		return new double[] {getHorizontalPosition(), this.verticalPosition};
	}
	
	/**
 	 * @param  	position
 	 * @post | 	new.getPosition == position;
 	 */
	@Override 
	public void setHorAndVerPosition(double[] position) {
		setHorizontalPosition(position[0]);
		this.verticalPosition = position[1];
	}
	
	@Override @Basic
	public double[] getVelocity() {
		return new double[] {getHorizontalVelocity(), this.verticalVelocity};
	}
	
	/**
 	 * @param  	velocity
 	 * @post | 	new.getVelocity == velocity;
 	 */
	@Override @Raw
	public void setVelocity(double[] velocity) {
		setHorizontalVelocity(velocity[0]);
	}
	
	@Override @Basic
	public double[] getAcceleration() {
		return new double[] {getHorizontalAcceleration(), this.verticalAcceleration};
	}
	
	/**
 	 * @param  	acceleration
 	 * @post | 	new.getAcceleration == acceleration;
 	 */
	@Override
	public void setAcceleration(double[] acceleration) {
		setHorizontalAcceleration(acceleration[0]);
	}

	@Override @Basic
	public int getOrientation() {
		return this.orientation;
	}
	
	/**
 	 * @param  orientation
 	 * @return | result == ((Math.abs(orientation) == 1) || orientation == 0)
	 */
	@Override
	public boolean isValidOrientation(int orientation) {
		return ((Math.abs(orientation) == 1) || orientation == 0);
	}

	/**
 	 * @param  	orientation
 	 * @pre	 | 	isValidOrientation(orientation)
 	 * @post | 	new.getAcceleration == acceleration;
 	 */
	@Override @Raw
	public void setOrientation(int orientation) {
		assert isValidOrientation(orientation);
		this.orientation = orientation;
	}
	
}
