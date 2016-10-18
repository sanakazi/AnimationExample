package com.example.sanakazi.animationexample.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sanakazi.animationexample.R;
import com.example.sanakazi.animationexample.animationClasses.TransitionHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    LinearLayout l1,l2,l3,l4;
    ImageView img_blueball,img_whiteBall;
    TextView txt_2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        setupWindowAnimations();
        setupLayout();

    }



    private void setupWindowAnimations() {
        // Re-enter transition is executed when returning to this activity
        Slide slideTransition = new Slide();
        slideTransition.setSlideEdge(Gravity.LEFT);
        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        getWindow().setReenterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);
    }


    private void setupLayout() {
        l1=(LinearLayout)findViewById(R.id.l1);
        l2=(LinearLayout)findViewById(R.id.l2);
        l3=(LinearLayout)findViewById(R.id.l3);
        l4=(LinearLayout)findViewById(R.id.l4);
        img_blueball = (ImageView)findViewById(R.id.img_blueball);
        img_whiteBall = (ImageView)findViewById(R.id.img_whiteBall);
        txt_2= (TextView)findViewById(R.id.txt_2);
        l1.setOnClickListener(MainActivity.this);
        l2.setOnClickListener(MainActivity.this);
        l3.setOnClickListener(MainActivity.this);
        l4.setOnClickListener(MainActivity.this);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.l1 :
                Intent i1 = new Intent(this, TransitionActivity.class);
                startActivity(i1, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.l2 :
                Intent i2 = new Intent(this, SharedElementsActivity.class);
                Pair<View, String> p2 = Pair.create((View)txt_2, "animationName");
                Pair<View, String> p3 = Pair.create((View)img_blueball, "animationBlueBall");
                ActivityOptionsCompat options2 = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(this, p3,p2);
                startActivity(i2, options2.toBundle());
                break;
            case R.id.l3:
                Intent i3 = new Intent(this, FragmentsAnimation.class);
                Pair<View, String> p4= Pair.create((View)img_whiteBall, "animationWhiteBall");
                ActivityOptionsCompat options3 = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(this, p4);
                startActivity(i3, options3.toBundle());
                break;
            case R.id.l4:
                break;
            default:
                break;
        }

    }


}

