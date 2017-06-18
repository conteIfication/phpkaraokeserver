package com.bku.cse.karaoke.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.bku.cse.karaoke.R;
import com.bku.cse.karaoke.controller.HomeActivity;
import com.bku.cse.karaoke.controller.SettingActivity;
import com.bku.cse.karaoke.helper.BaseURLManager;
import com.bku.cse.karaoke.helper.SessionManager;

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
                if ( newValue.toString().equals("theme1") ) {
                    getActivity().setTheme(R.style.AppTheme_theme1);
                    Toast.makeText(getActivity().getApplicationContext(), "Changed Dark Pink Theme", Toast.LENGTH_SHORT).show();
                }
                else {
                    getActivity().setTheme(R.style.AppTheme_theme2);
                    Toast.makeText(getActivity().getApplicationContext(), "Changed Blue Theme", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        EditTextPreference changeServer = (EditTextPreference) findPreference("baseUrl");
        EditText editText = changeServer.getEditText();
        editText.setTextColor(Color.BLACK);
        editText.setTextSize(16);

        changeServer.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String newUrl = (String) newValue;
                if (!newUrl.equals("")) {
                    new BaseURLManager(getContext()).setBaseURL(
                            "http://" + newUrl + ":8000/"
                    );
                }
                return true;
            }
        });
    }
}
