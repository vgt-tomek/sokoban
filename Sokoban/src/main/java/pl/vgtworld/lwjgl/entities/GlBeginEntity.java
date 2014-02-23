package pl.vgtworld.lwjgl.entities;

import org.lwjgl.opengl.GL11;

public class GlBeginEntity extends Entity {
	
	private float[] vertices;
	
	private float[] textureCoordinates;
	
	private int[] indices;
	
	@Override
	public void setMeshData(float[] vertices, int[] indices, float[] textureCoordinates) throws EntityException {
		this.vertices = vertices;
		this.indices = indices;
		this.textureCoordinates = textureCoordinates;
		if (!validate()) {
			throw new EntityException("Mesh data is not valid.");
		}
	}
	
	@Override
	public void draw() {
		int indicesIndex = 0;
		int coordinatesIndex = 0;
		if (getTextureId() != null) {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, getTextureId());
		}
		GL11.glBegin(GL11.GL_TRIANGLES);
		while (indicesIndex < indices.length) {
			GL11.glTexCoord2f(
					textureCoordinates[coordinatesIndex],
					textureCoordinates[coordinatesIndex + 1]
					);
			GL11.glVertex3f(
					vertices[indices[indicesIndex] * 3],
					vertices[indices[indicesIndex] * 3 + 1],
					vertices[indices[indicesIndex] * 3 + 2]
					);
			++indicesIndex;
			coordinatesIndex += 2;
		}
		GL11.glEnd();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	private boolean validate() {
		// TODO
		return true;
	}
	
}
