package e.android.sensmotion;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;


public class Terapeut_setting_frag extends Fragment implements View.OnClickListener{

    Button LogOut;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.terapeut_setting_frag, container, false);

        LogOut = (Button) view.findViewById(R.id.logud);
        LogOut.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == LogOut) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentindhold, new Login_frag())
                    .addToBackStack(null)
                    .commit();
        }

    }
}
