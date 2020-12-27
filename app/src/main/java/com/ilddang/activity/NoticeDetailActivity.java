package com.ilddang.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.ilddang.R;
import com.ilddang.data.NoticeListItemData;
import com.ilddang.util.Constants;
import com.ilddang.util.Util;

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
        createActionBar(getResources().getString(R.string.notice_detail));

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
        TextView viewCount = (TextView) itemView.findViewById(R.id.notice_item_view_count);
        LinearLayout viewCountLayout = (LinearLayout) itemView.findViewById(R.id.notice_item_view_count_layout);
        viewCountLayout.setVisibility(View.VISIBLE);
        title.setText(mData.title);
        content.setText(mData.content);
        people.setText(getResources().getString(R.string.pd_people_number, mData.peopleNumber));
        distance.setText(mData.distance);
        workPeriod.setText(mData.workPeriod);
        viewCount.setText(getResources().getString(R.string.view_count, 20));
        currentStatus.setText(mData.currentStatus);
        currentStatus.setBackgroundColor(getResources().getColor(Util.getStatusColor(mData.currentStatus)));

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

        if (mData.currentStatus.equals(Constants.CurrentStatus.CLOSED) || mData.currentStatus.equals(Constants.CurrentStatus.COMPLETE)) {
            TextView recruitingCompleted = (TextView) findViewById(R.id.recruiting_completed);
            recruitingCompleted.setVisibility(View.VISIBLE);
            recruitingCompleted.setText(getResources().getString(R.string.recruitment_completed));
            LinearLayout requestLayout = (LinearLayout) findViewById(R.id.request_job_layout);
            requestLayout.setVisibility(View.GONE);
        } else if (mData.currentStatus.equals(Constants.CurrentStatus.SCHEDULED)) {
            TextView cancelJob = (TextView) findViewById(R.id.cancel_job);
            cancelJob.setVisibility(View.VISIBLE);
            requestJobButton.setBackgroundColor(ContextCompat.getColor(this, R.color.base_grey));
            TextView requestJob = findViewById(R.id.request_job_text);
            requestJob.setText(getResources().getString(R.string.request_complete));
            cancelJob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NoticeDetailActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View rootView = inflater.inflate(R.layout.base_dialog_fragment, null);
                    builder.setView(rootView);

                    final ImageView caution = (ImageView) rootView.findViewById(R.id.caution_icon);
                    caution.setVisibility(View.VISIBLE);
                    final TextView title = (TextView) rootView.findViewById(R.id.dialog_title);
                    final Button no = (Button) rootView.findViewById(R.id.dialog_first_button);
                    final Button cancel = (Button) rootView.findViewById(R.id.dialog_second_button);
                    title.setText("정말로 구직을 취소하시겠어요? \n 취소 후에는 재신청이 불가합니다.");
                    no.setText(getResources().getString(R.string.no));
                    cancel.setText(getResources().getString(R.string.yes_cancel));

                    final AlertDialog dialog = builder.create();
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //TODO: change current status as canceled
                            finish();
                            dialog.dismiss();

                        }
                    });
                    dialog.show();
                }
            });
        }

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
        askPhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0123456789"));
                startActivity(intent);
            }
        });

        //TODO: get data from server
        setData("경기도 고양시 일산동구 장항2동 유국타워 지하2층 1234호",
            "10월 1일 인테리어 필름 작업자 구합니다. 위 현장사진 참고하시어 현장 작업자분들과 가족같은 점심식사도 제공하며 맛있는 벽돌입니다." +
                    "",
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