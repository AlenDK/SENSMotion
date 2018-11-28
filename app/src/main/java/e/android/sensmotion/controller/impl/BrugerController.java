package e.android.sensmotion.controller.impl;

import java.util.ArrayList;
import java.util.List;

import e.android.sensmotion.controller.interfaces.IBrugerController;
import e.android.sensmotion.entities.bruger.Bruger;
import e.android.sensmotion.entities.bruger.Patient;

public class BrugerController implements IBrugerController {

    private List<Bruger> brugerListe = new ArrayList<Bruger>();

    public BrugerController(){
        Patient patient1 = new Patient("patient1", "patient1", "patient1", null, null, "k5W2uX", "6rT39u");
        brugerListe.add(patient1);
    }

    public List<Bruger> getBrugerListe() {
        return brugerListe;
    }
}
