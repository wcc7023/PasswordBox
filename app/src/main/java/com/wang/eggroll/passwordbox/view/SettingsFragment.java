package com.wang.eggroll.passwordbox.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.util.Log;

import com.wang.eggroll.passwordbox.App;
import com.wang.eggroll.passwordbox.R;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by eggroll on 14/04/2017.
 */

public class SettingsFragment extends PreferenceFragment {

    SwitchPreference switchPreference;
    Preference changePasswordPreference;

    int CONFIRM_OLD_PASSWORD = 101;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("confirm_result", "000");
        if (requestCode == CONFIRM_OLD_PASSWORD){
            switch (resultCode){
                case RESULT_CANCELED:
                    Log.e("confirm_result", "cancle");
                    break;
                case RESULT_OK:
                    Log.e("confirm_result", "ok");
                    Intent intent = new Intent(getActivity(), SetPatternActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.main_preference);

        switchPreference = (SwitchPreference) findPreference("stealth_mode");
        changePasswordPreference = findPreference("change_password");

        boolean checked = App.getStealthModeSharedPreference().getBoolean("STEALTH_MODE", false);
        switchPreference.setChecked(checked);
        switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                SharedPreferences.Editor editor = App.getStealthModeSharedPreference().edit();
                Log.e("preference", newValue.toString());
                editor.putBoolean("STEALTH_MODE", (Boolean)newValue);
                editor.commit();
                switchPreference.setChecked((Boolean)newValue);
                return true;
            }
        });

        changePasswordPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), ConfirmForPwdResetActivity.class);
                startActivityForResult(intent, CONFIRM_OLD_PASSWORD);
                return true;
            }
        });


    }
}
