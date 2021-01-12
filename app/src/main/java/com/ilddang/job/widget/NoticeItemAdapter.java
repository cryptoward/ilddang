package com.ilddang.job.widget;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ilddang.job.R;
import com.ilddang.job.activity.NoticeDetailActivity;
import com.ilddang.job.data.NoticeListItemData;
import com.ilddang.job.util.Constants;
import com.ilddang.job.util.Util;

import java.util.ArrayList;

public class NoticeItemAdapter extends RecyclerView.Adapter<NoticeItemAdapter.ViewHolder> {
    private ArrayList<NoticeListItemData> mData;
    private Context mContext;
    private boolean mIsMain;

    public NoticeItemAdapter(Context context, ArrayList<NoticeListItemData> list, boolean isMain) {
        mContext = context;
        mData = list;
        mIsMain = isMain;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.notice_list_item, parent, false);
        NoticeItemAdapter.ViewHolder vh = new NoticeItemAdapter.ViewHolder(view) ;
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NoticeListItemData data = mData.get(position);
        holder.title.setText(data.title);
        holder.content.setText(data.content);
        holder.peopleNumber.setText(mContext.getResources().getString(R.string.pd_people_number, data.peopleNumber));
        holder.distance.setText(data.distance);
        holder.payment.setText(data.payment);
        holder.workPeriod.setText(data.workPeriod);
        holder.currentStatus.setText(data.currentStatus);
        holder.currentStatus.setBackgroundColor(mContext.getResources().getColor(Util.getStatusColor(data.currentStatus)));
        if (mIsMain && data.currentStatus.equals(Constants.CurrentStatus.SCHEDULED)) {
            holder.currentStatus.setText(Constants.CurrentStatus.RECRUITING);
            holder.alreadyAplied.setVisibility(View.VISIBLE);
            holder.itemView.setClickable(false);
        } else {
            holder.newItem.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;
        TextView peopleNumber;
        TextView distance;
        TextView payment;
        TextView workPeriod;
        TextView currentStatus;
        RelativeLayout alreadyAplied;
        TextView newItem;

        ViewHolder(View itemView) {
            super(itemView) ;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, NoticeDetailActivity.class);
                    intent.putExtra("intent_notice_detail", mData.get(getAdapterPosition()));
                    mContext.startActivity(intent);
                }
            });
            title = itemView.findViewById(R.id.notice_list_item_title);
            content = itemView.findViewById(R.id.notice_list_item_content);
            peopleNumber = itemView.findViewById(R.id.notice_list_item_people_number);
            distance = itemView.findViewById(R.id.notice_list_item_distance);
            payment = itemView.findViewById(R.id.notice_list_item_wage);
            workPeriod = itemView.findViewById(R.id.notice_list_item_work_period);
            currentStatus = itemView.findViewById(R.id.notice_list_item_current_status);
            alreadyAplied = itemView.findViewById(R.id.already_applied_layout);
            newItem = itemView.findViewById(R.id.notice_item_new);
        }
    }


}