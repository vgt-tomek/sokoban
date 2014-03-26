package pl.vgtworld.lwjgl.entities;

import pl.vgtworld.lwjgl.Renderable;

public abstract class Entity implements Renderable {
	
	private float[] color = {1, 1, 1, 1};
	
	private Integer textureId;
	
	protected float[] getColor() {
		return color;
	}
	
	protected Integer getTextureId() {
		return textureId;
	}
	
	public void setColor(float red, float green, float blue, float alpha) {
		color[0] = red;
		color[1] = green;
		color[2] = blue;
		color[3] = alpha;
	}
	
	public void setTextureId(Integer textureId) {
		this.textureId = textureId;
	}
	
	public abstract void setMeshData(float[] vertices, int[] indices, float[] textureCoordinates) throws EntityException;
	
	@Override
	public abstract void render();
}
