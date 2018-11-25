package e.android.sensmotion.views;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import e.android.sensmotion.JDBC_DataTransferObjects.PatientDTO;
import e.android.sensmotion.JDBC_Implementations.MySQLPatientDAO;
import e.android.sensmotion.JDBC_Interfaces.DALException;
import e.android.sensmotion.R;

public class test_Frag extends Fragment implements  View.OnClickListener  {
/*
    PatientDTO patient = new PatientDTO();
    MySQLPatientDAO pat = new MySQLPatientDAO();
    Button opret;
    EditText PatientID, CPR, Mob, navn, projekt;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test, container, false);

        PatientID = (EditText) view.findViewById(R.id.ID);
        CPR = (EditText) view.findViewById(R.id.CPR);
        projekt = (EditText) view.findViewById(R.id.Projekt);
        Mob = (EditText) view.findViewById(R.id.mobil);
        navn = (EditText) view.findViewById(R.id.Navn);

        patient.setCPR("hej");
        patient.setNavn("Al");
        patient.setTerapeut("test");
        patient.setProjekt("virk");
        patient.setMobilitet(2);
        patient.setId(13);

        opret = (Button) view.findViewById(R.id.button);
        opret.setOnClickListener(this);


        return view;

    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button:



                try {
                    pat.opretPatient(patient);
                } catch (DALException e) {
                    e.printStackTrace();
                }

                break;
        }

}
*/
}