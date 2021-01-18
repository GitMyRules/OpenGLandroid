package com.example.myapplication;

import android.content.Context;

import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class OpenGLView extends GLSurfaceView {



    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float previousX;
    private float previousY;

    private final OpenGLRenderer renderer;

    public OpenGLView(Context context) {
        super(context);

        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);

        renderer = new OpenGLRenderer();

        setRenderer(renderer);
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {

        float x = e.getX();
        float y = e.getY();


        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - previousX;
                float dy = y - previousY;

                // reverse direction of rotation above the mid-line
                if(dy < getHeight()) {
                    dy = dy * -1 ;
                }

                // reverse direction of rotation to left of the mid-line
                if(dx > getWidth()) {
                    dx = dx * -1 ;
                }

                renderer.setAngle(
                        renderer.getAngle() +
                                ((dx + dy) * TOUCH_SCALE_FACTOR));

                renderer.setX(x);
                renderer.setY(y);

                requestRender();
        }

        previousX = x;
        previousY = y;

        return true;

    }

}