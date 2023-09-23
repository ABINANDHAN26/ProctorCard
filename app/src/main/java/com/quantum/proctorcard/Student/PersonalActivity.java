package com.quantum.proctorcard.Student;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.quantum.proctorcard.Model.StudentData;
import com.quantum.proctorcard.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class PersonalActivity extends AppCompatActivity {

    private EditText nameEt, rollEt, regEt, degreeEt, branchEt, proctorNameEt, yearEt, dobEt, aadharEt;
    private EditText categoryEt, _12MarkEt, cutOffEt, genderEt, mTongueEt, bloodEt, religionEt, communityEt, emailEt;
    private EditText hostelerEt, transModeEt, emergencyNameEt, emergencyContactEt, hobbiesEt;
    private EditText pAddressEt, cAddressEt;

    private ImageView studentIv;

    private Button addBtn, editBtn;

    private ImageButton backBtn;

    private ProgressBar progressBar;

    private FirebaseFirestore database;
    private String uId;

    private Uri uri;

    String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_personal);
        findViews();
        database = FirebaseFirestore.getInstance();
        uId = FirebaseAuth.getInstance().getUid();

        database.collection("Student").document(uId)
                .collection("PersonalData")
                .document("1")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            StudentData studentData = documentSnapshot.toObject(StudentData.class);
                            if (studentData != null) {
                                downloadImage(uId);
                                disableET();
                                setData(studentData);
                                addBtn.setVisibility(View.GONE);
                            } else {
                                enableEt();
                            }
                        } else {
                            enableEt();
                        }
                    }
                });


    }

    @Override
    protected void onResume() {
        super.onResume();

        addBtn.setOnClickListener(view -> {

            progressBar.setVisibility(View.VISIBLE);
            disableET();
            addBtn.setEnabled(false);
            StudentData studentData = getData();
            addData(studentData);
            editBtn.setVisibility(View.VISIBLE);
        });

        editBtn.setOnClickListener(view -> {
            editBtn.setVisibility(View.GONE);
            enableEt();
            addBtn.setVisibility(View.VISIBLE);
        });


        studentIv.setOnClickListener(view -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            studentPhoto.launch(galleryIntent);
        });

        backBtn.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    private final ActivityResultLauncher<Intent> studentPhoto = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            uri = data.getData();
                            Glide.with(PersonalActivity.this)
                                    .load(uri)
                                    .into(studentIv);
                        }
                    }
                }
            }
    );

    private void disableET() {
        studentIv.setEnabled(false);
        nameEt.setEnabled(false);
        rollEt.setEnabled(false);
        regEt.setEnabled(false);
        degreeEt.setEnabled(false);
        branchEt.setEnabled(false);
        proctorNameEt.setEnabled(false);
        yearEt.setEnabled(false);
        dobEt.setEnabled(false);
        aadharEt.setEnabled(false);
        categoryEt.setEnabled(false);
        _12MarkEt.setEnabled(false);
        cutOffEt.setEnabled(false);
        genderEt.setEnabled(false);
        mTongueEt.setEnabled(false);
        bloodEt.setEnabled(false);
        religionEt.setEnabled(false);
        communityEt.setEnabled(false);
        emailEt.setEnabled(false);
        hostelerEt.setEnabled(false);
        transModeEt.setEnabled(false);
        emergencyNameEt.setEnabled(false);
        emergencyContactEt.setEnabled(false);
        hobbiesEt.setEnabled(false);
        pAddressEt.setEnabled(false);
        cAddressEt.setEnabled(false);
    }

    private void setData(StudentData studentData) {
        nameEt.setText(studentData.getStudentName());
        rollEt.setText(studentData.getRollNo());
        regEt.setText(studentData.getRegNo());
        degreeEt.setText(studentData.getDegree());
        branchEt.setText(studentData.getBranch());
        proctorNameEt.setText(studentData.getProctorName());
        yearEt.setText(studentData.getYearOfStudy());
        dobEt.setText(studentData.getDob());
        aadharEt.setText(studentData.getAadharNo());
        categoryEt.setText(studentData.getCategory());
        _12MarkEt.setText(studentData.get_12Mark());
        cutOffEt.setText(studentData.getCutOff());
        genderEt.setText(studentData.getGender());
        mTongueEt.setText(studentData.getMotherTongue());
        bloodEt.setText(studentData.getBlood());
        religionEt.setText(studentData.getReligion());
        communityEt.setText(studentData.getCommunity());
        emailEt.setText(studentData.geteMail());
        hostelerEt.setText(studentData.getIsHosteler());
        transModeEt.setText(studentData.getModeOfTrans());
        emergencyNameEt.setText(studentData.getEmergencyPersonName());
        emergencyContactEt.setText(studentData.getEmergencyPersonContact());
        hobbiesEt.setText(studentData.getHobbies());
        pAddressEt.setText(studentData.getPermanentAddress());
        cAddressEt.setText(studentData.getCommAddress());

    }

    private void enableEt() {
        studentIv.setEnabled(true);
        nameEt.setEnabled(true);
        rollEt.setEnabled(true);
        regEt.setEnabled(true);
        degreeEt.setEnabled(true);
        branchEt.setEnabled(true);
        proctorNameEt.setEnabled(true);
        yearEt.setEnabled(true);
        dobEt.setEnabled(true);
        aadharEt.setEnabled(true);
        categoryEt.setEnabled(true);
        _12MarkEt.setEnabled(true);
        cutOffEt.setEnabled(true);
        genderEt.setEnabled(true);
        mTongueEt.setEnabled(true);
        bloodEt.setEnabled(true);
        religionEt.setEnabled(true);
        communityEt.setEnabled(true);
        emailEt.setEnabled(true);
        hostelerEt.setEnabled(true);
        transModeEt.setEnabled(true);
        emergencyNameEt.setEnabled(true);
        emergencyContactEt.setEnabled(true);
        hobbiesEt.setEnabled(true);
        pAddressEt.setEnabled(true);
        cAddressEt.setEnabled(true);

    }


    private StudentData getData() {
        String name = nameEt.getText().toString();
        String roll = rollEt.getText().toString();
        String reg = regEt.getText().toString();
        String degree = degreeEt.getText().toString();
        String branch = branchEt.getText().toString();
        String proctorName = proctorNameEt.getText().toString();
        String year = yearEt.getText().toString();
        String dob = dobEt.getText().toString();
        String aadhar = aadharEt.getText().toString();
        String category = categoryEt.getText().toString();
        String _12Mark = _12MarkEt.getText().toString();
        String cutOff = cutOffEt.getText().toString();
        String gender = genderEt.getText().toString();
        String mTongue = mTongueEt.getText().toString();
        String blood = bloodEt.getText().toString();
        String religion = religionEt.getText().toString();
        String community = communityEt.getText().toString();
        String email = emailEt.getText().toString();
        String hosteler = hostelerEt.getText().toString();
        String transMode = transModeEt.getText().toString();
        String emergencyName = emergencyNameEt.getText().toString();
        String emergencyContact = emergencyContactEt.getText().toString();
        String hobbies = hobbiesEt.getText().toString();
        String pAddress = pAddressEt.getText().toString();
        String cAddress = cAddressEt.getText().toString();

        return new StudentData().setStudentName(name)
                .setRollNo(roll)
                .setRegNo(reg)
                .setDegree(degree)
                .setBranch(branch)
                .setProctorName(proctorName)
                .setYearOfStudy(year)
                .setDob(dob)
                .setAadharNo(aadhar)
                .setCategory(category)
                .set_12Mark(_12Mark)
                .setCutOff(cutOff)
                .setGender(gender)
                .setMotherTongue(mTongue)
                .setBlood(blood)
                .setReligion(religion)
                .setCommunity(community)
                .seteMail(email)
                .setIsHosteler(hosteler)
                .setModeOfTrans(transMode)
                .setEmergencyPersonName(emergencyName)
                .setEmergencyPersonContact(emergencyContact)
                .setHobbies(hobbies)
                .setPermanentAddress(pAddress)
                .setCommAddress(cAddress);

    }

    private void addData(StudentData studentData) {
        database.collection("Student").document(uId).collection("PersonalData")
                .document("1")
                .set(studentData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        uploadImage(uri);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PersonalActivity.this, "Failed to add Data", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        addBtn.setEnabled(true);
                    }
                });

    }


    private void uploadImage(Uri imageUri) {
        if (imageUri != null) {
            String fileName = uId + "/" + "profile" + ".jpg";
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            StorageReference picRef = storageReference.child(fileName);
            try {
                Bitmap bmp = null;
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                if (bmp != null)
                    bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = picRef.putBytes(data);
                uploadTask.addOnSuccessListener(taskSnapshot -> {
                    Toast.makeText(PersonalActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }).addOnFailureListener(e -> {
                    Toast.makeText(PersonalActivity.this, "Data added but Photo not added", Toast.LENGTH_SHORT).show();
                    finish();
                });


            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(PersonalActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void downloadImage(String uId) {

        String fileName = uId + "/" + "profile" + ".jpg";
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(fileName);

        storageReference.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {

            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

            Glide.with(PersonalActivity.this)
                    .load(bitmap)
                    .into(studentIv);
        }).addOnFailureListener(exception -> {

            if(!(exception.getMessage().equals("Object does not exist at location.")))
                Toast.makeText(this, "Failed to download image", Toast.LENGTH_SHORT).show();
        });
    }

    private void findViews() {
        nameEt = findViewById(R.id.name_et);
        rollEt = findViewById(R.id.roll_no_et);
        regEt = findViewById(R.id.reg_no_et);
        degreeEt = findViewById(R.id.degree_et);
        branchEt = findViewById(R.id.branch_et);
        proctorNameEt = findViewById(R.id.proctor_name_et);
        yearEt = findViewById(R.id.year_et);
        dobEt = findViewById(R.id.dob_et);
        aadharEt = findViewById(R.id.aadhar_et);
        categoryEt = findViewById(R.id.category_et);
        _12MarkEt = findViewById(R.id._2marks_et);
        cutOffEt = findViewById(R.id.cut_off_et);
        genderEt = findViewById(R.id.gender_et);
        mTongueEt = findViewById(R.id.m_tongue_et);
        bloodEt = findViewById(R.id.bg_et);
        religionEt = findViewById(R.id.religion_et);
        communityEt = findViewById(R.id.community_et);
        emailEt = findViewById(R.id.email_et);
        hostelerEt = findViewById(R.id.hostel_et);
        transModeEt = findViewById(R.id.mode_trans_et);
        emergencyNameEt = findViewById(R.id.em_contact_person_et);
        emergencyContactEt = findViewById(R.id.em_contact_et);
        hobbiesEt = findViewById(R.id.hobbies_et);
        pAddressEt = findViewById(R.id.perm_address_et);
        cAddressEt = findViewById(R.id.comm_address_et);

        addBtn = findViewById(R.id.add_btn);
        editBtn = findViewById(R.id.edit_btn);
        backBtn = findViewById(R.id.back_btn);

        progressBar = findViewById(R.id.progress_bar);

        studentIv = findViewById(R.id.add_student_img);
    }
}