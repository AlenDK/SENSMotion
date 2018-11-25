package e.android.sensmotion.serviceTest;

import e.android.sensmotion.dataTest.Value;

public interface SENScallback {
    void serviceSuccess(Value value);
    void serviceFailure(Exception exception);
}
