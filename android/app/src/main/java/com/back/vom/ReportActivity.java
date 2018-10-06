package com.back.vom;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.print.PrinterId;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.back.vom.models.Comment;
import com.back.vom.models.Report;
import com.back.vom.services.CommentService;
import com.back.vom.services.ReportService;
import com.back.vom.services.SFHandler;
import com.back.vom.services.UpvoteService;
import com.back.vom.services.VolunteerService;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class ReportActivity extends AppCompatActivity {


    public static String REPORT_ID = "ads";

    @BindView(R.id.report_category)
    TextView mCategoryTV;

    @BindView(R.id.report_desc)
    TextView mDescriptionTV;

    @BindView(R.id.report_voluteers_count)
    TextView mVCountTV;

    @BindView(R.id.report_voluteers_date)
    TextView mVDateTv;

    @BindView(R.id.report_upvotes)
    TextView mUpvotesTV;

    @BindView(R.id.report_image_view)
    ImageView mImageView;

    @BindView(R.id.comment_lv)
    ListView mCommentsLV;


    @BindView(R.id.volunteer_bt)
    Button mVolunteerBT;



    List<String> commentsFormatted  = new ArrayList<>();



    StableArrayAdapter adapter;



    private  Report mReport;
    private String uid;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        Intent i  = getIntent();
        String id = i.getStringExtra(REPORT_ID);

        adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, commentsFormatted);
        mCommentsLV.setAdapter(adapter);


        uid = FirebaseAuth.getInstance().getUid();
        name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        loadData(id);
    }


    public void upvote(View v) {
        UpvoteService.upvote(mReport,null);
        loadUI();
    }

    public void volunteer(View v) {
        VolunteerService.addVolunteer(mReport, uid,null,this);
        loadUI();
    }
    public void  comment(View v) {
        final EditText taskEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Enter Comment")
                .setView(taskEditText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Comment comment = new Comment();
                        comment.setCommentText(taskEditText.getText().toString());
                        comment.setUserId(uid);
                        comment.setUserName(name);
                        CommentService.addComment(mReport,comment,null);
                        loadUI();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    public void loadData(String id) {
        ReportService.getReport(id, new ReportService.CompleteListener() {
            @Override
            public void complete(Object object) {
                if(object instanceof Exception) {

                } else if(object instanceof Report) {

                    mReport = (Report) object;
                    commentsFormatted.clear();
                    loadUI();
                }
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_report,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_ward ) {
            WardActivity.ward = mReport.getWard();
            startActivity(new Intent(this,WardActivity.class));
        } else if(item.getItemId() == R.id.action_volunteers) {
            VolunteersActivity.report = mReport;
            startActivity(new Intent(this,VolunteersActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadUI() {
        if(mReport != null) {
            Picasso.get().load(mReport.getImageUrl()).into(mImageView);
            mCategoryTV.setText("Cateory: "+mReport.getCategory());
            mDescriptionTV.setText("Description: "+mReport.getDescription());
            mVCountTV.setText("Total Volunteers: "+mReport.getmVolunteers().size());
            mVDateTv.setText("Volunteer Date: "+mReport.getDate());
            mUpvotesTV.setText("Upvotes: "+mReport.getUpvotes());

            if(!mReport.getVolunteer())
                mVolunteerBT.setVisibility(View.GONE);
            else mVolunteerBT.setVisibility(View.VISIBLE);


            for (String volunteer:mReport.getmVolunteers()) {
                if(volunteer.equals(uid)) {
                    mVolunteerBT.setVisibility(GONE);
                    break;
                }
            }

            commentsFormatted.clear();
            for (Comment c: mReport.getmComments()) {
                commentsFormatted.add(c.getUserName() + ":\n" + c.getCommentText());
            }
            adapter.notifyDataSetChanged();
        }
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);

        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            for (int i = 0; i < getCount(); ++i) {
                mIdMap.put(getItem(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }


}
