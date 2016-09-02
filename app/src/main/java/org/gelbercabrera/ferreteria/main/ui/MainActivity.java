package org.gelbercabrera.ferreteria.main.ui;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.gelbercabrera.ferreteria.R;
import org.gelbercabrera.ferreteria.login.ui.LoginActivity;
import org.gelbercabrera.ferreteria.main.MainPresenter;
import org.gelbercabrera.ferreteria.main.MainPresenterImpl;
import org.gelbercabrera.ferreteria.main.adapters.MainPagerAdapter;
import org.gelbercabrera.ferreteria.messages.chatlist.ui.ChatListFragment;
import org.gelbercabrera.ferreteria.posts.postlist.ui.PostsFragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity  {
    @Bind(R.id.container)
    ViewPager viewPager;
    @Bind(R.id.tabs)
    TabLayout tabs;

    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupAdapter();

        mainPresenter = new MainPresenterImpl(this);
    }

    private void setupAdapter() {
        Fragment[] fragments = new Fragment[] {new PostsFragment(),
                                                new ChatListFragment()};

        String[] titles = new String[] {getString(R.string.main_header_posts),
                                        getString(R.string.main_header_messages)};

        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);

        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contactlist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            mainPresenter.signOff();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
