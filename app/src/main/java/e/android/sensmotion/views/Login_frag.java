package e.android.sensmotion.views;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import e.android.sensmotion.R;
import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.controller.interfaces.IUserController;
import e.android.sensmotion.controller.interfaces.IDataController;
import e.android.sensmotion.data.Firebase;
import e.android.sensmotion.entities.user.Patient;

import io.fabric.sdk.android.Fabric;
import com.crashlytics.android.Crashlytics;


public class Login_frag extends Fragment implements View.OnClickListener {

    EditText brugernavn;
    CheckBox dataHandling;
    Intent act;
    IDataController dc;
    IUserController bc;
    String jsonString;
    Firebase firebasee = new Firebase();
    boolean EMULATOR = Build.PRODUCT.contains("sdk") || Build.MODEL.contains("Emulator");

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);



        if (!EMULATOR) {

            Fabric.with(getActivity(), new Crashlytics());
        }
/*
        forceCrash(view);
*/


        brugernavn = view.findViewById(R.id.brugernavn);
        dc = ControllerRegistry.getDataController();
        bc = ControllerRegistry.getUserController();

        TextView opret = (TextView) view.findViewById(R.id.opret);
        TextView glemt = (TextView) view.findViewById(R.id.glemtLogin);
        Button login = (Button) view.findViewById(R.id.logIndKnap);
        dataHandling = (CheckBox) view.findViewById(R.id.dataHandling);

        login.setOnClickListener(this);
        opret.setOnClickListener(this);
        glemt.setOnClickListener(this);




        return view;
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.logIndKnap:
                if (brugernavn.getText().toString().matches("admin")) {
                    act = new Intent(getActivity(), Terapuet_activity.class);
                    startActivity(act);
                    break;
                } else {

                    if (!dataHandling.isChecked()) {
                        Toast.makeText(getActivity(), "Du skal acceptere SENSmotion\'s vilkår " +
                                "for håndtering af personfølsomme data", Toast.LENGTH_LONG).show();

                        break;
                    }

                    try {
                        String hentDataResult = new HentDataAsyncTask().execute().get();
                        dc.saveData(hentDataResult, bc.getPatient("p1").getSensor("s1"));

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    System.out.println("/////////////////////////////// REST /////////////////////////////////////");
                    //System.out.println(bc.getPatient("p1").getSensor("s1").getCurrentPeriod().getValuesList().get(0).getRest());

                    System.out.println("/////////////////////////////// Other /////////////////////////////////////");
                    //System.out.println(bc.getPatient("p1").getSensor("s1").getCurrentPeriod().getValuesList().get(0).getOther());

                 //   for (int i = 0; i < 10; i++){

//                        Log.d("testtt", String.valueOf(bc.getPatient("p1").getSensor("s1").getCurrentPeriod().getValuesList().get(i)));


                        //Log.d("Første", String.valueOf(bc.getPatient("p1").getSensor("s1").getCurrentPeriod().getValuesList()));


// bc.getPatient("p1").getSensor("s1").getCurrentPeriod().getValuesList().get(i)
                  //      firebasee.newPeriod(null, null , 5 );

                    //Patient p = new Patient("Lol123",null, null, "3333", null, null, null, null);

                    /*
                    Period pp = new Period(2);



                    Sensor s = new Sensor("hejsa", 0);
                    Sensor s1 = new Sensor("hejsav2", 1);

                    List<Sensor> sensors = new ArrayList<>();

                    sensors.add(s);
                    sensors.add(s1);

                    Patient p = new Patient("Tobias",null, null, "3333", sensors, null, null, null);
                    firebasee.newPatient(p);
                    */

                    Patient p1 = ControllerRegistry.getUserController().getPatient("p1");
                    firebasee.newPatient(p1);


                       // firebasee.newTest(null, 5);

                   //     }



                    act = new Intent(getActivity(), PatientActivity.class);
                    startActivity(act);

                    break;


                }

            case R.id.opret:
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentindhold, new IkkeImplementeret_frag())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.glemtLogin:
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentindhold, new IkkeImplementeret_frag())
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }

    public static class IkkeImplementeret_frag extends Fragment {
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.ikke_implementeret, container, false);


            return view;
        }
    }

    class HentDataAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            jsonString = dc.getDataString(bc.getPatient("p1"), null);

            return jsonString;
        }
    }

    public void forceCrash(View view) {
        throw new RuntimeException("This is a crash");
    }


}


