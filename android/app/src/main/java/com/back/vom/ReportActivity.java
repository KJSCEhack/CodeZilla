package com.back.vom;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.back.vom.models.Comment;
import com.back.vom.models.Report;
import com.back.vom.services.ReportService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportActivity extends AppCompatActivity {


    private static String REPORT_ID = "ads";

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

    List<String> commentsFormatted  = new ArrayList<>();



    StableArrayAdapter adapter;



    private  Report mReport;
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

    private void loadUI() {
        if(mReport != null) {
            Picasso.get().load(mReport.getImageUrl()).into(mImageView);
            mCategoryTV.setText("Cateory: "+mReport.getCategory());
            mDescriptionTV.setText("Description: "+mReport.getDescription());
            mVCountTV.setText("Total Volunteers: "+mReport.getmVolunteers().size());
            mVDateTv.setText("Volunteer Date: "+mReport.getDate());
            mUpvotesTV.setText("Upvotes: "+mReport.getUpvotes());


            for (Comment c:
            mReport.getmComments()) {
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
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
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
