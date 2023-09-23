package com.quantum.proctorcard.Student;



import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quantum.proctorcard.R;

public class SemListActivity extends AppCompatActivity {

    private LinearLayout semesterFieldsContainer;
    private int semNo = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem_list);

        semesterFieldsContainer = findViewById(R.id.sem_list_layout);
        semNo = 0;
        for (int i = 0; i <= 7; i++)
            addSemField();


    }

    private void addSemField() {

        semNo++;
        TextView tv = new TextView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                150);
        int verticalMargin = 5;
        int horizontalMargin = 5;
        layoutParams.setMargins(horizontalMargin, verticalMargin, horizontalMargin, verticalMargin);
        tv.setPadding(5, 5, 5, 5);
        tv.setGravity(Gravity.CENTER);
        tv.setText("Semester " + semNo);
        tv.setLayoutParams(layoutParams);
        tv.setTextSize(30);

        tv.setBackground(ContextCompat.getDrawable(this, R.drawable.border_bg));
        tv.setOnClickListener(view -> {

            String text = tv.getText().toString();
            Intent intent = new Intent(SemListActivity.this, ExamListActivity.class);
            intent.putExtra("SemName", text);
            startActivity(intent);
        });
        semesterFieldsContainer.addView(tv);

    }
}