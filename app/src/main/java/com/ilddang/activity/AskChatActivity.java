package com.ilddang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ilddang.R;
import com.ilddang.data.ChatData;
import com.ilddang.data.NoticeListItemData;
import com.ilddang.util.Constants;
import com.ilddang.widget.ChatItemAdapter;

import java.util.ArrayList;

public class AskChatActivity extends BaseActivity {
    private NoticeListItemData mData;
    private ArrayList<ChatData> mChatList = new ArrayList<>();
    private ChatItemAdapter mAdapter;
    private EditText mSendMessage;
    private RecyclerView mChatView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ask_chat_activity);
        setTitle(getResources().getString(R.string.chat));

        Intent intent = getIntent();
        if (intent != null ) {
            mData = intent.getParcelableExtra("intent_ask_chat");
        }

        View itemView = (View) findViewById(R.id.notice_item);

        TextView title = (TextView) itemView.findViewById(R.id.notice_list_item_title);
        TextView content = (TextView) itemView.findViewById(R.id.notice_list_item_content);
        TextView people = (TextView) itemView.findViewById(R.id.notice_list_item_people_number);
        TextView distance = (TextView) itemView.findViewById(R.id.notice_list_item_distance);
        TextView workPeriod = (TextView) itemView.findViewById(R.id.notice_list_item_work_period);
        TextView currentStatus = (TextView) itemView.findViewById(R.id.notice_list_item_current_status);
        title.setText(mData.title);
        content.setText(mData.content);
        people.setText(getResources().getString(R.string.pd_people_number, mData.peopleNumber));
        distance.setText(mData.distance);
        workPeriod.setText(mData.workPeriod);
        if (mData.currentStatus == Constants.CurrentStatus.RECRUITING) {
            currentStatus.setText(getResources().getString(R.string.recruiting));
            currentStatus.setBackgroundColor(getResources().getColor(R.color.base_green));
        } else if (mData.currentStatus == Constants.CurrentStatus.CLOSED) {
            currentStatus.setText(getResources().getString(R.string.closed));
            currentStatus.setBackgroundColor(getResources().getColor(R.color.base_grey));
        }
        LinearLayout requestJobButton = itemView.findViewById(R.id.request_job_button_in_item);
        requestJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mSendMessage = findViewById(R.id.send_message);

        RelativeLayout sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSendMessage.getText() != null && !mSendMessage.getText().toString().isEmpty()) {
                    mChatList.add(new ChatData(Constants.ChatType.MY_CHAT, mSendMessage.getText().toString(), "11:47"));
                    mAdapter.notifyDataSetChanged();
                    mChatView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                    mSendMessage.setText("");
                }
            }
        });

        mChatView = findViewById(R.id.chat_view);

        LinearLayoutManager manager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

        mChatView.setLayoutManager(manager);


        mChatList.add(new ChatData(Constants.ChatType.MY_CHAT, "일자리 구하려고 하는데요.", "22:30"));
        mChatList.add(new ChatData(Constants.ChatType.YOUR_CHAT, "네 맞습니다. 일당은 15만원이에요.", "22:38"));
        mChatList.add(new ChatData(Constants.ChatType.MY_CHAT, "네 생각해 보겠습니다.", "22:40"));
        mAdapter = new ChatItemAdapter(this, mChatList);

        mChatView.setAdapter(mAdapter);
    }
}
