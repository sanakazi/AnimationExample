package com.example.sanakazi.animationexample.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.sanakazi.animationexample.R;
import com.example.sanakazi.animationexample.fragments.SharedElementFragment1;

public class SharedElementsActivity extends BaseDetailActivity implements View.OnClickListener {
    Button btn_1,btn_2;
    ImageView img_blueball;
    private int savedWidth;
    private ViewGroup viewRoot;
    private boolean sizeChanged,positionChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_elements);
        setupToolbar();
        setupWindowAnimations();
        setupLayout();
    }

    private void setupWindowAnimations() {
        // We are not interested in defining a new Enter Transition. Instead we change default transition duration
        getWindow().getEnterTransition().setDuration(getResources().getInteger(R.integer.anim_duration_long));
    }

    private void setupLayout() {

        Slide slideTransition = new Slide(Gravity.LEFT);
        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        viewRoot = (ViewGroup) findViewById(R.id.l1);
        img_blueball = (ImageView)findViewById(R.id.img_blueball);
        btn_1 = (Button)findViewById(R.id.btn_1);
        btn_2 = (Button)findViewById(R.id.btn_2);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btn_1: changeLayout();
                break;
            case R.id.btn_2 :changePosition();
                break;
        }
    }


    private void changeLayout() {
        TransitionManager.beginDelayedTransition(viewRoot);

        ViewGroup.LayoutParams params = img_blueball.getLayoutParams();
        if (sizeChanged) {
            params.width = savedWidth;
        } else {
            savedWidth = params.width;
            params.width = 200;
        }
        sizeChanged = !sizeChanged;
        img_blueball.setLayoutParams(params);
    }

    private void changePosition() {
        TransitionManager.beginDelayedTransition(viewRoot);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) img_blueball.getLayoutParams();
        if (positionChanged) {
            lp.gravity = Gravity.CENTER;
        } else {
            lp.gravity = Gravity.LEFT;
        }
        positionChanged = !positionChanged;
        img_blueball.setLayoutParams(lp);
    }
}
