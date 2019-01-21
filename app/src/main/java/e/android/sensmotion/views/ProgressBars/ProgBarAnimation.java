package e.android.sensmotion.views.ProgressBars;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

import e.android.sensmotion.adapters.ProgressBar_adapter;

public class ProgBarAnimation extends Animation {
    private ProgressBar progressBar;
    private float from;
    private float to;

    public ProgBarAnimation(ProgressBar progressBar, float from, float to){
        super();
        this.progressBar = progressBar;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);
    }



}