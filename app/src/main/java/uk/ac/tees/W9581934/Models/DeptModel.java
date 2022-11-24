package uk.ac.tees.W9581934.Models;

public class DeptModel {
    String dname;
    String dept_id;

    public DeptModel(String dname, String dept_id) {
        this.dname = dname;
        this.dept_id = dept_id;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getDept_id() {
        return dept_id;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }
}
