package com.example.movieapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class Inicio extends AppCompatActivity {
    LottieAnimationView animationView;
    TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
        text=findViewById(R.id.textView17);
        animationView=findViewById(R.id.animationView);
        animationView.setSpeed(1f);
        startCheckAnimationLogo();



    }

    private void startCheckAnimationLogo(){
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(4000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator animation){
                animationView.setProgress((Float)animation.getAnimatedValue());

            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                AlphaAnimation fadein =new AlphaAnimation(0.0f,1.0f);
                fadein.setDuration(1000);
                //fadein.setStartOffset();
                fadein.setFillAfter(true);

                text.setAnimation(fadein);
                text.setText("MovieApp");
                fadein.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(Inicio.this,MainActivity.class);
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
        if(animationView.getProgress() == 0f){
            animator.start();
        }else{
            animationView.setProgress(0f);
        }
    }
}
