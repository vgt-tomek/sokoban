package pl.vgtworld.lwjgl.helpers;

import pl.vgtworld.lwjgl.Updateable;

public class Position implements Updateable {
	
	private static final int FULL_CIRCLE = 360;

	private float x;
	
	private float y;
	
	private float z;
	
	private float angleX;
	
	private float angleY;
	
	private float angleZ;
	
	private float deltaX;
	
	private float deltaY;
	
	private float deltaZ;
	
	private float deltaAngleX;
	
	private float deltaAngleY;
	
	private float deltaAngleZ;
	
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
	
	public float getDeltaAngleX() {
		return deltaAngleX;
	}

	public void setDeltaAngleX(float deltaAngleX) {
		this.deltaAngleX = deltaAngleX;
	}

	public float getDeltaAngleY() {
		return deltaAngleY;
	}

	public void setDeltaAngleY(float deltaAngleY) {
		this.deltaAngleY = deltaAngleY;
	}

	public float getDeltaAngleZ() {
		return deltaAngleZ;
	}

	public void setDeltaAngleZ(float deltaAngleZ) {
		this.deltaAngleZ = deltaAngleZ;
	}

	@Override
	public void update(long elapsedTime) {
		if (elapsedTime <= 0) {
			return;
		}
		float elapsedTimeFraction = elapsedTime / 1000f;
		updateAngleX(elapsedTimeFraction);
		updateAngleY(elapsedTimeFraction);
		updateAngleZ(elapsedTimeFraction);
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
	
	private void updateAngleX(float elapsedTimeFraction) {
		if (deltaAngleX != 0) {
			angleX+= deltaAngleX * elapsedTimeFraction;
			if (angleX >= FULL_CIRCLE) {
				angleX-= FULL_CIRCLE;
			}
			if (angleX < 0) {
				angleX+= FULL_CIRCLE;
			}
		}
	}
	
	private void updateAngleY(float elapsedTimeFraction) {
		if (deltaAngleY != 0) {
			angleY+= deltaAngleY * elapsedTimeFraction;
			if (angleY >= FULL_CIRCLE) {
				angleY-= FULL_CIRCLE;
			}
			if (angleY < 0) {
				angleY+= FULL_CIRCLE;
			}
		}
	}
	
	private void updateAngleZ(float elapsedTimeFraction) {
		if (deltaAngleZ != 0) {
			angleZ+= deltaAngleZ * elapsedTimeFraction;
			if (angleZ >= FULL_CIRCLE) {
				angleZ-= FULL_CIRCLE;
			}
			if (angleZ < 0) {
				angleZ+= FULL_CIRCLE;
			}
		}
	}
	
}
