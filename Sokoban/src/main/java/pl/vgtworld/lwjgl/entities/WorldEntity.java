package pl.vgtworld.lwjgl.entities;

import org.lwjgl.opengl.GL11;

import pl.vgtworld.lwjgl.Renderable;
import pl.vgtworld.lwjgl.Updateable;
import pl.vgtworld.lwjgl.helpers.Position;

public class WorldEntity implements Updateable, Renderable {
	
	public final Position position = new Position();
	
	private Entity entity;
	
	public WorldEntity(Entity entity) {
		this.entity = entity;
	}

	@Override
	public void update(long elapsedTime) {
		if (elapsedTime <= 0) {
			return;
		}
		position.update(elapsedTime);
	}

	@Override
	public void render() {
		GL11.glTranslatef(position.getX(), position.getY(), position.getZ());
		GL11.glRotatef(position.getAngleX(), position.getAngleY(), position.getAngleZ(), 0);
		entity.render();
	}
	
}
