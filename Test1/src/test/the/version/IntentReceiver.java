/*
Copyright 2009-2011 Urban Airship Inc. All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
this list of conditions and the following disclaimer in the documentation
and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE URBAN AIRSHIP INC ``AS IS'' AND ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
EVENT SHALL URBAN AIRSHIP INC OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package test.the.version;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.urbanairship.UAirship;
import com.urbanairship.push.PushManager;

public class IntentReceiver extends BroadcastReceiver {

	//	private String msg;
	Context context;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context=context;
		String action = intent.getAction();

		// if a notification is received ...
		if (action.equals(PushManager.ACTION_PUSH_RECEIVED)) {

			// Do whatever you want ....

			// if the notification is opened/clicked ...

		} else if (action.equals(PushManager.ACTION_NOTIFICATION_OPENED)) {

			Log.i("Notification", "User clicked notification. Message: "
					+ intent.getStringExtra(PushManager.EXTRA_ALERT));

			Intent launch = new Intent(Intent.ACTION_MAIN);
			launch.setClass(UAirship.shared().getApplicationContext(),
					Test1Activity.class);
			launch.putExtra("message", intent.getStringExtra(PushManager.EXTRA_ALERT));
			launch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			UAirship.shared().getApplicationContext().startActivity(launch);	

			Log.e("Notification", "Notification opened");
			logPushExtras(intent);

			// save the APID in a shared preferences ...
			// it'll be sent to the server ...
		} else if (action.equals(PushManager.ACTION_REGISTRATION_FINISHED)) {
			// to log the APID ...
			Log.e("LOG FROM INTENTRECEIVER",
					"Registration complete. APID:"
							+ intent.getStringExtra(PushManager.EXTRA_APID)
							+ ". Valid: "
							+ intent.getBooleanExtra(
									PushManager.EXTRA_REGISTRATION_VALID, false));

			// if registration is successful ...
			if (intent.getBooleanExtra(PushManager.EXTRA_REGISTRATION_VALID,
					false)) {

				// Do whatever you want ....
			} else {

				// register again ...
			}
		}
	}

	private void logPushExtras(Intent intent) {	

		Set<String> keys = intent.getExtras().keySet();
		for (String key : keys) {

			// ignore standard C2DM extra keys
			List<String> ignoredKeys = (List<String>) Arrays.asList(
					"collapse_key",// c2dm collapse key
					"from",// c2dm sender
					PushManager.EXTRA_NOTIFICATION_ID,// int id of generated
					// notification
					// (ACTION_PUSH_RECEIVED
					// only)
					PushManager.EXTRA_PUSH_ID,// internal UA push id
					PushManager.EXTRA_ALERT);// ignore alert
			if (ignoredKeys.contains(key)) {
				continue;
			}

		}
	}
}
