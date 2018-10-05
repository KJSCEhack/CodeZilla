package com.back.vom.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.back.vom.R;

import java.util.List;

/**
 * The type Notifications adapter.
 */
public class YourReportsAdapter extends RecyclerView.Adapter<YourReportsAdapter.YourReportsAdapterViewHolder> {

    /**
     * Notification list - data
     */
    private List<String> mData;

    /**
     * Instantiates a new Notifications adapter.
     *
     * @param mNotificationData the m data
     */
    public YourReportsAdapter(List<String> mNotificationData) {
        this.mData = mNotificationData;
    }

    /**
     * This method will be invoked when notifications is to be displayed.
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public YourReportsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_your_reports, parent, false);
        return new YourReportsAdapterViewHolder(view);
    }

    /**
     * This method will be invoked to bind all the data to recycler view.
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(YourReportsAdapterViewHolder holder, int position) {
        holder.mTitle.setText(mData.get(position));
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
    class YourReportsAdapterViewHolder extends RecyclerView.ViewHolder {

        /**
         * The M title and Message.
         */
        TextView mTitle, mDescription;

        /**
         * Instantiates a new Notification view holder.
         *
         * @param itemView the item view
         */
        YourReportsAdapterViewHolder(View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.title);
            mDescription = itemView.findViewById(R.id.description);
        }
    }
}
