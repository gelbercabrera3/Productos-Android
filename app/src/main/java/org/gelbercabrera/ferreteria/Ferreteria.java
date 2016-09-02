package org.gelbercabrera.ferreteria;

/**
 * Created by GelberCC on 24/08/2016.
 */

import android.app.Application;
import com.firebase.client.Firebase;
import com.google.firebase.storage.FirebaseStorage;

public class Ferreteria extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        setupFirebase();
    }

    private void setupFirebase(){
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }
}