package jumpingalien.model;


import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import jumpingalien.util.Sprite;
/**
 * A class that initializes new sharks.
 * 
 * @invar 	|	isValidMaxHorizontalVelocity(maxHorizontalVelocity)
 * 
 * @invar	|	isValidMinHorizontalVelocity(minHorizontalVelocity)
 * 
 * @invar	|	canHaveAsVerticalVelocity(verticalVelocity)
 *
 * @version	1.0
 * 
 * @author 	Arthur van Meerbeeck, 2e Bachelor Burgerlijk Ingenieur, Computerwetenschappen-Elektrotechniek
 * 
 * @author	Paul van Tieghem de Ten Berghe, 2e Bachelor Burgerlijk Ingenieur, Elektrotechniek-Computerwetenschappen
 * 
 * Github repository: https://github.com/KUL-ogp/ogp1819-project-van-meerbeeck-van-tieghem-de-ten-berghe.git
 */
public class Shark extends GameObject implements HorizontalMove, VerticalMove {
	/**
	 * 
	 * @param position
	 * @param sprites
	 * 
	 * @post	|	new.getPosition() == position
	 * @post	|	new.getSprites() == sprites
	 * @post	|	new.getAcceleration() == {-1.5,-10.0}
	 * @post	|	new.getHitpoints() == 100
	 */
	public Shark(double[] position,Sprite[] sprites) {
		
		this.setNewPosition(position);
		this.setSprites(sprites);
		this.setAcceleration(new double[] {-1.5,-10.0});
		this.setHitpoints(100);	
	}
	
	/** 
	 * @pre		|	isRunning()
	 * @pre 	|	!isDead()
	 * @effect 	|	setHorizontalVelocity(0)
	 * @effect 	|	setHorizontalAcceleration(0)
	 */
	public void endMove() throws IllegalStateException{
		assert(this.isRunning() || !this.isDead());
		setHorizontalVelocity(0);
		setHorizontalAcceleration(0);
	}
	
	/** 
	 * 
	 * @return 	|	result == getHorizontalVelocity() != 0
	 */
	public boolean isRunning() {
		return (getHorizontalVelocity() != 0);
	}

//********************************************* JUMPING ********************************************************//
	
	/** 
	 * 
	 * @effect 	|	isJumping = true
	 * @effect 	|	setVerticalVelocity(8.0)
	 * @effect 	| 	setVerticalAcceleration(-10.0)
	 * @throws 	IllegalStateException
	 * 			|	isJumping || isDead()
	 */
	public void startJump() throws IllegalStateException{
		if (isJumping || this.isDead()) throw new IllegalStateException();
		isJumping = true;
		setVerticalVelocity(2.0);
		setVerticalAcceleration(-10.0);
	}
	

	public boolean isJumping = false;
	
	/**
	 * @effect  | if this.getWorld() == null || restingOnSolidGround() || this.topPerimeterOverlappingWater()
	 * 			|	then result == false
	 * @return 	| result == ((!isJumping) && (getVerticalPosition() > 0))
	 */
	public boolean isFalling() {
		if (this.getWorld() == null || restingOnSolidGround() || this.topPerimeterOverlappingWater())
			return false;
		else
			return ((this.getVerticalVelocity() < 0) || (!isJumping));
	}
	
	/**
	 * @effect	| if this.getWorld() == null
	 * 			|	result == false
	 * @return	| result == for any pixelX in this.getPixelPosition()[0]+1..this.getPixelPosition()[0] + this.getCurrentSprite().getWidth()-1
	 * 			|	featureSymbol result == Feature.WATER.getSymbol()
	 */
	public boolean topPerimeterOverlappingWater() {
		if (this.getWorld() == null)
			return false;
		else {
			int PixelY = this.getPixelPosition()[1]+this.getCurrentSprite().getHeight()+1;
			for (int PixelX = this.getPixelPosition()[0]+1; PixelX <= this.getPixelPosition()[0] + this.getCurrentSprite().getWidth()-1
					;PixelX++) {
				int featureSymbol = this.getWorld().getFeatureSymbolAtLocation(PixelX, PixelY);
				if ( featureSymbol == Feature.WATER.getSymbol() ) 
					return true;
			}
			return false;
		}
	}
	
