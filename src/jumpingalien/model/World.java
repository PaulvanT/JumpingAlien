package jumpingalien.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import be.kuleuven.cs.som.annotate.*;

/**
 * @invar	|	canHaveAsTileLength(getTileLength())
 * 
 * @invar	|	canHaveAsNumberOfTiles(getNumberOfTiles())
 * 
 * @invar	|	canHaveAsSizeInPixels(getSizeInPixels())
 * 
 * @invar 	|	isValidFeature(int feature)
 * 
 * @invar	|	isValidPlayerMazub(getPlayerMazub())
 * 
 * @invar	|	for object in getObjects()
 * 			|		isValidObject(object)
 * 
 * @invar	|	canHaveAsVisibleWindowDimension(getVisibleWindowDimension())
 * 
 * @invar	|	isValidVisibleWindowPosition(getVisibleWindowPosition())
 * 	
 * @version	2.0
 * 
 * @author 	Arthur van Meerbeeck, 2e Bachelor Burgerlijk Ingenieur, Computerwetenschappen-Elektrotechniek
 * 
 * @author	Paul van Tieghem de Ten Berghe, 2e Bachelor Burgerlijk Ingenieur, Elektrotechniek-Computerwetenschappen 
 */
public class World {
	
	/**
	 * @param tileLength
	 * @param nbTilesX
	 * @param nbTilesY
	 * @param targetPosition
	 * @param visibleWindowWidth
	 * @param visibleWindowHeight
	 * @param features
	 * @post | new.getNumberOfHorTiles() == nbTilesX
	 * @post | new.getNumberOfVerTiles() == nbTilesY
	 * @post | new.getnumberOfTiles == nbTilesX*nbTilesY;
	 * @post | new.getTileLength() == tileLength
	 * @post | new.getSizeInPixels() == new int[] {nbTilesX,nbTilesY}
	 * @post | new.getVisibleWindowDimension() == new int[] {visibleWindowWidth,visibleWindowHeight}
	 * @post | new.getTargetTileCoordinates() == targetPosition
	 * @effect | for (int feature: features)
	 *		   |	if (!isValidFeature(feature))
	 *		   |		then feature == AIR;
	 *		   |	features.add(feature);
	 * @effect | while (features.size() < this.getNumberOfTiles())
	 *		   |	features.add(AIR);	
	 * 
	 */
	public World(int tileLength,int nbTilesX, int nbTilesY,int[] targetPosition,int visibleWindowWidth, 
			int visibleWindowHeight, int... features) throws IllegalArgumentException {
		setNumberOfHorTiles(nbTilesX);
		setNumberOfVerTiles(nbTilesY);
		setNumberOfTiles(nbTilesX*nbTilesY);
		setTileLength(tileLength);		
		setSizeInPixels(new int[] {nbTilesX,nbTilesY});
		setVisibleWindowDimension(new int[] {visibleWindowWidth,visibleWindowHeight});
		setTargetTileCoordinates(targetPosition);
		for (int feature: features) {
			if (!isValidFeature(feature))
				feature = Feature.AIR.getSymbol();
			this.features.add(feature);
		}
		while (this.features.size() < this.getNumberOfTiles()) {
			this.features.add(Feature.AIR.getSymbol());
		}
	}
	
	//****************************** HORIZONTAL SIZE IN PIXELS  **********************************//
	
	@Basic @Raw
	@Immutable
	public int getHorizontalSizeInPixels() {
		return this.horizontalSizeInPixels;
	}
	
	/**
	 * @param  horizontalSizeInPixels
	 * @return | result == (horizontalSizeInPixels > 0)
	*/
	@Raw
	public static boolean canHaveAsHorizontalSizeInPixels(int horizontalSizeInPixels) {
		return (horizontalSizeInPixels > 0);
	}
	
	/**
	 * @param  nbTilesX
	 * @post | if (canHaveAsHorizontalSizeInPixels(nbTilesX*getTileLength()))
	 *       | 		then new.getHorizontalSizeInPixels() == nbTilesX*getTileLength()
	 */
	@Raw
	public void setHorizontalSizeInPixels(int nbTilesX) {
		if (canHaveAsHorizontalSizeInPixels(nbTilesX*getTileLength()))
			this.horizontalSizeInPixels = nbTilesX*getTileLength();
	}

