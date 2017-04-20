package com.wang.eggroll.passwordbox.utils;

import static android.app.Activity.RESULT_FIRST_USER;

/**
 * Created by eggroll on 19/04/2017.
 */

public class Statics {
    public static int CONFIRM_OLD_PASSWORD = 101;

    public static int REQUEST_CAMERA_PERMS = 102;

    public static int SCAN_REQUEST = 2;
    public static int IMAGE_REQUEST = 3;

    public static int IMAGE_ANALYZED_SUCCESS = 104;
    public static int IMAGE_ANALYZED_FAILED = 105;

    public static final int CLEAR_PATTERN_DELAY_MILLI = 2000;
    public static final String KEY_NUM_FAILED_ATTEMPTS = "num_failed_attempts";
    public static final int RESULT_FORGOT_PASSWORD = RESULT_FIRST_USER;

}
