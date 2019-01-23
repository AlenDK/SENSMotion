package e.android.sensmotion.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import e.android.sensmotion.Notification.Alarm;
import e.android.sensmotion.R;

public class Patient_setting_Activity extends AppCompatActivity {

    Switch pop_switch, sound_switch;
    Button logout;
    ImageButton back;
    ImageView column1, column2, column3, column4, column5;
    Intent act;

    Context context;

    SharedPreferences mPrefs;
    SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_setting_frag);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefsEditor = mPrefs.edit();
        context = this;

        createImage();

        pop_switch = (Switch) findViewById(R.id.switchPOP);
        pop_switch.setChecked(mPrefs.getBoolean("NotiGoing", false));
        sound_switch = (Switch) findViewById(R.id.switchSound);

        back = (ImageButton) findViewById(R.id.back_patient_data);
        logout = (Button) findViewById(R.id.logUd);

        //Kan det gøres smartere?
        pop_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean on) {
                if (on) {
                    Alarm.startNotifications(context);
                    prefsEditor.putBoolean("NotiGoing", true);
                } else if (!on) {
                    Alarm.stopAlarm();
                    prefsEditor.putBoolean("NotiGoing", false);
                }
            }
        });
        sound_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(context, "comming soon", Toast.LENGTH_LONG).show();
            }
        });

        column1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "comming soon", Toast.LENGTH_LONG).show();
            }
        });

        column2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "comming soon", Toast.LENGTH_LONG).show();
            }
        });

        column3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "comming soon", Toast.LENGTH_LONG).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefsEditor.remove("remember");
                prefsEditor.remove("userID");
                prefsEditor.remove("walk");
                prefsEditor.remove("stand");
                prefsEditor.remove("cycle");
                prefsEditor.remove("exercise");
                prefsEditor.remove("other");
                prefsEditor.apply();
                prefsEditor.commit();
                act = new Intent(context, MainActivity.class);
                act.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(act);
            }
        });

    }

    private void createImage(){
        column1 = (ImageView) findViewById(R.id.column1);
        column2 = (ImageView) findViewById(R.id.column2);
        column3 = (ImageView) findViewById(R.id.column3);
        column4 = (ImageView) findViewById(R.id.column4);
        column5 = (ImageView) findViewById(R.id.column5);
    }


}
