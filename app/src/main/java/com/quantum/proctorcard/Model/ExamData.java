package com.quantum.proctorcard.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamData {


    Map<String, Map<String, String>> data;


    public ExamData(Map<String, Map<String, String>> data) {
        this.data = data;
    }

    public ExamData() {
        data = new HashMap<>();
    }

    public Map<String, Map<String, String>> getData() {
        return data;
    }

    public void setData(Map<String, Map<String, String>> data) {
        this.data = data;
    }


    public void addData(String key,Map<String,String> value){
        this.data.put(key,value);
    }

}
