package com.example.cs188_student.cardroid;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

/**
 * Wrap up socket.io and allows repetitive packet sending
 */
public class SocketLooper {

    private Socket socket;
    private String secretId = "stevesId";
    public boolean forward = false;
    public boolean backward = false;
    public boolean left = false;
    public boolean right = false;

    /**
     * Construct the socket, connect, and being data sending loop
     */
    public SocketLooper() {
        // Basic socket stuff
        try {
            // set the ip and port of the server (this will be changed)
            socket = IO.socket("http://stevenrgatto.com:5001");

            // define callback on the CONNECT event
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    // sync your secret id with the server
                    socket.emit("sync", secretId);
                    Log.d(null, "Socket connected successfully");
                }
            });

            // After we have defined all of our callbacks, try to connect
            socket.connect();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // This is where the loop begins in a new thread, do not change
        new Thread(new Runnable() {
            public void run() {
                socketLoop(socket, secretId, 100);
            }
        }).start();
    }

    /**
     * Loops continuously sending packets to the socket.io server
     *
     * @param socket socket object to repeatedly emit messages with
     * @param secretId secret string to authenticate messages
     * @param millisDelay delay between packets sent
     */
    private void socketLoop(Socket socket, String secretId, long millisDelay) {
        while (true) {
            if (socket != null) {
                // Handle forward vs backward
                if (forward) {
                    socket.emit("message", secretId, "up button was clicked");
                } else if (backward) {
                    socket.emit("message", secretId, "down button was clicked");
                }

                // Handle left vs right
                if (left) {
                    socket.emit("message", secretId, "left button was clicked");
                } else if (right) {
                    socket.emit("message", secretId, "right button was clicked");
                }
                try {
                    Thread.sleep(millisDelay); // send ten packets per second, adjust as necessary
                } catch (InterruptedException ie) {
                    // do nothing, doesn't affect us much
                }
            }
        }
    }

}
