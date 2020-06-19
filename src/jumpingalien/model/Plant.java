package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import jumpingalien.util.Sprite;

/**
 * @invar	|	isValidTimeToDie(TimeToDie)
 *
 * @version	2.0
 * 
 * @author 	Arthur van Meerbeeck, 2e Bachelor Burgerlijk Ingenieur, Computerwetenschappen-Elektrotechniek
 * 
 * @author	Paul van Tieghem de Ten Berghe, 2e Bachelor Burgerlijk Ingenieur, Elektrotechniek-Computerwetenschappen 
 */
public abstract class Plant extends GameObject {	
	
	//*********************************** TIME TO DIE ****************************************//
	
		
	/**
	 * Return the time to die of this plant.
	 */
	@Basic @Raw
	public double getTimeToDie() {
		return this.timeToDie;
	}
	
	/**
	 * Check whether the given time to die is a valid time to die for
	 * any plant.
	 *  
	 * @param  TimeToDie
	 * @return 
	 *       | result == TimeToDie>=0.0
	*/
	public static boolean isValidTimeToDie(double TimeToDie) {
		return TimeToDie>=0.0;
	}
	
	/**
	 * Set the time to die of this plant to the given time to die.
	 * 
	 * @param  TimeToDie
	 *   
	 * @post | if (isValidTimeToDie(TimeToDie))
	 *       |   then new.getTimeToDie() == TimeToDie
	 */
	@Raw
	public void setTimeToDie(double TimeToDie) {
		if (isValidTimeToDie(TimeToDie))
			this.timeToDie = TimeToDie;
	}
	

	private double timeToDie;

	
	//*********************************** SPRITES ******************************************//
	
	public static boolean isValidPlantSprites(Sprite[] sprites) {
		if (sprites == null)
			return false;
		for (int i = 0; i < sprites.length; i++) {
			if ((sprites[i] == null))
				return false;
		}
		return (sprites.length==2);
	}
	
	@Override
	public abstract Sprite getCurrentSprite();
}