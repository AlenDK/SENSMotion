package e.android.sensmotion.views.PageFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import e.android.sensmotion.views.Achievement_frag;
import e.android.sensmotion.views.Patient_start_frag;

public class PageAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Normal", "Endless" };
    private Context context;

    public PageAdapter(FragmentManager fm) {
        super(fm);
        //this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0: Achievement_frag tab2 = new Achievement_frag();
                    return tab2;

            case 1: Patient_start_frag tab1 = new Patient_start_frag();
                    return tab1;
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
