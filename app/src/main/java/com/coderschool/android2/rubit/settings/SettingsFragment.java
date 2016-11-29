package com.coderschool.android2.rubit.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coderschool.android2.rubit.R;

/**
 * Created by vinay on 27/11/16.
 */

public class SettingsFragment extends Fragment {

//    public static SettingsFragment newInstance() {
//
//        Bundle args = new Bundle();
//
//        SettingsFragment fragment = new SettingsFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, container, false);
    }
}
