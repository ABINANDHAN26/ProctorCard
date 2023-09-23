package com.quantum.proctorcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.quantum.proctorcard.Model.ExamData;
import com.quantum.proctorcard.Model.StudentData;
import com.quantum.proctorcard.Student.HomeActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadingScreen extends AppCompatActivity {

     StudentData studentData;
    private FirebaseFirestore database;
    private FirebaseFirestore database1;
    private FirebaseFirestore database2;
    static ArrayList<ExamData> examDataArrayList = new ArrayList<>();

    ArrayList<Map<String, Object>> attendanceDataArrayList = new ArrayList<>();
    static Map<String, Object> attendanceData = new HashMap<>();

    String uId;

    String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        FirebaseApp.initializeApp(this);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            downloadData();
//            openHome();
        } else {
            startActivity(new Intent(LoadingScreen.this, LoginActivity.class));
            finish();
        }

    }


    private void downloadData() {

        database = FirebaseFirestore.getInstance();

        uId = FirebaseAuth.getInstance().getUid();

        database.collection("Student").document(uId)
                .collection("PersonalData")
                .document("1")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {

                            studentData = documentSnapshot.toObject(StudentData.class);
                            openHome();

                        }else{
                            openHome();
                        }

                    }
                });
    }


    private void openHome() {
        Intent intent = new Intent(LoadingScreen.this, HomeActivity.class);
        intent.putExtra("StudentData", studentData);
        intent.putExtra("ExamData", examDataArrayList);
        //  intent.putExtra("AttendanceData", new AttendanceData(attendanceData));
        startActivity(intent);
        finish();

    }


}