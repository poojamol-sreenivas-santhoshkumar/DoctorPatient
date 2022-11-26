package uk.ac.tees.W9581934.Models;

public class BookingModel {
    String doc_id,doc_name,patient_name,tokenNo,patient_phone,bookingDate,bookingType,dept_name,userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BookingModel(String doc_id, String doc_name, String patient_name, String tokenNo, String patient_phone, String bookingDate, String bookingType, String dept_name, String userId) {
        this.doc_id = doc_id;
        this.doc_name = doc_name;
        this.patient_name = patient_name;
        this.tokenNo = tokenNo;
        this.patient_phone = patient_phone;
        this.bookingDate = bookingDate;
        this.bookingType = bookingType;
        this.dept_name = dept_name;
        this.userId = userId;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getTokenNo() {
        return tokenNo;
    }

    public void setTokenNo(String tokenNo) {
        this.tokenNo = tokenNo;
    }

    public String getPatient_phone() {
        return patient_phone;
    }

    public void setPatient_phone(String patient_phone) {
        this.patient_phone = patient_phone;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }
}
