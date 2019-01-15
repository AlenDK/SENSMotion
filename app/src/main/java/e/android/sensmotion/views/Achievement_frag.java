package e.android.sensmotion.views;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import e.android.sensmotion.R;
import e.android.sensmotion.adapters.Achievement_adapter;
import io.fabric.sdk.android.Fabric;


public class Achievement_frag extends Fragment  {

    boolean EMULATOR = Build.PRODUCT.contains("sdk") || Build.MODEL.contains("Emulator");
    GridView gridView;
    Achievement_adapter adapter;
    int numberIcons[] = {R.drawable.baseline_sentiment_very_satisfied_black_48, R.drawable.walking_stickman, R.drawable.login_knap,
    R.drawable.patient_ikon};
    int progress[] = {R.drawable.nuludaf3, R.drawable.enudaf3, R.drawable.toudaf3, R.drawable.treudaf3};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      //  View view = inflater.inflate(R.layout.achievement, container, false);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.achievement, container, false);


        if (!EMULATOR) {
            Fabric.with(getActivity(), new Crashlytics());
        }

        gridView = (GridView) view.findViewById(R.id.gridview);

        adapter = new Achievement_adapter(getActivity(), numberIcons, progress);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getActivity(), "Du har gennemført x", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }



}
