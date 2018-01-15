package com.community.jboss.leadmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.community.jboss.leadmanagement.main.MainActivity;
import com.community.jboss.leadmanagement.main.contacts.editcontact.EditContactActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends FragmentActivity {
    @BindView(R.id.settings_bar)
    Toolbar toolbar;
    @BindView(R.id.switch1)
    Switch toggle;

    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (savedInstanceState != null) {
            // During initial setup, plug in the details fragment.
            SettingsFragment details = new SettingsFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.settings_fragment_id, details).commit();
        }
        ButterKnife.bind(this);
        toolbar.setTitle("Settings");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toggle.setChecked(useDarkTheme);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                toggleTheme(isChecked);
                if (isChecked){
                    MainActivity.useDarkTheme = true;
                    EditContactActivity.useDarkTheme = true;
                    Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    MainActivity.useDarkTheme = false;
                    Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void toggleTheme(boolean darkTheme) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(PREF_DARK_THEME, darkTheme);
        editor.apply();

        Intent intent = getIntent();
        finish();

        startActivity(intent);

        Intent i = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(i);
    }
}
