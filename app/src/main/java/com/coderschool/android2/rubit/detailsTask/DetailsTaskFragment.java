package com.coderschool.android2.rubit.detailsTask;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.coderschool.android2.rubit.R;
import com.coderschool.android2.rubit.constants.DatabaseConstants;
import com.coderschool.android2.rubit.constants.IntentConstants;
import com.coderschool.android2.rubit.models.ImageResponse;
import com.coderschool.android2.rubit.models.RequestModel;
import com.coderschool.android2.rubit.portfolio.PortfolioActivity;
import com.coderschool.android2.rubit.utils.ConnectionUtils;
import com.coderschool.android2.rubit.utils.FileUtils;
import com.coderschool.android2.rubit.utils.FirebaseUtils;
import com.coderschool.android2.rubit.utils.ImgurApi;
import com.coderschool.android2.rubit.utils.PermissionUtils;
import com.coderschool.android2.rubit.utils.RetrofitUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.coderschool.android2.rubit.detailsTask.CameraActivity.UPLOAD_IMAGE_ACTIVITY_REQUEST_CODE;

/**
 * Created by vinay on 22/11/16.
 */

public class DetailsTaskFragment extends Fragment implements DetailsTaskContract.View, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, View.OnClickListener {

    private DetailsTaskContract.Presenter mPresenter;
    private long UPDATE_INTERVAL = 30 * 1000;
    private long FASTEST_INTERVAL = 5 * 1000;
    private GoogleMap mGoogleMap;

    @BindView(R.id.addressTxt)
    AppCompatTextView mAddressTxt;
    @BindView(R.id.firstEdtTxtView)
    AppCompatEditText mFirstEdtTxtView;
    @BindView(R.id.secondEdtTxtView)
    AppCompatEditText mSecondEdtTxtView;
    @BindView(R.id.thirdEdtTxtView)
    AppCompatEditText mThirdEdtTxtView;
    @BindView(R.id.fourthItemIv)
    AppCompatImageView mFourthItemIv;
    //    @BindView(R.id.detailedImageView)
//    AppCompatImageView mDetailedImageView;
    @BindView(R.id.addImages)
    AppCompatImageView mAddImages;
    @BindView(R.id.detailedImageRv)
    RecyclerView detailedImageRv;
    @BindView(R.id.goButtonDetailScreen)
    AppCompatImageView goButtonDetailScreen;
    private GoogleApiClient mGoogleApiClient;
    private LatLng mCurrentLatLng;
    private LocationRequest mLocationRequest;
    private Marker mMarker;
    SupportMapFragment mapFragment;
    PermissionUtils permissionUtils;
    Context mContext;
    private String mCurrentPhotoPath;
    private List<String> imagesUrls;

    DetailTakImagesAdapter detailTakImagesAdapter;

