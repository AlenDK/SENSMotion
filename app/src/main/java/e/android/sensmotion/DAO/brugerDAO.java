package e.android.sensmotion.DAO;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class brugerDAO {

    PatientDTO patient = new PatientDTO();

    private DatabaseReference mDatabase;




    public void createUser (String terapeut) {
        mDatabase = FirebaseDatabase.getInstance().getReference("" + terapeut);
        patient = new Patient(x,x,x);

    }


}