	private int horizontalSizeInPixels;

	//****************************** VERTICAL SIZE IN PIXELS  **********************************//

	@Basic @Raw
	@Immutable
	public int getVerticalSizeInPixels() {
		return this.verticalSizeInPixels;
	}
	
	/**
	 * @param  verticalSizeInPixels
	 * @return | result == (verticalSizeInPixels > 0)
	*/
	@Raw
	public static boolean canHaveASVerticalSizeInPixels(int verticalSizeInPixels) {
		return (verticalSizeInPixels>0);
	}
	
	/**
	 * @param  nbTilesY
	 * @post | if (canHaveASVerticalSizeInPixels(nbTilesY*getTileLength()))
	 *       | 		then new.getVerticalSizeInPixels() == nbTilesY*getTileLength()
	 */
	@Raw
	public void setVerticalSizeInPixels(int nbTilesY) {
		if (canHaveASVerticalSizeInPixels(nbTilesY*getTileLength()))
			this.verticalSizeInPixels = nbTilesY*getTileLength();
	}

	private int verticalSizeInPixels;

	//****************************** TOTAL SIZE IN PIXELS  **********************************//
	
	@Basic @Raw
	@Immutable
	public int[] getSizeInPixels() {
		this.sizeInPixels[0] = getHorizontalSizeInPixels();
		this.sizeInPixels[1] = getVerticalSizeInPixels();
		return this.sizeInPixels;
	}
	
	/**
	 * @param  sizeInPixels
	 * @return | result == ((canHaveAsHorizontalSizeInPixels(sizeInPixels[0])) && 
							(canHaveASVerticalSizeInPixels(sizeInPixels[1])))
	*/
	@Raw
	public static boolean canHaveAsSizeInPixels(int[] sizeInPixels) {
		return ((canHaveAsHorizontalSizeInPixels(sizeInPixels[0])) && 
				(canHaveASVerticalSizeInPixels(sizeInPixels[1])));
	}
	
	/**
	 * @param  sizeInPixels
	 * @post | if (canHaveAsSizeInPixels(sizeInPixels))
	 *       |   then new.getSizeInPixels() == sizeInPixels
	 *       |else
	 *       |	 then new.getSizeInPixels() == Math.abs(sizeInPixels)
	 */
	@Raw
	public void setSizeInPixels(int[] sizeInPixels) {
		if (canHaveAsSizeInPixels(sizeInPixels)) {
			setHorizontalSizeInPixels(sizeInPixels[0]);
			setVerticalSizeInPixels(sizeInPixels[1]);
		}
		else {
			setHorizontalSizeInPixels(Math.abs(sizeInPixels[0]));
			setVerticalSizeInPixels(Math.abs(sizeInPixels[1]));
		}
	}
	
	private int[] sizeInPixels = new int[2];
	
	//****************************** TILE LENGTH **********************************//

	@Basic @Raw @Immutable
	public int getTileLength() {
		return this.tileLength;
	}
	
	/**
	 * @param  	tileLength
	 * @return | result == (tileLength > 0) && ((getHorizontalSizeInPixels()*100) % tileLength == 0) && 
	 *		   | ((getVerticalSizeInPixels()*100) % tileLength == 0)      
	*/
	@Raw
	public boolean canHaveAsTileLength(int tileLength) {
		return ((tileLength > 0) && ((this.getHorizontalSizeInPixels()*100) % tileLength == 0) && 
				((this.getVerticalSizeInPixels()*100) % tileLength == 0)); 
	}
	
	/**
	 * @param  tileLength
	 * @post | if (canHaveAsTileLength(tileLength))
	 *       | 		then new.getTileLength == tileLength
	 * @post | if (tileLength < 0)
	 *       | 		then new.getTileLength == Math.abs(tileLength)
	 */
	@Raw
	public void setTileLength(int tileLength) {
		if (canHaveAsTileLength(tileLength))
			this.tileLength = tileLength;
		else if (tileLength < 0)
			this.tileLength = Math.abs(tileLength);
	}
	
