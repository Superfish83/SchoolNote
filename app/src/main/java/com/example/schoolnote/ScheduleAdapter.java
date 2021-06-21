package com.example.schoolnote;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolnote.ListItemSchedule;

import java.util.ArrayList;

public class ScheduleAdapter extends BaseAdapter {
    // 어댑터를 호출한 fragment 저장 (Frag_Schedule.java 에 있는 메소드 접근용)
    private final Frag_Schedule fragment;


    // Adapter 에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListItemSchedule> listViewItemList = new ArrayList<ListItemSchedule>();

    // Adapter 에 사용되는 데이터의 개수 반환
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }
    
    public ScheduleAdapter(Frag_Schedule fr){
        this.fragment = fr;
    }

    // position 에 위치한 데이터를 화면에 출력하는데 사용될 View 반환
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "list_item_schedule" Layout 을 inflate 함
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_schedule, parent, false);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title) ;
        TextView tvDue = (TextView) convertView.findViewById(R.id.tv_duedate) ;

        // Data Set(listViewItemList)에서 position 에 위치한 데이터 참조 획득
        ListItemSchedule listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        tvTitle.setText(listViewItem.getTitle());
        tvDue.setText(listViewItem.getDueStr());

        // 할일 완료 버튼
        Button btn_complete = convertView.findViewById(R.id.btn_complete);
        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = getIndex((String)tvTitle.getText());
                listViewItemList.remove(i); //항목 찾아서 삭제
                notifyDataSetChanged();
                fragment.deadlineList.remove(i);
                fragment.UpdateDeadlines();

                String s = "'" + tvTitle.getText() + "' 완료!"; //Toast 메세지 띄우기
                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
            }
        });

        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fragment.getActivity(), DeadlineDetailActivity.class);
                int i = getIndex((String)tvTitle.getText());
                intent.putExtra("info", fragment.deadlineList.get(i));

                fragment.startActivity(intent);
            }
        });

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 반환
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 반환
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    public void addItem(String title, String disc, Integer[] due) {
        while(getIndex(title) > -1){
            title += "_";
        }

        ListItemSchedule item = new ListItemSchedule();

        item.setItem(title,disc,due);

        listViewItemList.add(item);
    }

    public void addItem(String s) {
        String k[] = s.split("§"); //문자 '§' 이용해 제목, 설명, 기한 데이터 분리
        String title = k[0];
        String disc = k[1];
        Integer due[] = {0,0,0};

        String ds[] = k[2].split("-");
        for (int i = 0; i < 3; i++) {
            due[i] = Integer.parseInt(ds[i]);
        }

        addItem(title,disc,due);
    }

    private int getIndex(String title){ //title 로 listViewItemList 안에 있는 객체를 검색해 그 인덱스 반환
        for(int i = 0; i < listViewItemList.size(); i++){
            //Log.e("ScheduleAdapter",listViewItemList.get(i).getTitle());
            if(listViewItemList.get(i).getTitle().compareTo(title) == 0){
                return i;
            }
        }
        return -1;
    }
}
