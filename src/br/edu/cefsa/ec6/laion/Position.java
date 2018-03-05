package br.edu.cefsa.ec6.laion;

import java.lang.IllegalArgumentException;

public class Position {
	private double x;
	private double y;
	private int angle;
	
	public Position(double x, double y, int angle) throws IllegalArgumentException{
		this.setX(x);
		this.setY(y);
		this.setAngle(angle);
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public int getAngle() {
		return angle;
	}
	public void setAngle(int angle) throws IllegalArgumentException {
		if(angle < 0 || angle >= 360)
			throw new IllegalArgumentException("Angle must be between 0 and 360 degrees");
		this.angle = angle;
	}
	
}
