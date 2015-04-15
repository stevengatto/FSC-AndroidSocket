package com.example.cs188_student.cardroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class MainActivity extends Activity implements View.OnClickListener {

    private ImageView upButton, downButton;
    private Socket socket;
    private String id = "stevesId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        upButton = (ImageView) findViewById(R.id.up_button);
        upButton.setOnClickListener(this);

        downButton = (ImageView) findViewById(R.id.down_button);
        downButton.setOnClickListener(this);

        try {
            socket = IO.socket("http://stevenrgatto.com:5001");

            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                   Log.d(null, "connected");
                    socket.emit("sync", id);
                }
            });

            socket.connect();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.up_button:
                // Up button socket stuff
                if (socket != null) {
                    socket.emit("message", id, "up button was clicked");
                }

                // Up button stuff here
                Log.d(null, "We are in the up button click case");
                break;
            case R.id.down_button:
                // Up button socket stuff
                if (socket != null) {
                    socket.emit("message", id, "down button was clicked");
                }

                // Down button stuff here
                Log.d(null, "We are in the down button click case");
                break;
        }
    }
}
