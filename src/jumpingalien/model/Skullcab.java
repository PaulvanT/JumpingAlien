package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import jumpingalien.util.Sprite;

/**
 * A class that initializes skullcabs.
 *
 * @version	1.0
 * 
 * @author 	Arthur van Meerbeeck, 2e Bachelor Burgerlijk Ingenieur, Computerwetenschappen-Elektrotechniek
 * 
 * @author	Paul van Tieghem de Ten Berghe, 2e Bachelor Burgerlijk Ingenieur, Elektrotechniek-Computerwetenschappen 
 */
public class Skullcab extends Plant implements VerticalMove {

	/**
	 * 
	 * @param position
	 * @param sprites
	 * 
	 * @post |	new.getNewPosition() == position
	 * @post |	new.getOrientation() == 1
	 * @post |	new.getVerticalVelocity() == 0.5
	 * @post |	new.getHitpoints() == 3
	 * @post |	new.getSprites() == sprites
	 * @post |	new.getTimeToDie() == 12.0
	 */
	public Skullcab(double[] position,Sprite[] sprites) {
		this.setNewPosition(position);
		this.setOrientation(1);
		this.setVerticalVelocity(0.5);
		this.setHitpoints(3);
		this.setSprites(sprites);
		this.setTimeToDie(12.0);
	}
	
	
	/**
	 * @param timeDuration
	 * @effect 	|	while timeDuration>0.0
	 * 			|		if timeDuration>this.getTimeToDie() && this.getTimeToDie() < timeToSwitch then
	 * 			|			this.advanceTimePartially2(this.getTimeToDie())
	 *			|			this.setHitpoints(0)
	 *			|			timeDuration-=this.getTimeToDie()
	 *			|			this.setTimeToDie(12.0)
	 *@effect 	|	else if timeDuration>timeToSwitch then
	 *			|		this.advanceTimePartially2(timeToSwitch)
	 *			|		if !isDead() 
	 *			|			this.setVelocity(this.getVelocity()[0],-this.getVelocity()[1])
	 *			|		timeDuration -= timeToSwitch
	 *			|		this.setTimeToDie(this.getTimeToDie()-timeToSwitch)
	 *			|		timeToSwitch = 0.5
	 *@effect	|	else then
	 *			|		this.advanceTimePartially2(timeDuration)
	 *			|		timeToSwitch-=timeDuration
	 *			|		this.setTimeToDie(this.getTimeToDie()-timeDuration)
	 *			|		timeDuration = 0.0
	 */
	@Override
	public void advanceTimePartially(double timeDuration) {
		
		while (timeDuration>0.0) {
			
			if (timeDuration>this.getTimeToDie() && this.getTimeToDie() < timeToSwitch) {
				this.advanceTimePartially2(this.getTimeToDie());
				this.setHitpoints(0);
				timeDuration-=this.getTimeToDie();
				this.setTimeToDie(12.0);
			}
			else if (timeDuration>timeToSwitch) {
				this.advanceTimePartially2(timeToSwitch);
				if (!isDead()) {
					this.setVerticalVelocity(-this.getVelocity()[1]);
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
	 *		   |			if (object.OverlapsWithObject(this))  
	 *		   |				if (this.getTimeAfterCollision()>=0.6) then
	 *		   | 					object.EatingPlant(this,(Mazub) object)
	 *		   |					this.setTimeAfterCollision(0.0)
	 *		   |				else then this.setTimeAfterCollision(this.getTimeAfterCollision()+timeDuration)
	 * @effect | this.terminateDeadObject(timeDuration)
	 * @effect | if (!isDead()) 
	 *		   | 	then setNewPosition(new double[] {(getHorizontalPosition() + getVelocity()[0]*timeDuration),getPosition()[1]})
	 */
	public void advanceTimePartially2(double timeDuration) {
		
		if (this.getWorld() != null) {
			for (Object object: this.getWorld().getObjects()){
				if (object instanceof Mazub) {
					if (((GameObject) object).overlapsWithObject(this)){
						if (this.getTimeAfterCollision()>=0.6) {
							((GameObject) object).eatingPlant(this,(Mazub) object);
							this.setTimeAfterCollision(0.0);
						}
						else
							this.setTimeAfterCollision(this.getTimeAfterCollision()+timeDuration);
						
					}
					else
						this.setTimeAfterCollision(0.6);
				}	
			}
		}
		this.terminateDeadObject(timeDuration);
		if (! this.isDead()) {
			double new_ver_pos = (this.getVerticalPosition() + this.getVelocity()[1]*timeDuration);
			double[] position = new double[] {this.horizontalPosition,new_ver_pos};
			this.setNewPosition(position);
		}
	}
	
	private double timeToSwitch = 0.5;
	
	
	/**
	 * @return | if (getVerticalVelocity()>0)
	 * 		   |	then result == getSprites()[0]
	 * 		   | else
	 * 		   |	then result == getSprites()[1]
	*/
	@Basic @Raw
	@Override
	public Sprite getCurrentSprite() {
		Sprite[] sprites = getSprites();			
			if (this.getVerticalVelocity()>0) 
				return sprites[0];
			else 
				return sprites[1];
		}
	/**
	 * @return |	result == 3
	 */
	@Override @Basic
	protected int getMaxHitpoints() {
		return 3;
	}
	

	/*****************
	 * VERTICAL MOVE *
	 *****************/

	@Override @Basic
	public double getVerticalPosition() {
		return this.verticalPosition;
	}


	/**
 	 * @param  	verticalPosition
 	 * @post | 	new.getVerticalPosition == verticalPosition
 	 * @throws	IllegalArgumentException
 	 * 		 | 	!canHaveAsVerticalPosition(verticalPosition)
 	 */
	@Override @Raw
	public void setVerticalPosition(double verticalPosition) {
		if (this.canHaveAsVerticalPosition(verticalPosition))
			this.verticalPosition = verticalPosition;
	}

	@Override @Basic
	public double getVerticalVelocity() {
		return this.verticalVelocity;
	}
	
	/**
 	 * @param  	verticalVelocity
 	 * @return
 	 *       | 	(Math.abs(verticalVelocity) <= getMaxVerticalVelocity())
 	 */
	@Override
	public boolean isValidVerticalVelocity(double verticalVelocity) {
		return (Math.abs(verticalVelocity) <= getMaxVerticalVelocity());
	}


	/**
 	 * @param  	verticalVelocity
 	 * @post | 	if (isValidVerticalVelocity(verticalVelocity)
 	 * 		 | 		then new.getVerticalVelocity == verticalVelocity
 	 */
	@Override
	public void setVerticalVelocity(double verticalVelocity) {
		if (isValidVerticalVelocity(verticalVelocity))
			this.verticalVelocity = verticalVelocity;
	}


	@Override @Basic
	public double getMaxVerticalVelocity() {
		return 0.5;
	}


	@Override @Basic
	public double getVerticalAcceleration() {
		return this.verticalAcceleration;
	}


	/**
 	 * @param  	verticalAcceleration
 	 * @post | 	new.getVerticalAcceleration == verticalAcceleration;
 	 */
	@Override
	public void setVerticalAcceleration(double verticalAcceleration) {
		this.verticalAcceleration = verticalAcceleration;	
	}
	
	/**************
	 * TOTAL MOVE *
	 **************/


	@Override @Basic
	public double[] getPosition() {
		return new double[] {this.horizontalPosition, getVerticalPosition()};
	}

	/**
 	 * @param  	position
 	 * @post | 	new.getPosition == position;
 	 */
	@Override
	public void setHorAndVerPosition(double[] position) {
		this.horizontalPosition = position[0];
		setVerticalPosition(position[1]);
	}


	@Override @Basic
	public double[] getVelocity() {
		return new double[] {this.horizontalVelocity, getVerticalVelocity()};
	}


	/**
 	 * @param  	velocity
 	 * @post | 	new.getVelocity == velocity;
 	 */
	@Override @Raw
	public void setVelocity(double[] velocity) {
		setVerticalVelocity(velocity[1]);
	}


	@Override @Basic
	public double[] getAcceleration() {
		return new double[] {this.horizontalAcceleration, getVerticalAcceleration()};
	}


	/**
 	 * @param  	acceleration
 	 * @post | 	new.getAcceleration == acceleration;
 	 */
	@Override
	public void setAcceleration(double[] acceleration) {
		setVerticalAcceleration(acceleration[1]);
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
