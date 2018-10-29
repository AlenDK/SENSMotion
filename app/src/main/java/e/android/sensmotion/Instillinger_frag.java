package e.android.sensmotion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;


public class Instillinger_frag extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener{

    Switch Popup;
    Switch Lydef;
    Button LogOut;
    boolean notif = false;
    boolean lyd = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LogOut = (Button) getView().findViewById(R.id.logud);
        LogOut.setOnClickListener(this);
        
        Popup = (Switch) getView().findViewById(R.id.Pop_switch);
        Popup.setOnCheckedChangeListener(this);

        Lydef = (Switch) getView().findViewById(R.id.Lydswitch);
        Lydef.setOnCheckedChangeListener(this);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_instillinger, container, false);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if(compoundButton == Popup) {

            if(isChecked) {
                notif = true;
                System.out.print("notif: " + notif);
            } else {
                notif = false;
                System.out.print("notif: " + notif);
            }
        } else if(compoundButton == Lydef){

            if(isChecked) {
                notif = true;
                System.out.print("Lydeffekt: " + lyd);
            } else {
                notif = false;
                System.out.print("Lydeffekt: " + lyd);
            }
        }
    }

    @Override
    public void onClick(View view) {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentindhold, new Login_frag())
                .addToBackStack(null)
                .commit();
    }
}
