package com.yalinwang.birdhunter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startGame(View view) {
        BirdHunterView gameView = (BirdHunterView)findViewById(R.id.game_view);
        gameView.startGame();
    }

    public void stopGame(View view) {
        BirdHunterView gameView = (BirdHunterView)findViewById(R.id.game_view);
        gameView.stopAnimation();
    }

}
