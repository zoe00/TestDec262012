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

import android.app.Application;
import android.util.Log;

import com.urbanairship.Logger;
import com.urbanairship.UAirship;
import com.urbanairship.push.CustomPushNotificationBuilder;
import com.urbanairship.push.PushManager;

public class MyApplication extends Application {

	@Override
	public void onCreate() {

		// Take off with options defined in airshipconfig.properties...
		UAirship.takeOff(this);

		// The push service is DISABLED BY DEFAULT...
		PushManager.enablePush();
			
		// to make our receiver able to receive the broadcast intents...
		PushManager.shared().setIntentReceiver(IntentReceiver.class);

		String apid = PushManager.shared().getAPID();
		Logger.info("My Application onCreate - App APID: " + apid);

		Log.e("My Application onCreate - App APID: ", "" + apid);

		CustomPushNotificationBuilder nb = new CustomPushNotificationBuilder();

		nb.statusBarIconDrawableId = R.drawable.ic_launcher;

		nb.layout = R.layout.notification; // The layout resource to use
		nb.layoutIconDrawableId = R.drawable.ic_launcher; // The icon you want
															// to display
		nb.layoutIconId = R.id.icon; // The icon's layout 'id'
		nb.layoutSubjectId = R.id.subject; // The id for the 'subject' field
		nb.layoutMessageId = R.id.message; // The id for the 'message' field

		// set this ID to a value > 0 if you want a new notification to replace
		// the previous one
		// nb.constantNotificationId = 100;

		PushManager.shared().setNotificationBuilder(nb);

	}
	
	static void pushOff(boolean flag){
		if(flag)
			PushManager.enablePush();
		else
			PushManager.disablePush();
	}
}
