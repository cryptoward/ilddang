package com.ilddang.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ilddang.R;
import com.ilddang.data.NoticeListItemData;
import com.ilddang.util.Constants;

import java.util.ArrayList;

public class NoticeListItemAdapter extends RecyclerView.Adapter<NoticeListItemAdapter.ViewHolder> {
    private ArrayList<NoticeListItemData> mData;
    private Context mContext;

    public NoticeListItemAdapter(Context context, ArrayList<NoticeListItemData> list) {
        mContext = context;
        mData = list ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.notice_list_item, parent, false);
        NoticeListItemAdapter.ViewHolder vh = new NoticeListItemAdapter.ViewHolder(view) ;
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
        if (data.currentStatus == Constants.CurrentStatus.RECRUITING) {
            holder.currentStatus.setText(mContext.getResources().getString(R.string.recruiting));
            holder.currentStatus.setBackgroundColor(mContext.getResources().getColor(R.color.recruiting_color));
        } else if (data.currentStatus == Constants.CurrentStatus.CLOSED) {
            holder.currentStatus.setText(mContext.getResources().getString(R.string.closed));
            holder.currentStatus.setBackgroundColor(mContext.getResources().getColor(R.color.base_grey));
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

        ViewHolder(View itemView) {
            super(itemView) ;
            title = itemView.findViewById(R.id.notice_list_item_title);
            content = itemView.findViewById(R.id.notice_list_item_content);
            peopleNumber = itemView.findViewById(R.id.notice_list_item_people_number);
            distance = itemView.findViewById(R.id.notice_list_item_distance);
            payment = itemView.findViewById(R.id.notice_list_item_payment);
            workPeriod = itemView.findViewById(R.id.notice_list_item_work_period);
            currentStatus = itemView.findViewById(R.id.notice_list_item_current_status);

        }
    }


}