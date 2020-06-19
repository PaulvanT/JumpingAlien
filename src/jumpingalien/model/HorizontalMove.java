package jumpingalien.model;

public interface HorizontalMove {
	
	/***********************
	 * HORIZONTAL POSITION *
	 ***********************/
	
	double getHorizontalPosition();
	
	void setHorizontalPosition(double horizontalPosition);

	
	/***********************
	 * HORIZONTAL VELOCITY *
	 ***********************/

	double getHorizontalVelocity();
	
	boolean isValidHorizontalVelocity(double horizontalVelocity);
	
	void setHorizontalVelocity(double horizontalVelocity);
	
	
	double getMaxHorizontalVelocity();
	
	
	/***************************
	 * HORIZONTAL ACCELERATION *
	 ***************************/

	double getHorizontalAcceleration();
	
	void setHorizontalAcceleration(double horizontalAcceleration);
}
