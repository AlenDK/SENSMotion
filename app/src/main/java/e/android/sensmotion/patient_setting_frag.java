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

public class patient_setting_frag extends Fragment implements CompoundButton.OnCheckedChangeListener {

    Switch pop_switch, sound_switch;
    Button back;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.patient_setting_frag, container, false);

        pop_switch = (Switch) view.findViewById(R.id.switchPOP);
        sound_switch = (Switch) view.findViewById(R.id.switchSound);
        back = (Button) view.findViewById(R.id.backarrow);

        pop_switch.setOnCheckedChangeListener(this);
        sound_switch.setOnCheckedChangeListener(this);

        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.switchPOP:
                if(pop_switch.isChecked()){
                    Toast.makeText(getActivity(), "comming soon", Toast.LENGTH_LONG).show();
                }

            case R.id.switchSound:
                if(sound_switch.isChecked()){
                    Toast.makeText(getActivity(), "comming soon", Toast.LENGTH_LONG).show();
                }
        }
    }
}
