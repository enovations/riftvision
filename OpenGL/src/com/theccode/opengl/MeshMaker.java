package com.theccode.opengl;

import java.util.ArrayList;

public class MeshMaker {
	public static float[] leftMesh() {
		ArrayList<Float> vertices = new ArrayList<Float>();
		
		for(float y = 1; y >= -1; y -= 0.1) {
			for(float x = -1; x <= 0; x += 0.1) {
				vertices.add(x);
				vertices.add(y);
				vertices.add(1f);
			}
		}
		
		float[] result = new float[vertices.size()];
		
		for(int i = 0; i < result.length; i++)
			result[i] = vertices.get(i);

		return result;
	}
	
	public static float[] textureCoords() {
		ArrayList<Float> coordinates = new ArrayList<Float>();
		
		for(float y = 0f; y <= 1.0f; y += 0.05f) {
			for(float x = 0f; x <= 1.0f; x += 0.1f) {
				coordinates.add(x);
				coordinates.add(y);
			}
		}
		
		
		
		float[] result = new float[coordinates.size()];
		
		for(int i = 0; i < result.length; i++)
			result[i] = coordinates.get(i);
		
		
		return result;
	}
	
	public static int[] indices() {
		ArrayList<Integer> indices = new ArrayList<Integer>();
		
		for(int y = 0; y < 200; ++y) {
			for(int x = 0; x < 100; ++x) {
				indices.add(y * 100 + x);
				indices.add(y * 100 + 100 + x);
				indices.add(y * 100 + x + 1);
				indices.add(y * 100 + x + 1);
				indices.add(y * 100 + 100 + x);
				indices.add(y * 100 + 100 + x + 1);
			}
		}
		
		int[] result = new int[indices.size()];
		
		for(int i = 0; i < result.length; i++)
			result[i] = indices.get(i);
		
		System.out.println(result[0] + " " + result[1] + " " + result[2]);
		return result;
	}
}