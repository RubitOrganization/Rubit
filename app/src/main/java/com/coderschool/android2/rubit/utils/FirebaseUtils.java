package com.coderschool.android2.rubit.utils;

import com.coderschool.android2.rubit.constants.DatabaseConstants;
import com.coderschool.android2.rubit.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by vinay on 18/11/16.
 */

public class FirebaseUtils {


    //Base reference
    public static DatabaseReference getBaseRef() {
        return FirebaseDatabase.getInstance().getReference();
    }

    //get currentuserId
    public static String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        }
        return null;
    }

    //get user details
    public static UserModel getUserDetails() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null)
            return null;
        String name = user.getDisplayName();
        String email = user.getEmail();
        String picture = user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : null;
        for (UserInfo userInfo : user.getProviderData()) {
            if (name == null && userInfo.getDisplayName() != null) {
                name = userInfo.getDisplayName();
            }

            if (email == null && userInfo.getEmail() != null) {
                email = userInfo.getEmail();
            }

        }
        return new UserModel(name, email, picture);
    }

    // get Current userRef
    public static DatabaseReference getCurrentUserRef() {
        String uid = getCurrentUserId();
        if (uid != null) {
            return getBaseRef().child(DatabaseConstants.RUBIT_USERS).child(getCurrentUserId());
        }
        return null;
    }

    public static DatabaseReference getCurrentUserKey() {
        return getBaseRef().getKey() != null ? getBaseRef().child(DatabaseConstants.RUBIT_USERS).child(getBaseRef().getKey()) : null;
    }


    //get reference for user table
    public static DatabaseReference getRubitUser() {
        return getBaseRef().child(DatabaseConstants.RUBIT_USERS);
    }

    //get reference for request table
    public static DatabaseReference getRequest() {
        return getBaseRef().child(DatabaseConstants.REQUEST);
    }
}



