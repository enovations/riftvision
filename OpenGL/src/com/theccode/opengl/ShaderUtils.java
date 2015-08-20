package com.theccode.opengl;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class ShaderUtils {

	public static int load(String vertPath, String fragPath) {
		String vert = Utilities.loadAsString(vertPath);
		String frag = Utilities.loadAsString(fragPath);
		return create(vert, frag);
	}
	
	public static int create(String vert, String frag) {
		int program = glCreateProgram();
		
		int vertID = glCreateShader(GL_VERTEX_SHADER);
		int fragID = glCreateShader(GL_FRAGMENT_SHADER);
		
		glShaderSource(vertID, vert);
		glShaderSource(fragID, frag);
		
		glCompileShader(vertID);
		if(glGetShaderi(vertID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failed to compile vertex shader!");
			System.err.println(glGetShaderInfoLog(vertID));
		}
		
		glCompileShader(fragID);
		if (glGetShaderi(fragID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failed to compile fragment shader!");
			System.err.println(glGetShaderInfoLog(fragID));
		}
		
		glAttachShader(program, vertID);
		glAttachShader(program, fragID);
		glLinkProgram(program);
		glValidateProgram(program);
		
		return program;
	}
}