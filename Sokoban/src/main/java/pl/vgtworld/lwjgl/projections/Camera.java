package pl.vgtworld.lwjgl.projections;

import org.lwjgl.opengl.GL11;

public class Camera {
	
	private float positionX;
	
	private float positionY;
	
	private float positionZ;
	
	private float angleX;
	
	private float angleY;
	
	private float angleZ;
	
	public float getPositionX() {
		return positionX;
	}

	public void setPositionX(float x) {
		this.positionX = x;
	}

	public float getPositionY() {
		return positionY;
	}

	public void setPositionY(float y) {
		this.positionY = y;
	}

	public float getPositionZ() {
		return positionZ;
	}

	public void setPositionZ(float z) {
		this.positionZ = z;
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
	
	public void translation() {
		GL11.glRotatef(angleX, 1, 0, 0);
		GL11.glRotatef(angleY, 0, 1, 0);
		GL11.glRotatef(angleZ, 0, 0, 1);
		GL11.glTranslatef(-positionX, -positionY, -positionZ);
	}

}
