package e.android.sensmotion.JDBC_Interfaces;

import java.util.List;

import e.android.sensmotion.DTO.PatientDTO;
import e.android.sensmotion.DTO.SensorDTO;

public interface PatientDAO {

    List<PatientDTO> getPatienter() throws  DALException;
    PatientDTO getPatient(String CPR) throws DALException;

    void opretPatient(PatientDTO Patient) throws DALException;

}
