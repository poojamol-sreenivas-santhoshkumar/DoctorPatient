package uk.ac.tees.W9581934.Models;

public class PatientModel {
        String id,type,name,age,address,phone,username,password;

        public PatientModel(String id, String type,String name, String age, String address, String phone, String username, String password) {
                this.id = id;
                this.type = type;
                this.name = name;
                this.age = age;
                this.address = address;
                this.phone = phone;
                this.username = username;
                this.password = password;
        }

        public String getType() {
                return type;
        }

        public void setType(String type) {
                this.type = type;
        }

        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
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

        public String getAddress() {
                return address;
        }

        public void setAddress(String address) {
                this.address = address;
        }

        public String getPhone() {
                return phone;
        }

        public void setPhone(String phone) {
                this.phone = phone;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }
}
