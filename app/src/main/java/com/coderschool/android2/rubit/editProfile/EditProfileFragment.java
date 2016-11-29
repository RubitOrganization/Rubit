package com.coderschool.android2.rubit.editProfile;

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

public class EditProfileFragment extends Fragment {
//    public static EditProfileFragment newInstance() {
//
//        Bundle args = new Bundle();
//
//        EditProfileFragment fragment = new EditProfileFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_profile_fragment, container, false);
    }
}
