package uk.ac.tees.W9581934.Models;

public class DoctorListModel {
    String doctorDocId,userId, name, departmentName, specializedStream, dob, age, consultingTime, availabilityDays,
            username, phone, type, doctorImage, password, experience, endtime;

    public DoctorListModel(String doctorDocId, String userId, String name, String departmentName, String specializedStream, String dob, String age, String consultingTime, String availabilityDays, String username, String phone, String type, String doctorImage, String password, String experience, String endtime) {
        this.doctorDocId = doctorDocId;
        this.userId = userId;
        this.name = name;
        this.departmentName = departmentName;
        this.specializedStream = specializedStream;
        this.dob = dob;
        this.age = age;
        this.consultingTime = consultingTime;
        this.availabilityDays = availabilityDays;
        this.username = username;
        this.phone = phone;
        this.type = type;
        this.doctorImage = doctorImage;
        this.password = password;
        this.experience = experience;
        this.endtime = endtime;
    }

    public String getDoctorDocId() {
        return doctorDocId;
    }

    public void setDoctorDocId(String doctorDocId) {
        this.doctorDocId = doctorDocId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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