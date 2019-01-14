package e.android.sensmotion.adapters;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


public abstract class MotionDetection implements View.OnTouchListener {

    private  GestureDetector detector;

    public MotionDetection(Context c) {
        detector = new GestureDetector(c,  new GestureListener());
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return detector.onTouchEvent(motionEvent);
    }

    private  final class GestureListener extends GestureDetector.SimpleOnGestureListener {

    private static final int minSwipeDistanceX = 100;
    private static final int minSwipeDistanceY = 100;

    private static final int maxSwipeDistanceX = 100;
    private static final int maxSwipeDistanceY = 100;

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float veloccityX, float veloccityY) {
        boolean result = false;
        try {
        float deltaX = e1.getX() - e2.getX();

        float deltaY = e1.getY() - e2.getY();

        float deltaXabs = Math.abs(deltaX);
        float deltaYabs = Math.abs(deltaY);

        //        if((deltaXabs >= minSwipeDistanceX) && (deltaXabs <= maxSwipeDistanceX))
        if((deltaXabs > minSwipeDistanceX) && (veloccityX > maxSwipeDistanceX))
        {
            if(deltaX > 0) {
                onSwipeRight();
            }else {
                onSwipeLeft();
            }
        }
        //        if((deltaYabs >= minSwipeDistanceY) && (deltaYabs <= maxSwipeDistanceY))
        if((deltaYabs > minSwipeDistanceY) && (veloccityY > maxSwipeDistanceY))
        {
            if(deltaY > 0) {
                onSwipeDown();
            }else {
                onSwipeUp();
            }
        }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;

    }
    }
    public void onSwipeRight() {

    }

    public void onSwipeLeft() {

    }

    public void onSwipeUp() {

    }

    public void onSwipeDown() {

    }



}
