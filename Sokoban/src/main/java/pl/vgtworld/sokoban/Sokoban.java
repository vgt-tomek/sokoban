package pl.vgtworld.sokoban;

import pl.vgtworld.lwjgl.CoreEngine;
import pl.vgtworld.lwjgl.CoreEngineException;
import pl.vgtworld.lwjgl.entities.Entity;
import pl.vgtworld.lwjgl.entities.EntityException;
import pl.vgtworld.lwjgl.entities.GlBeginEntity;
import pl.vgtworld.lwjgl.io.Keyboard;
import pl.vgtworld.lwjgl.projections.Camera;
import pl.vgtworld.lwjgl.textures.TextureLoader;
import pl.vgtworld.lwjgl.textures.TextureLoaderException;

public class Sokoban extends CoreEngine {
	
	private Entity box;
	
	private Camera camera = new Camera();
	
	private Keyboard keyboard = new Keyboard();

	@Override
	public void init() throws CoreEngineException {
		try {
			//load entity
			box = createBox();
			
			//load texture
			TextureLoader loader = new TextureLoader();
			int boxTexture = loader.loadTexture(getClass().getResource("/1.jpg"));
			box.setTextureId(boxTexture);
			
		} catch (TextureLoaderException | EntityException e) {
			throw new CoreEngineException("Exception while loading data", e);
		}
		
		//position camera
		camera.position.setX(0f);
		camera.position.setY(2.5f);
		camera.position.setZ(6.5f);
		camera.position.setAngleX(0f);
	}

	@Override
	public void update(long elapsedTime) {
		keyboard.updateKeysState();
		camera.update(elapsedTime);
	}

	@Override
	public void render() {
		setPerspectiveProjection(90, 0.1f, 1000f);
		renderObject(box, camera);
	}
	
	private static Entity createBox() throws EntityException {
		float[] vertices = {
				-0.5f, 0f, -0.5f,
				0.5f, 0f, -0.5f,
				0.5f, 0f, 0.5f,
				-0.5f, 0f, 0.5f,
				-0.5f, 1f, 0.5f,
				0.5f, 1f, 0.5f,
				0.5f, 1f, -0.5f,
				-0.5f, 1f, -0.5f
		};
		int[] indices = {
				0, 1, 2,
				0, 2, 3,
				4, 5, 6,
				6, 7, 4,
				4, 3, 2,
				2, 5, 4,
				6, 1, 0,
				0, 7, 6,
				0, 3, 4,
				4, 7, 0,
				5, 2, 1,
				1, 6, 5
		};
		float[] textureCoordinates = {
				1f, 1f, 0f, 1f, 0f, 0f,
				1f, 1f, 0f, 0f, 1f, 0f,
				0f, 1f, 1f, 1f, 1f, 0f,
				1f, 0f, 0f, 0f, 0f, 1f,
				0f, 0f, 0f, 1f, 1f, 1f,
				1f, 1f, 1f, 0f, 0f, 0f,
				0f, 0f, 0f, 1f, 1f, 1f,
				1f, 1f, 1f, 0f, 0f, 0f,
				0f, 1f, 1f, 1f, 1f, 0f,
				1f, 0f, 0f, 0f, 0f, 1f,
				0f, 0f, 0f, 1f, 1f, 1f,
				1f, 1f, 1f, 0f, 0f, 0f
		};
		
		Entity entity = new GlBeginEntity();
		entity.setMeshData(vertices, indices, textureCoordinates);
		return entity;
	}
	
	public static void main(String[] args) throws CoreEngineException {
		Sokoban sokoban = new Sokoban();
		sokoban.setWindowSize(800, 600);
		sokoban.setResizable(true);
		sokoban.start(50);
	}
}
