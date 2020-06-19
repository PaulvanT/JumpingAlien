package jumpingalien.facade;

import java.util.Collection;
import java.util.Set;

import jumpingalien.model.*;
import jumpingalien.util.ModelException;
import jumpingalien.util.ShouldNotImplementException;
import jumpingalien.util.Sprite;

public class Facade implements IFacade {

	@Override
	public boolean isTeamSolution() {
		return true;
	}

	@Override
	public Mazub createMazub(int pixelLeftX, int pixelBottomY, Sprite... sprites) throws ModelException {
		if (! Mazub.isValidMazubSprites(sprites)) {
			throw new ModelException("Illegal Sprites");
		}
		else if (pixelLeftX<0 || pixelBottomY<0)
			throw new ModelException("position outside universe");
		Mazub newAlien = new Mazub(new int[] {pixelLeftX,pixelBottomY}, new double[] {(double) pixelLeftX/100, (double) pixelBottomY/100}, sprites);
		
		return newAlien;
	}

	@Override
	public double[] getActualPosition(Mazub alien) throws ModelException {
		return alien.getPosition();
	}

	@Override
	public void changeActualPosition(Mazub alien, double[] newPosition) throws ModelException {
    	if (!alien.canHaveAsPosition(newPosition)) {
    		alien.terminateObject();
    		throw new ModelException("Illegal Position");
    	}
    	alien.setNewPosition(newPosition);

	}

	@Override
	public int[] getPixelPosition(Mazub alien) throws ModelException {
		return alien.getPixelPosition();
	}

	@Override
	public int getOrientation(Mazub alien) throws ModelException {
		return alien.getOrientation();
	}

	@Override
	public double[] getVelocity(Mazub alien) throws ModelException {
		return alien.getVelocity();
	}

	@Override
	public double[] getAcceleration(Mazub alien) throws ModelException {
		return alien.getAcceleration();
	}
	
	@Override
	public Sprite[] getSprites(Mazub alien) throws ModelException, ShouldNotImplementException {
		return alien.getSprites();
	}

	@Override
	public Sprite getCurrentSprite(Mazub alien) throws ModelException {
		return alien.getCurrentSprite();
	}
	
	@Override
	public boolean isMoving(Mazub alien) throws ModelException {
		return alien.isRunning();
	}

	@Override
	public void startMoveLeft(Mazub alien) throws ModelException {
		if (alien.isRunning() || alien.isDead())
			throw new ModelException("Already Running / Is Dead");
		alien.startMove(-1);
		
	}

	@Override
	public void startMoveRight(Mazub alien) throws ModelException {
		if (alien.isRunning() || alien.isDead())
			throw new ModelException("Already Running / Is Dead");
		alien.startMove(1);
		
	}

	@Override
	public void endMove(Mazub alien) throws ModelException {
		if (!alien.isRunning() || alien.isDead())
			throw new ModelException("Not Running/Dead");
		alien.endMove();
		
	}

	@Override
	public boolean isJumping(Mazub alien) throws ModelException {
		return alien.isJumping;
	}

	@Override
	public void startJump(Mazub alien) throws ModelException {
		if (alien.isJumping || alien.isDead())
			throw new ModelException("Already Jumping/ Is Dead");
		alien.startJump();
		
	}

	@Override
	public void endJump(Mazub alien) throws ModelException {
		if (!alien.isJumping || alien.isDead())
			throw new ModelException("Not Jumping/Dead");
		alien.endJump();
		
	}

	@Override
	public boolean isDucking(Mazub alien) throws ModelException {
		return alien.isDucking;
	}

	@Override
	public void startDuck(Mazub alien) throws ModelException {
		alien.startDucking();
	}

	@Override
	public void endDuck(Mazub alien) throws ModelException {
		alien.endDucking();
	}
	
	@Override
	public World createWorld(int tileSize, int nbTilesX, int nbTilesY, int[] targetTileCoordinate,
			int visibleWindowWidth, int visibleWindowHeight, int... geologicalFeatures) throws ModelException {
		if (geologicalFeatures == null)
			throw new ModelException("Illegal features");
		if (!World.canHaveAsTargetTile(targetTileCoordinate))
			throw new ModelException("Illegal target tile");
		World new_world = new World(tileSize,nbTilesX,nbTilesY,targetTileCoordinate,
									visibleWindowWidth,visibleWindowHeight,geologicalFeatures);
		if (!(new_world.canHaveAsVisibleWindowDimension(new int[] {visibleWindowWidth,visibleWindowHeight})))
					throw new ModelException("Illegal Window Dimension");
		return new_world;
	}

	@Override
	public void terminateWorld(World world) throws ModelException {
		world.terminateWorld();
	}

	@Override
	public int[] getSizeInPixels(World world) throws ModelException {
		return world.getSizeInPixels();
	}

	@Override
	public int getTileLength(World world) throws ModelException {
		return world.getTileLength();
	}

