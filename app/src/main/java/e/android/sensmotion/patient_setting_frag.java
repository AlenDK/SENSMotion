package e.android.sensmotion;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class patient_setting_frag extends Fragment {

    Switch pop_switch, sound_switch;
    Button back, logout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.patient_setting_frag, container, false);

        pop_switch = (Switch) view.findViewById(R.id.switchPOP);
        sound_switch = (Switch) view.findViewById(R.id.switchSound);

        back = (Button) view.findViewById(R.id.backarrow);
        logout = (Button) view.findViewById(R.id.logUd);

        //Kan det gøres smartere?
        pop_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(getActivity(), "comming soon", Toast.LENGTH_LONG).show();
            }
        });
        sound_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(getActivity(), "comming soon", Toast.LENGTH_LONG).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentindhold, new Patient_start_frag())
                        .commit();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "comming soon", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
