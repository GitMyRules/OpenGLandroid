package com.example.myapplication;
import android.opengl.GLES30;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


public class Triangle {



    private final String vertexS =
            "uniform mat4 model;\n"+
            "attribute vec4 aPos;\n"+
            "attribute vec4 aColor;\n"+
            "varying vec4 vColor;\n"+
            "void main()\n"+
            "{\n"+
            "   vColor = aColor;\n"+
            "   gl_Position = model * aPos;\n"+
            "}";

    private final String fragmentS =
            "precision mediump float;\n" +
            "varying vec4 vColor;\n" +
            "void main() {\n" +
            "  gl_FragColor = vColor;\n" +
            "}";


    private FloatBuffer vertexBuffer;

    float vertices[] = {


            0.0f,  1.0f, 0.0f, //top
            1.0f,  0.0f, 0.0f, 1.0f,
            0.0f, -1.0f, -1.0f, //bottom left
            0.0f,  1.0f, 0.0f, 1.0f,
           -1.0f, -1.0f, 0.0f, //bottom right
            0.0f, 1.0f,  0.0f, 1.0f,

            0.0f,  1.0f, 0.0f, //top
            1.0f,  0.0f, 0.0f, 1.0f,
            1.0f, -1.0f, 0.0f, //bottom left
            0.0f,  1.0f, 0.0f, 1.0f,
            0.0f, -1.0f, -1.0f, //bottom right
            0.0f, 0.0f,  1.0f, 1.0f,

            0.0f,  1.0f, 0.0f, //top
            1.0f,  0.0f, 1.0f, 1.0f,
           -1.0f, -1.0f, 0.0f, //bottom left
            0.0f,  0.0f, 1.0f, 1.0f,
            0.0f, -1.0f, 1.0f, //bottom right
            0.0f, 1.0f,  0.0f, 1.0f,

            0.0f,  1.0f, 0.0f, //top
            1.0f,  0.0f, 1.0f, 1.0f,
            0.0f, -1.0f, 1.0f, //bottom left
            0.0f,  0.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 0.0f, //bottom right
            0.0f, 1.0f,  0.0f, 1.0f,

            //position
            0.0f, 0.0f,0.0f,
            1.0f,  0.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 0.0f,
            0.0f,  0.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 0.0f,
            0.0f, 1.0f,  0.0f, 1.0f,

            //position
            0.0f, 0.0f,0.0f,
            1.0f,  0.0f, 1.0f, 1.0f,
            0.0f, -1.0f, -1.0f,
            0.0f,  0.0f, 1.0f, 1.0f,
            0.0f, -1.0f, 1.0f,
            0.0f, 1.0f,  0.0f, 1.0f,

            //right inside bottom
            0.0f,  -1.0f, 0.0f,
            1.0f,  0.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 0.0f,
            0.0f,  0.0f, 1.0f, 1.0f,
            0.0f, -1.0f, -1.0f,
            0.0f, 1.0f,  0.0f, 1.0f,

            //left inside bottom
            0.0f,  -1.0f, 0.0f,
            1.0f,  0.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 0.0f,
            0.0f,  0.0f, 1.0f, 1.0f,
            0.0f, -1.0f, -1.0f,
            0.0f, 1.0f,  0.0f, 1.0f,

            //right outside bottom
            0.0f,  -1.0f, 0.0f,
            1.0f,  0.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 0.0f,
            0.0f,  0.0f, 1.0f, 1.0f,
            0.0f, -1.0f, 1.0f,
            0.0f, 1.0f,  0.0f, 1.0f,

            //left outside bottom
            0.0f,  -1.0f, 0.0f,
            1.0f,  0.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 0.0f,
            0.0f,  0.0f, 1.0f, 1.0f,
            0.0f, -1.0f, 1.0f,
            0.0f, 1.0f,  0.0f, 1.0f

    };

    private final int shaderProgram;

    public static int loadShader(int type, String shaderCode){
        int shader = GLES30.glCreateShader(type);

        GLES30.glShaderSource(shader, shaderCode);
        GLES30.glCompileShader(shader);
        return shader;
    }

    public Triangle(){

        vertexBuffer = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(vertices).position(0);

        int vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, vertexS);
        int fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, fragmentS);

        shaderProgram = GLES30.glCreateProgram();

        GLES30.glAttachShader(shaderProgram, vertexShader);
        GLES30.glAttachShader(shaderProgram, fragmentShader);
        GLES30.glBindAttribLocation(shaderProgram, 0, "aPos");
        GLES30.glBindAttribLocation(shaderProgram, 1, "aColor");
        GLES30.glLinkProgram(shaderProgram);
    }

    public void draw(float[] matrix){

// Add program to OpenGL ES environment

        GLES30.glUseProgram(shaderProgram);
        int aPos = GLES30.glGetAttribLocation(shaderProgram, "aPos");
        GLES30.glVertexAttribPointer(aPos,3, GLES30.GL_FLOAT, false, 28, vertexBuffer);
        GLES30.glEnableVertexAttribArray(aPos);

        int aColor = GLES30.glGetAttribLocation(shaderProgram, "aColor");

        GLES30.glVertexAttribPointer(aColor,3, GLES30.GL_FLOAT, false, 32, vertexBuffer);
        GLES30.glEnableVertexAttribArray(aColor);

        int  model = GLES30.glGetUniformLocation(shaderProgram, "model");
        GLES30.glUniformMatrix4fv(model, 1, false, matrix, 0);

        // Draw the triangle
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertices.length/7);

        // Disable vertex array
        GLES30.glDisableVertexAttribArray(aPos);


    }
}