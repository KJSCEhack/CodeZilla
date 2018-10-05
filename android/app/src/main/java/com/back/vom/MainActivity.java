package com.back.vom;

import android.content.Intent;
import android.content.res.Resources;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.back.vom.fragments.LocationReports;
import com.back.vom.fragments.YourReports;
import com.back.vom.models.Report;
import com.back.vom.utils.DialogBoxBuilder;
import com.back.vom.utils.InitFragment;
import com.bugsee.library.Bugsee;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PICK_IMAGE_REQUEST = 1;

    public FirebaseDatabase database;
    YourReports mYourReports = new YourReports();
    LocationReports mLocationReports = new LocationReports();
    AlertDialog mDialog;
    RadioButton volunterRadioButton, yes, no;
    RadioGroup radioGroup;
    View promptsView;

    EditText mTitle, mDescription;
    TextView mDate;

    CalendarView mCalendarView;

    String mSelectedDate = "";
    private boolean mVolunteer = false;

    Button mBrowseButton;
    private Uri mUri = null;

    ImageView mPreviewImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        Bugsee.launch(this, "f6a25b56-b280-4e64-aa0c-d9809493d3d4");

        setContentView(R.layout.activity_main);

        LayoutInflater li = LayoutInflater.from(MainActivity.this);
        promptsView = li.inflate(R.layout.infodialog, null);

        database = FirebaseDatabase.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        radioGroup = promptsView.findViewById(R.id.radioGroup);
        mCalendarView = promptsView.findViewById(R.id.calendarView);

        mTitle = promptsView.findViewById(R.id.reportTitle);
        mDescription = promptsView.findViewById(R.id.reportDescription);

        yes = promptsView.findViewById(R.id.yesradiobutton);
        no = promptsView.findViewById(R.id.noradiobutton);

        mBrowseButton = promptsView.findViewById(R.id.browse_button);

        mPreviewImage = promptsView.findViewById(R.id.preview_imageView);

        setSupportActionBar(toolbar);

        mBrowseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDialog = DialogBoxBuilder.createDialogBox(MainActivity.this,
                        "Describe your report",
                        false,
                        promptsView,
                        "submit",
                        null,
                        "",
                        null,
                        "cancel", null,
                        Resources.getSystem());
                mDialog.show();

                Window window = mDialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                Button positive = mDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positive.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        //Do Your thing
                        validateReport();
                    }
                });

                Button negative = mDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negative.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        //Do Your thing
                        mDialog.cancel();
                    }
                });
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (yes == promptsView.findViewById(checkedId)) {
                    //TODO: make date button visible
                    Toast.makeText(MainActivity.this, "yes", Toast.LENGTH_SHORT).show();
                    mVolunteer = true;
                } else {

                }
            }
        });

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                mSelectedDate = dayOfMonth + "-" + (month + 1) + "-" + year;
                Toast.makeText(MainActivity.this, mSelectedDate, Toast.LENGTH_SHORT).show();

            }
        });
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

        if (id == R.id.nav_camera) {
            InitFragment.set(MainActivity.this, mYourReports);
            Toast.makeText(this, "Your reports", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gallery) {
            InitFragment.set(MainActivity.this, mLocationReports);
            Toast.makeText(this, "Location", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {
            Toast.makeText(this, "FAQ", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public DatabaseReference getDatabaseReference() {
        return database.getReference();
    }


    private void validateReport() {

        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (mTitle.getText().toString().equals(""))
            Toast.makeText(this, "Please enter title !", Toast.LENGTH_SHORT).show();
        else if (mDescription.getText().toString().equals(""))
            Toast.makeText(this, "Please enter description !", Toast.LENGTH_SHORT).show();
        else if (selectedId == -1)
            Toast.makeText(MainActivity.this, "Select Volunteer !", Toast.LENGTH_SHORT).show();
        else if (mSelectedDate.equals("") && mVolunteer)
            Toast.makeText(MainActivity.this, "Please Select date !", Toast.LENGTH_SHORT).show();
        else
            new Report(mTitle.getText().toString(), mDescription.getText().toString(), mVolunteer, mSelectedDate, "");

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mUri = data.getData();
            Picasso.get().load(mUri).into(mPreviewImage);
        }

    }
}
