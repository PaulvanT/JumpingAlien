package jumpingalien.model;

public interface VerticalMove {
	
	/***********************
	 * VERTICAL POSITION *
	 ***********************/
	
	double getVerticalPosition();
	
	void setVerticalPosition(double verticalPosition);

	
	/***********************
	 * VERTICAL VELOCITY *
	 ***********************/
	
	double getVerticalVelocity();
	
	boolean isValidVerticalVelocity(double verticalVelocity);
	
	void setVerticalVelocity(double verticalVelocity);
	
	
	double getMaxVerticalVelocity();

	
	/***************************
	 * VERTICAL ACCELERATION *
	 ***************************/

	double getVerticalAcceleration();
	
	void setVerticalAcceleration(double verticalAcceleration);
	
}
