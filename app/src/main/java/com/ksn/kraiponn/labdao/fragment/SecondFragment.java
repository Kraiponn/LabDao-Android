package com.ksn.kraiponn.labdao.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksn.kraiponn.labdao.R;

public class SecondFragment extends Fragment {

    public SecondFragment() {

    }

    public static SecondFragment newInstance() {
        SecondFragment fm = new SecondFragment();
        Bundle args = new Bundle();
        fm.setArguments(args);
        return fm;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_second,
                container, false
        );

        initInstance();
        return rootView;
    }

    private void initInstance() {
    }

}
