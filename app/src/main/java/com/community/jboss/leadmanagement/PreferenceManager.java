package com.community.jboss.leadmanagement;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager
{
    SharedPreferences preference;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE =0;

    private  static  final String PREF_NAME = "slider-welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PreferenceManager(Context context) {
        this.context = context;
        preference = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preference.edit();

    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return preference.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}
