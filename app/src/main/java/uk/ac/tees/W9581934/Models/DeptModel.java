package uk.ac.tees.W9581934.Models;

public class DeptModel {
    String dname;
    int dept_id;

    public DeptModel(String dname, int dept_id) {
        this.dname = dname;
        this.dept_id = dept_id;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public int getDept_id() {
        return dept_id;
    }

    public void setDept_id(int dept_id) {
        this.dept_id = dept_id;
    }
}
