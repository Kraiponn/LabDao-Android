package com.ksn.kraiponn.labdao.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksn.kraiponn.labdao.R;

public class TemplateFragment extends Fragment {

    public TemplateFragment() {

    }

    public static TemplateFragment newInstance() {
        TemplateFragment fm = new TemplateFragment();
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
                R.layout.fragment_main,
                container, false
        );

        initInstance();
        return rootView;
    }

    private void initInstance() {
    }

}
