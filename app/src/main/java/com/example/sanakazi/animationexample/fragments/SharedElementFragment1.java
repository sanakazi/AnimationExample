package com.example.sanakazi.animationexample.fragments;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sanakazi.animationexample.R;

/**
 * Created by sanakazi on 10/17/2016.
 */
public class SharedElementFragment1 extends Fragment{
    Button btn1;
    ImageView img_whiteBall;
    TextView txt1;
    View v;


    public static SharedElementFragment1 newInstance(String  sample) {

        Bundle args = new Bundle();
        args.putString("key", "value");
        SharedElementFragment1 fragment = new SharedElementFragment1();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment1_shared_element, container, false);
        setupLayout();
        return v;
    }

    private void  setupLayout(){
        btn1 = (Button)v.findViewById(R.id.btn1);
        img_whiteBall = (ImageView)v.findViewById(R.id.img_whiteBall) ;
        txt1 = (TextView)v.findViewById(R.id.txt1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addNextFragment("Fragment 1 ",img_whiteBall, false);
            }
        });

    }

    private void addNextFragment(String value,ImageView img1, boolean overlap) {
        SharedElementFragment2 sharedElementFragment2 = SharedElementFragment2.newInstance(value);

        Slide slideTransition = new Slide(Gravity.RIGHT);
        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));

        ChangeBounds changeBoundsTransition = new ChangeBounds();
        changeBoundsTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));

        sharedElementFragment2.setEnterTransition(slideTransition);
        sharedElementFragment2.setAllowEnterTransitionOverlap(overlap);
        sharedElementFragment2.setAllowReturnTransitionOverlap(overlap);
        sharedElementFragment2.setSharedElementEnterTransition(changeBoundsTransition);

        getFragmentManager().beginTransaction()
                .replace(R.id.sample2_content, sharedElementFragment2)
                .addToBackStack(null)
                .addSharedElement(img1, "animationWhiteBall")
                .commit();

    }

}