	@Override
	public int getGeologicalFeature(World world, int pixelX, int pixelY) throws ModelException {
		return world.getFeatureSymbolAtLocation(pixelX,pixelY);
	}

	@Override
	public void setGeologicalFeature(World world, int pixelX, int pixelY, int geologicalFeature) throws ModelException {
		world.setFeature(pixelX,pixelY,geologicalFeature);
	}

	@Override
	public int[] getVisibleWindowDimension(World world) throws ModelException {
		return world.getVisibleWindowDimension();
	}
	
	@Override
	public int[] getVisibleWindowPosition(World world) throws ModelException {
		return world.getVisibleWindowPosition();
		
	}

	@Override
	public boolean hasAsGameObject(Object object, World world) throws ModelException {
		return world.getObjects().contains(object);
	}

	@Override
	public Set<Object> getAllGameObjects(World world) throws ModelException {
		return world.getObjects();
	}

	@Override
	public Mazub getMazub(World world) throws ModelException {
		return world.getPlayerMazub();
	}

	@Override
	public void addGameObject(Object object, World world) throws ModelException {
		if (! world.isValidObject(object)){
			throw new ModelException("Illegal Object");
		}
		else if (object instanceof Mazub && world.worldAlreadyContainsMazub())
			throw new ModelException("Already contains Mazub");
		
		else if (world.getObjects().size() >= 100) {
			if (((object instanceof Mazub) && (world.getPlayerMazub()!=null)) || !(object instanceof Mazub))
				throw new ModelException("Too many objects");
		}
		else if (world.gameStarted)
			throw new ModelException("Game already started!");
		
		else if (((GameObject) object).getWorld() != null) {
			throw new ModelException("Already in other world");
		}
		else if (((GameObject) object).getPixelPosition()[0] < 0 || ((GameObject) object).getPixelPosition()[0] >=world.getHorizontalSizeInPixels() 
				|| ((GameObject) object).getPixelPosition()[1] < 0 || ((GameObject) object).getPixelPosition()[1] >= world.getVerticalSizeInPixels()) {
			throw new ModelException("Illegal Position");
		}
		else if (! ((GameObject) object).canHaveAsPosition(((GameObject) object).getPosition()))
			throw new ModelException("Illegal Position");
		else if (world != null && (!(object instanceof Plant))) {
			 if (((GameObject) object).overlapsWithImpassableTerrain(world) || ((GameObject) object).overlapsWithObjectNotPlant(world)) {
				throw new ModelException("Overlapping");
			}
		}
		world.addObject(object);
		
	}

	@Override
	public void removeGameObject(Object object, World world) throws ModelException {
		if (object == null)
			throw new ModelException("Object is null");
		else if (((GameObject) object).getWorld() != world)
			throw new ModelException("Object not in this world");
		world.removeObject(object);
	}

	@Override
	public int[] getTargetTileCoordinate(World world) throws ModelException {
		return world.getTargetTileCoordinates();
	}

	@Override
	public void setTargetTileCoordinate(World world, int[] tileCoordinate) throws ModelException {
		if (! World.canHaveAsTargetTile(tileCoordinate))
			throw new ModelException("illeggal tt");
		world.setTargetTileCoordinates(tileCoordinate);
	}

	@Override
	public void startGame(World world) throws ModelException {
		if (world.getPlayerMazub() == null) throw new ModelException("No Mazub to play with");
		world.startGame();
		
	}

	@Override
	public boolean isGameOver(World world) throws ModelException {
		return world.isGameOver();
	}

	@Override
	public boolean didPlayerWin(World world) throws ModelException {
		return world.didPlayerWin;
	}

	@Override
	public void advanceWorldTime(World world, double dt) throws ModelException{
		if (!world.isValidTimeDuration(dt))
			throw new ModelException("illegal time");
		world.advanceWorldTime(dt);
		
	}

	@Override
	public void terminateGameObject(Object gameObject) throws ModelException {
		((GameObject) gameObject).terminateObject();
	}

	@Override
	public boolean isTerminatedGameObject(Object gameObject) throws ModelException {
		return ((GameObject) gameObject).isTerminated();
	}

	@Override
	public boolean isDeadGameObject(Object gameObject) throws ModelException {
		return ((GameObject) gameObject).isDead();
	}

	@Override
	public double[] getActualPosition(Object gameObject) throws ModelException {
		return ((GameObject) gameObject).getPosition();
	}

	@Override
	public void changeActualPosition(Object gameObject, double[] newPosition) throws ModelException {
		if (!((GameObject) gameObject).canHaveAsPosition(newPosition))
			throw new ModelException("Illegal Position");
		((GameObject) gameObject).setNewPosition(newPosition);
	}

	@Override
	public int[] getPixelPosition(Object gameObject) throws ModelException {
		return ((GameObject) gameObject).getPixelPosition();
	}

	@Override
	public int getOrientation(Object gameObject) throws ModelException {
		return ((GameObject) gameObject).getOrientation();
	}

	@Override
	public double[] getVelocity(Object gameObject) throws ModelException {
		return ((GameObject) gameObject).getVelocity();
	}

