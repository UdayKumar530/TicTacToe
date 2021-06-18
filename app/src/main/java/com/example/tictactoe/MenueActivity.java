package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menue);
    }


    public void startGame_SinglePlayer(View view){
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(intent);
    }

    public void EndGame(View view) {
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
    }

    public void ShowAboutNote(View view) {
        Intent intent = new Intent(getApplicationContext(), MenueActivity.class);
        startActivity(intent);
    }

    public void StartGameOnline(View view) {
        Intent intent = new Intent(getApplicationContext(), OnlineLoginActivity.class);
        startActivity(intent);
    }
}