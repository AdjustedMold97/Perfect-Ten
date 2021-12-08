package com.example.PerfectTen.Screens;

import static com.example.PerfectTen.net_utils.Const.DMS_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.PerfectTen.R;
import com.example.PerfectTen.app.AppController;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class DMsScreen extends AppCompatActivity {

    TextView mOutput;
    EditText mInput;
    Button send;
    WebSocketClient mWebSocketClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dms_screen);

        TextView username = findViewById(R.id.dms_username_TextView);
        username.setText(AppController.getTargetUser());

        Button backBtn = findViewById(R.id.dms_back_Button);
        backBtn.setOnClickListener(view -> startActivity(new Intent(view.getContext(), FriendsScreen.class)));

        mOutput = findViewById(R.id.DMs_Feed_TextView);
        mOutput.setMovementMethod(new ScrollingMovementMethod());
        mInput = findViewById(R.id.dms_Input_Text);
        send = findViewById(R.id.dms_send_Button);
        send.setOnClickListener(view -> sendMessage());

        connectWebSocket();
    }

    private void connectWebSocket() {

        URI uri;

        try {
            uri = new URI(DMS_URL + AppController.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {

            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.i("Websocket", "Opened");
                mOutput.append("Connected.");
            }

            @Override
            public void onMessage(String message) {
                Log.i("Websocket", "Message Received");
                mOutput.append("\n"
                        + AppController.getTargetUser() + ": "
                        + message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.i("Websocket", "Closed " + reason);
            }

            @Override
            public void onError(Exception ex) {
                Log.i("Websocket", "Error" + ex.getMessage());
            }
        };

        mWebSocketClient.connect();
    }

    private void sendMessage() {

        String message = mInput.getText().toString();

        if (message != null && message.length() > 0) {

            mWebSocketClient.send(message);
            mOutput.append("\n" + "You: " + message);

        }

    }
}