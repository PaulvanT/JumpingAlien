package jumpingalien.model;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * @invar	|	isValidSlime(Slime slime)
 * 
 * @version	1.0
 * 
 * @author 	Arthur van Meerbeeck, 2e Bachelor Burgerlijk Ingenieur, Computerwetenschappen-Elektrotechniek
 * 
 * @author	Paul van Tieghem de Ten Berghe, 2e Bachelor Burgerlijk Ingenieur, Elektrotechniek-Computerwetenschappen 
 */
public class School {
	/**
	 * 
	 * @param world
	 * @effect	|	if world !=null
	 * 			|		world.addSchool(this)
	 * @post	|	this.getWorld() == world
	 */
	public School(World world) {
		if (world != null)
			world.addSchool(this);
		setWorld(world);
	}
	
	
	//********************************** SCHOOL ***************************************//
	
	public Set<Slime> getSlimes() {
		Set<Slime> copy = new HashSet<>(this.slimes);
		return copy;
	}
	/**
	 * @param slime
	 * @return	|	(slime != null) && (!((GameObject) slime).isTerminated()) && (!this.isTerminated())
	 */
	public boolean isValidSlime(Slime slime) {
		return ((slime != null) && (!((GameObject) slime).isTerminated()) && (!this.isTerminated()));
	} 
	
	/**
	 * 
	 * @param slime
	 * @effect |	slime.setSchool(this)
	 * 		   |	slimes.add(slime)
	 * @throws IllegalArgumentException
	 * 		|  ! this.isValidSlime(slime)
	 */
	@Raw
	public void addSlime(Slime slime) throws IllegalArgumentException{
		if (! this.isValidSlime(slime))//|| slime.getSchool()!=null)
			throw new IllegalArgumentException("illegalSlime");
		else  {
			slime.setSchool(this);
			slimes.add(slime);
		}
	}
	
	/**
	 * 
	 * @param slime
	 * @effect	|	slime.setSchool(null)
	 *			|	slimes.remove(slime)
	 * @throws 	IllegalArgumentException
	 * 			|	!(slime.getSchool()== this)
	 */
	public void removeSlime(Slime slime) throws IllegalArgumentException{
		if (!(slime.getSchool()== this)) 
			throw new IllegalArgumentException("Slime not in this school");
		
		slime.setSchool(null);
		slimes.remove(slime);	
	}
	
	/**
	 * 
	 * @param newSchool
	 * @param slime
	 * 
	 * @effect	|	if newSchool == slime.getSchool()
	 * 			|		then result == null
	 * @effect	|	this.removeSlime(slime)
	 * @effect	|	for slimeInOldSchool in this.getslimes
	 * 			|		slime.setHitpoints(slime.getHitpoints()-1);
	 *			|		slimeInOldSchool.setHitpoints(slimeInOldSchool.getHitpoints()+1)
	 * @effect	|	for slimeInNNewSchool in newSchool.getSlimes()
	 *			|		slimeInNewSchool.setHitpoints(slimeInNewSchool.getHitpoints()-1)
	 *			|		slime.setHitpoints(slime.getHitpoints()+1)
	 * @effect 	|	newSchool.addSlime(slime)
	 * @effect	|	slime.setSchool(newSchool)
	 */
	public void switchSchool(School newSchool,Slime slime) {
		if (newSchool == slime.getSchool())
			return;
		else {
			this.removeSlime(slime);
			for (Slime slimeInOldSchool: this.getSlimes()) {
				slime.setHitpoints(slime.getHitpoints()-1);
				slimeInOldSchool.setHitpoints(slimeInOldSchool.getHitpoints()+1);
			}
			for (Slime slimeInNewSchool: newSchool.getSlimes()) {
				slimeInNewSchool.setHitpoints(slimeInNewSchool.getHitpoints()-1);
				slime.setHitpoints(slime.getHitpoints()+1);
			}
			newSchool.addSlime(slime);
			slime.setSchool(newSchool);
		}
		
		
	}
	
	TreeSet<Slime> slimes = new TreeSet<Slime>();
	
	
	//******************************************** WORLD *******************************************************//
	
	
	@Basic @Raw
	public World getWorld() {
		return this.world;
	}

	/**
	 * 
	 * @param  world
	 * @post   | new.getWorld() == world
	 */
	@Raw
	public void setWorld(World world) {
		this.world = world;
	}
	

	private World world;
	
	
	//********************************** TERMINATE ***************************************//
	
	/**
	 *
	 * @post   | new.isTerminated()
	 */
	public void terminateschool() {
		this.isTerminated = true;
	}

	/**
	 * @return | result == this.isTerminated
	 */
	@Basic @Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	private boolean isTerminated = false;

}
