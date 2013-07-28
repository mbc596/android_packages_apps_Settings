package com.android.settings.rascarlo;

import android.app.ActivityManagerNative;
import android.os.Bundle;
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
 

public class SystemSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {
    private static final String TAG = "SystemSettings";

    private static final float LARGE_FONT_SCALE = 1.3f;
  
    private static final String KEY_LOCKSCREEN_QUICK_UNLOCK_CONTROL = 
	        "lockscreen_quick_unlock_control";

		private final Configuration mCurConfig = new Configuration();

			private CheckBoxPreference mToggleLargeTextPreference;
			private CheckBoxPreference mLockscreenQuickUnlockControl;		


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

        addPreferencesFromResource(R.xml.system_settings);
               
			   //Quick Unlock				
        mLockscreenQuickUnlockControl = (CheckBoxPreference) findPreference(KEY_LOCKSCREEN_QUICK_UNLOCK_CONTROL);
        mLockscreenQuickUnlockControl.setChecked(Settings.System.getBoolean(getActivity().getApplicationContext().getContentResolver(),
                    Settings.System.LOCKSCREEN_QUICK_UNLOCK_CONTROL, false)); 
		}


   @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {	
    if (preference == mLockscreenQuickUnlockControl) {
            Settings.System.putBoolean(getContentResolver(),
                    Settings.System.LOCKSCREEN_QUICK_UNLOCK_CONTROL,
                    mLockscreenQuickUnlockControl.isChecked());         
		}

		return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
 

 }

