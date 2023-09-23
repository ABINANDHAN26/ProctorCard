package com.quantum.proctorcard;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.quantum.proctorcard.Model.ExamData;
import com.quantum.proctorcard.Model.StudentData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;

public class PdfGeneratorService {

    String TAG = "TAG";

    ArrayList<ExamData> examDataArrayList = new ArrayList<>();
    ArrayList<Map<String, Object>> attendanceList = new ArrayList<>();
    Context context;
    StudentData studentData;
    static int i = 2;
    ProgressBar progressBar;

    public PdfGeneratorService(Context context,ProgressBar progressBar) {
        this.context = context;
        this.progressBar = progressBar;
        progressBar.setVisibility(View.VISIBLE);
        i=2;
    }

    public void generatePdf() {
String pdfFilePath ="";
        Log.i(TAG, "exam: " + examDataArrayList.size());
        Log.i(TAG, "atte: " + attendanceList.size());
        Log.i(TAG, String.valueOf("generatePdf: " + studentData == null));
        if (studentData == null)
            return;
        try {
            String rollNo = studentData.getRollNo();
            rollNo = rollNo.replace(".", "");
            rollNo = rollNo.replace(" ", "");
            String fileName = rollNo + "_proctor.pdf";


            pdfFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName;
            PdfWriter writer = new PdfWriter(pdfFilePath);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // Add content to the PDF document
            document.add(new Paragraph("S.A Engineering College").setTextAlignment(TextAlignment.CENTER).setFontSize(12.0f));

            document.add(new Paragraph("Student Information").setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("Student Name: " + studentData.getStudentName()));
            document.add(new Paragraph("Roll No: " + studentData.getRollNo()));
            document.add(new Paragraph("Registration No: " + studentData.getRegNo()));
            document.add(new Paragraph("Degree: " + studentData.getDegree()));
            document.add(new Paragraph("Branch: " + studentData.getBranch()));
            document.add(new Paragraph("Proctor Name: " + studentData.getProctorName()));
            document.add(new Paragraph("Year Of Study: " + studentData.getYearOfStudy()));
            document.add(new Paragraph("DOB: " + studentData.getDob()));
            document.add(new Paragraph("Aadhar No: " + studentData.getAadharNo()));

            document.add(new Paragraph("Category: " + studentData.getCategory()));
            document.add(new Paragraph("12th Mark: " + studentData.get_12Mark()));
            document.add(new Paragraph("Cut off: " + studentData.getCutOff()));
            document.add(new Paragraph("Gender: " + studentData.getGender()));
            document.add(new Paragraph("Mother Tongue: " + studentData.getMotherTongue()));
            document.add(new Paragraph("Blood Group: " + studentData.getBlood()));
            document.add(new Paragraph("Religion: " + studentData.getReligion()));
            document.add(new Paragraph("Community: " + studentData.getCommunity()));
            document.add(new Paragraph("Day Scholar/Hosteler: " + studentData.getIsHosteler()));

            document.add(new Paragraph("E-Mail: " + studentData.geteMail()));
            document.add(new Paragraph("Mode Of Transport: " + studentData.getModeOfTrans()));
            document.add(new Paragraph("Emergency Contact Name: " + studentData.getEmergencyPersonName()));
            document.add(new Paragraph("Emergency Contact Phone: " + studentData.getEmergencyPersonContact()));
            document.add(new Paragraph("Hobbies: " + studentData.getHobbies()));
            document.add(new Paragraph("Permanent Address: " + studentData.getPermanentAddress()));
            document.add(new Paragraph("Communication Address: " + studentData.getCommAddress()));

            for (int i = 0; i < 8; i++) {
                ExamData examData = examDataArrayList.get(i);
                Map<String, Object> attendanceData = attendanceList.get(i);
                String[] exams = {"IA1", "IA2", "IA3", "Final"};
                String[] semNames = {"Semester 1", "Semester 2", "Semester 3", "Semester 4", "Semester 5", "Semester 6", "Semester 7", "Semester 8"};
                String semName = semNames[i];
                boolean isSemAdded = false;
                boolean newArea = false;
                if (examData != null) {
                    Log.i(TAG, "generatePdf: " + examData.getData());
                    document.add(new AreaBreak());
                    newArea=true;
                    isSemAdded = true;
                    document.add(new Paragraph(semName).setTextAlignment(TextAlignment.CENTER));
                    document.add(new Paragraph("Exam Record"));
                    Table table = new Table(5);
                    table.setTextAlignment(TextAlignment.CENTER);
                    table.setFixedLayout();
                    table.addCell(createCell("Subject", true));
                    table.addCell(createCell("IA1", true));
                    table.addCell(createCell("IA2", true));
                    table.addCell(createCell("IA3", true));
                    table.addCell(createCell("Final", true));
                    Set<String> subjects = new HashSet<>();

                    if (examData.getData().containsKey("Final")) {
                        subjects.addAll(examData.getData().get("Final").keySet());
                    }
                    if (examData.getData().containsKey("IA1")) {
                        subjects.addAll(examData.getData().get("IA1").keySet());
                    }
                    if (examData.getData().containsKey("IA2")) {
                        subjects.addAll(examData.getData().get("IA2").keySet());
                    }
                    if (examData.getData().containsKey("IA3")) {
                        subjects.addAll(examData.getData().get("IA3").keySet());
                    }


                    for (String subject : subjects) {
                        if (subject != null) {
                            // Create a new row for each subject
                            table.addCell(createCell(subject, false));
                            for (String exam : exams) {
                                if (examData.getData().containsKey(exam)) {
                                    String mark = examData.getData().get(exam).get(subject);
                                    table.addCell(createCell(mark, false));
                                } else {
                                    table.addCell(createCell("", false)); // Add an empty cell if no data for the exam
                                }
                            }
                            //Add Overall CGPA here
                        }
                    }
                    document.add(table);
                }


                if (attendanceData != null) {
                    Log.i(TAG, "generatePdf: " + attendanceData.keySet());
                    if(!newArea)
                        document.add(new AreaBreak());
                    if (!isSemAdded)
                        document.add(new Paragraph(semName).setTextAlignment(TextAlignment.CENTER));

                    document.add(new Paragraph());
                    document.add(new Paragraph());
                    document.add(new Paragraph("Absent Record"));
                    Set<String> dates = attendanceData.keySet();
                    Table table = new Table(2);
                    table.setTextAlignment(TextAlignment.CENTER);
                    table.setFixedLayout();
                    table.addCell(createCell("Date", true));
                    table.addCell(createCell("Reason", true));
                    for (String date : dates) {
                        table.addCell(createCell(date, false));
                        table.addCell(createCell(String.valueOf(attendanceData.get(date)), false));
                    }
                    document.add(table);
                }
            }
            document.close();
        } catch (FileNotFoundException e) {
            //Toast.makeText(context, "Can't Generate PDF", Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "Can't Generate PDF"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

// Assuming you have an exception 'e'
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTraceString = sw.toString();
            saveErrorToTextFile(""+e.getMessage()+"\n"+stackTraceString+"\n"+pdfFilePath);
            e.printStackTrace();
            Log.i("TAG", "generatePdf: error" + e.getLocalizedMessage());
        }


        Toast.makeText(context, "PDF Generated Successfully", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }

    // Create a cell with optional header styling
    private static Cell createCell(String content, boolean isHeader) {
        if (content == null)
            content = "";
        Cell cell = new Cell();
        cell.add(new Paragraph(content));
        if (isHeader) {
            cell.setBold();
        }
        return cell;
    }


    private void saveErrorToTextFile(String errorMessage) {
        try {

            String documentsDirectoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath();
            String fileName = "error_log.txt";
            String filePath = documentsDirectoryPath + File.separator + fileName;
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }


            FileWriter writer = new FileWriter(file, true); // Use "true" to append to the file
            writer.append(errorMessage);
            writer.append("\n"); // Add a newline for readability
            writer.close();
            Log.i("TAG", "Error message saved to " + filePath);
        } catch (IOException e) {
            Log.e("TAG", "Error saving error message: " + e.getLocalizedMessage());
        }
    }


