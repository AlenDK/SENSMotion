package e.android.sensmotion.views.Achievement;

import e.android.sensmotion.R;

public class Achievement {

    int image;
    String name, text;
    Boolean isComplete;

    public Achievement(String name, String text) {
        this.name = name;
        this.text = text;
        isComplete = false;
        image = (R.drawable.iconfinder_lock_299105);
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }
}
