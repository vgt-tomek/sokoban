package pl.vgtworld.lwjgl.textures;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class TextureLoader {
	
	private static final int RED_OFFSET = 16;
	
	private static final int GREEN_OFFSET = 8;
	
	private static final int ALPHA_OFFSET = 24;
	
	private static final int COLOR_CHANNEL_MASK = 0xFF;
	
	private static final int BYTES_PER_PIXEL = 4;

	public int loadTexture(URL file) throws TextureLoaderException {
		try {
			BufferedImage image = ImageIO.read(file);
			return loadTextureFromImage(image);
		} catch (IOException e) {
			throw new TextureLoaderException("Exception while loading texture from input stream ", e);
		}
	}
	
	private int loadTextureFromImage(BufferedImage image) {
		ByteBuffer byteArray = convertImageToByteArray(image);
		int textureId = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexImage2D(
				GL11.GL_TEXTURE_2D,
				0,
				GL11.GL_RGBA,
				image.getWidth(),
				image.getHeight(),
				0,
				GL11.GL_RGBA,
				GL11.GL_UNSIGNED_BYTE,
				byteArray
				);
		return textureId;
	}

	private ByteBuffer convertImageToByteArray(BufferedImage image) {
		int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
		int pixelsBufferSize = calculateByteBufferSize(image.getWidth(), image.getHeight());
		ByteBuffer pixelsBuffer = BufferUtils.createByteBuffer(pixelsBufferSize);
		pixelsBuffer.order(ByteOrder.nativeOrder());
		for (int index = 0; index < pixels.length; ++index) {
			int pixel = pixels[index];
			pixelsBuffer.put((byte) ((pixel >> RED_OFFSET) & COLOR_CHANNEL_MASK));
			pixelsBuffer.put((byte) ((pixel >> GREEN_OFFSET) & COLOR_CHANNEL_MASK));
			pixelsBuffer.put((byte) (pixel & COLOR_CHANNEL_MASK));
			pixelsBuffer.put((byte) ((pixel >> ALPHA_OFFSET) & COLOR_CHANNEL_MASK));
		}
		pixelsBuffer.position(0);
		return pixelsBuffer;
	}
	
	private int calculateByteBufferSize(int imageWidth, int imageHeight) {
		int biggerEdge = Math.max(imageWidth, imageHeight);
		return biggerEdge * biggerEdge * BYTES_PER_PIXEL;
	}
}
