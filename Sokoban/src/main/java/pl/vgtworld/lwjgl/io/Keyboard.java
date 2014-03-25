package pl.vgtworld.lwjgl.io;

public class Keyboard {
	
	private static final int NUMBER_OF_KEYS = 256;

	private boolean[] pressed = new boolean[NUMBER_OF_KEYS];
	
	private boolean[] checked = new boolean[NUMBER_OF_KEYS];
	
	public void updateKeysState() {
		for (int i = 0; i < NUMBER_OF_KEYS; ++i) {
			boolean keyDown = org.lwjgl.input.Keyboard.isKeyDown(i);
			if (pressed[i] != keyDown) {
				checked[i] = false;
			}
			pressed[i] = keyDown;
		}
	}
	
	public boolean isKeyPressed(Keys key) {
		return isKeyPressed(key, false);
	}
	
	public boolean isKeyPressedOnce(Keys key) {
		return isKeyPressed(key, true);
	}
	
	private boolean isKeyPressed(Keys key, boolean confirmOnce) {
		boolean isPressed = pressed[key.getValue()];
		if (!isPressed) {
			return false;
		}
		if (!confirmOnce) {
			return true;
		}
		if (!checked[key.getValue()]) {
			checked[key.getValue()] = true;
			return true;
		}
		return false;
	}
}
