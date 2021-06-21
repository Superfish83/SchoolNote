/*
 *
 * 소프트웨어 이름: SchoolNote
 * 작성 시작일: 2021-06-13
 * 코드 작성자: 김연준,김연건,김보석,강시윤 (충남삼성고 8기)
 * Frag_Schedule.java
 *
 */

package com.example.schoolnote;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.app.Activity.RESULT_OK;

public class Frag_Schedule extends Fragment {
    public ListView lv_deadlines;
    private List<String> list_deadlines;
    private TextView tv_date;
    private TextView tv_deadlines;
    private Button btn_addnewdeadline;
    private ScheduleAdapter adapter;

    //SharedPreference 이용한 데이터 저장
    private SharedPreferences preferences;
    private SharedPreferences.Editor pref_editor;
    public List<String> deadlineList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_schedule, container, false);

        //변수 초기화
        lv_deadlines = (ListView) view.findViewById(R.id.lv_deadlines);
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        tv_deadlines = (TextView) view.findViewById(R.id.tv_deadlines);
        btn_addnewdeadline = (Button) view.findViewById(R.id.btn_addnewdeadline);

        //현재 날짜 TextView
        LocalDate ld = LocalDate.now();
        LocalTime lt = LocalTime.now();
        String sDate = "오늘은 " + Integer.toString(ld.getYear()) + "년 " +
                Integer.toString(ld.getMonthValue()) + "월 " + Integer.toString(ld.getDayOfMonth()) + "일입니다.";
        tv_date.setText(sDate);

        //할 일 목록 ListView
        adapter = new ScheduleAdapter(this);
        lv_deadlines.setAdapter(adapter);

        //preference 에서 할일 데이터 로드
        preferences = getContext().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        pref_editor = preferences.edit();

        //데드라인 데이터 로드 (Set 으로 받아오고 ArrayList 로 변환해 사용)
        Set<String> deadlineStringSet = preferences.getStringSet("deadlines", new HashSet<String>());
        deadlineList = new ArrayList<>(deadlineStringSet);
        for(String s : deadlineList){
            adapter.addItem(s);
        }


        //할 일 개수 TextView 업데이트
        UpdateDeadlineCount();


        //항목 추가 Button
        btn_addnewdeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddDeadlineActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String name = data.getStringExtra("name");
                String disc = data.getStringExtra("disc");
                int k[] = data.getIntArrayExtra("due");


                String s = "";
                s += name + "§" + disc + "§";
                s += String.valueOf(k[0]) + "-" + String.valueOf(k[1]) + "-" + String.valueOf(k[2]);
                Log.e("Frag_Schedule",s);
                deadlineList.add(s);


                Integer due[] = {0,0,0};
                for(int i = 0; i < 3; i++) due[i] = k[i];

                adapter.addItem(name,disc,due);

                UpdateDeadlines();
            }
        }
    }

    public void UpdateDeadlineCount(){
        tv_deadlines.setText("항목 수: " + Integer.toString(adapter.getCount()) + "개");
    }

    public void UpdateDeadlines(){
        Set<String> deadlineStringSet = new HashSet<String>(deadlineList);

        pref_editor.remove("deadlines");
        pref_editor.commit();
        pref_editor.putStringSet("deadlines",deadlineStringSet);
        pref_editor.commit();

        UpdateDeadlineCount();
    }
}
