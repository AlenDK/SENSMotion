package e.android.sensmotion.JDBC_Implementations;

import com.google.firebase.database.DatabaseReference;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import e.android.sensmotion.JDBC_DataTransferObjects.PatientDTO;
import e.android.sensmotion.JDBC_DataTransferObjects.SensorDTO;
import e.android.sensmotion.JDBC_Interfaces.DALException;
import e.android.sensmotion.JDBC_Interfaces.PatientDAO;
import e.android.sensmotion.JDBC_Connection.Connector;


/*

public class MySQLPatientDAO implements PatientDAO {
    private DatabaseReference mDatabase;
    private PatientDTO Patient;

    public void Connect(String Path) {
           mDatabase = FirebaseDatabase.getInstance().getReference("" + Path);
       }
    public void createUser (String CPR, List<SensorDTO> Sensorer, String Mobilitet, String Projekt, String Terapeut, String Navn, boolean isActive) {


        Patient = new PatientDTO(CPR, Mobilitet, Projekt, Terapeut, Navn, isActive);

        mDatabase.child(CPR).setValue(Patient);

    }

    @Override
    public List<PatientDTO> getPatienter() throws DALException {
        List<PatientDTO> List = new ArrayList<PatientDTO>();
        ResultSet rs = Connector.getInstance().doQuery("SELECT * FROM patient");
        try
        {
            while (rs.next())
            {
                List.add(new PatientDTO(rs.getInt("Patient_id"), rs.getString("CPR"), rs.getString("Projekt"), rs.getString("Terapuet"), rs.getString("Navn"), rs.getInt("Mobilitet")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List;
    }

    @Override
    public PatientDTO getPatient(String CPR) throws DALException {
        ResultSet rs = Connector.getInstance().doQuery("SELECT * FROM patient WHERE CPR = " + CPR);
        try {
            if (!rs.first())
                throw new DALException("Patienten med CPR-nummeret " + CPR + ", findes ikke");
            return new PatientDTO(rs.getInt("Patient_id"), rs.getString("CPR"), rs.getString("Projekt"), rs.getString("Terapuet"), rs.getString("Navn"),  rs.getInt("Mobilitet"));
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

    @Override
    public void opretPatient(PatientDTO Patient) throws DALException {
        Connector.getInstance().doUpdate(
                "INSERT INTO patient VALUES " +
                        "(" + Patient.getId() + ", '" + Patient.getCPR() + "', '" +
                         Patient.getProjekt() + "', '" + Patient.getTerapeut() + "', '"
                        + Patient.getNavn() + "', " + Patient.getMobilitet() + ")"
        );
    }


    public void editActive(PatientDTO Patient) throws  DALException {
        if (Patient.isActive() == true) {
            Connector.getInstance().doUpdate(
                    "UPDATE patient SET aktiv = 0 WHERE Patient_ID = " + Patient.isActive()
            );
        } else {
            Connector.getInstance().doUpdate(
                    "UPDATE patient SET aktiv = 1 WHERE Patient_ID = " + Patient.isActive()
            );
            }
    }

*/

}