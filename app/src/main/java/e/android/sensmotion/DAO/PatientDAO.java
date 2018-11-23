package e.android.sensmotion.DAO;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import e.android.sensmotion.DTO.PatientDTO;
import e.android.sensmotion.DTO.SensorDTO;

public class PatientDAO {
    private DatabaseReference mDatabase;
    private PatientDTO Patient;


    public void createUser (String CPR, List<SensorDTO> Sensorer, String Mobilitet, String Projekt, String Navn, boolean isActive) {


        Patient = new PatientDTO(CPR, Sensorer, Mobilitet, Projekt, Navn, isActive);

        mDatabase.child(CPR).setValue(Patient);

    }
    public void Connect(String Path) {
        mDatabase = FirebaseDatabase.getInstance().getReference("" + Path);
    }
}






