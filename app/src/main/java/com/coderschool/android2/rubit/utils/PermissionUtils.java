package com.coderschool.android2.rubit.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import static android.support.v4.app.ActivityCompat.requestPermissions;

/**
 * Created by vinay on 22/11/16.
 */

public class PermissionUtils {
    public static final int REQUEST_LOCATION = 1000;
    public static final int REQUEST_CAMERA = 2000;

    Activity mActivity;

    public PermissionUtils(Activity activity) {
        this.mActivity = activity;
    }

    public boolean checkPermissionForAccessFineAndCoarseLocations() {
        String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && mActivity != null && PERMISSIONS != null) {
            for (String permission : PERMISSIONS) {
                if (ActivityCompat.checkSelfPermission(mActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void requestPermissionForLocationService(FragmentActivity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(mActivity, "Sorry, because Location permission are disabled we couldn't get your location exactly", Toast.LENGTH_LONG).show();
        } else {
<<<<<<< 4ec9fe13afa7b3c4e36ba7ab61e6e9b245b4aa73
            requestPermissions(mActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
=======
>>>>>>> Addd google map to detail screen
            requestPermissions(mActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        }
    }

}
