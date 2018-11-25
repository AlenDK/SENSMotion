package e.android.sensmotion.serviceTest;

import e.android.sensmotion.dataTest.Values;

public interface SENScallback {
    void serviceSuccess(Values values);
    void serviceFailure(Exception exception);
}
