/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.face;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coderschool.android2.rubit.R;
import com.coderschool.android2.rubit.connectionDialog.ConnectionDialogListener;
import com.coderschool.android2.rubit.constants.DatabaseConstants;
import com.coderschool.android2.rubit.constants.IntentConstants;
import com.coderschool.android2.rubit.detailsTask.DetailsTaskActivity;
import com.coderschool.android2.rubit.login.LoginActivity;
import com.coderschool.android2.rubit.models.RequestModel;
import com.coderschool.android2.rubit.models.RequestSimpleModel;
import com.coderschool.android2.rubit.models.TagItems;
import com.coderschool.android2.rubit.models.UserModel;
import com.coderschool.android2.rubit.portfolio.PortfolioActivity;
import com.coderschool.android2.rubit.utils.ConnectionUtils;
import com.coderschool.android2.rubit.utils.FirebaseUtils;
import com.coderschool.android2.rubit.utils.GoogleApiClientUtils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

/**
 * {@link FaceFragment}
 *
 * @author TienNguyen
 */
public class FaceFragment extends Fragment
        implements FaceContact.View, ConnectionDialogListener, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = FaceFragment.class.getSimpleName();

    @BindView(R.id.rtLoading)
    RelativeLayout rtLoading;
    @BindView(R.id.rvRequests)
    RecyclerView rvRequests;
    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.imgGo)
    ImageView imgGo;
    @BindView(R.id.txtDetails)
    TextView mTxtDetails;
    @BindView(R.id.edtQuestBar)
    EditText edtQuestBar;

    /**
     * Authenticate
     */
    private FaceContact.Presenter mPresenter;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mFirebaseAuth;
    private GoogleApiClient mGoogleApiClient;

    /**
     * runnable
     */
    private Handler handler = new Handler();
    /**
     * Requests list
     */
    private RequestAdapter requestAdapter;
    private List<RequestSimpleModel> requests = new ArrayList<>();
    private int numberCurrentRow = 0;
    private Runnable runnable = this::startRunnableForPop;

    /**
     * Constructor
     */
    public FaceFragment() {
    }

    /**
     * newInstance
     *
     * @return {@link FaceFragment}
     */
    public static FaceFragment newInstance() {
        final Bundle args = new Bundle();

        final FaceFragment fragment = new FaceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ConnectionUtils.verifyConnectionDialogForFragment(getActivity(), this, getActivity().getSupportFragmentManager())) {
            setUpGoogleApiClient();
            setUpFirebase();
            verifyDoesUserExists();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ConnectionUtils.verifyConnectionDialogForFragment(getActivity(), this, getActivity().getSupportFragmentManager())) {
            mPresenter.start();
            runnable.run();
        }
    }

    /**
     * stopRunnableForPop
     */
    public void stopRunnableForPop() {
        handler.removeCallbacks(runnable);
    }

    /**
     * startRunnableForPop
     * TODO: Temperate solution
     */
    public void startRunnableForPop() {
        handler.postDelayed(runnable, IntentConstants.POP_REQUEST_TIME);

        String userId = FirebaseUtils.getCurrentUserId();
        if (userId != null) {
            // Query Request
            FirebaseUtils.getRequests()
                    .orderByChild(userId)
                    .equalTo(null)
                    /*.orderByChild(DatabaseConstants.COMPLETED)
                    .equalTo(false)
                    .orderByChild(DatabaseConstants.CONNECTED)
                    .equalTo(false)*/ //TODO: Firebase doesn't allow multiple conditions
                    .limitToFirst(7)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                final RequestModel requestModel = snapshot.getValue(RequestModel.class);

                                if (numberCurrentRow == 7) {
                                    imgLogo.setVisibility(View.VISIBLE);
                                    numberCurrentRow = 0;
                                    requests.clear();
                                    requestAdapter.notifyDataSetChanged();
                                }

                                // Query User
                                FirebaseUtils.getRubitUser()
                                        .child(requestModel.getUid())
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                final UserModel userMode = dataSnapshot.getValue(UserModel.class);
                                                showRequestsPop(requestModel, userMode);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                System.out.println("[FACE_ACTIVITY] The read failed in user: " + databaseError.getCode());
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("[FACE_ACTIVITY] The read failed in requests: " + databaseError.getCode());
                        }
                    });
        } else {
            Log.e(TAG, "[FACE_ACTIVITY] Current user Id is null, we need to handle this case");
        }
    }

    /**
     * verify current user
     */
    private void verifyDoesUserExists() {
        if (FirebaseUtils.getCurrentUserRef() == null) {
            moveBackToLoginActivity();
        }
    }

    /**
     * set up google Api client
     */
    private void setUpGoogleApiClient() {
        mGoogleApiClient = GoogleApiClientUtils.authGoogleApiClient(getActivity(), getActivity(), this, null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_face, container, false);
        if (null != view) {
            ButterKnife.bind(this, view);
        }
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signout:
                // we need to make name, photourl and email to null on logout
                mFirebaseAuth.signOut();
                mFirebaseDatabase = null;
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                moveBackToLoginActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void moveBackToLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "[FACE_ACTIVITY] onConnectionFailed:-" + connectionResult);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    @Override
    public void setPresenter(FaceContact.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * showRequestsPop
     *
     * @param requestModel {@link RequestModel}
     * @param userMode     {@link UserModel}
     */
    public void showRequestsPop(final RequestModel requestModel, final UserModel userMode) {
        final RequestSimpleModel requestSimpleModel = new RequestSimpleModel();
        requestSimpleModel.setRequestModel(requestModel);
        requestSimpleModel.setUserModel(userMode);

        int first = requests.size();
        requests.add(requestSimpleModel);
        int last = requests.size();
        requestAdapter.notifyItemRangeInserted(first, last);
        numberCurrentRow++;

        if (first > 0) {
            imgLogo.setVisibility(View.INVISIBLE);
        } else {
            imgLogo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setUpRecyclerView() {
        rvRequests.setItemAnimator(new SlideInLeftAnimator());
        rvRequests.getItemAnimator().setAddDuration(500);
        rvRequests.getItemAnimator().setRemoveDuration(500);
        rvRequests.getItemAnimator().setMoveDuration(500);
        rvRequests.getItemAnimator().setChangeDuration(500);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvRequests.setLayoutManager(layoutManager);

        requestAdapter = new RequestAdapter(getContext(), requests, handler, runnable);
        rvRequests.setAdapter(requestAdapter);

    }

    @Override
    public void showImagePositive() {
        imgLogo.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.main_screen_logo));
    }

    @Override
    public void showImageNegative() {
        // TODO: Later
    }

    @Override
    public void showDetails() {
        final String detailsText = mTxtDetails.getText().toString();
        final SpannableString content = new SpannableString(detailsText);
        content.setSpan(new UnderlineSpan(), 0, detailsText.length(), 0);
        mTxtDetails.setText(content);
    }

    @Override
    public void btnGoOnclick() {
        imgGo.setOnClickListener(view -> {
            if (0 >= edtQuestBar.getText().length()) {
                Toast.makeText(getContext(), "Quest cannot be empty!", Toast.LENGTH_SHORT).show();
                edtQuestBar.requestFocus();
                return;
            }

            rtLoading.setVisibility(View.VISIBLE);

            stopRunnableForPop();

            String currentUserId = FirebaseUtils.getCurrentUserId();
            if (currentUserId == null) {
                return;
            }

            // TODO: Temper solution
            Map<String, Boolean> tags = new HashMap<>();
            tags.put(DatabaseConstants.TAG_OTHERS, true);

            final RequestModel newRequest = new RequestModel();
            newRequest.setSubject(edtQuestBar.getText().toString());
            newRequest.setTags(tags);
            newRequest.setUid(currentUserId);
            newRequest.setConnected(false);
            newRequest.setCompleted(false);

            // Create new record in Request table
            DatabaseReference newRequestReference = FirebaseUtils.getRequests().push();
            newRequestReference.setValue(newRequest);
            newRequest.setRequestId(newRequestReference.getKey());

            // Update that record & current user record too
            FirebaseUtils.getRequests()
                    .child(newRequest.getRequestId())
                    .setValue(newRequest)
                    .addOnCompleteListener(task -> {

                        Map<String, Object> requests1 = new HashMap<>();
                        requests1.put(newRequest.getRequestId(), true);

                        FirebaseUtils.getRubitUser()
                                .child(currentUserId)
                                .child(DatabaseConstants.REQUESTS)
                                .updateChildren(requests1, (databaseError, databaseReference) -> {

                                    final Intent intent = new Intent(FaceFragment.this.getContext(), PortfolioActivity.class);
                                    intent.putExtra(IntentConstants.QUEST, edtQuestBar.getText().toString());
                                    intent.putExtra(IntentConstants.REQUEST_ID, newRequest.getRequestId());
                                    FaceFragment.this.startActivity(intent);
                                    rtLoading.setVisibility(View.GONE);
                                });
                    });
        });
    }

    @Override
    public void edtQuestBarOnKeyListener() {
        edtQuestBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                return false;
            }
        });
    }

    @Override
    public void txtDetailsOnClick() {
        mTxtDetails.setOnClickListener(view -> {
            stopRunnableForPop();

            final Intent intent = new Intent(getContext(), DetailsTaskActivity.class);
            intent.putExtra(IntentConstants.QUEST, edtQuestBar.getText().toString());
            startActivity(intent);
        });
    }

    @Override
    public void onFinishConnectionDialog() {
        onResume();
    }

    /**
     * set up Firebase
     */
    private void setUpFirebase() {
        mFirebaseAuth = FirebaseUtils.getFirebaseNewInstance();
    }

    @Subscribe
    public void onEvent(FaceActivity.BundleData bundleData) {
        boolean isSkip = bundleData.isSkip;
        List<TagItems> tagItemsList = bundleData.tagItemsList;
        Log.d(TAG, isSkip + "");
        //Log.d(TAG, tagItemsList.size() + ""); //NullPointerException
    }
}
