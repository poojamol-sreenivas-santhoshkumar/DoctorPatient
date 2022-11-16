package uk.ac.tees.W9581934.Models;

public class DoctorListModel {
    String doctorName,departmentName,specializedStream,dob,age,consultingTime,availabilityDays,userName,mobileNumber;
int doctorImage;
    public DoctorListModel(String doctorName, String departmentName, String specializedStream, String dob, String age, String consultingTime, String availabilityDays, String userName, String mobileNumber, int doctorImage) {
        this.doctorName = doctorName;
        this.departmentName = departmentName;
        this.specializedStream = specializedStream;
        this.dob = dob;
        this.age = age;
        this.consultingTime = consultingTime;
        this.availabilityDays = availabilityDays;
        this.userName = userName;
        this.mobileNumber = mobileNumber;
        this.doctorImage = doctorImage;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getSpecializedStream() {
        return specializedStream;
    }

    public void setSpecializedStream(String specializedStream) {
        this.specializedStream = specializedStream;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getConsultingTime() {
        return consultingTime;
    }

    public void setConsultingTime(String consultingTime) {
        this.consultingTime = consultingTime;
    }

    public String getAvailabilityDays() {
        return availabilityDays;
    }

    public void setAvailabilityDays(String availabilityDays) {
        this.availabilityDays = availabilityDays;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getDoctorImage() {
        return doctorImage;
    }

    public void setDoctorImage(int doctorImage) {
        this.doctorImage = doctorImage;
    }
}
