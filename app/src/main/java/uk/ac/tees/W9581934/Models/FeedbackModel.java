package uk.ac.tees.W9581934.Models;

public class FeedbackModel {
    String pname,feedback;

    public FeedbackModel(String pname, String feedback) {
        this.pname = pname;
        this.feedback = feedback;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
