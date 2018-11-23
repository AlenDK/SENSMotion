package e.android.sensmotion.JDBC_DataTransferObjects;

import java.util.List;

public class TerapeutDTO {

    List<PatientDTO> Patienter;


    public TerapeutDTO(List<PatientDTO> Patienter) {
        this.Patienter = Patienter;
    }

    public List<PatientDTO> getPatienter() {
        return Patienter;
    }

    public void setPatienter(List<PatientDTO> patienter) {
        Patienter = patienter;
    }

    public void tilføjPatient(PatientDTO patient) {
        this.Patienter.add(patient);
    }

    public void sletPatient(PatientDTO patient) {
        this.Patienter.remove(patient);
    }
}
