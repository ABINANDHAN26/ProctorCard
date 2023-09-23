package com.quantum.proctorcard.Student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.quantum.proctorcard.LoginActivity;
import com.quantum.proctorcard.Model.StudentData;
import com.quantum.proctorcard.PdfGeneratorService;
import com.quantum.proctorcard.R;

public class HomeActivity extends AppCompatActivity {

    private Button personalBtn, academicBtn,logoutBtn;

    private ImageView downloadBtn;

    StudentData studentData;

    Bitmap bitmap;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        findViews();
        studentData = getIntent().getParcelableExtra("StudentData");

    }

    @Override
    protected void onResume() {
        super.onResume();
        personalBtn.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, PersonalActivity.class));
        });

        academicBtn.setOnClickListener(view -> {

            startActivity(new Intent(HomeActivity.this, SemListActivity.class));
        });

        logoutBtn.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        });

        downloadBtn.setOnClickListener(view -> {
            checkDiskPermission();

        });
    }



    private void generatePdf() {
        progressBar.setVisibility(View.VISIBLE);
        if(studentData == null){
            studentData = new StudentData();
        }
        PdfGeneratorService pdfGeneratorService = new PdfGeneratorService(this,progressBar);
        pdfGeneratorService.getData("Semester 1");
        //pdfGeneratorService.generatePdf(HomeActivity.this, studentData, examDataArrayList, null);
    }


    private void downloadImage(String uId) {
        // Construct the path to the image in Firebase Storage
        String fileName = uId + "/" + "profile" + ".jpg";
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(fileName);

        // Download the image
        storageReference.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
            // Decode the byte array into a Bitmap (if it's an image)

            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);


        }).addOnFailureListener(exception -> {
            // Handle any errors that may occur during the download
            Toast.makeText(this, "Failed to download image", Toast.LENGTH_SHORT).show();
        });
    }

    private void checkDiskPermission ()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, 1);
        }
        else
        {
            //Toast.makeText(this, "Has Permissions" , Toast.LENGTH_LONG).show();
            generatePdf();
        }
    }


    private void findViews() {
        personalBtn = findViewById(R.id.personal_btn);
        academicBtn = findViewById(R.id.academic_btn);
        downloadBtn = findViewById(R.id.download_btn);
        logoutBtn = findViewById(R.id.logout_btn);
        progressBar = findViewById(R.id.progress_bar);
    }
}