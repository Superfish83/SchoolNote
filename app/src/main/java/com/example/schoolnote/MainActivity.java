/*
 *
 * 소프트웨어 이름: SchoolNote
 * 작성 시작일: 2021-06-13
 * 코드 작성자: 김연준,김연건,김보석,강시윤 (충남삼성고 8기)
 * MainActivity.java
 *
 */

package com.example.schoolnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //이 두 변수는 Drawer(메뉴) 관리에 쓰임
    private DrawerLayout drawerLayout;
    private View drawerView;

    //이 두 변수는 메뉴를 통한 Fragment 전환 관리에 쓰임
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //drawerLayout, drawerView, fragmentManager 초기화
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerView = (View)findViewById(R.id.drawer);
        fragmentManager = getSupportFragmentManager();


        //메뉴 열기 버튼
        Button btn_open = (Button)findViewById(R.id.btn_open);
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //메뉴에 들어갈 lvDrawer(ListView)
                ListView lvDrawer = (ListView)findViewById(R.id.drawer_list);

                //lvDrawer 에 동기화할 list (String 형 List)
                List<String> list = new ArrayList<>();
                list.add("나의 목표");
                list.add("학교생활 스케줄");
                list.add("독서/탐구 보고서");
                list.add("설정");

                //adapter 로 lvDrawer 와 list 동기화
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                lvDrawer.setAdapter(adapter);
                lvDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //메뉴 리스트 아이템 클릭 시

                        //fragment 전환 준비
                        fragmentTransaction = fragmentManager.beginTransaction();
                        switch(position){
                            case 0: //'나의 목표' fragment 로 전환
                                Frag_Goals fr0 = new Frag_Goals();
                                fragmentTransaction.replace(R.id.frameLayout, fr0).commitAllowingStateLoss();
                                break;
                            case 1: //'학교생활 스케줄' fragment 로 전환
                                Frag_Schedule fr1 = new Frag_Schedule();
                                fragmentTransaction.replace(R.id.frameLayout, fr1).commitAllowingStateLoss();
                                break;
                            case 2: //'독서/심화 보고서' fragment 로 전환
                                break;
                            case 3: //'' fragment 로 전환
                                break;
                        }
                        drawerLayout.closeDrawers(); //메뉴 닫기
                    }
                });

                drawerLayout.openDrawer(drawerView);
            }
        });


        //메뉴 닫기 버튼
        Button btn_close = (Button)findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });


        //메뉴 창 처리
        drawerLayout.setDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    //메뉴 창에서 각종 입력 처리
    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };
}