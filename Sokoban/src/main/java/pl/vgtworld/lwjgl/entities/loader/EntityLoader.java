package pl.vgtworld.lwjgl.entities.loader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pl.vgtworld.lwjgl.entities.Entity;
import pl.vgtworld.lwjgl.entities.EntityException;
import pl.vgtworld.lwjgl.entities.GlBeginEntity;

public class EntityLoader {
	
	private static final int LINE_DATA_COUNT = 4;

	private static final int TEXTURE_INDEX_OFFSET = 1;

	private static final int VERTEX_INDEX_OFFSET = 1;

	private static final String FACE_DATA_PREFIX = "f";

	private static final String TEXTURE_COORDINATES_DATA_PREFIX = "vt";

	private static final String VERTEX_DATA_PREFIX = "v";

	private static final String DATA_SEPARATOR = " +";

	private static final String FACE_DATA_SEPARATOR = "/";

	private List<Float> vertexList = new ArrayList<>();
	
	private List<Float> textureCoordinates = new ArrayList<>();
	
	private List<String> faces = new ArrayList<>();
	
	public Entity load(InputStream stream) throws EntityException {
		vertexList.clear();
		textureCoordinates.clear();
		faces.clear();
		Scanner scanner = new Scanner(stream);
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			parseLine(line);
		}
		scanner.close();
		
		return buildEntity();
	}
	
	private Entity buildEntity() throws EntityException {
		List<Float> entityTextureCoordinates = new ArrayList<>();
		List<Integer> entityIndices = new ArrayList<>();
		
		for (String face : faces) {
			String[] elements = face.split(FACE_DATA_SEPARATOR);
			int vertexIndex = Integer.parseInt(elements[0]) - VERTEX_INDEX_OFFSET;
			int textureIndex = Integer.parseInt(elements[1]) - TEXTURE_INDEX_OFFSET;
			
			entityIndices.add(vertexIndex);
			entityTextureCoordinates.add(textureCoordinates.get(textureIndex * 3));
			entityTextureCoordinates.add(textureCoordinates.get(textureIndex * 3 + 1));
			
		}
		
		Entity entity = new GlBeginEntity();
		entity.setMeshData(
				convertToFloatArray(vertexList),
				convertToIntArray(entityIndices),
				convertToFloatArray(entityTextureCoordinates)
				);
		return entity;
	}
	
	private void parseLine(String line) {
		String[] elements = line.split(DATA_SEPARATOR);
		if (elements.length > 0) {
			if (elements[0].equals(VERTEX_DATA_PREFIX) && elements.length == LINE_DATA_COUNT) {
				processVertex(elements);
				return;
			}
			if (elements[0].equals(TEXTURE_COORDINATES_DATA_PREFIX) && elements.length == LINE_DATA_COUNT) {
				processTextureCoordinates(elements);
				return;
			}
			if (elements[0].equals(FACE_DATA_PREFIX) && elements.length == LINE_DATA_COUNT) {
				processFace(elements);
				return;
			}
		}
	}

	private void processVertex(String[] elements) {
		float x = Float.parseFloat(elements[1]);
		float y = Float.parseFloat(elements[2]);
		float z = Float.parseFloat(elements[3]);
		vertexList.add(y);
		vertexList.add(z);
		vertexList.add(x);
	}

	private void processTextureCoordinates(String[] elements) {
		for (int i = 1; i < elements.length; ++i) {
			float coordinate = Float.parseFloat(elements[i]);
			textureCoordinates.add(coordinate);
		}
	}
	
	private void processFace(String[] elements) {
		for (int i = 1; i < elements.length; ++i) {
			faces.add(elements[i]);
		}
	}

	private float[] convertToFloatArray(List<Float> elements) {
		float[] array = new float[elements.size()];
		for (int i = 0; i < elements.size(); ++i) {
			array[i] = elements.get(i);
		}
		return array;
	}
	
	private int[] convertToIntArray(List<Integer> elements) {
		int[] array = new int[elements.size()];
		for (int i = 0; i < elements.size(); ++i) {
			array[i] = elements.get(i);
		}
		return array;
	}
}
