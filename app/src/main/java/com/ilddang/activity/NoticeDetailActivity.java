package com.ilddang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ilddang.R;
import com.ilddang.data.NoticeListItemData;
import com.ilddang.util.Constants;

public class NoticeDetailActivity extends BaseActivity {
    private NoticeListItemData mData;
    private TextView mAddress;
    private TextView mDescription;
    private ImageView mFirstImage;
    private ImageView mSecondImage;
    private ImageView mThirdImage;
    private TextView mViewMoreImageNum;
    private TextView mCareer;
    private TextView mWage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_detail_activity);
        setTitle(getResources().getString(R.string.notice_detail));

        Intent intent = getIntent();
        if (intent != null ) {
            mData = intent.getParcelableExtra("intent_notice_detail");
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

        mCareer = (TextView) findViewById(R.id.career);
        mWage = (TextView) findViewById(R.id.wage);

        mAddress = (TextView) findViewById(R.id.address);
        ImageView firstImage = (ImageView) findViewById(R.id.first_image);
        ImageView secondImage = (ImageView) findViewById(R.id.second_image);
        ImageView thirdImage = (ImageView) findViewById(R.id.third_image);
        mViewMoreImageNum = (TextView) findViewById(R.id.view_more_image_num);

        mDescription = (TextView) findViewById(R.id.text_description);

        LinearLayout requestJobButton = (LinearLayout) findViewById(R.id.request_job_button);
        LinearLayout askChatButton = (LinearLayout) findViewById(R.id.ask_chat_button);
        LinearLayout askPhoneButton = (LinearLayout) findViewById(R.id.ask_phone_button);
        requestJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoticeDetailActivity.this, RequestJobActivity.class);
                intent.putExtra("intent_request_job", mData);
                startActivity(intent);
            }
        });
        askChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoticeDetailActivity.this, AskChatActivity.class);
                intent.putExtra("intent_ask_chat", mData);
                startActivity(intent);
            }
        });

        //TODO: get data from server
        setData("경기도 고양시 일산동구 장항2동 유국타워 지하2층 1234호",
            "10월 1일 인테리어 필름 작업자 구합니다. 위 현장사진 참고하시어 현장 작업자분들과 가족같은 점심식사도 제공하며 맛있는 벽돌입니다." +
                    "\n 1 line \n 2 line \n 3 line \n 4 line \n 5 line \n 6 line \n 7 line \n 8 line \n 9 line \n 10 line",
                3, "하 (1~5년)", "일 15만원");
    }

    private void setData(String address, String description, int imageNum, String career, String wage) {
        mAddress.setText(address);
        mDescription.setText(description);
        mViewMoreImageNum.setText(getResources().getString(R.string.view_more_image_num, imageNum));
        mCareer.setText(career);
        mWage.setText(wage);
    }
}