package com.example.PerfectTen.Screens;

import static com.example.PerfectTen.net_utils.Const.DMS_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.PerfectTen.R;
import com.example.PerfectTen.app.AppController;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class DMsScreen extends AppCompatActivity {

    TextView mOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dms_screen);

        TextView username = findViewById(R.id.dms_username_TextView);
        username.setText(AppController.getTargetUser());

        Button backBtn = findViewById(R.id.dms_back_Button);
        backBtn.setOnClickListener(view -> startActivity(new Intent(view.getContext(), FriendsScreen.class)));

        mOutput = findViewById(R.id.DMs_Feed_TextView);
    }

    private void connectWebSocket() {

        URI uri;

        try {

            uri = new URI(DMS_URL);

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        WebSocketClient mWebSocketClient = new WebSocketClient(uri) {

            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.i("Websocket", "Opened");
                mOutput.append("Connected.");
            }

            @Override
            public void onMessage(String message) {
                Log.i("Websocket", "Message Received");

                mOutput.append("\n" + message);
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
}