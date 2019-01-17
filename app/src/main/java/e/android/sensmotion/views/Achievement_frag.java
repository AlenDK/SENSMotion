package e.android.sensmotion.views;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.crashlytics.android.Crashlytics;

import e.android.sensmotion.R;
import e.android.sensmotion.adapters.Achievement_adapter;
import io.fabric.sdk.android.Fabric;


public class Achievement_frag extends Fragment  {

    boolean EMULATOR = Build.PRODUCT.contains("sdk") || Build.MODEL.contains("Emulator");
    GridView gridView;
    Achievement_adapter adapter;
    int numberIcons[] = {R.drawable.baseline_sentiment_very_satisfied_black_48, R.drawable.stickman_walking, R.drawable.login_knap,
    R.drawable.patient_ikon};
    int altNumberIcons[] = {R.drawable.sensmotionwhite, R.drawable.sensmotionwhite, R.drawable.sensmotionwhite, R.drawable.sensmotionwhite};
    int progress[] = {R.drawable.nuludaf3, R.drawable.enudaf3, R.drawable.toudaf3, R.drawable.treudaf3};
    String text[] = {"Du har gjort det", "Du klarede det", "Ja tak", "Kom så"};
    String alttext[] = {"Du har ikke gjort det endnu", "Du har ikke klaret det", "Nej tak", "Kom så"};
    String head[] = {"Marathon", "7 på stribe", "Keep going", "2018 beta tester"};
    Boolean check[] = {100 > 1000, 4 == 3, 4 > 5, 1 > 0};



    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
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
                onCreateDialog(savedInstanceState, position);

            }

            });



/*

        view.setOnTouchListener(new MotionDetection(getActivity()) {
            @Override
            public void onSwipeDown() {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentindhold, new Patient_start_frag())
                        .commit();
            }
        });

  */




        return view;
    }



    public void onCreateDialog(Bundle savedInstanceState, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        builder.setTitle(head[position])
                .setMessage(text[position]);
        if (check[position] == false) {
            builder.setIcon(numberIcons[position]);
        }else {
            builder.setIcon(altNumberIcons[position]);
        }
        builder.setNeutralButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getFragmentManager().beginTransaction()
                                .commit();
                    }
                });
         builder.create().show();

    }
}
