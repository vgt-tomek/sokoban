package pl.vgtworld.lwjgl.projections;

import org.lwjgl.opengl.GL11;

import pl.vgtworld.lwjgl.helpers.Position;

public class Camera {
	
	public final Position position = new Position();
	
	public void translation() {
		GL11.glRotatef(position.getAngleX(), 1, 0, 0);
		GL11.glRotatef(position.getAngleY(), 0, 1, 0);
		GL11.glRotatef(position.getAngleZ(), 0, 0, 1);
		GL11.glTranslatef(-position.getX(), -position.getY(), -position.getZ());
	}

}
