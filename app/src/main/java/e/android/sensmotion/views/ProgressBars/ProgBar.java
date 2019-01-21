package e.android.sensmotion.views.ProgressBars;

public class ProgBar {

    private int progress, goal;

    private String name;

    private Boolean isComplete;


    public ProgBar(String name, int progress, int goal){

        this.name = name;
        if(progress > goal){
            this.progress = goal;
        } else {
            this.progress = progress;
        }
        this.goal = goal;
        isComplete = false;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public String getName() {
        return name;
    }

    public double getPercent(){
        double percent;
        percent = ((double) progress/ (double) goal)*100;
        return percent;

    }

}
