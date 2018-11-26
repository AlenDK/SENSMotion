package e.android.sensmotion.controller.interfaces;

import e.android.sensmotion.dataTest.Value;

public interface IDataController {
    void serviceSuccess(Value value);
    void serviceFailure(Exception exception);
}
