package e.android.sensmotion.views;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

import e.android.sensmotion.R;
import e.android.sensmotion.adapters.Achievement_adapter;
import e.android.sensmotion.adapters.MotionDetection;
import e.android.sensmotion.adapters.Patientliste_adapter;
import io.fabric.sdk.android.Fabric;


public class Achievement_frag extends Fragment  {

    boolean EMULATOR = Build.PRODUCT.contains("sdk") || Build.MODEL.contains("Emulator");
    GridView gridView;
    Achievement_adapter adapter;
    int numberIcons[] = {R.drawable.baseline_sentiment_very_satisfied_black_48, R.drawable.walking_stickman, R.drawable.login_knap,
    R.drawable.patient_ikon};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.achievement, container, false);

        if (!EMULATOR) {
            Fabric.with(getActivity(), new Crashlytics());
        }

        gridView = (GridView) view.findViewById(R.id.gridview);

        adapter = new Achievement_adapter(getActivity(), numberIcons);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getActivity(), "Du har gennemført x", Toast.LENGTH_SHORT).show();
            }
        });



        view.setOnTouchListener(new MotionDetection(getActivity()) {
            @Override
            public void onSwipeDown() {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentindhold, new Patient_start_frag())
                        .commit();
            }
        });





        return view;
    }



}
