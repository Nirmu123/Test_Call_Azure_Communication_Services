package com.example.test_call;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.media.AudioManager;
import android.Manifest;
import android.content.pm.PackageManager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.azure.android.communication.calling.DeviceManager;
import com.azure.android.communication.common.CommunicationUserIdentifier;
import com.azure.android.communication.common.CommunicationTokenCredential;
import com.azure.android.communication.calling.CallAgent;
import com.azure.android.communication.calling.CallClient;
import com.azure.android.communication.calling.StartCallOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAllPermissions();
        CallAgent callAgent = createAgent();

        Button callButton = findViewById(R.id.call_button);
        callButton.setOnClickListener(l -> startCall(callAgent));

        setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
    }

    private void getAllPermissions() {
        String[] requiredPermissions = new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
        ArrayList<String> permissionsToAskFor = new ArrayList<>();
        for (String permission : requiredPermissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToAskFor.add(permission);
            }
        }
        if (!permissionsToAskFor.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToAskFor.toArray(new String[0]), 1);
        }
    }

    private CallAgent createAgent() {
        String userToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjEwNiIsIng1dCI6Im9QMWFxQnlfR3hZU3pSaXhuQ25zdE5PU2p2cyIsInR5cCI6IkpXVCJ9.eyJza3lwZWlkIjoiYWNzOmQ2MWM2MDcwLTY3YjItNDAzMC1iZmMxLTUyYjY4ZDg0N2Q0ZF8wMDAwMDAxMi1jNjQ0LTIzODYtMTY3Yy1jOTNhMGQwMDEyYTkiLCJzY3AiOjE3OTIsImNzaSI6IjE2NTg1MDEzNjUiLCJleHAiOjE2NTg1ODc3NjUsImFjc1Njb3BlIjoidm9pcCIsInJlc291cmNlSWQiOiJkNjFjNjA3MC02N2IyLTQwMzAtYmZjMS01MmI2OGQ4NDdkNGQiLCJpYXQiOjE2NTg1MDEzNjV9.tIHQeE67V32bUopcZPvg9bTHpbzRgtSfS2H15phmgS1lpxwcnbnpMJOHA8oS51xWaJUK5W-AgZIirQ92QUsYSJKwFLirU7wEidqw0KZQN_3-WprPq7TK3j8zsRJIdkUHMMH40k4aR4-DDDLAdbng1W-3S8St8_gP6AO-HeNBwPC6ACOo3KNnZ4hScXxiBdCxl6cEgjuXPg1oYG4fM8woxkb1dwivLdS7Gy7X5IDt3adEepDtAALEkm6ZumMAlfwtUVrE0ZtGBv2EDTq7333FmUxCl-I-i3470Gq6SPk1QcwWmURigul6XG20avGZTjva46t8MI0n6wZUwGPysR9Cig";

        try {
            CommunicationTokenCredential credential = new CommunicationTokenCredential(userToken);
            CallAgent callAgent = new CallClient().createCallAgent(getApplicationContext(), credential).get();
            return callAgent;
        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Failed to create call agent.", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private void startCall(CallAgent callAgent) {
        EditText calleeIdView = findViewById(R.id.callee_id);
        String calleeId = calleeIdView.getText().toString();

        StartCallOptions options = new StartCallOptions();

        callAgent.startCall(
                getApplicationContext(),
                new CommunicationUserIdentifier[] {new CommunicationUserIdentifier(calleeId)},
                options);
    }
}