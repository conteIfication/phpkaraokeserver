package com.bku.cse.karaoke.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.bku.cse.karaoke.R;
import com.bku.cse.karaoke.controller.HomeActivity;
import com.bku.cse.karaoke.controller.SettingActivity;

/**
 * Created by thonghuynh on 6/6/2017.
 */

public class SettingFragment extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_setting);

        ListPreference listPreference = (ListPreference) findPreference("themeStyle");
        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                SettingActivity settingActivity = (SettingActivity) getActivity();
                settingActivity.setChangedTheme(true);
                if ( newValue.toString().equals("theme1") ) {
                    Toast.makeText(getContext(), "Changed Dark Pink Theme", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Changed Blue Theme", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });
    }
}
