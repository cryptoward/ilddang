package com.ilddang.job.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ilddang.job.R;
import com.ilddang.job.data.ChatData;
import com.ilddang.job.util.Constants;

import java.util.ArrayList;

public class ChatItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ChatData> mData;
    private Context mContext;

    public ChatItemAdapter(Context context, ArrayList<ChatData> list) {
        mContext = context;
        mData = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (viewType == Constants.ChatType.MY_CHAT) {
            view = inflater.inflate(R.layout.chat_item_my_chat, parent, false);
            return new MyChatViewHolder(view);
        } else if (viewType == Constants.ChatType.YOUR_CHAT) {
            view = inflater.inflate(R.layout.chat_item_your_chat, parent, false);
            return new YourChatViewHolder(view);
        } else  {
            view = inflater.inflate(R.layout.chat_item_description, parent, false);
            return new DescriptionViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyChatViewHolder) {
            ((MyChatViewHolder) holder).content.setText(mData.get(position).content);
            ((MyChatViewHolder) holder).time.setText(mData.get(position).time);
        } else if(holder instanceof YourChatViewHolder) {
            ((YourChatViewHolder) holder).content.setText(mData.get(position).content);
            ((YourChatViewHolder) holder).time.setText(mData.get(position).time);
        } else {
            ((DescriptionViewHolder) holder).content.setText(mData.get(position).content);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class MyChatViewHolder extends RecyclerView.ViewHolder{
        TextView content;
        TextView time;

        MyChatViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.chat_time);
            content = itemView.findViewById(R.id.content);
        }
    }

    public class YourChatViewHolder extends RecyclerView.ViewHolder{
        TextView content;
        TextView time;

        YourChatViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
            time = itemView.findViewById(R.id.chat_time);
        }
    }

    public class DescriptionViewHolder extends RecyclerView.ViewHolder{
        TextView content;

        DescriptionViewHolder(View itemView) {
            super(itemView);

            content = itemView.findViewById(R.id.content);
        }
    }
}
