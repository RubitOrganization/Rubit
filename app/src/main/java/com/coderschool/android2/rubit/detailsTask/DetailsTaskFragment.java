package com.coderschool.android2.rubit.detailsTask;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.coderschool.android2.rubit.R;
import com.coderschool.android2.rubit.utils.ConnectionUtils;
import com.coderschool.android2.rubit.utils.FirebaseUtils;
import com.coderschool.android2.rubit.utils.PermissionUtils;
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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vinay on 22/11/16.
 */

public class DetailsTaskFragment extends Fragment implements DetailsTaskContract.View, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

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
    @BindView(R.id.detailedImageView)
    AppCompatImageView mDetailedImageView;
    @BindView(R.id.addImages)
    AppCompatImageView mAddImages;
    private GoogleApiClient mGoogleApiClient;
    private LatLng mCurrentLatLng;
    private LocationRequest mLocationRequest;
    private Marker mMarker;
    SupportMapFragment mapFragment;
    PermissionUtils permissionUtils;
    Context mContext;

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
        if (permissionUtils.checkPermissionForAccessFineAndCoarseLocations()) {
            showGoogleMap();
        } else {
            permissionUtils.requestPermissionForLocationService(getActivity());
        }
        setUpMapGoogleApiClient();
        return view;
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
                Toast.makeText(getActivity(), "Clicke me", Toast.LENGTH_LONG).show();
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
}