    //Default Constructor
    public DetailsTaskFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionUtils = new PermissionUtils(getActivity());
        if (ConnectionUtils.verifyConnectionDialogForFragment(getActivity(), this, getActivity().getSupportFragmentManager())) {
            if (FirebaseUtils.getCurrentUserRef() == null) {
                moveBackToLoginActivity();
            }
        }
    }

    private void setUpMapGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    private void moveBackToLoginActivity() {
        Intent intent = new Intent(getActivity(), DetailsTaskFragment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ConnectionUtils.verifyConnectionDialogForFragment(getActivity(), this, getActivity().getSupportFragmentManager()))
            mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_details_task, container, false);
        ButterKnife.bind(this, view);
        initUI();
        if (permissionUtils.checkPermissionForAccessFineAndCoarseLocations()) {
            showGoogleMap();
        } else {
            permissionUtils.requestPermissionForLocationService(getActivity());
        }
        setUpMapGoogleApiClient();
        return view;
    }

    private void initUI() {
        imagesUrls = new ArrayList<>();
        // Define Grid Layout Manager layout
        final LinearLayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        //pass the layout to recyclerView layout manager
        detailedImageRv.setLayoutManager(layout);
        //create adapter
        detailTakImagesAdapter = new DetailTakImagesAdapter(getContext(), imagesUrls);
        //assign adapter to recyclerView
        detailedImageRv.setAdapter(detailTakImagesAdapter);
        goButtonDetailScreen.setOnClickListener(this);
    }

    public static DetailsTaskFragment newInstance() {

        Bundle args = new Bundle();

        DetailsTaskFragment fragment = new DetailsTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void showGoogleMap() {
        if (mapFragment == null) {
            mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mGoogleMap = googleMap;
                    updateCamera();
                }
            });
        }
    }

    private void updateCamera() {
        if (mGoogleMap != null & mCurrentLatLng != null) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(mCurrentLatLng);
            mGoogleMap.moveCamera(cameraUpdate);
            addMarker();
        }

    }

    private void addMarker() {
        if (mMarker != null) {
            mMarker.remove();
            mMarker = null;
        }

        BitmapDescriptor defaultMarker = BitmapDescriptorFactory.
                defaultMarker(BitmapDescriptorFactory.HUE_RED);
        mMarker = mGoogleMap.addMarker(new MarkerOptions()
                .position(mCurrentLatLng).icon(defaultMarker));
        dropPinEffect(mMarker);

    }

    private void dropPinEffect(Marker marker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1500;
        final Interpolator interpolator = new BounceInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = Math.max(1 - interpolator.getInterpolation((float) elapsed / duration), 0);
                marker.setAnchor(0.5f, 1.0f + 14 * t);
                if (t > 0.0) {
                    handler.postDelayed(this, 15);
                } else {
                    marker.showInfoWindow();
                }
            }
        });
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void currentLocationOnClick() {

    }

    @Override
    public void showAddressDetails() {

    }

    @Override
    public void showFirstTextView() {

    }

    @Override
    public void showSecondTextView() {

    }

    @Override
    public void showThirdTextView() {

    }

    @Override
    public void showPictureImageView() {

    }

    @Override
    public void editFirstTextOnKeyListener() {
        String firstText = mFirstEdtTxtView.getText().toString();
    }

    @Override
    public void editSecondTextOnKeyListener() {
        String secondText = mSecondEdtTxtView.getText().toString();
    }

    @Override
    public void editThirdTextOnKeyListener() {
        String thirdText = mThirdEdtTxtView.getText().toString();
    }

    @Override
    public void plusButtonOnClick() {
        mAddImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), CameraActivity.class);
