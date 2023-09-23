package com.quantum.proctorcard.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.quantum.proctorcard.Model.ExamData;
import com.quantum.proctorcard.Model.LayoutData;
import com.quantum.proctorcard.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExamListActivity extends AppCompatActivity{

    private LinearLayout examFieldsContainer;
    private Button addAbsentBtn, saveBtn;

    TextView ia1Tv, ia2Tv, ia3Tv, finalTv;

    LinearLayout attendanceLayout;

    List<LayoutData> attendanceLayoutDataList;

    Map<String,String> attendanceData;

    static String semName;

    FirebaseFirestore database;
    String uId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_list);

        attendanceLayoutDataList = new ArrayList<>();

        examFieldsContainer = findViewById(R.id.exam_list_layout);
        addAbsentBtn = findViewById(R.id.add_attendance_btn);
        attendanceLayout = findViewById(R.id.attendance_list_layout);
        attendanceData = new HashMap<>();
        ia1Tv = findViewById(R.id.ia_1_tv);
        ia2Tv = findViewById(R.id.ia_2_tv);
        ia3Tv = findViewById(R.id.ia_3_tv);
        finalTv = findViewById(R.id.final_tv);
        saveBtn = findViewById(R.id.save_btn);

        semName = getIntent().getStringExtra("SemName");

        database = FirebaseFirestore.getInstance();

        uId = FirebaseAuth.getInstance().getUid();
        database = FirebaseFirestore.getInstance();
        uId = FirebaseAuth.getInstance().getUid();
        database.collection("Student").document(uId)
                .collection(semName).document("AttendanceData")
                .get().addOnSuccessListener(documentSnapshot -> {
                    if(documentSnapshot.exists()){
                        Map<String, Object> data = documentSnapshot.getData();
                        if (data != null) {
                            setAttendanceData(data);
                        }
                    }
                });

        addAbsentBtn.setOnClickListener(view -> {
            addAbsentField();
        });

        saveBtn.setOnClickListener(view -> {

            for (LayoutData data : attendanceLayoutDataList) {
                EditText dateEt = data.getEt1();
                EditText reasonEt = data.getEt2();
                String date = dateEt.getText().toString();
                String reason = reasonEt.getText().toString();
                if (!date.isEmpty() && !reason.isEmpty())
                    attendanceData.put(date,reason);
            }
            addData(attendanceData);
            finish();

        });

        ia1Tv.setOnClickListener(view -> {openAct("IA1");});
        ia2Tv.setOnClickListener(view -> {openAct("IA2");});
        ia3Tv.setOnClickListener(view -> {openAct("IA3");});
        finalTv.setOnClickListener(view -> {openAct("FINAL");});
    }

    private void addAbsentField() {
        // Create a new LinearLayout for the EditText elements
        LinearLayout newLinearLayout = new LinearLayout(this);
        newLinearLayout.setOrientation(LinearLayout.HORIZONTAL); // Set horizontal orientation

        TextInputLayout dateInLay = new TextInputLayout(this);
        TextInputLayout reasonInLay = new TextInputLayout(this);

        EditText dateEt = new EditText(this);
        EditText reasonEt = new EditText(this);

        // Set layout parameters with weight for both EditText elements
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        );


        dateInLay.setLayoutParams(layoutParams);
        reasonInLay.setLayoutParams(layoutParams);

        dateEt.setInputType(InputType.TYPE_CLASS_DATETIME);
        reasonEt.setInputType(InputType.TYPE_CLASS_TEXT);

        dateEt.setHint("Date");
        reasonEt.setHint("Reason");


        dateInLay.addView(dateEt);
        reasonInLay.addView(reasonEt);

        // Add EditText elements to the new LinearLayout
        newLinearLayout.addView(dateInLay);
        newLinearLayout.addView(reasonInLay);


        attendanceLayoutDataList.add(new LayoutData(dateEt, reasonEt));


        // Add the new LinearLayout (containing the EditText elements) to the existing LinearLayout
        attendanceLayout.addView(newLinearLayout);
    }


    public void openAct(String examName) {

        Intent intent = new Intent(ExamListActivity.this, AddExamActivity.class);
        intent.putExtra("ExamName", examName);
        startActivity(intent);

    }



    private void addData(Map<String,String> data){

        database.collection("Student").document(uId).collection(semName).document("AttendanceData")
                .set(data);
    }

    public void setAttendanceData(Map<String,Object> data){
        Set<String> keys = data.keySet();
        int i=0;
        for(String key:keys){
            addAbsentField();
            attendanceLayoutDataList.get(i).getEt1().setText(key);
            attendanceLayoutDataList.get(i).getEt2().setText(data.get(key).toString());
            i++;
        }

    }


}
