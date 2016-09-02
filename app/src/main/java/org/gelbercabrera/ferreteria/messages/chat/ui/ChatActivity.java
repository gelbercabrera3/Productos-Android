package org.gelbercabrera.ferreteria.messages.chat.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.gelbercabrera.ferreteria.R;
import org.gelbercabrera.ferreteria.entities.ChatMessage;
import org.gelbercabrera.ferreteria.messages.chat.ChatPresenter;
import org.gelbercabrera.ferreteria.messages.chat.ChatPresenterImpl;
import org.gelbercabrera.ferreteria.messages.chat.ui.adapters.ChatAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity implements ChatView {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.txtUser)
    TextView txtUser;
    @Bind(R.id.txtStatus)
    TextView txtStatus;
    @Bind(R.id.editTxtMessage)
    EditText inputMessage;
    @Bind(R.id.messageRecyclerView)
    RecyclerView recyclerView;

    public final static String EMAIL_KEY = "email";
    public final static String ONLINE_KEY = "online";
    private static final int PERMISSIONS_REQUEST_LOCATION = 1;
    public Location location;

    private ChatAdapter adapter;
    private ChatPresenter chatPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        chatPresenter = new ChatPresenterImpl(this);
        chatPresenter.onCreate();

        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        setToolbarData(intent);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupAdapter();
        setupRecyclerView();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_LOCATION);
        }
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        CustomLocationListenner mlocListener = new CustomLocationListenner();
        mlocListener.setMainActivity(this);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
    }


    @Override
    protected void onResume() {
        super.onResume();
        chatPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        chatPresenter.onPause();
    }

    @Override
    protected void onDestroy() {
        chatPresenter.onDestroy();
        super.onDestroy();
    }

    private void setToolbarData(Intent i) {
        String recipient = i.getStringExtra(EMAIL_KEY);
        chatPresenter.setChatRecipient(recipient);

        boolean online = i.getBooleanExtra(ONLINE_KEY, false);
        String status = online ? "online" : "offline";

        txtUser.setText(recipient);
        txtStatus.setText(status);

    }

    private void setupAdapter() {
        adapter = new ChatAdapter(this, new ArrayList<ChatMessage>());
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    @OnClick(R.id.btnSendMessage)
    public void sendMessage() {
        chatPresenter.sendMessage(inputMessage.getText().toString());
        inputMessage.setText("");
    }

    @Override
    @OnClick(R.id.btnSendLocation)
    public void sendLocation() {
        final ChatActivity activity = this;
        new AlertDialog.Builder(this)
                .setTitle("Enviar ubicación")
                .setMessage("Está seguro de que desea enviar su ubicación?")
                .setPositiveButton("SÍ", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(location != null){
                            String text = "http://maps.google.com/maps?&z=10&q="
                                    + location.getLatitude() + "+" + location.getLongitude() +
                                    "&ll="+location.getLatitude()+"+"+location.getLongitude();
                            chatPresenter.sendMessage(text);
                        }else{
                            Toast.makeText(activity, "Imposible obtener la ubicación", Toast.LENGTH_SHORT);
                        }
                    }

                })
                .setNegativeButton("NO", null)
                .show();
    }

    @Override
    public void onMessageReceived(ChatMessage msg) {
        adapter.add(msg);
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

    public void setLocation(Location loc) {
        location = loc;
    }

}