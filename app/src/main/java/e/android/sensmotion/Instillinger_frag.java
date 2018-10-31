package e.android.sensmotion;


import android.os.Bundle;
import android.app.Fragment;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instillinger, container, false);

        LogOut = (Button) view.findViewById(R.id.logud);
        LogOut.setOnClickListener(this);

        Popup = (Switch) view.findViewById(R.id.Pop_switch);
        Popup.setOnCheckedChangeListener(this);

        Lydef = (Switch) view.findViewById(R.id.Lydswitch);
        Lydef.setOnCheckedChangeListener(this);

        // Inflate the layout for this fragment
        return view;
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