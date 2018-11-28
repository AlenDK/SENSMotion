package e.android.sensmotion.controller.interfaces;

import java.util.List;

import e.android.sensmotion.entities.bruger.Bruger;

public interface IBrugerController {

    List<Bruger> getBrugerListe();
    Bruger getBruger(String id);


}