	private int tileLength;
	
	//*************************  AMOUNT OF HORIZONTAL TILES **********************//

	@Basic @Raw
	@Immutable
	public int getNumberOfHorTiles() {
		return this.nbHorTiles;
	}
	
	/** 
	 * @param  nbHorTiles
	 * @return | result == (nbHorTiles > 0)
	*/
	public static boolean canHaveAsNumberOfHorTiles(int nbHorTiles) {
		return (nbHorTiles > 0);
	}
	
	/**
	 * @param  nbHorTiles  
	 * @post | if (canHaveAsNumberOfHorTiles(nbHorTiles))
	 *       |   then new.getNumberOfHorTiles() == nbHorTiles
	 */
	@Raw
	public void setNumberOfHorTiles(int nbHorTiles) {
		if (canHaveAsNumberOfHorTiles(nbHorTiles))
			this.nbHorTiles = nbHorTiles;
		else
			this.nbHorTiles = Math.abs(nbHorTiles);
	}
	
	private int nbHorTiles;
	
	//*************************  AMOUNT OF VERTICAL TILES **********************//

	@Basic @Raw
	@Immutable
	public int getNumberOfVerTiles() {
		return this.nbVerTiles;
	}
	
	/** 
	 * @param  nbVerTiles
	 * @return | result == (nbVerTiles > 0)
	*/
	@Raw
	public static boolean canHaveAsNumberOfVerTiles(int nbVerTiles) {
		return nbVerTiles > 0;
	}
	
	/**
	 * @param  nbVerTiles  
	 * @post | if (canHaveAsNumberOfHorTiles(nbVerTiles))
	 *       |   then new.getNumberOfVerTiles() == nbVerTiles
	 */
	@Raw
	public void setNumberOfVerTiles(int nbVerTiles) {
		if (canHaveAsNumberOfVerTiles(nbVerTiles))
			this.nbVerTiles = nbVerTiles;
		else
			this.nbVerTiles = Math.abs(nbVerTiles);
	}
	
	private int nbVerTiles;
	
	//************************* TOTAL AMOUNT OF TILES **********************//

	@Basic @Raw
	@Immutable
	public int getNumberOfTiles() {
		return this.numberOfTiles;
	}
	
	/** 
	 * @param  numberOfTiles
	 * @return | result == (numberOfTiles > 0)
	*/
	@Raw
	public static boolean canHaveAsNumberOfTiles(int numberOfTiles) {
		return numberOfTiles > 0;
	}
	
	/**
	 * @param  numberOfTiles  
	 * @post | if (canHaveAsNumberOfTiles(numberOfTiles))
	 *       |   then new.getNumberOfTiles() == numberOfTiles
	 */
	@Raw
	public void setNumberOfTiles(int numberOfTiles) {
		if (canHaveAsNumberOfTiles(numberOfTiles))
			this.numberOfTiles = numberOfTiles;
		else
			this.numberOfTiles = Math.abs(numberOfTiles);
	}

	private int numberOfTiles;
		
	//************************* TARGET TILE COORDINATES **********************//

	@Basic @Raw
	public int[] getTargetTileCoordinates() {
		return this.targetTileCoordinates;
	}
	
	public static boolean canHaveAsTargetTile(int[] targettile) {
		return (targettile != null && targettile.length==2);
	}
	
	/**
	 * @param  	targetTileCoordinates 
	 * @pre	  | canHaveAsTargetTile(targetTileCoordinates)
	 * @post  | new.getTargetTileCoordinates() == targetTileCoordinates
	 */
	@Raw
	public void setTargetTileCoordinates(int[] targetTileCoordinates) {
		assert canHaveAsTargetTile(targetTileCoordinates);
		this.targetTileCoordinates = targetTileCoordinates.clone();
	}
	
	private int[] targetTileCoordinates  = new int[2];
	
//************************************ GEOLOGICAL FEATURES **************************************//	

