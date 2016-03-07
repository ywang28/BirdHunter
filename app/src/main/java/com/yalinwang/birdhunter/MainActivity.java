package com.yalinwang.birdhunter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private BirdHunterView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameView = (BirdHunterView)findViewById(R.id.game_view);
    }

    public void startGame(View view) {
        gameView.startGame();
    }

    public void stopGame(View view) {
        gameView.stopAnimation();
    }

    public void upgradeArrow(View view) {
        gameView.upgradeArrow();
    }
}
