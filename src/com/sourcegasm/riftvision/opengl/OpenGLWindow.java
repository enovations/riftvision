package com.sourcegasm.riftvision.opengl;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.awt.image.BufferedImage;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GLContext;

public class OpenGLWindow {
	
	public boolean running = true;
	
	private long window;
	
	private int width = 1280, height = 800;
	
	public GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
		public void invoke(long arg0, int arg1, int arg2, int arg3, int arg4) {
			if(arg1 == GLFW_KEY_ESCAPE && arg3 == GLFW_PRESS) {
				System.exit(0);
			}
		};
	};
	

	public Model leftSide, rightSide;
	
	public BufferedImage rightImage;
	public BufferedImage leftImage;

    public void start() {
        running = true;
		init();
		
		long startTime = System.currentTimeMillis();
		long delta = 0;
		float interval = 1000f/30f;

        while (running) {
            long now = System.currentTimeMillis();
			delta += now - startTime;
			startTime = now;

            leftSide.textureID = TextureLoader.loadTexture(leftImage);
            rightSide.textureID = TextureLoader.loadTexture(rightImage);

            while(delta >= interval) {
				update();
				delta -= interval;
			}
			render();

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
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		
		window = glfwCreateWindow(width, height, "RiftVision", glfwGetPrimaryMonitor() , NULL);
		
		if(window == NULL) {
			System.err.println("Could not create our Window!");
		}
		
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		
		GLContext.createFromCurrent();
		
		glClearColor(0f, 0f, 0f, 1f);
		
		glfwSetKeyCallback(window, keyCallback);

		BufferedImage texture = TextureLoader.loadImage("res/grid.png");
		
		leftImage = texture;
		rightImage = texture;
		
		int[] indices = MeshMaker.indices();
		float[] textureCoords = MeshMaker.textureCoords();

		leftSide = new Model(MeshMaker.leftMesh(), indices, textureCoords, texture, "shaders/vertexShaderLeft.txt", "shaders/fragmentShader.txt");
		rightSide = new Model(MeshMaker.rightMesh(), indices, textureCoords, texture, "shaders/vertexShaderRight.txt", "shaders/fragmentShader.txt");
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
		glBindTexture(GL_TEXTURE_2D, rightSide.textureID);
		glDrawElements(GL_TRIANGLES, rightSide.size, GL_UNSIGNED_INT, 0);
		glBindTexture(GL_TEXTURE_2D, 0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindVertexArray(0);
		
		glUseProgram(0);
	}
}