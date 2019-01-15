package e.android.sensmotion.views.PageFragment;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class VerticalViewPager extends ViewPager {


    public VerticalViewPager(Context context) {
        super(context);
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private class VerticalPage implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            page.setAlpha(0);
        }
    }
}
