package com.example.cs188_student.cardroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class MainActivity extends Activity implements View.OnTouchListener {

    private SocketLooper socketLooper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.up_button).setOnTouchListener(this);
        findViewById(R.id.down_button).setOnTouchListener(this);

        socketLooper = new SocketLooper();
    }

    public boolean onTouch(View view, MotionEvent event) {
        // Turn things on when button is pressed down
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switch (view.getId()) {
                case R.id.up_button:
                    socketLooper.forward = true;
                    break;
                case R.id.down_button:
                    socketLooper.backward = true;
                    break;
            }
            return true;
        }
        // Turn things off when button is released
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            switch (view.getId()) {
                case R.id.up_button:
                    socketLooper.forward = false;
                    break;
                case R.id.down_button:
                    socketLooper.backward = false;
                    break;
            }
            return true;
        }
        return true;
    }
}
