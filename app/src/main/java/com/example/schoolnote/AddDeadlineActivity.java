/*
 *
 * 소프트웨어 이름: SchoolNote
 * 작성 시작일: 2021-06-13
 * 코드 작성자: 김연준,김연건,김보석,강시윤 (충남삼성고 8기)
 * AddDeadlineActivity.java
 *
 */

package com.example.schoolnote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddDeadlineActivity extends AppCompatActivity {

    private EditText et_name; //스케줄 이름
    private EditText et_disc; //스케줄 설명

    private Button btn_date; //날짜 선택 버튼
    private TextView tv_date; //날짜 확인

    private Button btn_add; //최종 추가 버튼

    private int due[] = {-1,0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deadline);

        et_name = (EditText)findViewById(R.id.et_name);
        et_disc = (EditText)findViewById(R.id.et_disc);

        btn_add = (Button)findViewById(R.id.btn_add);
        tv_date = (TextView)findViewById(R.id.tv_date);

        btn_date = (Button)findViewById(R.id.btn_setdate);

        btn_add.setEnabled(false);


        //캘린더 창 날짜 선택 기능
        final Calendar cal = Calendar.getInstance();
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(AddDeadlineActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        due[0] = year;
                        due[1] = month+1;
                        due[2] = date;

                        tv_date.setText(String.format("기한: %d-%d-%d", due[0],due[1],due[2]));

                        if(et_name.getText().length() >= 1){
                            btn_add.setEnabled(true);
                        }
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dialog.show();

            }
        });

        //항목 이름 수정
        et_name.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String t = et_name.getText().toString();

                if(t.length() == 0 || due[0]==-1){
                    btn_add.setEnabled(false);
                }
                else{
                    if(!btn_add.isEnabled())
                        btn_add.setEnabled(true);

                    if(t.length() > 30)
                        et_name.setText(t.substring(0,30));
                }

                return false;
            }
        });

        //항목 설명 수정
        et_disc.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String t = et_disc.getText().toString();

                if(t.length() > 500)
                    et_disc.setText(t.substring(0,500));
                return false;
            }
        });

        //항목 추가 버튼
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent output = new Intent();
                output.putExtra("name", et_name.getText().toString());
                output.putExtra("disc", et_disc.getText().toString());
                output.putExtra("due", due);

                setResult(RESULT_OK, output);
                finish();
            }
        });
    }
}