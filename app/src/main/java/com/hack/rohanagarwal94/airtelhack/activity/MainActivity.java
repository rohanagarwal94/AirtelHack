package com.hack.rohanagarwal94.airtelhack.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hack.rohanagarwal94.airtelhack.R;
import com.hack.rohanagarwal94.airtelhack.fragments.MyAccountFragment;
import com.hack.rohanagarwal94.airtelhack.fragments.ReceivedRequestsFragment;
import com.hack.rohanagarwal94.airtelhack.fragments.SentRequestsFragment;
import com.hack.rohanagarwal94.airtelhack.service.SuggestionService;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgProfile;
    private TextView txtName;
    private Toolbar toolbar;

    public static int navItemIndex = 0;

    private static final String TAG_MY_ACCOUNT = "my_account";
    private static final String TAG_RECEIVED_REQUEST = "received_request";
    private static final String TAG_SENT_REQUEST = "sent_request";
    public static String CURRENT_TAG = TAG_MY_ACCOUNT;
    private String[] activityTitles;

    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, SuggestionService.class);
        startService(intent);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mHandler = new Handler();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        loadNavHeader();
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_MY_ACCOUNT;
            loadHomeFragment();
        }

    }

    private void loadHomeFragment() {
        selectNavMenu();
        setToolbarTitle();
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
        drawer.closeDrawers();
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                MyAccountFragment accountFragment = new MyAccountFragment();
                return accountFragment;
            case 1:
                SentRequestsFragment sentRequestsFragment = new SentRequestsFragment();
                return sentRequestsFragment;
            case 2:
                ReceivedRequestsFragment receivedRequestsFragment = new ReceivedRequestsFragment();
                return receivedRequestsFragment;
            default:
                return new MyAccountFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_profile:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_MY_ACCOUNT;
                        break;
                    case R.id.nav_sent_request:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_SENT_REQUEST;
                        break;
                    case R.id.nav_received_request:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_RECEIVED_REQUEST;
                        break;
                    default:
                        navItemIndex = 0;
                }

                loadHomeFragment();
                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void loadNavHeader() {
        txtName.setText("Deadpool");
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        if (shouldLoadHomeFragOnBackPress) {
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_MY_ACCOUNT;
                loadHomeFragment();
                return;
            }
        }
        super.onBackPressed();
    }

}
