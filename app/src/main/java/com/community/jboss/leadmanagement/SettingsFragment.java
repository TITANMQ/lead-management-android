package com.community.jboss.leadmanagement;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat {


    public SettingsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings_preferences);

        if(getActivity()!=null) {
            final SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            final String currentServer = sharedPref.getString(getString(R.string.saved_server_ip), "https://github.com/jboss-outreach");

            final EditTextPreference mPreference = (EditTextPreference) findPreference("server_location");
            mPreference.setSummary(currentServer);
            mPreference.setText(currentServer);

            mPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    String newData = newValue.toString().trim();
                    mPreference.setSummary(newData);
                    mPreference.setText(newData);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.saved_server_ip),newData);
                    editor.apply();
                    return false;
                }
            });
        }
    }
}
