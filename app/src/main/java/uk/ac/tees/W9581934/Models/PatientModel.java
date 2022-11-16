package uk.ac.tees.W9581934.Models;

public class PatientModel {
        String name,age,phone,address;

        public PatientModel(String name, String age, String phone, String address) {
                this.name = name;
                this.age = age;
                this.phone = phone;
                this.address = address;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getAge() {
                return age;
        }

        public void setAge(String age) {
                this.age = age;
        }

        public String getPhone() {
                return phone;
        }

        public void setPhone(String phone) {
                this.phone = phone;
        }

        public String getAddress() {
                return address;
        }

        public void setAddress(String address) {
                this.address = address;
        }
}
