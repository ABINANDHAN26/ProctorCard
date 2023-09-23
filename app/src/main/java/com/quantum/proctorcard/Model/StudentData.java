package com.quantum.proctorcard.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class StudentData implements Parcelable {
    private String studentName, rollNo, regNo, degree, branch, proctorName, yearOfStudy, dob, aadharNo;
    private String category, _12Mark, cutOff, gender, motherTongue, blood, religion, community, eMail;
    private String isHosteler, modeOfTrans, emergencyPersonName, emergencyPersonContact, hobbies;
    private String permanentAddress, commAddress;


    public StudentData() {
    }

    protected StudentData(Parcel in) {
        studentName = in.readString();
        rollNo = in.readString();
        regNo = in.readString();
        degree = in.readString();
        branch = in.readString();
        proctorName = in.readString();
        yearOfStudy = in.readString();
        dob = in.readString();
        aadharNo = in.readString();
        category = in.readString();
        _12Mark = in.readString();
        cutOff = in.readString();
        gender = in.readString();
        motherTongue = in.readString();
        blood = in.readString();
        religion = in.readString();
        community = in.readString();
        eMail = in.readString();
        isHosteler = in.readString();
        modeOfTrans = in.readString();
        emergencyPersonName = in.readString();
        emergencyPersonContact = in.readString();
        hobbies = in.readString();
        permanentAddress = in.readString();
        commAddress = in.readString();
    }

    public static final Creator<StudentData> CREATOR = new Creator<StudentData>() {
        @Override
        public StudentData createFromParcel(Parcel in) {
            return new StudentData(in);
        }

        @Override
        public StudentData[] newArray(int size) {
            return new StudentData[size];
        }
    };

    public String getStudentName() {
        return studentName;
    }

    public StudentData setStudentName(String studentName) {
        this.studentName = studentName;
        return this;
    }

    public String getRollNo() {
        return rollNo;
    }

    public StudentData setRollNo(String rollNo) {
        this.rollNo = rollNo;
        return this;
    }

    public String getRegNo() {
        return regNo;
    }

    public StudentData setRegNo(String regNo) {
        this.regNo = regNo;
        return this;
    }

    public String getDegree() {
        return degree;

    }

    public StudentData setDegree(String degree) {
        this.degree = degree;
        return this;
    }

    public String getBranch() {
        return branch;
    }

    public StudentData setBranch(String branch) {
        this.branch = branch;
        return this;
    }

    public String getProctorName() {
        return proctorName;
    }

    public StudentData setProctorName(String proctorName) {
        this.proctorName = proctorName;
        return this;
    }

    public String getYearOfStudy() {
        return yearOfStudy;
    }

    public StudentData setYearOfStudy(String yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
        return this;
    }

    public String getDob() {
        return dob;
    }

    public StudentData setDob(String dob) {
        this.dob = dob;
        return this;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public StudentData setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public StudentData setCategory(String category) {
        this.category = category;
        return this;
    }

    public String get_12Mark() {
        return _12Mark;
    }

    public StudentData set_12Mark(String _12Mark) {
        this._12Mark = _12Mark;
        return this;
    }

    public String getCutOff() {
        return cutOff;
    }

    public StudentData setCutOff(String cutOff) {
        this.cutOff = cutOff;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public StudentData setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getMotherTongue() {
        return motherTongue;

    }

    public StudentData setMotherTongue(String motherTongue) {
        this.motherTongue = motherTongue;
        return this;
    }

    public String getBlood() {
        return blood;
    }

    public StudentData setBlood(String blood) {
        this.blood = blood;
        return this;
    }

    public String getReligion() {
        return religion;
    }

    public StudentData setReligion(String religion) {
        this.religion = religion;
        return this;
    }

    public String getCommunity() {
        return community;
    }

    public StudentData setCommunity(String community) {
        this.community = community;
        return this;
    }

    public String geteMail() {
        return eMail;
    }

    public StudentData seteMail(String eMail) {
        this.eMail = eMail;
        return this;
    }

    public String getIsHosteler() {
        return isHosteler;
    }

    public StudentData setIsHosteler(String isHosteler) {
        this.isHosteler = isHosteler;
        return this;
    }

    public String getModeOfTrans() {
        return modeOfTrans;
    }

    public StudentData setModeOfTrans(String modeOfTrans) {
        this.modeOfTrans = modeOfTrans;
        return this;
    }

    public String getEmergencyPersonName() {
        return emergencyPersonName;
    }

    public StudentData setEmergencyPersonName(String emergencyPersonName) {
        this.emergencyPersonName = emergencyPersonName;
        return this;
    }

    public String getEmergencyPersonContact() {
        return emergencyPersonContact;
    }

    public StudentData setEmergencyPersonContact(String emergencyPersonContact) {
        this.emergencyPersonContact = emergencyPersonContact;
        return this;
    }

    public String getHobbies() {
        return hobbies;
    }

    public StudentData setHobbies(String hobbies) {
        this.hobbies = hobbies;
        return this;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public StudentData setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
        return this;
    }

    public String getCommAddress() {
        return commAddress;
    }

    public StudentData setCommAddress(String commAddress) {
        this.commAddress = commAddress;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(studentName);
        parcel.writeString(rollNo);
        parcel.writeString(regNo);
        parcel.writeString(degree);
        parcel.writeString(branch);
        parcel.writeString(proctorName);
        parcel.writeString(yearOfStudy);
        parcel.writeString(dob);
        parcel.writeString(aadharNo);
        parcel.writeString(category);
        parcel.writeString(_12Mark);
        parcel.writeString(cutOff);
        parcel.writeString(gender);
        parcel.writeString(motherTongue);
        parcel.writeString(blood);
        parcel.writeString(religion);
        parcel.writeString(community);
        parcel.writeString(eMail);
        parcel.writeString(isHosteler);
        parcel.writeString(modeOfTrans);
        parcel.writeString(emergencyPersonName);
        parcel.writeString(emergencyPersonContact);
        parcel.writeString(hobbies);
        parcel.writeString(permanentAddress);
        parcel.writeString(commAddress);
    }
}
