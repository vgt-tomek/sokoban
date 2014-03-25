package pl.vgtworld.lwjgl;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import pl.vgtworld.lwjgl.entities.Entity;
import pl.vgtworld.lwjgl.entities.EntityException;
import pl.vgtworld.lwjgl.entities.GlBeginEntity;
import pl.vgtworld.lwjgl.textures.TextureLoader;
import pl.vgtworld.lwjgl.textures.TextureLoaderException;

/**
 * Hello world!
 * 
 */
public class App
{
	static float cameraX = -0.5f, cameraY = 2.5f, cameraZ = 6.4f;
	static float cameraAngleX = 27.0f, cameraAngleY = 0.0f;
	
	static final int CUBE_MATRIX_LENGTH = 50;
	
	static float[] scale = new float[CUBE_MATRIX_LENGTH * CUBE_MATRIX_LENGTH];
	static float[] yRotation = new float[CUBE_MATRIX_LENGTH * CUBE_MATRIX_LENGTH];
	static float[] yRotationDelta = new float[CUBE_MATRIX_LENGTH * CUBE_MATRIX_LENGTH];
	static int[] texture = new int[CUBE_MATRIX_LENGTH * CUBE_MATRIX_LENGTH];
	static int[] textureIds = new int[32];
	static int healthBarTextureId;
	static int floorTextureId;
	
	public static void main(String[] args) throws LWJGLException {
		regenerate();
		//new TheQuadExampleDrawArrays();
		//new TheQuadExampleColored();
		test();
	}

	private static void regenerate() {
		for (int i = 0; i < CUBE_MATRIX_LENGTH * CUBE_MATRIX_LENGTH; ++i) {
			scale[i] = (float) Math.random();
			yRotation[i] = 0;
			if (scale[i] < 0.7) {
				yRotationDelta[i] = (float) (Math.random() - 0.5f) * 4;
			} else {
				yRotationDelta[i] = 0;
			}
			texture[i] = (int) (Math.random() * textureIds.length);
		}
	}
	
	public static void test()
	{
		int targetWidth = 1600;
		int targetHeight = 900;
		
		try {
			DisplayMode chosenMode = new DisplayMode(targetWidth, targetHeight);
			Display.setDisplayMode(chosenMode);
			Display.setTitle("LWJGL test");
			Display.setLocation(0, 0);
			Display.setResizable(true);
			Display.create();
		} catch (LWJGLException e) {
			Sys.alert("Error", "Unable to create display.");
			System.exit(0);
		}
		GL11.glClearColor(0, 0, 0, 0);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glViewport(0, 0, targetWidth, targetHeight);
		
		boolean gameRunning = true;
		
		int FRAMERATE = 60;
		long startTime = Sys.getTime();
		long frames = 0;
		
		//load textures
		try {
			TextureLoader textureLoader = new TextureLoader();
			for (int i = 0; i < textureIds.length; ++i) {
				textureIds[i] = textureLoader.loadTexture(App.class.getResource("/" + (i + 1) + ".jpg"));
			}
			healthBarTextureId = textureLoader.loadTexture(App.class.getResource("/health-bar.png"));
			floorTextureId = textureLoader.loadTexture(App.class.getResource("/floor-3.jpg"));
		} catch (TextureLoaderException e) {
			Sys.alert("Error", "Unable to load textures");
			System.exit(0);
		}
		
		Entity boxEntity = null;
		Entity guiHealthBar = null;
		Entity floorEntity = null;
		try {
			boxEntity = createBox();
			guiHealthBar = createHealthBar();
			guiHealthBar.setTextureId(healthBarTextureId);
			floorEntity = createFloor();
			floorEntity.setTextureId(floorTextureId);
			floorEntity.setColor(0.5f, 0.5f, 0.5f, 1);
		} catch (EntityException e) {
			Sys.alert("Error", "Unable to load models");
			System.exit(0);
		}
		
		while (gameRunning) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			float ratio = (float)targetWidth / targetHeight;
			GLU.gluPerspective(90 / ratio, ratio, 0.1f, 1000);
			
			
			
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			
			for (int i = 0; i < CUBE_MATRIX_LENGTH * CUBE_MATRIX_LENGTH; ++i) {
				int x = i % CUBE_MATRIX_LENGTH - CUBE_MATRIX_LENGTH/2;
				int z = i / CUBE_MATRIX_LENGTH - CUBE_MATRIX_LENGTH/2;
				
				//cube
				GL11.glLoadIdentity();
				cameraPosition();
				yRotation[i]+= yRotationDelta[i];
				GL11.glTranslatef(x, 0, z);
				GL11.glRotatef(yRotation[i], 0, 1, 0);
				GL11.glScalef(scale[i], scale[i], scale[i]);
				boxEntity.setTextureId(textureIds[texture[i]]);
				boxEntity.draw();
				
				//floor
				GL11.glLoadIdentity();
				cameraPosition();
				GL11.glTranslatef(x, 0, z);
				floorEntity.draw();
			}
			
			//gui
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, 1, 0, 1, 1, 0);
			
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			
			GL11.glLoadIdentity();
			guiHealthBar.draw();
			

