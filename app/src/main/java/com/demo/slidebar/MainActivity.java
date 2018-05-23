package com.demo.slidebar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView hitText = findViewById(R.id.hitText);


        SlideBar slideBar = findViewById(R.id.slideBar);
        slideBar.setOnTouchChangeListener(new SlideBar.OnTouchChangeListener() {
            @Override
            public void onLetterTouched(String touchLetter) {
                hitText.setVisibility(View.VISIBLE);
                hitText.setText(touchLetter);
            }

            @Override
            public void onLetterTouchedCancel() {
                hitText.setVisibility(View.GONE);
            }
        });

    }
}