	/**
	 * @param  pixelpositionx
	 * @param  pixelpositiony
	 * @return | result == ( (pixelpositiony/getTileLength())*getNumberOfHorTiles() + (pixelpositionx/getTileLength()+1) )
	 */
	@Basic @Raw
	public int getTileOfPosition(int pixelpositionx,int pixelpositiony) {
		int tileX = pixelpositionx/this.getTileLength()+1;
		int tileY = pixelpositiony/this.getTileLength();
		return (tileY*this.getNumberOfHorTiles() + tileX);
	}
	
	/**
	 * @param  PixelX
	 * @param  PixelY
	 * @return | if ((PixelX<0) || (PixelX>=getHorizontalSizeInPixels()) || (PixelY<0) || (PixelY>=getVerticalSizeInPixels())) 
	 * 		   |	then result ==  AIR
	 * 		   | else
	 * 		   |    then result ==  Features.get(getTileOfPosition(PixelX, PixelY)-1)
	 */
	@Basic
	public int getFeatureSymbolAtLocation(int PixelX,int PixelY) {
		if ((PixelX<0) || (PixelX>=getHorizontalSizeInPixels()) || (PixelY<0) || (PixelY>=getVerticalSizeInPixels())) 
			return Feature.AIR.getSymbol();
		else 
			return this.features.get(this.getTileOfPosition(PixelX, PixelY)-1);
	}
	
	/**
	 * @param  featureSymbol
	 * @return | for (Feature feature: Feature.values())
	 * 		   |	if (feature.getSymbol() == featureSymbol)
	 * 		   | 		then result == feature
	 * 		   | else
	 * 		   |    then result ==  Feature.AIR
	 */
	@Basic
	public Feature getFeature(int featureSymbol) {
		for (Feature feature: Feature.values()) {
			if (feature.getSymbol() == featureSymbol)
				return feature;
		}
		return Feature.AIR;
	}
	
	
	/**
	 * @param  featureSymbol
	 * @return | for (Feature feature: Feature.values())
	 * 		   |	if (feature.getSymbol() == featureSymbol)
	 * 		   | 		then result == true
	 * 		   | else
	 * 		   |    then result ==  false
	 */
	public boolean isValidFeature(int featureSymbol) {
		for (Feature feature: Feature.values()) {
			if (feature.getSymbol() == featureSymbol)
				return true;
		}
		return false;
	}
	
	/**
	 * @param PixelX
	 * @param PixelY
	 * @param feature
	 * @post  | if ((PixelX<0) || (PixelX>=getHorizontalSizeInPixels()) || (PixelY<0) || (PixelY>=getVerticalSizeInPixels()))
	 *        | 	then return
	 * @post  | if (isValidFeature(Feature))
	 *        | 	then new.getFeature(PixelX,PixelY) == feature
	 * @post  | else
	 *        |		then new.getFeature(PixelX,PixelY) == AIR
	 */
	@Raw
	public void setFeature(int PixelX,int PixelY,int feature) {
		if ((PixelX<0) || (PixelX>=getHorizontalSizeInPixels()) || (PixelY<0) || (PixelY>=getVerticalSizeInPixels()))
			return;
		if (isValidFeature(feature))
			this.features.set(this.getTileOfPosition(PixelX, PixelY)-1,feature);
		else
			this.features.set(this.getTileOfPosition(PixelX, PixelY)-1,Feature.AIR.getSymbol());
	}

	public ArrayList<Integer> features = new ArrayList<Integer>();

//*********************************** SET MAZUB TO CONTROL ******************************************//

	@Basic @Raw
	public Mazub getPlayerMazub() {
		return this.playerMazub;
	}
	
	/** 
	 * @param  playerMazub
	 * @return | result == (Mazub != null)
	*/
	public static boolean isValidPlayerMazub(Mazub playerMazub) {
		return (playerMazub != null);
	}
	
	/**
	 * @param  playerMazub
	 * @throws IllegalArgumentException
	 *       | ! isValidMazub(getMazub())
	 */
	@Raw
	public void setPlayerMazub(Mazub playerMazub) throws IllegalArgumentException {
		if (! isValidPlayerMazub(playerMazub)) throw new IllegalArgumentException();
		this.playerMazub = playerMazub;
		changeVisibleWindowPosition();
	}

