package com.back.vom.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.back.vom.R;
import com.back.vom.ReportActivity;
import com.back.vom.models.Report;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * The type Notifications adapter.
 */
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationAdapterViewHolder> {

    /**
     * Notification list - data
     */
    private List<Report> mData;
    private Activity mActivity;

    /**
     * Instantiates a new Notifications adapter.
     *
     * @param mReports the m data
     */
    public LocationAdapter(List<Report> mReports,Activity activity) {
        mActivity = activity;
        this.mData = mReports;
    }

    /**
     * This method will be invoked when notifications is to be displayed.
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public LocationAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_your_reports, parent, false);
        return new LocationAdapterViewHolder(view);
    }

    /**
     * This method will be invoked to bind all the data to recycler view.
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(LocationAdapterViewHolder holder, int position) {
        holder.mTitle.setText(mData.get(position).getTitle());
        holder.itemView.setTag(mData.get(position));
    }

    /**
     * The getItemCount method.
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * The type Notification view holder.
     */
    class LocationAdapterViewHolder extends RecyclerView.ViewHolder {

        /**
         * The M title and Message.
         */
        TextView mTitle, mDescription;
        ImageView mImageView;

        /**
         * Instantiates a new Notification view holder.
         *
         * @param itemView the item view
         */
        LocationAdapterViewHolder(final View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mDescription = itemView.findViewById(R.id.description);

            mImageView = itemView.findViewById(R.id.img_view_location);

            final Report report = (Report) itemView.getTag();
            Picasso
                    .get()
                    .load(report.getImageUrl())
                    .centerCrop()
                    .into(mImageView);

            itemView.findViewById(R.id.upvote_bt).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            itemView.findViewById(R.id.view_bt).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mActivity, ReportActivity.class);
                    i.putExtra("uid",report.getUid());
                    mActivity.startActivity(i);
                }
            });



        }




    }
}