    public void getData(String semName) {
        Log.i(TAG, "getData: ");
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        String uId = FirebaseAuth.getInstance().getUid();
        database.collection("Student").document(uId)
                .collection("PersonalData")
                .document("1")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            studentData = documentSnapshot.toObject(StudentData.class);
                        }

                        database.collection("Student").document(uId).collection(semName)
                                .document("ExamData").get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if (documentSnapshot.exists()) {
                                            ExamData examData = documentSnapshot.toObject(ExamData.class);
                                            examDataArrayList.add(examData);
                                        } else {
                                            examDataArrayList.add(null);
                                        }
                                        database.collection("Student").document(uId).collection(semName)
                                                .document("AttendanceData").get()
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        attendanceList.add(documentSnapshot.getData());
                                                        if (semName.equalsIgnoreCase("Semester 8")) {
                                                            generatePdf();
                                                        } else {
                                                            String tempName = "Semester " + i;
                                                            i++;
                                                            getData(tempName);
                                                        }
                                                    }
                                                }).addOnFailureListener(e -> {
                                                    attendanceList.add(null);
                                                    if (semName.equalsIgnoreCase("Semester 8")) {
                                                        generatePdf();
                                                    } else {
                                                        String tempName = "Semester " + i;
                                                        i++;
                                                        getData(tempName);
                                                    }
                                                });

                                    }
                                }).addOnFailureListener(e -> {
                                    examDataArrayList.add(null);
                                    if (semName.equalsIgnoreCase("Semester 8")) {
                                        generatePdf();
                                    } else {
                                        String tempName = "Semester " + i;
                                        i++;
                                        getData(tempName);
                                    }
                                });
                    }
                });

    }


}
