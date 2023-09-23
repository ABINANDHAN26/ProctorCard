package com.quantum.proctorcard.Student;

import static com.quantum.proctorcard.Student.ExamListActivity.semName;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.quantum.proctorcard.Model.ExamData;
import com.quantum.proctorcard.Model.LayoutData;
import com.quantum.proctorcard.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AddExamActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    Button addSubBtn, saveBtn,editBtn;
    TextView examNameTv;
    ProgressBar progressBar;

    ImageView backBtn;
    String examName;
    List<LayoutData> layoutDataList;

    ExamData examData;

    private FirebaseFirestore database;
    private String uId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam);

        findViews();
        examData = new ExamData();
        examName = getIntent().getStringExtra("ExamName");
        if(examName == null){
            finish();
        }
        examNameTv.setText(examName);
        layoutDataList = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);
        database = FirebaseFirestore.getInstance();
        uId = FirebaseAuth.getInstance().getUid();
        database.collection("Student").document(uId)
                .collection(semName).document("ExamData")
                .get().addOnSuccessListener(documentSnapshot -> {

                    if (documentSnapshot.exists()) {
                        examData = documentSnapshot.toObject(ExamData.class);
                        if (examData != null) {
                            setExamData(examData);
                        }
                    }else{
                        progressBar.setVisibility(View.GONE);
                    }
                }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                   progressBar.setVisibility(View.GONE);
                    }
                });

    }


    @Override
    protected void onResume() {
        super.onResume();
        addSubBtn.setOnClickListener(view -> {
            addSubject();
        });

        saveBtn.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            Map<String, String> markData = new HashMap<>();
            for (LayoutData data : layoutDataList) {
                String subject = data.getEt1().getText().toString();
                String mark = data.getEt2().getText().toString();
                if(subject.matches("\\s*")||mark.matches("\\s*"))
                    continue;

                markData.put(subject, mark);

            }
            examData.addData(examName, markData);
            addData(examData);
        });

        editBtn.setOnClickListener(view -> enableBtns());

        backBtn.setOnClickListener(view -> {
          onBackPressed();
        });
    }


    private void addSubject() {

        // Create a new LinearLayout for the EditText elements
        LinearLayout newLinearLayout = new LinearLayout(this);
        newLinearLayout.setOrientation(LinearLayout.HORIZONTAL); // Set horizontal orientation

        TextInputLayout subjectInLay = new TextInputLayout(this);
        TextInputLayout markInLay = new TextInputLayout(this);

        EditText subjectEt = new EditText(this);
        EditText markEt = new EditText(this);

        // Set layout parameters with weight for both EditText elements
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        );


        subjectInLay.setLayoutParams(layoutParams);
        markInLay.setLayoutParams(layoutParams);


        subjectEt.setHint("Subject");
        markEt.setHint("Mark/Grade");


        subjectInLay.addView(subjectEt);
        markInLay.addView(markEt);

        // Add EditText elements to the new LinearLayout
        newLinearLayout.addView(subjectInLay);
        newLinearLayout.addView(markInLay);

        layoutDataList.add(new LayoutData(subjectEt, markEt));
        // Add the new LinearLayout (containing the EditText elements) to the existing LinearLayout
        linearLayout.addView(newLinearLayout);

    }

    public void enableBtns(){
        for (LayoutData layoutData:layoutDataList) {
            layoutData.getEt1().setEnabled(true);
            layoutData.getEt2().setEnabled(true);
        }
    }


    private void setExamData(ExamData data) {
        Map<String, String> tempData = data.getData().get(examName);
        if (tempData == null)
            return;
        Set<String> keys = tempData.keySet();
        int i = 0;
        for (String key : keys) {
            addSubject();
            layoutDataList.get(i).getEt1().setText(key);
            layoutDataList.get(i).getEt1().setEnabled(false);
            layoutDataList.get(i).getEt2().setText(tempData.get(key));
            layoutDataList.get(i).getEt2().setEnabled(false);
            i++;
        }
        progressBar.setVisibility(View.GONE);

    }

    private void addData(ExamData data) {

        database.collection("Student").document(uId).collection(semName).document("ExamData")
                .set(data).addOnSuccessListener(unused -> finish());

    }

    private void findViews() {
        linearLayout = findViewById(R.id.subject_list_layout);
        addSubBtn = findViewById(R.id.add_subject_btn);
        saveBtn = findViewById(R.id.save_btn);
        examNameTv = findViewById(R.id.exam_name_tv);
        backBtn = findViewById(R.id.back_btn);
        editBtn = findViewById(R.id.edit_btn);
        progressBar = findViewById(R.id.progress_bar);
    }

}
