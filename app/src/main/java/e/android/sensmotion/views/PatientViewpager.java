package e.android.sensmotion.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import e.android.sensmotion.R;
import e.android.sensmotion.views.PageFragment.PageAdapter;
import me.kaelaela.verticalviewpager.VerticalViewPager;

public class PatientViewpager extends android.support.v4.app.Fragment {

    PagerAdapter adapter;
    VerticalViewPager verticalViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.patient_viewpager, container, false);



        verticalViewPager = (VerticalViewPager) view.findViewById(R.id.viewpager);
        adapter = new PageAdapter(getFragmentManager());
        verticalViewPager.setAdapter(adapter);
        verticalViewPager.setCurrentItem(adapter.getCount()-1);

        return view;
    }


}