			Display.update();
			Display.sync(FRAMERATE);
			
			if (Mouse.isButtonDown(0)) {
				regenerate();
				int mouseX = Mouse.getX();
				int mouseY = Mouse.getY();
				System.out.println(String.format("Mouse: %d,%d", mouseX, mouseY));
			}
			
			if (Display.wasResized()) {
				targetWidth = Display.getWidth();
				targetHeight = Display.getHeight();
				GL11.glViewport(0, 0, targetWidth, targetHeight);
				System.out.println(String.format("new size: %d,%d", targetWidth, targetHeight));
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				cameraZ-= 0.01f;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				cameraZ+= 0.01f;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				cameraX-= 0.01f;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				cameraX+= 0.01f;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				cameraY+= 0.01f;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
				cameraY-= 0.01f;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8)) {
				cameraAngleX-= 1f;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2)) {
				cameraAngleX+= 1f;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4)) {
				cameraAngleY-= 1f;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD6)) {
				cameraAngleY+= 1f;
			}
			
			if (Display.isCloseRequested()) {
				gameRunning = false;
				Display.destroy();
				System.exit(0);
			}
			
			++frames;
			if (frames > FRAMERATE * 5) {
				double timeDifference = (Sys.getTime() - startTime) / Sys.getTimerResolution(); 
				double fps = frames / timeDifference;
				System.out.println("FPS: " + fps);
				frames = 0;
				startTime = Sys.getTime();
			}
		}
	}
	
	private static void cameraPosition() {
		GL11.glRotatef(cameraAngleX, 1, 0, 0);
		GL11.glRotatef(cameraAngleY, 0, 1, 0);
		GL11.glTranslatef(-cameraX, -cameraY, -cameraZ);
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
	
	private static Entity createHealthBar() throws EntityException {
		float[] vertices = {
				0.0f, 0.1f, 0.0f,
				0.0f, 0.0f, 0.0f,
				0.2f, 0.0f, 0.0f,
				0.2f, 0.1f, 0.0f
		};
		int[] indices = {
				0, 1, 2,
				2, 3, 0
		};
		float[] textureCoordinates = {
				0f, 0f, 0f, 1f, 1f, 1f,
				1f, 1f, 1f, 0f, 0f, 0f
		};
		Entity entity = new GlBeginEntity();
		entity.setMeshData(vertices, indices, textureCoordinates);
		return entity;
	}
	
	private static Entity createFloor() throws EntityException {
		float[] vertices = {
				-0.5f, 0.0f, -0.5f,
				-0.5f, 0.0f, 0.5f,
				0.5f, 0.0f, 0.5f,
				0.5f, 0.0f, -0.5f
		};
		
		int[] indices = {
				0, 1, 2,
				2, 3, 0
		};
		
		float[] textureCoordinates = {
				0f, 0f, 0f, 2f, 2f, 2f,
				2f, 2f, 2f, 0f, 0f, 0f
		};
		Entity entity = new GlBeginEntity();
		entity.setMeshData(vertices, indices, textureCoordinates);
		return entity;
	}
	
}
