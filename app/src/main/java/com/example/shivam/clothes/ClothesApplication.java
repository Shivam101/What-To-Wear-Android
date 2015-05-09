package com.example.shivam.clothes;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

/**
 * Created by Shivam on 09/05/15 at 12:35 PM.
 */
public class ClothesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "YDseQi27NMHN8HqKCq349V4rnZpVmnA6jyrVipiS", "XzTJwxPwLKEUwomwOS8X9tKinDK4yYJpsAHYDNMk");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