	private Mazub playerMazub;
	
	//**************************************** GAME OBJECTS ***************************************//

	@Basic @Raw
	public Set<Object> getObjects() {
		LinkedHashSet<Object> copy = new LinkedHashSet<Object>(objects);
		return copy;
	}
	
	/**
	 * @param  object
	 * @return | result == (object != null) && (! object.isTerminated()) && (! this.isTerminated())
	*/
	public boolean isValidObject(Object object) {
		return ((object != null) && (!((GameObject) object).isTerminated()) && (!this.isTerminated()));
	} 
	
	/**
	 * @param object
	 * @throws IllegalArgumentException
	 * 		| (!isValidObject(object))
	 * @throws IllegalArgumentException
	 * 		|  getObjects().size() >= 100
	 * @throws IllegalArgumentException
	 * 		|  gameStarted
	 * @throws IllegalArgumentException
	 * 		|  getWorld() != null
	 * @throws IllegalArgumentException
	 * 		|  OverlapsWithImpassableTerrain(getWorld()) && (!(object instanceof Plant)
	 * @throws IllegalArgumentException
	 * 		|  (object.getPixelPosition()[0] < 0 || object.getPixelPosition()[0] >= getHorizontalSizeInPixels() 
	 *		|	|| object.getPixelPosition()[1] < 0 || object.getPixelPosition()[1] >= getVerticalSizeInPixels()) 
	 * @effect | if (this.getObjects().size() == 0 && object instance of Mazub)
	 *		   | 	then setMazub(object)
	 * @effect | objects.add(object);
	 * @effect | objects.setWorld(this);
	 */
	@Raw
	public void addObject(Object object) throws IllegalArgumentException{
		if (!isValidObject(object)) 
			throw new IllegalArgumentException("Illegal object");
		else if (object instanceof Mazub && this.worldAlreadyContainsMazub())
			throw new IllegalArgumentException("World already contains a Mazub");
		else if (this.getObjects().size() >= 100) {
			if (((object instanceof Mazub) && (this.getPlayerMazub()!=null)) || !(object instanceof Mazub))
			throw new IllegalStateException("Too many objects");
		}
		else if (this.gameStarted) 
			throw new IllegalStateException("Game already started");
		else if (((GameObject) object).getWorld() != null) 
			throw new IllegalArgumentException("Already in other world");
		else if (((GameObject) object).overlapsWithImpassableTerrain(this) && (!(object instanceof Plant))) 
			throw new IllegalStateException("Impassable terrain");
		else if (((GameObject) object).getPixelPosition()[0] < 0 || ((GameObject) object).getPixelPosition()[0] >= this.getHorizontalSizeInPixels() 
				|| ((GameObject) object).getPixelPosition()[1] < 0 || ((GameObject) object).getPixelPosition()[1] >= this.getVerticalSizeInPixels()) 
			throw new IllegalArgumentException("Illegal position");
		if (this.getPlayerMazub()==null && object instanceof Mazub) 
			this.setPlayerMazub((jumpingalien.model.Mazub) object);
		objects.add(object);
		((GameObject) object).setWorld(this);
	}
	
	/**
	 * 
	 * @return	|	result == this.objects.stream().anyMatch(object->object instanceof Mazub)
	 */
	public boolean worldAlreadyContainsMazub() {
		return this.objects.stream().anyMatch(object->object instanceof Mazub);

	}
	
	/**
	 * 
	 * @param slime
	 * @return |	result == for any worldobject in this.objects
	 * 		   |		worldobject.getId()==slime.getId()
	 */
	public boolean isSlimeDuplicate(Slime slime) {		
		for (Object worldobject: this.objects) {
			if (worldobject instanceof Slime) {
				if (((Slime) worldobject).getId() == slime.getId())
					return true;
			}
		}
		return false;
	}

