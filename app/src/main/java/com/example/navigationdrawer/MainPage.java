package com.example.navigationdrawer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainPage extends AppCompatActivity {
    Button btnSplash;
    TextView tvSplash;
    ImageView imageView;
    Animation fromtopbottom,smalltobig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        fromtopbottom= AnimationUtils.loadAnimation(this,R.anim.fromtopbottom);
        smalltobig= AnimationUtils.loadAnimation(this,R.anim.smalltobig);
//        Typeface MMedium = Typeface.createFromAsset(getAssets(),"font/mlight.ttf");
//        Typeface MLight = Typeface.createFromAsset(getAssets(),"font/mlight.ttf");
//        Typeface MRegular = Typeface.createFromAsset(getAssets(),"font/mlight.ttf");

        tvSplash= findViewById(R.id.tvSplash);
        btnSplash= findViewById(R.id.btnSplash);
        imageView = findViewById(R.id.imageView);

//        tvSplash.setTypeface(MRegular);
//        btnSplash.setTypeface(MMedium);

        tvSplash.startAnimation(fromtopbottom);
        btnSplash.startAnimation(fromtopbottom);
        imageView.startAnimation(smalltobig);

        btnSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain();
            }
        });
    }

    public void openMain(){
        Intent intent = new Intent(this,NewLogin.class);
        startActivity(intent);

    }
}