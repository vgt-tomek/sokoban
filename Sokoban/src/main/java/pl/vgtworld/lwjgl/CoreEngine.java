package pl.vgtworld.lwjgl;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import pl.vgtworld.lwjgl.entities.Entity;
import pl.vgtworld.lwjgl.projections.Camera;

public abstract class CoreEngine {
	
	private boolean running;
	
	private WindowSettings windowSettings = new WindowSettings();
	
	public void setTitle(String title) {
		windowSettings.setTitle(title);
	}
	
	public void setWindowSize(int width, int height) {
		windowSettings.setWidth(width);
		windowSettings.setHeight(height);
	}
	
	public void setPerspectiveProjection(int fov, float nearClippingPlane, float farClippingPlane) {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		float ratio = (float)windowSettings.getWidth() / windowSettings.getHeight();
		GLU.gluPerspective(fov / ratio, ratio, nearClippingPlane, farClippingPlane);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	public void setOrthographicProjection() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 1, 0, 1, 1, 0);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	public void start(int framerate) throws CoreEngineException {
		coreInit();
		init();
		
		running = true;
		long time = System.currentTimeMillis();
		while (running) {
			long elapsedTime = System.currentTimeMillis() - time;
			time+= elapsedTime;
			update(elapsedTime);
			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			render();
			Display.update();
			Display.sync(framerate);
			
			if (Display.isCloseRequested()) {
				running = false;
				Display.destroy();
				System.exit(0);
			}
		}
	}
	
	public void stop() {
		running = false;
	}
	
	public abstract void init() throws CoreEngineException;
	
	public abstract void update(long elapsedTime);
	
	public abstract void render();
	
	protected void renderEntity(Entity entity, Camera camera) {
		GL11.glLoadIdentity();
		camera.translation();
		entity.draw();
	}
	
	private void coreInit() throws CoreEngineException {
		try {
			DisplayMode chosenMode = new DisplayMode(windowSettings.getWidth(), windowSettings.getHeight());
			Display.setDisplayMode(chosenMode);
			Display.setTitle(windowSettings.getTitle());
			Display.setResizable(windowSettings.isResizable());
			Display.create();
		} catch (LWJGLException e) {
			throw new CoreEngineException("Error during OpenGL window initialization", e);
		}
		
		GL11.glClearColor(0, 0, 0, 0);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glViewport(0, 0, windowSettings.getWidth(), windowSettings.getHeight());
	}
}
