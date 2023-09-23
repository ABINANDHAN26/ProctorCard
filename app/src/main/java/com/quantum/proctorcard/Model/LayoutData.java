package com.quantum.proctorcard.Model;

import android.widget.EditText;

public class LayoutData {

    EditText et1, et2;

    public LayoutData() {
    }

    public LayoutData(EditText dateEt, EditText reasonEt) {
        this.et1 = dateEt;
        this.et2 = reasonEt;

    }

    public EditText getEt1() {
        return et1;
    }

    public void setEt1(EditText et1) {
        this.et1 = et1;
    }

    public EditText getEt2() {
        return et2;
    }

    public void setEt2(EditText et2) {
        this.et2 = et2;
    }

}
