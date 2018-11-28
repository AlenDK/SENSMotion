package e.android.sensmotion.controller.interfaces;

import java.util.List;

import e.android.sensmotion.entities.bruger.Bruger;
import e.android.sensmotion.entities.bruger.Patient;

public interface IBrugerController {

    List<Bruger> getBrugerListe();
    Bruger getBruger(String id);
    Patient getPatient(String id);


}
