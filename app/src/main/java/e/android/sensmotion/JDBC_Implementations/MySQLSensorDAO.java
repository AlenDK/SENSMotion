package e.android.sensmotion.JDBC_Implementations;

import e.android.sensmotion.JDBC_Connection.Connector;
import e.android.sensmotion.JDBC_DataTransferObjects.PatientDTO;
import e.android.sensmotion.JDBC_Interfaces.DALException;

public class MySQLSensorDAO {





/* Kan bruges, skal bare skiftes til sensor

    public void editActive(PatientDTO Patient) throws DALException {
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
