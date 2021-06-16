package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tictactoe.databinding.ActivityGameBinding;
import com.google.firebase.auth.FirebaseAuth;

public class GameActivity extends AppCompatActivity  implements View.OnClickListener {
    FirebaseAuth mAuth;
    ActivityGameBinding binding;
    TextView headerText;

    int PLAYER_O = 0;
    int PLAYER_X = 1;

    int activePlayer = PLAYER_O;

    int[] filledPos = {-1, -1, -1, -1, -1, -1, -1, -1, -1};

    boolean isGameActive = true;
    public static int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth=FirebaseAuth.getInstance();
        headerText = findViewById(R.id.header_text1);
        headerText.setText("O turn");


        binding.btn0.setOnClickListener(this);
        binding.btn1.setOnClickListener(this);
        binding.btn2.setOnClickListener(this);
        binding.btn3.setOnClickListener(this);
        binding.btn4.setOnClickListener(this);
        binding.btn5.setOnClickListener(this);
        binding.btn6.setOnClickListener(this);
        binding.btn7.setOnClickListener(this);
        binding.btn8.setOnClickListener(this);
//        binding.reset.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public void onClick(View v) {
//                restartGame();
//            }
//        });


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        // logic for button press
        Button clickedBtn = findViewById(v.getId());
        int clickedTag = Integer.parseInt(v.getTag().toString());


        if (!isGameActive){
            restartGame();
        }
        if (filledPos[clickedTag] == -1) {
            // increase the counter
            // after every tap
            counter++;
            Log.d("TAG", "onClick: "+counter);

            filledPos[clickedTag] = activePlayer;

            if (activePlayer == PLAYER_O) {
                clickedBtn.setText("O");
                activePlayer = PLAYER_X;
                headerText.setText("X turn");
            } else {
                clickedBtn.setText("X");
                activePlayer = PLAYER_O;
                headerText.setText("O turn");
            }

            checkForWin();
        }
    }
    private void checkForWin() {
        //we will check who is winner and show
        int[][] winningPos = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
        int flag = 0;
        for (int i = 0; i < 8; i++) {
            int val0 = winningPos[i][0];
            int val1 = winningPos[i][1];
            int val2 = winningPos[i][2];

            if (filledPos[val0] == filledPos[val1] && filledPos[val1] == filledPos[val2]) {
                if (filledPos[val0] != -1) {
                    //winner declare
                    flag = 1;
                    isGameActive = false;

                    if (filledPos[val0] == PLAYER_O){
                        showDialog("Congratulations !!! O is winner");
                        counter=0;
                    }
                    else
                    {
                        showDialog("Congratulations !!! X is winner");
                        counter=0;
                    }

                }
            }
        }
        if (counter == 9 && flag == 0) {
            showDialog1("Match draw");
            counter=0;
        }
    }

    private void showDialog1(String match_draw) {

        AlertDialog.Builder alertadd = new AlertDialog.Builder(GameActivity.this);
        LayoutInflater factory = LayoutInflater.from(GameActivity.this);
        final View view = factory.inflate(R.layout.sample1, null);
        alertadd.setTitle(match_draw);
        alertadd.setView(view);
        alertadd.setNeutralButton("Restart GAme", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(DialogInterface dlg, int sumthin) {
                restartGame();
            }
        });

        alertadd.show();
    }

    private void showDialog(String winnerText) {
        AlertDialog.Builder alertadd = new AlertDialog.Builder(GameActivity.this);
        LayoutInflater factory = LayoutInflater.from(GameActivity.this);
        final View view = factory.inflate(R.layout.sample, null);
        alertadd.setTitle(winnerText);
        alertadd.setView(view);
        alertadd.setNeutralButton("Restart GAme", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(DialogInterface dlg, int sumthin) {
                restartGame();
            }
        });

        alertadd.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void restartGame() {
        activePlayer = PLAYER_O;
        headerText.setText("O turn");
        filledPos = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1};
        binding.btn0.setText("");
        binding.btn1.setText("");
        binding.btn2.setText("");
        binding.btn3.setText("");
        binding.btn4.setText("");
        binding.btn5.setText("");
        binding.btn6.setText("");
        binding.btn7.setText("");
        binding.btn8.setText("");

        isGameActive = true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menue,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                break;
            case R.id.logout:
                mAuth.signOut();
                Intent intent=new Intent(GameActivity.this,Login.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}