	/**
	 * 
	 * @effect | if this.getWorld() == null 
	 * 		   |	then result == null
	 * @effect | if this.CollidesWithITToTheRight() && this.getHorizontalVelocity()>0
	 *		   |	then this.endmove()
	 * @effect | if this.CollidesWithITToTheLeft() && this.getHorizontalVelocity()<0
	 *		   |	then this.endmove()
	 * @effect | if this.CollidesWithITAtTheTop() && this.getVerticalVelocity()>0
	 * 		   |	then this.isJumping = false
	 *		   |		 this.setVerticalVelocity(0.0)
	 *		   |		 this.setVerticalAcceleration(-10.0);
	 * @effect | if this.CollidesWithITAtTheBottom() && this.getVerticalVelocity()<0
	 * 		   |	then this.setVerticalVelocity(0.0)
	 *		   |		 this.setVerticalAcceleration(0.0)
	 */
	public void checkImpassableTerrainCollision() {
		if (this.getWorld()==null) {
			return;
		}
		if (this.collidesWithITToTheRight() && this.getHorizontalVelocity()>0) {
			this.endMove();
			}
		else if (this.collidesWithITToTheLeft() && this.getHorizontalVelocity()<0) {
			this.endMove();
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
	
	/**
	 * 
	 * @effect | if this.getWorld() == null 
	 * 		   |	then result == null
	 * @effect | if this.CollidesWithanObjectToTheRight() && this.getHorizontalVelocity()>0
	 *		   |	then this.endmove()
	 * @effect | if this.CollidesWithanObjectToTheLeft() && this.getHorizontalVelocity()<0
	 *		   |	then this.endmove()
	 * @effect | if this.CollidesWithObjectAtTheTop() && this.getVerticalVelocity()>0
	 * 		   |	then this.isJumping = false
	 *		   |		 this.setVerticalVelocity(0.0)
	 *		   |		 this.setVerticalAcceleration(-10.0);
	 * @effect | if this.CollidesWithObjectAtTheBottom() && this.getVerticalVelocity()<0
	 * 		   |	then this.setVerticalVelocity(0.0)
	 *		   |		 this.setVerticalAcceleration(0.0)
	 */
	public void checkObjectCollision() {
		if (this.getWorld()== null) {
			return;
		}
		if (this.collidesWithanObjectToTheRight()) {
			this.endMove();
		}
		else if (this.collidesWithanObjectToTheLeft()) {
			this.endMove();
		}
		else {
			this.setHorizontalAcceleration(1.5*this.getOrientation());
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
	
	/**
	 * 
	 * @param timeDuration
	 * @effect | if this.isFalling()
	 * 		   |	then setVerticalAcceleration(-10);
	 * @effect | if topPerimeterOverlappingWater() && (!isJumping)
	 * 		   |	setVerticalVelocity(0.0)
	 *		   |	setVerticalAcceleration(0.0)
	 * @effect | if this.isFalling() or this.isJumping
	 * 		   |	then ver_pos.equals(getVerticalPosition() + getVerticalVelocity()*timeDuration + 0.5*getVerticalAcceleration()*(Math.pow(timeDuration,2)))
	 *		   |		 this.setVerticalVelocity((getVerticalVelocity() + getVerticalAcceleration()*timeDuration)
	 * @effect | if this.getworld()!= null
	 *		   |	then if allCollidingObjectsWithOrientation(this.getWorld()).isEmpty()
	 *		   |		then this.setHorizontalVelocity((this.getHorizontalVelocity() + this.getHorizontalAcceleration()*timeDuration))
	 * @effect | else then this.setHorizontalVelocity((this.getHorizontalVelocity() + this.getHorizontalAcceleration()*timeDuration))
	 * @effect |	this.setPosition({hor_pos,ver_pos})
	 */			
	public void updatePositionAndVelocity(double timeDuration) {
		double hor_pos = this.getHorizontalPosition();
		double ver_pos = this.getVerticalPosition();
		
		if (this.isFalling()) {
			setVerticalAcceleration(-10);
		}
		
		if (topPerimeterOverlappingWater() && (!isJumping)) {
			setVerticalVelocity(0.0);
			setVerticalAcceleration(0.0);
		}
		
		if (this.isFalling() || this.isJumping) {
			ver_pos = (getVerticalPosition() + getVerticalVelocity()*timeDuration + 0.5*getVerticalAcceleration()*(Math.pow(timeDuration,2)));
			this.setVerticalVelocity((getVerticalVelocity() + getVerticalAcceleration()*timeDuration));
		}
		
		if (this.getWorld()!=null) {
			if (this.allCollidingObjectsWithOrientation(this.getWorld()).isEmpty())	{
				hor_pos = (getHorizontalPosition() + getHorizontalVelocity()*timeDuration + 0.5*getHorizontalAcceleration()*(Math.pow(timeDuration,2)));
				this.setHorizontalVelocity((this.getHorizontalVelocity() + this.getHorizontalAcceleration()*timeDuration));
			}
		}
		else {
			hor_pos = (getHorizontalPosition() + getHorizontalVelocity()*timeDuration + 0.5*getHorizontalAcceleration()*(Math.pow(timeDuration,2)));
			this.setHorizontalVelocity((this.getHorizontalVelocity() + this.getHorizontalAcceleration()*timeDuration));
		}
	
		this.setNewPosition(new double[] {hor_pos,ver_pos});
	}

	
	//******************************************** ADVANCETIME *******************************************************//


	/**
	 * @param 	timeDuration
	 * 
	 * @effect |	while (timeDuration>0)
	 * 		   |		stopMovingIfDead()				
	 * 		   |		terminateDeadObject(timeDuration)
	 * 		   |		checkImpassableTerrainCollision()		
	 * 		   |		contactWithOtherObject(timeDuration)		
	 * 		   |		checkObjectCollision()
	 * 		   |		if (hasToRest && remainingRestingTime>timeDuration)	 
	 * 		   |		   then	notOverlappingWater(timeDuration)
	 * 		   |				updatePositionAndVelocity(timeDuration)
	 * 		   |		else if (hasToRest && remainingRestingTime<=timeDuration)
	 * 		   |		   then	notOverlappingWater(remainingRestingTime)
	 * 		   |				updatePositionAndVelocity(remainingRestingTime)
	 * 		   |		else if (startOfNewPeriod)
	 * 		   |		   then	notOverlappingWater(timeDuration)
	 * 		   |				setOrientation(oldOrientation*(-1))
	 * 		   |				setHorizontalAcceleration(1.5*this.getOrientation())
	 * 		   |				if (restingOnSolidGround() || overlapsWithWater())
	 * 		   |				   then	setVerticalVelocity(2.0)
	 * 		   |						setVerticalAcceleration(-10)
	 * 		   |				updatePositionAndVelocity(timeDuration)
	 * 		   |		else if (timeDuration>remainingPeriodTime)
	 * 		   |		   then	notOverlappingWater(remainingPeriodTime)	
	 * 		   |				updatePositionAndVelocity(remainingPeriodTime)
	 * 		   |				endMove()
	 * 		   |				setOrientation(0)
	 * 		   |		else
	 * 		   |		   then	notOverlappingWater(timeDuration)			
	 * 		   |				updatePositionAndVelocity(timeDuration)
	 */
	@Override
	public void advanceTimePartially(double timeDuration) {

		while (timeDuration>0) {
			stopMovingIfDead();
			terminateDeadObject(timeDuration);
			checkImpassableTerrainCollision();
			contactWithOtherObject(timeDuration);
			checkObjectCollision();
			//During resting period of 1.0s and remainingRestingTime > timeDuration
			if (hasToRest && remainingRestingTime>timeDuration) {
				isJumping = false;
				notOverlappingWater(timeDuration);
				this.updatePositionAndVelocity(timeDuration);
				remainingRestingTime-=timeDuration;
				timeDuration=0.0;
			}
			//During resting period of 1.0s and remainingRestingTime <= timeDuration
			else if (hasToRest && remainingRestingTime<=timeDuration) {
				notOverlappingWater(remainingRestingTime);
				this.updatePositionAndVelocity(remainingRestingTime);
				timeDuration-=remainingRestingTime;
				hasToRest=false;
				remainingRestingTime = 1.0;
				remainingPeriodTime=0.5;
				this.startOfNewPeriod=true;
			}
			
			//Start of a new active period
			else if (startOfNewPeriod) {
				notOverlappingWater(timeDuration);
				this.setOrientation(oldOrientation*(-1));
				oldOrientation *= -1;
				this.setHorizontalAcceleration(1.5*this.getOrientation());
				if (this.restingOnSolidGround() || this.overlapsWithWater()) {
					this.isJumping=true;
					this.setVerticalVelocity(2.0);
					this.setVerticalAcceleration(-10);
				}
				this.updatePositionAndVelocity(timeDuration);
				remainingPeriodTime = 0.5-timeDuration;
				timeDuration=0.0;
				startOfNewPeriod=false;
			}
			
			//During an active period of 0.5s and timeDuration > remainingPeriodTime	
			else if (timeDuration>remainingPeriodTime) {
				notOverlappingWater(remainingPeriodTime);
				this.startOfNewPeriod=false;
				timeDuration-=remainingPeriodTime;
				this.updatePositionAndVelocity(remainingPeriodTime);
				this.endMove();
				hasToRest=true;
				this.setOrientation(0);
				remainingPeriodTime= 0.5;
			}
			
			//During an active period of 0.5s and timeDuration < remainingPeriodTime
			else {
				notOverlappingWater(timeDuration);
				this.startOfNewPeriod=false;
				this.updatePositionAndVelocity(timeDuration);
				remainingPeriodTime-=timeDuration;
				timeDuration=0.0;
			}
		}
	}
	
	/**
	 * 
	 * @param timeDuration
	 * @effect |	if (getworld()==null)
	 * 		   |		then return
	 * @effect |	if !this.overlapsWithWater()
	 * 		   |		if timeNotInWater+timeDuration >= 0.2 
	 *		   |			then this.setHitpoints(this.getHitpoints() - 6)
	 * @effect |		else then timeNotInWater+=timeDuration
	 * 
	 */
	public void notOverlappingWater(double timeDuration) {
		if (this.getWorld()==null)
			return;
		if (! this.overlapsWithWater()) {
			if (timeNotInWater+timeDuration >= 0.2) {
				this.setHitpoints(this.getHitpoints() - 6);
				timeNotInWater+= (timeDuration-0.2);
			}
			else
				timeNotInWater+=timeDuration;
		}
		else 
			timeNotInWater = 0.0;
	}
	
	/**
	 * 
	 * @param 		time
	 * @effect |	if (getWorld()==null)	
	 * 		   |		then return
	 * 		   |	else
	 * 		   |		then for (Object object: allCollidingObjects(getWorld()))
	 * 		   |			if (!((GameObject) object).isDead() && object != this)
	 * 		   |				then if (object instanceof Mazub)
	 * 		   |					then if (this.getTimeAfterCollision()>=0.6)
	 * 		   |					   then setHitpoints(getHitpoints()-50)
	 * 		   |							endMove();
	 * 		   |							setHorizontalAcceleration(1.5*getOrientation())
	 * 		   |							if (((Mazub) object).isRunning())
	 * 		   |								then ((Mazub) object).setHitpoints(((Mazub) object).getHitpoints()-50)
	 * 		   |							setTimeAfterCollision(0.0)
	 * 		   |					else
	 * 		   |					   then	setTimeAfterCollision(getTimeAfterCollision()+time)
	 * 		   |							endMove()
	 * 		   |							setHorizontalAcceleration(1.5*getOrientation())
	 * 		   |				else if (object instanceof Shark)
	 * 		   |				   then	endMove()
	 * 		   |				else if (object instanceof Slime)
	 * 		   |				   then	if ((getHorizontalVelocity() != 0) && (!hasBouncedAgainstSlime))
	 * 		   |					  then	setHitpoints(getHitpoints()+10)
	 * 
	 */
	public void contactWithOtherObject(double time) {
		if (this.getWorld()==null)
			return;
		
		else {
			for (Object object: this.allCollidingObjects(this.getWorld())) {
				if (!((GameObject) object).isDead() && object != this) {
					if (object instanceof Mazub) {
						if (this.getTimeAfterCollision()>=0.6) {
							this.setHitpoints(this.getHitpoints()-50);
							this.endMove();
							this.setHorizontalAcceleration(1.5*this.getOrientation());
							if (((Mazub) object).isRunning())
								((Mazub) object).setHitpoints(((Mazub) object).getHitpoints()-50);
							this.setTimeAfterCollision(0.0);
						}
						else {
							this.setTimeAfterCollision(this.getTimeAfterCollision()+time);
							this.endMove();
							this.setHorizontalAcceleration(1.5*this.getOrientation());

						}
					}
					else if (object instanceof Shark) {
						this.endMove();
					}
					
					else if (object instanceof Slime) {
						if ((this.getHorizontalVelocity() != 0) && (!hasBouncedAgainstSlime)) {
							this.setHitpoints(this.getHitpoints()+10);
							hasBouncedAgainstSlime = true;
						}
						if ( ((Slime) object).getHorizontalVelocity() != 0 ) 
							((Slime) object).setHitpoints(0);
	
					}
					else
						hasBouncedAgainstSlime = false;
				}
				
			}
		}
	}
		
	
	public double timeNotInWater = 0.0;
	public double remainingPeriodTime = 0.5;
	public double remainingRestingTime = 1;
	public boolean hasToRest = false;
	public int oldOrientation = 1;
	public boolean startOfNewPeriod = true;
	private boolean hasBouncedAgainstSlime = false;
	
	//******************************************** SPRITES *******************************************************//

	public static boolean isValidSharkSprites(Sprite[] sprites) {
		if (sprites == null)
			return false;
		for (int i = 0; i < sprites.length; i++) {
			if ((sprites[i] == null))
				return false;
		}
		return (sprites.length==3);
	}
	
	@Override
	public Sprite getCurrentSprite() {
		Sprite[] sprites = this.getSprites();
		if (this.getOrientation() == 0)
			return sprites[0];
		else if (this.getOrientation() == -1)
			return sprites[1];
		else 
			return sprites[2];
	}
	/**
	 * @return |	result == 500
	 */
	@Override
	protected int getMaxHitpoints() {
		return 500;
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
		return 0.75;
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
 	 *       | 	result == (verticalVelocity) <= getMaxHorizontalVelocity())
 	 */
	@Override
	public boolean isValidVerticalVelocity(double verticalVelocity) {
		return (verticalVelocity <= getMaxVerticalVelocity());
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
		return 2.0;
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
		return new double[] {getHorizontalPosition(), getVerticalPosition()};
	}
	
	/**
 	 * @param  	position
 	 * @post | 	new.getPosition == position;
 	 */
	@Override 
	public void setHorAndVerPosition(double[] position) {
		setHorizontalPosition(position[0]);
		setVerticalPosition(position[1]);
	}
	
	@Override @Basic
	public double[] getVelocity() {
		return new double[] {getHorizontalVelocity(), getVerticalVelocity()};
	}
	
	/**
 	 * @param  	velocity
 	 * @post | 	new.getVelocity == velocity;
 	 */
	@Override @Raw
	public void setVelocity(double[] velocity) {
		setHorizontalVelocity(velocity[0]);
		setVerticalVelocity(velocity[1]);
	}

	@Override @Basic
	public double[] getAcceleration() {
		return new double[] {getHorizontalAcceleration(), getVerticalAcceleration()};
	}
	
	/**
 	 * @param  	acceleration
 	 * @post | 	new.getAcceleration == acceleration;
 	 */
	@Override
	public void setAcceleration(double[] acceleration) {
		setHorizontalAcceleration(acceleration[0]);
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
