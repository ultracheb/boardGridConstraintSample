package com.example.gridexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.Guideline;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import static androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.END;
import static androidx.constraintlayout.widget.ConstraintSet.BOTTOM;
import static androidx.constraintlayout.widget.ConstraintSet.START;
import static androidx.constraintlayout.widget.ConstraintSet.TOP;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    private Guideline[] horizontalGuidelines;
    private Guideline[] verticalGuidelines;
    private TextView playerView;
    private int boardSize = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constraintLayout = findViewById(R.id.layout);
        int guidelinesCount = boardSize + 1;
        horizontalGuidelines = new Guideline[guidelinesCount];
        verticalGuidelines = new Guideline[guidelinesCount];

        for (int i = 0; i < guidelinesCount; i++) {
            horizontalGuidelines[i] = constraintLayout.findViewWithTag("horizontalGuideline" + i);
            verticalGuidelines[i] = constraintLayout.findViewWithTag("verticalGuideline" + i);
        }
        initializeGameBoard();
        initializePlayerView();
        initializeMoveButton();
    }

    private void initializeGameBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                TextView textView = new TextView(this);
                textView.setId(View.generateViewId());
                textView.setText(String.valueOf(i * boardSize + j));
                textView.setGravity(Gravity.CENTER);

                GradientDrawable border = new GradientDrawable();
                border.setColor(0xffEB5D0D);
                border.setStroke(1, 0x66000000);
                textView.setBackground(border);

                Constraints.LayoutParams params = new Constraints.LayoutParams(0, 0);
                constraintLayout.addView(textView, params);

                ConstraintSet set = new ConstraintSet();
                set.clone(constraintLayout);
                set.connect(textView.getId(), TOP, horizontalGuidelines[i].getId(), TOP);
                set.connect(textView.getId(), BOTTOM, horizontalGuidelines[i + 1].getId(), BOTTOM);
                set.connect(textView.getId(), START, verticalGuidelines[j].getId(), START);
                set.connect(textView.getId(), END, verticalGuidelines[j + 1].getId(), END);
                set.applyTo(constraintLayout);
            }
        }
    }

    private void initializePlayerView() {
        playerView = new TextView(this);
        playerView.setId(View.generateViewId());
        playerView.setText("Player");
        playerView.setGravity(Gravity.CENTER);
        playerView.setBackgroundColor(Color.parseColor("#FFD500"));
        Constraints.LayoutParams params = new Constraints.LayoutParams(0, 0);
        constraintLayout.addView(playerView, params);

        ConstraintSet set = new ConstraintSet();
        set.clone(constraintLayout);
        set.connect(playerView.getId(), TOP, horizontalGuidelines[2].getId(), TOP);
        set.connect(playerView.getId(), BOTTOM, horizontalGuidelines[2 + 1].getId(), BOTTOM);
        set.connect(playerView.getId(), START, verticalGuidelines[2].getId(), START);
        set.connect(playerView.getId(), END, verticalGuidelines[2 + 1].getId(), END);
        set.applyTo(constraintLayout);
    }

    private void initializeMoveButton() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConstraintSet set = new ConstraintSet();
                set.clone(constraintLayout);
                set.connect(playerView.getId(), TOP, horizontalGuidelines[4].getId(), TOP);
                set.connect(playerView.getId(), BOTTOM, horizontalGuidelines[4 + 1].getId(), BOTTOM);
                set.connect(playerView.getId(), START, verticalGuidelines[2].getId(), START);
                set.connect(playerView.getId(), END, verticalGuidelines[2 + 1].getId(), END);
                TransitionManager.beginDelayedTransition(constraintLayout);
                set.applyTo(constraintLayout);
            }
        });
    }
}