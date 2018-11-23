package e.android.sensmotion.JDBC_Implementations;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import e.android.sensmotion.DTO.PatientDTO;
import e.android.sensmotion.DTO.SensorDTO;
import e.android.sensmotion.JDBC_Interfaces.DALException;
import e.android.sensmotion.JDBC_Interfaces.PatientDAO;

public class MySQLPatientDAO implements PatientDAO {
    private DatabaseReference mDatabase;
    private PatientDTO Patient;


    public void createUser (String CPR, List<SensorDTO> Sensorer, String Mobilitet, String Projekt, String Terapeut, String Navn, boolean isActive) {


        Patient = new PatientDTO(CPR, Sensorer, Mobilitet, Projekt, Terapeut, Navn, isActive);

        mDatabase.child(CPR).setValue(Patient);

    }
    /*public void Connect(String Path) {
        mDatabase = FirebaseDatabase.getInstance().getReference("" + Path);
    }*/

    @Override
    public List<PatientDTO> getPatienter() throws DALException {
        return null;
    }

    @Override
    public PatientDTO getPatient(String CPR) throws DALException {
        return null;
    }

    @Override
    public void opretPatient(String CPR, List<SensorDTO> Sensorer, String Mobilitet, String Projekt, String Terapeut, String Navn, boolean isActive) throws DALException {

    }
}






