package uk.ac.tees.W9581934.Models;

public class ViewPagerModel {
    String image,image2,image3;

    public ViewPagerModel(String image, String image2, String image3) {
        this.image = image;
        this.image2 = image2;
        this.image3 = image3;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }
}
