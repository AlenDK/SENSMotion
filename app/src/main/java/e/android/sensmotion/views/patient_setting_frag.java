package e.android.sensmotion.views;

import android.app.Fragment;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import e.android.sensmotion.Notification.NotificationService;
import e.android.sensmotion.R;

public class patient_setting_frag extends Fragment {

    Switch pop_switch, sound_switch;
    Button logout;
    ImageButton back;
    ImageView column1, column2, column3, column4, column5;
    Intent act;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.patient_setting_frag, container, false);

        createImage(view);

        pop_switch = (Switch) view.findViewById(R.id.switchPOP);
        sound_switch = (Switch) view.findViewById(R.id.switchSound);

        back = (ImageButton) view.findViewById(R.id.backarrow);
        logout = (Button) view.findViewById(R.id.logUd);

        //Kan det gøres smartere?
        /*pop_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    Intent act = new Intent(getActivity(), e.android.sensmotion.Notification.NotificationService.class);
                    startService(act);
                } else if (!compoundButton.isChecked()) {
                    Intent act = new Intent(getActivity(), e.android.sensmotion.Notification.NotificationService.class);
                    stopService(act);
                }
                Toast.makeText(getActivity(), "comming soon", Toast.LENGTH_LONG).show();
            }
        });*/
        sound_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(getActivity(), "comming soon", Toast.LENGTH_LONG).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act = new Intent(getActivity(), PatientActivity.class);
                startActivity(act);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "comming soon", Toast.LENGTH_LONG).show();
                act = new Intent(getActivity(), MainActivity.class);
                act.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(act);
            }
        });

        return view;
    }


    private void createImage(View view){
        column1 = (ImageView) view.findViewById(R.id.column1);
        column2 = (ImageView) view.findViewById(R.id.column2);
        column3 = (ImageView) view.findViewById(R.id.column3);
        column4 = (ImageView) view.findViewById(R.id.column4);
        column5 = (ImageView) view.findViewById(R.id.column5);
    }
}