	/**
	 * @param object
	 * @throws IllegalArgumentException
	 * 		   | if (!(object.getWorld()== this)) 
	 * @effect | if (object == getPlayerMazub())
	 *		   | 	then setMazub(null)
	 * @effect  | ((GameObject) object).setWorld(null)
	 *		   | objects.remove(object)
	 */
	@Raw
	public void removeObject(Object object) throws IllegalArgumentException {
		if (!(((GameObject) object).getWorld()== this)) 
			throw new IllegalArgumentException("Object not in this world");
		if (object == this.getPlayerMazub())
			this.playerMazub = null;
		((GameObject) object).setWorld(null);
		objects.remove(object);
	}
	
	LinkedHashSet<Object> objects = new LinkedHashSet<Object>();
	
	//**************************************** ITERATOR ********************************************//
	/**
	 * 
	 * @return	| result == new Iterator()
	 */
    @SuppressWarnings("all")
	public Iterator iterator() {
		return new Iterator() {

			public boolean hasNext() {
				return (currentIterator.hasNext());
			}

			public Object next() throws NoSuchElementException {
				if (!hasNext())
					throw new NoSuchElementException();
				return currentIterator.next();
			}

			public Iterator currentIterator = getObjects().iterator();
		};
	}
	
	//****************************************** SCHOOLS **********************************************//
	/**
	 * @return |	result == Set<School> copy = new HashSet<>(schools)
	 */
    @Basic @Raw
	public Set<School> getSchools() {
		Set<School> copy = new HashSet<>(schools);
		return copy;
	}
	
	/**
	 * @param school
	 * @throws IllegalArgumentException
	 * 		|	this.getSchools().size()>=10
	 * @effect |	schools.add(school)
	 */
	public void addSchool(School school) throws IllegalArgumentException {
		if (this.getSchools().size()>=10) {
			throw new IllegalArgumentException("Maximum schools reached");
		}
		schools.add(school);
	}
	/**
	 * @param school
	 * @throws IllegalArgumentException
	 * 		|	school.getWorld() != this
	 * @effect	|	schools.remove(school)
	 */
	public void removeSchool(School school) throws IllegalArgumentException {
		if (school.getWorld() != this)
				throw new IllegalArgumentException("school not in this world");
		schools.remove(school);
	}
	
	Set<School> schools = new HashSet<School>();

	
	//****************************** WORLD SETTINGS & CHARACTERISTICS **********************************//

	
	public void startGame() {
		this.gameStarted = true;
	}
	
	/**
	 * @effect |	for object in this.objects
	 * 		   |		object.setWorld(null)
	 * @effect |	this.objects.clear()
	 */
	public void terminateWorld() {
		this.isTerminated = true;
		for (Object object: this.objects) {
			((GameObject) object).setWorld(null);
		}
		this.objects.clear();
	}

