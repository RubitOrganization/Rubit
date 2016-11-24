/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.utils;

import com.coderschool.android2.rubit.constants.DatabaseConstants;
import com.coderschool.android2.rubit.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vinay on 18/11/16.
 */
public class FirebaseUtils {

    /**
     * New Instance
     *
     * @return FirebaseAuth
     */
    public static FirebaseAuth getFirebaseNewInstance() {
        return FirebaseAuth.getInstance();
    }

    /**
     * Base reference
     *
     * @return DatabaseReference
     */
    public static DatabaseReference getBaseRef() {
        return FirebaseDatabase.getInstance().getReference();
    }

    /**
     * get current User Id
     *
     * @return String
     */
    public static String getCurrentUserId() {
        FirebaseUser user = getFirebaseNewInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        }
        return null;
    }

    /**
     * get current User Name
     *
     * @return String
     */
    public static String getCurrentUserName() {
        FirebaseUser user = getFirebaseNewInstance().getCurrentUser();
        if (user != null) {
            return user.getDisplayName();
        }
        return null;
    }

    /**
     * get user details
     *
     * @return UserModel
     */
    public static UserModel getUserDetails() {
        final FirebaseUser user = getFirebaseNewInstance().getCurrentUser();
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

        // Tag
        Map<String, Boolean> tags = new HashMap<>();
        tags.put(DatabaseConstants.TAG_OTHERS, true);
        tags.put(DatabaseConstants.TAG_ANDROID, false);
        tags.put(DatabaseConstants.TAG_IOS, false);
        tags.put(DatabaseConstants.TAG_RUBY, false);
        tags.put(DatabaseConstants.TAG_PYTHON, false);

        return new UserModel(
                getCurrentUserId(),
                null,
                null,
                null,
                email,
                name,
                picture,
                0,
                false,
                tags,
                null);
    }

    /**
     * get Current userRef
     *
     * @return DatabaseReference
     */
    public static DatabaseReference getCurrentUserRef() {
        String uid = getCurrentUserId();
        if (uid != null) {
            return getRubitUser().child(uid);
        }
        return null;
    }

    /**
     * get reference for user table
     *
     * @return DatabaseReference
     */
    public static DatabaseReference getRubitUser() {
        return getBaseRef().child(DatabaseConstants.RUBIT_USERS);
    }

    /**
     * get reference for request table
     *
     * @return DatabaseReference
     */
    public static DatabaseReference getRequests() {
        return getBaseRef().child(DatabaseConstants.REQUESTS);
    }
}



