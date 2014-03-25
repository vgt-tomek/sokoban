package pl.vgtworld.lwjgl.helpers;

import pl.vgtworld.lwjgl.Updateable;

public class Position implements Updateable {
	
	private float x;
	
	private float y;
	
	private float z;
	
	private float angleX;
	
	private float angleY;
	
	private float angleZ;
	
	private float deltaX;
	
	private float deltaY;
	
	private float deltaZ;
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getAngleX() {
		return angleX;
	}

	public void setAngleX(float angleX) {
		this.angleX = angleX;
	}

	public float getAngleY() {
		return angleY;
	}

	public void setAngleY(float angleY) {
		this.angleY = angleY;
	}

	public float getAngleZ() {
		return angleZ;
	}

	public void setAngleZ(float angleZ) {
		this.angleZ = angleZ;
	}

	public float getDeltaX() {
		return deltaX;
	}

	public void setDeltaX(float deltaX) {
		this.deltaX = deltaX;
	}

	public float getDeltaY() {
		return deltaY;
	}

	public void setDeltaY(float deltaY) {
		this.deltaY = deltaY;
	}

	public float getDeltaZ() {
		return deltaZ;
	}

	public void setDeltaZ(float deltaZ) {
		this.deltaZ = deltaZ;
	}
	
	@Override
	public void update(long elapsedTime) {
		if (elapsedTime <= 0) {
			return;
		}
		float elapsedTimeFraction = elapsedTime / 1000f;
		updatePosition(elapsedTimeFraction);
	}
	
	private void updatePosition(float elapsedTimeFraction) {
		if (deltaX != 0) {
			x+= deltaX * elapsedTimeFraction;
		}
		if (deltaY != 0) {
			y+= deltaY * elapsedTimeFraction;
		}
		if (deltaZ != 0) {
			z+= deltaZ * elapsedTimeFraction;
		}
	}
}
