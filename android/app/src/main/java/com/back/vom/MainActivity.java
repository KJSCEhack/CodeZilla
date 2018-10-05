package com.back.vom;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.back.vom.fragments.LocationReports;
import com.back.vom.fragments.NewReports;
import com.back.vom.fragments.YourReports;
import com.back.vom.utils.InitFragment;
import com.bugsee.library.Bugsee;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    public FirebaseDatabase database;
    YourReports mYourReports = new YourReports();
    LocationReports mLocationReports = new LocationReports();
    NewReports mNewReports = new NewReports();
    AlertDialog mDialog;

    View promptsView;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        Bugsee.launch(this, "f6a25b56-b280-4e64-aa0c-d9809493d3d4");

        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);





         fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        /*if (id == R.id.action_settings) {
            return false;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.your_reports) {
            fab.setVisibility(View.VISIBLE);
            InitFragment.set(MainActivity.this, mYourReports);
            Toast.makeText(this, "Your reports", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.location_reports) {
            fab.setVisibility(View.VISIBLE);
            InitFragment.set(MainActivity.this, mLocationReports);
            Toast.makeText(this, "Location", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.new_report) {
            fab.setVisibility(View.INVISIBLE);
            InitFragment.set(MainActivity.this, mNewReports);
            Toast.makeText(this, "New", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.faq) {
            fab.setVisibility(View.VISIBLE);
            Toast.makeText(this, "FAQ", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
