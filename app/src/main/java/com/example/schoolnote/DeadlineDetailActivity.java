package com.example.schoolnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DeadlineDetailActivity extends AppCompatActivity {

    private TextView tv_deadline_title;
    private TextView tv_deadline_due;
    private TextView tv_deadline_details;
    private Button btn_ok;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deadline_detail);

        tv_deadline_title = (TextView)findViewById(R.id.tv_deadline_title);
        tv_deadline_due = (TextView)findViewById(R.id.tv_deadline_due);
        tv_deadline_details = (TextView)findViewById(R.id.tv_deadline_details);
        btn_ok = (Button)findViewById(R.id.btn_ok);

        Intent intent = getIntent();
        String info = intent.getStringExtra("info");

        String infoArr[] = info.split("§");
        tv_deadline_title.setText(infoArr[0]);
        tv_deadline_due.setText(infoArr[2] + "까지");
        tv_deadline_details.setText(infoArr[1]);


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}