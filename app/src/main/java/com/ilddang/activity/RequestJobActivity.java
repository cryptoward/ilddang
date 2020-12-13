package com.ilddang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ilddang.R;
import com.ilddang.data.NoticeListItemData;
import com.ilddang.util.Constants;

public class RequestJobActivity extends BaseActivity {
    private NoticeListItemData mData;
    private final String[] ageArray = {"나이", "20", "21", "22","23","24","25","26","27","28","29","30"};
    private final String[] genderArray = {"성별", "남자", "여자"};
    private final String[] careerArray = {"경력", "하 (1-5년)", "중 (5-10년)", "상 (10년 이상)"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_job_activity);
        setTitle(getResources().getString(R.string.request_job));

        Intent intent = getIntent();
        if (intent != null ) {
            mData = intent.getParcelableExtra("intent_request_job");
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


        Spinner ageSpinner = (Spinner) findViewById(R.id.age_spinner);
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item, ageArray
        );
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(ageAdapter);

        Spinner genderSpinner = (Spinner) findViewById(R.id.gender_spinner);
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, genderArray
        );
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        Spinner careerSpinner = (Spinner) findViewById(R.id.career_spinner);
        ArrayAdapter<String> careerAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, careerArray
        );
        careerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        careerSpinner.setAdapter(careerAdapter);

    }
}
