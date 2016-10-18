package com.example.sanakazi.animationexample.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sanakazi.animationexample.R;

/**
 * Created by sanakazi on 10/17/2016.
 */
public class SharedElementFragment2 extends Fragment {
    View v;

    public static SharedElementFragment2 newInstance(String sample) {
        Bundle args = new Bundle();
        args.putString("key", "value");
        SharedElementFragment2 fragment = new SharedElementFragment2();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment2_shared_element,container,false);
        return v;
    }
}
