package pl.vgtworld.lwjgl;

class WindowSettings {
	
	private static final int DEFAULT_WIDTH = 640;
	
	private static final int DEFAULT_HEIGHT = 480;
	
	private static final boolean DEFAULT_RESIZABLE = false;
	
	private int width = DEFAULT_WIDTH;
	
	private int height = DEFAULT_HEIGHT;
	
	private boolean resizable = DEFAULT_RESIZABLE;
	
	private String title = "App";

	int getWidth() {
		return width;
	}

	void setWidth(int width) {
		this.width = width;
	}

	int getHeight() {
		return height;
	}

	void setHeight(int height) {
		this.height = height;
	}

	boolean isResizable() {
		return resizable;
	}

	void setResizable(boolean resizable) {
		this.resizable = resizable;
	}
	
	String getTitle() {
		return title;
	}
	
	void setTitle(String title) {
		this.title = title;
	}
	
}
