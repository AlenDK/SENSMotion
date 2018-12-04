package e.android.sensmotion.controller.interfaces;

import java.util.List;

import e.android.sensmotion.entities.user.User;
import e.android.sensmotion.entities.user.Patient;

public interface IUserController {

    List<User> getUserList();
    User getUser(String id);
    Patient getPatient(String id);


}
