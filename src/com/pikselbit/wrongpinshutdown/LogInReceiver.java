package com.pikselbit.wrongpinshutdown;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class LogInReceiver extends DeviceAdminReceiver {

	DevicePolicyManager mDPM;
	ComponentName mAdminName;

	@Override
	public void onPasswordFailed(Context ctxt, Intent intent) {

		String PREFS_NAME1 = "number";
		SharedPreferences number = ctxt.getSharedPreferences(PREFS_NAME1, 0);
		int i = number.getInt("i", 0);

		i++;

		number.edit().putInt("i", i).commit();
		number.edit().apply();

		if (i >= 10) {

			i = 0;

			number.edit().putInt("i", i).commit();
			number.edit().apply();

			try {

				Process proc = Runtime.getRuntime().exec(
						new String[] { "su", "-c", "reboot", "-p" });
				proc.waitFor();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	@Override
	public void onPasswordSucceeded(Context ctxt, Intent intent) {

		int i = 0;

		String PREFS_NAME1 = "number";
		SharedPreferences number = ctxt.getSharedPreferences(PREFS_NAME1, 0);
		number.edit().putInt("i", i).commit();
		number.edit().apply();
	}
}
