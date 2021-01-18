package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    private OpenGLView glView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glView = new OpenGLView(this);
        setContentView(glView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        glView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glView.onPause();
    }
}
