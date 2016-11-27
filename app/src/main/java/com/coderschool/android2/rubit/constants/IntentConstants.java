/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.constants;

/**
 * {@link IntentConstants} contains the constant for Intent purposes.
 *
 * @author TienNguyen
 */
public class IntentConstants {

    /**
     * SPLASH_TIME_OUT
     */
    public final static int SPLASH_TIME_OUT = 1000;

    /**
     * POP_REQUEST_TIME
     */
    public final static int POP_REQUEST_TIME = 10000;

    /**
     * DIALOG_CONNECTION_TAG
     */
    public final static String DIALOG_CONNECTION_TAG = "dialog_connection";

    /**
     * DIALOG_CONNECTION_TITLE
     */
    public final static String DIALOG_CONNECTION_TITLE = "DIALOG_CONNECTION_TITLE";

    /**
     * REQUEST_MODEL
     */
    public final static String REQUEST_MODEL = "REQUEST_MODEL";

    /**
     * QUEST
     */
    public final static String QUEST = "QUEST";

    /**
     * REQUEST_ID
     */
    public final static String REQUEST_ID = "REQUEST_ID";

    /**
     * USER
     */
    public final static String USER = "USER";

    /**
     * USER_ID
     */
    public final static String USER_ID = "USER_ID";

    /**
     * to check for Tag Screen - show at first time launch app
     */
    public static final String IS_FIRST = "is_first";

    /**
     * to know does the user tap on slip button  -- we maintain true / false in firebase
     */
    public static final String IS_SKIP = "is_skip";

    public static final String LIST_OF_CHOSEN_TAGS = "list_of_chosen_tags";

    /**
     * UPLOAD_IMAGE_ACTIVITY_REQUEST_CODE
     */
    public final static int UPLOAD_IMAGE_ACTIVITY_REQUEST_CODE = 2000;
}
