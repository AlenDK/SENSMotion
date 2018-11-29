package e.android.sensmotion.controller.impl;

import java.util.ArrayList;
import java.util.List;

import e.android.sensmotion.controller.ControllerRegistry;
import e.android.sensmotion.controller.interfaces.IBrugerController;
import e.android.sensmotion.controller.interfaces.ISensorController;
import e.android.sensmotion.entities.Sensor;
import e.android.sensmotion.entities.bruger.Bruger;
import e.android.sensmotion.entities.bruger.Patient;
import e.android.sensmotion.entities.bruger.Terapeut;

public class BrugerController implements IBrugerController {

    private List<Bruger> brugerListe = new ArrayList<Bruger>();
    private List<Patient> patientList = new ArrayList<Patient>();
    private ISensorController sc;

    public BrugerController(){

        sc = ControllerRegistry.getSensorController();

        List<Sensor> p1Sensorer = new ArrayList<>();
        p1Sensorer.add(sc.getSensor("s1"));

        Patient patient1 = new Patient("p1","p1", "p1", "p1", p1Sensorer,
                null, "k5W2uX", "6rT39u");
        brugerListe.add(patient1);

        Patient patient2 = new Patient("p2", "p2", "p2", "p2",
                null, null, null, null);
        brugerListe.add(patient2);

        Patient patient3 = new Patient("p3", "p3", "p3", "p3",
                null, null, null, null);
        brugerListe.add(patient3);

        patientList.add(patient1);
        patientList.add(patient2);
        patientList.add(patient3);

        Terapeut terapeut1 = new Terapeut("t1", "t1", "t1", patientList);
        brugerListe.add(terapeut1);
    }

    public List<Bruger> getBrugerListe() {
        return brugerListe;
    }

    public Bruger getBruger(String id){
        for(Bruger b : brugerListe){
            if(b.getId().equals(id)){
                return b;
            }
        }
        return null;
    }

    public Patient getPatient(String id){
        for(Patient p : patientList){
            if(p.getId().equals(id)){
                return p;
            }
        }
        return null;
    }
}
