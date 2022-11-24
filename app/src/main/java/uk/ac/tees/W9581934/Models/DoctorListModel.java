package uk.ac.tees.W9581934.Models;

public class DoctorListModel {
    String doc_id,doctorName,departmentName,specializedStream,dob,age,consultingTime,availabilityDays,
            userName,mobileNumber,type,doctorImage,password,experience,endtime;

    public DoctorListModel(String doc_id, String doctorName, String departmentName, String specializedStream, String dob, String age, String consultingTime, String availabilityDays, String userName, String mobileNumber, String type, String doctorImage, String password, String experience, String endtime) {
        this.doc_id = doc_id;
        this.doctorName = doctorName;
        this.departmentName = departmentName;
        this.specializedStream = specializedStream;
        this.dob = dob;
        this.age = age;
        this.consultingTime = consultingTime;
        this.availabilityDays = availabilityDays;
        this.userName = userName;
        this.mobileNumber = mobileNumber;
        this.type = type;
        this.doctorImage = doctorImage;
        this.password = password;
        this.experience = experience;
        this.endtime = endtime;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDoctorImage() {
        return doctorImage;
    }

    public void setDoctorImage(String doctorImage) {
        this.doctorImage = doctorImage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
}
