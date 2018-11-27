package e.android.sensmotion.controller.interfaces;

import e.android.sensmotion.entities.Value;

public interface SENScallback {
    void serviceSuccess(Value value);
    void serviceFailure(Exception exception);
}