	@Override
	public World getWorld(Object object) throws ModelException {
		if (object instanceof GameObject)
			return ((GameObject) object).getWorld();
		else if (object instanceof School)
			return ((School) object).getWorld();
		else
			return null;
	}

	@Override
	public int getHitPoints(Object object) throws ModelException {
		return ((GameObject) object).getHitpoints();
	}

	@Override
	public Sprite[] getSprites(Object gameObject) throws ModelException {
		return ((GameObject) gameObject).getSprites();
	}
	
	@Override
	public Sprite getCurrentSprite(Object gameObject) throws ModelException {
		return ((GameObject) gameObject).getCurrentSprite();
	}

	@Override
	public void advanceTime(Object gameObject, double dt) throws ModelException {
		if (! ((GameObject) gameObject).isValidTimeDuration(dt))
			throw new ModelException("Illegal Time");
		((GameObject) gameObject).advanceObjectsTime(dt);
	}

	@Override
	public double[] getAcceleration(Object gameObject) throws ModelException {
		return ((GameObject) gameObject).getAcceleration();
	}


	@Override
	public long getIdentification(Slime slime) throws ModelException {
		return slime.getId();
	}


	@Override
	public boolean hasAsSlime(School school, Slime slime) throws ModelException {
		return (school.getSlimes().contains(slime));
	}

	@Override
	public Collection<? extends Slime> getAllSlimes(School school) {
		return school.getSlimes();
	}

	@Override
	public void addAsSlime(School school, Slime slime) throws ModelException {
		if (!school.isValidSlime(slime)|| slime.getSchool()!=null)
			throw new ModelException("Invalid slime/Slime already in this school");
		school.addSlime(slime);
		
	}

	@Override
	public void removeAsSlime(School school, Slime slime) throws ModelException {
		if (slime.getSchool()!= school)
			throw new ModelException("Slime not in this school");
		school.removeSlime(slime);
		
	}

	@Override
	public void switchSchool(School newSchool, Slime slime) throws ModelException {
		if (slime.getSchool()==null)
			throw new ModelException("slime in no school");
		else if (newSchool == null)
			throw new ModelException("newschool can't be null");
		else if (slime.isTerminated())
			throw new ModelException("terminated slime");
		else if (newSchool.isTerminated())
			throw new ModelException("terminated school");
		slime.getSchool().switchSchool(newSchool, slime);
		
	}

	@Override
	public Sneezewort createSneezewort(int pixelLeftX, int pixelBottomY, Sprite... sprites) throws ModelException {
		if (! Plant.isValidPlantSprites(sprites))
			throw new ModelException("illegal sprites");
		Sneezewort newSneezewort = new Sneezewort(new double[] {(double) pixelLeftX/100, (double) pixelBottomY/100}, sprites);
		return newSneezewort;
	}

	@Override
	public Skullcab createSkullcab(int pixelLeftX, int pixelBottomY, Sprite... sprites) throws ModelException {
		if (! Plant.isValidPlantSprites(sprites))
			throw new ModelException("illegal sprites");
		Skullcab newSkullcab = new Skullcab(new double[] {(double) pixelLeftX/100, (double) pixelBottomY/100} ,sprites);
		return newSkullcab;
	}

	@Override
	public Slime createSlime(long id, int pixelLeftX, int pixelBottomY, School school, Sprite... sprites)
			throws ModelException {
		if (!Slime.canHaveAsID(id))
			throw new ModelException("Illegal ID");
		else if (! Slime.isValidSlimeSprites(sprites))
			throw new ModelException("Illegal sprites");
		Slime newSlime = new Slime(id, new double[] {(double) pixelLeftX/100, (double) pixelBottomY/100}, school, sprites);
		return newSlime;
	}

	@Override
	public School createSchool(World world) throws ModelException {
		if (world != null) {
			if (world.getSchools().size()>=10)
				throw new ModelException("already maximum schools reached");
		}
		School newSchool = new School(world);
		return newSchool;
	}

	@Override
	public School getSchool(Slime slime) throws ModelException {
		return slime.getSchool();
	}	
	
	public boolean isLateTeamSplit() {
		return false;
	}

	@Override
	public Set<School> getAllSchools(World world) throws ModelException {
		return world.getSchools();
	}

	@Override
	public void cleanAllSlimeIds() {
		Slime.cleanAllIDs();
	}

	@Override
	public void terminateSchool(School school) throws ModelException {
		school.terminateschool();
	}

	@Override
	public  Shark createShark(int pixelLeftX, int pixelBottomY, Sprite... sprites) throws ModelException {
		if (!isTeamSolution()) {
			return null;
		}
		if (! Shark.isValidSharkSprites(sprites))
			throw new ModelException("illegal sprites");
		Shark newShark = new Shark(new double[] {(double) pixelLeftX/100,(double) pixelBottomY/100}, sprites);
		return newShark;
	}

	@Override
	public boolean hasImplementedWorldWindow() {
		return true;
	}	
}
	