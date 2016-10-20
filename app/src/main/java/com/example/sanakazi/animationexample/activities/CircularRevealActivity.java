package com.example.sanakazi.animationexample.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.transition.TransitionValues;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.sanakazi.animationexample.R;

public class CircularRevealActivity extends BaseDetailActivity implements  View.OnTouchListener {
    private Interpolator interpolator;
    private RelativeLayout  rl1;
    private RelativeLayout main_Layout;
    private ImageView img_Blackball,img_red,img_white,img_blue;
    private Toolbar toolbar;
    private static final int DELAY = 100;
    private Transition.TransitionListener mEnterTransitionListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular_reveal);
        setupToolbar();
        setupLayout();
        setupWindowAnimations();

    }

    private void setupWindowAnimations() {
        interpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in);
       setupEnterAnimations();
        setupExitAnimations();

    }

    private void setupLayout() {
        rl1 = (RelativeLayout)findViewById(R.id.rl1);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        main_Layout = (RelativeLayout) findViewById(R.id.main_Layout);
        img_Blackball = (ImageView)findViewById(R.id.img_blackBall);
        img_red = (ImageView)findViewById(R.id.img_red);
        img_white = (ImageView)findViewById(R.id.img_white);
        img_blue = (ImageView)findViewById(R.id.img_blue);

        img_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revealWhite();
            }
        });
        img_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revealBlue();
            }
        });
        img_red.setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            if (view.getId() == R.id.img_red) {
                revealRed(motionEvent.getRawX(), motionEvent.getRawY());
            }
        }
        return false;
    }

    private void setupEnterAnimations() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                // Removing listener here is very important because shared element transition is executed again backwards on exit. If we don't remove the listener this code will be triggered again.
                transition.removeListener(this);
               hideTarget();
                 animateRevealShow(toolbar);
                animateButtonsIn();
            }

            @Override
            public void onTransitionCancel(Transition transition) {
            }

            @Override
            public void onTransitionPause(Transition transition) {
            }

            @Override
            public void onTransitionResume(Transition transition) {
            }
        });
    }
    private void setupExitAnimations() {
        Fade returnTransition = new Fade();
        getWindow().setReturnTransition(returnTransition);
        returnTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        returnTransition.setStartDelay(getResources().getInteger(R.integer.anim_duration_medium));
        returnTransition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                transition.removeListener(this);
                animateButtonsOut();
                animateRevealHide(main_Layout);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
            }

            @Override
            public void onTransitionCancel(Transition transition) {
            }

            @Override
            public void onTransitionPause(Transition transition) {
            }

            @Override
            public void onTransitionResume(Transition transition) {
            }
        });
    }

    private void hideTarget() {
        img_Blackball.setVisibility(View.GONE);
    }
    private void animateRevealShow(View viewRoot) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
        viewRoot.setVisibility(View.VISIBLE);
        anim.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        anim.setInterpolator(new AccelerateInterpolator());
        anim.start();
    }
    private void animateButtonsIn() {
        for (int i = 0; i < main_Layout.getChildCount(); i++) {
            View child = main_Layout.getChildAt(i);
            child.animate()
                    .setStartDelay(100 + i * DELAY)
                    .setInterpolator(interpolator)
                    .alpha(1)
                    .scaleX(1)
                    .scaleY(1);
        }
    }

    private void animateButtonsOut() {
        for (int i = 0; i < main_Layout.getChildCount(); i++) {
            View child = main_Layout.getChildAt(i);
            child.animate()
                    .setStartDelay(i)
                    .setInterpolator(interpolator)
                    .alpha(0)
                    .scaleX(0f)
                    .scaleY(0f);
        }
    }
    private void animateRevealHide(final View viewRoot) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int initialRadius = viewRoot.getWidth();

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, initialRadius, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                viewRoot.setVisibility(View.INVISIBLE);
            }
        });
        anim.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        anim.start();
    }

    private void animateRevealColor(ViewGroup viewRoot, @ColorRes int color) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        animateRevealColorFromCoordinates(viewRoot, color, cx, cy);
    }

    private Animator animateRevealColorFromCoordinates(ViewGroup viewRoot, @ColorRes int color, int x, int y) {
        float finalRadius = (float) Math.hypot(viewRoot.getWidth(), viewRoot.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, x, y, 0, finalRadius);
        viewRoot.setBackgroundColor(ContextCompat.getColor(this, color));
        anim.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
        return anim;
    }

    private void revealRed(float x, float y) {
        animateRevealColorFromCoordinates(main_Layout, R.color.colorRed, (int) x, (int) y);

    }

    private void revealWhite() {
        animateButtonsOut();
        Animator anim = animateRevealColorFromCoordinates(main_Layout, R.color.colorWhite, main_Layout.getWidth() / 2, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animateButtonsIn();
            }
        });
    }

    private void revealBlue() {
        animateRevealColor(main_Layout, R.color.colorBlue);
    }
}
