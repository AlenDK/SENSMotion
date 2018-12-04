package e.android.sensmotion.controller.interfaces;

import java.util.List;

import e.android.sensmotion.entities.bruger.User;
import e.android.sensmotion.entities.bruger.Patient;

public interface IUserController {

    List<User> getUserList();
    User getUser(String id);
    Patient getPatient(String id);


}
