package com.theccode.opengl;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.awt.image.BufferedImage;

import org.lwjgl.opengl.GLContext;

public class Main {
	
	public boolean running = true;
	
	private long window;
	
	private int width = 1280, height = 800;
	
	private Model leftSide, rightSide;
	
	public static void main(String[] args) {
		/*for(float i : MeshMaker.createLeftMesh()) {
			System.out.println(i);
		}*/
		
		Main game = new Main();
		game.start();
	}
	
	public void start() {
		running = true;
		init();
		
		long startTime = System.currentTimeMillis();
		long delta = 0;
		float interval = 1000f/30f;
		
		long timer = System.currentTimeMillis();
		int ups = 0, fps = 0;
		
		while(running) {
			long now = System.currentTimeMillis();
			delta += now - startTime;
			startTime = now;
			
			while(delta >= interval) {
				update();
				delta -= interval;
				++ups;
			}
			render();
			++fps;
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("ups: " + ups + " | fps: " + fps);
				ups = 0;
				fps = 0;
				timer = System.currentTimeMillis();
			}
			
			if(glfwWindowShouldClose(window) == GL_TRUE) {
				running = false;
			}
		}
	}
	
	public void init() {
		if(glfwInit() != GL_TRUE) {
			System.err.println("GLFW initialization failed");
		}
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		
		window = glfwCreateWindow(width, height, "Distortion", NULL, NULL);
		
		if(window == NULL) {
			System.err.println("Could not create our Window!");
		}
		
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		
		GLContext.createFromCurrent();
		
		glClearColor(0f, 0f, 0f, 1f);
	
		
//		float[] leftVertices = {
//				-1f, -1f, 0f,
//				 0f, -1f, 0f,
//				 0f,  1f, 0f,
//				-1f,  1f, 0f
//		};
//		
//		float[] rightVertices = {
//				0f, -1f, 0f,
//				1f, -1f, 0f,
//				1f,  1f, 0f,
//				0f,  1f, 0f
//		};
		
//		int[] indices = {
//				0, 2, 3,
//				0, 1, 2
//		};
//		
//		float[] textureCoords = {
//				 0f, 1f,
//				 1f, 1f,
//				 1f, 0f,
//				 0f, 0f
//		};
		
		BufferedImage texture = com.theccode.opengl.TextureLoader.loadImage("res/image.png");
		
		int[] indices = MeshMaker.indices();
		float[] textureCoords = MeshMaker.textureCoords();
		
		leftSide = new Model(/*MeshMaker.distortMesh(*/MeshMaker.leftMesh()/*, true)*/, indices, textureCoords, texture, "shaders/vertexShaderLeft.txt", "shaders/fragmentShader.txt");
		rightSide = new Model(/*MeshMaker.distortMesh(*/MeshMaker.rightMesh()/*, false)*/, indices, textureCoords, texture, "shaders/vertexShaderRight.txt", "shaders/fragmentShader.txt");
	}
	
	public void update() {
		glfwPollEvents();
	}
	
	public void render() {
		glfwSwapBuffers(window);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		
		//Left side
		glUseProgram(leftSide.programID);
		
		glBindVertexArray(leftSide.vao);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		//GL13.glActiveTexture(GL13.GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, leftSide.textureID);
		glDrawElements(GL_TRIANGLES, leftSide.size, GL_UNSIGNED_INT, 0);
		glBindTexture(GL_TEXTURE_2D, 0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindVertexArray(0);

		glUseProgram(0);
		
		
		//Right side
		glUseProgram(rightSide.programID);
			
		glBindVertexArray(rightSide.vao);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		//GL13.glActiveTexture(GL13.GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, rightSide.textureID);
		glDrawElements(GL_TRIANGLES, rightSide.size, GL_UNSIGNED_INT, 0);
		glBindTexture(GL_TEXTURE_2D, 0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindVertexArray(0);
		
		glUseProgram(0);
	}
}