//                startActivity(intent);
                openCamera(UPLOAD_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    private void openCamera(int uploadImageActivityRequestCode) {
        if (PermissionUtils.checkExternal(getActivity())) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = FileUtils.createPhotoFile(getActivity());
            mCurrentPhotoPath = file.getAbsolutePath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(intent, uploadImageActivityRequestCode);
            }
        } else {
            PermissionUtils.requestExternal(getActivity());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPLOAD_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            uploadImage();
        }
    }

    private void uploadImage() {
        File file = new File(mCurrentPhotoPath);
        RetrofitUtils.get(getString(R.string.IMGUR_CLIENT_ID))
                .create(ImgurApi.class)
                .create(FileUtils.partFromFile(file), FileUtils.requestBodyFromFile(file))
                .enqueue(new Callback<ImageResponse>() {
                    @Override
                    public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                        if (response.isSuccessful()) {
                            ImageResponse imageResponse = response.body();
                            imagesUrls.add(imageResponse.getData().getLink());
//                            Glide.with(getContext())
//                                    .load(imageResponse.getData().getLink())
//                                    .placeholder(R.drawable.image_placeholder)
//                                    .into(mDetailedImageView);
                            Log.d("imagesUrl ::- ", imagesUrls.size() + "");
                            String link = imageResponse.getData().getLink();
                            imagesUrls.add(link);
                            Set<String> hs = new HashSet<>();
                            hs.addAll(imagesUrls);
                            imagesUrls.clear();
                            imagesUrls.addAll(hs);
                            detailTakImagesAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ImageResponse> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void showPlusButton() {

    }

    @Override
    public void setPresenter(DetailsTaskContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (permissionUtils.checkPermissionForAccessFineAndCoarseLocations()) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (location != null) {
                mCurrentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                updateCamera();
            }
            startLocationUpdates();
        }
    }

    @SuppressWarnings("MissingPermission")
    private void startLocationUpdates() {
        if (permissionUtils.checkPermissionForAccessFineAndCoarseLocations()) {
            mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(UPDATE_INTERVAL)
                    .setFastestInterval(FASTEST_INTERVAL);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (i == CAUSE_SERVICE_DISCONNECTED) {
            Toast.makeText(getActivity(), "Disconnected -- please-reconnect", Toast.LENGTH_LONG).show();
        } else if (i == CAUSE_NETWORK_LOST) {
            Toast.makeText(getActivity(), "Network -- please-reconnect", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getActivity(), connectionResult.getErrorMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtils.REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.GET_RESOLVED_FILTER) {
                showGoogleMap();
            } else {
                Toast.makeText(getActivity(), "Location permissions are denied", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        String msg = "Updated Location: "
                + Double.toString(location.getLatitude()) + ","
                + Double.toString(location.getLongitude());
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        mCurrentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        updateCamera();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goButtonDetailScreen:
                if (0 >= mFirstEdtTxtView.getText().length()) {
                    Toast.makeText(getContext(), "Quest heading can't be Empty", Toast.LENGTH_SHORT).show();
                    mFirstEdtTxtView.requestFocus();
                    return;
                }

                String currentUserId = FirebaseUtils.getCurrentUserId();
                if (currentUserId == null)
                    return;

                Map<String, Boolean> tags = new HashMap<>();
                tags.put(DatabaseConstants.TAG_OTHERS, true);

                final RequestModel newRequesDetailTaktModel = new RequestModel();
                newRequesDetailTaktModel.setDetailEdtFirstQstTxt(mFirstEdtTxtView.getText().toString());
                newRequesDetailTaktModel.setDetailEdtSecondQstTxt(mSecondEdtTxtView.getText().toString());
                newRequesDetailTaktModel.setDetailEdtThirdQsttxt(mThirdEdtTxtView.getText().toString());
                newRequesDetailTaktModel.setDetailImageQst(imagesUrls);
                newRequesDetailTaktModel.setTags(tags);
                newRequesDetailTaktModel.setUid(currentUserId);
                newRequesDetailTaktModel.setConnected(false);
                newRequesDetailTaktModel.setCompleted(false);
                // Create new detail record in request table
                DatabaseReference newDetailRequestReference = FirebaseUtils.getRequests().push();
                newDetailRequestReference.setValue(newRequesDetailTaktModel);
                newRequesDetailTaktModel.setRequestId(newDetailRequestReference.getKey());


                // Update that record & current user record too
                FirebaseUtils.getRequests()
                        .child(newRequesDetailTaktModel.getRequestId())
                        .setValue(newRequesDetailTaktModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Map<String, Object> detailTaskRequests = new HashMap<>();
                        detailTaskRequests.put(newRequesDetailTaktModel.getRequestId(), true);

                        FirebaseUtils.getRubitUser()
                                .child(currentUserId).child(DatabaseConstants.REQUESTS)
                                .updateChildren(detailTaskRequests, (databaseError, databaseReference) -> {

                                    final Intent intent = new Intent(DetailsTaskFragment.this.getContext(), PortfolioActivity.class);
                                    intent.putExtra(IntentConstants.QUEST, mFirstEdtTxtView.getText().toString());
                                    intent.putExtra(IntentConstants.QUEST, mSecondEdtTxtView.getText().toString());
                                    intent.putExtra(IntentConstants.QUEST, mThirdEdtTxtView.getText().toString());
                                    intent.putExtra(IntentConstants.QUEST, String.valueOf(imagesUrls));
                                    intent.putExtra(IntentConstants.USER_ID, currentUserId);
                                    DetailsTaskFragment.this.startActivity(intent);
                                });
                    }
                });
                break;
        }
    }
}
