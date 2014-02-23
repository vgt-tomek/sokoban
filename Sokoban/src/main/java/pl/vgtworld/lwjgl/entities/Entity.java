package pl.vgtworld.lwjgl.entities;

public abstract class Entity {
	
	private float[] color = new float[4];
	
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
	
	public abstract void draw();
	
}
