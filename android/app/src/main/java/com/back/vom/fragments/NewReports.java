package com.back.vom.fragments;


import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.back.vom.MainActivity;
import com.back.vom.R;
import com.back.vom.models.Report;
import com.back.vom.services.ReportService;
import com.back.vom.utils.InitFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


/**
 * The type Sign up step one fragment.
 */
public class NewReports extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String TAG = NewReports.class.getSimpleName();
    private static final int CAMERA_REQUEST_CODE = 2;
    RadioButton volunterRadioButton, yes, no;
    RadioGroup radioGroup;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    EditText mTitle, mDescription;
    TextView mDate;
    String mSelectedDate = "";
    Button mBrowseButton;
    ImageView mPreviewImage;
    FirebaseDatabase database;
    Button mSubmitButton;


    Report mReport;


    private View mView;
    private ProgressBar mProgressBar;
    private boolean mVolunteer = false;
    private Uri mUri = null;


    static final int REQUEST_IMAGE_CAPTURE = 1;


    private DatePicker datePicker;
    private YourReports mYourReports = new YourReports();


    private FusedLocationProviderClient mLocationProviderClient;
    private Object mLoc;

    public NewReports() {
    }

    private int mYear, mMonth, mDay, mHour, mMinute;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.newreports, container, false);
        database = FirebaseDatabase.getInstance();

        radioGroup = mView.findViewById(R.id.radioGroup);


        mTitle = mView.findViewById(R.id.reportTitle);
        mDescription = mView.findViewById(R.id.reportDescription);

        mReport = new Report();

        yes = mView.findViewById(R.id.yesradiobutton);
        no = mView.findViewById(R.id.noradiobutton);

        mBrowseButton = mView.findViewById(R.id.browse_button);

        mPreviewImage = mView.findViewById(R.id.preview_imageView);
        mProgressBar = mView.findViewById(R.id.upload_progress);

        mSubmitButton = mView.findViewById(R.id.report_submit_button);

        mDate = mView.findViewById(R.id.select_date);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateReport();
            }
        });
        mBrowseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        mLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (yes == mView.findViewById(checkedId)) {
                    //TODO: make date button visible
                    Toast.makeText(getActivity(), "yes", Toast.LENGTH_SHORT).show();
                    mVolunteer = true;
                    mDate.setVisibility(View.VISIBLE);
                } else {
                    mDate.setVisibility(View.GONE);
                    mVolunteer = false;
                }
            }
        });


        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "hey", Toast.LENGTH_SHORT).show();
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                mSelectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                mDate.setText(mSelectedDate);
                                Toast.makeText(getActivity(), mSelectedDate, Toast.LENGTH_SHORT).show();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }
        });

        getLoc();


        return mView;
    }


    private void validateReport() {

        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (mTitle.getText().toString().equals(""))
            Toast.makeText(getActivity(), "Please enter title !", Toast.LENGTH_SHORT).show();
        else if (selectedId == -1)
            Toast.makeText(getActivity(), "Select Volunteer !", Toast.LENGTH_SHORT).show();
        else if (mSelectedDate.equals("") && mVolunteer)
            Toast.makeText(getActivity(), "Please Select date !", Toast.LENGTH_SHORT).show();
        else
            uploadFile();
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mPreviewImage.setImageBitmap(imageBitmap);

            Uri tempUri = getImageUri(getContext(), imageBitmap);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));

            mUri = Uri.fromFile(finalFile);


        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getActivity().getContentResolver() != null) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    private void uploadFile() {


        Toast.makeText(getActivity(), "Uploading !", Toast.LENGTH_SHORT).show();
        String fileName = System.currentTimeMillis() + "." + getFileExtension(mUri);
        final String path = "uploads/" + fileName;

        StorageReference storageRef = storage.getReference();

        final StorageReference productImage = storageRef.child(path);
        final UploadTask uploadTask = productImage.putFile(mUri);


        Task<Uri> urlTask = uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                mProgressBar.setProgress((int) progress);
            }
        }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                // Continue with the task to get the download URL
                return productImage.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    //AddProduct(downloadUri.toString());
                    //add report
                    //

                    Log.d(TAG, "onComplete: " + downloadUri.toString());
                    mReport.setCategory(mTitle.getText().toString());
                    mReport.setDescription(mDescription.getText().toString());
                    mReport.setVolunteer(mVolunteer);
                    mReport.setDate(mSelectedDate);
                    mReport.setImageUrl(downloadUri.toString());
                    ReportService.createReport(mReport);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(0);
                            Toast.makeText(getActivity(), "File Uploaded", Toast.LENGTH_SHORT).show();
                            //mDialog.dismiss();
                            mUri = null;
                            InitFragment.set(getActivity(), mYourReports);


                        }
                    }, 500);

                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }


    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void getLoc() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationProviderClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location == null) {
                            Toast.makeText(getContext(), "GPS IS OFF", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(),MainActivity.class));
                            getActivity().finish();
                        }
                        mReport.setLatitude(location.getLatitude());
                        mReport.setLongitude(location.getLongitude());
                    }
                });
    }
}