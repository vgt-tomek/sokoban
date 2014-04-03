package pl.vgtworld.sokoban;

import pl.vgtworld.lwjgl.CoreEngine;
import pl.vgtworld.lwjgl.CoreEngineException;
import pl.vgtworld.lwjgl.entities.Entity;
import pl.vgtworld.lwjgl.entities.EntityException;
import pl.vgtworld.lwjgl.entities.WorldEntity;
import pl.vgtworld.lwjgl.entities.loader.EntityLoader;
import pl.vgtworld.lwjgl.io.Keyboard;
import pl.vgtworld.lwjgl.projections.Camera;
import pl.vgtworld.lwjgl.textures.TextureLoader;
import pl.vgtworld.lwjgl.textures.TextureLoaderException;

public class Sokoban extends CoreEngine {
	
	private WorldEntity box;
	
	private Camera camera = new Camera();
	
	private Keyboard keyboard = new Keyboard();

	@Override
	public void init() throws CoreEngineException {
		try {
			//load entity
			EntityLoader entityLoader = new EntityLoader();
			Entity boxModel = entityLoader.load(Sokoban.class.getResourceAsStream("/models/cube1.obj"));
			
			//load texture
			TextureLoader loader = new TextureLoader();
			int boxTexture = loader.loadTexture(getClass().getResource("/1.jpg"));
			boxModel.setTextureId(boxTexture);
			box = new WorldEntity(boxModel);
			
		} catch (TextureLoaderException | EntityException e) {
			throw new CoreEngineException("Exception while loading data", e);
		}
		
		//position camera
		camera.position.setX(0f);
		camera.position.setY(1.4f);
		camera.position.setZ(1.8f);
		camera.position.setAngleX(30f);
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
	
	public static void main(String[] args) throws CoreEngineException {
		Sokoban sokoban = new Sokoban();
		sokoban.setWindowSize(800, 600);
		sokoban.setResizable(true);
		sokoban.start(50);
	}
}
