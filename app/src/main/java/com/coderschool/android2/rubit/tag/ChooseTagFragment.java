/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.tag;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.coderschool.android2.rubit.R;
import com.coderschool.android2.rubit.constants.DatabaseConstants;
import com.coderschool.android2.rubit.constants.IntentConstants;
import com.coderschool.android2.rubit.face.FaceActivity;
import com.coderschool.android2.rubit.login.LoginActivity;
import com.coderschool.android2.rubit.models.TagItems;
import com.coderschool.android2.rubit.utils.FirebaseUtils;
import com.coderschool.android2.rubit.utils.SpacesItemDecoration;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vinay on 12/11/16.
 */
public class ChooseTagFragment extends Fragment
        implements ChooseTagContract.View, View.OnClickListener, ChooseTagAdapter.TagScreenAdapterCallback {

    public static final String TAG = ChooseTagFragment.class.getSimpleName();
    public static final String[] tagTitles = new String[]{"#Android", "#iOS", "#Ruby", "#Node js", "#Python", "#Others"};
    public static final Integer[] tagImages = {
            R.drawable.android,
            R.drawable.ios,
            R.drawable.ruby,
            R.drawable.node,
            R.drawable.python_icon,
            R.drawable.img_others
    };
    @BindView(R.id.recyclerViewHashTagGridView)
    RecyclerView recyclerViewHashTagGridView;
    @BindView(R.id.skipBtn)
    AppCompatButton skipBtn;
    @BindView(R.id.finishBtn)
    AppCompatButton finishBtn;
    private ChooseTagContract.Presenter mPresenter;
    private List<TagItems> mTagItems;
    private ChooseTagAdapter mChooseTagAdapter;
    private List<TagItems> selectedTags;


    public static ChooseTagFragment newInstance() {

        Bundle args = new Bundle();

        ChooseTagFragment fragment = new ChooseTagFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTagItems = new ArrayList<>();
        for (int i = 0; i < tagTitles.length; i++) {
            TagItems tagItems = new TagItems(tagTitles[i], tagImages[i]);
            mTagItems.add(tagItems);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_choose_tag, container, false);
        if (view != null) {
            ButterKnife.bind(this, view);
            mChooseTagAdapter = new ChooseTagAdapter(mTagItems, this);
            recyclerViewHashTagGridView.setAdapter(mChooseTagAdapter);
            int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
            recyclerViewHashTagGridView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
            recyclerViewHashTagGridView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            skipBtn.setOnClickListener(this);
            finishBtn.setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void setPresenter(ChooseTagContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.skipBtn:
                Intent skipIntent = new Intent(getActivity(), FaceActivity.class);
                skipIntent.putExtra(IntentConstants.IS_SKIP, true);
                getActivity().startActivity(skipIntent);
                break;
            case R.id.finishBtn:
                updateUserTags(selectedTags);
                break;
        }
    }

    private void updateUserTags(List<TagItems> selectedTags) {
        if (FirebaseUtils.getCurrentUserRef() != null) {
            final String currentUserId = FirebaseUtils.getCurrentUserId();
            if (currentUserId != null) {
                ArrayList<String> ar = new ArrayList<>();
                if (selectedTags == null) {
                    Toast.makeText(getActivity(), "please choose at-least 1 tags or select skip button", Toast.LENGTH_LONG).show();
                } else {
                    for (TagItems selectedTag : selectedTags) {
                        TagItems tag = selectedTag;
                        String tagName = tag.getTagName();
                        String result = tagName.replaceAll("#", "");
                        ar.add(result);
                    }
                    final DatabaseReference rubitUser = FirebaseUtils.getRubitUser().child(currentUserId).child("tags");
                    Map<String, Object> objectMap = new HashMap<>();
                    if (ar.contains(DatabaseConstants.TAG_ANDROID))
                        objectMap.put(DatabaseConstants.TAG_ANDROID, true);
                    if (ar.contains(DatabaseConstants.TAG_OTHERS))
                        objectMap.put(DatabaseConstants.TAG_OTHERS, true);
                    else
                        objectMap.put(DatabaseConstants.TAG_OTHERS, false);
                    if (ar.contains(DatabaseConstants.TAG_PYTHON))
                        objectMap.put(DatabaseConstants.TAG_PYTHON, true);
                    if (ar.contains(DatabaseConstants.TAG_RUBY))
                        objectMap.put(DatabaseConstants.TAG_RUBY, true);
                    if (ar.contains(DatabaseConstants.TAG_IOS))
                        objectMap.put(DatabaseConstants.TAG_IOS, true);
                    if (ar.contains(DatabaseConstants.TAG_NODEJS))
                        objectMap.put(DatabaseConstants.TAG_NODEJS, true);
                    rubitUser.updateChildren(objectMap);
                    Intent finishIntent = new Intent(getActivity(), FaceActivity.class);
                    finishIntent.putExtra(IntentConstants.IS_SKIP, false);
                    finishIntent.putParcelableArrayListExtra(IntentConstants.LIST_OF_CHOSEN_TAGS, (ArrayList<? extends Parcelable>) selectedTags);
                    getActivity().startActivity(finishIntent);
                }
            }
        } else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onTagSelectedCallback(List<TagItems> selectedTags) {
        this.selectedTags = selectedTags;
    }
}
