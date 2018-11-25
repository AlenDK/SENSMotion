package e.android.sensmotion.JDBC_Interfaces;

import java.util.List;

import e.android.sensmotion.JDBC_DataTransferObjects.PatientDTO;

public class TerapeutDAO {

    List<PatientDTO> patientliste;
    PatientDTO patient1 = new PatientDTO(5, "33-31-2222", "Nærum Læge", "Jøren", "Alen", 3);
    PatientDTO patient2 = new PatientDTO(2, "12-23-2312", "DTU", "Jørgen", "Simon", 3);


    public void createUsers() {
        patientliste.add(patient1);
        patientliste.add(patient2);


    }


}
