package jumpingalien.model;

import java.util.TreeSet;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import jumpingalien.util.Sprite;

/**
 * A class that initializes new slimes.
 * 
 * @invar	|	canHaveAsID(id) 
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
public class Slime extends GameObject implements HorizontalMove, Comparable<Slime> {

	/**
	 * 
	 * @param id
	 * @param position
	 * @param school
	 * @param sprites
	 * 
	 * @post 	|	new.getId = id
	 * @effect 	|	allIDs.add(id)
	 * @post 	|	new.getPosition == position
	 * @post 	|	new.getSprites() == sprites
	 * @post 	|	new.getHitpoints() ==100
	 * @post 	|	new.getOrientation() == 1
	 * @post 	|	new.getAcceleration()== {0.7, 0.0}
	 * @post 	|	new.getSchool(school) == school
	 * @effect 	|	if school!=null
	 * 		   	|		then school.addSlime(this)
	 */
	public Slime(long id, double[] position,School school,Sprite[] sprites) {
		
		this.slimeID = new SlimeID(id);
		allIDs.add(slimeID);
		this.setNewPosition(position);
		this.setSprites(sprites);
		this.setHitpoints(100);
		this.setOrientation(1);
		this.setAcceleration(new double[] {0.7, 0.0});	
		this.setSchool(school);
		if (school != null)
			school.addSlime(this);
	}

	
	//******************************************** ID *******************************************************//

	
	@Basic
	@Raw
	@Immutable
	public long getId() {
		return this.slimeID.getSlimeID();
	}
	/**
	 * 
	 * @param id
	 * @return | (!(idAlreadyExists) && (id > 0))
	 */
	public static boolean canHaveAsID(long id) {
		boolean idAlreadyExists = false;
		for (SlimeID slimeID: allIDs) {
			if (slimeID.getSlimeID() == id)
				idAlreadyExists = true;
		}
		return (!(idAlreadyExists) && (id > 0));
			
	}
	
	/**
	 * 
	 * @effect | allIDs.clear();
	 */
	public static void cleanAllIDs() {
		allIDs.clear();
	}
	
	private final SlimeID slimeID;

	private static TreeSet<SlimeID> allIDs = new TreeSet<SlimeID>();
	
	
	
	//******************************************** SCHOOL *******************************************************//
	
	@Basic @Raw
	public School getSchool() {
		return this.school;
	}

	/**
	 * 
	 * @param  school
	 * @post   | new.getSchool() == school
	 */
	@Raw
	public void setSchool(School school) {
		this.school = school;
	}
	
	private School school;


	/**
	 * 
	 * @throws IllegalStateException
	 * @effect |	setHorizontalVelocity(0)
	 *		   |	setHorizontalAcceleration(0)
	 */
	public void endMove() throws IllegalStateException{
		assert(!this.isDead());
		setHorizontalVelocity(0);
		setHorizontalAcceleration(0);
	}
	
	/**
	 * 
	 * @effect |	if this.getWorld() == null 
	 * 		   |		then result == null
	 * @effect |	if this.CollidesWithITToTheRight() && this.getHorizontalVelocity()>0
	 *		   |		then this.endmove()
	 * @effect |	if this.CollidesWithITToTheLeft() && this.getHorizontalVelocity()<0
	 *		   |		then this.endmove()
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
		
	}
	
	/**
	 * @effect |	if this.getSchool()!=null
	 * 		   |		then for slime in this.getSchool().getSlimes()
	 * 		   |			if slime!= this then slime.setHitpoints(slime.getHitpoints()-1)
	 */
	public void decreaseSchoolSlimesHitpoint() {
		if (this.getSchool()!=null) {
			for (Slime slime: this.getSchool().getSlimes()) {
				if (slime !=this)
					slime.setHitpoints(slime.getHitpoints()-1);
			}
		}
	}
	
	
	//********************************************* ADVANCETIME ***********************************************//

	/**
	 * @param timeDuration
	 * @effect	|	this.stopMovingIfDead()
	 * @effect	|	this.collidesWithOtherObject(timeDuration)
	 * @effect	|	this.terminateDeadObject(timeDuration)
	 * @effect	|	this.checkImpassableTerrainCollision()
	 * @effect	|	this.overlappingWithFeatures(timeDuration)
	 * @effect	|	this.updatePositionAndVelocity(timeDuration)
	 */
	@Override
	public void advanceTimePartially(double timeDuration) {
		this.stopMovingIfDead();
		this.collidesWithOtherObject(timeDuration);
		this.terminateDeadObject(timeDuration);		
		this.checkImpassableTerrainCollision();
		this.overlappingWithFeatures(timeDuration);
		this.updatePositionAndVelocity(timeDuration);	
	}
	/**
	 * 
	 * @return	|	result == (this.getWorld() != null && !restingOnSolidGround())
	 */
	public boolean isFalling() {
		return (this.getWorld() != null && !restingOnSolidGround());
		
	}
	/**
	 * 
	 * @param timeDuration
	 * @effect |	if this.getworld()!= null
	 *		   |		then if allCollidingObjectsWithOrientation(this.getWorld()).isEmpty()
	 *		   |			then this.setHorizontalVelocity((this.getHorizontalVelocity() + this.getHorizontalAcceleration()*timeDuration))
	 * @effect |	else then this.setHorizontalVelocity((this.getHorizontalVelocity() + this.getHorizontalAcceleration()*timeDuration))
	 * @effect |	this.setPosition({hor_pos,verticalPosition})
	 */			
	public void updatePositionAndVelocity(double timeDuration) {
		double hor_pos = this.getHorizontalPosition();
		
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
		setNewPosition(new double[] {hor_pos,this.verticalPosition});
	}
		
	
	//********************************************* COLLISION ***********************************************//

	/**
	 * 
	 * @param time
	 * @effect 	|	if getworld() == null then
	 * 		   	|		result ==null
	 * @effect 	|	for object in allCollidingObjectsWithOrientation(getWorld())
	 * 		   	|		if !object.isDead() && object !=this && !isDead()
	 * @effect 	|	if (object instanceof Shark) then 
	 * 			|			if this.getHorizontalVelocity() != 0
	 * 			|				then setHitpoints(0);
	 *			|				decreaseSchoolSlimesHitpoint();
	 *			|				endMove()
	 *	 		|			if (((Shark) object).getHorizontalVelocity() != 0)
	 *			|				then (Shark) object).setHitpoints(((Shark) object).getHitpoints()+10
	 * @effect	|	else if object instanceof Mazub 
	 * 			|		if this.getTimeAfterCollision()>=0.6
	 * 			|			then setHitpoints(this.getHitpoints()-30)
	 * 			|			if object.isrunning()
	 * 			|				then object.setHitpoints(((Mazub) object).getHitpoints()-20)
	 * 			|			decreaseSchoolSlimesHitpoint()
	 *			|			endMove()
 	 *			|			setHorizontalAcceleration(0.7*getOrientation())
	 *			|			setTimeAfterCollision(0.0)
	 *			|		else then setTimeAfterCollision(getTimeAfterCollision()+time)
	 *			|			endMove()
	 *			|			setHorizontalAcceleration(0.7*getOrientation())
	 * @effect  |	else if object instanceof Slime then
	 *			|		setOrientation(this.getOrientation()*-1)
	 *			|		endMove()
	 *			|		setHorizontalAcceleration(0.7*this.getOrientation())
	 *			|		if getSchool()!= null && ((Slime) object).getSchool()!=null
	 *			|			then if getSchool().getSlimes().size()<((Slime) object).getSchool().getSlimes().size()
	 *			|				then getSchool().switchSchool(((Slime) object).getSchool(), this)	
	 */			
	public void collidesWithOtherObject(double time) {
		if (this.getWorld()==null)
			return;
		
		else {
			for (Object object: this.allCollidingObjectsWithOrientation(this.getWorld())) {
				if (!((GameObject) object).isDead() && object !=this && !this.isDead()) {
					if (object instanceof Shark) {
						if (this.getHorizontalVelocity() != 0) {
							this.setHitpoints(0);
							this.decreaseSchoolSlimesHitpoint();
							this.endMove();	
						}
						if (((Shark) object).getHorizontalVelocity() != 0)
							((Shark) object).setHitpoints(((Shark) object).getHitpoints()+10);

					}
					else if (object instanceof Mazub) {
						if (this.getTimeAfterCollision()>=0.6) {
							this.setHitpoints(this.getHitpoints()-30);
							if (((Mazub) object).isRunning())
								((Mazub) object).setHitpoints(((Mazub) object).getHitpoints()-20);
							this.decreaseSchoolSlimesHitpoint();
							this.endMove();
							this.setHorizontalAcceleration(0.7*this.getOrientation());
							this.setTimeAfterCollision(0.0);
						}
						else {
							this.setTimeAfterCollision(this.getTimeAfterCollision()+time);;
							this.endMove();
							this.setHorizontalAcceleration(0.7*this.getOrientation());
						}
					}
					else if (object instanceof Slime) {
						this.setOrientation(this.getOrientation()*-1);
						this.endMove();
						this.setHorizontalAcceleration(0.7*this.getOrientation());
						if (this.getSchool()!= null && ((Slime) object).getSchool()!=null) {
							if (this.getSchool().getSlimes().size()<((Slime) object).getSchool().getSlimes().size()) {
								this.getSchool().switchSchool(((Slime) object).getSchool(), this);
							}
						}
					}
				}
			}
		}
	}
	
	//********************************************* OVERLAPWITHFEATURES ***********************************************//

	/**
	 * @param dt 
	 * 
	 * @effect	|	if getWorld == null
	 * 			|		return;
	 * @effect	|	if OverlapsWithMagma() then
	 *			|		setHitpoints(0)
	 * @effect 	|	if OverlapsWithWater() then
	 *			|		timeOverlappingWater += dt
	 *			|		if timeOverlappingWater >= 0.4 then
	 *			|			setHitpoints(getHitpoints()-4)
	 * @effect 	|	if overlapsWithGgas() then
	 * 			|		if timeOverlappingGas >= 0.3 then
	 * 			|			setHitpoints(this.getHitpoints()+2);
	 */	
	public void overlappingWithFeatures(double dt) {
		if (this.getWorld() == null)
			return;
		if (this.overlapsWithMagma()) {
			this.setHitpoints(0);
		}	
		else if (this.overlapsWithWater()) {
			timeOverlappingWater += dt;
			if (timeOverlappingWater >= 0.4) {
				this.setHitpoints(this.getHitpoints()-4);
				this.decreaseSchoolSlimesHitpoint();
				timeOverlappingWater-=0.4;
			}
		} 
		else if (this.overlapsWithGas()) {
			timeOverlappingGas += dt;
			if (timeOverlappingGas >= 0.3) {
				this.setHitpoints(this.getHitpoints()+2);
				timeOverlappingGas-=0.3;
			}
		}
		
		else {
			timeOverlappingWater = 0.0;
			timeOverlappingGas = 0.0;
		}
	}
			

	private double timeOverlappingWater = 0.0;
	private double timeOverlappingGas = 0.0;

	//********************************************* SPRITES ***********************************************//

	

	
	public static boolean isValidSlimeSprites(Sprite[] sprites) {
		if (sprites == null)
			return false;
		for (int i = 0; i < sprites.length; i++) {
			if ((sprites[i] == null))
				return false;
		}
		return (sprites.length == 2);
	}

	@Override
	public Sprite getCurrentSprite() {
		Sprite[] sprites = getSprites();			
			if (getOrientation() > 0) 
				return sprites[0];
			else 
				return sprites[1];
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


	@Override
	public double getMaxHorizontalVelocity() {
		return 2.5;
	}


	@Override
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
	
	/**
	 * @param	otherSlime
	 * @return	
	 * 		|	if (otherSlime.getId() < this.getId())
	 * 		|	then result == -1
	 * 		|	else if (otherSlime.getId() > this.getId())
	 * 		|	then result == 1
	 * 		|	else
	 * 		|	then result == 0
	 * @throws 	ClassCastException
	 * 		|	(otherSlime == null)
	 */
	@Override
	public int compareTo(Slime other) throws ClassCastException { 
		
		if (other == null) throw new ClassCastException("Non-effective ID");
		
		if (other.getId() < this.getId())
			return -1;
		else if (other.getId() > this.getId())
			return 1;
		else
			return 0;
	}
	
}
