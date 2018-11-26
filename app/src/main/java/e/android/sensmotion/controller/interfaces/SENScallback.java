package e.android.sensmotion.controller.interfaces;

import e.android.sensmotion.dataTest.Value;
import e.android.sensmotion.dataTest.Values;

public interface SENScallback {
    void serviceSuccess(Value value);
    void serviceFailure(Exception exception);
}