	@Basic @Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}
	
	/**
	 * @return | if ((getPlayerMazub() != null) && ((getPlayerMazub().isDead()) || 
	 *		   |	(getPlayerMazub().getHorizontalPosition() < 0) || (getPlayerMazub().getVerticalPosition() < 0)))
	 *		   |	then result == true
	 * 		   | else result == (this.isGameOver || (this.didPlayerWin))
	 */
	public boolean isGameOver() {
		if ((this.getPlayerMazub() != null) && ((this.getPlayerMazub().isDead()) || 
			(this.getPlayerMazub().getHorizontalPosition() < 0) || (this.getPlayerMazub().getVerticalPosition() < 0))) {
			return true;
		}
		else return (this.isGameOver || (this.didPlayerWin));
			
	}
	
	public boolean didPlayerWin = false;
	public boolean gameStarted = false;
	public boolean isGameOver = false;
	private boolean isTerminated = false;
	
	
	//*********************************** VISIBLE WINDOW DIMENSION ****************************************//
	
	@Basic @Raw
	@Immutable
	public int[] getVisibleWindowDimension() {
		return this.visibleWindowDimension;
	}
	
	/** 
	 * @param  visibleWindowDimension
	 * @return | result == ((visibleWindowDimension[0] >= 0) && 
	 * 		   |			(visibleWindowDimension[0] <= (this.getNumberOfHorTiles())*this.getTileLength()) &&
	 *		   |			(visibleWindowDimension[1] >= 0) && 
	 *		   |			(visibleWindowDimension[1] <= (this.getNumberOfVerTiles())*this.getTileLength()))
	 */
	public boolean canHaveAsVisibleWindowDimension(int[] visibleWindowDimension) {
		return ((visibleWindowDimension[0] >= 0) && (visibleWindowDimension[0] <= (this.getNumberOfHorTiles())*this.getTileLength()) &&
				(visibleWindowDimension[1] >= 0) && (visibleWindowDimension[1] <= (this.getNumberOfVerTiles())*this.getTileLength()));
	}
	
	/**
	 * @param  visibleWindowDimension
	 */
	@Raw
	public void setVisibleWindowDimension(int[] visibleWindowDimension) {
		this.visibleWindowDimension = visibleWindowDimension;
	}
	
	private int[] visibleWindowDimension;
		
		
	//*************************************** VISIBLE WINDOW POSITION ****************************************//

	@Basic @Raw
	public int[] getVisibleWindowPosition() {
		return this.windowPosition ;
	}
	
	/**
	 * @param  windowPosition
	 * @return | result == !(windowPosition == null || windowPosition.length != 2) &&
	 *		   |			(windowPosition[0] >= 0 && windowPosition[1] >= 0)
	 */
	public static boolean isValidVisibleWindowPosition(int[] windowPosition ) {

		return (!(windowPosition == null || windowPosition.length != 2) &&
				(windowPosition[0] >= 0 && windowPosition[1] >= 0));
	}
	
	/**
	 * @param  windowPosition 
	 * @throws IllegalArgumentException
	 *       | !isValidVisibleWindowPosition(getVisibleWindowPosition())
	 * @post | new.getVisibleWindowPosition() == windowPosition
	 */
	@Raw
	public void setVisibleWindowPosition(int[] windowPosition ) throws IllegalArgumentException {
		if (! isValidVisibleWindowPosition(windowPosition )) throw new IllegalArgumentException("Illegal window position");
		this.windowPosition  = windowPosition ;
	}
	
	/**
	 * @effect | if (getVisibleWindowDimension()[0] > getPlayerMazub().getCurrentSprite().getWidth()+400)
	 *		   |	then if (getPlayerMazub().getPixelPosition()[0] > 200 && (getHorizontalSizeInPixels() - 
	 *		   |			(getPlayerMazub().getPixelPosition()[0] + getPlayerMazub().getCurrentSprite().getWidth()) > 200))
	 *		   |				then setVisibleWindowPosition(new int[] {(getPlayerMazub().getPixelPosition()[0]-200),
	 *		   |														  getVisibleWindowPosition()[1]});
	 * @effect | if (getVisibleWindowDimension()[1] > getPlayerMazub().getCurrentSprite().getHeight()+400)
	 *		   |	then if (getPlayerMazub().getPixelPosition()[1] > 200 && (getVerticalSizeInPixels() - 
	 *		   |			(getPlayerMazub().getPixelPosition()[1] + getPlayerMazub().getCurrentSprite().getHeight()) > 200))
	 *		   |				then setVisibleWindowPosition(new int[] {getVisibleWindowPosition()[0],
	 *		   |														 (getPlayerMazub().getPixelPosition()[1]-200)});
	 */
	public void changeVisibleWindowPosition() {
		
		//Horizontal Window Position
		if (this.getVisibleWindowDimension()[0] > this.getPlayerMazub().getCurrentSprite().getWidth()+400) {
			if (this.getPlayerMazub().getPixelPosition()[0] > 200 && (this.getHorizontalSizeInPixels() - 
					(this.getPlayerMazub().getPixelPosition()[0] + this.getPlayerMazub().getCurrentSprite().getWidth()) > 200)) {
				int new_hor_VWP = (this.getPlayerMazub().getPixelPosition()[0]-200);
				this.setVisibleWindowPosition(new int[] {new_hor_VWP,this.getVisibleWindowPosition()[1]});
			}
		}
		else {
			int middle_of_mazub = (this.getPlayerMazub().getPixelPosition()[0] + (this.getPlayerMazub().getCurrentSprite().getWidth())/2);
			int new_hor_VWP = ( (middle_of_mazub) - (this.getVisibleWindowDimension()[0])/2);
			if ((new_hor_VWP + this.getVisibleWindowDimension()[0]) >= this.horizontalSizeInPixels) {
				this.setVisibleWindowPosition(new int[] {this.horizontalSizeInPixels-this.getVisibleWindowDimension()[0],this.getVisibleWindowPosition()[1]});
			}
			else if (new_hor_VWP < 0) {
				this.setVisibleWindowPosition(new int[] {0,this.getVisibleWindowPosition()[1]});
			}
			else {
				this.setVisibleWindowPosition(new int[] {new_hor_VWP,this.getVisibleWindowPosition()[1]});
			}
			
		}
		
		//Vertical Window Position
		if (this.getVisibleWindowDimension()[1] > this.getPlayerMazub().getCurrentSprite().getHeight()+400) {
			if (this.getPlayerMazub().getPixelPosition()[1] > 200 && (this.getVerticalSizeInPixels() - 
					(this.getPlayerMazub().getPixelPosition()[1] + this.getPlayerMazub().getCurrentSprite().getHeight()) > 200)) {
				int new_ver_VWP = (this.getPlayerMazub().getPixelPosition()[1]-200);
				this.setVisibleWindowPosition(new int[] {this.getVisibleWindowPosition()[0],new_ver_VWP});
			}
		}
		else {
			int middle_of_mazub = (this.getPlayerMazub().getPixelPosition()[1] + (this.getPlayerMazub().getCurrentSprite().getHeight())/2);
			int new_ver_VWP = ( (middle_of_mazub) - (this.getVisibleWindowDimension()[1])/2);
			if ((new_ver_VWP + this.getVisibleWindowDimension()[1]) >= this.verticalSizeInPixels) {
				this.setVisibleWindowPosition(new int[] {this.getVisibleWindowPosition()[0],this.verticalSizeInPixels-this.getVisibleWindowDimension()[1]});
			}
			else if (new_ver_VWP < 0) {
				this.setVisibleWindowPosition(new int[] {this.getVisibleWindowPosition()[0],0});
			}
			else {
				this.setVisibleWindowPosition(new int[] {this.getVisibleWindowPosition()[0],new_ver_VWP});
			}
			
		}
	}
	
	private int[] windowPosition = new int[] {0,0} ;

	//************************************ ADVANCE TIME ******************************************//
	/** 
	 * @param  timeDuration
	 * @effect | if (timeDuration == Double.NaN || timeDuration<0 || timeDuration == Double.POSITIVE_INFINITY 
	 * 		   |	then result == false	
	 * @return | else then result == (timeDuration >= 0 && timeDuration < 0.2)
	*/
	public boolean isValidTimeDuration(double timeDuration){
		if (timeDuration == Double.NaN || timeDuration<0 || timeDuration == Double.POSITIVE_INFINITY )
			return false;
		return (timeDuration >= 0 && timeDuration < 0.2);
	}

	/**
	 * @param 	dt
	 * @throws 	 IllegalArgumentException
	 *		   | !isValidTimeDuration(dt)
	 * @effect | if (getObjects().size() != 0)
	 *		   | 	getObjects().forEach(object->((GameObject) object).advanceObjectsTime(dt))
	 * @effect | if ((getPlayerMazub() != null) && (getPlayerMazub().isDead()))
	 *		   | 	then didPlayerWin == false
	 *		   | 	then isGameOver == true
	 * @effect | if ((getPlayerMazub() != null) && (!(getPlayerMazub().isDead())))
	 *		   | 	then changeVisibleWindowPosition()
	 */
	public void advanceWorldTime(double dt) throws IllegalArgumentException {
		
		if (!isValidTimeDuration(dt)) throw new IllegalArgumentException("Illegal time duration");
		
		if (this.getObjects().size() != 0) {
			this.getObjects().forEach(object->((GameObject) object).advanceObjectsTime(dt));
		}
		if ((getPlayerMazub() != null) && (getPlayerMazub().isDead())) {
			this.didPlayerWin = false;
			this.isGameOver = true;
		}
		if ((getPlayerMazub() != null) && (!(getPlayerMazub().isDead())))
			changeVisibleWindowPosition();
	}
	
	
}