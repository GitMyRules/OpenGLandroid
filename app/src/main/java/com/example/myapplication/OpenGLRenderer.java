package com.example.myapplication;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLRenderer implements GLSurfaceView.Renderer{

    private final float[] model = new float[16];
    private final float[] projection = new float[16];
    private final float[] view = new float[16];

    public volatile float mAngle;

    private Triangle triangle;

    float x;
    float y;

    @Override
    public void onDrawFrame(GL10 gl) {


        GLES30.glDepthMask(false);
        GLES30.glEnable(GLES30.GL_BLEND);
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_DST_COLOR);

        GLES30.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
       //Camera position
        Matrix.setLookAtM(view, 0,0f,0,-6f, 0f, 0f, 0f,0f,1.0f, 0.0f);
        //Calculate the projection and view transformation
        Matrix.multiplyMM(model,0,projection,0,view,0);

        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);

        Matrix.translateM(model, 0,0f,0f,0f);
        Matrix.rotateM(model,0, mAngle+angle,Matrix.length(x,0,0),Matrix.length(0,y+1,0),0);

        triangle.draw(model);

        GLES30.glDisable(GLES30.GL_BLEND);
        GLES30.glDepthMask(true);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0,0, width, height);

        float ratio = (float) width/height;
        Matrix.frustumM(projection, 0, -ratio, ratio, -1, 1, 3, 7);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
         triangle = new Triangle();

    }

    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float angle) {
        mAngle = angle;
    }

    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){ this.y = y; }

}
