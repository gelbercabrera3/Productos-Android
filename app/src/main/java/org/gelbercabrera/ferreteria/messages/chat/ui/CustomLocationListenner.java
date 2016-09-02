package org.gelbercabrera.ferreteria.messages.chat.ui;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

public class CustomLocationListenner implements LocationListener{
    ChatActivity mainActivity;

    public ChatActivity getMainActivity() {
        return mainActivity;
    }
    public void setMainActivity(ChatActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onLocationChanged(Location loc) {
        this.mainActivity.setLocation(loc);
    }


    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(mainActivity, "GPS Desactivado",  Toast.LENGTH_SHORT);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(mainActivity, "GPS Activado",  Toast.LENGTH_SHORT);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Toast.makeText(mainActivity, "GPS Activado",  Toast.LENGTH_SHORT);
    }

}
