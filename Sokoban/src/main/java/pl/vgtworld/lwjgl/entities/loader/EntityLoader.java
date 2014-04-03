package pl.vgtworld.lwjgl.entities.loader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pl.vgtworld.lwjgl.entities.Entity;
import pl.vgtworld.lwjgl.entities.EntityException;
import pl.vgtworld.lwjgl.entities.GlBeginEntity;

public class EntityLoader {
	
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
			String[] elements = face.split("/");
			int vertexIndex = Integer.parseInt(elements[0]) - 1;
			int textureIndex = Integer.parseInt(elements[1]) - 1;
			
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
		String[] elements = line.split(" +");
		if (elements.length > 0) {
			if (elements[0].equals("v") && elements.length == 4) {
				processVertex(elements);
				return;
			}
			if (elements[0].equals("vt") && elements.length == 4) {
				processTextureCoordinates(elements);
				return;
			}
			if (elements[0].equals("f") && elements.length == 4) {
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
