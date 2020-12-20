package com.ilddang.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ilddang.R;
import com.ilddang.data.NoticeListItemData;
import com.ilddang.util.Constants;
import com.ilddang.widget.NoticeItemAdapter;

import java.util.ArrayList;

public class MyJobCalendarFragment extends Fragment {
    View view;
    private CalendarView mCalendarView;

    public MyJobCalendarFragment() {
        // Required empty public constructor
    }

    public static MyJobCalendarFragment newInstance() {
        MyJobCalendarFragment tab = new MyJobCalendarFragment();
        return tab;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_job_calendar_fragment, container, false);
        mCalendarView = (CalendarView) view.findViewById(R.id.my_job_calendar_view);

        RecyclerView recyclerView = view.findViewById(R.id.my_job_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        //TODO: get data from server

        NoticeListItemData data
                = new NoticeListItemData("장인타일", "화장실 타일 시공자 4명급구",
                2, "장항2동 2km", "총 12만원", "10.01 오전 03:00 ~ 10.05 오후 18:30", Constants.CurrentStatus.CLOSED);

        NoticeListItemData data2
                = new NoticeListItemData("하늘건설", "공사 끝난 건물 필름 시공자 4명 급구",
                2, "장항2동 2km", "총 12만원", "10.01 오전 03:00 ~ 10.05 오후 18:30", Constants.CurrentStatus.COMPLETE);
        ArrayList<NoticeListItemData> list = new ArrayList<>();
        list.add(data);
        list.add(data2);

        NoticeItemAdapter adapter = new NoticeItemAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
