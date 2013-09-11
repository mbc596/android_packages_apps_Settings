 /*
 * Copyright (C) 2012 The CyanogenMod project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.rascarlo;

import android.app.ActivityManagerNative;
import android.os.Bundle;
import android.content.ContentResolver;
import android.content.res.Configuration;
import android.preference.Preference;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.os.RemoteException;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
 

public class Extras extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {
    private static final String TAG = "SystemSettings";

    private static final float LARGE_FONT_SCALE = 1.3f;
  
    private static final String KEY_LOCKSCREEN_QUICK_UNLOCK_CONTROL = 
	        "lockscreen_quick_unlock_control";

    private static final String KILL_APP_LONGPRESS_BACK = "kill_app_longpress_back";

		private final Configuration mCurConfig = new Configuration();

			private CheckBoxPreference mToggleLargeTextPreference;
			private CheckBoxPreference mLockscreenQuickUnlockControl;
			private CheckBoxPreference mKillAppLongpressBack;		


	private void handleToggleLargeTextPreferenceClick() {
		try {
		    mCurConfig.fontScale = mToggleLargeTextPreference.isChecked() ? LARGE_FONT_SCALE : 1;
		    ActivityManagerNative.getDefault().updatePersistentConfiguration(mCurConfig);
		} catch (RemoteException re) {
		    /* ignore */
		}
	}
   
 public boolean onPreferenceChange(Preference preference, Object newValue) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.extras);
               
			   //Quick Unlock				
        mLockscreenQuickUnlockControl = (CheckBoxPreference) findPreference(KEY_LOCKSCREEN_QUICK_UNLOCK_CONTROL);
        mLockscreenQuickUnlockControl.setChecked(Settings.System.getBoolean(getActivity().getApplicationContext().getContentResolver(),
                    Settings.System.LOCKSCREEN_QUICK_UNLOCK_CONTROL, false)); 
			   //Kill App
       mKillAppLongpressBack = (CheckBoxPreference) findPreference(KILL_APP_LONGPRESS_BACK);
        mKillAppLongpressBack.setChecked(Settings.System.getBoolean(getActivity().getApplicationContext().getContentResolver(),
                    Settings.System.KILL_APP_LONGPRESS_BACK, false));
		}

    @Override
    public void onResume() {
        super.onResume();
        final ContentResolver cr = getActivity().getContentResolver();
        updateKillAppLongpressBackOptions();

    }

    private void writeKillAppLongpressBackOptions() {
        Settings.Secure.putInt(getActivity().getContentResolver(),
                Settings.Secure.KILL_APP_LONGPRESS_BACK,
                mKillAppLongpressBack.isChecked() ? 1 : 0);
    }

    private void updateKillAppLongpressBackOptions() {
        mKillAppLongpressBack.setChecked(Settings.Secure.getInt(
            getActivity().getContentResolver(), Settings.Secure.KILL_APP_LONGPRESS_BACK, 0) != 0);
    }

   @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {	
   	if (preference == mLockscreenQuickUnlockControl) {
            Settings.System.putBoolean(getContentResolver(),
                    Settings.System.LOCKSCREEN_QUICK_UNLOCK_CONTROL,
                    mLockscreenQuickUnlockControl.isChecked());
        } else if (preference == mKillAppLongpressBack) {
            writeKillAppLongpressBackOptions();        
		}

		return super.onPreferenceTreeClick(preferenceScreen, preference);
    }


 }

